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
	 * When a projection is used that contains dynamic properties (i.e. SpEL expressions in an {@link Value} annotation),
	 * the normal target entity will be loaded but dynamically projected so that the target can be referred to in the
	 * expression.
	 * 
	 * @return
	 */
	Collection<CustomerSummary> findAllSummarizedBy();

	/**
	 * Projection interfaces can be used with manually declared queries, too. Make sure you alias the projects matching
	 * the projection fields.
	 * 
	 * @return
	 */
	@Query("select c.firstname as firstname, c.lastname as lastname from Customer c")
	Collection<CustomerProjection> findsByProjectedColumns();

	/**
	 * Uses a concrete DTO type to indicate the fields to be returned. This gets translated into a constructor expression
	 * in the query.
	 * 
	 * @return
	 */
	Collection<CustomerDto> findAllDtoedBy();

	/**
	 * Passes in the projection type dynamically (either interface or DTO).
	 * 
	 * @param firstname
	 * @param projection
	 * @return
	 */
	<T> Collection<T> findByFirstname(String firstname, Class<T> projection);

	/**
	 * Projection for a single entity.
	 * 
	 * @param id
	 * @return
	 */
	CustomerProjection findProjectedById(Long id);

	/**
	 * Dynamic projection for a single entity.
	 * 
	 * @param id
	 * @param projection
	 * @return
	 */
	<T> T findProjectedById(Long id, Class<T> projection);

	/**
	 * Projections used with pagination.
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
	 * A projection wrapped into an {@link Optional}.
	 * 
	 * @param lastname
	 * @return
	 */
	Optional<CustomerProjection> findOptionalProjectionByLastname(String lastname);
}
