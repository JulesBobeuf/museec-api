package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateBandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.CreateUpdateBandDtoToBandMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class BandService {

	@Autowired
	private BandRepository bandRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceService;
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean createBand(CreateUpdateBandDto request) {
		Band band = CreateUpdateBandDtoToBandMapper.INSTANCE.createUpdateBandDtoToBand(request);
		if (userRepository.findById(band.getOwner()).isPresent()) {
			band.setId(sequenceService.generateSequence(Band.SEQUENCE_NAME));
			LocalDateTime now = LocalDateTime.now();
			band.setDateCreation(now);
			band.setDateUpdate(now);
			bandRepository.save(band);
			return true;
		}
		return false;
	}
}
