package example.springdata.jpa.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 实现自定义repository {@link UserRepositoryCustom}给予JPA声明的功能. To use
 * 与Spring Data JPA联合使用自定义实现，你需要编程注册:
 * 
 * <pre>
 * EntityManager em = ... // Obtain EntityManager
 * 
 * UserRepositoryCustom custom = new UserRepositoryImpl();
 * custom.setEntityManager(em);
 * 
 * RepositoryFactorySupport factory = new JpaRepositoryFactory(em);
 * UserRepository repository = factory.getRepository(UserRepository.class, custom);
 * </pre>
 * 
 * Using the Spring namespace the implementation will just get picked up due to the classpath scanning for
 * implementations with the {@code Impl} postfix.
 * 
 * <pre>
 * &lt;jpa:repositories base-package=&quot;com.acme.repository&quot; /&gt;
 * </pre>
 * 
 * If you need to manually configure the custom instance see {@link UserRepositoryImplJdbc} for an example.
 * 
 */
class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext private EntityManager em;

	/**
	 * Configure the entity manager to be used.
	 * 
	 * @param em the {@link EntityManager} to set.
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/*
	 * @see example.springdata.jpa.UserRepositoryCustom#myCustomBatchOperation()
	 */
	public List<User> myCustomBatchOperation() {

		CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
		criteriaQuery.select(criteriaQuery.from(User.class));
		return em.createQuery(criteriaQuery).getResultList();
	}
}
