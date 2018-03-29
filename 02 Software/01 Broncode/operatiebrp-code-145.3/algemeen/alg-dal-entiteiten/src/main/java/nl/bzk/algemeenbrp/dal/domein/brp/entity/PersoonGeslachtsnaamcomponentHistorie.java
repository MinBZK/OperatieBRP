/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persgeslnaamcomp database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persgeslnaamcomp", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persgeslnaamcomp", "tsreg", "dataanvgel"}))
public class PersoonGeslachtsnaamcomponentHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persgeslnaamcomp_id_generator", sequenceName = "kern.seq_his_persgeslnaamcomp", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persgeslnaamcomp_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "stam", nullable = false, length = 200)
    private String stam;

    @Column(length = 1)
    private Character scheidingsteken;

    @Column(length = 10)
    private String voorvoegsel;

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    // bi-directional many-to-one association to PersoonGeslachtsnaamcomponent
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persgeslnaamcomp", nullable = false)
    private PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent;

    @Column(name = "predicaat")
    private Integer predicaatId;

    /**
     * JPA default constructor.
     */
    protected PersoonGeslachtsnaamcomponentHistorie() {}

    /**
     * Maak een nieuwe persoon geslachtsnaamcomponent historie.
     *
     * @param persoonGeslachtsnaamcomponent persoon geslachtsnaamcomponent
     * @param stam stam
     */
    public PersoonGeslachtsnaamcomponentHistorie(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent, final String stam) {
        setPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
        setStam(stam);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonGeslachtsnaamcomponentHistorie(final PersoonGeslachtsnaamcomponentHistorie ander) {
        super(ander);
        stam = ander.getStam();
        scheidingsteken = ander.getScheidingsteken();
        voorvoegsel = ander.getVoorvoegsel();
        adellijkeTitelId = ander.getAdellijkeTitel() != null ? ander.getAdellijkeTitel().getId() : null;
        persoonGeslachtsnaamcomponent = ander.getPersoonGeslachtsnaamcomponent();
        predicaatId = ander.getPredicaat() != null ? ander.getPredicaat().getId() : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonGeslachtsnaamcomponentHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van stam van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @return de waarde van stam van PersoonGeslachtsnaamcomponentHistorie
     */
    public String getStam() {
        return stam;
    }

    /**
     * Zet de waarden voor stam van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param stam de nieuwe waarde voor stam van PersoonGeslachtsnaamcomponentHistorie
     */
    public void setStam(final String stam) {
        ValidationUtils.controleerOpNullWaarden("stam mag niet null zijn", stam);
        ValidationUtils.controleerOpLegeWaarden("stam mag geen lege string zijn", stam);
        this.stam = stam;
    }

    /**
     * Geef de waarde van scheidingsteken van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @return de waarde van scheidingsteken van PersoonGeslachtsnaamcomponentHistorie
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van
     *        PersoonGeslachtsnaamcomponentHistorie
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van voorvoegsel van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @return de waarde van voorvoegsel van PersoonGeslachtsnaamcomponentHistorie
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van
     *        PersoonGeslachtsnaamcomponentHistorie
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van adellijke titel van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @return de waarde van adellijke titel van PersoonGeslachtsnaamcomponentHistorie
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarden voor adellijke titel van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param adellijkeTitel de nieuwe waarde voor adellijke titel van
     *        PersoonGeslachtsnaamcomponentHistorie
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van persoon geslachtsnaamcomponent van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @return de waarde van persoon geslachtsnaamcomponent van
     *         PersoonGeslachtsnaamcomponentHistorie
     */
    public PersoonGeslachtsnaamcomponent getPersoonGeslachtsnaamcomponent() {
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * Zet de waarden voor persoon geslachtsnaamcomponent van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param persoonGeslachtsnaamcomponent de nieuwe waarde voor persoon geslachtsnaamcomponent van
     *        PersoonGeslachtsnaamcomponentHistorie
     */
    public void setPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        ValidationUtils.controleerOpNullWaarden("persoonGeslachtsnaamcomponent mag niet null zijn", persoonGeslachtsnaamcomponent);
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponent;
    }

    /**
     * Geef de waarde van predicaat van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @return de waarde van predicaat van PersoonGeslachtsnaamcomponentHistorie
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarden voor predicaat van PersoonGeslachtsnaamcomponentHistorie.
     *
     * @param predikaat de nieuwe waarde voor predicaat van PersoonGeslachtsnaamcomponentHistorie
     */
    public void setPredicaat(final Predicaat predikaat) {
        if (predikaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predikaat.getId();
        }
    }

    @Override
    public final PersoonGeslachtsnaamcomponentHistorie kopieer() {
        return new PersoonGeslachtsnaamcomponentHistorie(this);
    }

    @Override
    public Persoon getPersoon() {
        return getPersoonGeslachtsnaamcomponent().getPersoon();
    }

    @Override
    public boolean isDegGelijkAanDagToegestaan() {
        return true;
    }

    @Override
    public boolean moetHistorieAaneengeslotenZijn() {
        return true;
    }
}
