/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.Date;

import nl.bzk.brp.testdatageneratie.afnemerindicaties.AfnemerIndicatiesGenerator;
import nl.bzk.brp.testdatageneratie.utils.PerformanceLogger;
import org.apache.log4j.Logger;


public final class AfnemerIndicatiesVullenGen extends AbstractMain {

    private static final Logger LOGGER = Logger.getLogger(AfnemerIndicatiesVullenGen.class);

    /**
     * Private constructor.
     */
    private AfnemerIndicatiesVullenGen() {
        super();
    }

    /**
     * Hoofdmethode om generatie te starten.
     *
     * @param args argumenten (worden niet gebruikt)
     */
    public static void main(final String[] args) {
        try {
            voerTaakUit();
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            PerformanceLogger.quitLogger();

            sluitHibernate();
        }
    }

    /**
     * Doet het daadwerkelijke vullen van de database.
     *
     * @throws Exception exceptie
     */
    private static void voerTaakUit() throws Exception {
        final int personenPerThread = getProperty("personenPerThread");
        final int batchBlockSize = getProperty("batchBlockSize");
        final int numberOfThreads = getProperty("numberOfThreads");
        LOGGER.info("personenPerThread=" + personenPerThread + ", batchBlockSize=" + batchBlockSize
                + ", numberOfThreads=" + numberOfThreads);

        final int maxSet = getProperty("maxSet");
        if (maxSet == 0) {
            LOGGER.error("maxSet moet > 0 zijn");
            return;
        }

        long startProcess = System.currentTimeMillis();

        startHibernate();

        AfnemerIndicatiesGenerator afnemerIndicatiesGenerator =
                new AfnemerIndicatiesGenerator(batchBlockSize, numberOfThreads);
        afnemerIndicatiesGenerator.start();

        // Weghalen en plaatsen in finally?
        PerformanceLogger.quitLogger();
        long stopProcess = System.currentTimeMillis();
        LOGGER.info("---------------------- Einde --------------------------");
        LOGGER.info("Start : " + new Date(startProcess)
                + " Einde " + new Date(stopProcess)
                + " heeft geduurd " + (stopProcess - startProcess) / 1000 + " sec.");
    }

}
