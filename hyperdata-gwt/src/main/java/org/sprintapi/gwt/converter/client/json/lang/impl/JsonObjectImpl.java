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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.sprintapi.gwt.converter.client.json.lang.JsonObject;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

public class JsonObjectImpl extends JsonValueImpl implements JsonObject {

	private final Map<String, JsonValue> map;
	
	public JsonObjectImpl() {
		super();
		this.map = new HashMap<String, JsonValue>();
	}
	
	@Override
	public boolean containsKey(String name) {
		return map.containsKey(name);
	}

	@Override
	public JsonValue get(String name) {
		return map.get(name);
	}

	@Override
	public int size() {
		return map.size();
	}

	public void put(String name, JsonValue value) {
		map.put(name, value);
	}

	@Override
	public Set<String> names() {
		return map.keySet();
	}
}
