/*
 * Copyright 2013 the original author or authors.
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
package org.sprintapi.hyperdata.gson;

import java.lang.reflect.Type;
import java.util.Map;

import org.sprintapi.hyperdata.HyperMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonHyperDataSerializer implements JsonSerializer<HyperMap> {

	@Override
	public JsonElement serialize(HyperMap src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == null) {
			return JsonNull.INSTANCE;
		}
		
		JsonObject obj = new JsonObject();
		
		final Map<String, Object> metadata = src.getMetadata(); 
		if (metadata != null) {
			for (String name : metadata.keySet()) {
				Object value = metadata.get(name);
				obj.add("@".concat(name), context.serialize(value));
			}
		}
		for (String name : src.names()) {
			Object value = src.get(name);
			obj.add(name, context.serialize(value));
		}
		return obj;
	}
}
