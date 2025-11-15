package teliang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PojoDaoTest {
	private static final Log log = new Log(PojoDaoTest.class);
	static Content content;
	PojoDao dao;

	@BeforeAll
	static void createTable() {
		DbConfigure dbConfigure = new DbConfigure();
		dbConfigure.url = "jdbc:postgresql://192.168.2.2:5432/teliang";
		dbConfigure.user = "teliang";
		dbConfigure.password = "todo";
		content = new Content(dbConfigure);
		String ddl = "CREATE TABLE IF NOT EXISTS pojo(id BIGINT PRIMARY KEY,name varchar(64))";
		executeSql(ddl);

	}

	@BeforeEach
	void setUp() {
		dao = content.getDao(PojoDao.class);
		String sql = "TRUNCATE TABLE pojo";
		executeSql(sql);
	}

	@Test
	public void insert() {
		Pojo t = new Pojo();
		t.id = 10L;
		t.name = "teliang";
		int ret = dao.insert(t);
		assertEquals(1, ret);

		String countSql = "SELECT COUNT(*) FROM pojo";
		int count = executeCountSql(countSql);
		assertEquals(1, count);
	}

	@Test
	public void selectById() {
		String sql = "insert into Pojo ( id,name ) VALUES ( 10,'teliang' )";
		executeSql(sql);

		Pojo t = new Pojo();
		t.id = 10L;
		Pojo ret = dao.selectById(t);

		assertEquals(10L, ret.id);
		assertEquals("teliang", ret.name.trim());
	}

	@Test
	public void select() {
		String sql = "insert into Pojo ( id,name ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		Pojo t = new Pojo();
		t.name = "teliang";
		List<Pojo> list = dao.select(t);

		assertEquals(3, list.size());
	}

	@Test
	public void select2() {
		String sql = "insert into Pojo ( id,name ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		Pojo t = new Pojo();
		t.id = 2L;
		List<Pojo> list = dao.select(t);

		assertEquals(1, list.size());
		Pojo pojo = list.get(0);
		assertEquals(2L, pojo.id);
		assertEquals("teliang", pojo.name);
	}

	@Test
	public void updateById() {
		String sql = "insert into Pojo ( id,name ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		Pojo t = new Pojo();
		t.id = 1L;
		t.name = "hung";
		int ret = dao.updateById(t);

		assertEquals(1, ret);

		String selectSql = "SELECT name FROM pojo where id = 1";
		String name = executeSelectSql(selectSql);
		assertEquals("hung", name);
	}

	@Test
	public void deleteById() {
		String sql = "insert into Pojo ( id,name ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		Pojo t = new Pojo();
		t.id = 1L;
		int ret = dao.deleteById(t);

		assertEquals(1, ret);

		String countSql = "SELECT COUNT(*) AS rowcount FROM pojo";
		int count = executeCountSql(countSql);
		assertEquals(2, count);
	}

	@AfterAll
	static void dropTable() {
		String ddl = "DROP TABLE IF EXISTS pojo";
		executeSql(ddl);
	}

	private static String executeSelectSql(String sql) {
		log.info(() -> "execute sql: " + sql);
		Connection connection = content.getConnection();
		String ret = "";
		try (Statement st = connection.createStatement(); ResultSet resultSet = st.executeQuery(sql);) {
			if (resultSet.next()) {
				ret = resultSet.getString(1);
			}
			log.info("Select column value: " + ret);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	private static int executeCountSql(String sql) {
		log.info(() -> "execute sql: " + sql);
		Connection connection = content.getConnection();
		int rowCount = 0;
		try (Statement st = connection.createStatement(); ResultSet resultSet = st.executeQuery(sql)) {
			if (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
			log.info("Number of rows: " + rowCount);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return rowCount;
	}

	private static void executeSql(String sql) {
		Connection connection = content.getConnection();
		try (Statement st = connection.createStatement();) {
			st.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		log.info(() -> "execute sql: " + sql);
	}
}
