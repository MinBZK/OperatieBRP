/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

/**
 * Constanten voor entiteiten.
 */
class Constanten {
    /**
     * Naam van de named query. Deze word aangeroepen om de dynamische stamtabel caches vanuit
     * bijhouding te herladen. Alle eager koppelingen kunnen met een join fetch worden meegenomen.
     */
    static final String ZOEK_ALLES_VOOR_CACHE = ".findAll";
}
