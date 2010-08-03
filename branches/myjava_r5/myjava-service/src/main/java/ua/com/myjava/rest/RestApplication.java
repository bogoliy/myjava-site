package ua.com.myjava.rest;

import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.core.Application;

public class RestApplication extends Application{
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classesSet = new TreeSet<Class<?>> (); 
		classesSet.add(RestArticleService.class);
        return classesSet;
    }
    
}
