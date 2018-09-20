/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.exporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumpDatabase {
	private Connection conn;
	private PrintWriter out;

	private static Logger logger = LoggerFactory.getLogger(DumpDatabase.class);
	
	public static void main(String[] args) {
		try {
			DumpDatabase app = new DumpDatabase();
			String timestamp = new SimpleDateFormat("MMdd-hhmmss")
					.format(new Date());
			String fileName = "dumpdatabase-" + timestamp + ".txt";
			System.out.println("Creating " + fileName);
			app.dumpDatabase(fileName);
			System.out.println("Done.");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public DumpDatabase() throws IOException, ClassNotFoundException,
			SQLException {
		// Get database connection from hibernate.properties.
		// Or hard-code your own JDBC connection if desired.
		InputStream in = getClass()
				.getResourceAsStream("/hibernate.properties");
		Properties properties = new Properties();
		properties.load(in);
		String driver = properties
				.getProperty("hibernate.connection.driver_class");
		String url = properties.getProperty("hibernate.connection.url");
		String user = properties.getProperty("hibernate.connection.username");
		String password = properties
				.getProperty("hibernate.connection.password");

		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);
	}

	public void dumpDatabase(String fileName) throws FileNotFoundException,
			SQLException {
		logger.info("Creating dumpfile: " + fileName);
		out = new PrintWriter(fileName);
		listAll();
		out.close();
		conn.close();
	}

	public void listAll() throws SQLException {
		DatabaseMetaData metadata = conn.getMetaData();
		String[] types = { "TABLE" };
		ResultSet rs = metadata.getTables(null, null, null, types);
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			logger.info("Table:" + tableName);
			if(!tableName.startsWith("MAT$")) {
				try {
					logger.info("Dump table contents for table " + tableName);
					listTable(tableName);
				} catch (SQLException ex) {
					
				}
			}
		}
	}

	private void listTable(String tableName) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("select * from "
				+ tableName + " a");
		out.println("----" + tableName + "----");
		int rowNo = 0;
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			if (rowNo == 0)
				printTableColumns(rs);
			printResultRow(rs);
			rowNo++;
		}
	}

	private void printTableColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 0; i < metaData.getColumnCount(); i++) {
			int col = i + 1;
			out.println(metaData.getColumnName(col) + " "
					+ metaData.getColumnTypeName(col) + " " + "("
					+ metaData.getPrecision(col) + ")");
		}
		out.println("");
	}

	private void printResultRow(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 0; i < metaData.getColumnCount(); i++) {
			String column = metaData.getColumnName(i + 1);
			try {
				String value = rs.getString(column);
				if (value != null && !value.equals("null") && !value.equals("")
						&& !value.equals("0"))
					out.print(column + ": " + value + ", ");
			} catch (SQLException e) {
				out.print(column + ": " + e.getMessage());
			}
		}
		out.println("");
	}
}