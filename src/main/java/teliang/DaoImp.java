package teliang;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoImp implements Dao<Object> {

	Class<?> clazz;

	Connection con;

	public DaoImp(Class<?> c, Connection con) {
		this.clazz = c;
		this.con = con;
	}

	@Override
	public int insert(Object obj) {
		try {
			String sql = SqlGenerator.genInsert(clazz);
			PreparedStatement st = con.prepareStatement(sql);
			Field[] fields = clazz.getDeclaredFields();

			RefectionUtils.setStatementValue(st, obj, fields);

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
		Object ret = null;
		try {
			String sql = SqlGenerator.genSelectById(clazz);
			PreparedStatement st = con.prepareStatement(sql);

			Field[] fields = clazz.getDeclaredFields();
			Field[] keyFileds = RefectionUtils.getKeyFileds(fields);

			RefectionUtils.setStatementValue(st, obj, keyFileds);

			ResultSet resultSet = st.executeQuery();

			ret = RefectionUtils.newInstance(clazz);

			if (resultSet.next()) {
				RefectionUtils.setValueToObject(ret, fields, resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.print("selectById result: ");
		System.out.println(ret);
		return ret;
	}

	@Override
	public List<Object> select(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
