/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.testdata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Constanten;
import nl.bzk.brp.testclient.misc.RandomUtil;
import nl.bzk.brp.testclient.util.DatumUtil;

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


/**
 * Database collector.
 */
public final class DBCollector {

    /** De Constante LOG. */
    private static final Logger LOG                      = LoggerFactory.getLogger(DBCollector.class);
    private static final int    MAX_QUERY_RETRY          = 25;
    private static final double TESTDATA_FACTOR          = 1.5;

    private static DataSource   dataSource               = setupDataSource("jdbc:postgresql://"
                                                             + TestClient.eigenschappen.getDbHost() + ":"
                                                             + TestClient.eigenschappen.getDbPort() + "/"
                                                             + TestClient.eigenschappen.getDbName());
    private static int[] artPersIds = null;

    private static final String SQL_GELDIGE_NL_ADRES =
            "SELECT pa.id, pa.pers, p.bsn, pa.afgekortenor nor "
                    + " , pa.huisnr as huisnummer, pa.huisletter as huisletter"
                    + " , pa.huisnrtoevoeging as huisnrtoevoeging, pa.postcode as postcode"
                    + " , pa.identcodeadresseerbaarobject, pa.identcodenraand "
                    + " , g.code as gemeentecode, w.naam as wpl, l.code as landcode "
                    + " FROM kern.persadres pa, kern.pers p, kern.gem g, kern.plaats w, kern.landgebied l "
                    + " WHERE "
                    + " pa.pers = p.id "
                    + " AND pa.gem = g.id "
                    + " AND pa.wplnaam = w.naam "
                    + " AND pa.landgebied = l.id "
                    + " AND p.naderebijhaard = 1 "
                    + " AND pa.afgekortenor is not null "
                    + " AND pa.huisnr is not null"
                    + " AND g.dateindegel is null"
                    + " AND w.dateindegel is null";

    /**
     * Standaard lege, private constructor zodat deze utility klasse niet geinstantieerd kan worden.
     */
    private DBCollector() {
    }

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
        return new PoolingDataSource(connectionPool);
    }

    /**
     * Lees art pers ids.
     *
     * @throws SQLException sQL exception
     */
    private static void leesArtPersIds() throws SQLException {
        if (artPersIds == null) {
            final QueryRunner queryRunner = new QueryRunner();
            Connection conn = startConnectie();

            String queryCountId = "SELECT COUNT(kp.id) FROM kern.pers kp";
            List<Object[]> idsArrayList =
                    queryRunner.query(conn, "select id from helper.brpPers", new ArrayListHandler());
            artPersIds = new int[idsArrayList.size()];
            for (int i = 0; i < idsArrayList.size(); i++) {
                artPersIds[i] = ((Integer) idsArrayList.get(i)[0]).intValue();
            }
            beeindigConnectie(conn);
        }
    }

    /**
     * Verkrijgt random pers id from art data.
     *
     * @return random pers id from art data
     */
    private static int getRandomPersIdFromArtData() {
        // we hoeven geen offset te gebruiken, kunnen meteen random inprikken (aka. minId == 0)
        return artPersIds[(int) RandomUtil.nextLong(artPersIds.length)];
    }

    /**
     * Verzamel adressen.
     *
     * @return linked list
     * @throws SQLException sQL exception
     */
    public static LinkedList<Map<String, String>> collectAdressen() throws SQLException {
        LinkedList<Map<String, String>> adresList = new LinkedList<Map<String, String>>();
        leesArtPersIds();

        final QueryRunner queryRunner = new QueryRunner();

        Connection conn;

        int queryCount = 0;

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (adresList.size() < TestClient.eigenschappen.getTestCount() && queryCount < MAX_QUERY_RETRY) {

            StringBuilder querySB = new StringBuilder(SQL_GELDIGE_NL_ADRES).append(" AND p.id in (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * TESTDATA_FACTOR); i++) {
                querySB.append(getRandomPersIdFromArtData()).append(",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) \r\n");

            conn = startConnectie();
            List<Map<String, Object>> adresListFromDB = queryRunner.query(conn, querySB.toString(),
                new MapListHandler());
            beeindigConnectie(conn);

            for (Map<String, Object> adres : adresListFromDB) {
                HashMap<String, String> adresMap = new HashMap<String, String>();

                if (!getMapValue(adres, TestdataServiceImpl.GEMEENTECODE).equals("")) {
                    String gemeentecode =
                        String.format("%04d", Integer.parseInt(getMapValue(adres, TestdataServiceImpl.GEMEENTECODE)));
                    adresMap.put(TestdataServiceImpl.GEMEENTECODE, gemeentecode);
                }
                if (!getMapValue(adres, TestdataServiceImpl.LANDCODE).equals("")) {
                    adresMap.put(TestdataServiceImpl.LANDCODE, getMapValue(adres, TestdataServiceImpl.LANDCODE));
                }
                if (!getMapValue(adres, TestdataServiceImpl.WOONPLAATSNAAM).equals("")) {
                    adresMap.put(TestdataServiceImpl.WOONPLAATSNAAM, getMapValue(adres,
                        TestdataServiceImpl.WOONPLAATSNAAM));
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
                adresMap.put("afgekortenor", getMapValue(adres, "afgekortenor"));
                adresMap.put("adresseerbaarobject", getMapValue(adres, "adresseerbaarobject"));
                adresMap.put("identcodenraand", getMapValue(adres, "identcodenraand"));
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
     * Collect bsns.
     *
     * @return lijst met bsn's
     * @throws SQLException de sQL exception
     */
    public static Map.Entry<LinkedList<String>, Map<Long, Long>> collectBsns() throws SQLException {
        LinkedList<String> bsnList = new LinkedList<String>();
        Map<Long, Long> idList = new HashMap<Long, Long>();
        leesArtPersIds();

        final QueryRunner queryRunner = new QueryRunner();

        Connection conn = null;
        int queryCount = 0;

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (bsnList.size() < TestClient.eigenschappen.getTestCount() && queryCount < MAX_QUERY_RETRY) {
            StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT kp.bsn, kp.id FROM kern.pers kp, kern.persadres pa WHERE kp.id IN (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * TESTDATA_FACTOR); i++) {
                querySB.append(getRandomPersIdFromArtData()).append(",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) AND kp.bijhaard = 1 ")
                .append("AND kp.bsn IS NOT NULL ")
                .append("AND kp.srt=1 ")
                .append("AND pa.pers=kp.id ")
                .append("AND kp.naderebijhaard = 1;");
            conn = startConnectie();
            List<Object[]> bsnArrayList = queryRunner.query(conn, querySB.toString(), new ArrayListHandler());

            beeindigConnectie(conn);
            for (Object[] bsnArray : bsnArrayList) {
                if (!bsnList.contains("" + bsnArray[0])) {
                    bsnList.add("" + bsnArray[0]);
                    idList.put(Long.valueOf("" + bsnArray[0]), Long.valueOf("" + bsnArray[1]));
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
        LOG.debug("IdList: " + idList.size());
        
        return new AbstractMap.SimpleImmutableEntry<LinkedList<String>, Map<Long, Long>>(bsnList, idList);
    }
    

    /**
     * Collect vrijgezel bsns.
     *
     * @return linked list
     * @throws SQLException de sQL exception
     */
    public static LinkedList<BsnPers> collectVrijgezelBsns() throws SQLException {
        final LinkedList<BsnPers> vrijgezelBsnList = new LinkedList<BsnPers>();
        leesArtPersIds();

        final QueryRunner queryRunner = new QueryRunner();

        Connection conn;
        int queryCount = 0;

        final Date nuDate = new Date();
        final String nu = DatumUtil.datumNaarBrpString(nuDate);
        final String datumMinimaal18 = "" + (Integer.valueOf(nu) - Constanten.HONDERTACHTIGDUIZEND);

        // Vul lijst zo vol mogelijk naar testcount via max # queries
        while (vrijgezelBsnList.size() < TestClient.eigenschappen.getTestCount()
                && queryCount < (MAX_QUERY_RETRY * Constanten.DRIE))
        {
            final StringBuilder querySB = new StringBuilder();
            querySB.append("SELECT kp.bsn, kp.id FROM kern.pers kp WHERE kp.id IN (");
            for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * TESTDATA_FACTOR); i++) {
                querySB.append(getRandomPersIdFromArtData()).append(",");
            }
            // Afronden met 0 om laatste komma op te heffen
            querySB.append("0) ")
                   .append(" AND kp.bsn IS NOT NULL AND kp.naderebijhaard = 1")
                   .append(" AND kp.datgeboorte < ").append(datumMinimaal18)
                   .append(" AND NOT EXISTS (SELECT kb.pers FROM kern.betr kb WHERE kb.rol = 3 AND kb.pers = kp.id )")
                   .append(" AND NOT EXISTS (")
                   .append("SELECT ki.pers FROM kern.persindicatie ki WHERE ki.srt=2 AND ki.pers = kp.id)");

            conn = startConnectie();
            final List<Object[]> vrijgezelBsnArrayList = queryRunner.query(conn, querySB.toString(), new ArrayListHandler());

            beeindigConnectie(conn);
            for (final Object[] vrijgezelBsnArray : vrijgezelBsnArrayList) {
                if (!vrijgezelBsnList.contains(String.valueOf(vrijgezelBsnArray[0]))) {
                    
                    Long bsn = Long.valueOf("" + vrijgezelBsnArray[0]);
                    String persId = "" + vrijgezelBsnArray[1];
                    
                    vrijgezelBsnList.add(new BsnPers(bsn, persId));
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
     * Haalt huwelijken of geregistreerdpartnerschappen op.
     *
     * @param soortRelatie soort relatie 1=Huwelijk, 2=Geregistreerd partnerschap.
     * @return Lijst met huwelijken en/of partnerschappen, waar middels een {@link Map} zogenaamde 'key-value' pairs
     *     met waardes van het huwelijk/partnerschap worden geretourneerd.
     * @throws SQLException de sQL exception
     */

    public static LinkedList<Map<String, String>> collectHuwelijkGeregistreerdPartnerschappen(
            final HPG_SOORT soortRelatie) throws SQLException
    {
        final LinkedList<Map<String, String>> hpgs = new LinkedList<Map<String, String>>();
        final QueryRunner queryRunner = new QueryRunner();
        final StringBuilder queryBuilder = new StringBuilder();

        // Onderstaande query geeft af een toe geen man mee terug

        queryBuilder
                .append("SELECT h.id AS %1$s \n"
                                + ", MAN.id AS %12$s \n"
                                + ", MAN.bsn AS %2$s \n"
                                + ", MAN.geslnaamstam AS %3$s \n"
                                + ", MAN.voorvoegsel AS %4$s \n"
                                + ", MAN.scheidingsteken AS %5$s \n"
                                + ", VROUW.id as %13$s \n"
                                + ", VROUW.bsn AS %6$s \n"
                                + ", VROUW.geslnaamstam AS %7$s \n"
                                + ", VROUW.voorvoegsel AS %8$s \n"
                                + ", VROUW.scheidingsteken AS %9$s \n"
                                + ", false AS %10$s \n"
                                + "\n"
                                + "FROM helper.brprelatie br \n"
                                + ", kern.relatie h \n"
                                + "\n"
                                + "LEFT OUTER JOIN \n"
                                + "( SELECT m.id \n"
                                + ", m.bsn \n"
                                + ", m.geslnaamstam \n"
                                + ", m.voorvoegsel \n"
                                + ", m.scheidingsteken \n"
                                + ", bm.relatie AS bm_relatie \n"
                                + "FROM helper.brppers bpm \n"
                                + ", kern.pers m \n"
                                + ", kern.betr bm \n"
                                + "WHERE bm.pers = m.id \n"
                                + "AND m.id = bpm.id \n"
                                + "AND m.geslachtsaand = 1 \n"
                                + "AND m.srt = 1 \n"
                                + ") MAN \n"
                                + "ON h.id = MAN.bm_relatie \n"
                                + "\n"
                                + "LEFT OUTER JOIN \n"
                                + "( SELECT v.id -- AS vId \n"
                                + ", v.bsn -- AS vbsn \n"
                                + ", v.geslnaamstam -- AS vnaam \n"
                                + ", v.voorvoegsel -- AS vvoorvoegsel \n"
                                + ", v.scheidingsteken -- AS vscheidingsteken \n"
                                + ", bv.relatie AS vm_relatie \n"
                                + "FROM helper.brppers bpv \n"
                                + ", kern.pers v \n"
                                + ", kern.betr bv \n"
                                + ", kern.persadres vAdres \n"
                                + "WHERE bv.pers = v.id \n"
                                + "AND v.id = bpv.id \n"
                                + "AND v.geslachtsaand = 2 \n"
                                + "AND v.srt = 1 \n"
                                + "AND vAdres.pers = v.id \n"
                                + "AND vAdres.srt = 1 \n"
                                + ") VROUW \n"
                                + "ON h.id = vrouw.vm_relatie \n"
                                + "\n"
                                + "WHERE h.srt = %11$s \n"
                                + "AND h.id = br.id \n"
                                + "AND br.logischrelatieid NOT IN (");

        for (final Integer verwantschapRelatieId : Constanten.VERWANTSCHAP_LOGISCHE_RELATIE_IDS) {
            queryBuilder.append(verwantschapRelatieId + ", ");
        }

        queryBuilder.append("0) "
                                + "AND NOT(MAN = VROUW \n)"
                                + "AND h.dateinde IS NULL \n"
                                + "LIMIT " + TestClient.eigenschappen.getTestCount());

        final String query = String.format(queryBuilder.toString(), TestdataServiceImpl.HGP_RELATIE,
            TestdataServiceImpl.HGP_MAN_BSN, TestdataServiceImpl.HGP_MAN_NAAM,
            TestdataServiceImpl.HGP_MAN_VOORVOEGSEL, TestdataServiceImpl.HGP_MAN_SCHEIDINGSTEKEN,
            TestdataServiceImpl.HGP_VROUW_BSN, TestdataServiceImpl.HGP_VROUW_NAAM,
            TestdataServiceImpl.HGP_VROUW_VOORVOEGSEL, TestdataServiceImpl.HGP_VROUW_SCHEIDINGSTEKEN,
            TestdataServiceImpl.HGP_MET_KINDEREN_MET_ANDERE_NAAM,
            soortRelatie.rolId, TestdataServiceImpl.HGP_MAN_ID, TestdataServiceImpl.HGP_VROUW_ID);

        final String hgpMetAndereNaamSQL = new StringBuilder()
                .append("SELECT COUNT(pp.geslnaamstam) > 0 as JaNee "
                                + " FROM kern.relatie rr "
                                + " JOIN kern.betr bb ON (bb.relatie = rr.id) "
                                + " JOIN kern.pers pp ON (pp.id = bb.pers)  "
                                + " WHERE rr.id IN "
                                + " (SELECT r.id "
                                + " FROM kern.relatie r "
                                + " JOIN kern.betr b ON (b.relatie = r.id) "
                                + " JOIN kern.pers p ON (b.pers = p.id) "
                                + " WHERE r.srt = 3 "
                                + " AND b.rol = 2 "
                                + " AND p.id = %1$s ) "
                                + " AND bb.rol = 1 "
                                + " AND (pp.geslnaamstam <>  "
                                + " (SELECT p.geslnaamstam "
                                + " FROM kern.pers p "
                                + " WHERE p.id = %1$s)"
				+ " OR (pp.voorvoegsel <>  "
				+ " (SELECT p.voorvoegsel "
				+ " FROM kern.pers p "
				+ " WHERE p.id = %1$s))"
				+ " OR (pp.scheidingsteken <>  "
				+ " (SELECT p.scheidingsteken "
				+ " FROM kern.pers p "
				+ " WHERE p.id = %1$s)))"
		).toString();

        final Connection conn = startConnectie();
        final List<Map<String, Object>> huwelijkListFromDB = queryRunner.query(conn, query, new MapListHandler());

        LOG.debug("Huwelijken/Partnerschappen opgehaald: " + huwelijkListFromDB.size());

        LOG.debug("Nu bepalen welke huwelijken/partnerschappen kinderen hebben met afwijkende geslachtsnamen...");

        int tellerHuwelijken = 0;
        // verrijk nu elk huwelijk met de vlag HGP_MET_KINDEREN_MET_ANDERE_NAAM
        for (Map<String, Object> row : huwelijkListFromDB) {
            final Integer vadersId = (Integer) row.get(TestdataServiceImpl.HGP_MAN_ID);
            final String queryHgp = String.format(hgpMetAndereNaamSQL, vadersId);
            final List<Map<String, Object>> flagRow = queryRunner.query(conn, queryHgp, new MapListHandler());

            row.put(TestdataServiceImpl.HGP_MET_KINDEREN_MET_ANDERE_NAAM, flagRow.get(0).get("JaNee"));
            tellerHuwelijken++;

            if (Long.valueOf(tellerHuwelijken) % Constanten.DUIZEND == 0) {
                LOG.debug("Huwelijken/Partnerschappen geanalyseerd: " + tellerHuwelijken);
            }
        }

        beeindigConnectie(conn);

        // Vul de lijst met huwelijken
        for (Map<String, Object> huwelijkMap : huwelijkListFromDB) {
            final Map<String, String> tussenMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : huwelijkMap.entrySet()) {
                if (entry.getValue() == null) {
                    tussenMap.put(entry.getKey(), null);
                } else {
                    tussenMap.put(entry.getKey(), entry.getValue().toString());
                }
            }
            hpgs.add(tussenMap);
        }

        LOG.debug("HGPLijst: " + hpgs.size());
        return hpgs;
    }

    /**
     * Haalt de relaties op met een kind dat geadopteerd kan worden.
     *
     * @return lijst met map met daarin relatieId en kind
     * @throws SQLException SQL exception
     */
    public static LinkedList<Map<String, String>> collectAdoptieKinderen() throws SQLException {

        final QueryRunner queryRunner = new QueryRunner();
        final Connection conn = startConnectie();

        final StringBuilder queryBuilder = new StringBuilder();

        final String datumNuString = DatumUtil.datumNaarBrpString(new Date());
        final Long datum18Long = Long.valueOf(datumNuString) - Constanten.HONDERTACHTIGDUIZEND;

        queryBuilder.append("SELECT r.id as %1$s, p.bsn as %2$s, b.id as %3$s "
                                    + "FROM kern.relatie r "
                                    + "JOIN kern.betr b ON (b.relatie = r.id) "
                                    + "JOIN kern.pers p ON (b.pers = p.id) "
                                    + "WHERE r.srt = 3 "
                                    + "AND b.rol = 1 "
                                    + "AND p.srt = 1 "
                                    + "AND p.bijhaard = 1 "
                                    + "AND p.naderebijhaard = 1 "
                                    + "AND p.datgeboorte > %4$s "
                                    + "AND r.id IS NOT NULL "
                                    + "AND p.bsn IS NOT NULL "

                // De ART testdata bevat te weinig personen die voldoen. (<18 en kind in familierechtelijke betrekking)
//                                    + "AND p.id IN (");
//
//        for (int i = 0; i < (TestClient.eigenschappen.getTestCount() * TESTDATA_FACTOR); i++) {
//            queryBuilder.append(getRandomPersIdFromArtData()).append(",");
//        }
//
//        // Afronden met 0 om laatste komma op te heffen
//        queryBuilder.append("0) "
                                    + "GROUP BY p.bsn, r.id, b.id "
                                    + "LIMIT %4$s");

        final String query = String.format(queryBuilder.toString(),
                                           TestdataServiceImpl.ADOPTIE_RELATIE_ID,
                                           TestdataServiceImpl.ADOPTIE_KIND_BSN,
                                           TestdataServiceImpl.ADOPTIE_KIND_BETR_ID,
                                           datum18Long,
                                           TestClient.eigenschappen.getTestCount());

        final List<Map<String, Object>> lijstKindRelaties =
                queryRunner.query(conn, query, new MapListHandler());

        final LinkedList<Map<String, String>> kindRelaties = new LinkedList<>();

        for (final Map<String, Object> kindRelatie : lijstKindRelaties) {
            final Map<String, String> tussenmap = new HashMap<>();
            for (final String key : kindRelatie.keySet()) {
                tussenmap.put(key, kindRelatie.get(key).toString());
            }

            // Voeg oorspronkelijke ouders toe aan map
            final StringBuilder queryBuilderEchteOuders = new StringBuilder();
            queryBuilderEchteOuders.append("select p.bsn\n"
                                                   + "from kern.pers p\n"
                                                   + "join kern.betr b on (b.pers = p.id) \n"
                                                   + "join kern.relatie r on (r.id = b.relatie)\n"
                                                   + "where b.rol = 2 and r.srt = 3 and r.id = %1$s \n"
                                                   + "and p.bsn is not null");

            final String queryEchteOuders = String.format(queryBuilderEchteOuders.toString(),
                                                          kindRelatie.get(TestdataServiceImpl.ADOPTIE_RELATIE_ID));

            final List<Object[]> echteOudersArrayList = queryRunner.query(conn, queryEchteOuders,
                                                                    new ArrayListHandler());
            if (echteOudersArrayList.size() > 0 && echteOudersArrayList.get(0) != null) {
                tussenmap.put("ECHTE_OUDER_1", echteOudersArrayList.get(0)[0].toString());
            }
            if (echteOudersArrayList.size() > 1 && echteOudersArrayList.get(1) != null) {
                tussenmap.put("ECHTE_OUDER_2", echteOudersArrayList.get(1)[0].toString());
            }
            // Einde toevoeging echte ouders

            kindRelaties.add(tussenmap);
        }

        LOG.debug("Adoptielijst: " + kindRelaties.size());

        return kindRelaties;
    }

    /**
     * Geeft het hoogste ANummer.
     *
     * @return ANummer als long
     * @throws SQLException SQL exception
     */
    public static long collectMaxANummer() throws SQLException {
        final QueryRunner queryRunner = new QueryRunner();
        String query = "SELECT MAX(kp.anr) FROM kern.pers kp";
        Connection conn = startConnectie();
        Long maxANummer = queryRunner.query(conn, query, new ScalarHandler<Long>());
        beeindigConnectie(conn);

        return maxANummer;
    }

    /**
     * Geeft het hoogswte BSN nummer.
     *
     * @return BSN als int
     * @throws SQLException SQL exception
     */
    public static int collectMaxBsn() throws SQLException {
        final QueryRunner queryRunner = new QueryRunner();
        String query = "SELECT MAX(kp.bsn) FROM kern.pers kp";
        Connection conn = startConnectie();
        Integer maxBsn = queryRunner.query(conn, query, new ScalarHandler<Integer>());
        beeindigConnectie(conn);

        return maxBsn;
    }

    /**
     * Start de connectie.
     *
     * @return de connection
     * @throws SQLException de sQL exception
     */
    private static Connection startConnectie() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.rollback();
        return conn;
    }

    /**
     * Beeindig de connectie.
     *
     * @param conn de connectie
     * @throws SQLException SQL exception
     */
    private static void beeindigConnectie(final Connection conn) throws SQLException {
        conn.rollback();
        conn.close();
    }

    /**
     * Haalt een waarde uit de map op.
     *
     * @param map de map
     * @param key de key
     * @return map waarde als de key bestaat, anders een lege string
     */
    private static String getMapValue(final Map<String, Object> map, final String key) {
        String mapValue = "";
        if (map.get(key) != null) {
            mapValue = "" + map.get(key);
        }
        return mapValue;
    }

    /**
     * Lokale enumeratie om de soort van de relatie mee te kunnen geven aan de
     * {@link #collectHuwelijkGeregistreerdPartnerschappen(nl.bzk.brp.testclient.testdata.DBCollector.HPG_SOORT)}
     * methode.
     */
    public static enum HPG_SOORT {

        /** HPG Soort voor Huwelijk. */
        HUWELIJK(1),
        /** HPG Soort voor Geregistreerd partnerschap. */
        GEREGISTREERD_PARTNERSCHAP(2);

        private final int rolId;

        /**
         * Standaard private constructor voor de enumeratie die de id van de rol zet. Dit is de id die in de database
         * voor deze rol wordt gebruikt.
         *
         * @param rolId de id van de rol.
         */
        private HPG_SOORT(final int rolId) {
            this.rolId = rolId;
        }
    }
}
