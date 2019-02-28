package fi.tvermila.carapispringboot.car;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private CarRepository carRepository;
	
	public ResponseEntity<List<Car>> getCars(String make, String model, Integer minYear,
							 Integer maxYear) {
		List<Car> cars = carRepository.findAll();
		
		//filters cars by make
		if (make != null ) {
			cars = cars.stream()
					.filter(car -> car.getMake().equalsIgnoreCase(make))
					.collect(Collectors.toList());
		}
		
		//filters cars by model
		if (model != null) {
			cars = cars.stream()
					.filter(car -> car.getModel().equalsIgnoreCase(model))
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
	
	public ResponseEntity<?> getCarById(long id) {
		if (carRepository.existsById(id)) 
			return ResponseEntity.ok(carRepository.getOne(id));		
		else
			return ResponseEntity.notFound().build();
		
	}
	
	public ResponseEntity<?> deleteCarById(long id) {
		if (carRepository.existsById(id)) {
			carRepository.deleteById(id);
			return ResponseEntity.noContent().build();			
		}				
		else
			return ResponseEntity.notFound().build();	
	}
	
	public ResponseEntity<?> updateCarById(long id, Car updatedCar) {
		if (carRepository.existsById(id)) {
			//Get car from database, update its attributes and save it to database
			Car car = carRepository.getOne(id);
			car.setMake(updatedCar.getMake());
			car.setModel(updatedCar.getModel());
			car.setEngine(updatedCar.getEngine());
			car.setPlate(updatedCar.getPlate());
			car.setPower(updatedCar.getPower());
			car.setYear(updatedCar.getYear());
			car.setDateOfInspection(updatedCar.getDateOfInspection());
			carRepository.save(car);
			return ResponseEntity.ok(car);			
		}				
		else
			return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> addCar(Car car) {
		Car addedCar = carRepository.save(car);
		URI location = ServletUriComponentsBuilder				
				.fromCurrentRequest().path("/{id}")					
				.buildAndExpand(addedCar.getId())
				.toUri();
			
		return ResponseEntity.created(location).build();		
	}

}
