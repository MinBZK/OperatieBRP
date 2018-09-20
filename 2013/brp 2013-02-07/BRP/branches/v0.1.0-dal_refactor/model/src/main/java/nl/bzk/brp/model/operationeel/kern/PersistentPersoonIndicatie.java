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
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.gedeeld.SoortIndicatie;


/** Operationel Model Class voor de A laag van een persoons indicatie. */
@Entity
@Access(AccessType.FIELD)
@Table(name = "PersIndicatie", schema = "Kern")
public class PersistentPersoonIndicatie {

    @Id
    @SequenceGenerator(name = "PERSOONINDICATIE", sequenceName = "Kern.seq_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOONINDICATIE")
    private Integer           id;

    @ManyToOne(targetEntity = PersistentPersoon.class)
    @JoinColumn(name = "Pers")
    @NotNull
    private PersistentPersoon persoon;

    @Column(name = "Srt")
    @NotNull
    private SoortIndicatie    soort;

    private Boolean           waarde;

    public Integer getId() {
        return this.id;
    }

    public PersistentPersoon getPersoon() {
        return this.persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public SoortIndicatie getSoort() {
        return this.soort;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }

    public Boolean getWaarde() {
        return this.waarde;
    }

    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }
}
