/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.jibx.model;

import java.util.Set;

/**
 * Model class voor de samengestelde naam.
 */
public class SamengesteldeNaam {
    
    private String voorNaam;
    private String achterNaam;
    private Set<Verantwoording> verantwoordingen;

    public SamengesteldeNaam() {
    }

    public SamengesteldeNaam(final String voorNaam, final String achterNaam) {
        this.voorNaam = voorNaam;
        this.achterNaam = achterNaam;
    }

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public Set<Verantwoording> getVerantwoordingen() {
        return verantwoordingen;
    }

    public void setVerantwoordingen(Set<Verantwoording> verantwoordingen) {
        this.verantwoordingen = verantwoordingen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SamengesteldeNaam)) {
            return false;
        }

        SamengesteldeNaam that = (SamengesteldeNaam) o;

        if (achterNaam != null ? !achterNaam.equals(that.achterNaam) : that.achterNaam != null) {
            return false;
        }
        /*
        if (verantwoordingen != null ? !verantwoordingen.equals(that.verantwoordingen) :
                that.verantwoordingen != null)
        {
            return false;
        }
        */
        if (voorNaam != null ? !voorNaam.equals(that.voorNaam) : that.voorNaam != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = voorNaam != null ? voorNaam.hashCode() : 0;
        result = 31 * result + (achterNaam != null ? achterNaam.hashCode() : 0);
//        result = 31 * result + (verantwoordingen != null ? verantwoordingen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SamengesteldeNaam{" +
                "voorNaam='" + voorNaam + '\'' +
                ", achterNaam='" + achterNaam + '\'' +
                ", verantwoordingen=" + verantwoordingen +
                '}';
    }
}
