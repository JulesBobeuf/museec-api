package fr.univartois.butinfo.s5a01.musicmatcher.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.RecommendationService;

@SpringBootTest
class RecommendationServiceTest {
	
	@Value("${python-server-path}")
	private String pythonServerPath;

	@Value("${save-pfp-path}")
	private String pfpPath;

	@Value("${pfp-extension}")
	private String pfpext;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private OfferRepository offerRepository;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Autowired
	private RecommendationService recommendationService;
	
	@Test
	void testJaccard() {
		int userid = 0;
		String offer1Name = "offer1";

		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		offer1.setName(offer1Name);
		offer1.setActive(true);
		
		OfferDto offerDto1 = new OfferDto();
		offerDto1.setId(1);
		offerDto1.setName(offer1Name);
		offerDto1.setActive(true);
		
		List<String> recommendationList = new ArrayList<>();
		recommendationList.add("1");
		
		Map<String, Integer> requestBody = new HashMap<>();
		requestBody.put("id", userid);
		
		Map<String, List<String>> response = new HashMap<>();
		response.put("recommendation_list", recommendationList);
		
		URI uri = null;
		try {
			uri = new URI(String.format("%srecommendation/jaccard", pythonServerPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));

		when(offerRepository.findByIdIn(List.of(1))).thenReturn(List.of(offer1));

		when(restTemplate.postForEntity(uri, requestBody, Map.class)).thenReturn(ResponseEntity.ok(response));
		
		assertEquals(recommendationService.jaccardRecommendation(userid).get(0).getName(), offer1Name);
		assertThrows(NullPointerException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	recommendationService.jaccardRecommendation(4);
        	}
        });

		when(restTemplate.postForEntity(uri, requestBody, Map.class)).thenReturn(ResponseEntity.ok(null));
		assertEquals(0, recommendationService.jaccardRecommendation(userid).size());
	}
	
	@Test
	void testMatrix() {
		int userid = 0;
		String offer1Name = "offer1";

		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		offer1.setName(offer1Name);
		offer1.setActive(true);
		
		OfferDto offerDto1 = new OfferDto();
		offerDto1.setId(1);
		offerDto1.setName(offer1Name);
		offerDto1.setActive(true);
		
		List<String> recommendationList = new ArrayList<>();
		recommendationList.add("1");
		
		Map<String, Integer> requestBody = new HashMap<>();
		requestBody.put("id", userid);
		
		Map<String, List<String>> response = new HashMap<>();
		response.put("recommendation_list", recommendationList);
		
		URI uri = null;
		try {
			uri = new URI(String.format("%srecommendation/matrix", pythonServerPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));

		when(offerRepository.findByIdIn(List.of(1))).thenReturn(List.of(offer1));

		when(restTemplate.postForEntity(uri, requestBody, Map.class)).thenReturn(ResponseEntity.ok(response));
		
		assertEquals(recommendationService.matrixRecommendation(userid).get(0).getName(), offer1Name);
		assertThrows(NullPointerException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	recommendationService.matrixRecommendation(4);
        	}
        });
		
		when(restTemplate.postForEntity(uri, requestBody, Map.class)).thenReturn(ResponseEntity.ok(null));
		assertEquals(0, recommendationService.matrixRecommendation(userid).size());
	}
}
