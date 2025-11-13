package teliang;

import java.lang.reflect.Field;

public class SqlGenerator {
	public static String genInsert(Class<?> clazz) {

		String name = clazz.getName();
		String tableName = name.substring(name.lastIndexOf('.') + 1);

		Field[] fields = clazz.getDeclaredFields();

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("insert into ");
		sqlBuilder.append(tableName);
		sqlBuilder.append("(");

		for (int i = 0; i < fields.length; i++) {
			sqlBuilder.append(fields[i].getName());

			if (i != fields.length - 1) {
				sqlBuilder.append(",");
			}
		}

		sqlBuilder.append(")");
		sqlBuilder.append(" VALUES ");
		sqlBuilder.append("(");

		for (int i = 0; i < fields.length; i++) {
			sqlBuilder.append("?");

			if (i != fields.length - 1) {
				sqlBuilder.append(",");
			}
		}
		sqlBuilder.append(")");
		String sql = sqlBuilder.toString();

		System.out.print("genInsert: ");
		System.out.println(sql);
		return sql;
	}
}
