/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

/**
 * Een index regel voor het tonen van een overzicht.
 */
public final class GgoResultaatIndex {

    private final String omschrijving;
    private final int aantalMeldingen;
    private final GgoResultaat resultaat;

    /**
     * @param aantalMeldingen
     *            int
     * @param resultaat
     *            GgoResultaat
     */
    public GgoResultaatIndex(final int aantalMeldingen, final GgoResultaat resultaat) {
        omschrijving = resultaat != null ? resultaat.getLabel() : "";
        this.aantalMeldingen = aantalMeldingen;
        this.resultaat = resultaat;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return the omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van aantal meldingen.
     *
     * @return the aantalMeldingen
     */
    public int getAantalMeldingen() {
        return aantalMeldingen;
    }

    /**
     * Geef de waarde van resultaat.
     *
     * @return the resultaat
     */
    public GgoResultaat getResultaat() {
        return resultaat;
    }
}
