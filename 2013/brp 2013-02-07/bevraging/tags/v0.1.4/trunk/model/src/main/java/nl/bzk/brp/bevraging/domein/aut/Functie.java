/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;


/**
 * Functie is een door een Partij uitvoerbare activiteit die ten dienste staat aan het vervullen van één of meer
 * rollen van die Partij.
 */
public enum Functie {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0.
     */
    DUMMY,
    /**
     * Bevraging Service.
     */
    BEVRAGING_SERVICE;

}
