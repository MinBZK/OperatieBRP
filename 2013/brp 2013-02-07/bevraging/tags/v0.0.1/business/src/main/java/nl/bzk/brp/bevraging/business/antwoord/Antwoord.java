/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.antwoord;


/**
 * Standaard antwoord object.
 *
 * @param <T> Type/Class van het object dat geretourneerd wordt.
 */
public class Antwoord<T> {

    private T resultaat;

    /**
     * Retourneert het resultaat; het te retourneren object.
     *
     * @return het resultaat van een bericht verwerking.
     */
    public final T getResultaat() {
        return resultaat;
    }

    /**
     * Zet het resultaat; het te retourneren object.
     *
     * @param resultaatObject het resultaat van een bericht verwerking.
     */
    public final void setResultaat(final T resultaatObject) {
        this.resultaat = resultaatObject;
    }

}
