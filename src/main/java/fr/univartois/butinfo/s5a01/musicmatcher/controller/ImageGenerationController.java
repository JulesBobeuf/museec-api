package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.GenerateImageFromImageRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ImageGenerationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.RetrieveDeleteGeneratedImageDto;
import fr.univartois.butinfo.s5a01.musicmatcher.service.ImageGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ImageGeneration", description = "Image generation endpoint")
@RestController
@RequestMapping("/api/recommendation")
public class ImageGenerationController {
	
	@Autowired
	private ImageGenerationService imageGenerationService;
	
	@Operation(summary = "generateImageFromPrompt", description = "Generate an image from a prompt", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/genimgfromprompt")
	@ResponseBody
	public ResponseEntity<String> generateImageFromPrompt(@RequestBody ImageGenerationRequest request) {
        boolean res = imageGenerationService.generateImageFromPrompt(request);
        if (res) {
        	return ResponseEntity.ok("The image was generated successfully");
        }
    	return ResponseEntity.ok("The image could not be generated");
	}
	
	@Operation(summary = "generateImageFromImage", description = "Generate an image from an image", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/genimgfromimg")
	@ResponseBody
	public ResponseEntity<String> generateImageFromImage(@RequestBody GenerateImageFromImageRequest request) {
        boolean res = imageGenerationService.generateImageFromImage(request);
        if (res) {
        	return ResponseEntity.ok("The image was generated successfully");
        }
    	return ResponseEntity.ok("The image could not be generated");
	}

	@Operation(summary = "getImagesPath", description = "Get all paths from generated images", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/getimgspath/{id}")
	@ResponseBody
	public ResponseEntity<List<String>> getImagesPath(@PathVariable int id) {
		return ResponseEntity.ok(imageGenerationService.getImagesPath(id));
	}
	
	@Operation(summary = "getProfilePicture", description = "Retrieve someone's profile picture", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/retrievepfp/{id}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> retrievePfp(@PathVariable int id) {
		InputStream pfpInputStream = imageGenerationService.retrieveProfilePictureImage(id);
		if (pfpInputStream != null) {
	        InputStreamResource resource = new InputStreamResource(pfpInputStream);
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_PNG)
					.body(resource);
			}
		return ResponseEntity.status(404).body(null);
	}
	
	@Operation(summary = "retrieveGeneratedImage", description = "Retrieve an already generated Image", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/retrieveimg")
	@ResponseBody
	public ResponseEntity<InputStreamResource> retrieveGeneratedImage(@RequestBody RetrieveDeleteGeneratedImageDto request) {
        InputStreamResource resource = new InputStreamResource(imageGenerationService.retrieveGeneratedImage(request));
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(resource);
	}

	@Operation(summary = "retrieveGeneratedImage", description = "Retrieve an already generatedImage", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@DeleteMapping("/deleteimg")
	@ResponseBody
	public ResponseEntity<String> deleteGeneratedImage(@RequestBody RetrieveDeleteGeneratedImageDto request) {
        boolean res = imageGenerationService.deleteGeneratedImage(request);
        if (res) {
        	return ResponseEntity.ok("The image was deleted successfully.");
        }
    	return ResponseEntity.ok("The image could not be deleted.");
	}

	@Operation(summary = "updateProfilePictureService", description = "Update the profile picture of the user", tags = { "ImageGeneration" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PutMapping("/profilepicture")
	@ResponseBody
	public ResponseEntity<String> updateProfilePictureService(@RequestBody RetrieveDeleteGeneratedImageDto request) {
        boolean res = imageGenerationService.updateProfilePicture(request);
        if (res) {
        	return ResponseEntity.ok("The image was updated successfully");
        }
    	return ResponseEntity.ok("The image could not be updated");
	}
	
}
