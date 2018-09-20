/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.jibx.model;

/**
 * Model class voor een Bron.
 */
public class Bron {

    private BronType type;
    private String code;
    private String nummer;

    public Bron() {
    }

    public Bron(BronType type, String code) {
        this.type = type;
        this.code = code;
    }

    public BronType getType() {
        return type;
    }

    public void setType(BronType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bron)) {
            return false;
        }

        Bron bron = (Bron) o;

        if (code != null ? !code.equals(bron.code) : bron.code != null) {
            return false;
        }
        if (nummer != null ? !nummer.equals(bron.nummer) : bron.nummer != null) {
            return false;
        }
        if (type != bron.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (nummer != null ? nummer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bron{" +
                "type=" + type +
                ", code='" + code + '\'' +
                ", nummer='" + nummer + '\'' +
                '}';
    }
}
