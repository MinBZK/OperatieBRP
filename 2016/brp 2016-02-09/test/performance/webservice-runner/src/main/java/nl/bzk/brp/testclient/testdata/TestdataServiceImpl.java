/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.testdata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.generators.AnrGenerator;
import nl.bzk.brp.testclient.generators.BSNGenerator;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.util.ObjectSleutelHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class TestdataServiceImpl.
 */
public class TestdataServiceImpl implements TestdataService {

    private static final Logger LOG = LoggerFactory.getLogger(TestdataServiceImpl.class);
    private static final int HONDERD_PROCENT = 100;
    private static final int RAPPORTAGE_PERCENTAGE = 25;

    /**
     * Naam van huisnummer veld in test data resultaat.
     */
    public static final String HUISNUMMER = "huisnummer";
    /**
     * Naam van huisletter veld in test data resultaat.
     */
    public static final String HUISLETTER = "huisletter";
    /**
     * Naam van huisnummertoevoeging veld in test data resultaat.
     */
    public static final String HUISNUMMERTOEVOEGING = "huisnrtoevoeging";
    /**
     * Naam van postcode veld in test data resultaat.
     */
    public static final String POSTCODE = "postcode";
    /**
     * Naam van woonplaatscode veld in test data resultaat.
     */
    public static final String WOONPLAATSNAAM = "wpl";
    /**
     * Naam van gemeentecode veld in test data resultaat.
     */
    public static final String GEMEENTECODE = "gemeentecode";
    /**
     * Naam van landcode veld in test data resultaat.
     */
    public static final String LANDCODE = "landcode";
    /**
     * Naam van naam openbare ruimte veld in test data resultaat.
     */
    public static final String NOR = "nor";
    /**
     * Naam van huwelijk/relatie id veld in test data resultaat.
     */
    public static final String HGP_RELATIE = "relatie";
    /**
     * Identifier van man.
     */
    public static final Object HGP_MAN_ID = "mid";
    /**
     * Naam van BSN veld in test data resultaat voor de man uit een huwelijk of geregistreerdpartnerschap.
     */
    public static final String HGP_MAN_BSN = "mbsn";
    /**
     * Naam van naam veld in test data resultaat voor de man uit een huwelijk of geregistreerdpartnerschap.
     */
    public static final String HGP_MAN_NAAM = "mnaam";
    /**
     * Naam van voorvoegsel veld in test data resultaat voor de man uit een huwelijk of geregistreerdpartnerschap.
     */
    public static final String HGP_MAN_VOORVOEGSEL = "mvoorvoegsel";
    /**
     * Naam van scheidingsteken veld in test data resultaat voor de man uit een huwelijk of
     * geregistreerdpartnerschap.
     */
    public static final String HGP_MAN_SCHEIDINGSTEKEN = "mscheidingsteken";
    /**
     * Naam van BSN veld in test data resultaat voor de vrouw uit een huwelijk of geregistreerdpartnerschap.
     */
    public static final String HGP_VROUW_BSN = "vbsn";
    /**
     * Identifier van vrouw.
     */
    public static final Object HGP_VROUW_ID = "vid";
    /**
     * Naam van naam veld in test data resultaat voor de vrouw uit een huwelijk of geregistreerdpartnerschap.
     */
    public static final String HGP_VROUW_NAAM = "vnaam";
    /**
     * Naam van voorvoegsel veld in test data resultaat voor de vrouw uit een huwelijk of geregistreerdpartnerschap.
     */
    public static final String HGP_VROUW_VOORVOEGSEL = "vvoorvoegsel";
    /**
     * Naam van scheidingsteken veld in test data resultaat voor de vrouw uit een huwelijk of
     * geregistreerdpartnerschap.
     */
    public static final String HGP_VROUW_SCHEIDINGSTEKEN = "vscheidingsteken";
    /**
     * Naam van veld in test data resultaat dat aangeeft of er kinderen in een huwelijk of geregistreerdpartnerschap
     * zijn met een andere naam dan die van de vader.
     */
    public static final String HGP_MET_KINDEREN_MET_ANDERE_NAAM = "metkinderenmetanderenaam";

    /**
     * De identifier van de relatie.
     */
    public static final String ADOPTIE_RELATIE_ID = "idrelatie";

    /**
     * De BSN van het kind.
     */
    public static final String ADOPTIE_KIND_BSN = "bsnkind";

    /**
     * De identifier van de betrokkenheid van het kind.
     */
    public static final Object ADOPTIE_KIND_BETR_ID = "betrkind";


    private static LinkedList<String> bsnLijst = new LinkedList<>();
    private static Map<Long, Long> bsn2perIdLijst = new HashMap<>();
    private static LinkedList<String> aNummerLijst = new LinkedList<>();
    private static LinkedList<BsnPers> vrijgezelBsnLijst = new LinkedList<>();
    private static LinkedList<String> nieuweBsnLijst = new LinkedList<>();
    private static LinkedList<String> nieuweANummerLijst = new LinkedList<>();
    private static LinkedList<Map<String, String>> huwelijkLijst = new LinkedList<>();
    private static LinkedList<Map<String, String>> geregistreerdPartnerschapLijst = new LinkedList<>();
    private static LinkedList<Map<String, String>> adresLijst = new LinkedList<>();
    private static LinkedList<Map<String, String>> adoptieLijst = new LinkedList<>();

    /**
     * Instantieert een nieuwe testdata service impl.
     *
     * @param eigenschappen eigenschappen
     * @throws ClassNotFoundException class not found exception
     * @throws SQLException SQL exception
     */
    public TestdataServiceImpl(final Eigenschappen eigenschappen) throws ClassNotFoundException, SQLException {
        LOG.info("Verkrijgen testdata uit database ...");

        String flow = eigenschappen.getFlowInhoud();
        Bericht.BERICHT soortBericht = Bericht.getBericht(flow);
        Bericht.TYPE soortType = Bericht.getType(flow);

        if (soortBericht != null && soortBericht.getType().equals(Bericht.TYPE.BEVRAGING)
                || soortType != null && soortType.equals(Bericht.TYPE.BEVRAGING))
        {
            getTestDataFromDb(TESTDATA.getBevragingData());
        } else {
            getTestDataFromDb(TESTDATA.values());
        }

        LOG.info("Einde verkrijgen testdata...");
    }

    /**
     * Haalt een test data from db op.
     *
     * @param testdataParam de testdata param
     * @throws SQLException           de sQL exception
     * @throws ClassNotFoundException de class not found exception
     */
    private void getTestDataFromDb(final TESTDATA... testdataParam) throws SQLException, ClassNotFoundException {
        int testCount = TestClient.eigenschappen.getTestCount();
        if (testCount > 0) {
            LOG.debug("Minimum aantal voor ophalen testgegevens: " + testCount);
        }

        for (TESTDATA testdata : testdataParam) {
            LOG.info("Collecting " + testdata);
            collectTestData(testdata);
        }
    }

    /**
     * Collect test data.
     *
     * @param testdata de testdata
     * @throws SQLException de sQL exception
     */
    private void collectTestData(final TESTDATA testdata) throws SQLException {
        switch (testdata) {
            case ADRESSEN:
                adresLijst = DBCollector.collectAdressen();
                break;
            case BSNS:
                Map.Entry<LinkedList<String>, Map<Long, Long>> entry = DBCollector.collectBsns(); 
                bsnLijst = entry.getKey();
                bsn2perIdLijst = entry.getValue();
                break;
            case HUWELIJKEN:
                huwelijkLijst = DBCollector
                        .collectHuwelijkGeregistreerdPartnerschappen(DBCollector.HPG_SOORT.HUWELIJK);
                break;
            case NIEUWE_ANUMMERS:
                genereerNieuweANummers(DBCollector.collectMaxANummer());
                break;
            case NIEUWE_BSNS:
                genereerNieuweBsns(DBCollector.collectMaxBsn());
                break;
            case VRIJGEZEL_BSNS:
                vrijgezelBsnLijst = DBCollector.collectVrijgezelBsns();
                break;
            case GEREGISTREERD_PARTNERSCHAPPEN:
                geregistreerdPartnerschapLijst = DBCollector
                        .collectHuwelijkGeregistreerdPartnerschappen(DBCollector.HPG_SOORT.GEREGISTREERD_PARTNERSCHAP);
                break;
//            case ADOPTIE_KINDEREN:
//                adoptieLijst = DBCollector.collectAdoptieKinderen();
            default:
                break;
        }
    }

    /**
     * Berekent het percentage waarop je bent met het huidige aantal uitgezet tegen het totaal aantal.
     *
     * @param huidig het huidige aantal.
     * @param totaal het totaal aantal (dus 100%).
     * @return het percentage.
     */
    private float berekentPercentage(final int huidig, final int totaal) {
        return (float) huidig / (float) totaal * HONDERD_PROCENT;
    }

    /**
     * Genereer nieuwe a nummers.
     *
     * @param maxANummer het maximale A Nummer.
     * @throws SQLException de sQL exception
     */
    private void genereerNieuweANummers(final long maxANummer) throws SQLException {
        AnrGenerator.setAnr(maxANummer);

        int aantalNieuw = TestClient.eigenschappen.getTestCount();

        while (nieuweANummerLijst.size() < aantalNieuw) {
            final String anr = "" + AnrGenerator.getVolgendeAnr();

            if (!aNummerLijst.contains(anr) && anr.length() == 10) {
                nieuweANummerLijst.add(anr);
                if (berekentPercentage(nieuweANummerLijst.size(), aantalNieuw) % RAPPORTAGE_PERCENTAGE == 0) {
                    LOG.info(berekentPercentage(nieuweANummerLijst.size(), aantalNieuw) + " %");
                }
            } else {
		// buiten bereik van anummers
		break;
	    }
        }
        LOG.debug("nieuweANummerLijst: " + nieuweANummerLijst.size()
			  + " (mocht dit niet gevuld worden, dan is de max van het anr bereikt: >10 cijfers)");
    }

    /**
     * Genereer nieuwe bsns.
     *
     * @param maxBsn het maximale BSN.
     * @throws SQLException de sQL exception
     */
    private void genereerNieuweBsns(final int maxBsn) throws SQLException {
        BSNGenerator.setBsn(maxBsn);

        int aantalNieuw = TestClient.eigenschappen.getTestCount();

        while (nieuweBsnLijst.size() < aantalNieuw) {
            String bsn = BSNGenerator.volgendeBSN();

            if (!bsnLijst.contains(bsn)) {
                nieuweBsnLijst.add(bsn);
                if (berekentPercentage(nieuweBsnLijst.size(), aantalNieuw) % RAPPORTAGE_PERCENTAGE == 0) {
                    LOG.info(berekentPercentage(nieuweBsnLijst.size(), aantalNieuw) + " %");
                }
            }
        }
        LOG.debug("nieuweBsnLijst: " + nieuweBsnLijst.size());
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getUniekeBsn()
     */
    @Override
    public synchronized String getBsn() throws SQLException, ClassNotFoundException {
        if (bsnLijst.isEmpty()) {
            throw new IllegalArgumentException("BSNlijst is leeg");
        }
        if (bsnLijst.isEmpty()) {
            return null;
        } else {
            return bsnLijst.removeFirst();
        }
    }

    @Override
    public Long getPersIdByBsn(Long bsn) {
        if (bsn == null) {
            throw new IllegalArgumentException("bsn can not be null");
        }
        Long persId = bsn2perIdLijst.get(bsn);
        

        if (persId == null) {
            throw new IllegalArgumentException("persid for bsn " + bsn + " not found");
        }
        
        return persId;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getUniekeBsn()
     */
    @Override
    public synchronized Map<String, String> getAdres() throws SQLException, ClassNotFoundException {
        if (adresLijst.isEmpty()) {
            throw new IllegalArgumentException("Adressenlijst is leeg");
        }
        return adresLijst.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getHuwelijkBsn()
     */
    @Override
    public synchronized BsnPers getVrijgezelBsn() throws SQLException, ClassNotFoundException {
        if (vrijgezelBsnLijst.isEmpty()) {
            throw new IllegalArgumentException("Vrijgezel BSN lijst is leeg");
        }
        BsnPers value = vrijgezelBsnLijst.removeFirst();
        
        String cryptedPersId = ObjectSleutelHandler.genereerObjectSleutelString(Long.valueOf(value.persId), Integer.valueOf("101"), System.currentTimeMillis());
        
        return new BsnPers(value.bsn, cryptedPersId);
        
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getNieuweBsn()
     */
    @Override
    public synchronized String getNieuweBsn() throws SQLException, ClassNotFoundException {
        if (nieuweBsnLijst.isEmpty()) {
            throw new IllegalArgumentException("Nieuwe BSN lijst is leeg");
        }
        return nieuweBsnLijst.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getNieuwANummer()
     */
    @Override
    public synchronized String getNieuwANummer() throws SQLException, ClassNotFoundException {
        if (nieuweANummerLijst.isEmpty()) {
            throw new IllegalArgumentException("Nieuwe Anummer lijst is leeg");
        }
        return nieuweANummerLijst.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getHuwelijk()
     */
    @Override
    public synchronized Map<String, String> getHuwelijk() throws SQLException, ClassNotFoundException {
        if (huwelijkLijst.isEmpty()) {
            throw new IllegalArgumentException("Huwelijklijst is leeg");
        }
        return huwelijkLijst.removeFirst();
    }

    @Override
    public synchronized Map<String, String> getGeregistreerdPartnerschap() throws SQLException, ClassNotFoundException {
        if (geregistreerdPartnerschapLijst.isEmpty()) {
            throw new IllegalArgumentException("Geregistreerde partnerschap lijst is leeg");
        }
        return geregistreerdPartnerschapLijst.removeFirst();
    }

    @Override
    public Map<String, String> getAdoptieKind() throws SQLException, ClassNotFoundException {
        if (adoptieLijst.isEmpty() || adoptieLijst.peekFirst().keySet().size() < 2) {
            throw new IllegalArgumentException("Adoptie lijst is leeg");
        }
        return adoptieLijst.removeFirst();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.service.TestdataService#getBsnLijstGrootte()
     */
    @Override
    public synchronized int getBsnLijstGrootte() {
        return bsnLijst.size();
    }

}
