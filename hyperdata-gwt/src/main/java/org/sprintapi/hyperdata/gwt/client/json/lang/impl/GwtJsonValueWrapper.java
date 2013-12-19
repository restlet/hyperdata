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
package org.sprintapi.hyperdata.gwt.client.json.lang.impl;

import org.sprintapi.hyperdata.gwt.client.json.lang.JsonArray;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonBoolean;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonNumber;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonObject;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonString;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;

import com.google.gwt.json.client.JSONValue;

public abstract class GwtJsonValueWrapper implements JsonValue {

	private JSONValue value;
	
	protected GwtJsonValueWrapper(JSONValue value) {
		super();
		this.value = value;
	}
	
	public static JsonValue wrap(JSONValue value) {
		if (value == null) {
			
		} else if (value.isArray() != null) {
			return new GwtJsonArrayWrapper(value.isArray());
			
		} else if (value.isBoolean() != null) {
			return new GwtJsonBooleanWrapper(value.isBoolean());
			
		} else if (value.isNull() != null) {
			return null; 
			
		} else if (value.isNumber() != null) {
			return new GwtJsonNumberWrapper(value.isNumber());
			
		} else if (value.isObject() != null) {
			return new GwtJsonObjectWrapper(value.isObject());
			
		} else if (value.isString() != null) {
			return new GwtJsonStringWrapper(value.isString());
		}
		return null;
	}

	@Override
	public JsonObject isObject() {
		return ((value != null) && (value.isObject() != null)) ? new GwtJsonObjectWrapper(value.isObject()) : null;
	}

	@Override
	public JsonArray isArray() {
		return ((value != null) && (value.isArray() != null)) ? new GwtJsonArrayWrapper(value.isArray()) : null;
	}

	@Override
	public JsonString isString() {
		return ((value != null) && (value.isString() != null)) ? new GwtJsonStringWrapper(value.isString()) : null;
	}

	@Override
	public JsonBoolean isBoolean() {
		return ((value != null) && (value.isBoolean() != null)) ? new GwtJsonBooleanWrapper(value.isBoolean()) : null;
	}

	@Override
	public JsonNumber isNumber() {
		return ((value != null) && (value.isNumber() != null)) ? new GwtJsonNumberWrapper(value.isNumber()) : null;
	}
	
}
