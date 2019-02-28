package fi.tvermila.carapispringboot.car;

import java.util.List;

import javax.validation.Valid;

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


@RestController
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getCars(@RequestParam(required=false) String make,
							 @RequestParam(required=false) String model,
							 @RequestParam(required=false) Integer minYear,
							 @RequestParam(required=false) Integer maxYear) {
		
		return carService.getCars(make, model, minYear, maxYear);		
	}
	
	@GetMapping("/cars/{id}")
	public ResponseEntity<?> getCarById(@PathVariable long id) {
		return carService.getCarById(id);		
	}
	
	@DeleteMapping("/cars/{id}")
	public ResponseEntity<?> deleteCarById(@PathVariable long id) {
		return carService.deleteCarById(id);
	}
	
	@PutMapping("/cars/{id}")
	public ResponseEntity<?> updateCarById(@PathVariable long id,
										   @Valid @RequestBody Car updatedCar) {
		return carService.updateCarById(id, updatedCar);
	}
	
	@PostMapping("/cars")
	public ResponseEntity<?> addCar(@Valid @RequestBody Car car) {
		return carService.addCar(car);		
	}

}
