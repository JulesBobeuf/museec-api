package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.UpdateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.ApiUserToApiUserDtoMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<ApiUserDto> getUsers() {
		List<ApiUser> users = userRepository.findAll();
		return ApiUserToApiUserDtoMapper.INSTANCE.listApiUserToListApiUser(users);
	}
	
	public ApiUserDto getUser(int id) {
		Optional<ApiUser> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return ApiUserToApiUserDtoMapper.INSTANCE.apiUserToApiUserDto(optionalUser.get());
		}
		return null;
	}
	
	public ApiUserDto updateUser(int id, UpdateUserRequest request, String email) {
		Optional<ApiUser> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			return null;
		}
		ApiUser user = optionalUser.get();
		
		// if the user is trying to update a user that is not himself, make sure it's an administrator.
		if (! email.equals(user.getEmail())) {
			Optional<ApiUser> optionalAdminUser = userRepository.findByEmail(email);
			if (optionalAdminUser.isEmpty()) {
	            throw new IllegalArgumentException("Forbidden");
			}
			if (optionalAdminUser.get().getRole() != Role.ADMINISTRATOR) {
	            throw new IllegalArgumentException("Forbidden");
			}
		}
		user.setAge(request.getAge());
		user.setCountry(request.getCountry());
		user.setDateUpdate(LocalDateTime.now());
		user.setDescription(request.getDescription());
		user.setLookingForAGroup(request.isLookingForAGroup());
		user.setGender(request.getGender());
		user.setInstruments(request.getInstruments());
		user.setSkills(request.getSkills());
		user.setMusicStyles(request.getMusicStyles());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setProfilePicture(request.getProfilePicture());
		
		user = userRepository.save(user);
		return ApiUserToApiUserDtoMapper.INSTANCE.apiUserToApiUserDto(user);
	}
	
	/**
	 * Method that allows a user to delete his account / an admin to delete a user
	 * @param id
	 * @param email
	 * @return
	 */
	public boolean deleteUser(int id, String email) {
		Optional<ApiUser> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			return false;
		}
		ApiUser user = optionalUser.get();
		
		// if the user is trying to delete a user that is not himself, make sure it's an administrator.
		if (! email.equals(user.getEmail())) {
			Optional<ApiUser> optionalAdminUser = userRepository.findByEmail(email);
			if (optionalAdminUser.isEmpty()) {
	            throw new IllegalArgumentException("Forbidden");
			}
			if (optionalAdminUser.get().getRole() != Role.ADMINISTRATOR) {
	            throw new IllegalArgumentException("Forbidden");
			}
		}
		userRepository.delete(user);
		return true;
	}
	
    /**
     * Method that allows to ban a user
     */
    public Boolean banUser(int id) {
    	Optional<ApiUser> user = userRepository.findById(id);
    	if (user.isEmpty()) {
    		return false;
    	}
    	ApiUser realUser = user.get();
    	boolean isNotBan = realUser.isAccountNonLocked();
    	if (isNotBan) {
    		realUser.setLocked(true);
    		userRepository.save(realUser);
    	}
    	return true;
    }
    
    /**
     * Method that allows to unban a user
     */
    public Boolean unbanUser(int id) {
    	Optional<ApiUser> user = userRepository.findById(id);
    	if (user.isEmpty()) {
    		return false;
    	}
    	ApiUser realUser = user.get();
    	boolean isBan = realUser.isLocked();
    	if (isBan) {
    		realUser.setLocked(false);
    		userRepository.save(realUser);
    	}
    	return true;
    }
}
