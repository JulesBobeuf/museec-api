package fr.univartois.butinfo.s5a01.musicmatcher;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@SecurityScheme(
		  name = "BearerAuthentication",
		  type = SecuritySchemeType.HTTP,
		  bearerFormat = "JWT",
		  scheme = "bearer"
		)
public class OpenAPIConfig {

	@Bean
	public OpenAPI myOpenAPI() {

		Contact contact = new Contact();
		contact.setEmail("thomas_santoro@ens.univ-artois.fr");
		contact.setName("Thomas SANTORO");

		License license = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("API for our Music Matcher application").version("0.1").contact(contact)
				.description("This API exposes endpoints to manage the Music Matcher application").license(license);

		return new OpenAPI().info(info)
				.security(List.of(new SecurityRequirement().addList("BearerAuthentication")));
	}
}