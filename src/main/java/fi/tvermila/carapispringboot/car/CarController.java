package fi.tvermila.carapispringboot.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
	
	@Autowired
	private CarRepository carRepository;
	
	@GetMapping("/cars")
	public List<Car> getCars() {
		List<Car> cars = carRepository.findAll();
		
		return cars;
	}

}
