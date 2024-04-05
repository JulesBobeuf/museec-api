package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.JwtService;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.AuthenticationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ChangePasswordDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.CreateUserRequestToApiUserMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	/*
	 * findByEmail
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ApiUser> optionalUser = userRepository.findByEmail(username);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UsernameNotFoundException("User does not exist");
		}
	}

	/**
	 * Change user password
	 * @param request
	 * @return String
	 */
    public String changePassword(ChangePasswordDto request) {
    	//check whether the user exists or not
    	Optional<ApiUser> optionalUser = userRepository.findByEmail(request.getEmail());
    	if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
    	}
    	
    	//check whether the user is banned or not
    	ApiUser user = optionalUser.get();
    	if (user.isLocked()) {
            throw new IllegalArgumentException("User is banned");
    	}        
    	
    	//if the old password is not matching the login password
    	if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        
        //if the new passwords are not equal
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalArgumentException("Password are not the same");
        }

        //update user
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
		return "Password changed sucessfully";
    }
    
    public boolean createUser(CreateUserRequest request) {
    	if (! request.getPassword().equals(request.getConfirmPassword())) {
        	return false;
    	}
    	if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        	return false;
    	}
    	ApiUser user = CreateUserRequestToApiUserMapper.INSTANCE.createUserRequestToApiUser(request);
        initUser(user);
    	user.setPassword(passwordEncoder.encode(request.getPassword()));
    	
    	userRepository.save(user);
    	return true;
    }

    public String login(AuthenticationRequest request) {
    	String errorMsg = "Can't login : wrong credentials";
    	Optional<ApiUser> optionalUser = userRepository.findByEmail(request.getEmail());
    	if (optionalUser.isEmpty()) {
        	return errorMsg;
    	}
    	ApiUser user = optionalUser.get();
    	if (user.isLocked()) {
        	return errorMsg;
    	}
    	if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    	    Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    	    SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtService.generateToken(user);
    	}
    	return errorMsg;
    }
    
    public Map<String, String> loginAndRetriveUser(AuthenticationRequest request) {
    	Optional<ApiUser> optionalUser = userRepository.findByEmail(request.getEmail());
    	Map<String, String> result = new HashMap<>();
    	if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User doesn't exists");
    	}
    	ApiUser user = optionalUser.get();
    	if (user.isLocked()) {
            throw new IllegalArgumentException("User is locked");
    	}
    	if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    	    Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    	    SecurityContextHolder.getContext().setAuthentication(authentication);
    	    result.put("jwt", jwtService.generateToken(user));
    	    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	    try {
				result.put("user", ow.writeValueAsString(user));
			} catch (JsonProcessingException e) {
				result.put("user", "error when serializing user to Json");
				e.printStackTrace();
			}
    	}
    	return result;
    }
    
    /**
     * Method that inits some attributes of a user.
     * @param user
     * @return the user updated
     */
	private ApiUser initUser(ApiUser user) {
		user.setId(sequenceGeneratorService.generateSequence(ApiUser.SEQUENCE_NAME));
    	LocalDateTime now = LocalDateTime.now();
		user.setDateCreation(now);
    	user.setDateUpdate(now);
    	user.setIdBand(-1);
    	user.setLocked(false);
    	user.setHistory(new ArrayList<>());
    	return user;
	}
}
