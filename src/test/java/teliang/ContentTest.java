package teliang;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ContentTest {
	@Test
	public void getDao() {
		DbConfigure dbConfigure = new DbConfigure();
		dbConfigure.url = "jdbc:postgresql://192.168.2.2:5432/teliang";
		dbConfigure.user = "teliang";
		dbConfigure.password = "todo";
		Content content = new Content(dbConfigure);
		PojoDao dao = content.getDao(PojoDao.class);
		assertNotNull(dao);
	}
}
