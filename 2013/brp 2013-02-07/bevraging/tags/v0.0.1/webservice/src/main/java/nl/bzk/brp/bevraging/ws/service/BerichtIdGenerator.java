/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service;


/**
 * Antwoord berichten dienen te worden voorzien van een id, welke middels een implementatie van deze interface kunnen
 * worden gegenereerd. Uiteraard dient er ten alle tijden slechts een implementatie instantie van deze interface
 * gebruikt te worden.
 */
public interface BerichtIdGenerator {

    /**
     * Retourneert de volgende id.
     * @return de volgende id.
     */
    long volgendeId();

}
