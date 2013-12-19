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

import java.util.Set;

import org.sprintapi.gwt.converter.client.json.lang.JsonObject;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

import com.google.gwt.json.client.JSONObject;

public class GwtJsonObjectWrapper extends GwtJsonValueWrapper implements JsonObject {

	private final JSONObject obj;
	
	public GwtJsonObjectWrapper(JSONObject obj) {
		super(obj);
		this.obj = obj;
	}

	@Override
	public boolean containsKey(String name) {
		return (obj != null) ? obj.containsKey(name) : false;
	}

	@Override
	public JsonValue get(String name) {
		return containsKey(name) ? GwtJsonValueWrapper.wrap(obj.get(name)) : null;
	}

	@Override
	public int size() {
		return (obj != null) ? obj.size() : 0;
	}

	@Override
	public Set<String> names() {
		return (obj != null) ? obj.keySet() : null;
	}
}
