/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 *
 */
public class JdbcUpdateRef {
//    private static final int BUFFER_SIZE = 1024;
    private static final String INSERT_REFERENTIE =
            "INSERT INTO test.referentie (persId, bsn, dbVersie, excelTimestamp, sql) VALUES (?, ?, ?, ?, ?)";
    private static final String CLEAN_REFERENTIE =
            "DELETE FROM test.referentie where dbVersie = ?";
    private Connection connection;
    private String userName = "brp";
    private String password = "brp";
    private String url = "jdbc:postgresql://localhost/brp";
    private PreparedStatement insertReferntieSt = null;

    /**
     * .
     * @param dburl .
     * @param uname .
     * @param pword .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    private void init(final String dburl, final String uname, final String pword)
        throws SQLException, ClassNotFoundException
    {
        Properties connectionProps = new Properties();
        userName = uname;
        password = pword;
        url = dburl;
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        Class.forName("org.postgresql.Driver");
        connection =  DriverManager.getConnection(url, connectionProps);
        insertReferntieSt = getConnection().prepareStatement(INSERT_REFERENTIE);
    }

    /**
     * Constructor met database, userName en password.
     * @param url de url naar de database
     * @param user de userName
     * @param password het password
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    public JdbcUpdateRef(final String url, final String user, final String password)
        throws SQLException, ClassNotFoundException
    {
        init(url, user, password);
    }

//    public JdbcUpdateRef() throws SQLException, ClassNotFoundException, Exception {
//        init(url, userName, password);
//    }

    /**
     * Maakt de tabel schoon.
     * @param dbVersion de database versie die geschoond moet worden.
     * @throws SQLException .
     */
    public void reset(final String dbVersion) throws SQLException {
        PreparedStatement  st = getConnection().prepareStatement(CLEAN_REFERENTIE);
        st.setString(1, dbVersion);
        st.execute();
    }

    /**
     * Voeg een record toe van een specifiek persoon.
     * @param persId .
     * @param bsn .
     * @param dbVersion .
     * @param excelTimestamp .
     * @param sqlInsert .
     * @throws SQLException .
     */
    public void addSqlQuery(final Integer persId, final Integer bsn, final String dbVersion,
            final String excelTimestamp, final String sqlInsert) throws SQLException
    {
        insertReferntieSt.setObject(1, persId);
        insertReferntieSt.setObject(2, bsn);
        insertReferntieSt.setString(3, dbVersion);
        insertReferntieSt.setString(4, excelTimestamp);
        insertReferntieSt.setString(5, sqlInsert);
        insertReferntieSt.executeUpdate();
    }

    private Connection getConnection() {
        return connection;
    }

}
