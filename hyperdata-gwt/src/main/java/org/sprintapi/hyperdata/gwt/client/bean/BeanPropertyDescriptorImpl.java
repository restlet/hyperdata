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
package org.sprintapi.hyperdata.gwt.client.bean;

public class BeanPropertyDescriptorImpl implements BeanPropertyDescriptor {

	private String name;
	private Kind kind;
	private Class<?> clazz;
	private HyperBeanPropertyAttribute[] attributes;

	public BeanPropertyDescriptorImpl(String name, Class<?> clazz, Kind kind, HyperBeanPropertyAttribute[] attributes) {
		super();
		this.name = name;
		this.clazz = clazz;
		this.kind = kind;
		this.attributes = attributes;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public Kind getKind() {
		return kind;
	}
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public HyperBeanPropertyAttribute[] getAttributes() {
		return attributes;
	}

	public void setAttributes(HyperBeanPropertyAttribute[] attributes) {
		this.attributes = attributes;
	}
}
