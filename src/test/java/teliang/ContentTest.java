package teliang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ContentTest {
	@Test
	public void getDao() {
		Content content = new Content();
		PojoDao dao = content.getDao(PojoDao.class);
		Pojo t = new Pojo();
		t.id = 10L;
		t.name = "teliang";
		int ret = dao.insert(t);
		assertEquals(1, ret);
	}
}
