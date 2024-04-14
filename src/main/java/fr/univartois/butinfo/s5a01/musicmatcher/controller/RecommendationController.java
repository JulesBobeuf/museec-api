package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Recommendation", description = "Recommendation endpoint")
@RestController
@CrossOrigin
@RequestMapping("/api/recommendation")
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;
	
	@Operation(summary = "getRecommendationsWithJaccard", description = "Get recommendations with the jaccard algorithm", tags = { "Recommendation" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/jaccard/{id}")
	@ResponseBody
	public ResponseEntity<List<OfferDto>> getOffersWithJaccard(@PathVariable int id) {
		return ResponseEntity.ok(recommendationService.jaccardRecommendation(id));
	}
	
	@Operation(summary = "getMatricialRecomendation", description = "Get Matricial Recomendation", tags = { "Recommendation" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/matrix/{id}")
	@ResponseBody
	public ResponseEntity<List<OfferDto>> getOffersWithMatricialRecomendation(@PathVariable int id) {
		return ResponseEntity.ok(recommendationService.factoMatricielRecommendation(id));
	}
	
	
}
