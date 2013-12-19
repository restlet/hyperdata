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
package org.sprintapi.hyperdata.gwt.client.json.hdata;

import java.util.HashMap;


public class Meta extends HashMap<String, Object> {

	private static final long serialVersionUID = 3805020859705261170L;
	
	protected static String HREF_KEY = "href";
	
	public String getHref() {
		if (containsKey(HREF_KEY)) {
			Object href = get(HREF_KEY);
			if (href instanceof String) {
				return (String)href;
			} 
			if (href != null) {
				return href.toString();
			}
		}
		return null;
	}
	
	public void setHref(String href) {
		put(HREF_KEY, href);
	}
}
