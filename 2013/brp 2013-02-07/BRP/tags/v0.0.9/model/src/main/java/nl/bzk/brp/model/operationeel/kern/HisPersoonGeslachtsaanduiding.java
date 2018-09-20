/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorie;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;


/**
 * Entity voor de historie van de groep "Geslachtsaanduiding" van Kern.Persoon.
 */
@Entity
@Table(schema = "kern", name = "His_PersGeslachtsaand")
@Access(AccessType.FIELD)
public class HisPersoonGeslachtsaanduiding extends AbstractMaterieleEnFormeleHistorie {

    @Id
    @SequenceGenerator(name = "HisPersGeslachtsaand", sequenceName = "Kern.seq_His_PersGeslachtsaand")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersGeslachtsaand")
    @Column(name = "ID")
    private Long                id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersistentPersoon   persoon;

    @Column(name = "Geslachtsaand")
    private GeslachtsAanduiding geslachtsAanduiding;

    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public void setGeslachtsAanduiding(final GeslachtsAanduiding geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public Long getId() {
        return id;
    }

}
