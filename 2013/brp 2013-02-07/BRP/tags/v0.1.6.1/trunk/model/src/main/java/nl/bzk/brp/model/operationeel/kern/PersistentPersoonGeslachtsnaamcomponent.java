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

import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.Predikaat;
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
    private Long              id;

    @ManyToOne(targetEntity = PersistentPersoon.class)
    @JoinColumn(name = "pers")
    private PersistentPersoon persoon;

    @Column(name = "Volgnr")
    private Integer           volgnummer;

    @Column(name = "Naam")
    private String            naam;

    @Column(name = "Voorvoegsel")
    private String            voorvoegsel;

    @Column(name = "scheidingsteken")
    private String            scheidingsteken;

    @Column(name = "predikaat")
    @Enumerated
    private Predikaat         predikaat;

    @Column(name = "adellijketitel")
    @Enumerated
    private AdellijkeTitel    adellijkeTitel;

    @Column(name = "PersGeslnaamcompStatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie    persoonGeslachtsnaamcomponentStatusHis;

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

    /**
     * @return the adellijkeTitel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * @param adellijkeTitel the adellijkeTitel to set
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    /**
     * @return the scheidingsteken
     */
    public String getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * @param scheidingsteken the scheidingsteken to set
     */
    public void setScheidingsteken(final String scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * @return the predikaat
     */
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * @param predikaat the predikaat to set
     */
    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
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
}
