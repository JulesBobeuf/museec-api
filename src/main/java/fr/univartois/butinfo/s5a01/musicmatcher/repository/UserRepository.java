package fr.univartois.butinfo.s5a01.musicmatcher.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;

@Repository
public interface UserRepository extends MongoRepository<ApiUser, String>{

	public Optional<ApiUser> findByEmail(String email);
	
}
