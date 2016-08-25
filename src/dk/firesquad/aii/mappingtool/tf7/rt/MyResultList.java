package dk.firesquad.aii.mappingtool.tf7.rt;


import java.util.Vector;

import com.sap.aii.mappingtool.tf7.rt.ResultList;

public class MyResultList implements ResultList {

	private Vector<Object> values = new Vector<Object>();

	@Override
	public void addContextChange() {
		values.add(CC);
	}

	@Override
	public void addSuppress() {
		values.add(SUPPRESS);
	}

	@Override
	public void addValue(Object value) {
		values.add(value);
	}

	@Override
	public void clear() {
		values.removeAllElements();
	}

	public String[] getValues() {
		return values.toArray(new String[0]);
	}

	public void printValues() {
		for (Object value : values) {
			System.out.println(value.toString());
		}
	}
}
