package dk.firesquad.aii.mapping.api;


import com.sap.aii.mapping.api.AbstractTrace;

public class MyAbstractTrace extends AbstractTrace {

	@Override
	public void addDebugMessage(String debugMessage) {
		System.out.println(debugMessage);
	}
	
	@Override
	public void addInfo(String info) {
		System.out.println(info);
	}

	@Override
	public void addWarning(String warning) {
		System.out.println(warning);
	}

}
