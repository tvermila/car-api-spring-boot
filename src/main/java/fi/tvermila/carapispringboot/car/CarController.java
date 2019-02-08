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
							 @RequestParam(required=false) String model) {
		List<Car> cars = carRepository.findAll();
		
		//filter cars by make
		if (make != null) {
			cars = cars.stream()
					.filter(car -> car.getMake().equals(make))
					.collect(Collectors.toList());
		}
		
		//filter cars by model
		if (model != null) {
			cars = cars.stream()
					.filter(car -> car.getModel().equals(model))
					.collect(Collectors.toList());
		}
		
		return ResponseEntity.ok(cars);
	}

}
