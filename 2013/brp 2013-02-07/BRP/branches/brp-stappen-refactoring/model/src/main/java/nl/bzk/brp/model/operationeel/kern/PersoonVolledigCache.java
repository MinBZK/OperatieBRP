/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Model voor het opslaan van PersoonVolledig in geserializeerde vorm.
 *
 * @see <a href="http://docs.jboss.org/hibernate/annotations/3.5/reference/en/html_single/#entity-mapping-identifier">Hibernate documentation - identity copy</a>
 */
@Entity
@Table(schema = "Kern", name = "PersVol")
public class PersoonVolledigCache implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "ID")
    private PersoonModel persoon;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "Gegevens", nullable = false)
    private byte[] data;

    /**
     * Geef de id van dit object.
     *
     * @return id - gelijk aan persoon id, anders -1.
     */
    public Integer getId() {
        if (null != persoon) {
            return persoon.getID();
        }

        return -1;
    }

    public PersoonModel getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersoonModel persoon) {
        this.persoon = persoon;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(final byte[] pl) {
        this.data = pl;
    }
}
