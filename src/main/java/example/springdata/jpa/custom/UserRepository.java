package example.springdata.jpa.custom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// 继承自定义接口UserRepositoryCustom
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

	/**
	 * 使用NamedQuery
	 * 通过指定username查找User. 这个方法会转换为使用{@link User}类中注解{@link javax.persistence.NamedQuery}的查询语句
	 */
	User findByTheUsersName(String username);

	/**
	 * 通过指定lastname查找所有User. 这个方法将直接根据方法名称转换，没有其他查询语句声明
	 */
	List<User> findByLastname(String lastname);

	/**
	 * 通过指定firstname查找所有User. 这个方法将根据{@link Query}注解声明的查询转换查询语句
	 */
	@Query("select u from User u where u.firstname = :firstname")
	List<User> findByFirstname(String firstname);
	
}
