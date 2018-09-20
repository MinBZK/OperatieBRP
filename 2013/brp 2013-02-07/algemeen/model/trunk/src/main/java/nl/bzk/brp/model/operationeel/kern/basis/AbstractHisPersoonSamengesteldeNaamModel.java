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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonSamengesteldeNaamGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonSamengesteldeNaamModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonSamengesteldeNaamGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONSAMENGESTELDENAAM", sequenceName = "Kern.seq_His_PersSamengesteldeNaam")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONSAMENGESTELDENAAM")
    @JsonProperty
    private Integer         iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel    persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndAlgoritmischAfgeleid"))
    @JsonProperty
    private JaNee           indicatieAlgoritmischAfgeleid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndNreeks"))
    @JsonProperty
    private JaNee           indicatieNamenreeks;

    @ManyToOne
    @JoinColumn(name = "Predikaat")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Predikaat       predikaat;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voornamen"))
    @JsonProperty
    private Voornamen       voornamen;

    @ManyToOne
    @JoinColumn(name = "AdellijkeTitel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AdellijkeTitel  adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private Voorvoegsel     voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Geslnaam"))
    @JsonProperty
    private Geslachtsnaam   geslachtsnaam;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonSamengesteldeNaamModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonSamengesteldeNaamModel(final PersoonModel persoonModel,
            final PersoonSamengesteldeNaamGroepModel groep)
    {
        this.persoon = persoonModel;
        this.indicatieAlgoritmischAfgeleid = groep.getIndicatieAlgoritmischAfgeleid();
        this.indicatieNamenreeks = groep.getIndicatieNamenreeks();
        this.predikaat = groep.getPredikaat();
        this.voornamen = groep.getVoornamen();
        this.adellijkeTitel = groep.getAdellijkeTitel();
        this.voorvoegsel = groep.getVoorvoegsel();
        this.scheidingsteken = groep.getScheidingsteken();
        this.geslachtsnaam = groep.getGeslachtsnaam();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonSamengesteldeNaamModel(final AbstractHisPersoonSamengesteldeNaamModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        indicatieAlgoritmischAfgeleid = kopie.getIndicatieAlgoritmischAfgeleid();
        indicatieNamenreeks = kopie.getIndicatieNamenreeks();
        predikaat = kopie.getPredikaat();
        voornamen = kopie.getVoornamen();
        adellijkeTitel = kopie.getAdellijkeTitel();
        voorvoegsel = kopie.getVoorvoegsel();
        scheidingsteken = kopie.getScheidingsteken();
        geslachtsnaam = kopie.getGeslachtsnaam();

    }

    /**
     * Retourneert ID van His Persoon Samengestelde naam.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Samengestelde naam.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Algoritmisch afgeleid? van His Persoon Samengestelde naam.
     *
     * @return Algoritmisch afgeleid?.
     */
    public JaNee getIndicatieAlgoritmischAfgeleid() {
        return indicatieAlgoritmischAfgeleid;
    }

    /**
     * Retourneert Namenreeks? van His Persoon Samengestelde naam.
     *
     * @return Namenreeks?.
     */
    public JaNee getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Retourneert Predikaat van His Persoon Samengestelde naam.
     *
     * @return Predikaat.
     */
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * Retourneert Voornamen van His Persoon Samengestelde naam.
     *
     * @return Voornamen.
     */
    public Voornamen getVoornamen() {
        return voornamen;
    }

    /**
     * Retourneert Adellijke titel van His Persoon Samengestelde naam.
     *
     * @return Adellijke titel.
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * Retourneert Voorvoegsel van His Persoon Samengestelde naam.
     *
     * @return Voorvoegsel.
     */
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Retourneert Scheidingsteken van His Persoon Samengestelde naam.
     *
     * @return Scheidingsteken.
     */
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Retourneert Geslachtsnaam van His Persoon Samengestelde naam.
     *
     * @return Geslachtsnaam.
     */
    public Geslachtsnaam getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonSamengesteldeNaamModel kopieer() {
        return new HisPersoonSamengesteldeNaamModel(this);
    }
}
