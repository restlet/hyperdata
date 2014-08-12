package org.sprintapi.hyperdata.gson;

import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.sprintapi.hyperdata.Patch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonPatch<T> implements Patch<T> {

	final JsonElement diff;
	final T ref;
	
	protected GsonPatch(JsonElement diff, T ref) {
		super();
		this.diff = diff;
		this.ref = ref;
	}

	@Override
	public T patch(T instance) {
		if (diff != null) {
			if (diff.isJsonNull()) {
				return null;
								
			} else if (diff.isJsonArray() || diff.isJsonPrimitive()) {
				return ref;
				
			} else if (diff.isJsonObject()) {
				return (T) patch(diff.getAsJsonObject(), ref, instance);
			}
			
		}
		return instance;
	}

	protected Object patch(JsonObject diff, Object ref, Object instance) {
		if (instance == null) {
			return ref;
		}
		
		Set<Entry<String, JsonElement>> es = diff.entrySet();
		for (Entry<String, JsonElement> e : es) {
			if (e.getKey().startsWith(Constants.META_CHAR)) {
				continue;	//TODO ignoring meta now
			}
			try {
				if (e.getValue().isJsonNull()) {
					PropertyUtils.setProperty(instance, e.getKey(), null);

				} else if (e.getValue().isJsonObject()) {
					Object r = PropertyUtils.getProperty(ref, e.getKey());
					Object i = PropertyUtils.getProperty(instance, e.getKey());
					
					PropertyUtils.setProperty(instance, e.getKey(), patch(e.getValue().getAsJsonObject(), r, i));
					
				} else {
					PropertyUtils.setProperty(instance, e.getKey(), PropertyUtils.getProperty(ref, e.getKey()));
				}
			} catch (IllegalAccessException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		return instance;
	}

}
