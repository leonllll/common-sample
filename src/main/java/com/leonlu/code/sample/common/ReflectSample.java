package com.leonlu.code.sample.common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflectSample {
	/**
	 * load a jar at runtime, and call the specific method using java reflect technique
	 * @param localJar
	 * @param className
	 * @param methodName
	 * @param methodArgsClasses
	 * @param methodArgs
	 * @throws Exception
	 */
	public static void invokeMethodAtRuntime(String localJar, String className, String methodName,
			Class[] methodArgsClasses, Object[] methodArgs) 
			throws Exception{
		URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
	    Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
	    addUrl.setAccessible(true);
	    URL url = new File(localJar).toURI().toURL();
	    addUrl.invoke(classLoader, url);
	    
	    Class<?> clazz = classLoader.loadClass(className);
	    Object instance = clazz.newInstance ();
	    Method method = clazz.getMethod(methodName, methodArgsClasses); 
	    method.invoke(instance, methodArgs);
	}
}
