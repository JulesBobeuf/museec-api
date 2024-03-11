package fr.univartois.butinfo.s5a01.musicmatcher.band;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.controller.BandController;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.BandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.service.BandService;
import jakarta.servlet.ServletContext;

@SpringBootTest
@AutoConfigureMockMvc
class BandControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private BandService bandService;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}
	
	@Test
	void mockitoMvcWorksTest() {
	    ServletContext servletContext = webApplicationContext.getServletContext();
	    assertNotNull(servletContext);
	    assertTrue(servletContext instanceof MockServletContext);
	    assertNotNull(webApplicationContext.getBean("bandController"));
	}
	
	@Test
	void getBandsTest() throws Exception {
	    this.mockMvc.perform(get("/api/band/"))
	    		.andExpect(status().isOk())
	    	    .andExpect(content().contentType("application/json"));
	}
	
	@Test
	void getBandTest() throws Exception {
		when(bandService.getBand(1)).thenReturn(new BandDto());
		
	    this.mockMvc.perform(get("/api/band/{id}", 1))
			.andExpect(status().isOk())
		    .andExpect(content().contentType("application/json"));
		    
	    this.mockMvc.perform(get("/api/band/{id}", 8796))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(username = "toto@example.org", password="passwd", roles={"READ", "ADD", "UPDATE", "DELETE"})
	void deleteBandTest() throws Exception {
		ApiUser apiUser = new ApiUser();
		apiUser.setId(1);
		apiUser.setEmail("toto@example.org");
		apiUser.setPassword("passwd");
		apiUser.setRole(Role.ADMINISTRATOR);
		
		when(bandService.deleteBand(anyInt(), anyString())).thenReturn(false);
		when(bandService.deleteBand(eq(1), anyString())).thenReturn(true);
		
	    this.mockMvc.perform(delete("/api/band/{id}", 1))
			.andExpect(status().isOk())
		    .andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.message").value("The band was deleted successfully"));
		    
	    this.mockMvc.perform(delete("/api/band/{id}", 8796))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value("The band was not found"));
	}
	
}
