package dk.firesquad.sax.helpers;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.firesquad.aii.lookup.SQLStatement;

public class SQLHandler extends DefaultHandler {

	private List<SQLStatement> sqlStatements = new Vector<SQLStatement>();
	// private SQLStatement sqlStatement;

	private String statement;

	private String table;
	private StringBuffer statementBuilder;
	private boolean accessField = false;

	private String action;

	private String elementName;

	private int elementLevel = 0;

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		elementLevel++;
		elementName = qName;
		System.out.println("Start Element :" + qName + " Level Element: "
				+ elementLevel);

		switch (elementLevel) {
		case 1:
		case 2:
			break;
		case 3:
			action = attributes.getValue("action");
			if ("SELECT".equals(action)) {
				statementBuilder = new StringBuffer("SELECT ");
			}
			break;
		case 4:
			if ("SELECT".equals(action) && "access".equals(elementName)) {
				accessField = true;
			}
			break;
		case 5:
			if ("SELECT".equals(action) && accessField) {
				statementBuilder.append(elementName);
			}
			break;
		default:
			break;
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("End Element :" + qName + " Level Element: "
				+ elementLevel);
		switch (elementLevel) {
		case 2:
			if (statement.trim().length() > 0) {
				sqlStatements.add(new SQLStatement(statement.trim(), qName));
			}
			break;
		case 3:
			action = null;
			break;
		case 4:
			break;
		default:
			break;
		}
		elementLevel--;
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {

		String value = new String(ch, start, length);
		System.out.println("Value : " + value);

		switch (elementLevel) {
		case 4:
			if ("SQL_QUERY".equals(action)) {
				if ("access".equals(elementName)) {
					// 'action' is SQL_QUERY and we're at 'access' element.
					// Therefore by
					// definition the SQL statement must be here.
					statement = value.trim();
					if (statement.length() == 0) {
						throw new RuntimeException(
								"Action is '"
										+ action
										+ "', but 'access' element does not contain a value.");
					}
				}
			} else if ("SELECT".equals(action)) {
				if ("table".equals(elementName)) {
					table = value.trim();
					if (table.length() == 0) {
						throw new RuntimeException(
								"Action is '"
										+ action
										+ "', but 'table' element does not contain a value.");
					}
					// } else if ("access".equals(elementName)) {

				}
			}
			break;
		case 5:
			// Seems like we need to do some variable exchange. Yeepiiie!
			if ("SQL_QUERY".equals(action)) {
				statement = statement.replaceAll(Pattern.quote("$"
						+ elementName + "$"), value.trim());
			} else if ("SELECT".equals(action)) {

			}

			break;
		}
	}

	public List<SQLStatement> getSQLStatements() {
		return sqlStatements;
	}

}
