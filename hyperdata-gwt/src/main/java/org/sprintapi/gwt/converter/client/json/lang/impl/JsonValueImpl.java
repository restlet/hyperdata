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
package org.sprintapi.gwt.converter.client.json.lang.impl;

import org.sprintapi.gwt.converter.client.json.lang.JsonArray;
import org.sprintapi.gwt.converter.client.json.lang.JsonBoolean;
import org.sprintapi.gwt.converter.client.json.lang.JsonNumber;
import org.sprintapi.gwt.converter.client.json.lang.JsonObject;
import org.sprintapi.gwt.converter.client.json.lang.JsonString;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

public abstract class JsonValueImpl implements JsonValue {

	@Override
	public JsonObject isObject() {
		return (this instanceof JsonObject) ? (JsonObject)this : null;
	}

	@Override
	public JsonArray isArray() {
		return (this instanceof JsonArray) ? (JsonArray)this : null;
	}

	@Override
	public JsonString isString() {
		return (this instanceof JsonString) ? (JsonString)this : null;
	}

	@Override
	public JsonBoolean isBoolean() {
		return (this instanceof JsonBoolean) ? (JsonBoolean)this : null;
	}

	@Override
	public JsonNumber isNumber() {
		return (this instanceof JsonNumber) ? (JsonNumber)this : null;
	}
}
