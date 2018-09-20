/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient;

import java.util.HashMap;

import com.google.common.util.concurrent.RateLimiter;

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.testclient.aanroepers.FlowAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagDetailsPersoonAanroeper;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.misc.ScenarioEngineImpl;
import nl.bzk.brp.testclient.misc.Statistieken;
import nl.bzk.brp.testclient.misc.StatistiekenImpl;
import nl.bzk.brp.testclient.testdata.TestdataService;
import nl.bzk.brp.testclient.testdata.TestdataServiceImpl;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class TestClient.
 */
public final class TestClient {

    private static final Long LOG_INTERVAL = 5L * 1000;
    
    /**
     * Testdata service voor ophalen testdata.
     */
    public static TestdataService    testdataService;
    /**
     * Eigenschappen voor de test.
     */
    public static Eigenschappen      eigenschappen;
    /**
     * Aantal threads waarin berichten worden verzonden tijdens de test.
     */
    public static int                aantalThreads = 0;

    /**
     * Rate limiter zorgt ervoor dat er een vast aantal berichten per seconde wordt verstuurd.
     * Verdeeld over alle threads.
     */
    public static RateLimiter RATELIMITER;

    /**
     * Controleert of de waarde gelijk is aan klaar.
     *
     * @return true, als waarde gelijk is aan klaar
     */
    public static boolean isKlaar() {
        return StatistiekenImpl.INSTANCE.getAantalVerzondenFlow() >= eigenschappen.getFlowCount();
    }

    /**
     * Instantieert een nieuwe test client.
     */
    private TestClient() {
    }

    /** De Constante LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(TestClient.class);

    /**
     * De main methode.
     *
     * @param args de argumenten
     */
    public static void main(final String[] args) {
        String propertiesFilename;
        if (args.length > 0) {
            propertiesFilename = args[0];
        } else {
            LOG.error("Fout: u dient het pad naar het bijbehorende properties-bestand als applicatie argument mee te geven, bijvoorbeeld: test-client.properties.");
            throw new RuntimeException();
        }

        try {
            eigenschappen = new Eigenschappen(propertiesFilename);
            RATELIMITER = RateLimiter.create(eigenschappen.getAantalBerichtenPerSeconde());
            testdataService = new TestdataServiceImpl(eigenschappen);
            TestClient testClient = new TestClient();
            testClient.warmOp(eigenschappen);

            LOG.info("####### Start test (" + testdataService.getBsnLijstGrootte() + " personen) ########");
            testClient.execute(eigenschappen);
        } catch (Exception e) {
            LOG.error("Fout: ", e);
        }
        LOG.debug("####### Einde test ########");
    }

    /**
     * Warm op.
     *
     * @param eigenschappen the eigenschappen
     * @throws Exception the exception
     */
    private void warmOp(final Eigenschappen eigenschappen) throws Exception {
        // Eerste bericht versturen om server op te starten
        verstuurEersteBericht(eigenschappen);
        FlowAanroeper aanroeper = new FlowAanroeper(eigenschappen);
        aanroeper.fire();
        // Even wachten tot server opgestart is
        Thread.sleep(eigenschappen.getVoorloopTijd());
    }

    /**
     * Execute.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void execute(final Eigenschappen eigenschappen) throws Exception {
        
        StatistiekenImpl.INSTANCE.reset();
        
        // Taken toevoegen tot aan minimum aantal threads
        Thread threads[] = new Thread[eigenschappen.getAantalThreads()];
        for (int i = 0; i < eigenschappen.getAantalThreads(); i++) {
            threads[i] = new Thread(new FlowAanroeper(eigenschappen), "FlowAanroeper Thread " + i);
            threads[i].start();
        }

        while (!isKlaar()) {
            Thread.sleep(LOG_INTERVAL);
            StatistiekenImpl.INSTANCE.logStats();
        }

        for (Thread thread : threads) {
            thread.join(1000);
        }

        LOG.debug("Laatste logStats:");
        
        StatistiekenImpl.INSTANCE.logStats();
        
        threads = null;
    }

    /**
     * Verstuur eerste bericht.
     *
     * @param eigenschappen2 the eigenschappen 2
     * @throws Exception the exception
     */
    private void verstuurEersteBericht(final Eigenschappen eigenschappen2) throws Exception {
        String bsn = testdataService.getBsn();


        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(BhgBevraging.class);
        String url = String.format(ScenarioEngineImpl.BEVRAGING_BIJHOUDING_URL, eigenschappen2.getProtocolBevraging(),
                                   eigenschappen2.getHostBevraging(), eigenschappen2.getPortBevraging(),
                                   eigenschappen2.getContextRootBevraging());
        proxyFactory.setAddress(url);

        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen2, (BhgBevraging) proxyFactory.create(),
                                         parameterMap1).fire();
    }
}
