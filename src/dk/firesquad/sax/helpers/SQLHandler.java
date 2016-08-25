package dk.firesquad.sax.helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.firesquad.aii.lookup.SQLStatement;

public class SQLHandler extends DefaultHandler {

	// SQL Statements from SAX parsing
	private List<SQLStatement> sqlStatements = new Vector<SQLStatement>();

	// Database variables
	private final Connection conn;
	private Map<String, Map<String, Integer>> tableColumnMetadata = new TreeMap<String, Map<String, Integer>>();

	// State variables for class
	private String elementName;
	private int elementLevel = 0;

	// State variables for SAX loop run
	private StringBuffer statementBuilder = new StringBuffer();
	private String table;
	private boolean tableSegment = false;
	private boolean accessSegment = false;
	private boolean firstAccessField = true;
	private boolean keySegment = false;
	private boolean firstKeySegment = true;
	private boolean firstKeyField = true;
	private String keyCompareOperation;
	private String keyHasQuot;
	private boolean keyIsNull = false;
	private String action;

	public SQLHandler(Connection conn) {
		this.conn = conn;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		elementLevel++;
		elementName = qName.trim();
		System.out.println("Start Element : " + elementName
				+ " Level Element : " + elementLevel);

		switch (elementLevel) {
		case 2:
			// Initialise variables for run
			statementBuilder = statementBuilder.delete(0, statementBuilder
					.length());
			table = null;
			tableSegment = false;
			accessSegment = false;
			firstAccessField = true;
			keySegment = false;
			firstKeySegment = true;
			firstKeyField = true;
			keyCompareOperation = null;
			keyHasQuot = null;
			keyIsNull = false;
			action = null;
			break;
		case 3:
			action = attributes.getValue("action");
			if ("SELECT".equals(action)) {
				table = elementName;
				System.out.println("Table set from element name : " + table);
				statementBuilder.append("SELECT ");
			}
			break;
		case 4:
			if ("SQL_QUERY".equals(action)) {
				if ("access".equals(elementName)) {
					accessSegment = true;
				} else if (elementName.startsWith("key")) {
					keySegment = true;
					firstKeyField = true;
				}
			} else if ("SELECT".equals(action)) {
				if ("table".equals(elementName)) {
					tableSegment = true;
				} else if ("access".equals(elementName)) {
					accessSegment = true;
				} else if (elementName.startsWith("key")) {
					keySegment = true;
					firstKeyField = true;
				}
			}
			break;
		case 5:
			if ("SELECT".equals(action)) {
				if (accessSegment) {
					if (!firstAccessField) {
						statementBuilder.append(", ");
					}
					firstAccessField = false;
					statementBuilder.append(elementName);
				} else if (keySegment) {
					keyCompareOperation = attributes.getValue("compareOperation") == null ? "EQ"
							: attributes.getValue("compareOperation");

					keyHasQuot = attributes.getValue("hasQuot");

					keyIsNull = "TRUE".equals(attributes.getValue("isNull"));

					if (!keyIsNull) {
						if (firstKeySegment) {
							firstKeySegment = false;
							statementBuilder.append(" WHERE ");
						} else if (!firstKeySegment && firstKeyField) {
							statementBuilder.append(" OR ");
						} else if (!firstKeyField) {
							statementBuilder.append(" AND ");
						}
						firstKeyField = false;
						statementBuilder.append(elementName);
						if ("EQ".equals(keyCompareOperation)) {
							statementBuilder.append(" = ");
						} else if ("NEQ".equals(keyCompareOperation)) {
							statementBuilder.append(" <> ");
						} else if ("LT".equals(keyCompareOperation)) {
							statementBuilder.append(" < ");
						} else if ("LTEQ".equals(keyCompareOperation)) {
							statementBuilder.append(" <= ");
						} else if ("GT".equals(keyCompareOperation)) {
							statementBuilder.append(" > ");
						} else if ("GTEQ".equals(keyCompareOperation)) {
							statementBuilder.append(" >= ");
						} else if ("LIKE".equals(keyCompareOperation)) {
							statementBuilder.append(" LIKE ");
						}
					}
				}
			}
			break;
		default:
			break;
		}

	}

	public void characters(char ch[], int start, int length)
			throws SAXException {

		String value = new String(ch, start, length);
		System.out.println("Value : " + value);

		switch (elementLevel) {
		case 4:
			if ("SQL_QUERY".equals(action)) {
				if (accessSegment) {
					// 'action' is SQL_QUERY and we're at 'access' element.
					// Therefore by definition the SQL statement must be here.
					statementBuilder.append(value.trim());
					if (statementBuilder.length() == 0) {
						throw new RuntimeException(
								"Action is '"
										+ action
										+ "', but 'access' element does not contain a value.");
					}
				}
			} else if ("SELECT".equals(action)) {
				if (tableSegment) {
					table = value.trim();
					if (table.length() == 0) {
						throw new RuntimeException(
								"Action is '"
										+ action
										+ "', but 'table' element does not contain a value.");
					}
					System.out
							.println("Table set from value of <table> element : "
									+ table);
				}
			}
			break;
		case 5:
			// Seems like we need to do some variable exchange. Yeepiiie!
			if ("SQL_QUERY".equals(action)) {
				if (keySegment) {
					String statement = statementBuilder.toString();
					statement = statement.replaceAll(Pattern.quote("$"
							+ elementName + "$"), value.trim());
					statementBuilder.delete(0, statementBuilder.length());
					statementBuilder.append(statement);
					System.out.println("Variables exchanged : " + elementName);
					System.out.println("Statement is now this : " + statement);
				}
			} else if ("SELECT".equals(action)) {
				if (keySegment && !keyIsNull) {
					boolean quote = false;
					Integer columnType = tableColumnMetadata.get(table).get(
							elementName);
					switch (columnType.intValue()) {
					case Types.CHAR:
					case Types.DATE:
					case Types.NCHAR:
					case Types.NVARCHAR:
					case Types.TIME:
					case Types.TIMESTAMP:
					case Types.VARCHAR:
						quote = true;
						break;
					}

					if (keyHasQuot != null) {
						if ("YES".equals(keyHasQuot)) {
							quote = true;
						} else if ("NO".equals(keyHasQuot)) {
							quote = false;
						}
					}

					if (quote) {
						statementBuilder.append("'").append(value).append("'");
					} else {
						statementBuilder.append(value);
					}
				}
			}

			break;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		elementName = qName;
		System.out.println("End Element : " + elementName + " Level Element : "
				+ elementLevel);
		switch (elementLevel) {
		case 1:
			break;
		case 2:
			// SAX run almost coming to an end
			if ("SQL_QUERY".equals(action)) {
				String statement = statementBuilder.toString().trim();
				if (statement.length() > 0) {
					sqlStatements.add(new SQLStatement(statement, qName));
				}
			} else if ("SELECT".equals(action)) {
				if (statementBuilder.length() > 0) {
					sqlStatements.add(new SQLStatement(statementBuilder
							.toString(), qName));
				}
			}
			break;
		case 3:
			break;
		case 4:
			if ("SQL_QUERY".equals(action)) {
				if (accessSegment) {
					accessSegment = false;
				} else if (keySegment) {
					keySegment = false;
					firstKeyField = true;
				}
			} else if ("SELECT".equals(action)) {
				if (accessSegment) {
					accessSegment = false;
					firstAccessField = true;
					statementBuilder.append(" FROM " + table);
					try {
						loadTableMetaData(table);
					} catch (SQLException e) {
						throw new SAXException(
								"Unable to load table metadata!", e);
					}
				} else if (keySegment) {
					keySegment = false;
					firstKeyField = true;
				}
			}
			break;
		case 5:
			break;
		default:
			break;
		}
		elementLevel--;
	}

	private void loadTableMetaData(String table) throws SQLException {
		System.out.println("Loading metadata for table : " + table);
		if (!tableColumnMetadata.containsKey(table)) {
			Map<String, Integer> columnMetadata = new TreeMap<String, Integer>();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM " + table + " WHERE 0 = 1";
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metadata = rs.getMetaData();
			for (int i = 1; i <= metadata.getColumnCount(); i++) {
				String columnName = metadata.getColumnName(i);
				Integer columnType = metadata.getColumnType(i);
				System.out.println("Column : " + columnName + " Type : "
						+ metadata.getColumnTypeName(i));
				columnMetadata.put(columnName, columnType);
			}
			tableColumnMetadata.put(table, columnMetadata);
		}
	}

	public List<SQLStatement> getSQLStatements() {
		return sqlStatements;
	}

}
