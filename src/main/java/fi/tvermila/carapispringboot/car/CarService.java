package fi.tvermila.carapispringboot.car;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CarService {
	
	ResponseEntity<List<Car>> getCars(String make, String model, Integer minYear, Integer maxYear);
	
	ResponseEntity<?> getCarById(long id);
	
	ResponseEntity<?> deleteCarById(long id);
	
	ResponseEntity<?> updateCarById(long id, Car updatedCar);
	
	ResponseEntity<?> addCar(Car car);

}
