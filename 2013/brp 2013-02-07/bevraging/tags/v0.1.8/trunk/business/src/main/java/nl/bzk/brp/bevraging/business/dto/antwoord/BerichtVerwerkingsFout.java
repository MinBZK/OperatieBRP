/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

/**
 * Een fout welke is opgetreden tijdens de verwerking/uitvoering van een bericht. Een fout heeft naast een melding,
 * tevens een code en een zwaarte.
 */
public class BerichtVerwerkingsFout {

    private final String                        melding;
    private final BerichtVerwerkingsFoutCode    code;
    private final BerichtVerwerkingsFoutZwaarte zwaarte;

    /**
     * Constructor die de fout direct initieert met de opgegeven waardes en waarbij het bericht van de fout
     * wordt gezet op het standaardbericht behorende bij de fout code.
     *
     * @param code de code van de fout.
     * @param zwaarte de zwaarte van de fout.
     */
    public BerichtVerwerkingsFout(final BerichtVerwerkingsFoutCode code, final BerichtVerwerkingsFoutZwaarte zwaarte)
    {
        // CHECKSTYLE:OFF
        // Kan dit niet omzetten naar normale if-statement vanwege feit dat call naar andere constructor op de eerste regel moet staan
        this(code, zwaarte, code == null ? null : code.getStandaardBericht());
        // CHECKSTYLE:ON
    }

    /**
     * Constructor die de fout direct initieert met de opgegeven waardes.
     *
     * @param code de code van de fout.
     * @param zwaarte de zwaarte van de fout.
     * @param melding de melding behorende bij de fout.
     */
    public BerichtVerwerkingsFout(final BerichtVerwerkingsFoutCode code, final BerichtVerwerkingsFoutZwaarte zwaarte,
            final String melding)
    {
        this(code, zwaarte, melding, false);
    }

    /**
     * Constructor die de fout direct initieert met de opgegeven waardes, waarbij de melding, afhankelijk van de {@code
     * concatMelding} parameter, gezet wordt op de opgegeven melding of een concatenatie van de opgegeven melding en
     * de standaard melding behorende bij de fout code.
     *
     * @param code de code van de fout.
     * @param zwaarte de zwaarte van de fout.
     * @param melding de melding behorende bij de fout.
     * @param concatMelding indicatie of de opgegeven melding moet worden geconcateneerd met de standaard fout melding
     *     op basis van de fout code.
     */
    public BerichtVerwerkingsFout(final BerichtVerwerkingsFoutCode code, final BerichtVerwerkingsFoutZwaarte zwaarte,
            final String melding, final boolean concatMelding)
    {
        this.code = code;
        this.zwaarte = zwaarte;
        if (concatMelding) {
            this.melding = String.format("%s: %s", code.getStandaardBericht(), melding);
        } else {
            this.melding = melding;
        }
    }

    /**
     * Retourneert de code van de fout.
     * @return de code van de fout.
     */
    public String getCode() {
        if (code == null) {
            return "<ONBEKEND>";
        }

        return code.getCode();
    }

    /**
     * Retourneert de melding behorende bij de fout.
     * @return de melding behorende bij de fout.
     */
    public String getMelding() {
        return melding;
    }

    /**
     * Retourneert de zwaarte van de fout.
     * @return de zwaarte van de fout.
     */
    public BerichtVerwerkingsFoutZwaarte getZwaarte() {
        return zwaarte;
    }

}
