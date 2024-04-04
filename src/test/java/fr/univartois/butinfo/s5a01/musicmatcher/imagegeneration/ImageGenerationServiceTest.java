package fr.univartois.butinfo.s5a01.musicmatcher.imagegeneration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.GenerateImageFromImageRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ImageGenerationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.RetrieveDeleteGeneratedImageDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.ImageGenerationService;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.ImageStyle;

@SpringBootTest
class ImageGenerationServiceTest {
	
	@Value("${python-server-path}")
	private String pythonServerPath;

	@Value("${save-pfp-path}")
	private String pfpPath;

	@Value("${pfp-extension}")
	private String pfpext;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Autowired
	private ImageGenerationService imageGenerationService;
	
	@Test
	void testGenerateImageFromPrompt() throws IOException {
		
		int userid = 0;
		
		ImageGenerationRequest request = new ImageGenerationRequest();
		request.setId(userid);
		request.setPrompt("hey this is a test prompt");
		request.setStyle(ImageStyle.COLOR_SPLASH);
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("id", String.valueOf(request.getId()));
		requestBody.put("style", request.getStyle().getName());
		requestBody.put("prompt", request.getPrompt());

		URI uri = null;
		try {
			uri = new URI(String.format("%simage/generate", pythonServerPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		byte[] bytesFromFile;
		
		try (Stream<Path> stream = Files.list(Paths.get(pfpPath))) {
			bytesFromFile = Files.readAllBytes(stream.findFirst().get());
	    } catch (IOException e) {
			e.printStackTrace();
			bytesFromFile = new byte[2048];
		}
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));
		
		when(restTemplate.postForEntity(uri, requestBody, byte[].class)).thenReturn(ResponseEntity.of(Optional.of(bytesFromFile)));
		
		assertTrue(imageGenerationService.generateImageFromPrompt(request));
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
        		request.setId(1);
        		imageGenerationService.generateImageFromPrompt(request);
        	}
        });

	}
	
	@Test
	void testGenerateImageFromImage() throws IOException {
		
		int userid = 0;
		
		GenerateImageFromImageRequest request = new GenerateImageFromImageRequest();
		request.setId(userid);
		request.setPath("path");
		request.setPrompt("a cool prompt");
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("id", String.valueOf(request.getId()));
		requestBody.put("prompt", request.getPrompt());
		requestBody.put("path", request.getPath());

		URI uri = null;
		try {
			uri = new URI(String.format("%simage/generate", pythonServerPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		byte[] bytesFromFile;
		
		try (Stream<Path> stream = Files.list(Paths.get(pfpPath))) {
			bytesFromFile = Files.readAllBytes(stream.findFirst().get());
	    } catch (IOException e) {
			e.printStackTrace();
			bytesFromFile = new byte[2048];
		}
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));
		
		when(restTemplate.postForEntity(uri, requestBody, byte[].class)).thenReturn(ResponseEntity.of(Optional.of(bytesFromFile)));
		
		assertTrue(imageGenerationService.generateImageFromImage(request));
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
        		request.setId(1);
        		imageGenerationService.generateImageFromImage(request);
        	}
        });

	}
	
	@Test
	void getImagesPathTest() {
		
		int userid = 0;
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("id", String.valueOf(userid));
		
		Map<String, List<String>> response = new HashMap<>();
		response.put("file_list", List.of("file1.png", "file2.png"));
		
		URI uri = null;
		try {
			uri = new URI(String.format("%simage/for-id", pythonServerPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));
		
		when(restTemplate.postForEntity(uri, requestBody, Map.class)).thenReturn(ResponseEntity.of(Optional.of(response)));
		
		assertTrue(imageGenerationService.getImagesPath(0).contains("file1.png"));
		
		
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
        		imageGenerationService.getImagesPath(1);
        	}
        });

	}
	
	@Test
	void retrievePfpTest() throws IOException {
		
		int userid = 0;
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		byte[] bytesFromFile;
		
		try (Stream<Path> stream = Files.list(Paths.get(pfpPath))) {
			bytesFromFile = Files.readAllBytes(stream.findFirst().get());
	    } catch (IOException e) {
			e.printStackTrace();
			bytesFromFile = new byte[2048];
		}
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));
		
		assertThat(imageGenerationService.retrieveProfilePictureImage(userid).available()).isEqualTo(new ByteArrayInputStream(bytesFromFile).available());
		
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
        		imageGenerationService.retrieveProfilePictureImage(913901);
        	}
        });

	}
	
	@Test
	void retrieveGeneratedImageTest() throws IOException {
		
		int userid = 0;
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		RetrieveDeleteGeneratedImageDto request = new RetrieveDeleteGeneratedImageDto();
		request.setId(userid);
		request.setPath("mypath");
		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("path", request.getPath());
		
		URI uri = null;
		try {
			uri = new URI(String.format("%simage/by-path", pythonServerPath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		byte[] bytesFromFile;
		
		try (Stream<Path> stream = Files.list(Paths.get(pfpPath))) {
			bytesFromFile = Files.readAllBytes(stream.findFirst().get());
	    } catch (IOException e) {
			e.printStackTrace();
			bytesFromFile = new byte[2048];
		}
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));
		
		when(restTemplate.postForEntity(uri, requestBody, byte[].class)).thenReturn(ResponseEntity.of(Optional.of(bytesFromFile)));
		
		assertThat(imageGenerationService.retrieveGeneratedImage(request).available()).isEqualTo(new ByteArrayInputStream(bytesFromFile).available());
		
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	request.setId(-9);
        		imageGenerationService.retrieveGeneratedImage(request);
        	}
        });

	}
	

	@Test
	void deleteImageTest() throws IOException {
		
		int userid = 0;
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(userid);
		apiUser.setEmail("toto@example.com");
		
		RetrieveDeleteGeneratedImageDto request = new RetrieveDeleteGeneratedImageDto();
		request.setId(userid);
		request.setPath("mypath");
		
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("path", request.getPath());
		
		when(userRepository.findById(userid)).thenReturn(Optional.of(apiUser));
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		
		when(restTemplate.exchange(
                String.format("%simage/by-path/delete", pythonServerPath),
                HttpMethod.DELETE,
                new HttpEntity<>(requestBody, headers),
                Void.class
        )).thenReturn(ResponseEntity.ok().build());
		
		assertTrue(imageGenerationService.deleteGeneratedImage(request));
		
		assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	request.setId(-9);
        		imageGenerationService.deleteGeneratedImage(request);
        	}
        });

	}

}
