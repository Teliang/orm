package teliang;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoImp implements Dao<Object> {
	private static final Log log = new Log(DaoImp.class);
	Class<?> clazz;

	Connection con;

	public DaoImp(Class<?> c, Connection con) {
		this.clazz = c;
		this.con = con;
	}

	@Override
	public int insert(Object obj) {
		String sql = SqlGenerator.genInsert(clazz);
		try (PreparedStatement st = con.prepareStatement(sql);) {

			Field[] fields = clazz.getDeclaredFields();

			RefectionUtils.setStatementValue(st, obj, fields);

			int count = st.executeUpdate();

			log.info(() -> "insert count: " + count);
			return count;
		} catch (SQLException e) {
			throw new ORMException(e);
		}
	}

	@Override
	public Object selectById(Object obj) {
		Object ret = null;
		String sql = SqlGenerator.genSelectById(clazz);
		try (PreparedStatement st = con.prepareStatement(sql);) {

			Field[] fields = clazz.getDeclaredFields();
			Field[] keyFileds = RefectionUtils.getKeyFileds(fields);

			RefectionUtils.setStatementValue(st, obj, keyFileds);

			try (ResultSet resultSet = st.executeQuery()) {
				if (resultSet.next()) {
					ret = RefectionUtils.newInstance(clazz);
					RefectionUtils.setValueToObject(ret, fields, resultSet);
				}
			}

		} catch (SQLException e) {
			throw new ORMException(e);
		}

		log.info("selectById result: " + ret);
		return ret;
	}

	@Override
	public List<Object> select(Object obj) {
		List<Object> ret = new ArrayList<>();
		String sql = SqlGenerator.genSelect(clazz, obj);
		try (PreparedStatement st = con.prepareStatement(sql)) {

			Field[] fields = clazz.getDeclaredFields();

			Field[] nonNullFields = RefectionUtils.getNonNullFields(obj, fields);

			RefectionUtils.setStatementValue(st, obj, nonNullFields);
			try (ResultSet resultSet = st.executeQuery();) {
				while (resultSet.next()) {
					Object resultObject = RefectionUtils.newInstance(clazz);
					ret.add(resultObject);
					RefectionUtils.setValueToObject(resultObject, fields, resultSet);
				}
			}
		} catch (SQLException e) {
			throw new ORMException(e);
		}
		log.info(() -> "select result: " + ret);
		return ret;
	}

	@Override
	public int updateById(Object obj) {
		String sql = SqlGenerator.genUpdateById(clazz);
		try (PreparedStatement st = con.prepareStatement(sql);) {

			Field[] fields = clazz.getDeclaredFields();
			Field[] keyFields = RefectionUtils.getKeyFileds(fields);

			Field[] setFields = RefectionUtils.filter(fields, keyFields);

			RefectionUtils.setStatementValue(st, obj, setFields);

			RefectionUtils.setStatementValueWithIndex(st, obj, keyFields, setFields.length);

			int count = st.executeUpdate();

			log.info(() -> "updateById count: " + count);
			return count;
		} catch (SQLException e) {
			throw new ORMException(e);
		}
	}

	@Override
	public int deleteById(Object obj) {
		String sql = SqlGenerator.genDeleteById(clazz);
		try (PreparedStatement st = con.prepareStatement(sql);) {

			Field[] fields = clazz.getDeclaredFields();

			Field[] keyFields = RefectionUtils.getKeyFileds(fields);

			RefectionUtils.setStatementValue(st, obj, keyFields);

			int count = st.executeUpdate();

			log.info(() -> "deleteById count: " + count);
			return count;
		} catch (SQLException e) {
			throw new ORMException(e);
		}
	}

}
