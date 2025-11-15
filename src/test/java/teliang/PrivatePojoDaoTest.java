package teliang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test private pojo
 */
public class PrivatePojoDaoTest {
	private static final Log log = new Log(PrivatePojoDaoTest.class);
	static Content content;
	PrivatePojoDao dao;

	@BeforeAll
	static void createTable() {
		DbConfigure dbConfigure = new DbConfigure();
		dbConfigure.url = "jdbc:postgresql://192.168.2.2:5432/teliang";
		dbConfigure.user = "teliang";
		dbConfigure.password = "todo";
		content = new Content(dbConfigure);
		String ddl = "CREATE TABLE IF NOT EXISTS PrivatePojo(code INT PRIMARY KEY,address varchar(64))";
		executeSql(ddl);

	}

	@BeforeEach
	void setUp() {
		dao = content.getDao(PrivatePojoDao.class);
		String sql = "TRUNCATE TABLE PrivatePojo";
		executeSql(sql);
	}

	@Test
	public void insert() {
		PrivatePojo t = new PrivatePojo();
		t.setCode(10);
		t.setAddress("teliang");
		int ret = dao.insert(t);
		assertEquals(1, ret);

		String countSql = "SELECT COUNT(*) FROM PrivatePojo";
		int count = executeCountSql(countSql);
		assertEquals(1, count);
	}

	@Test
	public void selectById() {
		String sql = "insert into PrivatePojo ( code,address ) VALUES ( 10,'teliang' )";
		executeSql(sql);

		PrivatePojo t = new PrivatePojo();
		t.setCode(10);
		PrivatePojo ret = dao.selectById(t);

		assertEquals(10, ret.getCode());
		assertEquals("teliang", ret.getAddress().trim());
	}

	@Test
	public void selectById_notExist() {
		String sql = "insert into PrivatePojo ( code,address ) VALUES ( 10,'teliang' )";
		executeSql(sql);

		PrivatePojo t = new PrivatePojo();
		t.setCode(100);
		PrivatePojo ret = dao.selectById(t);

		assertNull(ret);
	}

	@Test
	public void select() {
		String sql = "insert into PrivatePojo ( code,address ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		PrivatePojo t = new PrivatePojo();
		t.setAddress("teliang");
		List<PrivatePojo> list = dao.select(t);

		assertEquals(3, list.size());
	}

	@Test
	public void select2() {
		String sql = "insert into PrivatePojo ( code,address ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		PrivatePojo t = new PrivatePojo();
		t.setCode(2);
		List<PrivatePojo> list = dao.select(t);

		assertEquals(1, list.size());
		PrivatePojo privatePojo = list.get(0);
		assertEquals(2, privatePojo.getCode());
		assertEquals("teliang", privatePojo.getAddress());
	}

	@Test
	public void updateById() {
		String sql = "insert into PrivatePojo ( code,address ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		PrivatePojo t = new PrivatePojo();
		t.setCode(1);
		t.setAddress("hung");
		int ret = dao.updateById(t);

		assertEquals(1, ret);

		String selectSql = "SELECT address FROM PrivatePojo where code = 1";
		String name = executeSelectSql(selectSql);
		assertEquals("hung", name);
	}

	@Test
	public void deleteById() {
		String sql = "insert into PrivatePojo ( code,address ) VALUES ( 1,'teliang' ),( 2,'teliang' ),( 3,'teliang' )";
		executeSql(sql);

		PrivatePojo t = new PrivatePojo();
		t.setCode(1);
		int ret = dao.deleteById(t);

		assertEquals(1, ret);

		String countSql = "SELECT COUNT(*) AS rowcount FROM PrivatePojo";
		int count = executeCountSql(countSql);
		assertEquals(2, count);
	}

	@AfterAll
	static void dropTable() {
		String ddl = "DROP TABLE IF EXISTS PrivatePojo";
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
