/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern;

/**
 * Deze marker interface typeert zogenaamde dynamische stamtabellen. Dit zijn stamgegevens binnen de BRP die gewijzigd
 * kunnen worden. Dit in tegenstelling tot statische stamtabellen. Deze statische stamtabellen zijn niet als entities
 * maar als enums getypeerd.
 */
public interface DynamischeStamtabel {
    /**
     * Geef een identificatie voor een dynamische stamtabel waarde.
     * 
     * @return Een identificatienummer.
     */
    Number getId();
}
