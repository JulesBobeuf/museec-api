package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "User endpoint")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "unbanUser", description = "Unban a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/deban")
	@ResponseBody
	public ResponseEntity<String> unbanUser(@RequestParam int id) {
		boolean result = userService.unbanUser(id);
		if (result) {
			ResponseEntity.ok("User was unbanned");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
	}
	
	@Operation(summary = "banUser", description = "Ban a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/ban")
	@ResponseBody
	public ResponseEntity<String> banUser(@RequestParam int id) {
		boolean result = userService.banUser(id);
		if (result) {
			ResponseEntity.ok("User was banned");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
	}
	
}
