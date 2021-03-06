package example.springdata.jpa.simple;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NamedQuery(name = "User.findByTheUsersName", query = "from User u where u.username = ?1")
public class User extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -2952735933715107252L;

	@Column(unique = true) private String username;

	private String firstname;
	private String lastname;

	public User() {
		this(null);
	}

	public User(Long id) {
		this.setId(id);
	}

}
