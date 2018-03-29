/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

/**
 * Exception indien op de partij code van de centrale voorziening wordt gezocht.
 */
public class CentraleMailboxException extends RuntimeException {

    CentraleMailboxException() {
        super("Er kan niet gezocht worden op de patijcode van de centrale voorziening");
    }
}
