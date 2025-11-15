package teliang.orm;

import java.util.List;

public interface Dao<T> {
	int insert(T obj);

	int updateById(T obj);

	int deleteById(T obj);

	T selectById(T obj);

	List<T> select(T obj);
}
