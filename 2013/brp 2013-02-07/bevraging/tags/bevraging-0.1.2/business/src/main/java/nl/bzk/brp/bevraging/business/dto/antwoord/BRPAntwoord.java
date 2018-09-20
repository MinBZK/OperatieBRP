/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import java.util.Collection;

import nl.bzk.brp.bevraging.domein.Persoon;


/**
 * Generieke interface voor antwoord berichten. Alle BRP antwoord berichten dienen deze interface te implementeren,
 * zodat in de bericht verwerking middels de in deze interface gedefinieerde methodes de juiste informatie uit het
 * antwoord bericht kan worden gehaald.
 */
public interface BRPAntwoord {

    /**
     * Personen in het antwoord, wordt o.a. gebruikt voor protocollering.
     * @return personen die in het antwoord bericht worden geretourneerd.
     */
    Collection<Persoon> getPersonen();

}
