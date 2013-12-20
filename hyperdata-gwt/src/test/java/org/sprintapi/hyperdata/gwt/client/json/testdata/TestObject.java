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
package org.sprintapi.hyperdata.gwt.client.json.testdata;

import java.util.Date;

import org.sprintapi.hyperdata.HyperDataContainer;

@HyperDataContainer(profile={"http://sprintapi.org/profile/test1"})
public class TestObject extends TestBaseObject {

	public enum EnumTest { E1, E2 };
	
	private Boolean booleanType;
	private Integer integerType;
	private Long longType;
	private Float floatType; 
	private Double doubleType;
	private String stringType;
	private Date dateType;
	
	private TestObject[] selfRefArray1;
	private TestObject[][] selfRefArray2; 

	private EnumTest enumType;
	
	public Float getFloatType() {
		return floatType;
	}
	public void setFloatType(Float floatType) {
		this.floatType = floatType;
	}
	public Double getDoubleType() {
		return doubleType;
	}
	public void setDoubleType(Double doubleType) {
		this.doubleType = doubleType;
	}
	public String getStringType() {
		return stringType;
	}
	public void setStringType(String stringType) {
		this.stringType = stringType;
	}
	public Integer getIntegerType() {
		return integerType;
	}
	public void setIntegerType(Integer integerType) {
		this.integerType = integerType;
	}
	public Date getDateType() {
		return dateType;
	}
	public void setDateType(Date dateType) {
		this.dateType = dateType;
	}
	public Boolean getBooleanType() {
		return booleanType;
	}
	public void setBooleanType(Boolean booleanType) {
		this.booleanType = booleanType;
	}
	public Long getLongType() {
		return longType;
	}
	public void setLongType(Long longType) {
		this.longType = longType;
	}
	public TestObject[] getSelfRefArray1() {
		return selfRefArray1;
	}
	public void setSelfRefArray1(TestObject[] selfRefArray1) {
		this.selfRefArray1 = selfRefArray1;
	}
	public TestObject[][] getSelfRefArray2() {
		return selfRefArray2;
	}
	public void setSelfRefArray2(TestObject[][] selfRefArray2) {
		this.selfRefArray2 = selfRefArray2;
	}
	public EnumTest getEnumType() {
		return enumType;
	}
	public void setEnumType(EnumTest enumType) {
		this.enumType = enumType;
	}
}
