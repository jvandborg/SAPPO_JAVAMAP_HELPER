package dk.firesquad.aii.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import com.sap.aii.mapping.lookup.ChannelHelper;
import com.sap.aii.mapping.lookup.LookupException;

public class ConfigurationLoader {

	private static Configuration configuration;
	
	public ConfigurationLoader(String configurationFile) throws FileNotFoundException, ClassNotFoundException, LookupException {
		Yaml yaml = new Yaml();
		InputStream in = new FileInputStream(configurationFile);
		configuration = yaml.loadAs(in, Configuration.class);
		System.out.println(configuration.toString());

		List<Driver> drivers = configuration.getDrivers();
		for (Driver driver : drivers) {
			if ("JDBC".equalsIgnoreCase(driver.getType())) {
				DriverManager.getDrivers();
				System.out.println("Loading JDBC driver(s)");
				Class.forName(driver.getName());
			}
		}
		
		List<ConnectionDetail> connections = configuration.getConnections();
		for (ConnectionDetail connection : connections) {
			connection.setChannel(ChannelHelper.createChannel(connection.getParty(), connection.getService(), connection.getChannelName()));
		}
		
	}

	public static Configuration getConfiguration() {
		if (configuration == null) {
			throw new RuntimeException("Configuration has not been loaded!");
		}
		return configuration;
	}

}
