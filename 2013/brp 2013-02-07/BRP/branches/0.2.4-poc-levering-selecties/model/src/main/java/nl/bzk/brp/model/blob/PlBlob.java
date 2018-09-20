/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.blob;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "Kern", name = "plblob")
public class PlBlob implements Serializable {

    /*
     * @see <a href="http://docs.jboss.org/hibernate/annotations/3.5/reference/en/html_single/#entity-mapping-identifier">Hibernate documentation - identity copy</a>
     */
    @Id
    @OneToOne()
    @JoinColumn(name = "id")
    private PersoonModel persoon;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(nullable = false)
    private byte[] pl;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(nullable = true, name = "betr")
    private byte[] betrokkenheden;

    public Integer getId() {
        return persoon.getId();
    }

    public PersoonModel getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersoonModel persoon) {
        this.persoon = persoon;
    }

    public byte[] getPl() {
        return pl;
    }

    public void setPl(final byte[] pl) {
        this.pl = pl;
    }

    public byte[] getBetrokkenheden() {
        return betrokkenheden;
    }

    public void setBetrokkenheden(final byte[] betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }
}
