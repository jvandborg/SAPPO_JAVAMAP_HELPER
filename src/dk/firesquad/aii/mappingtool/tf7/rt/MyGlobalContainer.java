package dk.firesquad.aii.mappingtool.tf7.rt;


import java.util.Map;

import com.sap.aii.mapping.api.AbstractTrace;
import com.sap.aii.mapping.api.InputAttachments;
import com.sap.aii.mapping.api.InputHeader;
import com.sap.aii.mapping.api.InputParameters;
import com.sap.aii.mapping.api.OutputAttachments;
import com.sap.aii.mapping.api.OutputHeader;
import com.sap.aii.mapping.api.OutputParameters;
import com.sap.aii.mappingtool.tf7.rt.GlobalContainer;

import dk.firesquad.aii.mapping.api.MyAbstractTrace;
import dk.firesquad.aii.mapping.api.MyInputAttachments;
import dk.firesquad.aii.mapping.api.MyOutputAttachments;

public class MyGlobalContainer implements GlobalContainer {

	private MyInputAttachments _inputAttachments = new MyInputAttachments();
	private MyOutputAttachments _outputAttachments = new MyOutputAttachments();
	private MyAbstractTrace _trace = new MyAbstractTrace();
	
	@Override
	public void copyInputAttachments() {
	}

	@Override
	public InputAttachments getInputAttachments() {
		return _inputAttachments;
	}

	@Override
	public InputHeader getInputHeader() {
		return null;
	}

	@Override
	public InputParameters getInputParameters() {
		return null;
	}

	@Override
	public OutputAttachments getOutputAttachments() {
		return _outputAttachments;
	}

	@Override
	public OutputHeader getOutputHeader() {
		return null;
	}

	@Override
	public OutputParameters getOutputParameters() {
		return null;
	}

	@Override
	public Object getParameter(String arg0) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getParameters() {
		return null;
	}

	@Override
	public AbstractTrace getTrace() {
		return _trace;
	}

	@Override
	public void setParameter(String arg0, Object arg1) {
	}

}
