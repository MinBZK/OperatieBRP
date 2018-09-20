/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Interface voor alle attribuuttypes die een vaste set van mogelijke waardes hebben. Een attribuut van dit type
 * kan ook nooit een andere waarde hebben dan een van de voor gedefinieerde waardes.
 */
public interface VasteWaardeAttribuutType<T> {

    /**
     * Retourneert de waarde van het attribuut.
     *
     * @return de waarde van het attribuut.
     */
    T getWaarde();

}
