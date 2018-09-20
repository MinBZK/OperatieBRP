/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.testdata;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.RandomUtil;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBCollector {

    /** De Constante LOG. */
    private static final Logger LOG                  = LoggerFactory.getLogger(DBCollector.class);

    public static final String  HUWELIJK_PARTNER1    = "partner1";
    public static final String  HUWELIJK_PARTNER2    = "partner2";
    public static final String  HUWELIJK_NAAM        = "naam";
    public static final String  HUWELIJK_VOORVOEGSEL = "voorvoegsel";
    private static final String HUWELIJK_RELATIE_ID  = "relatie";

    private static final int    MAX_QUERY_RETRY      = 5;

    private static DataSource   dataSource           = setupDataSource("jdbc:postgresql://"
                                                         + TestClient.eigenschappen.getDbHost() + ":"
                                                         + TestClient.eigenschappen.getDbPort() + "/"
                                                         + TestClient.eigenschappen.getDbName());

    /**
     * Setup data source.
     *
     * @param connectURI de connect uri
     * @return de data source
     */
    private static DataSource setupDataSource(final String connectURI) {
        ConnectionFactory connectionFactory =
            new DriverManagerConnectionFactory(connectURI, TestClient.eigenschappen.getDbUsername(),
                    TestClient.eigenschappen.getDbPassword());
        ObjectPool connectionPool = new GenericObjectPool(null);
        new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT 42", false, false);
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
        return dataSource;
    }

    public static LinkedList<Map<String, String>> collectAdressen() throws SQLException {
        LinkedList<Map<String, String>> adresList = new LinkedList<Map<String, String>>();

        final QueryRunner queryRunner = new QueryRunner();

        Connection conn = startConnection();

        String queryCountId = "SELECT COUNT(kp.id) FROM kern.persadres kp";
        Long countId = queryRunner.query(conn, queryCountId, new ScalarHandler<Long>());

        String queryMin = "SELECT MIN(kp.id) FROM kern.persadres kp";
        Integer minId = queryRunner.query(conn, queryMin, new ScalarHandler<Integer>());

        endConnection(conn);

        int queryCount = 0;

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (adresList.size() < TestClient.eigenschappen.getTestCount() && queryCount < MAX_QUERY_RETRY) {

            StringBuilder querySB =
                new StringBuilder(
                        "SELECT \r\n"
                            + "    (SELECT p.code FROM kern.partij p WHERE p.id = kp.gem) as gemeentecode, (SELECT pl.code FROM kern.plaats pl WHERE pl.id = kp.wpl)"
                            + ", kp.nor" + ", kp.postcode" + ", kp.huisnr" + ", kp.huisletter"
                            + ", kp.huisnrtoevoeging" + ", kp.pers \r\n" + "FROM kern.persadres kp \r\n"
                            + "WHERE kp.id IN (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * 1.5); i++) {
                long id = RandomUtil.nextLong(countId - minId) + minId;
                querySB.append(id + ",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) \r\n");
            querySB.append(" AND kp.postcode IS NOT NULL \r\n" + "AND kp.huisnr IS NOT NULL \r\n"
                + "AND NOT EXISTS (SELECT p.id FROM kern.pers p WHERE p.datOverlijden IS NOT NULL AND kp.pers = p.id )");

            conn = startConnection();
            List<Map<String, Object>> adresListFromDB = queryRunner.query(conn, querySB.toString(), new MapListHandler());
            endConnection(conn);

            for (Map<String, Object> adres : adresListFromDB) {
                HashMap<String, String> adresMap = new HashMap<String, String>();

                if (!getMapValue(adres, TestdataServiceImpl.GEMEENTECODE).equals("")) {
                    String gemeentecode =
                        String.format("%04d", Integer.parseInt(getMapValue(adres, TestdataServiceImpl.GEMEENTECODE)));
                    adresMap.put(TestdataServiceImpl.GEMEENTECODE, gemeentecode);
                }
                if (!getMapValue(adres, TestdataServiceImpl.WOONPLAATSCODE).equals("")) {
                    adresMap.put(TestdataServiceImpl.WOONPLAATSCODE, getMapValue(adres, TestdataServiceImpl.WOONPLAATSCODE));
                }
                if (!getMapValue(adres, TestdataServiceImpl.NOR).equals("")) {
                    adresMap.put(TestdataServiceImpl.NOR, getMapValue(adres, TestdataServiceImpl.NOR));
                }
                if (!getMapValue(adres, TestdataServiceImpl.POSTCODE).equals("")) {
                    adresMap.put(TestdataServiceImpl.POSTCODE, getMapValue(adres, TestdataServiceImpl.POSTCODE));
                }
                if (!getMapValue(adres, TestdataServiceImpl.HUISNUMMER).equals("")) {
                    adresMap.put(TestdataServiceImpl.HUISNUMMER, getMapValue(adres, TestdataServiceImpl.HUISNUMMER));
                }
                if (!getMapValue(adres, TestdataServiceImpl.HUISLETTER).equals("")) {
                    adresMap.put(TestdataServiceImpl.HUISLETTER, getMapValue(adres, TestdataServiceImpl.HUISLETTER));
                }
                if (!getMapValue(adres, TestdataServiceImpl.HUISNUMMERTOEVOEGING).equals("")) {
                    adresMap.put(TestdataServiceImpl.HUISNUMMERTOEVOEGING,
                            getMapValue(adres, TestdataServiceImpl.HUISNUMMERTOEVOEGING));
                }
                adresList.add(adresMap);
            }

            queryCount++;

            if (queryCount == MAX_QUERY_RETRY) {
                LOG.info("Adreslist kon helaas niet volledig gevuld worden (" + adresList.size() + " / "
                    + TestClient.eigenschappen.getTestCount() + ") binnen de max aantal pogingen (" + MAX_QUERY_RETRY
                    + ").");
            }
        }

        LOG.debug("AdresList: " + adresList.size());

        return adresList;
    }

    /**
     * Collect a nummers.
     *
     * @param dataSource de data source
     * @throws SQLException de sQL exception
     */
    public static LinkedList<String> collectANummers() throws SQLException {
        LinkedList<String> aNummerList = new LinkedList<String>();
        final QueryRunner queryRunner = new QueryRunner();

        Connection conn = startConnection();

        String queryCountId = "SELECT COUNT(kp.id) FROM kern.pers kp";
        Long countId = queryRunner.query(conn, queryCountId, new ScalarHandler<Long>());

        String queryMin = "SELECT MIN(kp.id) FROM kern.pers kp";
        Integer minId = queryRunner.query(conn, queryMin, new ScalarHandler<Integer>());

        endConnection(conn);

        int queryCount = 0;

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (aNummerList.size() < TestClient.eigenschappen.getTestCount() && queryCount < MAX_QUERY_RETRY) {
            StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT kp.anr FROM kern.pers kp WHERE kp.id IN (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * 1.5); i++) {
                long id = RandomUtil.nextLong(countId - minId) + minId;
                querySB.append(id + ",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) AND anr IS NOT NULL AND kp.rdnopschortingbijhouding IS NULL");

            conn = startConnection();
            List<Object[]> aNummerArrayList = queryRunner.query(conn, querySB.toString(), new ArrayListHandler());

            endConnection(conn);
            for (Object[] aNummerArray : aNummerArrayList) {
                if (!aNummerList.contains("" + aNummerArray[0])) {
                    aNummerList.add("" + aNummerArray[0]);
                }
            }

            queryCount++;

            if (queryCount == MAX_QUERY_RETRY) {
                LOG.info("Anummerlist kon helaas niet volledig gevuld worden (" + aNummerList.size() + " / "
                    + TestClient.eigenschappen.getTestCount() + ") binnen de max aantal pogingen (" + MAX_QUERY_RETRY
                    + ").");
            }
        }

        LOG.debug("AnummerList: " + aNummerList.size());
        return aNummerList;
    }

    /**
     * Collect bsns.
     *
     * @param dataSource de data source
     * @return
     * @throws SQLException de sQL exception
     */
    public static LinkedList<String> collectBsns() throws SQLException {
        LinkedList<String> bsnList = new LinkedList<String>();

        final QueryRunner queryRunner = new QueryRunner();

        Connection conn = startConnection();

        String queryCountId = "SELECT COUNT(kp.id) FROM kern.pers kp";
        Long countId = queryRunner.query(conn, queryCountId, new ScalarHandler<Long>());

        String queryMin = "SELECT MIN(kp.id) FROM kern.pers kp";
        Integer minId = queryRunner.query(conn, queryMin, new ScalarHandler<Integer>());

        endConnection(conn);

        int queryCount = 0;

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (bsnList.size() < TestClient.eigenschappen.getTestCount() && queryCount < MAX_QUERY_RETRY) {
            StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT kp.bsn FROM kern.pers kp WHERE kp.id IN (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * 1.5); i++) {
                long id = RandomUtil.nextLong(countId - minId) + minId;
                querySB.append(id + ",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) AND kp.bsn IS NOT NULL AND kp.rdnopschortingbijhouding IS NULL");

            conn = startConnection();
            List<Object[]> bsnArrayList = queryRunner.query(conn, querySB.toString(), new ArrayListHandler());

            endConnection(conn);
            for (Object[] bsnArray : bsnArrayList) {
                if (!bsnList.contains("" + bsnArray[0])) {
                    bsnList.add("" + bsnArray[0]);
                }
            }

            queryCount++;

            if (queryCount == MAX_QUERY_RETRY) {
                LOG.info("BSNlist kon helaas niet volledig gevuld worden (" + bsnList.size() + " / "
                    + TestClient.eigenschappen.getTestCount() + ") binnen de max aantal pogingen (" + MAX_QUERY_RETRY
                    + ").");
            }
        }

        LOG.debug("BsnList: " + bsnList.size());
        return bsnList;
    }

    /**
     * Collect vrijgezel bsns.
     *
     * @param dataSource de data source
     * @throws SQLException de sQL exception
     */
    public static LinkedList<String> collectVrijgezelBsns() throws SQLException {
        LinkedList<String> vrijgezelBsnList = new LinkedList<String>();

        final QueryRunner queryRunner = new QueryRunner();

        Connection conn = startConnection();

        String queryCountId = "SELECT COUNT(kp.id) FROM kern.pers kp";
        Long countId = queryRunner.query(conn, queryCountId, new ScalarHandler<Long>());

        String queryMin = "SELECT MIN(kp.id) FROM kern.pers kp";
        Integer minId = queryRunner.query(conn, queryMin, new ScalarHandler<Integer>());

        endConnection(conn);

        int queryCount = 0;

        Date nuDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nu = sdf.format(nuDate);
        String datumMinimaal18 = "" + (Integer.valueOf(nu) - 180000);

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (vrijgezelBsnList.size() < TestClient.eigenschappen.getTestCount() && queryCount < (MAX_QUERY_RETRY * 3))
        {
            StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT kp.bsn FROM kern.pers kp WHERE kp.id IN (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * 1.5); i++) {
                long id = RandomUtil.nextLong(countId - minId) + minId;
                querySB.append(id + ",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) " + " AND kp.bsn IS NOT NULL " + " AND kp.rdnopschortingbijhouding IS NULL"
                + " AND kp.datgeboorte < " + datumMinimaal18
                + " AND NOT EXISTS (SELECT kb.pers FROM kern.betr kb WHERE kb.rol = 3 AND kb.pers = kp.id )\r\n"
                + " AND NOT EXISTS (SELECT ki.pers FROM kern.persindicatie ki WHERE ki.srt=2 AND ki.pers = kp.id)\r\n");

            conn = startConnection();
            List<Object[]> vrijgezelBsnArrayList = queryRunner.query(conn, querySB.toString(), new ArrayListHandler());

            endConnection(conn);
            for (Object[] vrijgezelBsnArray : vrijgezelBsnArrayList) {
                if (!vrijgezelBsnList.contains("" + vrijgezelBsnArray[0])) {
                    vrijgezelBsnList.add("" + vrijgezelBsnArray[0]);
                }
            }

            queryCount++;

            if (queryCount == MAX_QUERY_RETRY) {
                LOG.info("Vrijgezel BSNlist kon helaas niet volledig gevuld worden (" + vrijgezelBsnList.size() + " / "
                    + TestClient.eigenschappen.getTestCount() + ") binnen de max aantal pogingen ("
                    + MAX_QUERY_RETRY + ").");
            }
        }

        LOG.debug("VrijgezelBsnList: " + vrijgezelBsnList.size());
        return vrijgezelBsnList;
    }

    /**
     * Collect huwelijken.
     *
     * @param dataSource de data source
     * @throws SQLException de sQL exception
     */
    public static LinkedList<Map<String, String>> collectHuwelijken() throws SQLException {
        LinkedList<Map<String, String>> huwelijkList = new LinkedList<Map<String, String>>();

        final QueryRunner queryRunner = new QueryRunner();

        StringBuilder query =
            new StringBuilder(
                    "SELECT b.relatie as relatie\r\n"
                        + "    , (SELECT p.bsn FROM kern.pers p WHERE p.id = b.pers AND p.geslachtsaand = 1) as man\r\n"
                        + "    , (SELECT p.bsn FROM kern.pers p WHERE p.id = b.pers AND p.geslachtsaand = 2) as vrouw\r\n"
                        + "    , (SELECT p.geslnaam FROM kern.pers p WHERE p.id = b.pers AND p.geslachtsaand = 1) as naam \r\n"
                        + "    , (SELECT p.voorvoegsel FROM kern.pers p WHERE p.id = b.pers AND p.geslachtsaand = 1) as voorvoegsel \r\n"
                        + "FROM kern.betr b\r\n" + "WHERE b.relatie IN (\r\n" + "    SELECT r.id \r\n"
                        + "    FROM kern.relatie r \r\n" + "    WHERE r.srt != 3\r\n"
                        + "    AND dateinde IS NULL    \r\n" + "    LIMIT " + TestClient.eigenschappen.getTestCount()
                        + "\r\n" + ")");

        Connection conn = startConnection();

        List<Map<String, Object>> huwelijkListFromDB = queryRunner.query(conn, query.toString(), new MapListHandler());

        endConnection(conn);

        HashMap<String, Map<String, String>> huwelijkHashMap = new HashMap<String, Map<String, String>>();

        // Vul de map met huwelijken (omslachtig doordat query aparte rijen oplevert voor partner)
        for (Map<String, Object> huwelijk : huwelijkListFromDB) {
            HashMap<String, String> huwelijkMap;

            if (huwelijkHashMap.containsKey(getMapValue(huwelijk, "relatie"))) {
                huwelijkMap = (HashMap<String, String>) huwelijkHashMap.get(getMapValue(huwelijk, "relatie"));
            } else {
                huwelijkMap = new HashMap<String, String>();
            }

            if (!huwelijkMap.containsKey(HUWELIJK_RELATIE_ID) && !getMapValue(huwelijk, "relatie").equals("")) {
                huwelijkMap.put(HUWELIJK_RELATIE_ID, getMapValue(huwelijk, "relatie"));
            }
            if (!huwelijkMap.containsKey(HUWELIJK_PARTNER1) && !getMapValue(huwelijk, "man").equals("")) {
                huwelijkMap.put(HUWELIJK_PARTNER1, getMapValue(huwelijk, "man"));
            }
            if (!huwelijkMap.containsKey(HUWELIJK_PARTNER2) && !getMapValue(huwelijk, "vrouw").equals("")) {
                huwelijkMap.put(HUWELIJK_PARTNER2, getMapValue(huwelijk, "vrouw"));
            }
            if (!huwelijkMap.containsKey(HUWELIJK_NAAM) && !getMapValue(huwelijk, "naam").equals("")) {
                huwelijkMap.put(HUWELIJK_NAAM, getMapValue(huwelijk, "naam"));
            }
            if (!huwelijkMap.containsKey(HUWELIJK_VOORVOEGSEL) && !getMapValue(huwelijk, "voorvoegsel").equals("")) {
                huwelijkMap.put(HUWELIJK_VOORVOEGSEL, getMapValue(huwelijk, "voorvoegsel"));
            }

            huwelijkHashMap.put(huwelijkMap.get(HUWELIJK_RELATIE_ID), huwelijkMap);
        }

        // Vul de lijst met huwelijken
        for (Map<String, String> huwelijkMap : huwelijkHashMap.values()) {
            huwelijkList.add(huwelijkMap);
        }

        LOG.debug("HuwelijkList: " + huwelijkList.size());
        return huwelijkList;
    }

    public static long collectMaxANummer() throws SQLException {
        final QueryRunner queryRunner = new QueryRunner();
        String query = "SELECT MAX(kp.anr) FROM kern.pers kp";
        Connection conn = startConnection();
        Long maxANummer = queryRunner.query(conn, query, new ScalarHandler<Long>());
        endConnection(conn);

        return maxANummer;
    }

    public static int collectMaxBsn() throws SQLException {
        final QueryRunner queryRunner = new QueryRunner();
        String query = "SELECT MAX(kp.bsn) FROM kern.pers kp";
        Connection conn = startConnection();
        Integer maxBsn = queryRunner.query(conn, query, new ScalarHandler<Integer>());
        endConnection(conn);

        return maxBsn;
    }

    /**
     * Start connection.
     *
     * @param dataSource de data source
     * @return de connection
     * @throws SQLException de sQL exception
     */
    private static Connection startConnection() throws SQLException {
        Connection conn = null;
        conn = dataSource.getConnection();
        conn.rollback();
        return conn;
    }

    /**
     * End connection.
     *
     * @param conn de conn
     * @throws SQLException de sQL exception
     */
    private static void endConnection(Connection conn) throws SQLException {
        conn.rollback();
        conn.close();
        conn = null;
    }

    /**
     * Haalt een map value op.
     *
     * @param map de map
     * @param key de key
     * @return map value
     */
    private static String getMapValue(final Map<String, Object> map, final String key) {
        String mapValue = "";
        if (map.get(key) != null) {
            mapValue = "" + map.get(key);
        }
        return mapValue;
    }

}
