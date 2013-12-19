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
package org.sprintapi.gwt.converter.client.json.impl;

import org.sprintapi.gwt.converter.client.json.JsonWriter;
import org.sprintapi.gwt.converter.client.json.lang.JsonArray;
import org.sprintapi.gwt.converter.client.json.lang.JsonBoolean;
import org.sprintapi.gwt.converter.client.json.lang.JsonNumber;
import org.sprintapi.gwt.converter.client.json.lang.JsonObject;
import org.sprintapi.gwt.converter.client.json.lang.JsonString;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

public class JsonWriterImpl implements JsonWriter {

	@Override
	public String write(JsonValue value) {
		if (value == null) {
			
		} else if (value.isArray() != null) {
			return writeArray(value.isArray());
			
		} else if (value.isBoolean() != null) {
			return writeBool(value.isBoolean());
			
		} else if (value.isNumber() != null) {
			return writeNumber(value.isNumber());
			
		} else if (value.isObject() != null) {
			return writeObject(value.isObject());
			
		} else if (value.isString() != null) {
			return writeString(value.isString());
		}
		
		return "null";
	}
	
	protected String writeBool(JsonBoolean bool) {
		return ((bool != null) && bool.booleanValue()) ? "true" : "false";
	}
	
	protected String writeNumber(JsonNumber number) {
		return (number != null && number.doubleValue() != null) ? number.doubleValue().toString() : "null";		
	}
	
	protected String writeString(JsonString string) {
		return ((string != null) && (string.stringValue() != null)) ? "\"" + string.stringValue() + "\"" : "null";
	}
	
	protected String writeArray(JsonArray array) {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (int i=0; i < array.length(); i++) {
			if (i > 0) {
				builder.append(",");
			}
			builder.append(write(array.get(i)));
		}
		builder.append(']');
		return builder.toString();		
	}
	
	protected String writeObject(JsonObject object) {
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		
		boolean f = true;
		for (String name : object.names()) {
			String value = write(object.get(name));
			if ((value != null) && (!value.equals("null"))) {
				if (!f) {
					builder.append(",");
				} else {
					f = false;
				}
				builder.append('"');
				builder.append(name);
				builder.append('"');
				builder.append(':');
				builder.append(value);
			}
		}

		builder.append('}');
		return builder.toString();		
	}
}
