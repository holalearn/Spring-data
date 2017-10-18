package example.springdata.jpa.custom;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
// @ActiveProfiles("jdbc") // @ActiveProfiles使用自定义repository的JDBC实现
public class UserRepositoryCustomizationTests {

	// 直接注入UserRepository bean
	@Autowired UserRepository repository;

	/**
	 * Tests inserting a user and asserts it can be loaded again.
	 */
	@Test
	public void testInsert() {

		User user = new User();
		user.setUsername("username");

		user = repository.save(user);

		assertThat(repository.findById(user.getId())).hasValue(user);
	}

	@Test
	public void saveAndFindByLastNameAndFindByUserName() {

		User user = new User();
		user.setUsername("foobar");
		user.setLastname("lastname");

		user = repository.save(user);

		List<User> users = repository.findByLastname("lastname");

		assertThat(users).contains(user);
		assertThat(user).isEqualTo(repository.findByTheUsersName("foobar"));
	}

	/**
	 * Test invocation of custom method.
	 */
	@Test
	public void testCustomMethod() {

		User user = new User();
		user.setUsername("username");

		user = repository.save(user);

		List<User> users = repository.myCustomBatchOperation();

		assertThat(users).contains(user);
	}
}
