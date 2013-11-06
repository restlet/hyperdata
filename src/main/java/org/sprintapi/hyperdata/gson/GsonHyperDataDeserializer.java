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
import java.util.Map.Entry;
import java.util.Set;

import org.sprintapi.hyperdata.HyperHashMap;
import org.sprintapi.hyperdata.HyperMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GsonHyperDataDeserializer implements JsonDeserializer<HyperMap> {

	@Override
	public HyperMap deserialize(JsonElement json, Type arg1, JsonDeserializationContext ctx) throws JsonParseException {

		if (!json.isJsonObject()) {
			throw new JsonParseException("Unexpected " + json.getClass().getSimpleName() + ", expected JsonObject object.");
		}
		
		
		JsonObject jsonObject = json.getAsJsonObject();
		HyperHashMap hdata = null;
		
		if (jsonObject != null) {
			hdata = new HyperHashMap();
			
			final Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			if (entrySet != null) {
				
				Metadata metadata = null;
				
				for (Entry<String, JsonElement> entry : entrySet) {
					if (entry.getKey().startsWith("@")) {
						if (metadata == null) {
							metadata = new Metadata();
						}	
						JsonElement value = entry.getValue();
						if (value != null) {
							metadata.put(entry.getKey().substring(1),
								ctx.deserialize(value, 
									(value.isJsonObject() && value.getAsJsonObject().has("@".concat(Metadata.HREF_KEY)))
									? HyperMap.class
									: Object.class
									)
								);
						}

					} else {
						JsonElement value = entry.getValue();
						if (value != null) {
							hdata.put(entry.getKey(), 
								ctx.deserialize(value, 
									(value.isJsonObject() && value.getAsJsonObject().has("@".concat(Metadata.HREF_KEY)))
									? HyperMap.class
									: Object.class
									)
								);
						}
					}
				}
				hdata.metadata(metadata);
			}
		}
		return hdata;
	}
}
