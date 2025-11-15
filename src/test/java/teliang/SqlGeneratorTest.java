package teliang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SqlGeneratorTest {
	@Test
	public void genInsert() {
		String sql = SqlGenerator.genInsert(Pojo.class);
		assertEquals("insert into Pojo ( id,name ) VALUES ( ?,? )", sql);
	}

	@Test
	public void genSelectById() {
		String sql = SqlGenerator.genSelectById(Pojo.class);
		assertEquals("select id,name from Pojo where id = ?", sql);
	}
}
