package teliang.orm;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentTest {
	private static final Log log = new Log(ContentTest.class);
	static Content content;
	PojoDao dao;

	@BeforeAll
	static void createTable() {
		DbConfigure dbConfigure = new DbConfigure();
		dbConfigure.url = "jdbc:postgresql://192.168.2.2:5432/teliang";
		dbConfigure.user = "teliang";
		dbConfigure.password = "todo";
		content = new Content(dbConfigure);
	}

	@Test
	public void getDao() {
		PojoDao dao = content.getDao(PojoDao.class);
		assertNotNull(dao);
	}

	@Test
	public void testToString() {
		PojoDao dao = content.getDao(PojoDao.class);
		assertNotNull(dao.toString());
	}
}
