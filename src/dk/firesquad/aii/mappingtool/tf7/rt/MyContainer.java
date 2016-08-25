package dk.firesquad.aii.mappingtool.tf7.rt;


import java.util.Map;

import com.sap.aii.mapping.api.AbstractTrace;
import com.sap.aii.mapping.api.InputHeader;
import com.sap.aii.mapping.api.InputParameters;
import com.sap.aii.mapping.api.OutputHeader;
import com.sap.aii.mapping.api.OutputParameters;
import com.sap.aii.mappingtool.tf7.rt.Container;
import com.sap.aii.mappingtool.tf7.rt.GlobalContainer;


public class MyContainer implements Container {
	private MyGlobalContainer _globalContainer = new MyGlobalContainer();

	@Override
	public GlobalContainer getGlobalContainer() {
		return _globalContainer;
	}

	@Override
	public InputHeader getInputHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputParameters getInputParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputHeader getOutputHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputParameters getOutputParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractTrace getTrace() {
		return getGlobalContainer().getTrace();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getTransformationParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParameter(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
