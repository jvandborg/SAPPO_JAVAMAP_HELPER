package dk.firesquad.aii.mapping.api;


import javax.xml.bind.DatatypeConverter;

import com.sap.aii.mapping.api.Attachment;

public class MyAttachment implements Attachment {
	
	private String _contentId;
	private String _contentType;
	private byte[] _content;
	
	protected MyAttachment(String contentId, byte[] content) {
		_contentId = contentId;
		_content = content;
		_contentType = "application/xml";
	}

	protected MyAttachment(String contentId, String contentType, byte[] content) {
		_contentId = contentId;
		_content = content;
		_contentType = contentType;
	}
	
	@Override
	public String getBase64EncodedContent() {
		return DatatypeConverter.printBase64Binary(_content);
	}

	@Override
	public byte[] getContent() {
		return _content;
	}

	@Override
	public String getContentId() {
		return _contentId;
	}

	@Override
	public String getContentType() {
		return _contentType;
	}

}
