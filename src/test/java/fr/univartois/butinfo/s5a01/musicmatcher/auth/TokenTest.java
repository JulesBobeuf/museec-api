package fr.univartois.butinfo.s5a01.musicmatcher.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;

@SpringBootTest
class TokenTest {

	@Test
	void tokenTest() {
		ApiUser apiUser = new ApiUser();
		apiUser.setEmail("email");
		apiUser.setPassword("Passwd");
		apiUser.setRole(Role.ADMINISTRATOR);
		
		String tokenValue = "dskldqnlkqsndql";
		
		Token token = new Token();
		token.setId(1);
		token.setToken(tokenValue);
		token.setRevoked(true);
		token.setExpired(true);
		token.setUser(apiUser);
		
		assertThat(token.getId()).isEqualTo(1);
		assertThat(token.getToken()).isEqualTo(tokenValue);
		assertThat(token.isRevoked()).isTrue();
		assertThat(token.isExpired()).isTrue();
		assertThat(token.getUser().getRole()).isEqualTo(apiUser.getRole());
		
		Token token2 = new Token(2, tokenValue, false, false, apiUser);
		token2.setTokenType(TokenType.BEARER);
		
		assertThat(token2.getId()).isEqualTo(2);
		assertThat(token2.getToken()).isEqualTo(tokenValue);
		assertThat(token2.getUser()).isEqualTo(apiUser);
		assertThat(token2.getTokenType()).isEqualTo(TokenType.BEARER);
		assertThat(token2.isExpired()).isFalse();
		assertThat(token2.isRevoked()).isFalse();

		Token token3 = new Token(2, tokenValue, TokenType.BEARER, false, false, apiUser);
		
		assertThat(token3.getId()).isEqualTo(2);
		assertThat(token3.getToken()).isEqualTo(tokenValue);
		assertThat(token3.getUser()).isEqualTo(apiUser);
		assertThat(token3.getTokenType()).isEqualTo(TokenType.BEARER);
		assertThat(token3.isExpired()).isFalse();
		assertThat(token3.isRevoked()).isFalse();
	}
}
