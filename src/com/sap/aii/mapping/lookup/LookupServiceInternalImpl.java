package com.sap.aii.mapping.lookup;

import java.util.List;

import dk.firesquad.aii.config.ConfigurationLoader;
import dk.firesquad.aii.config.ConnectionDetail;

public class LookupServiceInternalImpl extends LookupService.Internal {

	@Override
	public byte[] getChannelId(String party, String service,
			String channelName) {

		List<ConnectionDetail> connections = ConfigurationLoader.getConfiguration().getConnections();
		for (ConnectionDetail connection : connections) {
			if (connection.getParty().equals(party) && connection.getService().equals(service) && connection.getChannelName().equals(channelName)) {
				return connection.getChannel().getChannelId();
			}
		}

		throw new RuntimeException("Channel is not configured!");
	}

	@Override
	public SystemAccessor.Internal getSystemAccessor(
			Channel channel) throws LookupException {

		return new SystemAccessorInternalImpl(channel);
	}

}
