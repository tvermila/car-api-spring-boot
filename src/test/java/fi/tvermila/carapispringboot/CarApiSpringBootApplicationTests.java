package fi.tvermila.carapispringboot;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarApiSpringBootApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Ignore
	public void shouldReturnSeededCars() throws Exception {
		this.mockMvc.perform(get("/cars")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].make", is("Honda")))
				.andExpect(jsonPath("$[3].id", is(4)))
				.andExpect(jsonPath("$[3].make", is("Toyota")));
	}
	
	@Test
	public void shouldReturnHonda() throws Exception {
		this.mockMvc.perform(get("/cars?make=Honda&model=Civic")
							.contentType(MediaType.APPLICATION_JSON))
							.andDo(print())
							.andExpect(status().isOk())
							.andExpect(jsonPath("$", hasSize(1)))
							.andExpect(jsonPath("$[0].id", is(1)))
							.andExpect(jsonPath("$[0].make", is("Honda")));
	}


}

