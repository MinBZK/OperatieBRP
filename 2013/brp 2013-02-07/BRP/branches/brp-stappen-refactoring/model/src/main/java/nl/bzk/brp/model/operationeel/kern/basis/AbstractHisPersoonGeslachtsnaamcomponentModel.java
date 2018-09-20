/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonGeslachtsnaamcomponentModel extends AbstractMaterieleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONGESLACHTSNAAMCOMPONENT", sequenceName = "Kern.seq_His_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONGESLACHTSNAAMCOMPONENT")
    @JsonProperty
    private Integer                            iD;

    @ManyToOne
    @JoinColumn(name = "PersGeslnaamcomp")
    private PersoonGeslachtsnaamcomponentModel persoonGeslachtsnaamcomponent;

    @ManyToOne
    @JoinColumn(name = "Predikaat")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Predikaat                          predikaat;

    @ManyToOne
    @JoinColumn(name = "AdellijkeTitel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AdellijkeTitel                     adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private Voorvoegsel                        voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private Scheidingsteken                    scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    @JsonProperty
    private Geslachtsnaamcomponent             naam;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonGeslachtsnaamcomponentModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonGeslachtsnaamcomponentModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonGeslachtsnaamcomponentModel(
            final PersoonGeslachtsnaamcomponentModel persoonGeslachtsnaamcomponentModel,
            final PersoonGeslachtsnaamcomponentStandaardGroepModel groep)
    {
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponentModel;
        this.predikaat = groep.getPredikaat();
        this.adellijkeTitel = groep.getAdellijkeTitel();
        this.voorvoegsel = groep.getVoorvoegsel();
        this.scheidingsteken = groep.getScheidingsteken();
        this.naam = groep.getNaam();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonGeslachtsnaamcomponentModel(final AbstractHisPersoonGeslachtsnaamcomponentModel kopie) {
        super(kopie);
        persoonGeslachtsnaamcomponent = kopie.getPersoonGeslachtsnaamcomponent();
        predikaat = kopie.getPredikaat();
        adellijkeTitel = kopie.getAdellijkeTitel();
        voorvoegsel = kopie.getVoorvoegsel();
        scheidingsteken = kopie.getScheidingsteken();
        naam = kopie.getNaam();

    }

    /**
     * Retourneert ID van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Geslachtsnaamcomponent van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon \ Geslachtsnaamcomponent.
     */
    public PersoonGeslachtsnaamcomponentModel getPersoonGeslachtsnaamcomponent() {
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * Retourneert Predikaat van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Predikaat.
     */
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * Retourneert Adellijke titel van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Adellijke titel.
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * Retourneert Voorvoegsel van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Voorvoegsel.
     */
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Retourneert Scheidingsteken van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Scheidingsteken.
     */
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Retourneert Naam van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Naam.
     */
    public Geslachtsnaamcomponent getNaam() {
        return naam;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public HisPersoonGeslachtsnaamcomponentModel kopieer() {
        return new HisPersoonGeslachtsnaamcomponentModel(this);
    }
}
