/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

/**
 * Deze interface geeft aan welke informatie altijd in de context voor stappen aanwezig moet zijn.
 */
public interface StappenContext {

    /**
     * Geef het referentieId terug van bijvoorbeeld het inkomende bericht of de administratieve handeling die de start
     * van het stappen process heeft veroorzaakt. Deze Id wordt gebruikt als referentie voor onderliggende processen,
     * zoals het locken van records.
     *
     * @return Long referentie id (bijv. inkomend bericht id)
     */
    Long getReferentieId();

    /**
     * Geef een id terug als referentie naar het resultaat van de stappen. Bijvoorbeeld het id van de Administratieve
     * Handeling, of van het uitgaande Bericht.
     *
     * @return Long id verwijzend naar een uitgaand bericht, administratieve handeling of ander resulterend object id
     * in de databse.
     */
    Long getResultaatId();

}
