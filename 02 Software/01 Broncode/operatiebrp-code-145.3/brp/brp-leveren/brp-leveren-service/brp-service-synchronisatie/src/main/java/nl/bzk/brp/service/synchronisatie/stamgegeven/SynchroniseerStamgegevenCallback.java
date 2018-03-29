/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;

/**
 * StamgegevenCallback.
 * @param <T> type resultaat
 */
public interface SynchroniseerStamgegevenCallback<T> {
    /**
     * Verwerk het {@link BepaalStamgegevenResultaat}.
     * @param resultaat het {@link BepaalStamgegevenResultaat}
     */
    void verwerkResultaat(SynchroniseerStamgegevenBericht resultaat);

    /**
     * Geef het verwerkte resultaat.
     * @return het verwerkte resultaat
     */
    T getResultaat();
}
