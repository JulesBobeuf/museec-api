package fr.univartois.butinfo.s5a01.musicmatcher.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;

public interface BandRepository extends MongoRepository<Band, Integer> {

}
