/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public class Gemeente {

    private String code;

    private String naam;

    public Gemeente() {
    }

    public Gemeente(final String naam) {
        this.naam = naam;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((naam == null) ? 0 : naam.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gemeente other = (Gemeente) obj;
        if (naam == null) {
            if (other.naam != null)
                return false;
        } else if (!naam.equals(other.naam))
            return false;
        return true;
    }

}
