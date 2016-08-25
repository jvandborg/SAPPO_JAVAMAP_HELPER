package dk.firesquad.aii.mapping.api;


import java.util.Hashtable;

import com.sap.aii.mapping.api.Attachment;
import com.sap.aii.mapping.api.OutputAttachments;


public class MyOutputAttachments implements OutputAttachments {

	Hashtable<String, Attachment> attachments = new Hashtable<String, Attachment>();
	
	@Override
	public Attachment create(String contentId, byte[] content) {
		return new MyAttachment(contentId, content);
	}

	@Override
	public Attachment create(String contentId, String contentType, byte[] content) {
		return new MyAttachment(contentId, contentType, content);
	}

	@Override
	public void removeAttachment(String contentId) {
		attachments.remove(contentId);
	}

	@Override
	public void setAttachment(Attachment attachment) {
		attachments.put(attachment.getContentId(), attachment);
	}

}
