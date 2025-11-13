package teliang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SqlGeneratorTest {
	@Test
	public void generate() {
		String sql = SqlGenerator.genInsert(Pojo.class);
		System.out.println(sql);
		assertEquals("insert into Pojo('id','name') VALUES (?,?)", sql);
	}
}
