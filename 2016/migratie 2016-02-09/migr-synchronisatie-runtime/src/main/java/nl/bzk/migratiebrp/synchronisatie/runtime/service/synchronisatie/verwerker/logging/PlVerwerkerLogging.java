/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging;

import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Synchronisatie logging voor verwerking.
 */
public final class PlVerwerkerLogging {

    private final String key;

    /**
     * Constructor (en log start).
     *
     * @param melding
     *            melding
     */
    public PlVerwerkerLogging(final PlVerwerkerMelding melding) {
        key = melding.getKey();
        SynchronisatieLogging.addMelding(key + ": " + melding.getOmschrijving());
    }

    /**
     * Log een verwerkings beslissing (in context van de controle).
     *
     * @param beslissing
     *            beslissing
     */
    public void addBeslissing(final SynchronisatieBeslissing beslissing) {
        SynchronisatieLogging.addBeslissing(beslissing);
        SynchronisatieLogging.addMelding(String.format("[%s] %s (%s)", key, beslissing.getOmschrijving(), beslissing.getCode()));
    }

    /**
     * Log een melding (in context van de controle).
     *
     * @param melding
     *            melding
     */
    public void addMelding(final String melding) {
        SynchronisatieLogging.addMelding(String.format("[%s] %s", key, melding));
    }

}
