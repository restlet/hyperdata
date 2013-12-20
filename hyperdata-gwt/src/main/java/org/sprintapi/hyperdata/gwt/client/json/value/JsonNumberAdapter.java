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
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonNumber;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonNumberImpl;

public abstract class JsonNumberAdapter<T> implements JsonValueAdapter<T> {

	protected Double readDouble(JsonValue value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		JsonNumber jsonNumber = value.isNumber();
		if (jsonNumber == null) {
			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JsonNumber.class);
		}
		return jsonNumber.doubleValue();
	}

	protected JsonValue writeDouble(double value) {
		return new JsonNumberImpl(value);
	}
}