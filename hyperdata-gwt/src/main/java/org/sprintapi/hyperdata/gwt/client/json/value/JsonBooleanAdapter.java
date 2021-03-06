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

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.json.JsonValueAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonBoolean;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonBooleanImpl;

public class JsonBooleanAdapter implements JsonValueAdapter<Boolean> {

	@Override
	public Boolean read(JsonValue value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		JsonBoolean jsonBool = value.isBoolean();
		if (jsonBool == null) {
			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JsonBoolean.class);
		}
		return jsonBool.booleanValue();
	}

	@Override
	public JsonValue write(Boolean value) {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}		
		return JsonBooleanImpl.getInstance(value);
	}

	@Override
	public Class<Boolean> getJavaClass() {
		return Boolean.class;
	}
}
