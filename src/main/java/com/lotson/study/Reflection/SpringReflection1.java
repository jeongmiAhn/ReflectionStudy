package com.lotson.study.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SpringReflection1 {
	
	public static Set<Class<?>> beanList = new HashSet<Class<?>>(0);
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		beanList.add(UserController.class);
		beanList.add(ManagerService.class);
		beanList.add(DummyComponent.class);
		
		String request = "hi!";
		
		// beanList의 클래스중에 Controller 인터페이스를 구현한 클래스를 찾아서 handleRequest 메소드를 실행하기 
		// output) -> hi! handleRequest
		
		Iterator iter = beanList.iterator();
		
		while(iter.hasNext()) {
			Class<?> cls = (Class<?>) iter.next();
			
			Class<?>[] interfaces = cls.getInterfaces();
			
			for(int i=0; i<interfaces.length; i++) {
				if (interfaces[i] == Controller.class) {
					Constructor<?> cst = cls.getDeclaredConstructor();
					Object obj = cst.newInstance();
					
					Method method = cls.getMethod("handleRequest", String.class);
					method.invoke(obj, request);
				}
			}
		}
	}
	
	static class UserController implements Controller {

		@Override
		public void handleRequest(String request) {
			System.out.println(request + " handleRequest");
		}
	}
	
	static class ManagerService implements Service {

		@Override
		public void init() {
			System.out.println("init");
		}

		@Override
		public void service() {
			System.out.println("service");
		}

		@Override
		public void destroy() {
			System.out.println("destroy");
		}
		
	}
	
	static class DummyComponent {
		
		public void dummyMethod() {
			System.out.println();
		}
	}

//------------------ interface
	
	public static interface Controller {
		void handleRequest(String request);
	}
	
	public static interface Service {
		void init();
		void service();
		void destroy();
	}
}
