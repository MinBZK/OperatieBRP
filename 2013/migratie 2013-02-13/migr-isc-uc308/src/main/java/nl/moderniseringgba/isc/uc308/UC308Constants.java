/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

/**
 * Bevat constantes gebruikt binnen uc308: Toevallige gebeurtenis van BRP naar LO3.
 * 
 */
public final class UC308Constants {

    /**
     * Statusovergang indien de controle op LO3 gemeente mislukt.
     */
    public static final String CONTROLE_LO3_GEMEENTE_MISLUKT = "2b. BRP-gemeente";

    /**
     * Statusovergang indien de validatie van het BRP Bijhoudingverzoek mislukt.
     */
    public static final String VALIDATIE_BRP_BIJHOUDING_VERZOEK_MISLUKT = "3b. Validatie mislukt";

    /**
     * Statusovergang indien de conversie van het BRP Bijhoudingverzoekmislukt.
     */
    public static final String CONVERSIE_MISLUKT = "4b. Conversie mislukt";

    /**
     * Naam van het BRP Bijhoudingverzoekbericht in de parameter-map.
     */
    public static final String BRP_BIJHOUDING_VERZOEK_BERICHT = "input";

    /**
     * Naam van het converteerNaarLO3Verzoek-bericht in de parameter-map.
     */
    public static final String CONVERTEER_NAAR_LO3_VERZOEK = "converteerBericht";

    /**
     * Naam van het converteerNaarLO3Antwoord-bericht in de parameter-map.
     */
    public static final String CONVERTEER_NAAR_LO3_ANTWOORD = "converteerNaarLo3Antwoord";

    /**
     * Naam van het BRP Bijhoudingantwoord bericht in de paramter-map.
     */
    public static final String BRP_BIJHOUDING_ANTWOORD_BERICHT = "brpBijhoudingAntwoordBericht";

    /**
     * Naam van het Tb02-bericht in de parameter-map.
     */
    public static final String TB02_BERICHT = "tb02Bericht";

    private UC308Constants() {
        // niet instantieerbaar
    }
}
