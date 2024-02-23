package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.UpdateUserRequest;
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
	
	@Operation(summary = "getUsers", description = "Get users", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<ApiUserDto>> getUsers() {
		List<ApiUserDto> users = userService.getUsers();
		return ResponseEntity.ok(users);
	}
	
	@Operation(summary = "getUser", description = "Get a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<ApiUserDto> getUser(@PathVariable int id) {
		ApiUserDto user = userService.getUser(id);
		if (user!=null) {
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@Operation(summary = "updateUser", description = "Update a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> updateUser(Authentication authentication, @PathVariable int id, @RequestBody UpdateUserRequest request) {
		boolean wasUpdated = userService.updateUser(id, request, authentication.getName());
		if (wasUpdated) {
			return ResponseEntity.ok("User was updated Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
	}
	
	
	@Operation(summary = "deleteUser", description = "Delete a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> deleteUser(Authentication authentication, @PathVariable int id) {
		boolean wasDeleted = userService.deleteUser(id, authentication.getName());
		if (wasDeleted) {
			return ResponseEntity.ok("User was deleted successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
	}
	
	
	@Operation(summary = "banUser", description = "Ban a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/ban/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_UPDATE')")
	public ResponseEntity<String> banUser(@PathVariable int id) {
		boolean result = userService.banUser(id);
		if (result) {
			return ResponseEntity.ok("User was banned");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
	}

	@Operation(summary = "unbanUser", description = "Unban a user", tags = { "User" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@GetMapping("/unban/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_UPDATE')")
	public ResponseEntity<String> unbanUser(@PathVariable int id) {
		boolean result = userService.unbanUser(id);
		if (result) {
			return ResponseEntity.ok("User was unbanned");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
	}
	
}
