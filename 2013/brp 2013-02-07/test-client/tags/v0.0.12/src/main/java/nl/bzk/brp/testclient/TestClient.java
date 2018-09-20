/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient;

import java.util.HashMap;

import nl.bzk.brp.testclient.aanroepers.FlowAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagDetailsPersoonAanroeper;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.misc.Statistieken;
import nl.bzk.brp.testclient.misc.StatistiekenImpl;
import nl.bzk.brp.testclient.testdata.TestdataService;
import nl.bzk.brp.testclient.testdata.TestdataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class TestClient.
 */
public final class TestClient {

    public static final Statistieken statistieken  = new StatistiekenImpl();
    public static TestdataService    testdataService;
    public static Eigenschappen      eigenschappen;
    public static int                aantalThreads = 0;

    /**
     * Controleert of de waarde gelijk is aan klaar.
     *
     * @return true, als waarde gelijk is aan klaar
     */
    public static boolean isKlaar() {
        return statistieken.getAantalVerzondenFlow() >= eigenschappen.getFlowCount();
    }

    /**
     * Instantieert een nieuwe test client.
     */
    private TestClient() {
    };

    /** De Constante LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(TestClient.class);

    /**
     * De main methode.
     *
     * @param args de argumenten
     */
    public static void main(final String[] args) {
        String propertiesFilename = null;
        if (args.length > 0) {
            propertiesFilename = args[0];
        } else {
            LOG.error("Fout: u dient het pad naar het bijbehorende properties-bestand als applicatie argument mee te geven, bijvoorbeeld: test-client.properties.");
            throw new RuntimeException();
        }

        try {
            eigenschappen = new Eigenschappen(propertiesFilename);
            testdataService = new TestdataServiceImpl();

            LOG.info("####### Start test (" + testdataService.getBsnListSize() + " personen) ########");
            new TestClient().execute(eigenschappen);
        } catch (Exception e) {
            LOG.error("Fout: ", e);
        }

        LOG.debug("####### Einde test ########");
    }

    /**
     * Execute.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void execute(final Eigenschappen eigenschappen) throws Exception {
        // Eerste bericht versturen om server op te starten
        verstuurEersteBericht(eigenschappen);

        // Even wachten tot server opgestart is
        Thread.sleep(eigenschappen.getVoorloopTijd());

        // Taken toevoegen tot aan minimum aantal threads
        Thread threads[] = new Thread[eigenschappen.getAantalThreads()];
        for (int i = 0; i < eigenschappen.getAantalThreads(); i++) {
            Thread thread = new Thread(new FlowAanroeper(eigenschappen), "FlowAanroeper Thread " + i);
            threads[i] = thread;
            thread.start();
        }

        while (!isKlaar()) {
            Thread.sleep(1000);
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        threads = null;
    }

    private void verstuurEersteBericht(final Eigenschappen eigenschappen2) throws Exception {
        String bsn = testdataService.getBsn();

        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen2, parameterMap1).fire();
    }
}
