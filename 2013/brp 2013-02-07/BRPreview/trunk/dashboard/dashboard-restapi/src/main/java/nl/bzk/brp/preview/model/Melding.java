/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De Class Melding.
 */
public class Melding {

    /** De soort. */
    private MeldingSoort soort;

    /** De tekst. */
    private String tekst;

    /**
     * Instantieert een nieuwe melding.
     */
    public Melding() {
    }

    /**
     * Instantieert een nieuwe melding.
     *
     * @param soort de soort
     * @param tekst de tekst
     */
    public Melding(final MeldingSoort soort, final String tekst) {
        this.soort = soort;
        this.tekst = tekst;
    }

    /**
     * Haalt een soort op.
     *
     * @return soort
     */
    public MeldingSoort getSoort() {
        return soort;
    }

    /**
     * Instellen van soort.
     *
     * @param soort de nieuwe soort
     */
    public void setSoort(final MeldingSoort soort) {
        this.soort = soort;
    }

    /**
     * Haalt een tekst op.
     *
     * @return tekst
     */
    public String getTekst() {
        return tekst;
    }

    /**
     * Instellen van tekst.
     *
     * @param tekst de nieuwe tekst
     */
    public void setTekst(final String tekst) {
        this.tekst = tekst;
    }

}
