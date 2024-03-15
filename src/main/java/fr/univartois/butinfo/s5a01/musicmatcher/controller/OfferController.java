package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateOfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.CreateUpdateOfferDtoToOfferMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.service.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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
		boolean result = offerService.acceptOffer(idUser,idOffer);
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
			return ResponseEntity.ok("Musician rejected of the band");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musician, owner or offer was not found");
	}
	
	@Operation(summary = "createOffer", description = "Create an offer", tags = { "Offer" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/createOffer")
	@ResponseBody
	public ResponseEntity<String> createOffer(@Valid @RequestBody CreateUpdateOfferDto request,Authentication authentication) {
		boolean wasCreated = offerService.createOffer(request,authentication.getName());
		if (wasCreated) {
			return ResponseEntity.ok("Offer created successfully");
		}
		return ResponseEntity.badRequest().body("Offer could not be created. Bad request");
	}
	
	@Operation(summary = "getOffer", description = "Get an offer", tags = { "Offer" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "4O4", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<OfferDto> getOffer(@PathVariable int id) {
		OfferDto offer = offerService.getOffer(id);
		if (offer != null) {
			return ResponseEntity.ok(offer);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@Operation(summary = "getOffers", description = "Get all offers", tags = { "Offer" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<OfferDto>> getAllOffers() {
		return ResponseEntity.ok(offerService.getAllOffers());
	}
	
	@Operation(summary = "updateOffer", description = "Update an offer", tags = { "Offer" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> updateOffer(Authentication authentication, @PathVariable int id, @Valid @RequestBody CreateUpdateOfferDto request) {
		boolean wasUpdated = offerService.updateOffer(id, request, authentication.getName());
		if (wasUpdated) {
			return ResponseEntity.ok("The offer was updated successfully");

		}
		return ResponseEntity.badRequest().body("The offer could not be updated. Bad request");
	}
	
	@Operation(summary = "deleteOffer", description = "Delete an oofer", tags = { "Offer" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> deleteOffer(Authentication authentication, @PathVariable int id) {
		boolean wasDeleted = offerService.deleteOffer(id, authentication.getName());
		if (wasDeleted) {
			return ResponseEntity.ok("The offer was deleted successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The offer was not found");
	}
	
}
