package teliang.orm;

import java.lang.reflect.Field;

public class SqlGenerator {
	private static final Log log = new Log(SqlGenerator.class);

	public static String genInsert(Class<?> clazz) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		String selectClause = RefectionUtils.getSelectClause(fields);

		String insertValueClause = RefectionUtils.getInsertValueClause(fields);

		String sql = String.format("insert into %s ( %s ) VALUES ( %s )", tableName, selectClause, insertValueClause);

		log.info(() -> "genInsert: " + sql);
		return sql;
	}

	public static String genSelectById(Class<?> clazz) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		String selectClause = RefectionUtils.getSelectClause(fields);

		Field[] keyFields = RefectionUtils.getKeyFileds(fields);

		String whereClause = RefectionUtils.getWhereClause(keyFields);
		String sql = String.format("select %s from %s where %s", selectClause, tableName, whereClause);

		log.info(() -> "genSelectById: " + sql);
		return sql;
	}

	public static String genSelect(Class<?> clazz, Object obj) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		String selectClause = RefectionUtils.getSelectClause(fields);

		Field[] nonoNullFields = RefectionUtils.getNonNullFields(obj, fields);

		String whereClause = RefectionUtils.getWhereClause(nonoNullFields);
		String sql = String.format("select %s from %s where %s", selectClause, tableName, whereClause);

		log.info(() -> "genSelect: " + sql);
		return sql;
	}

	public static String genUpdateById(Class<?> clazz) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		Field[] keyFields = RefectionUtils.getKeyFileds(fields);

		Field[] setFields = RefectionUtils.filter(fields, keyFields);

		String setClause = RefectionUtils.getWhereClause(setFields);

		String whereClause = RefectionUtils.getWhereClause(keyFields);

		String sql = String.format("update %s set %s where %s", tableName, setClause, whereClause);

		log.info(() -> "updateById: " + sql);
		return sql;
	}

	public static String genDeleteById(Class<?> clazz) {
		String tableName = RefectionUtils.getTableName(clazz);

		Field[] fields = clazz.getDeclaredFields();

		Field[] keyFields = RefectionUtils.getKeyFileds(fields);

		String whereMarks = RefectionUtils.getWhereClause(keyFields);
		String sql = String.format("delete from %s where %s", tableName, whereMarks);

		log.info(() -> "deleteById: " + sql);
		return sql;
	}

}
