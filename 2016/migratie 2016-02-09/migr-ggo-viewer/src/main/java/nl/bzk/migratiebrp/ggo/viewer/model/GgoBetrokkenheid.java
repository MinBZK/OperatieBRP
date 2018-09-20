/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Modelleert een Betrokkenheid.
 */
public class GgoBetrokkenheid implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long aNummer;
    private int brpStapelNr;
    private String label;
    private final List<GgoStapel> stapels = new ArrayList<>();
    private Set<GgoBrpOnderzoek> onderzoeken = new LinkedHashSet<>();

    /**
     * @return the aNummer
     */
    public final Long getaNummer() {
        return aNummer;
    }

    /**
     * @param aNummer
     *            the aNummer to set
     */
    public final void setaNummer(final Long aNummer) {
        this.aNummer = aNummer;
    }

    /**
     * Geef de waarde van brp stapel nr.
     *
     * @return the brpStapelNr
     */
    public final int getBrpStapelNr() {
        return brpStapelNr;
    }

    /**
     * Zet de waarde van brp stapel nr.
     *
     * @param brpStapelNr
     *            the brpStapelNr to set
     */
    public final void setBrpStapelNr(final int brpStapelNr) {
        this.brpStapelNr = brpStapelNr;
    }

    /**
     * Geef de waarde van label.
     *
     * @return the label
     */
    public final String getLabel() {
        return label;
    }

    /**
     * Zet de waarde van label.
     *
     * @param label
     *            the label to set
     */
    public final void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Geef de waarde van stapels.
     *
     * @return the stapels
     */
    public final List<GgoStapel> getStapels() {
        return stapels;
    }

    /**
     * @param stapel
     *            Voeg deze toe aan de stapels.
     */
    public final void addStapel(final GgoStapel stapel) {
        stapels.add(stapel);
    }

    /**
     * Geef de waarde van onderzoeken.
     *
     * @return the onderzoeken
     */
    public final Set<GgoBrpOnderzoek> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * Zet de waarde van onderzoeken.
     *
     * @param onderzoeken
     *            the onderzoeken to set
     */
    public final void setOnderzoeken(final Set<GgoBrpOnderzoek> onderzoeken) {
        if (onderzoeken != null && onderzoeken.size() > 0) {
            this.onderzoeken = onderzoeken;
        }
    }
}
