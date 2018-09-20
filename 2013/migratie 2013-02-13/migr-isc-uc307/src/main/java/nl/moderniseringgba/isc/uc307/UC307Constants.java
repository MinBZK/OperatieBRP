/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

/**
 * Bevat constantes gebruikt binnen uc307: Toevallige geboorte van Lo3 naar BRP.
 * 
 */
public final class UC307Constants {

    /**
     * Naam van het Tb01-bericht in de parameter-map.
     */
    public static final String TB01_BERICHT = "tb01_bericht";

    /**
     * Statusovergang indien de moeder niet in BRP gevonden wordt.
     */
    public static final String INSCHRIJVING_OP_MOEDER_MISLUKT = "2a. Inschrijving op moeder mislukt";

    /**
     * Naam van het converteerNaarBrpVerzoek-bericht in de parameter-map.
     */
    public static final String CONVERTEER_NAAR_BRP_VERZOEK = "converteerNaarBrpVerzoek";

    /**
     * Naam van de LO3-persoonslijst in de parameter-map.
     */
    public static final String LO3_PL = "lo3PL";

    /**
     * Naam van het Tv01-bericht in de parameter-map.
     */
    public static final String TV01_BERICHT = "tv01Bericht";

    /**
     * Naam van het Tf01-bericht in de parameter-map.
     */
    public static final String TF01_BERICHT = "tf01Bericht";

    /**
     * Statusovergang in het geval van een Tf11 bericht op een verzonden Tv01 bericht.
     */
    public static final String TF11_BERICHT = "9c. Tf11";

    /**
     * Naam van het Tv01Antwoord-bericht in de parameter-map.
     */
    public static final String TV01_ANTWOORD_BERICHT = "verzendenTv01AntwoordBericht";

    private UC307Constants() {
        // niet instantieerbaar
    }
}
