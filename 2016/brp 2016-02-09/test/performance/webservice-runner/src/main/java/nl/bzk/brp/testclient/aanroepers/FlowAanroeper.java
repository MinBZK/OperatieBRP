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
import nl.bzk.brp.testclient.misc.StatistiekenImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class FlowAanroeper.
 */
public class FlowAanroeper implements Runnable
{

    /** De Constante LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(FlowAanroeper.class);

    /** De Constante scenarioEngine. */
    private static final ScenarioEngine SCENARIO_ENGINE = new ScenarioEngineImpl();

    /** De eigenschappen. */
    private final Eigenschappen eigenschappen;

    /** De bericht. */
    private BERICHT bericht;

    /** De type. */
    private TYPE type;

    /**
     * Instantieert een nieuwe flow aanroeper.
     * 
     * @param eigenschappen
     *            de eigenschappen
     */
    public FlowAanroeper(final Eigenschappen eigenschappen)
    {
        this.eigenschappen = eigenschappen;

        bericht = null;
        type = null;

        // Specifiek bericht
        if (eigenschappen.getFlowInhoud() != null && !eigenschappen.getFlowInhoud().equals(Eigenschappen.SCENARIOS)) {
            for (TYPE t : TYPE.values()) {
                if (t.getNaam().equals(eigenschappen.getFlowInhoud())) {
                    type = t;
                    break;
                }
            }
            for (BERICHT b : BERICHT.values()) {
                if (b.getNaam().equals(eigenschappen.getFlowInhoud())) {
                    bericht = b;
                    break;
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
        
        LOG.trace("thread started");
        
        try {
            while (!TestClient.isKlaar() && !Thread.currentThread().isInterrupted()) {
                fire();
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    /**
     * Fire.
     */
    public void fire() {
        try {
            if (bericht != null) {
                LOG.trace("runBerichtFlow");
                runBerichtFlow(bericht);
            } else if (type != null) {
                LOG.trace("runTypeFlow");
                runTypeFlow(type);
            } else {
                LOG.trace("runScenariosFlow");
                runScenariosFlow();
            }
        } catch (Exception e) {
            LOG.error("Fout opgetreden!", e);
        } finally {
            StatistiekenImpl.INSTANCE.bijwerkenNaBerichtenFlow();
        }

    }

    /**
     * Run scenarios flow. Dus opvragen persoon, prevalidatie, bijhouding.
     * 
     * @throws Exception
     *             de exception
     */
    private void runScenariosFlow() throws Exception {
        Random random = new Random();
        if (random.nextInt(Eigenschappen.PROMILLE) <= eigenschappen.getScenariosVerhoudingen().getPromilleVerhuizing()) {
            SCENARIO_ENGINE.runScenario(BERICHT.BIJHOUDING_VERHUIZING, eigenschappen);
        }
        // if (random.nextInt(Eigenschappen.PROMILLE)
        // <=
        // eigenschappen.getScenariosVerhoudingen().getPromilleCorrectieAdres())
        // {
        // SCENARIO_ENGINE.runScenario(BERICHT.BIJHOUDING_CORRECTIE_ADRES,
        // eigenschappen);
        // }
        if (random.nextInt(Eigenschappen.PROMILLE) <= eigenschappen.getScenariosVerhoudingen().getPromilleHuwelijk()) {
            SCENARIO_ENGINE.runScenario(BERICHT.BIJHOUDING_HUWELIJK, eigenschappen);
        }
        if (random.nextInt(Eigenschappen.PROMILLE) <= eigenschappen.getScenariosVerhoudingen().getPromilleGeboorte()) {
            SCENARIO_ENGINE.runScenario(BERICHT.BIJHOUDING_GEBOORTE, eigenschappen);
        }
        if (random.nextInt(Eigenschappen.PROMILLE) <= eigenschappen.getScenariosVerhoudingen().getPromilleOverlijden()) {
            SCENARIO_ENGINE.runScenario(BERICHT.BIJHOUDING_OVERLIJDEN, eigenschappen);
        }
        // if (random.nextInt(Eigenschappen.PROMILLE)
        // <= eigenschappen.getScenariosVerhoudingen().getPromilleAdoptie())
        // {
        // SCENARIO_ENGINE.runScenario(BERICHT.BIJHOUDING_ADOPTIE,
        // eigenschappen);
        // }
    }

    /**
     * Run bericht flow.
     * 
     * @param runBericht
     *            de bericht
     * @throws Exception
     *             de exception
     */
    private void runBerichtFlow(final BERICHT runBericht) throws Exception {
        if (runBericht != null) {
            SCENARIO_ENGINE.runBericht(runBericht, eigenschappen);
        } else {
            throw new RuntimeException("FOUT: Er dient een correct bericht te worden ingesteld in de properties.");
        }
    }

    /**
     * Run type flow.
     * 
     * @param runType
     *            de type
     * @throws Exception
     *             de exception
     */
    private void runTypeFlow(final TYPE runType) throws Exception {
        if (runType != null) {
            SCENARIO_ENGINE.runType(runType, eigenschappen);
        } else {
            throw new RuntimeException("FOUT: Er dient een correct type bericht te worden ingesteld in de properties.");
        }
    }

}
