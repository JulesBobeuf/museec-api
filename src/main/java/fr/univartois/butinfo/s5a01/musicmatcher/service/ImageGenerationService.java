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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.RetrieveDeleteGeneratedImageDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.request.GenerateImageFromImageRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.request.ImageGenerationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.ConvertUtils;

@Service
public class ImageGenerationService {

	private static final String FILEPATH_FORMAT = "%s%d%s";

	private static final String FORBIDDEN_MESSAGE = "Forbidden";

	@Autowired
	private RestTemplate restTemplate;

	@Value("${python-server-path}")
	private String pythonServerPath;

	@Value("${save-pfp-path}")
	private String pfpPath;
	
	@Value("${save-band-pfp-path}")
	private String bandPfpPath;

	@Value("${pfp-extension}")
	private String pfpext;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BandRepository bandRepository;

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
		requestBody.put("prompt", request.getPrompt());
		requestBody.put("image_path", request.getPath());

		URI uri = null;
		try {
			uri = new URI(String.format("%simage/generate", pythonServerPath));
		} catch (URISyntaxException e) {
			return false;
		}
		restTemplate.postForEntity(uri, requestBody, byte[].class);
		return true;
	}
	
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
		
		ResponseEntity<Map<String, List<String>>> responseEntity = ConvertUtils.toT(restTemplate.postForEntity(uri, requestBody, Map.class));
		Map<String, List<String>> body = responseEntity.getBody();
		if (body==null) {
			return Collections.emptyList();
		}
		return body.get("file_list");
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
		
		String filepath = String.format(FILEPATH_FORMAT, pfpPath, id, pfpext);
		File savePfp = new File(filepath);
		
		try {
			return new FileInputStream(savePfp);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public boolean updateProfilePicture(RetrieveDeleteGeneratedImageDto request) {

		Optional<ApiUser> optionalUser = userRepository.findById(request.getId());
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}

		ApiUser user = optionalUser.get();
		
		InputStream retrieveGeneratedImage = retrieveGeneratedImage(request);

		String filepath = String.format(FILEPATH_FORMAT, pfpPath, request.getId(), pfpext);
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
	
	
	/**
	 * Retrieve a band's profile picture
	 * @param id
	 * @return
	 */
	public InputStream retrieveBandProfilePictureImage(int id) {

		Optional<Band> optionalBand = bandRepository.findById(id);
		if (optionalBand.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
		
		String filepath = String.format(FILEPATH_FORMAT, bandPfpPath, id, pfpext);
		File savePfp = new File(filepath);
		
		try {
			return new FileInputStream(savePfp);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Method that saves a band profile picture
	 * @param request
	 * @return
	 */
	public boolean saveBandProfilePicture(int bandId, MultipartFile image) {

		Optional<Band> optionalBand = bandRepository.findById(bandId);
		if (optionalBand.isEmpty()) {
			throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
		
		String filepath = String.format(FILEPATH_FORMAT, bandPfpPath, bandId, pfpext);
		File savePfp = new File(filepath);
		
		try {
			FileCopyUtils.copy(image.getInputStream(), new FileOutputStream(savePfp));
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
