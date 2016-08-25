package dk.firesquad.aii.mapping.api;

import java.util.Collection;

import com.sap.aii.mapping.api.Attachment;
import com.sap.aii.mapping.api.InputAttachments;

public class MyInputAttachments implements InputAttachments {

	@Override
	public boolean areAttachmentsAvailable() {
		return false;
	}

	@Override
	public Collection<String> getAllContentIds(boolean withRemoved) {
		return null;
	}

	@Override
	public Attachment getAttachment(String contentID) {
		return null;
	}

}
