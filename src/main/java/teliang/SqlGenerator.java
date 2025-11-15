package teliang;

import java.lang.reflect.Field;

public class SqlGenerator {
	public static String genInsert(Class<?> clazz) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		String allColumns = RefectionUtils.getAllColumns(fields);

		String allColumnMarks = RefectionUtils.getAllColumnMarks(fields);

		String sql = String.format("insert into %s ( %s ) VALUES ( %s )", tableName, allColumns, allColumnMarks);

		System.out.print("genInsert: ");
		System.out.println(sql);
		return sql;
	}

	public static String genSelectById(Class<?> clazz) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		String allColumns = RefectionUtils.getAllColumns(fields);

		Field[]keyFields = RefectionUtils.getKeyFileds(fields);

		String whereMarks = RefectionUtils.getWhereMarks(keyFields);
		String sql = String.format("select %s from %s where %s", allColumns, tableName, whereMarks);

		System.out.print("genSelectById: ");
		System.out.println(sql);
		return sql;
	}

}
