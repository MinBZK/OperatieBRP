/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.jibx.model;

/**
 * Model class voor een verantwoording.
 */
public class Verantwoording {
    
    private Bron bron;
    private String toelichting;

    public Verantwoording() {
    }

    public Verantwoording(Bron bron) {
        this.bron = bron;
    }

    public Bron getBron() {
        return bron;
    }

    public void setBron(Bron bron) {
        this.bron = bron;
    }

    public String getToelichting() {
        return toelichting;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verantwoording)) {
            return false;
        }

        Verantwoording that = (Verantwoording) o;

        if (bron != null ? !bron.equals(that.bron) : that.bron != null) {
            return false;
        }
        if (toelichting != null ? !toelichting.equals(that.toelichting) : that.toelichting != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = bron != null ? bron.hashCode() : 0;
        result = 31 * result + (toelichting != null ? toelichting.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Verantwoording{" +
                "bron=" + bron +
                ", toelichting='" + toelichting + '\'' +
                '}';
    }
}
