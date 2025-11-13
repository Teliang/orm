package teliang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Arrays;

import teliang.DaoImp;

public class DynamicInvocationHandler implements InvocationHandler {

	DaoImp imp;
	Connection con;

	public DynamicInvocationHandler(Class t, Connection con) {
		imp = new DaoImp(t, con);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.print("invoke: ");

		System.out.println(method);

		return method.invoke(imp, args);
	}
}
