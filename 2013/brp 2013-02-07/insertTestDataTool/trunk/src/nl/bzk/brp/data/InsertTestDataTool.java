/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class InsertTestDataTool {
	private static final String DB_DRIVER = "org.postgresql.Driver";

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, SQLException {
		
		if (args.length < 4) {
			System.out.println("required parameter: <connectionURL> <username> <password> <csv_file_name>");
			System.exit(0);
		}

		// "jdbc:postgresql://localhost:5432/brp-2"
		String connectionURL = args[0];
		// brp
		String username = args[1];
		// brp
		String password = args[2];

		String inputFile = args[3];

		new InsertTestDataTool(connectionURL, username, password, inputFile);
		
		System.out.println("-- DONE --");
	}

	public InsertTestDataTool(String connectionURL, String username,
			String password, String inputFile) throws NumberFormatException,
			ClassNotFoundException, SQLException, IOException {
		JdbcConnection jdbcConnection = new JdbcConnection(connectionURL,
				username, password, DB_DRIVER);

		File file = new File(inputFile);

		jdbcConnection.executeBatch(maakSqlBatch(file));
	}

	private List<String> maakSqlBatch(File file) throws NumberFormatException,
			IOException {
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;

		List<String> sqlBatch = new ArrayList<String>();

		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ";");

			String tableName = st.nextToken();
			int numberOfColumns = Integer.parseInt(st.nextToken());

			StringBuffer sqlStatement = new StringBuffer();

			sqlStatement.append("INSERT INTO ");
			sqlStatement.append(tableName);
			sqlStatement.append(" (");

			// Append columns
			for (int i = 0; i < numberOfColumns; i++) {
				if (i > 0) {
					sqlStatement.append(", ");
				}
				sqlStatement.append(st.nextToken());
			}

			sqlStatement.append(") VALUES (");

			// Append values
			while (st.hasMoreTokens()) {
				sqlStatement.append(st.nextToken());
				if (st.hasMoreTokens()) {
					sqlStatement.append(", ");
				}
			}

			sqlStatement.append(");");

			sqlBatch.add(sqlStatement.toString());
			System.out.println(sqlStatement);
		}
		// close the file
		bufRdr.close();

		return sqlBatch;
	}
}
