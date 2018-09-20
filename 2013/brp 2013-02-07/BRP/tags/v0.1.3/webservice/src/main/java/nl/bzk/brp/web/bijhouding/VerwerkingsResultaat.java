/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

/**
 * Enumaratie voor het verwerkingsresultaat van een bijhouding, welke aangeeft of de verwerking/bijhouding goed is
 * gegaan of fout.
 */
public enum VerwerkingsResultaat {

    /**  Verwerking geslaagd (mogelijk I/W/O-meldingen en/of 'Overrules' in response opgenomen). */
    GOED("G"),
    /**
     * Verwerking gefaald/foutief (mogelijk I/W/O-meldingen en/of 'Overrules; zeker F-meldingen in response opgenomen).
     */
    FOUT("F");

    private final String code;

    /**
     * Standaard constructor die de code van het verwerkingsresultaat zet.
     * @param code de code van het verwerkingsresultaat.
     */
    private VerwerkingsResultaat(final String code) {
        this.code = code;
    }

    /**
     * Retourneert de code van het verwerkingsresultaat.
     * @return de code van het verwerkingsresultaat.
     */
    public String getCode() {
        return code;
    }

}
