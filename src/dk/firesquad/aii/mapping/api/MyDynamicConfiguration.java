package dk.firesquad.aii.mapping.api;

import java.util.Iterator;

import com.sap.aii.mapping.api.DynamicConfiguration;
import com.sap.aii.mapping.api.DynamicConfigurationKey;

public class MyDynamicConfiguration extends DynamicConfiguration {

	@Override
	public boolean containsKey(DynamicConfigurationKey key) {
		return false;
	}

	@Override
	public String get(DynamicConfigurationKey key) {
		return null;
	}

	@Override
	public Iterator getKeys() {
		return null;
	}

	@Override
	public Iterator getKeys(String namespace) {
		return null;
	}

	@Override
	public String put(DynamicConfigurationKey key, String value) {
		return null;
	}

	@Override
	public String remove(DynamicConfigurationKey key) {
		return null;
	}

	@Override
	public void removeAll() {

	}

	@Override
	public void removeNamespace(String namespace) {

	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public int size(String namespace) {
		return 0;
	}

}
