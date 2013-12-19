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

import org.sprintapi.gwt.converter.client.ConverterException;
import org.sprintapi.gwt.converter.client.json.JsonReader;
import org.sprintapi.gwt.converter.client.json.lang.JsonValue;
import org.sprintapi.gwt.converter.client.json.lang.impl.GwtJsonValueWrapper;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class GwtJsonReader implements JsonReader {

	private boolean strictMode;
	
	public GwtJsonReader() {
		super();
		this.strictMode = true;
	}
	
	@Override
	public JsonValue read(String json) throws ConverterException {
		if (json == null) {
			return null;
		}
		
		try {
			JSONValue jsonValue = strictMode ? JSONParser.parseStrict(json) : JSONParser.parseLenient(json);
			if (jsonValue == null) {
				return null;
			}
			return GwtJsonValueWrapper.wrap(jsonValue);

		} catch (IllegalArgumentException ex) {
			throw new ConverterException(ex);
		
		} catch (NullPointerException ex) {
			throw new ConverterException(ex);
		}
	}

	public boolean isStrictMode() {
		return strictMode;
	}

	public void setStrictMode(boolean strictMode) {
		this.strictMode = strictMode;
	}
}
