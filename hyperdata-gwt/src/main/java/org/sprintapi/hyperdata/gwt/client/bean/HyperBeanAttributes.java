package org.sprintapi.hyperdata.gwt.client.bean;

public class HyperBeanAttributes {

	private String[] profile;

	public HyperBeanAttributes() {
		this(null);
	}

	public HyperBeanAttributes(String[] profile) {
		super();
		this.profile = profile;
	}

	public String[] getProfile() {
		return profile;
	}

	public void setProfile(String[] profile) {
		this.profile = profile;
	}
}
