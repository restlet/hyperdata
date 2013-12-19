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

import org.sprintapi.hyperdata.gwt.client.Converter;
import org.sprintapi.hyperdata.gwt.client.ConverterException;
import org.sprintapi.hyperdata.gwt.client.array.ArrayAdapter;
import org.sprintapi.hyperdata.gwt.client.array.impl.BuiltinArrayMapping;
import org.sprintapi.hyperdata.gwt.client.bean.BeanAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonArrayConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonBooleanConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonDateConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonDoubleConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonEnumConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonFloatConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonIntegerConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonLongConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonObjectConverter;
import org.sprintapi.hyperdata.gwt.client.json.value.JsonStringConverter;

public class JsonConverter implements Converter {
	
	private final Map<Class<?>, JsonValueConverter<?>> converters;
	private final Map<Class<?>, BeanAdapter<?>> beanAdapters;
	private final Map<Class<?>, ArrayAdapter> arrayAdapters;
	
	private final JsonReader reader;
	private final JsonWriter writer;
	
	public JsonConverter(JsonReader reader, JsonWriter writer) {
		super();
		this.reader = reader;
		this.writer = writer;
		
		this.converters = new HashMap<Class<?>, JsonValueConverter<?>>();
		this.beanAdapters = new HashMap<Class<?>, BeanAdapter<?>>();
		this.arrayAdapters = new HashMap<Class<?>, ArrayAdapter>();
		
		register(new JsonStringConverter());
		register(new JsonIntegerConverter());
		register(new JsonLongConverter());
		register(new JsonFloatConverter());
		register(new JsonDoubleConverter());
		register(new JsonBooleanConverter());
		register(new JsonDateConverter());
		
		register(BuiltinArrayMapping.STRING_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.INTEGER_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.LONG_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.FLOAT_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.DOUBLE_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.BOOLEAN_ARRAY_ADAPTER);
		register(BuiltinArrayMapping.DATE_ARRAY_ADAPTER);
	}
	
	@Override
	public <T> T read(Class<T> clazz, String jsonString) throws ConverterException {
		
		if (jsonString == null) {
			throw new IllegalArgumentException("The 'jsonString' parameter cannot be a null.");
		}
		
		JsonValueConverter<T> converter = findConverter(clazz);

		if (converter == null) {
			throw new ConverterException("Unknown class: " + clazz);
		}
		
		JsonValue jsonValue = reader.read(jsonString);

		if (jsonValue == null) {
			return null;
		}
		return converter.read(jsonValue);			
	}

	@Override
	public <T> String write(T value) throws ConverterException {

		if (value == null) {
			throw new IllegalArgumentException("The 'value' parameter cannot be a null.");
		}

		@SuppressWarnings("unchecked")
		JsonValueConverter<T> converter = (JsonValueConverter<T>) findConverter(value.getClass());
		
		if (converter == null) {
			throw new ConverterException("Unknown object: " + value.getClass());
		}
		
		return writer.write(converter.write(value));			
	}

	@Override
	public <T> void register(BeanAdapter<T> adapter) {
		beanAdapters.put(adapter.getBeanClass(), adapter);
	}
	
	public void setDateTimeFormat(String dateTimeFormat) {
		register(new JsonDateConverter(dateTimeFormat));
	}

	protected void register(JsonValueConverter<?> converter) {
		converters.put(converter.getJavaClass(), converter);
	}	
	
	@SuppressWarnings("unchecked")
	public <T> JsonValueConverter<T> findConverter(Class<T> clazz) throws ConverterException {
		if (converters.containsKey(clazz)) {
			return (JsonValueConverter<T>) converters.get(clazz);
		}
		if (beanAdapters.containsKey(clazz)) {
			return new JsonObjectConverter<T>(this, (BeanAdapter<T>) beanAdapters.get(clazz));
		}
		if (clazz.isEnum()) {
			return new JsonEnumConverter(clazz);
		}
		if (clazz.isArray() && arrayAdapters.containsKey(clazz.getComponentType())) {
			ArrayAdapter adapter = arrayAdapters.get(clazz.getComponentType());
			JsonValueConverter<Object> c = (JsonValueConverter<Object>) findConverter(clazz.getComponentType());
			if (c == null) {
				throw new ConverterException("Unknown class: " + clazz);
			}
			return new JsonArrayConverter<T>(c, adapter); 
		}
		return null;
	}

	@Override
	public <T> void register(ArrayAdapter adapter) {
		if (!arrayAdapters.containsKey(adapter.getArrayClass(0)) || (arrayAdapters.get(adapter.getArrayClass(0)).maxDimension() <= adapter.maxDimension())) {
			arrayAdapters.put(adapter.getArrayClass(0), adapter);			
		}
	}

	@Override
	public String mediaType() {
		return "application/json";
	}
}
