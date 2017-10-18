package example.springdata.jpa.customall;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 通过JavaConfig的简单配置来启动Spring Data JPA. 注意如何为Spring Data JPA配置自定义repository基类
 * 通过配置，所有的repository接口都会使用配置类作为repository基类.
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
class CustomRepositoryConfig {}
