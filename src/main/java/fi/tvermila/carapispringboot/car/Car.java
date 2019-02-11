package fi.tvermila.carapispringboot.car;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
public class Car {
	
	@Id
	@GeneratedValue
	private long id;
	private String make;
	private String model;
	private String plate;
	private int year;
	private int power;
	private int engine;
	private Date dateOfInspection;	

}
