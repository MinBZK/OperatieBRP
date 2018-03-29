/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging;

import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Synchronisatie logging voor persoonslijst controles.
 */
public final class ControleLogging {

    private final ControleMelding controleMelding;

    /**
     * Constructor (en log start).
     * @param controleMelding melding
     */
    public ControleLogging(final ControleMelding controleMelding) {
        this.controleMelding = controleMelding;
        SynchronisatieLogging.addMelding(controleMelding.getKey() + ": " + controleMelding.getOmschrijving());
    }

    /**
     * Log waarden aangeboden persoonlijst.
     * @param waarden waarden
     */
    public void logAangebodenWaarden(final Object... waarden) {
        SynchronisatieLogging.addMelding(String.format("[%s] Waarden aangeboden persoonslijst: %s", controleMelding.getKey(), arrayToString(waarden)));
    }

    /**
     * Log waarden gevonden persoonslijst.
     * @param waarden waarden
     */
    public void logGevondenWaarden(final Object... waarden) {
        SynchronisatieLogging.addMelding(String.format("[%s] Waarden gevonden persoonslijst: %s", controleMelding.getKey(), arrayToString(waarden)));
    }

    /**
     * Log resultaat.
     * @param resultaat resultaat
     */
    public void logResultaat(final boolean resultaat) {
        logResultaat(resultaat, false);
    }

    /**
     * Log resultaat.
     * @param resultaat resultaat
     * @param voegWitregelToe indicatie of een witregel toegevoegd moet worden.
     */
    public void logResultaat(final boolean resultaat, final boolean voegWitregelToe) {
        if (voegWitregelToe) {
            SynchronisatieLogging.addMelding(String.format("[%s] Resultaat: %s%n", controleMelding.getKey(), resultaat));
        } else {
            SynchronisatieLogging.addMelding(String.format("[%s] Resultaat: %s", controleMelding.getKey(), resultaat));
        }
    }

    /**
     * Log resultaat.
     * @param controleUitkomst Het resultaat
     */
    public void logResultaat(final ControleUitkomst controleUitkomst) {
        SynchronisatieLogging.addMelding(String.format("[%s] Resultaat: %s", controleMelding.getKey(), controleUitkomst.getResultaat()));
    }

    /**
     * Log een melding (in context van de controle).
     * @param melding melding
     */
    public void addMelding(final String melding) {
        SynchronisatieLogging.addMelding(String.format("[%s] %s", controleMelding.getKey(), melding));
    }

    private static String arrayToString(final Object[] objecten) {
        final StringBuilder result = new StringBuilder();
        if (objecten != null) {
            for (final Object object : objecten) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(objectToString(object));
            }
        }
        return result.toString();
    }

    private static String objectToString(final Object object) {
        if (object == null) {
            return "<leeg>";
        } else {
            return object.toString();
        }
    }
}
