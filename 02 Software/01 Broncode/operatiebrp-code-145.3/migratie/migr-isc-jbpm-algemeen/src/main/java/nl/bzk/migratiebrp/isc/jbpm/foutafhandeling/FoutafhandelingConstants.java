/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

/**
 * Constanten voor communicatie tijdens ISC-foutafhandeling.
 */
public final class FoutafhandelingConstants {

    // Indicatie variabelen
    /**
     * Variabele 'indicatieBeheerder'.
     */
    public static final String INDICATIE_BEHEERDER = "indicatieBeheerder";
    /**
     * Variabele 'indicatieCyclusFout'.
     */
    public static final String INDICATIE_CYCLUS_FOUT = "indicatieCyclusFout";
    /**
     * Variabele 'indicatiePf'.
     */
    public static final String INDICATIE_PF = "indicatiePf";
    /**
     * Variabele 'indicatieVb'.
     */
    public static final String INDICATIE_VB = "indicatieVb";

    // Bericht variabelen
    /**
     * Variabele 'lo3Bericht'.
     */
    public static final String BERICHT_LO3 = "lo3Bericht";
    /**
     * Variabele 'blokkeringBericht'.
     */
    public static final String BERICHT_BLOKKERING = "blokkeringBericht";
    /**
     * Variabele 'brpBericht'.
     */
    public static final String BERICHT_BRP = "brpBericht";

    /**
     * Variable 'deblokkeringVerzoekBericht'.
     */
    public static final String BERICHT_VERZOEK_DEBLOKKERING = "deblokkeringVerzoekBericht";
    /**
     * Variable 'deblokkeringAntwoordBericht'.
     */
    public static final String BERICHT_ANTWOORD_DEBLOKKERING = "deblokkeringAntwoordBericht";

    /**
     * Variable 'brpAntwoordBericht'.
     */
    public static final String BERICHT_BRP_ANTWOORD = "brpAntwoordBericht";

    /**
     * Variable 'pfBericht'.
     */
    public static final String BERICHT_PF = "pfBericht";
    /**
     * Variable 'vb01Bericht'.
     */
    public static final String BERICHT_VB = "vbBericht";
    /**
     * Variabele 'overigBericht'.
     */
    public static final String BERICHT_OVERIG = "overigBericht";

    // Foutafhandeling variabelen
    /**
     * Variabele 'fout'.
     */
    public static final String FOUT = "fout";
    /**
     * Variabele 'foutmelding'.
     */
    public static final String FOUTMELDING = "foutmelding";
    /**
     * Constante ':'.
     */
    public static final String FOUTMELDING_SCHEIDINGSTEKEN = ":";
    /**
     * Variabele 'functioneleStap'.
     */
    public static final String STAP = "functioneleStap";

    // Paden
    /**
     * Variabele 'foutafhandelingPaden'.
     */
    public static final String PADEN = "foutafhandelingPaden";

    // Restart
    /**
     * Variabele 'restart'.
     */
    public static final String RESTART = "restart";

    // Fout registratie
    /**
     * Variabele 'registratieId'.
     */
    public static final String REGISTRATIE_ID = "foutId";
    /**
     * Variabele 'bronPartijCode'.
     */
    public static final String BRON_PARTIJ_CODE = "bronPartijCode";
    /**
     * Variabele 'doelPartijCode'.
     */
    public static final String DOEL_PARTIJ_CODE = "doelPartijCode";

    // Afhandeling type
    /**
     * Variabele 'afhandelingType'.
     */
    public static final String AFHANDELING_TYPE = "afhandelingType";

    // Overzicht met persoonslijsten
    /**
     * Variabele 'persoonslijstOverzicht'.
     */
    public static final String PERSOONSLIJSTOVERZICHT = "persoonslijstOverzicht";

    /**
     * Static melding met betrekking tot de execution context.
     */
    public static final String FOUTMELDING_GEEN_EXECUTION_CONTEXT = "JbpmRapportageDao moet binnen een geldige execution context worden gebruikt.";

    /**
     * Static melding met betrekking tot het ontbreken van een procesinstantie.
     */
    public static final String FOUTMELDING_GEEN_GESTARTE_PROCES_INSTANTIE = "JbpmRapportageDao moet binnen een gestart proces worden gebruikt.";

    private FoutafhandelingConstants() {
        throw new AssertionError("Niet instantieerbaar");
    }

}
