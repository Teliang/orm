package teliang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RefectionUtils {

	public static Field[] getNonNullFields(Object obj, Field[] fields) {
		List<Field> nonNullFields = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			Class<?> type = field.getType();
			if (type.isPrimitive()) {
				throw new IllegalArgumentException("Unexpected value: " + type);
			} else {
				try {
					Object value = field.get(obj);
					if (value == null) {
						continue;
					}
					nonNullFields.add(field);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new ORMException(e);
				}
			}
		}
		return nonNullFields.toArray(new Field[0]);
	}

	public static void setValueToObject(Object ret, Field[] fields, ResultSet resultSet) throws SQLException {
		try {
			for (Field field : fields) {

				Class<?> type = field.getType();
				if (type.isPrimitive()) {
					throw new IllegalArgumentException("Unexpected value: " + type);
				} else {
					String name = field.getName();
					Object value = resultSet.getObject(name);
					field.set(ret, value);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
			throw new ORMException(e);
		}
	}

	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new ORMException(e);
		}
	}

	public static void setStatementValue(PreparedStatement st, Object obj, Field[] fields) throws SQLException {
		int begin = 0;
		setStatementValueWithIndex(st, obj, fields, begin);
	}

	public static void setStatementValueWithIndex(PreparedStatement st, Object obj, Field[] fields, int begin)
			throws SQLException {
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			Class<?> type = field.getType();
			if (type.isPrimitive()) {
				throw new IllegalArgumentException("Unexpected value: " + type);
			} else {
				try {
					Object value = field.get(obj);
					st.setObject(begin + i + 1, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new ORMException(e);
				}
			}
		}
	}

	public static String getTableName(Class<?> clazz) {
		String fullName = clazz.getName();
		String tableName = fullName.substring(fullName.lastIndexOf('.') + 1);
		return tableName;
	}

	public static String getSelectClause(Field[] fields) {
		StringBuilder sqlBuilder = new StringBuilder();
		for (int i = 0; i < fields.length; i++) {
			sqlBuilder.append(fields[i].getName());

			if (i != fields.length - 1) {
				sqlBuilder.append(",");
			}
		}
		return sqlBuilder.toString();
	}

	public static String getInsertValueClause(Field[] fields) {

		StringBuilder sqlBuilder = new StringBuilder();
		for (int i = 0; i < fields.length; i++) {
			sqlBuilder.append("?");

			if (i != fields.length - 1) {
				sqlBuilder.append(",");
			}
		}
		return sqlBuilder.toString();
	}

	public static String getWhereClause(Field[] keyFields) {
		StringBuilder sqlBuilder = new StringBuilder();
		for (int i = 0; i < keyFields.length; i++) {
			sqlBuilder.append(keyFields[i].getName());
			sqlBuilder.append(" = ");
			sqlBuilder.append("?");
			if (i != keyFields.length - 1) {
				sqlBuilder.append(" and ");
			}
		}
		return sqlBuilder.toString();
	}

	public static Field[] getKeyFileds(Field[] fields) {
		List<Field> keyFields = Stream.of(fields).filter(field -> {
			PrimaryKey annotation = field.getAnnotation(PrimaryKey.class);
			return annotation != null;
		}).toList();
		return keyFields.toArray(new Field[0]);
	}

	public static Field[] filter(Field[] fields, Field[] keyFields) {
		Field[] setFields = Stream.of(fields).filter(e -> {
			for (var field : keyFields) {
				if (field.equals(e))
					return false;
			}
			return true;
		}).toList().toArray(new Field[0]);
		return setFields;
	}
}
