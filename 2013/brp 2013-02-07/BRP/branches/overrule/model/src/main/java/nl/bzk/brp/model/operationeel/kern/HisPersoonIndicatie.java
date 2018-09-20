/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;


/** Operationel Model Class voor de C/D laag van een persoons indicatie. */
@Entity
@Access(AccessType.FIELD)
@Table(name = "His_PersIndicatie", schema = "Kern")
public class HisPersoonIndicatie extends AbstractMaterieleEnFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HisPersIndicatie", sequenceName = "Kern.seq_His_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersIndicatie")
    private Integer id;

    @ManyToOne(targetEntity = PersistentPersoonIndicatie.class)
    @JoinColumn(name = "PersIndicatie")
    @NotNull
    private PersistentPersoonIndicatie persoonIndicatie;

    private Boolean waarde;

    public Integer getId() {
        return this.id;
    }

    public Boolean getWaarde() {
        return this.waarde;
    }

    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }

    public PersistentPersoonIndicatie getPersoonIndicatie() {
        return persoonIndicatie;
    }

    public void setPersoonIndicatie(final PersistentPersoonIndicatie persoonIndicatie) {
        this.persoonIndicatie = persoonIndicatie;
    }
}
