package fr.univartois.butinfo.s5a01.musicmatcher.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;

@Repository
public interface OfferRepository extends MongoRepository<Offer, Integer>{
	
	@Query(value = "{ '_id' : {'$in' : ?0 } }")
    List<Offer> findByIdIn(Iterable<Integer> ids);

}
