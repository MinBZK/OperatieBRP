/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.testdata;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.generators.AnrGenerator;
import nl.bzk.brp.testclient.generators.BSNGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class TestdataServiceImpl.
 */
public class TestdataServiceImpl implements TestdataService {

    private static final Logger                    LOG                  = LoggerFactory
                                                                                .getLogger(TestdataServiceImpl.class);

    public static final String                     HUISNUMMER           = "huisnummer";
    public static final String                     HUISLETTER           = "huisletter";
    public static final String                     HUISNUMMERTOEVOEGING = "huisnrtoevoeging";
    public static final String                     POSTCODE             = "postcode";
    public static final String                     WOONPLAATSCODE       = "wpl";
    public static final String                     GEMEENTECODE         = "gemeentecode";
    public static final String                     LANDCODE             = "landcode";
    public static final String                     NOR                  = "nor";
    public static final String                     HUWELIJK_PARTNER1    = "partner1";
    public static final String                     HUWELIJK_PARTNER2    = "partner2";
    public static final String                     HUWELIJK_NAAM        = "naam";
    public static final String                     HUWELIJK_VOORVOEGSEL = "voorvoegsel";

    private static LinkedList<String>              bsnList              = new LinkedList<String>();
    private static LinkedList<String>              aNummerList          = new LinkedList<String>();
    private static LinkedList<String>              vrijgezelBsnList     = new LinkedList<String>();
    private static LinkedList<String>              nieuweBsnList        = new LinkedList<String>();
    private static LinkedList<String>              nieuweANummerList    = new LinkedList<String>();
    private static LinkedList<Map<String, String>> huwelijkList         = new LinkedList<Map<String, String>>();
    private static LinkedList<Map<String, String>> adresList            = new LinkedList<Map<String, String>>();

    private static int                             AANTAL_NIEUW         = 50000;

    private Integer                                testCount            = 0;

    /**
     * Instantieert een nieuwe testdata service impl.
     *
     * @throws ClassNotFoundException de class not found exception
     * @throws SQLException de sQL exception
     */
    public TestdataServiceImpl() throws ClassNotFoundException, SQLException {
        LOG.info("Verkrijgen testdata uit database ...");
        getTestDataFromDb(null);
        LOG.info("Einde verkrijgen testdata...");
    }

    /**
     * Haalt een test data from db op.
     *
     * @param testdataParam de testdata param
     * @return test data from db
     * @throws SQLException de sQL exception
     * @throws ClassNotFoundException de class not found exception
     */
    private void getTestDataFromDb(final TESTDATA testdataParam) throws SQLException, ClassNotFoundException {
        testCount = TestClient.eigenschappen.getTestCount();
        if (testCount > 0) {
            LOG.info("Minimum aantal voor ophalen testgegevens: " + testCount);
        }

        // Haal een of meerdere soorten testdata op
        if (testdataParam != null) {
            collectTestData(testdataParam);
        } else {
            for (TESTDATA testdata : TESTDATA.values()) {
                LOG.info("Collecting " + testdata);
                collectTestData(testdata);
            }
        }
    }

    /**
     * Collect test data.
     *
     * @param testdata de testdata
     * @param dataSource de data source
     * @throws SQLException de sQL exception
     */
    private void collectTestData(final TESTDATA testdata) throws SQLException {
        switch (testdata) {
            case ADRESSEN:
                adresList = DBCollector.collectAdressen();
                break;
            case ANUMMERS:
                aNummerList = DBCollector.collectANummers();
                break;
            case BSNS:
                bsnList = DBCollector.collectBsns();
                break;
            case HUWELIJKEN:
                huwelijkList = DBCollector.collectHuwelijken();
                break;
            case NIEUWE_ANUMMERS:
                genereerNieuweANummers(DBCollector.collectMaxANummer());
                break;
            case NIEUWE_BSNS:
                genereerNieuweBsns(DBCollector.collectMaxBsn());
                break;
            case VRIJGEZEL_BSNS:
                vrijgezelBsnList = DBCollector.collectVrijgezelBsns();
                break;
            default:
                break;
        }
    }

    /**
     * Genereer nieuwe a nummers.
     *
     * @param dataSource de data source
     * @throws SQLException de sQL exception
     */
    private void genereerNieuweANummers(final long maxANummer) throws SQLException {
        AnrGenerator.anr = maxANummer;

        int aantalNieuw = TestClient.eigenschappen.getTestCount();

        while (nieuweANummerList.size() < aantalNieuw) {
            String anr = "" + AnrGenerator.getNextAnr();

            if (!aNummerList.contains(anr)) {
                nieuweANummerList.add(anr);
                if (((float) nieuweANummerList.size() / (float) aantalNieuw) * 100 % 25 == 0) {
                    LOG.info(((float) nieuweANummerList.size() / (float) aantalNieuw) * 100 + " %");
                }
            }
        }
        LOG.debug("nieuweANummerList: " + nieuweANummerList.size());
    }

    /**
     * Genereer nieuwe bsns.
     *
     * @param dataSource de data source
     * @throws SQLException de sQL exception
     */
    private void genereerNieuweBsns(final int maxBsn) throws SQLException {
        BSNGenerator.bsn = maxBsn;

        int aantalNieuw = TestClient.eigenschappen.getTestCount();

        while (nieuweBsnList.size() < aantalNieuw) {
            String bsn = BSNGenerator.nextBSN();

            if (!bsnList.contains(bsn)) {
                nieuweBsnList.add(bsn);
                if (((float) nieuweBsnList.size() / (float) aantalNieuw) * 100 % 25 == 0) {
                    LOG.info(((float) nieuweBsnList.size() / (float) aantalNieuw) * 100 + " %");
                }
            }
        }
        LOG.debug("nieuweBsnList: " + nieuweBsnList.size());
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getUniekeBsn()
     */
    @Override
    public synchronized String getBsn() throws SQLException, ClassNotFoundException {
        if (bsnList.size() < 1) {
            LOG.info("BSNlijst is leeg, wordt opnieuw opgebouwd.");
            getTestDataFromDb(TESTDATA.BSNS);
        }
        return bsnList.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getUniekeBsn()
     */
    @Override
    public synchronized Map<String, String> getAdres() throws SQLException, ClassNotFoundException {
        if (adresList.size() < 1) {
            LOG.info("Adressenlijst is leeg, wordt opnieuw opgebouwd.");
            getTestDataFromDb(TESTDATA.ADRESSEN);
        }
        return adresList.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getHuwelijkBsn()
     */
    @Override
    public synchronized String getVrijgezelBsn() throws SQLException, ClassNotFoundException {
        if (vrijgezelBsnList.size() < 1) {
            LOG.info("Vrijgezel BSN lijst is leeg, wordt opnieuw opgebouwd.");
            getTestDataFromDb(TESTDATA.VRIJGEZEL_BSNS);
        }
        return vrijgezelBsnList.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getNieuweBsn()
     */
    @Override
    public synchronized String getNieuweBsn() throws SQLException, ClassNotFoundException {
        if (nieuweBsnList.size() < 1) {
            LOG.info("Nieuwe BSN lijst is leeg, wordt opnieuw opgebouwd.");
            getTestDataFromDb(TESTDATA.NIEUWE_BSNS);
        }
        return nieuweBsnList.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getNieuwANummer()
     */
    @Override
    public synchronized String getNieuwANummer() throws SQLException, ClassNotFoundException {
        if (nieuweANummerList.size() < 1) {
            LOG.info("Nieuwe BSN lijst is leeg, wordt opnieuw opgebouwd");
            getTestDataFromDb(TESTDATA.NIEUWE_ANUMMERS);
        }
        return nieuweANummerList.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getHuwelijk()
     */
    @Override
    public synchronized Map<String, String> getHuwelijk() throws SQLException, ClassNotFoundException {
        if (huwelijkList.size() < 1) {
            LOG.info("Huwelijklijst is leeg, wordt opnieuw opgebouwd");
            getTestDataFromDb(TESTDATA.HUWELIJKEN);
        }
        return huwelijkList.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getBsnListSize()
     */
    @Override
    public int getBsnListSize() {
        return bsnList.size();
    }
}
