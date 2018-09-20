/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;

/**
 * Een foutmelding met hetkomst gegevens.
 */
public final class Foutmelding {
    private final Lo3Herkomst lo3Herkomst;
    private final LogSeverity severity;
    private final SoortMeldingCode soortMeldingCode;
    private final Lo3ElementEnum lo3ElementNummer;

    /**
     * Maak een foutmelding met de gegeven data.
     * 
     * @param lo3Herkomst
     *            de herkomst set
     * @param severity
     *            de ernst van de fout
     * @param soortMeldingCode
     *            de soort melding
     * @param lo3ElementNummer
     *            het lo3 element waar de melding betrekking op heeft. Alleen noodzakelijk als de omschrijving van de
     *            melding niet duidelijk maakt op welke element deze betrekking heeft.
     */
    private Foutmelding(
        final Lo3Herkomst lo3Herkomst,
        final LogSeverity severity,
        final SoortMeldingCode soortMeldingCode,
        final Lo3ElementEnum lo3ElementNummer)
    {
        this.lo3Herkomst = lo3Herkomst;
        this.severity = severity;
        this.soortMeldingCode = soortMeldingCode;
        this.lo3ElementNummer = lo3ElementNummer;
    }

    /**
     * Maak een foutmelding voor de specifieke melding.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param soortMeldingCode
     *            de soort melding
     * @param lo3ElementNummer
     *            het lo3 element waar de melding betrekking op heeft. Alleen noodzakelijk als de omschrijving van de
     *            melding niet duidelijk maakt op welke element deze betrekking heeft.
     * 
     * @return de foutmelding
     */
    static Foutmelding maakMeldingFout(
        final Lo3Herkomst lo3Herkomst,
        final LogSeverity severity,
        final SoortMeldingCode soortMeldingCode,
        final Lo3ElementEnum lo3ElementNummer)
    {
        return new Foutmelding(lo3Herkomst, severity, soortMeldingCode, lo3ElementNummer);
    }

    /**
     * Log een foutmelding voor de specifieke soort melding.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param soortMeldingCode
     *            de soort melding
     * @param lo3ElementNummer
     *            het lo3 element waar de melding betrekking op heeft. Alleen noodzakelijk als de omschrijving van de
     *            melding niet duidelijk maakt op welke element deze betrekking heeft.
     */
    public static void logMeldingFout(
        final Lo3Herkomst lo3Herkomst,
        final LogSeverity severity,
        final SoortMeldingCode soortMeldingCode,
        final Lo3ElementEnum lo3ElementNummer)
    {
        maakMeldingFout(lo3Herkomst, severity, soortMeldingCode, lo3ElementNummer).log();
    }

    /**
     * Log een foutmelding voor de specifieke soort melding met severity: INFO.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param soortMeldingCode
     *            de soort melding
     * @param lo3ElementNummer
     *            het lo3 element waar de melding betrekking op heeft. Alleen noodzakelijk als de omschrijving van de
     *            melding niet duidelijk maakt op welke element deze betrekking heeft.
     */
    public static void logMeldingFoutInfo(final Lo3Herkomst lo3Herkomst, final SoortMeldingCode soortMeldingCode, final Lo3ElementEnum lo3ElementNummer) {
        logMeldingFout(lo3Herkomst, LogSeverity.INFO, soortMeldingCode, lo3ElementNummer);
    }

    /**
     * Geef de waarde van lo3 herkomst.
     *
     * @return de herkomst set
     */
    private Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * Geef de waarde van severity.
     *
     * @return de severity
     */
    private LogSeverity getSeverity() {
        return severity;
    }

    /**
     * Geef de waarde van soort melding code.
     *
     * @return soort melding code
     */
    public SoortMeldingCode getSoortMeldingCode() {
        return soortMeldingCode;
    }

    /**
     * Geef de waarde van lo3 element nummer.
     *
     * @return lo3 element nummer
     */
    public Lo3ElementEnum getLo3ElementNummer() {
        return lo3ElementNummer;
    }

    /**
     * Log een fout.
     * 
     */
    void log() {
        Logging.log(getLo3Herkomst(), getSeverity(), getSoortMeldingCode(), getLo3ElementNummer());
    }
}
