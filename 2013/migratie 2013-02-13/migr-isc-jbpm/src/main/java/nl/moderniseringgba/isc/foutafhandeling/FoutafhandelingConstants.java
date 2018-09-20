/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

/**
 * Constanten voor communicatie tijdens ISC-foutafhandeling.
 */
public final class FoutafhandelingConstants {

    // Indicatie variabelen
    /** Variabele 'indicatieBeheerder'. */
    public static final String INDICATIE_BEHEERDER = "indicatieBeheerder";

    /** Variabele 'indicatiePf'. */
    public static final String INDICATIE_PF = "indicatiePf";
    /** Variabele 'indicatieVb01'. */
    public static final String INDICATIE_VB01 = "indicatieVb01";
    /** Variabele 'indicatieDeblokkering'. */
    public static final String INDICATIE_DEBLOKKERING = "indicatieDeblokkering";
    /** Variabele 'indicatieFoutnotificatie'. */
    public static final String INDICATIE_ANTWOORD = "indicatieAntwoord";

    // Bericht variabelen
    /** Variabele 'lo3Bericht'. */
    public static final String BERICHT_LO3 = "lo3Bericht";
    /** Variabele 'blokkeringBericht'. */
    public static final String BERICHT_BLOKKERING = "blokkeringBericht";
    /** Variabele 'brpBericht'. */
    public static final String BERICHT_BRP = "brpBericht";

    /** Variable 'deblokkeringVerzoekBericht'. */
    public static final String BERICHT_VERZOEK_DEBLOKKERING = "deblokkeringVerzoekBericht";
    /** Variable 'deblokkeringAntwoordBericht'. */
    public static final String BERICHT_ANTWOORD_DEBLOKKERING = "deblokkeringAntwoordBericht";

    /** Variable 'brpAntwoordBericht'. */
    public static final String BERICHT_BRP_ANTWOORD = "brpAntwoordBericht";

    /** Variable 'pfBericht'. */
    public static final String BERICHT_PF = "pfBericht";
    /** Variable 'vb01Bericht'. */
    public static final String BERICHT_VB01 = "vb01Bericht";

    // Foutafhandeling variabelen
    /** Variabele 'fout'. */
    public static final String FOUT = "fout";
    /** Variabele 'foutmelding'. */
    public static final String FOUTMELDING = "foutmelding";
    /** Constante ':'. */
    public static final String FOUTMELDING_SCHEIDINGSTEKEN = ":";
    /** Variabele 'functioneleStap'. */
    public static final String STAP = "functioneleStap";
    // Paden
    /** Variabele 'foutafhandelingPaden'. */
    public static final String PADEN = "foutafhandelingPaden";

    // Restart
    /** Variabele 'restart'. */
    public static final String RESTART = "restart";

    // Fout registratie
    /** Variabele 'registratieId'. */
    public static final String REGISTRATIE_ID = "foutId";
    /** Variabele 'bronGemeente'. */
    public static final String BRON_GEMEENTE = "bronGemeente";
    /** Variabele 'doelGemeente'. */
    public static final String DOEL_GEMEENTE = "doelGemeente";

    private FoutafhandelingConstants() {
        throw new AssertionError("Niet instantieerbaar");
    }

}
