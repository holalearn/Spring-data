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
 */
public interface SimpleUserRepository extends CrudRepository<User, Long> {

	/**
	 * NamedQuery
	 * 该方法使用{@link User}类中{@link javax.persistence.NamedQuery}注解转换为查询语句
	 */
	User findByTheUsersName(String username);

	/**
	 * Optional
	 * 使用{@link Optional}作为返回和参数类型.
	 */
	Optional<User> findByUsername(Optional<String> username);

	/**
	 * method name
	 * 该方法直接根据方法名称转换为查询语句，没有其他声明的查询
	 */
	List<User> findByLastname(String lastname);

	/**
	 * Query
	 * 该方法转换为{@link Query}注解声明的查询语句.
	 */
	@Query("select u from User u where u.firstname = :firstname")
	List<User> findByFirstname(String firstname);

	/**
	 * Query
	 * 该方法转换为{@link Query}注解声明的查询语句.
	 */
	@Query("select u from User u where u.firstname = :name or u.lastname = :name")
	List<User> findByFirstnameOrLastname(String name);

	/**
	 * remove
	 * 返回删除记录的总数
	 */
	Long removeByLastname(String lastname);

	/**
	 * Pageable
	 * 返回符合给定条件的最大页{@link Pageable#getPageSize()}的切片{@link Slice}
	 */
	Slice<User> findByLastnameOrderByUsernameAsc(String lastname, Pageable page);

	/**
	 * FirstK / TopK
	 */
	List<User> findFirst2ByOrderByLastnameAsc();

	/**
	 * Sort & TopK
	 * 返回根据{@code sort}排序后的前2个user
	 */
	List<User> findTop2By(Sort sort);

	/**
	 * SpEL
	 * 使用SpEL (Spring Expression Language).
	 */
	@Query("select u from User u where u.firstname = :#{#user.firstname} or u.lastname = :#{#user.lastname}")
	Iterable<User> findByFirstnameOrLastname(User user);
}
