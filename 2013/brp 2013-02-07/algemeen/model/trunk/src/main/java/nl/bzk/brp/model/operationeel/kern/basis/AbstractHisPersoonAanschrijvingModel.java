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
import javax.persistence.Enumerated;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAanschrijvingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonAanschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonAanschrijvingModel extends AbstractFormeleHistorieEntiteit implements
        PersoonAanschrijvingGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONAANSCHRIJVING", sequenceName = "Kern.seq_His_PersAanschr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONAANSCHRIJVING")
    @JsonProperty
    private Integer                   iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel              persoon;

    @Enumerated
    @Column(name = "Naamgebruik")
    @JsonProperty
    private WijzeGebruikGeslachtsnaam naamgebruik;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndTitelsPredikatenBijAansch"))
    @JsonProperty
    private JaNee                     indicatieTitelsPredikatenBijAanschrijven;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndAanschrAlgoritmischAfgele"))
    @JsonProperty
    private JaNee                     indicatieAanschrijvingAlgoritmischAfgeleid;

    @ManyToOne
    @JoinColumn(name = "PredikaatAanschr")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Predikaat                 predikaatAanschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "VoornamenAanschr"))
    @JsonProperty
    private Voornamen                 voornamenAanschrijving;

    @ManyToOne
    @JoinColumn(name = "AdellijkeTitelAanschr")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AdellijkeTitel            adellijkeTitelAanschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "VoorvoegselAanschr"))
    @JsonProperty
    private Voorvoegsel               voorvoegselAanschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "ScheidingstekenAanschr"))
    @JsonProperty
    private Scheidingsteken           scheidingstekenAanschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "GeslnaamAanschr"))
    @JsonProperty
    private Geslachtsnaam             geslachtsnaamAanschrijving;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonAanschrijvingModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonAanschrijvingModel(final PersoonModel persoonModel,
            final PersoonAanschrijvingGroepModel groep)
    {
        this.persoon = persoonModel;
        this.naamgebruik = groep.getNaamgebruik();
        this.indicatieTitelsPredikatenBijAanschrijven = groep.getIndicatieTitelsPredikatenBijAanschrijven();
        this.indicatieAanschrijvingAlgoritmischAfgeleid = groep.getIndicatieAanschrijvingAlgoritmischAfgeleid();
        this.predikaatAanschrijving = groep.getPredikaatAanschrijving();
        this.voornamenAanschrijving = groep.getVoornamenAanschrijving();
        this.adellijkeTitelAanschrijving = groep.getAdellijkeTitelAanschrijving();
        this.voorvoegselAanschrijving = groep.getVoorvoegselAanschrijving();
        this.scheidingstekenAanschrijving = groep.getScheidingstekenAanschrijving();
        this.geslachtsnaamAanschrijving = groep.getGeslachtsnaamAanschrijving();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonAanschrijvingModel(final AbstractHisPersoonAanschrijvingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        naamgebruik = kopie.getNaamgebruik();
        indicatieTitelsPredikatenBijAanschrijven = kopie.getIndicatieTitelsPredikatenBijAanschrijven();
        indicatieAanschrijvingAlgoritmischAfgeleid = kopie.getIndicatieAanschrijvingAlgoritmischAfgeleid();
        predikaatAanschrijving = kopie.getPredikaatAanschrijving();
        voornamenAanschrijving = kopie.getVoornamenAanschrijving();
        adellijkeTitelAanschrijving = kopie.getAdellijkeTitelAanschrijving();
        voorvoegselAanschrijving = kopie.getVoorvoegselAanschrijving();
        scheidingstekenAanschrijving = kopie.getScheidingstekenAanschrijving();
        geslachtsnaamAanschrijving = kopie.getGeslachtsnaamAanschrijving();

    }

    /**
     * Retourneert ID van His Persoon Aanschrijving.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Aanschrijving.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Naamgebruik van His Persoon Aanschrijving.
     *
     * @return Naamgebruik.
     */
    public WijzeGebruikGeslachtsnaam getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * Retourneert Titels predikaten bij aanschrijven? van His Persoon Aanschrijving.
     *
     * @return Titels predikaten bij aanschrijven?.
     */
    public JaNee getIndicatieTitelsPredikatenBijAanschrijven() {
        return indicatieTitelsPredikatenBijAanschrijven;
    }

    /**
     * Retourneert Aanschrijving algoritmisch afgeleid? van His Persoon Aanschrijving.
     *
     * @return Aanschrijving algoritmisch afgeleid?.
     */
    public JaNee getIndicatieAanschrijvingAlgoritmischAfgeleid() {
        return indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    /**
     * Retourneert Predikaat aanschrijving van His Persoon Aanschrijving.
     *
     * @return Predikaat aanschrijving.
     */
    public Predikaat getPredikaatAanschrijving() {
        return predikaatAanschrijving;
    }

    /**
     * Retourneert Voornamen aanschrijving van His Persoon Aanschrijving.
     *
     * @return Voornamen aanschrijving.
     */
    public Voornamen getVoornamenAanschrijving() {
        return voornamenAanschrijving;
    }

    /**
     * Retourneert Adellijke titel aanschrijving van His Persoon Aanschrijving.
     *
     * @return Adellijke titel aanschrijving.
     */
    public AdellijkeTitel getAdellijkeTitelAanschrijving() {
        return adellijkeTitelAanschrijving;
    }

    /**
     * Retourneert Voorvoegsel aanschrijving van His Persoon Aanschrijving.
     *
     * @return Voorvoegsel aanschrijving.
     */
    public Voorvoegsel getVoorvoegselAanschrijving() {
        return voorvoegselAanschrijving;
    }

    /**
     * Retourneert Scheidingsteken aanschrijving van His Persoon Aanschrijving.
     *
     * @return Scheidingsteken aanschrijving.
     */
    public Scheidingsteken getScheidingstekenAanschrijving() {
        return scheidingstekenAanschrijving;
    }

    /**
     * Retourneert Geslachtsnaam aanschrijving van His Persoon Aanschrijving.
     *
     * @return Geslachtsnaam aanschrijving.
     */
    public Geslachtsnaam getGeslachtsnaamAanschrijving() {
        return geslachtsnaamAanschrijving;
    }

}
