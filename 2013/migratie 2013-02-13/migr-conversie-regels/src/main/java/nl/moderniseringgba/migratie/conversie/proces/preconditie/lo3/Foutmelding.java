/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

/**
 * Een foutmelding met hetkomst gegevens.
 */
public final class Foutmelding {
    private final Lo3Herkomst lo3Herkomst;
    private final LogSeverity severity;
    private final LogType type;
    private final String code;
    private final String omschrijving;

    /**
     * Maak een foutmelding met de gegeven data.
     * 
     * @param lo3Herkomst
     *            de herkomst set
     * @param severity
     *            de ernst van de fout
     * @param type
     *            het type fout
     * @param code
     *            de foutdcode
     * @param omschrijving
     *            de foutomschrijving
     */
    private Foutmelding(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final LogType type,
            final String code,
            final String omschrijving) {
        this.lo3Herkomst = lo3Herkomst;
        this.severity = severity;
        this.type = type;
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Maak een structuur foutmelding zonder een specifieke code.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param omschrijving
     *            de fout omschrijving
     * 
     * @return de foutmelding
     */
    static Foutmelding maakStructuurFout(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final String omschrijving) {
        return maakStructuurFout(lo3Herkomst, severity, null, omschrijving);
    }

    /**
     * Maak een structuur foutmelding met een specifieke code.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param code
     *            de foutmelding code
     * @param omschrijving
     *            de fout omschrijving
     * 
     * @return de foutmelding
     */
    static Foutmelding maakStructuurFout(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final String code,
            final String omschrijving) {
        return new Foutmelding(lo3Herkomst, severity, LogType.STRUCTUUR, code, omschrijving);
    }

    /**
     * Creëer een structuur foutmelding zonder specifieke code.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param omschrijving
     *            de fout omschrijving
     * 
     */
    static void
            logStructuurFout(final Lo3Herkomst lo3Herkomst, final LogSeverity severity, final String omschrijving) {
        maakStructuurFout(lo3Herkomst, severity, omschrijving).log();
    }

    /**
     * Log een preconditie foutmelding met de preconditie naam als specifieke code.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param preconditie
     *            de preconditie
     * @param omschrijving
     *            de fout omschrijving
     * 
     */
    static void logPreconditieFout(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final Precondities preconditie,
            final String omschrijving) {
        maakPreconditieFout(lo3Herkomst, severity, preconditie, omschrijving).log();
    }

    /**
     * Maak een preconditie foutmelding met de preconditie naam als specifieke code.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param severity
     *            de severety
     * @param preconditie
     *            de preconditie
     * @param omschrijving
     *            de fout omschrijving
     * 
     * @return De aangemaakte preconditie fout
     */
    static Foutmelding maakPreconditieFout(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final Precondities preconditie,
            final String omschrijving) {
        return new Foutmelding(lo3Herkomst, severity, LogType.PRECONDITIE, preconditie.name(), omschrijving);
    }

    /**
     * Creëer een bijzondere situatie foutmelding.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param bijzondereSituatie
     *            de bijzondere situatie
     */
    public static void logBijzondereSituatieFout(
            final Lo3Herkomst lo3Herkomst,
            final BijzondereSituaties bijzondereSituatie) {
        new Foutmelding(lo3Herkomst, LogSeverity.INFO, LogType.BIJZONDERE_SITUATIE, bijzondereSituatie.getCode(),
                bijzondereSituatie.getOmschrijving()).log();
    }

    /**
     * Creëer een bijzondere situatie foutmelding.
     * 
     * @param lo3Herkomst
     *            de herkomst
     * @param bijzondereSituatie
     *            de bijzondere situatie
     * @param groep
     *            BRP-groep de groep waar van de naam gelogd moet worden
     * @param <T>
     *            BRP-groep type
     */
    public static <T extends BrpGroepInhoud> void logBijzondereSituatieFout(
            final Lo3Herkomst lo3Herkomst,
            final BijzondereSituaties bijzondereSituatie,
            final BrpGroep<T> groep) {
        String groepNaam = groep.getInhoud().getClass().getName();
        groepNaam = groepNaam.substring(groepNaam.lastIndexOf(".") + 1);
        final String omschrijving = String.format(bijzondereSituatie.getOmschrijving(), groepNaam);
        new Foutmelding(lo3Herkomst, LogSeverity.INFO, LogType.BIJZONDERE_SITUATIE, bijzondereSituatie.getCode(),
                omschrijving).log();
    }

    /**
     * @return de herkomst set
     */
    private Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * @return de severity
     */
    private LogSeverity getSeverity() {
        return severity;
    }

    /**
     * @return het type foutmelding
     */
    private LogType getType() {
        return type;
    }

    /**
     * @return de code van de foutmelding
     */
    private String getCode() {
        return code;
    }

    /**
     * @return de fout omschrijving
     */
    private String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Log een fout.
     * 
     */
    void log() {
        Logging.log(getLo3Herkomst(), getSeverity(), getType(), getCode(), getOmschrijving());
    }
}
