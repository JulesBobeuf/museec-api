package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    
    public String createUser(CreateUserRequest request) {
    	if (! request.getPassword().equals(request.getConfirmPassword())) {
        	return "Failed to create User : Passwords don't match";
    	}
    	if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        	return "Failed to create User : email already exists";
    	}
    	ApiUser user = CreateUserRequestToApiUserMapper.INSTANCE.createUserRequestToApiUser(request);
    	
    	user.setId(sequenceGeneratorService.generateSequence(ApiUser.SEQUENCE_NAME));
    	user.setDateCreation(LocalDateTime.now());
    	user.setDateUpdate(LocalDateTime.now());
    	user.setLocked(false);
    	user.setHistory(new ArrayList<>());
    	user.setPassword(passwordEncoder.encode(request.getPassword()));
    	
    	userRepository.save(user);
    	return "User created successfully";
    }
    
    public String login(AuthenticationRequest request) {
    	Optional<ApiUser> optionalUser = userRepository.findByEmail(request.getEmail());
    	if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
    	}
    	ApiUser user = optionalUser.get();
    	if (user.isLocked()) {
            throw new IllegalArgumentException("User is banned");
    	}
    	if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    	    Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    	    SecurityContextHolder.getContext().setAuthentication(authentication);
    	    String jwt = jwtService.generateToken(user);
            return jwt;
    	}
    	return "Can't login : wrong credentials";
    }
}
