package ua.com.myjava.xstream.converter;

import java.util.Date;

import com.thoughtworks.xstream.converters.basic.DateConverter;

public class MyJavaDateConverter extends DateConverter{
	  public boolean canConvert(Class type) {
	        return type.equals(Date.class) || type.equals(java.sql.Timestamp.class);
	    }
}
