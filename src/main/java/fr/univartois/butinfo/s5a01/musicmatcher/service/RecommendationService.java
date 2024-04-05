package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
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
		
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity(uri, requestBody, Map.class);
		Map<String, List<String>> body = responseEntity.getBody();
		List<Offer> findByIdIn = offerRepository.findByIdIn(body.get("recommendation_list").stream().map(Integer::valueOf).collect(Collectors.toList()));
		System.out.println(findByIdIn);
		return OfferToOfferDtoMapper.INSTANCE.listOfferToListOfferDto(findByIdIn);
	}
	
}

