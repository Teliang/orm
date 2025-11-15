package teliang;

public class Pojo {
	@PrimaryKey
	Long id;
	String name;

	@Override
	public String toString() {
		return "Pojo [id=" + id + ", name=" + name + "]";
	}

}
