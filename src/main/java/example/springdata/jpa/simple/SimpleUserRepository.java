package example.springdata.jpa.simple;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link User}实例的简单repository接口.
 * 
 */
public interface SimpleUserRepository extends CrudRepository<User, Long> {

	/**
	 * NamedQuery
	 * 该方法使用{@link User}类中{@link javax.persistence.NamedQuery}注解转换为查询语句
	 * 
	 * @param lastname
	 * @return
	 */
	User findByTheUsersName(String username);

	/**
	 * Optional
	 * 使用{@link Optional}作为返回和参数类型.
	 * 
	 * @param username
	 * @return
	 */
	Optional<User> findByUsername(Optional<String> username);

	/**
	 * method name
	 * 该方法直接根据方法名称转换为查询语句，没有其他声明的查询
	 * 
	 * @param lastname
	 * @return
	 */
	List<User> findByLastname(String lastname);

	/**
	 * Query
	 * 该方法转换为{@link Query}注解声明的查询语句.
	 * 
	 * @param firstname
	 * @return
	 */
	@Query("select u from User u where u.firstname = :firstname")
	List<User> findByFirstname(String firstname);

	/**
	 * Returns all users with the given name as first- or lastname. This makes the query to method relation much more
	 * refactoring-safe as the order of the method parameters is completely irrelevant.
	 * 
	 * @param name
	 * @return
	 */
	@Query("select u from User u where u.firstname = :name or u.lastname = :name")
	List<User> findByFirstnameOrLastname(String name);

	/**
	 * Returns the total number of entries deleted as their lastnames match the given one.
	 * 
	 * @param lastname
	 * @return
	 */
	Long removeByLastname(String lastname);

	/**
	 * Returns a {@link Slice} counting a maximum number of {@link Pageable#getPageSize()} users matching given criteria
	 * starting at {@link Pageable#getOffset()} without prior count of the total number of elements available.
	 * 
	 * @param lastname
	 * @param page
	 * @return
	 */
	Slice<User> findByLastnameOrderByUsernameAsc(String lastname, Pageable page);

	/**
	 * Return the first 2 users ordered by their lastname asc.
	 * 
	 * <pre>
	 * Example for findFirstK / findTopK functionality.
	 * </pre>
	 * 
	 * @return
	 */
	List<User> findFirst2ByOrderByLastnameAsc();

	/**
	 * Return the first 2 users ordered by the given {@code sort} definition.
	 * 
	 * <pre>
	 * This variant is very flexible because one can ask for the first K results when a ASC ordering
	 * is used as well as for the last K results when a DESC ordering is used.
	 * </pre>
	 * 
	 * @param sort
	 * @return
	 */
	List<User> findTop2By(Sort sort);

	/**
	 * Return all the users with the given firstname or lastname. Makes use of SpEL (Spring Expression Language).
	 *
	 * @param user
	 * @return
	 */
	@Query("select u from User u where u.firstname = :#{#user.firstname} or u.lastname = :#{#user.lastname}")
	Iterable<User> findByFirstnameOrLastname(User user);
}
