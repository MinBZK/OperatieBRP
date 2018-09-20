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


/**
 * Een actuele Component van de Geslachtsnaam van een Persoon in het operationele model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "His_PersGeslnaamcomp", schema = "Kern")
public class HisPersoonGeslachtsnaamcomponent extends AbstractMaterieleEnFormeleHistorie {

    @Id
    @SequenceGenerator(name = "HisPersGeslnaamcomp", sequenceName = "Kern.seq_His_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersGeslnaamcomp")
    private Long                                    id;

    @ManyToOne(targetEntity = PersistentPersoonGeslachtsnaamcomponent.class)
    @JoinColumn(name = "PersGeslnaamcomp")
    private PersistentPersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent;

    @Column(name = "Naam")
    private String                                  naam;

    @Column(name = "Voorvoegsel")
    private String                                  voorvoegsel;

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public Long getId() {
        return id;
    }

    public PersistentPersoonGeslachtsnaamcomponent getPersoonGeslachtsnaamcomponent() {
        return persoonGeslachtsnaamcomponent;
    }

    public void setPersoonGeslachtsnaamcomponent(
        final PersistentPersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent)
    {
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponent;
    }
}
