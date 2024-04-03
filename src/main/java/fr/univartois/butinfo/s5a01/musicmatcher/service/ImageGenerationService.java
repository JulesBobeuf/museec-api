package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.GenerateImageFromImageRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ImageGenerationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.RetrieveDeleteGeneratedImageDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class ImageGenerationService {

	private static final String FORBIDDEN_MESSAGE = "Forbidden";

	@Autowired
	private RestTemplate restTemplate;

	@Value("${python-server-path}")
	private String pythonServerPath;

	@Value("${save-pfp-path}")
	private String pfpPath;

	@Value("${pfp-extension}")
	private String pfpext;

	@Autowired
	private UserRepository userRepository;

	public boolean generateImageFromPrompt(ImageGenerationRequest request) {
		Optional<ApiUser> optionalUser = userRepository.findById(request.getId());
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("id", String.valueOf(request.getId()));
		requestBody.put("style", request.getStyle().getName());
		requestBody.put("prompt", request.getPrompt());

		URI uri = null;
		try {
			uri = new URI(String.format("%simage/generate", pythonServerPath));
		} catch (URISyntaxException e) {
			return false;
		}
		restTemplate.postForEntity(uri, requestBody, byte[].class);
		return true;
	}
	
	public boolean generateImageFromImage(GenerateImageFromImageRequest request) {
		Optional<ApiUser> optionalUser = userRepository.findById(request.getId());
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("id", String.valueOf(request.getId()));
		requestBody.put("style", request.getStyle().getName());
		requestBody.put("path", request.getPath());

		URI uri = null;
		try {
			uri = new URI(String.format("%simage/generate", pythonServerPath));
		} catch (URISyntaxException e) {
			return false;
		}
		restTemplate.postForEntity(uri, requestBody, byte[].class);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getImagesPath(int userid) {
		Optional<ApiUser> optionalUser = userRepository.findById(userid);
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
		
		URI uri = null;
		try {
			uri = new URI(String.format("%simage/for-id", pythonServerPath));
		} catch (URISyntaxException e) {
			return Collections.emptyList();
		}
		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("id", String.valueOf(userid));
		
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity(uri, requestBody, Map.class);
		return (List<String>) responseEntity.getBody().get("file_list");
	}
	
	public InputStream retrieveGeneratedImage(RetrieveDeleteGeneratedImageDto request) {

		Optional<ApiUser> optionalUser = userRepository.findById(request.getId());
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("path", request.getPath());

		URI uri = null;
		try {
			uri = new URI(String.format("%simage/by-path", pythonServerPath));
		} catch (URISyntaxException e) {
			return null;
		}
		ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(uri, requestBody, byte[].class);
		byte[] responseBody = responseEntity.getBody();
		
		return new ByteArrayInputStream(responseBody);
	}
	
	public InputStream retrieveProfilePictureImage(int id) {

		Optional<ApiUser> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
		
		String filepath = String.format("%s%d%s", pfpPath, id, pfpext);
		File savePfp = new File(filepath);
		
		try {
			return new FileInputStream(savePfp);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public boolean updateProfilePictureService(RetrieveDeleteGeneratedImageDto request) {

		Optional<ApiUser> optionalUser = userRepository.findById(request.getId());
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}

		ApiUser user = optionalUser.get();
		
		InputStream retrieveGeneratedImage = retrieveGeneratedImage(request);

		String filepath = String.format("%s%d%s", pfpPath, request.getId(), pfpext);
		File savePfp = new File(filepath);
		
		try {
			FileCopyUtils.copy(retrieveGeneratedImage, new FileOutputStream(savePfp));
		} catch (Exception e) {
			return false;
		}

		user.setProfilePicture(filepath);
		userRepository.save(user);
		
		return true;
	}
	
	public boolean deleteGeneratedImage(RetrieveDeleteGeneratedImageDto request) {
		Optional<ApiUser> optionalUser = userRepository.findById(request.getId());
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}

		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("path", request.getPath());
		// Send DELETE request with request entity
        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("%simage/by-path/delete", pythonServerPath),
                HttpMethod.DELETE,
                new HttpEntity<>(requestBody, headers),
                Void.class
        );

        return response.getStatusCode().is2xxSuccessful();
    }
}
