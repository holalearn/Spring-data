package example.springdata.jpa.customall;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/** 
 * 定义好自定义的方法后，需要为方法提供实现：
 * 1. 创建ExtendedJpaRepository类，继承SimpleJpaRepository类，使其拥有Jpa Repository的基本方法
 * 2. 实现自定义接口BaseRepository中方法
 */
class ExtendedJpaRepository<T> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

	/**
	 * 构造函数：根据{@link JpaEntityInformation} and {@link EntityManager}创建一个新的{@link ExtendedJpaRepository}
	 */
	public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	/* 
	 * 实现自定义方法：example.springdata.jpa.customall.BaseRepository#customMethod()
	 */
	@Override
	public long customMethod() {
		return 0;
	}
}
