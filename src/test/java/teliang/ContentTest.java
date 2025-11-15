package teliang;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ContentTest {
	@Test
	public void getDao() {
		Content content = new Content();
		PojoDao dao = content.getDao(PojoDao.class);
		assertNotNull(dao);
	}
}
