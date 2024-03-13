package fr.univartois.butinfo.s5a01.musicmatcher.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;

@Document
public class Token {

	@Id
	public Integer id;

	public String token;

	public TokenType tokenType = TokenType.BEARER;

	public boolean revoked;

	public boolean expired;

	@DocumentReference()
	public ApiUser user;

	public Token(Integer id, String token, TokenType tokenType, boolean revoked, boolean expired, ApiUser user) {
		super();
		this.id = id;
		this.token = token;
		this.tokenType = tokenType;
		this.revoked = revoked;
		this.expired = expired;
		this.user = user;
	}
	
	public Token(Integer id, String token, boolean revoked, boolean expired, ApiUser user) {
		super();
		this.id = id;
		this.token = token;
		this.revoked = revoked;
		this.expired = expired;
		this.user = user;
	}

	public Token() {
		// should be empty
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public ApiUser getUser() {
		return user;
	}

	public void setUser(ApiUser user) {
		this.user = user;
	}
}
