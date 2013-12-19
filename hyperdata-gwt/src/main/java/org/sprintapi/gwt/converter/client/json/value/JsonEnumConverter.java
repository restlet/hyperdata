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

import org.sprintapi.gwt.converter.client.ConverterException;
import org.sprintapi.gwt.converter.client.json.JsonValueConverter;
import org.sprintapi.gwt.converter.client.json.lang.JsonString;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;
import org.sprintapi.gwt.converter.client.json.lang.impl.JsonStringImpl;

public class JsonEnumConverter<T> implements JsonValueConverter<T> {

	private final Class clazz;
	
	public JsonEnumConverter(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	@Override
	public T read(JsonValue value) throws ConverterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		JsonString jsonString = value.isString();
		if (jsonString == null) {
			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JsonString.class);
		}
		return (T) Enum.valueOf(clazz, jsonString.stringValue());
	}

	@Override
	public JsonValue write(T value) {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		return new JsonStringImpl(((Enum)value).name());
	}

	@Override
	public Class<T> getJavaClass() {
		return clazz;
	}
}
