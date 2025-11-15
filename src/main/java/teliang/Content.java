package teliang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import teliang.proxy.DynamicInvocationHandler;

public class Content {

	private Connection con;

	public Content() {
		String url = "jdbc:postgresql://192.168.2.2:5432/teliang";
		String user = "teliang";
		String password = "todo";
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Connection getConnection() {
		return con;
	}

	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<T> c) {
		ParameterizedType genericInterfaces = (ParameterizedType) c.getGenericInterfaces()[0];
		System.out.print("getDao: ");
		System.out.println(genericInterfaces.getActualTypeArguments()[0]);
		Object proxyInstance = Proxy.newProxyInstance(Content.class.getClassLoader(), new Class[] { c },
				new DynamicInvocationHandler((Class<?>) genericInterfaces.getActualTypeArguments()[0], con));
		return (T) proxyInstance;
	}
}
