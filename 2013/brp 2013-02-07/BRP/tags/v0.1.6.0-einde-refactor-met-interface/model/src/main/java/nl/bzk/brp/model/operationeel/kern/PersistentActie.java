/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Date;

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

import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.SoortActie;

/**
 * Entity klasse voor Kern.Actie.
 */
@Entity
@Table(schema = "kern", name = "actie")
@Access(AccessType.FIELD)
public class PersistentActie {

    @Id
    @SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIE")
    private Long id;
    @Column(name = "srt")
    private SoortActie soort;
    @ManyToOne
    @JoinColumn(name = "partij")
    private Partij partij;
    @Column(name = "tsontlening")
    private Date tijdstipOntlening;
    @Column(name = "tsreg")
    private Date tijdstipRegistratie;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SoortActie getSoort() {
        return soort;
    }

    public void setSoort(final SoortActie soort) {
        this.soort = soort;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public Date getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    public void setTijdstipOntlening(final Date tijdstipOntlening) {
        this.tijdstipOntlening = tijdstipOntlening;
    }

    public Date getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public void setTijdstipRegistratie(final Date tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }
}
