package fr.univartois.butinfo.s5a01.musicmatcher.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;

@Repository
public interface AuthRepository extends MongoRepository<ApiUser, String>{

}
