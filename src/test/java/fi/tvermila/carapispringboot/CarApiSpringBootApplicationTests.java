package fi.tvermila.carapispringboot;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runners.MethodSorters;
import fi.tvermila.carapispringboot.car.Car;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarApiSpringBootApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test1_shouldReturnSeededCars() throws Exception {
		this.mockMvc.perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].make", is("Honda"))).andExpect(jsonPath("$[3].id", is(4)))
				.andExpect(jsonPath("$[3].make", is("Toyota")));
	}

	@Test
	public void test2_shouldReturnHonda() throws Exception {
		this.mockMvc.perform(get("/cars?make=Honda&model=Civic").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].make", is("Honda")));
	}

	@Test
	public void test3_shouldReturnOneById() throws Exception {
		this.mockMvc.perform(get("/cars/3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(3))).andExpect(jsonPath("$.make", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q7"))).andExpect(jsonPath("$.plate", is("CDB-456")))
				.andExpect(jsonPath("$.engine", is(1799))).andExpect(jsonPath("$.year", is(2017)))
				.andExpect(jsonPath("$.power", is(170))).andExpect(jsonPath("$.dateOfInspection", is("2017-06-10")));
	}

	@Test
	public void test4_shouldThrowNotFoundException() throws Exception {
		this.mockMvc.perform(get("/cars/11")).andExpect(status().isNotFound());
	}
	
	@Test
	public void test5_shouldAddNewCar() throws Exception {
		Car newCar = new Car(0, "Opel", "Vectra", "BOL-912", 2006, 150, 1900, LocalDate.of(2019, 9, 1));
		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc
				.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(newCar)))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("http://localhost/cars/5")));
	}

	@Test
	public void test6_shouldUpdateCar() throws Exception {
		Car updatedCar = new Car(0, "Opel", "Vectra", "BOL-912", 2006, 150, 1900, LocalDate.of(2019, 9, 1));
		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc.perform(
				put("/cars/4").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updatedCar)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void test7_shouldDeleteCar() throws Exception {
		this.mockMvc.perform(delete("/cars/1")).andExpect(status().isNoContent());
	}

}
