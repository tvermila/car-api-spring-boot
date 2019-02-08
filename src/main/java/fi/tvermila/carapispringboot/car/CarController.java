package fi.tvermila.carapispringboot.car;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	@GetMapping("/cars/{id}")
	public ResponseEntity<?> getCarById(@PathVariable long id) {
		if (carRepository.existsById(id)) 
			return ResponseEntity.ok(carRepository.getOne(id));		
		else
			return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/cars/{id}")
	public ResponseEntity<?> deleteCarById(@PathVariable long id) {
		if (carRepository.existsById(id)) {
			carRepository.deleteById(id);
			return ResponseEntity.ok().build();			
		}				
		else
			return ResponseEntity.notFound().build();	
	}
	
	@PutMapping("/cars/{id}")
	public ResponseEntity<?> updateCarById(@PathVariable long id,
										   @RequestBody Car car) {
		if (carRepository.existsById(id)) {
			carRepository.save(car);
			return ResponseEntity.ok().build();			
		}				
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cars")
	public ResponseEntity<?> addCar(@RequestBody Car car, UriComponentsBuilder uri) {
		Car addedCar = carRepository.save(car);
		if (addedCar != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(addedCar.getId()).toUri();
			return ResponseEntity.created(location).build();			
		}
		else
			return ResponseEntity.unprocessableEntity().build();
		
	}

}
