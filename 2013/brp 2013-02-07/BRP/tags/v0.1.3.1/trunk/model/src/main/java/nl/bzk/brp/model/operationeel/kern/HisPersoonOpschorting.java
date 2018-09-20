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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.gedeeld.RedenOpschorting;


/** Operationel Model Class voor de C/D laag van een persoons indicatie. */
@Entity
@Access(AccessType.FIELD)
@Table(name = "His_PersOpschorting", schema = "Kern")
public class HisPersoonOpschorting extends AbstractMaterieleEnFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HisPersOpschrorting", sequenceName = "Kern.seq_His_PersOpschorting")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersOpschrorting")
    private Integer           id;

    @ManyToOne(targetEntity = PersistentPersoon.class)
    @JoinColumn(name = "Pers")
    @NotNull
    private PersistentPersoon persoon;

    @Column(name = "rdnopschortingbijhouding")
    @Enumerated(value = EnumType.ORDINAL)
    private RedenOpschorting  redenopschorting;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public RedenOpschorting getRedenopschorting() {
        return redenopschorting;
    }

    public void setRedenopschorting(final RedenOpschorting redenopschorting) {
        this.redenopschorting = redenopschorting;
    }
}
