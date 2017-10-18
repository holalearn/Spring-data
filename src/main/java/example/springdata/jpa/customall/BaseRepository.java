package example.springdata.jpa.customall;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 该repository接口声明了一个自定义方法供所有repositorie使用.
 */
@NoRepositoryBean
interface BaseRepository<T> extends CrudRepository<T, Long> {

	long customMethod();
}
