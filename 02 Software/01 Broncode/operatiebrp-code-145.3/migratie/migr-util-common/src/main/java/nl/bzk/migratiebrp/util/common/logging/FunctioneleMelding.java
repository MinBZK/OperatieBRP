/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

/**
 * Functionele meldingen.
 */
public enum FunctioneleMelding implements nl.bzk.algemeenbrp.util.common.logging.FunctioneleMelding {

    //
    // Logmeldingen VOISC.
    //

    /**
     * Melding starten VOISC.
     */
    VOISC_STARTEN_APPLICATIE("VOISC001", FunctioneleMeldingConstanten.STARTEN_APPLICATIE.getMelding()),

    /**
     * Melding ongeldige parameters bij opstarten VOISC.
     */
    VOISC_ONGELDIGE_PARAMETERS("VOISC002", "Ongeldige parameters meegegeven."),

    /**
     * Melding configureren SSL verbinding naar Mailbox.
     */
    VOISC_CONFIGUREREN_SSL("VOISC003", "Configureren SSL verbinding"),

    /**
     * Melding starten VOISC jobs.
     */
    VOISC_STARTEN_JOBS("VOISC004", "Starten jobs."),

    /**
     * Melding starten VOISC queue listeners.
     */
    VOISC_STARTEN_QUEUELISTENERS("VOISC005", "Starten queues."),

    /**
     * Melding starten VOISC voltooid.
     */
    VOISC_APPLICATIE_CORRECT_GESTART("VOISC006", FunctioneleMeldingConstanten.APPLICATIE_CORRECT_GESTART.getMelding()),

    /**
     * Melding testen connectie tussen VOISC en mailbox.
     */
    VOISC_CONNECTIE_MAILBOX_TESTEN("VOISC007", "Testen connectie mailbox."),

    /**
     * Melding controleren configuratie mailbox.
     */
    VOISC_CONFIGURATIE_MAILBOX_TESTEN("VOISC008", "Testen configuratie mailbox."),

    /**
     * Melding connectie met mailbox niet getest.
     */
    VOISC_CONNECTIE_MAILBOX_NIET_GETEST("VOISC009", "Testen connectie mailbox overgeslagen."),

    /**
     * Melding configuratie mailbox niet getest.
     */
    VOISC_CONFIGURATIE_MAILBOX_NIET_GETEST("VOISC010", "Testen configuratie mailbox overgeslagen."),

    /**
     * Melding bericht ontvangen.
     */
    VOISC_ISC_ONTVANGEN("VOISC100", "Bericht van ISC ontvangen."),

    /**
     * Melding bericht verstuurd.
     */
    VOISC_MAILBOX_ONTVANGEN("VOISC101", "Bericht van mailbox ontvangen."),

    /**
     * Melding bericht verstuurd.
     */
    VOISC_MAILBOX_VERSTUURD("VOISC110", "Bericht naar mailbox verstuurd."),

    /**
     * Melding bericht verstuurd.
     */
    VOISC_ISC_VERSTUURD("VOISC111", "Bericht naar ISC verstuurd."),

    //
    // Logmeldingen ISC.
    //

    /**
     * Melding starten ISC.
     */
    ISC_STARTEN_APPLICATIE("ISC001", FunctioneleMeldingConstanten.STARTEN_APPLICATIE.getMelding()),

    /**
     * Melding starten ISC voltooid.
     */
    ISC_APPLICATIE_CORRECT_GESTART("ISC002", FunctioneleMeldingConstanten.APPLICATIE_CORRECT_GESTART.getMelding()),

    /**
     * Melding opschoner uitgevoerd.
     */
    ISC_OPSCHONER_UITGEVOERD("ISC003", "Opschoner uitgevoerd."),

    /**
     * Melding verzoek verwerkt.
     */
    ISC_VOISC_VERWERKT("ISC100", "VOISC bericht verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    ISC_SYNC_VERWERKT("ISC101", "SYNC bericht verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    ISC_LEVERING_VERWERKT("ISC102", "Levering bericht verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    ISC_VOISC_VERSTUURD("ISC110", "VOISC bericht verstuurd"),

    /**
     * Melding verzoek verwerkt.
     */
    ISC_SYNC_VERSTUURD("ISC111", "SYNC bericht verstuurd"),

    /**
     * Melding job verwerkt.
     */
    ISC_TIMER_VERWERKT("ISC120", "Timer verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    ISC_JOB_VERWERKT("ISC121", "Job verwerkt"),

    /**
     * Melding ISC gestopt.
     */
    ISC_APPLICATIE_GESTOPT("ISC122", "Applicatie gestopt."),

    //
    // Logmeldingen ROUTERING.
    //

    /**
     * Melding starten ROUTERING.
     */
    ROUTERING_STARTEN_APPLICATIE("ROUTE001", FunctioneleMeldingConstanten.STARTEN_APPLICATIE.getMelding()),

    /**
     * Melding starten ROUTERING voltooid.
     */
    ROUTERING_APPLICATIE_CORRECT_GESTART("ROUTE002", FunctioneleMeldingConstanten.APPLICATIE_CORRECT_GESTART.getMelding()),

    /**
     * Melding ROUTERING gestopt.
     */
    ROUTERING_APPLICATIE_GESTOPT("ROUTE003", "Applicatie gestopt."),

    //
    // Logmeldingen SYNC.
    //

    /**
     * Melding starten SYNC.
     */
    SYNC_STARTEN_APPLICATIE("SYNC001", FunctioneleMeldingConstanten.STARTEN_APPLICATIE.getMelding()),

    /**
     * Melding starten SYNC voltooid.
     */
    SYNC_APPLICATIE_GESTART("SYNC002", FunctioneleMeldingConstanten.APPLICATIE_CORRECT_GESTART.getMelding()),

    /**
     * Melding SYNC verzoek verwerkt.
     */
    SYNC_VERZOEK_VERWERKT("SYNC100", "Sync verzoek verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    SYNC_PARTIJREGISTER_VERWERKT("SYNC101", "Partijregister verzoek verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    SYNC_AUTORISATIEREGISTER_VERWERKT("SYNC102", "Autorisatieregister verzoek verwerkt"),

    /**
     * Melding verzoek verwerkt.
     */
    SYNC_ARCHIVERING_VERWERKT("SYNC103", "Archivering verzoek verwerkt"),

    /**
     * Melding notificatie verwerkt.
     */
    SYNC_AFNEMERSINDICATIE_VERWERKT("SYNC200", "Afnemersindicaties notificatie verwerkt"),

    /**
     * Melding notificatie verwerkt.
     */
    SYNC_TOEVALLIGEGEBEURTENIS_VERWERKT("SYNC201", "Toevallige gebeurtenis notificatie verwerkt"),

    /**
     * Melding notificaitie verwerkt.
     */
    SYNC_LEVERING_VERWERKT("SYNC202", "Levering notificatie verwerkt"),

    //
    // Logmeldingen NOTIFICATIE.
    //

    /**
     * Melding notificatie bericht verwerkt.
     */
    ISC_NOTIFICATIE_VERWERKT("NOTIFICATIE100", "NOTIFICATIE bericht verwerkt");

    private final String code;
    private final String omschrijving;

    /**
     * Constructor.
     * @param code code
     * @param omschrijving omschrijving
     */
    FunctioneleMelding(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Geeft de code van de functionele melding terug.
     * @return De code van de functionele melding.
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Geeft de omschrijving van de functionele melding terug.
     * @return De omschrijving van de functionele melding.
     */
    @Override
    public String getOmschrijving() {
        return omschrijving;
    }
}
