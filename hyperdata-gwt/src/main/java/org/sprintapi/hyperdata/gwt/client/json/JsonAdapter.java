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
package org.sprintapi.hyperdata.gwt.client.json;

import java.util.HashMap;
import java.util.Map;

import org.sprintapi.hyperdata.gwt.client.Adapter;
import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.array.ArrayAdapter;
import org.sprintapi.hyperdata.gwt.client.array.impl.BuiltinArrayMapping;
import org.sprintapi.hyperdata.gwt.client.bean.BeanAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonArrayAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonBooleanAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonDateAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonDoubleAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonEnumAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonFloatAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonIntegerAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonLongAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonObjectAdapter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonStringAdapter;

public class JsonAdapter implements Adapter {
	
	private final Map<Class<?>, JsonValueAdapter<?>> converters;
	private final Map<Class<?>, BeanAdapter<?>> beanAdapters;
	private final Map<Class<?>, ArrayAdapter<?>> arrayAdapters;
	
	private final JsonReader reader;
	private final JsonWriter writer;
	
	public JsonAdapter(JsonReader reader, JsonWriter writer) {
		super();
		this.reader = reader;
		this.writer = writer;
		
		this.converters = new HashMap<Class<?>, JsonValueAdapter<?>>();
		this.beanAdapters = new HashMap<Class<?>, BeanAdapter<?>>();
		this.arrayAdapters = new HashMap<Class<?>, ArrayAdapter<?>>();
		
		register(new JsonStringAdapter());
		register(new JsonIntegerAdapter());
		register(new JsonLongAdapter());
		register(new JsonFloatAdapter());
		register(new JsonDoubleAdapter());
		register(new JsonBooleanAdapter());
		register(new JsonDateAdapter());
		
		register(BuiltinArrayMapping.STRING_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.INTEGER_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.LONG_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.FLOAT_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.DOUBLE_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.BOOLEAN_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.DATE_ARRAY_ADAPTER);
	}
	
	@Override
	public <T> T read(Class<T> clazz, String jsonString) throws AdapterException {
		
		if (jsonString == null) {
			throw new IllegalArgumentException("The 'jsonString' parameter cannot be a null.");
		}
		
		JsonValueAdapter<T> converter = findConverter(clazz);

		if (converter == null) {
			throw new AdapterException("Unknown class: " + clazz);
		}
		
		JsonValue jsonValue = reader.read(jsonString);

		if (jsonValue == null) {
			return null;
		}
		return converter.read(jsonValue);			
	}

	@Override
	public <T> String write(T value) throws AdapterException {

		if (value == null) {
			throw new IllegalArgumentException("The 'value' parameter cannot be a null.");
		}

		@SuppressWarnings("unchecked")
		JsonValueAdapter<T> converter = (JsonValueAdapter<T>) findConverter(value.getClass());
		
		if (converter == null) {
			throw new AdapterException("Unknown object: " + value.getClass());
		}
		
		return writer.write(converter.write(value));			
	}

	@Override
	public <T> void register(BeanAdapter<T> adapter) {
		beanAdapters.put(adapter.getBeanClass(), adapter);
	}
	
	public void setDateTimeFormat(String dateTimeFormat) {
		register(new JsonDateAdapter(dateTimeFormat));
	}

	protected void register(JsonValueAdapter<?> converter) {
		converters.put(converter.getJavaClass(), converter);
	}	
	
	@SuppressWarnings("unchecked")
	public <T> JsonValueAdapter<T> findConverter(Class<T> clazz) throws AdapterException {
		if (converters.containsKey(clazz)) {
			return (JsonValueAdapter<T>) converters.get(clazz);
		}
		if (beanAdapters.containsKey(clazz)) {
			return new JsonObjectAdapter<T>(this, (BeanAdapter<T>) beanAdapters.get(clazz));
		}
		if (clazz.isEnum()) {
			return new JsonEnumAdapter<T>(clazz);
		}
		if (clazz.isArray() && arrayAdapters.containsKey(clazz.getComponentType())) {
			ArrayAdapter<?> adapter = arrayAdapters.get(clazz.getComponentType());
			JsonValueAdapter<Object> c = (JsonValueAdapter<Object>) findConverter(clazz.getComponentType());
			if (c == null) {
				throw new AdapterException("Unknown class: " + clazz);
			}
			return new JsonArrayAdapter<T>(c, adapter); 
		}
		return null;
	}

	@Override
	public <T> void register(ArrayAdapter<T> adapter) {
		if (!arrayAdapters.containsKey(adapter.getArrayClass(0)) || (arrayAdapters.get(adapter.getArrayClass(0)).maxDimension() <= adapter.maxDimension())) {
			arrayAdapters.put(adapter.getArrayClass(0), adapter);			
		}
	}

	@Override
	public String mediaType() {
		return "application/json";
	}
}
