/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Interface voor het bepalen van de geldigheid van een entiteit.
 * Geldig wil zeggen dat indicatie Actueel en Geldig waar is en
 * de datum ingang ligt op of voor de peildatum en de datum einde
 * is leeg og ligt na de peildatum.
 */
public interface Geldigheid {

    /**
     * Geeft aan of entiteit geldig is
     * @return is geldig
     */
    default boolean isGeldig() {
        return isGeldig(Integer.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
    }

    /**
     * Geeft aan of entiteit geldig is op peilmoment
     * @param peilDatum datum waarop geldigheid bepaald moet worden
     * @return is geldig
     */
    default boolean isGeldig(Integer peilDatum) {
        boolean result = isActueelEnGeldig();
        result = result && getDatumIngang() != null;
        result = result && getDatumIngang() <= peilDatum;
        result = result && (getDatumEinde() == null || getDatumEinde() > peilDatum);
        return result;
    }

    /**
     * Geef de waarde van datum ingang van Partij.
     * @return de waarde van datum ingang van Partij
     */
    Integer getDatumIngang();

    /**
     * Geef de waarde van datum einde van Partij.
     * @return de waarde van datum einde van Partij
     */
    Integer getDatumEinde();

    /**
     * Geef de waarde van isActueelEnGeldig.
     * @return isActueelEnGeldig
     */
    boolean isActueelEnGeldig();
}
