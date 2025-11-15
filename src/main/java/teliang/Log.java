package teliang;

import java.util.function.Supplier;

public class Log {
	private Class<?> clazz;

	public Log() {
	}

	public Log(Class<?> claz) {
		this.clazz = claz;

	}

	public void info(Supplier<String> supplier) {
		if (clazz != null) {
			System.out.print(clazz.getTypeName());
		}
		System.out.println(supplier.get());
	}

	public void info(String msg) {
		if (clazz != null) {
			System.out.print(clazz.getTypeName());
		}
		System.out.println(msg);
	}

	public void debug(Supplier<String> supplier) {
		if (clazz != null) {
			System.out.print(clazz.getTypeName());
		}
		System.out.println(supplier.get());
	}

	public void error(Supplier<String> supplier) {
		if (clazz != null) {
			System.out.print(clazz.getTypeName());
		}
		System.out.println(supplier.get());
	}
}
