/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.log;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

/**
 * Gebruikt om fouten te melden bij het aanroepen van de verschillende services.
 * 
 * Dit om ook bij problemen nog iets zinnigs op het beeld te kunnen zetten.
 * 
 * We gebruiken hiervoor de FoutRegel structuur.
 */
public class FoutMelder {

    private final List<FoutRegel> foutRegels = new ArrayList<FoutRegel>();

    /**
     * Logt de (fout) melding en severity.
     * 
     * @param severity
     *            Niveau van de melding.
     * @param code
     *            Melding code.
     * @param omschrijving
     *            Omschrijving van de code.
     */
    public final void log(final LogSeverity severity, final String code, final String omschrijving) {
        foutRegels.add(new FoutRegel(null, severity, null, code, omschrijving));
    }

    /**
     * Log de (fout) melding en severity.
     * 
     * @param severity
     *            Niveau van de melding.
     * @param code
     *            Melding code.
     * @param e
     *            De exception die gelogt moet worden.
     */
    public final void log(final LogSeverity severity, final String code, final Exception e) {
        String omschrijving = e.getMessage();

        if (omschrijving == null) {
            if (e.getStackTrace().length > 0) {
                omschrijving = "at " + e.getStackTrace()[0].toString();
            } else {
                omschrijving = e.toString();
            }
        }

        final StringBuffer logMessage = new StringBuffer(omschrijving);
        logMessage.insert(0, e.getClass().getSimpleName() + " - ");

        foutRegels.add(new FoutRegel(null, severity, null, code, logMessage.toString()));
    }

    /**
     * Get de lijst met foutRegels.
     * 
     * @return get een lijst met foutRegels.
     */
    public final List<FoutRegel> getFoutRegels() {
        return foutRegels;
    }
}
