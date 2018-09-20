/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Gemprov manually added
 */
@Entity
@Table(name = "gemprov", schema = "kern")
public class Gemprov implements java.io.Serializable {

    private int    id;
    private Gem    gem;
    private String provnaam;

    public Gemprov() {
    }

    public Gemprov(int id, Gem gem, String naam) {
        this.id = id;
        this.gem = gem;
        this.provnaam = naam;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gemid")
    public Gem getGem() {
        return this.gem;
    }

    public void setGem(Gem gem) {
        this.gem = gem;
    }

    @Column(name = "provnaam", nullable = false, length = 100)
    public String getProvNaam() {
        return this.provnaam;
    }

    public void setProvNaam(String naam) {
        this.provnaam = naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 33;
        int result = 1;
        result = prime * result + ((gem == null) ? 0 : gem.hashCode());
        result = prime * result + ((provnaam == null) ? 0 : provnaam.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Gemprov)) {
            return false;
        }
        Gemprov other = (Gemprov) obj;
        if (gem == null) {
            if (other.gem != null) {
                return false;
            }
        } else if (!gem.equals(other.gem)) {
            return false;
        }
        if (provnaam == null) {
            if (other.provnaam != null) {
                return false;
            }
        } else if (!provnaam.equals(other.provnaam)) {
            return false;
        }
        return true;
    }

}
