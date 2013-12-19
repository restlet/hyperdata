/*
 *  Copyright 2012 sprintapi.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sprintapi.gwt.converter.client;

import org.sprintapi.gwt.converter.client.array.ArrayAdapter;
import org.sprintapi.gwt.converter.client.bean.BeanAdapter;



/**
 * Specifies a converter that can convert Java object from and to string.
 * 
 * @author filip
 *
 */
public interface Converter {


	/**
	 * Read an object of the given class from the given string and returns it.
	 * 
	 * @param clazz - the type of object to return. 
	 * @param string - the string representing given object
	 * @return the converted object
	 * @throws ConverterException - in case of invalid string
	 * @throws IllegalArgumentException - in case of <code>null</code> as string
	 *  
	 */
	<T> T read(Class<T> clazz, String string) throws ConverterException;
	
	/**
	 * Write an given object to the string.
	 * 
	 * @param value - the object to write.
	 * @return the string representing given object
	 * @throws ConverterException 
	 * @throws IllegalArgumentException - in case of <code>null</code> as value
	 */
	<T> String write(T value) throws ConverterException;
	
	/**
	 * Register a <class>BeanAdapter</class>.
	 * 
	 * @param reader - the adapter to register
	 */
	<T> void register(BeanAdapter<T> adapter);
	
	/**
	 * Register a <class>ArrayAdapter</class>.
	 * 
	 * @param reader - the adapter to register
	 */
	<T> void register(ArrayAdapter adapter);
	
	/**
	 * Return media type (e.g. application/json)
	 * 
	 * @return the media type
	 */
	String mediaType();
}
