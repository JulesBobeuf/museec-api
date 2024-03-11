package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.service.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Offer", description = "Gestion of offers")
@RestController
@RequestMapping("/api/offers")
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@Operation(summary = "acceptOffer", description = "Accept an offer", tags = { "Offer" })
	@ApiResponse(responseCode = "200", description = "Offer Accepted Successfully", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "User/Offer was not found", content = { @Content(schema = @Schema()) })
	@GetMapping("/acceptOffer")
	@ResponseBody
	public ResponseEntity<String> accepteOffer(@RequestParam int idUser, @RequestParam int idOffer) {
		boolean result = offerService.accepteOffer(idUser,idOffer);
		if (result) {
			return ResponseEntity.ok("User accept the offer succefully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or offer was not found");
	}
	
	@Operation(summary = "rejectOffer", description = "Reject an offer", tags = { "Offer" })
	@ApiResponse(responseCode = "200", description = "Offer rejected Successfully", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "User/Offer was not found", content = { @Content(schema = @Schema()) })
	@GetMapping("/rejectOffer")
	@ResponseBody
	public ResponseEntity<String> rejectOffer(@RequestParam int idUser, @RequestParam int idOffer) {
		boolean result = offerService.rejectOffer(idUser,idOffer);
		if (result) {
			return ResponseEntity.ok("Offer rejected succefully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or offer was not found");
	}
	
	@Operation(summary = "acceptMusician", description = "Accept a Musician in a band", tags = { "Offer" })
	@ApiResponse(responseCode = "200", description = "Musician accepted Successfully", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "Owner/Offer/Musician was not found", content = { @Content(schema = @Schema()) })
	@GetMapping("/acceptMusician")
	@ResponseBody
	public ResponseEntity<String> acceptMusician(@RequestParam int idUser, @RequestParam int idOffer) {
		boolean result = offerService.rejectOffer(idUser,idOffer);
		if (result) {
			return ResponseEntity.ok("Musician accepted in the band");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musician, owner or offer was not found");
	}
	
	@Operation(summary = "rejectMusician", description = "Reject a Musician in a band", tags = { "Offer" })
	@ApiResponse(responseCode = "200", description = "Musician rejected Successfully", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "Owner/Offer/Musician was not found", content = { @Content(schema = @Schema()) })
	@GetMapping("/rejectMusician")
	@ResponseBody
	public ResponseEntity<String> rejectMusician(@RequestParam int idUser, @RequestParam int idOffer) {
		boolean result = offerService.rejectOffer(idUser,idOffer);
		if (result) {
			return ResponseEntity.ok("Musician rejectef of the band");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musician, owner or offer was not found");
	}

}
