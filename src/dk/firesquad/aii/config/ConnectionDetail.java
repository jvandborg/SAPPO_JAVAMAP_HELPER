package dk.firesquad.aii.config;

import com.sap.aii.mapping.lookup.Channel;

public final class ConnectionDetail {
	private String party;
	private String service;
	private String channelName;
	private String type;
	private String url;
	private String username;
	private String password;
	private Channel channel;

	public ConnectionDetail() {
		
	}
	
	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		if (party == null) {
			party = "";
		}
		this.party = party;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		if (service == null) {
			service = "";
		}
		this.service = service;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "Connection [channelName=" + channelName + ", party=" + party
				+ ", password=" + password + ", service=" + service + ", type="
				+ type + ", url=" + url + ", username=" + username + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((channelName == null) ? 0 : channelName.hashCode());
		result = prime * result + ((party == null) ? 0 : party.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionDetail other = (ConnectionDetail) obj;
		if (channelName == null) {
			if (other.channelName != null)
				return false;
		} else if (!channelName.equals(other.channelName))
			return false;
		if (party == null) {
			if (other.party != null)
				return false;
		} else if (!party.equals(other.party))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}

}
