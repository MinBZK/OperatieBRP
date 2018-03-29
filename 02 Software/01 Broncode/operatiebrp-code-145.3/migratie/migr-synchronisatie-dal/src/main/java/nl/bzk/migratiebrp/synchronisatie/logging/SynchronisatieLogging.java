/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.logging;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;

/**
 * Logging van controle, resultaten en beslissing tijdens de synchronisatie.
 */
public final class SynchronisatieLogging {

    private static final String NEXTLINE = "\n";
    private static final String NEXTITEM = ", ";

    private static final ThreadLocal<SynchronisatieLogging> CONTEXT = new ThreadLocal<>();

    private final StringBuilder sb = new StringBuilder();
    private final StringBuilder beslisboom = new StringBuilder();
    private String laatsteBeslissing;
    private String beslisResultaat;
    private final StringBuilder deltaResultaat = new StringBuilder();

    /**
     * Constructor.
     */
    private SynchronisatieLogging() {
        // private constructor
    }

    /**
     * Initialiseren.
     */
    public static void init() {
        CONTEXT.set(new SynchronisatieLogging());
    }

    /**
     * Melding toevoegen.
     * @param melding melding
     */
    public static void addMelding(final String melding) {
        CONTEXT.get().append(melding);
    }

    /**
     * Beslissing toevoegen.
     * @param beslissing beslissing
     */
    public static void addBeslissing(final SynchronisatieBeslissing beslissing) {
        CONTEXT.get().beslissing(beslissing);
    }

    /**
     * Resultaat toevoegen.
     * @param resultaat resultaat
     */
    public static void setResultaat(final StatusType resultaat) {
        CONTEXT.get().resultaat(resultaat);
    }

    /**
     * Resultaat Delta toevoegen.
     * @param resultaat resultaat
     */
    public static void addDeltaResultaat(final SoortAdministratieveHandeling resultaat) {
        CONTEXT.get().deltaResultaat(resultaat);
    }

    /**
     * Retourneer de opgebouwde melding.
     * @return melding
     */
    public static String getMelding() {
        return CONTEXT.get().getString();
    }

    private void append(final String melding) {
        if (sb.length() > 0) {
            sb.append(NEXTLINE);
        }
        sb.append(melding);
    }

    /**
     * Voeg een beslissing toe aan de beslisboom en zet tegelijk de laatste beslissing.
     * @param beslissing beslissing
     */
    void beslissing(final SynchronisatieBeslissing beslissing) {
        if (beslisboom.length() != 0) {
            beslisboom.append(",");
        }
        beslisboom.append(beslissing.getCode());
        laatsteBeslissing = beslissing.getCode();
    }

    /**
     * Voeg het resultaat toe van de verwerking (voeg ook de beslisboom toe aan het resulterende bericht).
     * @param resultaat resultaat
     */
    void resultaat(final StatusType resultaat) {
        beslisResultaat = resultaat.toString();
    }

    /**
     * Geef het complete bericht.
     * @return bericht
     */
    String getString() {
        if (deltaResultaat.length() > 0) {
            sb.insert(0, maakDeltaTotaalResultaat() + NEXTLINE);
        }
        // Resultaat en beslisboom vooraan
        sb.insert(0, "Beslissingen: " + beslisboom.toString() + NEXTLINE);
        sb.insert(0, "Resultaat: " + beslisResultaat + " (" + laatsteBeslissing + ")" + NEXTLINE);
        return sb.toString();
    }

    /**
     * Zet het resultaat van IST binnen relateren.
     * @param resultaat het resultaat van IST binnen relateren
     */
    void deltaResultaat(final SoortAdministratieveHandeling resultaat) {
        if (deltaResultaat.length() != 0) {
            deltaResultaat.append(NEXTITEM);
        }
        deltaResultaat.append(resultaat.getNaam());
    }

    private String maakDeltaTotaalResultaat() {
        final StringBuilder result = new StringBuilder("Deltabepaling: ");
        if (deltaResultaat.length() > 0) {
            result.append(deltaResultaat.toString()).append(NEXTITEM);
        }

        result.setLength(result.length() - NEXTITEM.length());

        return result.toString();
    }
}
