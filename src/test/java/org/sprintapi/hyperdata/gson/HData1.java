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

import org.sprintapi.hyperdata.HyperData;

public class HData1 implements HyperData<Meta> {

	private Meta metadata;
	
	private Double a;
	private Boolean b;
	
	private HData1 c;
	
	private Embedded d;
	
	public Double getA() {
		return a;
	}
	public void setA(Double a) {
		this.a = a;
	}
	public Boolean getB() {
		return b;
	}
	public void setB(Boolean b) {
		this.b = b;
	}
	public HData1 getC() {
		return c;
	}
	public void setC(HData1 c) {
		this.c = c;
	}
	@Override
	public Meta metadata() {
		return metadata;
	}
	@Override
	public void metadata(Meta meta) {
		this.metadata = meta;
	}
	public Embedded getD() {
		return d;
	}
	public void setD(Embedded d) {
		this.d = d;
	}
}
