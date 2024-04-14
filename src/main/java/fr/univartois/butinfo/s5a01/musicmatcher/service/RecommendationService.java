package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.OfferToOfferDtoMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;

@Service
public class RecommendationService {
	
	@Value("${python-server-path}")
	private String pythonServerPath;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OfferRepository offerRepository;
	
	
	public List<OfferDto> jaccardRecommendation(int userid) {
		Map<String, Integer> requestBody = new HashMap<>();
		requestBody.put("id", userid);
		
		URI uri = null;
		try {
			uri = new URI(String.format("%srecommendation/jaccard", pythonServerPath));
		} catch(URISyntaxException e) {
			return Collections.emptyList();
		}
		
		@SuppressWarnings("unchecked")
		List<Integer> result = restTemplate.postForObject(uri, requestBody, List.class);
		return OfferToOfferDtoMapper.INSTANCE.listOfferToListOfferDto(offerRepository.findByIdIn(result));
	}

	
	public List<OfferDto> factoMatricielRecommendation(int userid) {
		Map<String, Integer> requestBody = new HashMap<>();
		requestBody.put("id", userid);
		
		URI uri = null;
		try {
			uri = new URI(String.format("%srecommendation/matrix", pythonServerPath));
		} catch(URISyntaxException e) {
			return Collections.emptyList();
		}
		
		@SuppressWarnings("unchecked")
		List<Integer> result = restTemplate.postForObject(uri, requestBody, List.class);
		return OfferToOfferDtoMapper.INSTANCE.listOfferToListOfferDto(offerRepository.findByIdIn(result));
	}

}

