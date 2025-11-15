package teliang;

/**
 * CREATE TABLE IF NOT EXISTS pojo(id BIGINT,name char(64));
 */
public class Pojo {
	@PrimaryKey
	Long id;
	String name;

	@Override
	public String toString() {
		return "Pojo [id=" + id + ", name=" + name + "]";
	}

}
