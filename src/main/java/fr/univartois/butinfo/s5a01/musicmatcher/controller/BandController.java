package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.BandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateBandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.service.BandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Band", description = "Band endpoint")
@RestController
@RequestMapping("/api/band")
public class BandController {

	@Autowired
	private BandService bandService;
	
	@Operation(summary = "createBand", description = "Create a band", tags = { "Band" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<String> createBand(Authentication authentication, @RequestBody CreateUpdateBandDto request) {
		boolean isCreated = bandService.createBand(request, authentication.getName());
		if (isCreated) {
			return ResponseEntity.ok("The band was created successfully");

		}
		return ResponseEntity.badRequest().body("The band could not be created. Bad request");
	}
	
	@Operation(summary = "getBand", description = "Get a band", tags = { "Band" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "4O4", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<BandDto> getBand(@PathVariable int id) {
		BandDto band = bandService.getBand(id);
		if (band != null) {
			return ResponseEntity.ok(band);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@Operation(summary = "getBands", description = "Get all bands", tags = { "Band" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<BandDto>> getAllBands() {
		return ResponseEntity.ok(bandService.getAllBands());
	}
	
}
