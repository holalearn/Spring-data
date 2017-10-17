package example.springdata.jpa.custom;

import java.util.List;

interface UserRepositoryCustom {

	/**
	 * 自定义repository操作.
	 * 
	 * @return
	 */
	List<User> myCustomBatchOperation();
}
