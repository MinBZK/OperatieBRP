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

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;


/**
 * Een (historische) voornaam van een persoon.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "His_PersVoornaam", schema = "Kern")
public class HisPersoonVoornaam extends AbstractMaterieleEnFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HisPersVoornaam", sequenceName = "Kern.seq_His_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersVoornaam")
    private Long                      id;

    @ManyToOne
    @JoinColumn(name = "PersVoornaam")
    @NotNull
    private PersistentPersoonVoornaam persoonVoornaam;

    @Column(name = "Naam")
    private String                    naam;

    /**
     * @return the persoonVoornaam
     */
    public PersistentPersoonVoornaam getPersoonVoornaam() {
        return persoonVoornaam;
    }

    /**
     * @param persoonVoornaam the persoonVoornaam to set
     */
    public void setPersoonVoornaam(final PersistentPersoonVoornaam persoonVoornaam) {
        this.persoonVoornaam = persoonVoornaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }
}
