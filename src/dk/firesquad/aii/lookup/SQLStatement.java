package dk.firesquad.aii.lookup;

public class SQLStatement {
	private String statement;
	private String statementName;

	public SQLStatement(String statement, String statementName) {
		super();
		this.statement = statement;
		this.statementName = statementName;
	}

	public String getStatement() {
		return statement;
	}

	public String getStatementName() {
		return statementName;
	}

	@Override
	public String toString() {
		return "SQLStatement [statement=\"" + statement + "\", statementName="
				+ statementName + "]";
	}

}
