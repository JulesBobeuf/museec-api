package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.OfferToOfferDtoMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.ConvertUtils;

@Service
public class RecommendationService {
	
	@Value("${python-server-path}")
	private String pythonServerPath;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OfferRepository offerRepository;
	
	
	/**
	 * Retrieve the recommended offers for a user using the jaccard algorithm
	 * @param userid
	 * @return
	 */
	public List<OfferDto> jaccardRecommendation(int userid) {
		return retrieveData(userid, "%srecommendation/jaccard");
	}
	
	/**
	 * Retrieve the recommended offers for a user using the matrix factorisation algorithm
	 * @param userid
	 * @return
	 */
	public List<OfferDto> matrixRecommendation(int userid) {
		return retrieveData(userid, "%srecommendation/matrix");
	}
	
	
	/**
	 * Retrieve the recommended offers for a user.
	 * The process being the same for the two algorithms, we extracted process to improve maintainability
	 * @param userid
	 * @param formatString
	 * @return
	 */
	private List<OfferDto> retrieveData(int userid, String formatString) {
		Map<String, Integer> requestBody = new HashMap<>();
		requestBody.put("id", userid);
		
		URI uri = null;
		try {
			uri = new URI(String.format(formatString, pythonServerPath));
		} catch(URISyntaxException e) {
			return Collections.emptyList();
		}
		ResponseEntity<Map<String, List<String>>> responseEntity = ConvertUtils.toT(restTemplate.postForEntity(uri, requestBody, Map.class));
		Map<String, List<String>> body = responseEntity.getBody();
		
		if (body == null) {
			return Collections.emptyList();
		}
		
		List<Integer> listOfferId = body.get("recommendation_list")
				.stream()
				.map(Integer::valueOf)
				.toList();
		
		List<Offer> findByIdIn = offerRepository.findByIdIn(listOfferId);
		return OfferToOfferDtoMapper.INSTANCE.listOfferToListOfferDto(findByIdIn);
	}

}
