/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.ElementList;

/**
 * Logging wrapper.
 */
public final class Logging {

    private static final String LOGGING_NOT_INITIALIZED_MESG = "Logging not initialized.";
    private static final ThreadLocal<Logging> LOCAL = new ThreadLocal<Logging>();
    @ElementList(name = "logging", inline = true, type = LogRegel.class)
    private final List<LogRegel> regels;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Simple XML constructor.
     * 
     * @param regels
     *            regels
     */
    public Logging(@ElementList(name = "logging", inline = true, type = LogRegel.class) final List<LogRegel> regels) {
        if (regels == null) {
            throw new NullPointerException("regels mag niet null zijn");
        }
        this.regels = regels;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Init thread local logging context.
     */
    public static void initContext() {
        LOCAL.set(new Logging(new ArrayList<LogRegel>()));
    }

    /**
     * Init thread local logging context.
     * 
     * @param logging
     *            logging
     */
    public static void initContext(final Logging logging) {
        LOCAL.set(logging);
    }

    /**
     * Get the logging.
     * 
     * @return logging
     */
    public static Logging getLogging() {
        return LOCAL.get();
    }

    /**
     * Destroy thread local logging context.
     */
    public static void destroyContext() {
        LOCAL.remove();
    }

    /**
     * Log.
     * 
     * @param lo3Herkomst
     *            LO3 herkomst
     * @param severity
     *            severity
     * @param type
     *            type
     * @param code
     *            code
     * @param omschrijving
     *            omschrijving
     */
    public static void log(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final LogType type,
            final String code,
            final String omschrijving) {
        final Logging logging = LOCAL.get();

        if (logging == null) {
            throw new IllegalStateException(LOGGING_NOT_INITIALIZED_MESG);
        }

        logging.addLogRegel(lo3Herkomst, severity, type, code, omschrijving);
    }

    /**
     * /** Voegt de gegeven regel toe aan het log.
     * 
     * @param log
     *            de regel die aan het log toegevoegd moet worden
     */
    public static void log(final LogRegel log) {
        final Logging logging = LOCAL.get();

        if (logging == null) {
            throw new IllegalStateException(LOGGING_NOT_INITIALIZED_MESG);
        }

        logging.addLogRegel(log);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * @return log regels
     */
    public List<LogRegel> getRegels() {
        return Collections.unmodifiableList(regels);
    }

    /**
     * Geeft het maximale foutniveau van de gegeven regels.
     * 
     * @param regels
     *            de logregels die gegenereerd zijn
     * @return het maximale foutniveau van de gegeven regels.
     */
    public static LogSeverity getMaxSeverity(final List<LogRegel> regels) {
        LogSeverity severity = null;

        for (final LogRegel regel : regels) {
            if (severity == null || severity.getSeverity() < regel.getSeverity().getSeverity()) {
                severity = regel.getSeverity();
            }
        }

        return severity;
    }

    /**
     * @param regels
     *            de logregels waarin gecontroleerd moet worden of het niveau ERROR voorkomt
     * @return true als er een logregel is met het niveau ERROR
     */
    public static boolean containSeverityLevelError(final List<LogRegel> regels) {
        final LogSeverity severity = getMaxSeverity(regels);
        return severity != null && severity.compareTo(LogSeverity.ERROR) == 0;
    }

    /**
     * @param regels
     *            de logregels waarin gecontroleerd moet worden of het niveau ERROR voorkomt
     * @return true als er een logregel is met het niveau CRITICAL
     */
    public static boolean containSeverityLevelCritical(final List<LogRegel> regels) {
        final LogSeverity severity = getMaxSeverity(regels);
        return severity != null && severity.compareTo(LogSeverity.CRITICAL) == 0;
    }

    /**
     * @return (hoogste) severity
     */
    public LogSeverity getSeverity() {
        return getMaxSeverity(regels);
    }

    /**
     * @return true als er een logregel is met het niveau CRITICAL.
     */
    public boolean containSeverityLevelCritical() {
        return containSeverityLevelCritical(regels);
    }

    /**
     * @return true als er een logregel is met het niveau ERROR.
     */
    public boolean containSeverityLevelError() {
        return containSeverityLevelError(regels);
    }

    /**
     * Log.
     * 
     * @param lo3Herkomst
     *            LO3 herkomst
     * @param severity
     *            severity
     * @param type
     *            type
     * @param code
     *            code
     * @param omschrijving
     *            omschrijving
     */
    public void addLogRegel(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final LogType type,
            final String code,
            final String omschrijving) {
        regels.add(new LogRegel(lo3Herkomst, severity, type, code, omschrijving));
    }

    /**
     * Voegt de gegeven regel toe aan het log.
     * 
     * @param regel
     *            de regel die aan het log toegevoegd moet worden
     */
    public void addLogRegel(final LogRegel regel) {
        regels.add(regel);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("regels", regels).toString();
    }

}
