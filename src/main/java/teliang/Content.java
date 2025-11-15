package teliang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import teliang.proxy.DynamicInvocationHandler;

public class Content {
	private static final Log log = new Log(Content.class);
	private DbConfigure dbConfigure;

	public Content(DbConfigure dbConfigure) {
		this.dbConfigure = dbConfigure;

	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(dbConfigure.url, dbConfigure.user, dbConfigure.password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<T> c) {
		ParameterizedType genericInterfaces = (ParameterizedType) c.getGenericInterfaces()[0];
		log.info(() -> "getDao: " + genericInterfaces.getActualTypeArguments()[0]);

		Object proxyInstance = Proxy.newProxyInstance(Content.class.getClassLoader(), new Class[] { c },
				new DynamicInvocationHandler((Class<?>) genericInterfaces.getActualTypeArguments()[0],
						getConnection()));
		return (T) proxyInstance;
	}
}
