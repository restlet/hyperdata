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
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

public class JsonArrayImpl extends JsonValueImpl implements JsonArray {

	private final JsonValue[] items;
	
	public JsonArrayImpl(int length) {
		super();
		this.items = new JsonValue[length];
	}
	
	@Override
	public int length() {
		return items.length;
	}

	@Override
	public JsonValue get(int index) {
		if ((index < 0) || (index >= length())) {
			throw new IllegalArgumentException("The 'index' argument must be greater then zero and less then " + length() +".");
		}
		return items[index];
	}
	
	public void set(int index, JsonValue value) {
		if ((index < 0) || (index >= length())) {
			throw new IllegalArgumentException("The 'index' argument must be greater then zero and less then " + length() +".");
		}
		items[index] = value;
	}
}
