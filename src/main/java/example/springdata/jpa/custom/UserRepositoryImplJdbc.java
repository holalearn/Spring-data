package example.springdata.jpa.custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

/**
 * 自定义repository的实现代码. 本实例使用JDBC. 更基本的自定义可以查看{@link UserRepositoryImpl}.
 * 如果你需要处理{@link javax.sql.DataSource}或
 * {@link org.springframework.jdbc.core.simple.SimpleJdbcTemplate}，你需要手动将其声明为Spring bean:
 * 
 * <jpa:repository base-package="com.acme.repository" />
 * <bean id="userRepositoryImpl" class="...UserRepositoryJdbcImpl">
 * 	<property name="dataSource" ref="dataSource" />
 * </bean>
 * 
 * 使用{@code userRepositoryImpl}，因为自定义repository的实现类以{@code Impl}为后缀
 * 
 */
@Profile("jdbc")
@Component("userRepositoryImpl")
class UserRepositoryImplJdbc extends JdbcDaoSupport implements UserRepositoryCustom {

	private static final String COMPLICATED_SQL = "SELECT * FROM User";

	@Autowired
	public UserRepositoryImplJdbc(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.UserRepositoryCustom#myCustomBatchOperation()
	 */
	public List<User> myCustomBatchOperation() {
		return getJdbcTemplate().query(COMPLICATED_SQL, new UserRowMapper());
	}

	private static class UserRowMapper implements RowMapper<User> {

		/*
		 * (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {

			User user = new User(rs.getLong("id"));
			user.setUsername(rs.getString("username"));
			user.setLastname(rs.getString("lastname"));
			user.setFirstname(rs.getString("firstname"));

			return user;
		}
	}
}
