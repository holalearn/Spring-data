package example.springdata.jpa.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 实现自定义repository {@link UserRepositoryCustom}声明的功能. 
 * 
 * EntityManager em = ... // 获取 EntityManager
 * 
 * UserRepositoryCustom custom = new UserRepositoryImpl();
 * custom.setEntityManager(em);
 * 
 * // Factory为给定的repository接口创建代理实例.
 * RepositoryFactorySupport factory = new JpaRepositoryFactory(em);
 * // 通过自定义实现创建代理类
 * UserRepository repository = factory.getRepository(UserRepository.class, custom);
 * 
 * 使用Spring的namespace，实现类只需放在扫描路径上，后缀为{@code Impl}
 * <jpa:repositories base-package="com.acme.repository" />
 * 
 * 如果需要手动配置自定义接口，查看{@link UserRepositoryImplJdbc}示例
 * 
 */
class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext private EntityManager em;

	/**
	 * 配置entity manager
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
