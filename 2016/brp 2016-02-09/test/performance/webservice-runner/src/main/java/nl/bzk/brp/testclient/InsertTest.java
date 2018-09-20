/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient;

import nl.bzk.brp.testclient.insert.InsertTester;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert test klasse.
 */
public final class InsertTest {

    private static final Logger LOG = LoggerFactory.getLogger(TestClient.class);

    /**
     * Eigenschappen.
     */
    private static Eigenschappen eigenschappen;

    /**
     * Instantieert een nieuwe Insert test.
     */
    private InsertTest() {
    }

    /**
     * Main methode.
     *
     * @param args args
     */
    public static void main(final String[] args) {
        try {
            String propertiesFilename;
            if (args.length == 1) {
                propertiesFilename = args[0];
            } else {
                LOG.error("Fout: u dient het pad naar de properties-bestand als applicatie argument mee te geven.");
                throw new RuntimeException();
            }

            eigenschappen = new Eigenschappen(propertiesFilename);

            new InsertTester().execute();

        } catch (Exception e) {
            LOG.error("Fout: ", e);
        }
    }

    /**
     * Verkrijgt eigenschappen.
     *
     * @return eigenschappen
     */
    public static Eigenschappen getEigenschappen() {
        return eigenschappen;
    }

    /**
     * Zet eigenschappen.
     *
     * @param eigenschappen eigenschappen
     */
    public static void setEigenschappen(final Eigenschappen eigenschappen) {
        InsertTest.eigenschappen = eigenschappen;
    }
}
