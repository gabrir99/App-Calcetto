package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	public static final String JDBCurlSQLServer = new String("jdbc:sqlserver://;servername=LAPTOP-1FQVA96L\\SQLEXPRESS;databaseName=Calcetto;user=sa;password=pwd");
	public static final String JDBCdriverSQLServer = new String("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	protected Connection connection;
	protected Statement statement;

	public DBManager() throws ClassNotFoundException, SQLException{
		//DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	}

	public java.sql.Connection ConnectDB()
	{
		try
		{
			Class.forName(JDBCdriverSQLServer);
			connection = DriverManager.getConnection(JDBCurlSQLServer);
			statement = connection.createStatement();
			ShowMetadata();
		}
		
		catch (Exception e)
		{
			System.out.print(e.toString());
		}

		return connection;
	}

	public DBManager (String JDBCdriver, String JDBCurl, int resultSetType, int resultSetConcurrencyType) throws ClassNotFoundException, SQLException {
		Class.forName(JDBCdriver);
		connection = DriverManager.getConnection(JDBCurl);
		statement = connection.createStatement(resultSetType, resultSetConcurrencyType);
		ShowMetadata();
	}

	public void ShowMetadata() throws SQLException{
		DatabaseMetaData md = connection.getMetaData();

		System.out.println("-- ResultSet Type --");
		System.out.println("Supports TYPE_FORWARD_ONLY: " + md.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
		System.out.println(
				"Supports TYPE_SCROLL_INSENSITIVE: " + md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
		System.out.println(
				"Supports TYPE_SCROLL_SENSITIVE: " + md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));

		/**System.out.println("-- ResultSet Concurrency --");
		System.out.println("Supports CONCUR_READ_ONLY: " + md.supportsResultSetType(ResultSet.CONCUR_READ_ONLY));
		System.out.println("Supports CONCUR_UPDATABLE: " + md.supportsResultSetType(ResultSet.CONCUR_UPDATABLE));
*/
	}

	public ResultSet executeQuery(String query) throws SQLException{
		return statement.executeQuery(query);
	}

	public void executeUpdate(String query) throws SQLException{
		statement.executeUpdate(query);
	}
	public void close() throws SQLException {
		if (connection != null) {
			statement.close();
			connection.close();
		}
	}

}
