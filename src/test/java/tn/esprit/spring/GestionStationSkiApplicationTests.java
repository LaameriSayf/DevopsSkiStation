package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// Mockito imports
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.controllers.SkierRestController;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.SkierServicesImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GestionStationSkiApplicationTests {

	@Autowired
	private MockMvc mockMvc;
//junit
	@Test
	public void testGetSkiersByCity() throws Exception {
		mockMvc.perform(get("/skier/byCity/Tunisie"))
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$[0].city").value("Tunisie"))
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	public void testCountSkiersBySubscription() throws Exception {
		mockMvc.perform(get("/skier/countBySubscription/ANNUAL"))
				.andExpect(status().isOk());
	}
}

/////////mockito

@WebMvcTest(SkierRestController.class)
class SkierControllerMockitoTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SkierServicesImpl skierService;

	@Test
	public void testGetSkiersByCityWithMock() throws Exception {
		Skier mockSkier = new Skier();
		mockSkier.setCity("Tunisie");
		List<Skier> mockList = Collections.singletonList(mockSkier);
		when(skierService.retrieveSkiersByCity("Tunisie")).thenReturn(mockList);
		mockMvc.perform(get("/skier/byCity/Tunisie"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Tunisie"))
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	public void testCountSkiersBySubscriptionWithMock() throws Exception {
		when(skierService.countSkiersBySubscriptionType(TypeSubscription.ANNUAL)).thenReturn(2L);
		mockMvc.perform(get("/skier/countBySubscription/ANNUAL"))
				.andExpect(status().isOk())
				.andExpect(content().string("2"));
	}


}
