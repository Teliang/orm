package teliang.orm;

public class Pojo {
	@PrimaryKey
	public Long id;
	public String name;

	@Override
	public String toString() {
		return "Pojo [id=" + id + ", name=" + name + "]";
	}

}
