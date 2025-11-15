package teliang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PojoDaoTest {
	static Content content;
	PojoDao dao;

	@BeforeAll
	static void createTable() {
		content = new Content();
		String ddl = "CREATE TABLE IF NOT EXISTS pojo(id BIGINT PRIMARY KEY,name char(64))";
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

	@AfterAll
	static void dropTable() {
		String ddl = "DROP TABLE IF EXISTS pojo";
		executeSql(ddl);
	}

	private static void executeSql(String ddl) {
		Connection connection = content.getConnection();
		try {
			Statement st = connection.createStatement();
			st.execute(ddl);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		System.out.println("execute ddl: " + ddl);
	}
}
