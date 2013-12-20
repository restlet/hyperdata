package org.sprintapi.hyperdata.gwt.client.json.testdata;

import java.util.Map;

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.bean.BeanAdapter;
import org.sprintapi.hyperdata.gwt.client.bean.BeanPropertyDescriptor;
import org.sprintapi.hyperdata.gwt.client.bean.HyperBeanAttributes;
import org.sprintapi.hyperdata.gwt.client.bean.HyperBeanPropertyAttributes;

public class TestObjectBeanAdapterMock implements BeanAdapter<TestObject> {

	@Override
	public void setPropertyValue(TestObject bean, String name, Object value) throws AdapterException {
		if ("integerType".equals(name)) {
			bean.setIntegerType((Integer)value);
		} else if ("metadata".equals(name)) {
			bean.setMetadata((Map<String, String>) value);
		}		
	}

	@Override
	public Object getPropertyValue(TestObject bean, String name) throws AdapterException {
		if ("integerType".equals(name)) {
			return bean.getIntegerType();
		} else if ("metadata".equals(name)) {
			return bean.getMetadata();
		}
		return null;
	}

	@Override
	public BeanPropertyDescriptor[] getProperties() {
		return new BeanPropertyDescriptor[]{
			new BeanPropertyDescriptor() {

				@Override
				public String getName() {
					return "integerType";
				}

				@Override
				public Kind getKind() {
					return null;
				}

				@Override
				public Class<?> getClazz() {
					return Integer.class;
				}

				@Override
				public HyperBeanPropertyAttributes getAttributes() {
					return null;
				}
			},
			new BeanPropertyDescriptor() {

				@Override
				public String getName() {
					return "metadata";
				}

				@Override
				public Kind getKind() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Class<?> getClazz() {
					return Map.class;
				}

				@Override
				public HyperBeanPropertyAttributes getAttributes() {
					return new HyperBeanPropertyAttributes();
				}
			}
		};
	}

	@Override
	public Class<TestObject> getBeanClass() {
		return TestObject.class;
	}

	@Override
	public TestObject createInstance() {
		return new TestObject();
	}

	@Override
	public HyperBeanAttributes getAttributes() {
		return new HyperBeanAttributes(new String[]{"http://sprintapi.org/profile/test1"});
	}

}
