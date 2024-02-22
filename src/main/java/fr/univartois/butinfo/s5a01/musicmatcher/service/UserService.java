package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;
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
