package fr.univartois.butinfo.s5a01.musicmatcher.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;

@Repository
public interface BandRepository extends MongoRepository<Band, Integer> {

}
