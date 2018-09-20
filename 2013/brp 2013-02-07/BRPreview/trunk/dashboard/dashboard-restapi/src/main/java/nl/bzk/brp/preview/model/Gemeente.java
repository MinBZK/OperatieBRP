/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;


/**
 * De Class Gemeente.
 */
public class Gemeente {

    /** De code. */
    private String code;

    /** De naam. */
    private String naam;

    /**
     * Instantieert een nieuwe gemeente.
     */
    public Gemeente() {
    }

    /**
     * Instantieert een nieuwe gemeente.
     *
     * @param naam de naam
     */
    public Gemeente(final String naam) {
        this.naam = naam;
    }

    /**
     * Haalt een code op.
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Instellen van code.
     *
     * @param code de nieuwe code
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Haalt een naam op.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Instellen van naam.
     *
     * @param naam de nieuwe naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int naamHashCode = 0;
        if (naam != null) {
            naamHashCode = naam.hashCode();
        }
        result = prime * result + naamHashCode;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Gemeente other = (Gemeente) obj;
        if (naam == null) {
            if (other.naam != null) {
                return false;
            }
        } else if (!naam.equals(other.naam)) {
            return false;
        }
        return true;
    }

}
