/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Interface voor alle objecttypen, groepen en attributen die in onderzoek kunnen staan.
 */
public interface Onderzoekbaar {

    /**
     * Geeft aan of het objecttype, groep of attribuut in onderzoek staat.
     * @return true indien in onderzoek.
     */
    boolean isInOnderzoek();
}
