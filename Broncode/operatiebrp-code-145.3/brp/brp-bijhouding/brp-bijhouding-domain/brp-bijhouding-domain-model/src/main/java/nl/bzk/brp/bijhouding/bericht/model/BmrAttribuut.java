/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.List;

/**
 * Een niet-identificeerbaar element binnen een bijhoudingsbericht dat wordt aangeduid als attribuut in het BMR.
 *
 * @param <T>
 *            het type van de waarde van dit attribuut
 */
public interface BmrAttribuut<T> extends Element {

    /**
     * Geeft de waarde van bmrAttribuut terug of null als bmrAttribuut null is.
     * 
     * @param bmrAttribuut
     *            bmrAttribuut
     * @param <V>
     *            het type van de waarde van bmrAttribuut
     * @return waarde van bmrAttribuut als deze niet null is, anders null.
     */
    static <V> V getWaardeOfNull(final BmrAttribuut<V> bmrAttribuut) {
        return bmrAttribuut != null ? bmrAttribuut.getWaarde() : null;
    }

    /**
     * Geef de waarde van het attribuut.
     *
     * @return de waarde
     */
    T getWaarde();

    /**
     * Valideert de inhoud het attribuut en geeft een lege lijst als resultaat
     * wanneer er geen fouten zijn en anders een lijst met daarin de gevonden fouten.
     * @param groep De groep om de melding aan te koppelen.
     * @return Lijst van eventueel opgetreden meldingen, of lege lijst.
     */
    default List<MeldingElement> valideer(final BmrGroep groep) {
        return Collections.emptyList();
    }

}

