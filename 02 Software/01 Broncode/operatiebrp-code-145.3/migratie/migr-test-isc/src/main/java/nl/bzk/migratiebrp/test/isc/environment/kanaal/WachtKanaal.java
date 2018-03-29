/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;

import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Wacht kanaal.
 */
public final class WachtKanaal extends AbstractKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String ERROR_ONGELDIGE_INHOUD = "Ongeldige inhoud voor wachttijd.";
    private static final int MILLIS = 1000;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
     */
    @Override
    public String getKanaal() {
        return "WACHT";
    }

    @Override
    public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        try {
            wacht(Long.parseLong(bericht.getInhoud()));
        } catch (final NumberFormatException e) {
            LOG.error(ERROR_ONGELDIGE_INHOUD, e);
        }
    }

    @Override
    public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        try {
            wacht(Long.parseLong(verwachtBericht.getInhoud()));
        } catch (final NumberFormatException e) {
            LOG.error(ERROR_ONGELDIGE_INHOUD, e);
        }
        return null;
    }

    private void wacht(final long seconds) {
        try {
            LOG.info("Testtooling wacht gedurende " + seconds + " seconden.");
            TimeUnit.MILLISECONDS.sleep(seconds * MILLIS);
        } catch (final InterruptedException e) {
            LOG.debug("Wacht interrupted.");
        }
    }

}
