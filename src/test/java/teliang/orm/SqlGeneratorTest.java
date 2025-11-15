package teliang.orm;

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

	@Test
	public void genSelect1() {
		Pojo t = new Pojo();
		t.id = 10L;

		String sql = SqlGenerator.genSelect(Pojo.class, t);
		assertEquals("select id,name from Pojo where id = ?", sql);
	}

	@Test
	public void genSelect2() {
		Pojo t = new Pojo();
		t.name = "teliang";

		String sql = SqlGenerator.genSelect(Pojo.class, t);
		assertEquals("select id,name from Pojo where name = ?", sql);
	}

	@Test
	public void genSelect3() {
		Pojo t = new Pojo();
		t.id = 10L;
		t.name = "teliang";

		String sql = SqlGenerator.genSelect(Pojo.class, t);
		assertEquals("select id,name from Pojo where id = ? and name = ?", sql);
	}

	@Test
	public void genDeleteById() {
		String sql = SqlGenerator.genDeleteById(Pojo.class);
		assertEquals("delete from Pojo where id = ?", sql);
	}

	@Test
	public void genUpdateById() {
		String sql = SqlGenerator.genUpdateById(Pojo.class);
		assertEquals("update Pojo set name = ? where id = ?", sql);
	}
}
