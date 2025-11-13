package teliang;

import java.util.List;

public interface Dao<T> {
	int insert(T obj);

	int update(T obj);

	int delete(T obj);

	T selectById(T obj);

	List<T> select(T obj);
}
