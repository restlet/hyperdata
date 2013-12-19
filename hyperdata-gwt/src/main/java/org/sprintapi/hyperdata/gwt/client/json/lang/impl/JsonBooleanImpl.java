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

import org.sprintapi.hyperdata.gwt.client.json.lang.JsonBoolean;

public class JsonBooleanImpl extends JsonValueImpl implements JsonBoolean {

	private Boolean value; 
	
	protected static JsonBoolean FALSE = new JsonBooleanImpl(false);
	protected static JsonBoolean TRUE = new JsonBooleanImpl(true);
	
	protected JsonBooleanImpl(Boolean value) {
		super();
		this.value = value;
	}
	
	public static JsonBoolean getInstance(Boolean value) {
		 return ((value != null) && value) ? TRUE : FALSE;
	}
	
	@Override
	public Boolean booleanValue() {
		return value;
	}
}
