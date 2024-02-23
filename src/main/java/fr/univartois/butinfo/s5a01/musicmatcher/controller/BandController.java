package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> register(Authentication authentication, @RequestBody CreateUpdateBandDto request) {
		boolean isCreated = bandService.createBand(request, authentication.getName());
		if (isCreated) {
			return ResponseEntity.ok("The band was created successfully");

		}
		return ResponseEntity.badRequest().body("The band could not be created. Bad request");
	}
}
