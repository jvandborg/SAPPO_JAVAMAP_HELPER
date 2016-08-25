package com.sap.aii.mapping.lookup;

import java.util.Random;

public class ChannelHelper {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String serializeChannel(Channel channel) {
		char[] hexChars = new char[channel.getChannelId().length * 2];
		for (int j = 0; j < channel.getChannelId().length; j++) {
			int v = channel.getChannelId()[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static Channel createChannel(String party, String service, String channelName) {
	    int size = 16;
	    byte[] bytes = new byte[size];
	    new Random().nextBytes(bytes);
		return createChannel(bytes, party, service, channelName);
	}
	
	public static Channel createChannel(byte[] bytes, String party,
			String service, String channelName) {
		return new Channel(bytes, party, service, channelName);
	}
	
}
