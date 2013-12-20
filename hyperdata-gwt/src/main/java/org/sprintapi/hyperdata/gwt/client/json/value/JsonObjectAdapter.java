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
package org.sprintapi.hyperdata.gwt.client.json.value;

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.bean.BeanAdapter;
import org.sprintapi.hyperdata.gwt.client.bean.BeanPropertyDescriptor;
import org.sprintapi.hyperdata.gwt.client.bean.HyperBeanPropertyAttributes;
import org.sprintapi.hyperdata.gwt.client.json.JsonAdapter;
import org.sprintapi.hyperdata.gwt.client.json.JsonValueAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonObject;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonObjectImpl;

import com.google.gwt.json.client.JSONObject;

public class JsonObjectAdapter<T> implements JsonValueAdapter<T> {

	private final BeanAdapter<T> adapter;
	private final JsonAdapter jsonConverter;
	
	public JsonObjectAdapter(JsonAdapter converter, BeanAdapter<T> adapter) {
		super();
		this.jsonConverter = converter;
		this.adapter = adapter;
	}
	
	@Override
	public T read(JsonValue value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		JsonObject jsonObject = value.isObject();
		if (jsonObject == null) {
			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JSONObject.class);
		}
		
		BeanPropertyDescriptor[] properties = adapter.getProperties();
		if (properties == null) {
			return null;
		}
		
		T object = adapter.createInstance();

		for (BeanPropertyDescriptor property : properties) {
			HyperBeanPropertyAttributes attrs = property.getAttributes();
			if (attrs != null) {
			
			} else if (jsonObject.containsKey(property.getName())) {
				JsonValue propertyJsonValue = jsonObject.get(property.getName());
				
				JsonValueAdapter<?> converter = (JsonValueAdapter<?>) jsonConverter.findConverter(property.getClazz());					
				if (converter == null) {
					throw new AdapterException("Unknown property '" + property.getName() + "' type: " + property.getClazz());
				}
				adapter.setPropertyValue(object, property.getName(), (propertyJsonValue != null) ? converter.read(propertyJsonValue) : null);
			}
		}
		return object;
	}

	@Override
	public JsonValue write(T value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}

		BeanPropertyDescriptor[] properties = adapter.getProperties();
		if (properties == null) {
			return null;
		}

		JsonObjectImpl obj = new JsonObjectImpl();

		for (BeanPropertyDescriptor property : properties) {
			JsonValueAdapter<Object> converter = (JsonValueAdapter<Object>) jsonConverter.findConverter(property.getClazz());					
			if (converter == null) {
				throw new AdapterException("Unknown property '" + property.getName() + "' type: " + property.getClazz());
			}
			if (value != null) {
				Object pValue = adapter.getPropertyValue(value, property.getName());
				obj.put(property.getName(), (pValue != null) ? converter.write(pValue) : null);
			} else {
				obj.put(property.getName(), null);
			}
		}
		return obj;
	}

	@Override
	public Class<T> getJavaClass() {
		return adapter.getBeanClass();
	}	
}
