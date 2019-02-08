package fi.tvermila.carapispringboot.car;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

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
	private int motor;
	private Date inspectionDate;	

}
