package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.BandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateBandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.BandToBandDtoMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.CreateUpdateBandDtoToBandMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class BandService {

	private static final String FORBIDDEN = "Forbidden";

	@Autowired
	private BandRepository bandRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * Get all bands
	 * @return bands
	 */
	public List<BandDto> getAllBands() {
		List<Band> allBands = bandRepository.findAll();
		return BandToBandDtoMapper.INSTANCE.listBandToListBandDto(allBands);
	}
	
	/**
	 * Get a single band
	 * @param id
	 * @return The band. Result can be null if the band does not exist.
	 */
	public BandDto getBand(int id) {
		Optional<Band> optionalBand = bandRepository.findById(id);
		if (optionalBand.isPresent()) {
			return BandToBandDtoMapper.INSTANCE.bandToBandDto(optionalBand.get());
		}
		return null;
	}
	
	/**
	 * Create a band
	 * @param request
	 * @param email
	 * @return wasCreated
	 */
	public boolean createBand(CreateUpdateBandDto request, String email) {
		Band band = CreateUpdateBandDtoToBandMapper.INSTANCE.createUpdateBandDtoToBand(request);
		Optional<ApiUser> optionalOwner = userRepository.findById(band.getOwner());
		Optional<ApiUser> optionalUser = userRepository.findByEmail(email);
		if (optionalOwner.isPresent() && optionalUser.isPresent()) {
			ApiUser owner = optionalOwner.get();
			if ((owner.getIdBand()==-1 && owner.getEmail().equals(email)) || optionalUser.get().getRole() == Role.ADMINISTRATOR) {
				band.setId(sequenceService.generateSequence(Band.SEQUENCE_NAME));
				LocalDateTime now = LocalDateTime.now();
				band.setDateCreation(now);
				band.setDateUpdate(now);
				bandRepository.save(band);
				
				owner.setIdBand(band.getId());
				userRepository.save(owner);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Update a band
	 * @param id
	 * @param request
	 * @param email
	 * @return wasCreated
	 */
	public boolean updateBand(int id, CreateUpdateBandDto request, String email) {
		Optional<Band> optionalBand = bandRepository.findById(id);
		Optional<ApiUser> optionalUser = userRepository.findByEmail(email);
		if (optionalBand.isEmpty() || optionalUser.isEmpty()) {
			return false;
		}
		
		Band band = optionalBand.get();
		ApiUser user = optionalUser.get();
		
		if (band.getOwner() != user.getId() &&  (user.getRole() != Role.ADMINISTRATOR)) {
	            throw new IllegalArgumentException(FORBIDDEN);
		}
		
		// make sure the new owner exists
		if (userRepository.findById(request.getOwner()).isEmpty()) {
			return false;
		}
		
		band.setName(request.getName());
		band.setDescription(request.getDescription());
		band.setOwner(request.getOwner());
		band.setProfilePicture(request.getProfilePicture());
		band.setVideoLink(request.getVideoLink());
		band.setDateUpdate(LocalDateTime.now());
		bandRepository.save(band);
		return true;
	}
	
	/**
	 * Method that allows a user/an admin to delete a band
	 * @param id
	 * @param email
	 * @return
	 */
	public boolean deleteBand(int id, String email) {
		Optional<Band> optionalBand = bandRepository.findById(id);
		Optional<ApiUser> optionalUser = userRepository.findByEmail(email);
		
		if (optionalBand.isEmpty() || optionalUser.isEmpty()) {
			return false;
		}
		Band band = optionalBand.get();
		ApiUser user = optionalUser.get();
		
		// if the user is trying to delete a band that is not his, make sure it's an administrator.
		if (band.getOwner() != user.getId() &&  (user.getRole() != Role.ADMINISTRATOR)) {
	            throw new IllegalArgumentException(FORBIDDEN);
			
		}
		bandRepository.delete(band);
		return true;
	}
	
}
