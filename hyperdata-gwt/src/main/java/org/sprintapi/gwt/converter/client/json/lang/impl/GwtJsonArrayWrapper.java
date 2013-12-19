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

import com.google.gwt.json.client.JSONArray;

public class GwtJsonArrayWrapper extends GwtJsonValueWrapper implements JsonArray {

	private final JSONArray array;
	
	public GwtJsonArrayWrapper(JSONArray array) {
		super(array);
		this.array = array;
	}

	@Override
	public int length() {
		return (array != null) ? array.size() : 0;
	}

	@Override
	public JsonValue get(int index) {
		return (array != null) ? GwtJsonValueWrapper.wrap(array.get(index)) : null;
	}
}
