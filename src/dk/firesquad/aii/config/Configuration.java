package dk.firesquad.aii.config;

import static java.lang.String.format;

import java.io.FileNotFoundException;
import java.util.List;


public final class Configuration {
	private List<Driver> drivers;
	private List<ConnectionDetail> connections;

	public Configuration() throws FileNotFoundException, ClassNotFoundException {
	}

	public List<ConnectionDetail> getConnections() {
		return connections;
	}

	public void setConnections(List<ConnectionDetail> connections) {
		this.connections = connections;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(format("Drivers: %s\n", drivers))
				.append(format("Connections: %s\n", connections)).toString();
	}
}
