/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import java.util.Collection;
import java.util.List;

import nl.bzk.brp.bevraging.domein.kern.Persoon;


/**
 * Generieke interface voor antwoord berichten, waarbij de antwoord berichten tevens toegang geven tot de opgetreden
 * fouten. Alle BRP antwoord berichten dienen deze interface te implementeren, zodat in de bericht verwerking middels
 * de in deze interface gedefinieerde methodes de juiste informatie uit het antwoord bericht kan worden gehaald.
 */
public interface BerichtAntwoord {

    /**
     * Retourneert een lijst van opgetreden fouten.
     * @return een lijst van opgetreden fouten.
     */
    List<BerichtVerwerkingsFout> getFouten();

    /**
     * Voegt fout toe aan de lijst van fouten.
     * @param fout de opgetreden fout.
     */
    void voegFoutToe(BerichtVerwerkingsFout fout);

    /**
     * Personen in het antwoord, wordt o.a. gebruikt voor protocollering.
     * @return personen die in het antwoord bericht worden geretourneerd.
     */
    Collection<Persoon> getPersonen();

    /**
     * Retourneert de id van de levering/protocollering record die heeft plaatsgevonden tbv dit antwoord.
     * @return de id van de levering/protocollering record die heeft plaatsgevonden tbv dit antwoord.
     */
    Long getLeveringId();

    /**
     * Zet de id van de levering die is geschiedt voor dit antwoord.
     * @param leveringId de id van de levering die is geschiedt voor dit antwoord.
     */
    void setLeveringId(Long leveringId);

    /**
     * Methode voor het leeghalen van de content die reeds aan het antwoord is toegevoegd, bijvoorbeeld
     * vanwege een optredende fout.
     */
    void wisContent();

}
