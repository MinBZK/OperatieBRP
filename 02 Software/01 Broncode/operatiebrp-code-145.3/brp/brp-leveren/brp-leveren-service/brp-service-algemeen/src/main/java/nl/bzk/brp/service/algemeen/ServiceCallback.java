/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

/**
 * Generieke service callback.
 * @param <B> type bericht
 * @param <R> type verwerkt resultaat
 */
public interface ServiceCallback<B, R> {

    /**
     * Verwerk het bericht B tot delivery format.
     * @param bericht het bericht
     */
    void verwerkBericht(B bericht);

    /**
     * Geef het verwerkte resultaat.
     * @return het verwerkte resultaat B
     */
    R getBerichtResultaat();

}
