# orm
A simple Object–relational mapping for Java

## example
1. create a POJO class

```Java
public class Pojo {
	@PrimaryKey
	public Long id;
	public String name;

	@Override
	public String toString() {
		return "Pojo [id=" + id + ", name=" + name + "]";
	}
}

```

2. extends dao interface

```Java
public interface PojoDao extends Dao<Pojo> {

}

```
3. then you can use the CRUD functions

```Java
Content content = new Content(dbConfigure);
PojoDao dao = content.getDao(PojoDao.class);
dao.insert(t);
dao.selectById(t);
...
```

## TODO
- transaction

