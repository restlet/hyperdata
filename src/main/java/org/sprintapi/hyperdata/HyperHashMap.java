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
package org.sprintapi.hyperdata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HyperHashMap extends HashMap<String, Object> implements HyperMap {

	private static final long serialVersionUID = 3484311546512255395L;
	
	Map<String, Object> metadata;
	
	@Override
	public Map<String, Object> metadata() {
		return metadata;
	}

	public void metadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	@Override
	public Set<String> names() {
		return keySet();
	}
	
	@Override
	public Object get(String name) {
		return super.get(name);
	}
}
