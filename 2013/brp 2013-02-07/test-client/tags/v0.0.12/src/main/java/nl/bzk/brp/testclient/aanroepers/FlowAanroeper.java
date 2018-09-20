/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers;

import java.util.Random;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Bericht.TYPE;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.misc.ScenarioEngine;
import nl.bzk.brp.testclient.misc.ScenarioEngineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class FlowAanroeper.
 */
public class FlowAanroeper implements Runnable {

    /** De Constante LOG. */
    private static final Logger         LOG            = LoggerFactory.getLogger(FlowAanroeper.class);

    /** De eigenschappen. */
    private final Eigenschappen         eigenschappen;

    /** De bericht. */
    private BERICHT                     bericht;

    /** De type. */
    private TYPE                        type;

    /** De Constante scenarioEngine. */
    private static final ScenarioEngine scenarioEngine = new ScenarioEngineImpl();

    /**
     * Instantieert een nieuwe flow aanroeper.
     *
     * @param eigenschappen de eigenschappen
     */
    public FlowAanroeper(final Eigenschappen eigenschappen) {
        this.eigenschappen = eigenschappen;

        bericht = null;
        type = null;

        // Specifiek bericht
        if (eigenschappen.getFlowInhoud() != null
            && !eigenschappen.getFlowInhoud().equals(Eigenschappen.DEFAULT_FLOWINHOUD))
        {
            for (TYPE t : TYPE.values()) {
                if (t.getNaam().equals(eigenschappen.getFlowInhoud())) {
                    type = t;
                    continue;
                }
            }
            for (BERICHT b : BERICHT.values()) {
                if (b.getNaam().equals(eigenschappen.getFlowInhoud())) {
                    bericht = b;
                    continue;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public void run() {
        LOG.info("Started thread " + Thread.currentThread().getName() + ", bericht=" + bericht);
        while (!TestClient.isKlaar() && !Thread.currentThread().isInterrupted()) {
            try {
                fire();
            } catch (Exception e) {
                LOG.error("", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    /**
     * Fire.
     *
     * @throws Exception de exception
     */
    public void fire() throws Exception {
        if (bericht != null) {
            runBerichtFlow(bericht);
        } else if (type != null) {
            runTypeFlow(type);
        } else {
            runDefaultFlow();
        }

        TestClient.statistieken.bijwerkenNaBerichtenFlow();
    }

    /**
     * Run default flow.
     *
     * @throws Exception de exception
     */
    private void runDefaultFlow() throws Exception {
        // Stats in NL per jaar:
        // - Geboorten: 200 000 -> 8,6 %
        // - Verhuizing: 2 000 000 -> 86 %
        // - Correctie adres: 2,8 procent van verhuizingen (berekening van Roel mbv cijfers adam) = 56 000 -> 2,4 %
        // - Huwelijk: 70 000 -> 3,0 %
        // Totaal: 2 326 000

        Random random = new Random();
        // 86 %
        if (random.nextInt(1000) <= 860) {
            scenarioEngine.runScenario(BERICHT.BIJHOUDING_VERHUIZING, eigenschappen);
        }
        // 2,4 %
        if (random.nextInt(1000) <= 24) {
            scenarioEngine.runScenario(BERICHT.BIJHOUDING_CORRECTIE_ADRES, eigenschappen);
        }
        // 3,0 %
        if (random.nextInt(1000) <= 30) {
            scenarioEngine.runScenario(BERICHT.BIJHOUDING_REGISTREER_HUWELIJK, eigenschappen);
        }
        // 8,6 %
        if (random.nextInt(1000) <= 86) {
            scenarioEngine.runScenario(BERICHT.BIJHOUDING_INSCHRIJVING_GEBOORTE, eigenschappen);
        }
    }

    /**
     * Run bericht flow.
     *
     * @param bericht de bericht
     * @throws Exception de exception
     */
    private void runBerichtFlow(final BERICHT bericht) throws Exception {
        if (bericht != null) {
            scenarioEngine.runBericht(bericht, eigenschappen);
        } else {
            throw new RuntimeException("FOUT: Er dient een correct bericht te worden ingesteld in de properties.");
        }
    }

    /**
     * Run type flow.
     *
     * @param type de type
     * @throws Exception de exception
     */
    private void runTypeFlow(final TYPE type) throws Exception {
        if (type != null) {
            scenarioEngine.runType(type, eigenschappen);
        } else {
            throw new RuntimeException("FOUT: Er dient een correct type bericht te worden ingesteld in de properties.");
        }
    }

}
