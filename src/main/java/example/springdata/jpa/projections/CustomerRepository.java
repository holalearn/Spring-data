package example.springdata.jpa.projections;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Oliver Gierke
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	/**
	 * interface投影：接口方法指定返回字段.
	 */
	Collection<CustomerProjection> findAllProjectedBy();

	/**
	 * 动态属性投影：如：{@link Value}注解内的SpEL表达式,会加载正常的target entity然后再动态投影，以方便表达式对于target的引用
	 */
	Collection<CustomerSummary> findAllSummarizedBy();

	/**
	 * interface投影 & 手动声明查询 ： 需要保证使用别名与投影字段匹配
	 */
	@Query("select c.firstname as firstname, c.lastname as lastname from Customer c")
	Collection<CustomerProjection> findsByProjectedColumns();

	/**
	 * DTO投影：使用具体的DTO类型来指定返回字段. 这个将转换为查询语句中的构造函数表达式
	 */
	Collection<CustomerDto> findAllDtoedBy();
	
	/**
	 * DTO投影：在声明的查询语句中使用构造器表达式
	 */
	@Query("select new example.springdata.jpa.projections.CustomerDto(c.firstname) from Customer c where c.firstname = ?1")
	Collection<CustomerDto> findDtoWithConstructorExpression(String firstname);

	/**
	 * 动态类型投影：(interface或DTO).
	 */
	<T> Collection<T> findByFirstname(String firstname, Class<T> projection);

	/**
	 * 单个记录的投影.
	 */
	CustomerProjection findProjectedById(Long id);

	/**
	 * 动态投影单个记录.
	 */
	<T> T findProjectedById(Long id, Class<T> projection);

	/**
	 * 翻页 & 投影
	 */
	Page<CustomerProjection> findPagedProjectedBy(Pageable pageable);

	/**
	 * Optional & 投影
	 */
	Optional<CustomerProjection> findOptionalProjectionByLastname(String lastname);
}
