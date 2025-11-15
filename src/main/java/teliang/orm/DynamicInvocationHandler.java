package teliang.orm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class DynamicInvocationHandler implements InvocationHandler {
	private static final Log log = new Log(DynamicInvocationHandler.class);
	DaoImp imp;
	Connection con;

	public DynamicInvocationHandler(Class<?> t, Connection con) {
		imp = new DaoImp(t, con);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info(() -> "invoke: " + method);

		return method.invoke(imp, args);
	}
}
