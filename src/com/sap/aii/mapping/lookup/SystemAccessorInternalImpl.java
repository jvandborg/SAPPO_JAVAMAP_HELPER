package com.sap.aii.mapping.lookup;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.sap.aii.mapping.lookup.SystemAccessor.Internal;

import dk.firesquad.aii.config.Configuration;
import dk.firesquad.aii.config.ConfigurationLoader;
import dk.firesquad.aii.config.ConnectionDetail;
import dk.firesquad.aii.lookup.SQLStatement;
import dk.firesquad.sax.helpers.SQLHandler;

public class SystemAccessorInternalImpl implements Internal {

	private final Configuration configuration = ConfigurationLoader
			.getConfiguration();
	private ConnectionDetail connectionDetail;

	public SystemAccessorInternalImpl(Channel channel) {
		List<ConnectionDetail> connections = configuration.getConnections();
		for (ConnectionDetail connection : connections) {
			if (connection.getChannel().equals(channel)) {
				System.out.println("Connection detail found");
				this.connectionDetail = connection;
			}
		}
	}

	@Override
	public Payload call(Payload payload, String operationName,
			String operationNamespace, String application)
			throws LookupException {

		if ("JDBC".equals(connectionDetail.getType())) {

			try {
				Connection conn = DriverManager.getConnection(connectionDetail.getUrl(), connectionDetail.getUsername(), connectionDetail.getPassword());
				
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();

				SQLHandler handler = new SQLHandler(conn);
				saxParser.parse(payload.getContent(), handler);
				List<SQLStatement> sqlStatements = handler.getSQLStatements();
				System.out.println("SQL Statements: " + sqlStatements);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseAccessor accessor = new DataBaseAccessor(this);
		}

		return null;
	}

	@Override
	public void close() throws LookupException {
	}

	@Override
	public Serializable execute(Payload payload, String operationName,
			String operationNamespace, String application)
			throws LookupException {
		return null;
	}

}
