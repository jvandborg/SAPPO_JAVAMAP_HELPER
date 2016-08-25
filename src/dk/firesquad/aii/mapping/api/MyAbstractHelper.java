package dk.firesquad.aii.mapping.api;



import java.io.InputStream;

import org.xml.sax.EntityResolver;

import com.sap.aii.mapping.api.AbstractHelper;
import com.sap.aii.mapping.api.AbstractTrace;

public class MyAbstractHelper extends AbstractHelper {

	@Override
	public EntityResolver getEntityResolver() {
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String arg0) {
		return null;
	}

	@Override
	public AbstractTrace getTrace() {
		return new MyAbstractTrace();
	}

}
