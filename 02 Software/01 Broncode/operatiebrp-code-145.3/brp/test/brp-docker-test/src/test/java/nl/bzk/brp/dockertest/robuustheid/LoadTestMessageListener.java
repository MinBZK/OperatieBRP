/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 */
public class LoadTestMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    int aantalOntvangen;

    @Override
    public void onMessage(final Message message) {
        aantalOntvangen++;

        if (aantalOntvangen % 100 == 0) {
            LOGGER.debug("Aantal ontvangen {}", aantalOntvangen);
        }
    }

    public int getAantalOntvangen() {
        return aantalOntvangen;
    }
}
