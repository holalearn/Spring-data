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
	 * 使用投影接口指定返回字段. 因为投影不使用任何动态字段， doesn't use any dynamic
	 * fields, the query execution will be restricted to only the fields needed by the projection.
	 * 
	 * @return
	 */
	Collection<CustomerProjection> findAllProjectedBy();

	/**
	 * 如果投影包含动态属性(i.e.  {@link Value}注解内的SpEL表达式),会加载正常的target entity然后再动态投影，以方便表达式对于target的引用
	 * 
	 * @return
	 */
	Collection<CustomerSummary> findAllSummarizedBy();

	/**
	 * 投影接口也可以和手动声明的查询语句一起使用. 需要保证使用别名与投影字段匹配
	 * 
	 * @return
	 */
	@Query("select c.firstname as firstname, c.lastname as lastname from Customer c")
	Collection<CustomerProjection> findsByProjectedColumns();

	/**
	 * 使用具体的DTO类型来指定返回字段. This gets translated into a constructor expression
	 * in the query.
	 * 
	 * @return
	 */
	Collection<CustomerDto> findAllDtoedBy();

	/**
	 * 动态传入投影类型(interface或DTO).
	 * 
	 * @param firstname
	 * @param projection
	 * @return
	 */
	<T> Collection<T> findByFirstname(String firstname, Class<T> projection);

	/**
	 * 单个记录的投影.
	 * 
	 * @param id
	 * @return
	 */
	CustomerProjection findProjectedById(Long id);

	/**
	 * 动态投影单个记录.
	 * 
	 * @param id
	 * @param projection
	 * @return
	 */
	<T> T findProjectedById(Long id, Class<T> projection);

	/**
	 * 翻页 & 投影
	 * 
	 * @param pageable
	 * @return
	 */
	Page<CustomerProjection> findPagedProjectedBy(Pageable pageable);

	/**
	 * A DTO projection using a constructor expression in a manually declared query.
	 * 
	 * @param firstname
	 * @return
	 */
	@Query("select new example.springdata.jpa.projections.CustomerDto(c.firstname) from Customer c where c.firstname = ?1")
	Collection<CustomerDto> findDtoWithConstructorExpression(String firstname);

	/**
	 * Optional & 投影
	 * 
	 * @param lastname
	 * @return
	 */
	Optional<CustomerProjection> findOptionalProjectionByLastname(String lastname);
}
