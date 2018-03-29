/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.log;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStap;

/**
 * Gebruikt om fouten te melden bij het aanroepen van de verschillende services.
 *
 * Dit om ook bij problemen nog iets zinnigs op het beeld te kunnen zetten.
 */
public class FoutMelder {

    private GgoStap huidigeStap;
    private final List<GgoFoutRegel> foutRegels = new ArrayList<>();

    /**
     * Logt de (fout) melding en severity.
     * @param severity Niveau van de melding.
     * @param code Melding code.
     * @param omschrijving Omschrijving van de code.
     */
    public final void log(final LogSeverity severity, final String code, final String omschrijving) {
        log(severity, code, omschrijving, null);
    }

    /**
     * Logt de (fout) melding en severity.
     * @param severity Niveau van de melding.
     * @param code Melding code.
     * @param omschrijving Omschrijving van de code.
     * @param htmlFieldId Het id van het html component waar de fout betrekking op heeft. Deze wordt dan rood gehighlight.
     */
    public final void log(final LogSeverity severity, final String code, final String omschrijving, final String htmlFieldId) {
        foutRegels.add(new GgoFoutRegel(huidigeStap, null, severity, null, code, omschrijving, htmlFieldId));
    }

    /**
     * Log de (fout) melding en severity.
     * @param severity Niveau van de melding.
     * @param code Melding code.
     * @param e De exception die gelogt moet worden.
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

        final StringBuilder logMessage = new StringBuilder(omschrijving);
        logMessage.insert(0, e.getClass().getSimpleName() + " - ");

        foutRegels.add(new GgoFoutRegel(huidigeStap, null, severity, null, code, logMessage.toString()));
    }

    /**
     * Get de lijst met foutRegels.
     * @return get een lijst met foutRegels.
     */
    public final List<GgoFoutRegel> getFoutRegels() {
        return foutRegels;
    }

    /**
     * Geef aan welke stap op dit moment wordt uitgevoerd.
     * @param huidigeStap De huidige stap.
     */
    public final void setHuidigeStap(final GgoStap huidigeStap) {
        this.huidigeStap = huidigeStap;
    }
}
