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

import java.util.LinkedHashMap;
import java.util.Map;

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.json.JsonAdapterContext;
import org.sprintapi.hyperdata.gwt.client.json.JsonValueAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonObject;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonObjectImpl;

import com.google.gwt.json.client.JSONObject;

public class JsonMapAdapter implements JsonValueAdapter<Map> {

	private final JsonAdapterContext context;
	
	public JsonMapAdapter(JsonAdapterContext context) {
		super();
		this.context = context;
	}
	
	@Override
	public Map read(JsonValue value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		JsonObject jsonObject = value.isObject();
		if (jsonObject == null) {
			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JSONObject.class);
		}
		
		Map<Object, Object> object = new LinkedHashMap<Object, Object>();

		for (String property : jsonObject.names()) {
			JsonValue propertyJsonValue = jsonObject.get(property);
			
			if (propertyJsonValue != null) {
				JsonValueAdapter<?> converter = (JsonValueAdapter<?>) context.findAdapter(propertyJsonValue.getClass());					
				if (converter == null) {
					throw new AdapterException("Cannot find an adapter for " + propertyJsonValue.getClass() + ".");
				}
						
				object.put(property, (propertyJsonValue != null) ? converter.read(propertyJsonValue) : null);
			}
		}
		return object;
	}

	@Override
	public JsonValue write(Map value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}

		JsonObjectImpl obj = new JsonObjectImpl();

		for (Object property : value.keySet()) {

			Object propertyValue = value.get(property);
			if (propertyValue != null) {
				JsonValueAdapter<Object> converter = (JsonValueAdapter<Object>) context.findAdapter(propertyValue.getClass());					
				if (converter == null) {
					throw new AdapterException("Cannot find an adapter for " + propertyValue.getClass() + ".");
				}				
				obj.put(property.toString(), converter.write(propertyValue));				
			}			
		}
		return obj;
	}

	@Override
	public Class<Map> getJavaClass() {
		return Map.class;
	}	 
}
