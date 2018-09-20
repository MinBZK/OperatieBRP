/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import java.util.concurrent.atomic.AtomicInteger;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deze class is er alleen voor debug doeleinden.
 */

public class VertraagStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(VertraagStap.class);

    public static AtomicInteger concurrentCounter = new AtomicInteger();

    private int timeInMillies = 500;

    public int getTimeInMillies() {
        return timeInMillies;
    }

    public void setTimeInMillies(final int timeInMillies) {
        this.timeInMillies = timeInMillies;
    }

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {
        try {
            concurrentCounter.incrementAndGet();
            Thread.sleep(timeInMillies);
            //LOGGER.debug("Bericht ontvangen, actionId={}, concurrentCounter={}", context.getAdministratieveHandelingId(), concurrentCounter.get());
            return StapResultaat.DOORGAAN_MET_VERWERKING;

        } catch (InterruptedException e) {
            return StapResultaat.STOP_VERWERKING;
        }
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final LevMutAdmHandBerichtContext context) {
        concurrentCounter.decrementAndGet();
    }

}
