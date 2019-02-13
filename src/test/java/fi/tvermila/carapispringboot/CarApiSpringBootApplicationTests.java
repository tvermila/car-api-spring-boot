package fi.tvermila.carapispringboot;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.sql.Date;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.tvermila.carapispringboot.car.Car;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarApiSpringBootApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Ignore
	@Test
	public void shouldReturnSeededCars() throws Exception {
		this.mockMvc.perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].make", is("Honda"))).andExpect(jsonPath("$[3].id", is(4)))
				.andExpect(jsonPath("$[3].make", is("Toyota")));
	}

	@Ignore
	@Test
	public void shouldReturnHonda() throws Exception {
		this.mockMvc.perform(get("/cars?make=Honda&model=Civic").contentType(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].make", is("Honda")));
	}

	@Ignore
	@Test
	public void shouldReturnOneById() throws Exception {
		this.mockMvc.perform(get("/cars/3").contentType(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(3))).andExpect(jsonPath("$.make", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q7"))).andExpect(jsonPath("$.plate", is("CDB-456")))
				.andExpect(jsonPath("$.engine", is(1799))).andExpect(jsonPath("$.year", is(2017)))
				.andExpect(jsonPath("$.power", is(170))).andExpect(jsonPath("$.dateOfInspection", is("2017-06-10")));
	}

	@Ignore
	@Test
	public void shouldThrowNotFoundException() throws Exception {
		this.mockMvc.perform(get("/cars/11")).andExpect(status().isNotFound());
	}

	@Ignore
	@Test
	public void shouldAddNewCar() throws Exception {
		Car newCar = new Car(0, "Opel", "Vectra", "BOL-912", 2006, 150, 1900, Date.valueOf("2019-09-01"));
		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc
				.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(newCar)))
				// .andDo(print())
				.andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("http://localhost/cars/5")));
	}

	@Test
	public void shouldUpdateCar() throws Exception {
		Car updatedCar = new Car(0, "Opel", "Vectra", "BOL-912", 2006, 150, 1900, Date.valueOf("2019-09-01"));
		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc.perform(
				put("/cars/4").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updatedCar)))
				.andDo(print()).andExpect(status().isOk());
	}

}
