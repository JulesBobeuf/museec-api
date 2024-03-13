package fr.univartois.butinfo.s5a01.musicmatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univartois.butinfo.s5a01.musicmatcher.dto.AuthenticationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ChangePasswordDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "Authentication endpoint")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Operation(summary = "Change password", description = "Change password.", tags = { "Auth" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/changepswrd")
	@ResponseBody
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
		return ResponseEntity.ok(authService.changePassword(dto));
	}

	@Operation(summary = "register", description = "Create an account", tags = { "Auth" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/createuser")
	@ResponseBody
	public ResponseEntity<String> register(@Valid @RequestBody CreateUserRequest request) {
		boolean wasCreated = authService.createUser(request);
		if (wasCreated) {
			return ResponseEntity.ok("User registered successfully");
		}
		return ResponseEntity.badRequest().body("User could not be created. Bad request");
	}

	@Operation(summary = "login", description = "Log in!", tags = { "Auth" })
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<String> login(@Valid @RequestBody AuthenticationRequest request) {
	    String result = authService.login(request);
	    if (result.contains("wrong credentials")) {
	    	return ResponseEntity.status(401).body(result);
	    }
		return ResponseEntity.ok(result);
	}
}
