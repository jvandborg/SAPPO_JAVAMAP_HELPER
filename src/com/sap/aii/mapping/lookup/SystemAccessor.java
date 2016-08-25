/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.sap.aii.mapping.lookup;

import com.sap.aii.mapping.lookup.LookupException;
import com.sap.aii.mapping.lookup.Payload;
import java.io.Serializable;

public final class SystemAccessor {
	private final SystemAccessor.Internal accessor;
	private String operationName = "";
	private String operationNamespace = "";

	SystemAccessor(SystemAccessor.Internal aAccessor) {
		if (aAccessor == null) {
			throw new NullPointerException("Parameter aAccessor is null.");
		} else {
			this.accessor = aAccessor;
		}
	}

	public void setOperationName(String aOperationName) {
		this.operationName = aOperationName;
	}

	public void setOperationNamespace(String aOperationNamespace) {
		this.operationNamespace = aOperationNamespace;
	}

	public Payload call(Payload payload) throws LookupException {
		if (payload == null) {
			throw new NullPointerException("payload is null");
		} else {
			return this.accessor.call(payload, this.operationName,
					this.operationNamespace, "Mapping");
		}
	}

	public void close() throws LookupException {
		this.accessor.close();
	}

	public interface Internal {
		Payload call(Payload payload, String operationName,
				String operationNamespace, String application)
				throws LookupException;

		Serializable execute(Payload payload, String operationName,
				String operationNamespace, String application)
				throws LookupException;

		void close() throws LookupException;
	}

}