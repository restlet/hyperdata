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
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;

public class JsonFloatConverter extends JsonNumberConverter<Float> {

	@Override
	public Float read(JsonValue value) throws ConverterException {
		Double d = readDouble(value);
		if (d == null) {
			return null;
		}
		return (float)((double)d);
	}

	@Override
	public JsonValue write(Float value) {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		return writeDouble(value);
	}

	@Override
	public Class<Float> getJavaClass() {
		return Float.class;
	}
}
