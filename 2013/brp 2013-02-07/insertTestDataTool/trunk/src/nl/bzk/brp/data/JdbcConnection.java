/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcConnection {

	// example:
	// "jdbc:postgresql://localhost:5432/brp-2";
	private String connectionURL;

	// example:"org.postgresql.Driver"
	private String driver;

	private String username;

	private String password;

	public JdbcConnection(String connectionURL, String username,
			String password, String driver) {
		this.connectionURL = connectionURL;
		this.driver = driver;
		this.username = username;
		this.password = password;
	}

	public boolean executeBatch(List<String> sqlStatements)
			throws ClassNotFoundException, SQLException {

		// Load the Driver class.
		Class.forName(driver);

		// Create the connection using the static getConnection method
		Connection conn;
		conn = DriverManager.getConnection(connectionURL, username, password);

		Statement statement = conn.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		conn.setAutoCommit(false);

		for (String sql : sqlStatements) {
			statement.addBatch(sql);
		}

		try {
			statement.executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e.getNextException());
		} finally {
			conn.commit();

			statement.close();
			conn.close();
		}

		return true;
	}
}
