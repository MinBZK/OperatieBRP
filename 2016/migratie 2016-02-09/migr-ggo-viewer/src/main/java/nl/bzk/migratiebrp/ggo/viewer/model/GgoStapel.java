/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representeert de Stapel. Gebruikt voor zowel de Lo3 als de BRP kant.
 */
public class GgoStapel implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String label;
    private String omschrijving;
    private final List<GgoVoorkomen> voorkomens;

    /**
     * Constructor bij geen omschrijving.
     * 
     * @param label
     *            Het label.
     */
    public GgoStapel(final String label) {
        this.label = label;
        omschrijving = null;
        voorkomens = new ArrayList<>();
    }

    /**
     * Geef de waarde van label.
     *
     * @return label
     */
    public final String getLabel() {
        return label;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public final String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van voorkomens.
     *
     * @return voorkomens
     */
    public final List<GgoVoorkomen> getVoorkomens() {
        return voorkomens;
    }

    /**
     * Zet de waarde van omschrijving.
     *
     * @param omschrijving
     *            the omschrijving to set
     */
    public final void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * @param voorkomen
     *            Het voorkomen dat wordt toegevoegd aan de lijst van voorkomens van deze stapel.
     */
    public final void addVoorkomen(final GgoVoorkomen voorkomen) {
        voorkomens.add(voorkomen);
    }
}
