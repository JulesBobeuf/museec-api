package fr.univartois.butinfo.s5a01.musicmatcher.service;

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
		System.out.println(username);
		Optional<ApiUser> optionalUser = userRepository.findByEmail(username);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UsernameNotFoundException("User does not exist");
		}
	}

    /**
     * Permet de changer le mot de passe d'un utilisateur.
     *
     * @param request La requête pour changer de mot de passe.
     * @param user L'utilisateur à mettre à jour.
     */
    public String changePassword(ChangePasswordDto request) {
        // On commence par regarder si le mot de passe est correct.
    	ApiUser user = (ApiUser) loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        
        // On vérifie ensuite si le mot de passe et la confirmation sont les mêmes.
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalArgumentException("Password are not the same");
        }

        // Il faut mettre à jour l'utilisateur maintenant.
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
		return "Password changed sucessfully";
    }
    
    public String createUser(CreateUserRequest request) {
    	if (! request.getPassword().equals(request.getConfirmPassword())) {
        	return "Failed to create Users : Passwords don't match";
    	}
    	ApiUser user = CreateUserRequestToApiUserMapper.INSTANCE.createUserRequestToApiUser(request);
    	user.setId(sequenceGeneratorService.generateSequence(ApiUser.SEQUENCE_NAME));
    	user.setPassword(passwordEncoder.encode(request.getPassword()));
    	userRepository.save(user);
    	return "User created successfully";
    }
    
    public String login(AuthenticationRequest request) {
    	System.out.println("yi");
    	UserDetails user = loadUserByUsername(request.getEmail());
    	System.out.println(request.getEmail());
    	if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    	    System.out.println("ts");

    	    Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    	    SecurityContextHolder.getContext().setAuthentication(authentication);
    	    System.out.println("ts");
    	    String jwt = jwtService.generateToken(user);
            return jwt;
    	}
    	return "Can't login : wrong credentials";
    }
}
