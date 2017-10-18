package example.springdata.jpa.customall;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 用ExtendedJpaRepository代替SimpleJpaRepository作为Repository接口的实现。这样我们就能够达到为所有Repository添加自定义方法的目的。
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
class CustomRepositoryConfig {}
