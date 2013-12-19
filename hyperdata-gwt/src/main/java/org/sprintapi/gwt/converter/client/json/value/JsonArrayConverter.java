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
package org.sprintapi.gwt.converter.client.json.value;

import java.util.Stack;

import org.sprintapi.gwt.converter.client.ConverterException;
import org.sprintapi.gwt.converter.client.array.ArrayAdapter;
import org.sprintapi.gwt.converter.client.json.JsonValueConverter;
import org.sprintapi.gwt.converter.client.json.lang.JsonArray;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

public class JsonArrayConverter<T> implements JsonValueConverter<T> {

	private final JsonValueConverter valueConverter;
	private final ArrayAdapter adapter;
	
	public JsonArrayConverter(JsonValueConverter valueConverter, ArrayAdapter adapter) {
		super();
		this.adapter = adapter;
		this.valueConverter = valueConverter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(JsonValue jsonValue) throws ConverterException {
		if (jsonValue == null) {
			throw new IllegalArgumentException("The 'value' argument cannot be a null.");
		}
		JsonArray json = jsonValue.isArray();
		if (json == null) {
			throw new IllegalArgumentException("The value '" + json + "' argument is not " + JsonArray.class);
		}
		return (T)doRead(adapter.createInstance(json.length(), adapter.maxDimension()), json, new Stack<Integer>(), adapter.maxDimension() - 1);
	}	
	
	protected Object doRead(Object array, JsonArray json, Stack<Integer> index, int dimension) throws ConverterException {
		
		for (int i=0; i < json.length(); i++) {
			JsonValue v = json.get(i);
			if ((v.isArray() == null) && (dimension == 0)) {
				index.push(i);
				adapter.set(array, dimension, i, valueConverter.read(v));
				index.pop();
				
			} else if ((v.isArray() != null) && (dimension > 0)) {
				index.push(i);
				Object na = adapter.createInstance(v.isArray().length(), dimension);
				adapter.set(array, dimension, i, na);
				doRead(na, v.isArray(), index, dimension-1);
				index.pop();
			}
		}		
		return array;
	}

	@Override
	public JsonValue write(T value) throws ConverterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
//		JsonObject jsonObject = value.isObject();
//		if (jsonObject == null) {
//			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JSONObject.class);
//		}
//
//		return;
		return null;
	}

	@Override
	public Class<T> getJavaClass() {
		return (Class<T>) adapter.getArrayClass(adapter.maxDimension());
	}
}
