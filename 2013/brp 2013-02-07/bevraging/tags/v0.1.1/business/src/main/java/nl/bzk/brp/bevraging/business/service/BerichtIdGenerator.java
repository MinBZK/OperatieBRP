/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;


/**
 * Alle binnenkomende berichten dienen te worden voorzien van een uniek id, welke middels een implementatie van deze
 * interface kunnen worden opgehaald. Uiteraard dient er ten alle tijden slechts een implementatie instantie van deze
 * interface gebruikt te worden en dient de gegenereerde id uniek te zijn over eventueel op verschillende servers
 * uitgerolde instanties.
 */
public interface BerichtIdGenerator {

    /**
     * Retourneert de volgende id.
     * @return de volgende id.
     */
    long volgendeId();

}
