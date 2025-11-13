package teliang;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DaoImp implements Dao<Object> {

	Class clazz;

	Connection con;

	public DaoImp(Class c, Connection con) {
		this.clazz = c;
		this.con = con;
	}

	@Override
	public int insert(Object obj) {
		try {
			String sql = SqlGenerator.genInsert(clazz);
			PreparedStatement st = con.prepareStatement(sql);
			Field[] fields = clazz.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];

				Class<?> type = field.getType();
				if (type.isPrimitive()) {
					throw new IllegalArgumentException("Unexpected value: " + type);
				} else {
					try {
						Object value = field.get(obj);
						st.setObject(i + 1, value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			int count = st.executeUpdate();
			System.out.print("insert count: ");
			System.out.println(count);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object selectById(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> select(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
