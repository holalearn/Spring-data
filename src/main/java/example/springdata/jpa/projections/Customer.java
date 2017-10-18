package example.springdata.jpa.projections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Customer {

	private @GeneratedValue @Id Long id;
	private final String firstname, lastname;

	protected Customer() {
		this.firstname = null;
		this.lastname = null;
	}
}
