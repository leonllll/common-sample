package com.leonlu.code.sample.common.reflect;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadJarAtRuntime {
	private static void callReflectedMethod(String localJar, String className, String methodName,
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
