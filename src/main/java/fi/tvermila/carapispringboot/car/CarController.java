package fi.tvermila.carapispringboot.car;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
	
	@Autowired
	private CarRepository carRepository;
	
	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getCars(@RequestParam(required=false) String make,
							 @RequestParam(required=false) String model,
							 @RequestParam(required=false) Integer minYear,
							 @RequestParam(required=false) Integer maxYear) {
		List<Car> cars = carRepository.findAll();
		
		//filters cars by make
		if (make != null) {
			cars = cars.stream()
					.filter(car -> car.getMake().equals(make))
					.collect(Collectors.toList());
		}
		
		//filters cars by model
		if (model != null) {
			cars = cars.stream()
					.filter(car -> car.getModel().equals(model))
					.collect(Collectors.toList());
		}
		
		//filters cars by minimum year of manufacture
		if (minYear != null) {
			cars = cars.stream()
					.filter(car -> car.getYear() >= minYear)
					.collect(Collectors.toList());
		}
		
		//filters cars by maximum year of manufacture
		if (maxYear != null) {
			cars = cars.stream()
					.filter(car -> car.getYear() <= maxYear)
					.collect(Collectors.toList());
		}		
		
		return ResponseEntity.ok(cars);
	}

}
