/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.operationeel.StatusHistorie;


/**
 * Een actuele Component van de Geslachtsnaam van een Persoon in het operationele model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "PersGeslnaamcomp", schema = "Kern")
public class PersistentPersoonGeslachtsnaamcomponent {

    @Id
    @SequenceGenerator(name = "PersGeslnaamcomp", sequenceName = "Kern.seq_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersGeslnaamcomp")
    private Long                                  id;

    @ManyToOne(targetEntity = PersistentPersoon.class)
    @JoinColumn(name = "pers")
    private PersistentPersoon                     persoon;

    @Column(name = "Volgnr")
    private Integer                               volgnummer;

    @Column(name = "Naam")
    private String                                naam;

    @Column(name = "Voorvoegsel")
    private String                                voorvoegsel;

    @Column(name = "PersGeslnaamcompStatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie persoonGeslachtsnaamcomponentStatusHis;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Persgeslnaamcomp")
    private Set<HisPersoonGeslachtsnaamcomponent> hisPersoonGeslachtsnaamcomponenten =
                                                                                         new HashSet<HisPersoonGeslachtsnaamcomponent>();

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

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

    public StatusHistorie getPersoonGeslachtsnaamcomponentStatusHis() {
        return persoonGeslachtsnaamcomponentStatusHis;
    }


    public void setPersoonGeslachtsnaamcomponentStatusHis(final StatusHistorie persoonGeslachtsnaamcomponentStatusHis) {
        this.persoonGeslachtsnaamcomponentStatusHis = persoonGeslachtsnaamcomponentStatusHis;
    }

    public Set<HisPersoonGeslachtsnaamcomponent> getHisPersoonGeslachtsnaamcomponenten() {
        return hisPersoonGeslachtsnaamcomponenten;
    }

    public void setHisPersoonGeslachtsnaamcomponenten(
            final Set<HisPersoonGeslachtsnaamcomponent> hisPersoonGeslachtsnaamcomponenten)
    {
        this.hisPersoonGeslachtsnaamcomponenten = hisPersoonGeslachtsnaamcomponenten;
    }

    /**
     * Voeg historie persoon geslachtsnaam component toe aan de HisPersoonGeslachtsnaamcomponenten set.
     *
     * @param hisPersoonGeslachtsnaamcomponent de {@link HisPersoonGeslachtsnaamcomponent}
     */
    public void addHisPersoonGeslachtsnaamcomponent(
            final HisPersoonGeslachtsnaamcomponent hisPersoonGeslachtsnaamcomponent)
    {
        hisPersoonGeslachtsnaamcomponenten.add(hisPersoonGeslachtsnaamcomponent);
    }
}
