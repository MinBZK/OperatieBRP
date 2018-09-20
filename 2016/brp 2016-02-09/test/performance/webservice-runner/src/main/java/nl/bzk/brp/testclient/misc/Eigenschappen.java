/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


/**
 * De Class Eigenschappen.
 */
public class Eigenschappen {

    public static final int PROMILLE = 1000;
    public static final int PROMILLE_FACTOR = 10;

    /** De protocol bevraging. */
    private final String       protocolBevraging;

    /** De host bevraging. */
    private final String       hostBevraging;

    /** De port bevraging. */
    private final int          portBevraging;

    /** De context root bevraging. */
    private final String       contextRootBevraging;

    /** De protocol bijhouding. */
    private final String       protocolBijhouding;

    /** De host bijhouding. */
    private final String       hostBijhouding;

    /** De port bijhouding. */
    private final int          portBijhouding;

    /** De context root bijhouding. */
    private final String       contextRootBijhouding;

    /** De aantal threads. */
    private final int          aantalThreads;

    /** De voorloop tijd. */
    private final int          voorloopTijd;

    /** De flow inhoud. */
    private final String       flowInhoud;

    private final Boolean       flowPrevalidatie;

    /** De flow count. */
    private final int          flowCount;

    /** De db host bijhouding. */
    private final String       dbHostBijhouding;

    /** De db host bevraging. */
    private final String       dbHostBevraging;

    /** De exclusion tests. */
    private final int          exclusionTests;

    /** De exclusion test rows. */
    private final int          exclusionTestRows;

    /** De db host. */
    private final String       dbHost;

    /** De db host. */
    private final String       dbPort;

    /** De db name. */
    private final String       dbName;

    /** De db name. */
    private final String       dbUsername;

    private final String       dbPassword;

    private final boolean meldingenLoggen;

    /** De test count. */
    private final int          testCount;

    /** De props. */
    private final Properties   props                 = new Properties();

    /** De Constante DEFAULT_PORT. */
    private static final int   DEFAULT_PORT          = 8080;

    /** De Constante DEFAULT_AANTALTHREADS. */
    private static final int   DEFAULT_AANTALTHREADS = 10;

    /** De Constante DEFAULT_VOORLOOPTIJD. */
    private static final int   DEFAULT_VOORLOOPTIJD  = 4000;

    /** De Constante DEFAULT_FLOWCOUNT. */
    private static final int   DEFAULT_FLOWCOUNT     = 1000;

    /** De Constante SCENARIOS. */
    public static final String SCENARIOS = "Scenarios";

    /** De Constante DEFAULT_FLOWPREVALIDATIE. */
    public static final Boolean DEFAULT_FLOWPREVALIDATIE = false;

    /** Aantal berichten per seconden in totaal voor alle threads. */
    private final int aantalBerichtenPerSeconde;

    /** Verhoudingen tussen de scenarios. */
    private final ScenariosVerhoudingen scenariosVerhoudingen;

    /** Verhouding tussen bijhoudingen met eenmaal en tweemaal prevalidatie. */
    private final int verhoudingTweemaalPrevalidatiePromille;

    /**
     * Privatekeypassword.
     */
    private final String privatekeypassword;

    /**
     * Instantieert een nieuwe eigenschappen.
     *
     * @param propertiesFilename de properties filename
     */
    public Eigenschappen(final String propertiesFilename) {
        if (propertiesFilename != null && propertiesFilename.length() != 0) {
            try {
                props.load(new FileReader(propertiesFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protocolBevraging = getString("protocolBevraging", "http");
        hostBevraging = getString("hostBevraging", "localhost");
        portBevraging = getInt("portBevraging", DEFAULT_PORT);
        contextRootBevraging = getString("contextRootBevraging", "brp");

        dbHostBijhouding = getString("dbHostBijhouding", null);
        dbHostBevraging = getString("dbHostBevraging", null);
        exclusionTests = getInt("exclusionTests", Constanten.DUIZEND);
        exclusionTestRows = getInt("exclusionTestRows", 1);

        protocolBijhouding = getString("protocolBijhouding", "http");
        hostBijhouding = getString("hostBijhouding", "localhost");
        portBijhouding = getInt("portBijhouding", DEFAULT_PORT);
        contextRootBijhouding = getString("contextRootBijhouding", "brp");

        aantalThreads = getInt("aantalThreads", DEFAULT_AANTALTHREADS);
        voorloopTijd = getInt("voorloopTijd", DEFAULT_VOORLOOPTIJD);
        flowInhoud = getString("flowInhoud", SCENARIOS);
        flowPrevalidatie = getBoolean("flowPrevalidatie", DEFAULT_FLOWPREVALIDATIE);
        flowCount = getInt("flowCount", DEFAULT_FLOWCOUNT);

        dbHost = getString("dbHost", null);
        dbPort = getString("dbPort", "5432");
        dbName = getString("dbName", "brp");
        dbUsername = getString("dbUsername", "brp");
        dbPassword = getString("dbPassword", "brp");
        testCount = getInt("testCount", 0);

        privatekeypassword = getString("privatekeypassword", "changedit");
        meldingenLoggen = getBoolean("meldingenLoggen", true);

        aantalBerichtenPerSeconde = getInt("aantalBerichtenPerSeconde", 50);

        // Default waarden scenario verhoudingen gebaseerd op:
        // Stats in NL per jaar:
        // - Geboorten: 200 000 -> 8,1 %
        // - Verhuizing: 2 000 000 -> 81 %
        // - Correctie adres: 2,3 procent van verhuizingen (berekening van Roel mbv cijfers A'dam) = 56 000 -> 2,3 %
        // - Huwelijk: 70 000 -> 2,8 %
        // - Overlijden: 140 000 --> 5,7%
        // - Adoptie: 28 (Nederlandse kinderen in 2011) -->  0,001 %
        // Totaal: 2 466 028
        scenariosVerhoudingen = new ScenariosVerhoudingen(
                getDouble("scenarios.percentage.verhuizing", 81),
                getDouble("scenarios.percentage.correctieAdres", 2.3),
                getDouble("scenarios.percentage.huwelijk", 2),
                getDouble("scenarios.percentage.eersteInschrijving", 8),
                getDouble("scenarios.percentage.overlijden", 5.7),
                getDouble("scenarios.percentage.adoptie", 1));

        verhoudingTweemaalPrevalidatiePromille =
                (int) (getDouble("scenarios.percentage.tweemaal.prevalidatie", 20) * Eigenschappen.PROMILLE_FACTOR);
    }

    /**
     * Haalt een string op.
     *
     * @param key de key
     * @param defaultValue de default value
     * @return string
     */
    private String getString(final String key, final String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    /**
     * Haalt een int op.
     *
     * @param key de key
     * @param defaultValue de default value
     * @return int
     */
    private int getInt(final String key, final int defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        } else {
            return Integer.parseInt(value);
        }
    }

    /**
     * Haalt een double op.
     * @param key key in het properties bestand.
     * @param defaultValue standaard waarde.
     * @return double waarde
     */
    private double getDouble(final String key, final double defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        } else {
            return Double.parseDouble(value);
        }
    }

    /**
     * Haalt een boolean op.
     *
     * @param key de key
     * @param defaultValue de default value
     * @return boolean
     */
    private boolean getBoolean(final String key, final boolean defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        } else {
            return Boolean.parseBoolean(value);
        }
    }

    /**
     * Haalt een aantal threads op.
     *
     * @return aantal threads
     */
    public int getAantalThreads() {
        return aantalThreads;
    }

    /**
     * Haalt een voorloop tijd op.
     *
     * @return voorloop tijd
     */
    public int getVoorloopTijd() {
        return voorloopTijd;
    }

    /**
     * Haalt een flow count op.
     *
     * @return flow count
     */
    public int getFlowCount() {
        return flowCount;
    }

    /**
     * Haalt een flow inhoud op.
     *
     * @return flow inhoud
     */
    public String getFlowInhoud() {
        return flowInhoud;
    }

    /**
     * Haalt een protocol bevraging op.
     *
     * @return protocol bevraging
     */
    public String getProtocolBevraging() {
        return protocolBevraging;
    }

    /**
     * Haalt een host bevraging op.
     *
     * @return host bevraging
     */
    public String getHostBevraging() {
        return hostBevraging;
    }

    /**
     * Haalt een port bevraging op.
     *
     * @return port bevraging
     */
    public int getPortBevraging() {
        return portBevraging;
    }

    /**
     * Haalt een context root bevraging op.
     *
     * @return context root bevraging
     */
    public String getContextRootBevraging() {
        return contextRootBevraging;
    }

    /**
     * Haalt een protocol bijhouding op.
     *
     * @return protocol bijhouding
     */
    public String getProtocolBijhouding() {
        return protocolBijhouding;
    }

    /**
     * Haalt een host bijhouding op.
     *
     * @return host bijhouding
     */
    public String getHostBijhouding() {
        return hostBijhouding;
    }

    /**
     * Haalt een port bijhouding op.
     *
     * @return port bijhouding
     */
    public int getPortBijhouding() {
        return portBijhouding;
    }

    /**
     * Haalt een context root bijhouding op.
     *
     * @return context root bijhouding
     */
    public String getContextRootBijhouding() {
        return contextRootBijhouding;
    }

    /**
     * Haalt een db host bijhouding op.
     *
     * @return db host bijhouding
     */
    public String getDbHostBijhouding() {
        return dbHostBijhouding;
    }

    /**
     * Haalt een db host bevraging op.
     *
     * @return db host bevraging
     */
    public String getDbHostBevraging() {
        return dbHostBevraging;
    }

    /**
     * Haalt een exclusion tests op.
     *
     * @return exclusion tests
     */
    public int getExclusionTests() {
        return exclusionTests;
    }

    /**
     * Haalt een exclusion test rows op.
     *
     * @return exclusion test rows
     */
    public int getExclusionTestRows() {
        return exclusionTestRows;
    }

    /**
     * Haalt een db host op.
     *
     * @return db host
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * Haalt een test count op.
     *
     * @return test count
     */
    public int getTestCount() {
        return testCount;
    }

    /**
     * Haalt een db name op.
     *
     * @return db name
     */
    public String getDbName() {
        return dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public Boolean getFlowPrevalidatie() {
        return flowPrevalidatie;
    }

    public String getDbPort() {
        return dbPort;
    }

    public String getPrivatekeypassword() {
        return privatekeypassword;
    }

    public Boolean getMeldingenLoggen() {
        return meldingenLoggen;
    }

    public int getAantalBerichtenPerSeconde() {
        return aantalBerichtenPerSeconde;
    }

    public ScenariosVerhoudingen getScenariosVerhoudingen() {
        return scenariosVerhoudingen;
    }

    public int getVerhoudingTweemaalPrevalidatiePromille() {
        return verhoudingTweemaalPrevalidatiePromille;
    }
}
