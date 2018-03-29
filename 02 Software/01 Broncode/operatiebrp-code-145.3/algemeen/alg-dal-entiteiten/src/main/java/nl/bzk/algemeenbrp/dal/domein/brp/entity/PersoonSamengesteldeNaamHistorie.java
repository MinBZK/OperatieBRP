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
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the his_perssamengesteldenaam database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_perssamengesteldenaam", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel"}))
public class PersoonSamengesteldeNaamHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_perssamengesteldenaam_id_generator", sequenceName = "kern.seq_his_perssamengesteldenaam", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_perssamengesteldenaam_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "geslnaamstam", nullable = false, length = 200)
    private String geslachtsnaamstam;

    @Column(name = "indafgeleid", nullable = false)
    private boolean indicatieAfgeleid;

    @Column(name = "indnreeks", nullable = false)
    private boolean indicatieNamenreeks;

    @Column(length = 1)
    private Character scheidingsteken;

    @Column(length = 200)
    private String voornamen;

    @Column(length = 10)
    private String voorvoegsel;

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predicaat")
    private Integer predicaatId;

    /**
     * JPA default constructor.
     */
    protected PersoonSamengesteldeNaamHistorie() {}

    /**
     * Maak een nieuwe persoon samengestelde naam historie.
     *
     * @param persoon persoon
     * @param geslachtsnaamstam geslachtsnaamstam
     * @param indicatieAlgoritmischAfgeleid indicatie algoritmisch afgeleid
     * @param indicatieNamenreeks indicatie namenreeks
     */
    public PersoonSamengesteldeNaamHistorie(final Persoon persoon, final String geslachtsnaamstam, final boolean indicatieAlgoritmischAfgeleid,
            final boolean indicatieNamenreeks) {
        setPersoon(persoon);
        setGeslachtsnaamstam(geslachtsnaamstam);
        setIndicatieAfgeleid(indicatieAlgoritmischAfgeleid);
        setIndicatieNamenreeks(indicatieNamenreeks);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonSamengesteldeNaamHistorie(final PersoonSamengesteldeNaamHistorie ander) {
        super(ander);
        geslachtsnaamstam = ander.getGeslachtsnaamstam();
        indicatieAfgeleid = ander.getIndicatieAfgeleid();
        indicatieNamenreeks = ander.getIndicatieNamenreeks();
        scheidingsteken = ander.getScheidingsteken();
        voornamen = ander.getVoornamen();
        voorvoegsel = ander.getVoorvoegsel();
        adellijkeTitelId = ander.getAdellijkeTitel() != null ? ander.getAdellijkeTitel().getId() : null;
        persoon = ander.getPersoon();
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
     * Zet de waarden voor id van PersoonSamengesteldeNaamHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonSamengesteldeNaamHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van geslachtsnaamstam van PersoonSamengesteldeNaamHistorie
     */
    public String getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet de waarden voor geslachtsnaamstam van PersoonSamengesteldeNaamHistorie.
     *
     * @param geslachtsnaamstam de nieuwe waarde voor geslachtsnaamstam van
     *        PersoonSamengesteldeNaamHistorie
     */
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        ValidationUtils.controleerOpNullWaarden("geslachtsnaamstam mag niet null zijn", geslachtsnaamstam);
        ValidationUtils.controleerOpLegeWaarden("geslachtsnaamstam mag geen lege string zijn", geslachtsnaamstam);
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van indicatie afgeleid van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van indicatie afgeleid van PersoonSamengesteldeNaamHistorie
     */
    public boolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Zet de waarden voor indicatie afgeleid van PersoonSamengesteldeNaamHistorie.
     *
     * @param indicatieAfgeleid de nieuwe waarde voor indicatie afgeleid van
     *        PersoonSamengesteldeNaamHistorie
     */
    public void setIndicatieAfgeleid(final boolean indicatieAfgeleid) {
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    /**
     * Geef de waarde van indicatie namenreeks van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van indicatie namenreeks van PersoonSamengesteldeNaamHistorie
     */
    public boolean getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Zet de waarden voor indicatie namenreeks van PersoonSamengesteldeNaamHistorie.
     *
     * @param indicatieNamenreeks de nieuwe waarde voor indicatie namenreeks van
     *        PersoonSamengesteldeNaamHistorie
     */
    public void setIndicatieNamenreeks(final boolean indicatieNamenreeks) {
        this.indicatieNamenreeks = indicatieNamenreeks;
    }

    /**
     * Geef de waarde van scheidingsteken van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van scheidingsteken van PersoonSamengesteldeNaamHistorie
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van PersoonSamengesteldeNaamHistorie.
     *
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van
     *        PersoonSamengesteldeNaamHistorie
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van voornamen van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van voornamen van PersoonSamengesteldeNaamHistorie
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarden voor voornamen van PersoonSamengesteldeNaamHistorie.
     *
     * @param voornamen de nieuwe waarde voor voornamen van PersoonSamengesteldeNaamHistorie
     */
    public void setVoornamen(final String voornamen) {
        ValidationUtils.controleerOpLegeWaarden("voornamen mag geen lege string zijn", voornamen);
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van voorvoegsel van PersoonSamengesteldeNaamHistorie
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van PersoonSamengesteldeNaamHistorie.
     *
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van PersoonSamengesteldeNaamHistorie
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van adellijke titel van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van adellijke titel van PersoonSamengesteldeNaamHistorie
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarden voor adellijke titel van PersoonSamengesteldeNaamHistorie.
     *
     * @param adellijkeTitel de nieuwe waarde voor adellijke titel van
     *        PersoonSamengesteldeNaamHistorie
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel != null) {
            adellijkeTitelId = adellijkeTitel.getId();
        } else {
            adellijkeTitelId = null;
        }
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonSamengesteldeNaamHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonSamengesteldeNaamHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van predicaat van PersoonSamengesteldeNaamHistorie.
     *
     * @return de waarde van predicaat van PersoonSamengesteldeNaamHistorie
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarden voor predicaat van PersoonSamengesteldeNaamHistorie.
     *
     * @param predicaat de nieuwe waarde voor predicaat van PersoonSamengesteldeNaamHistorie
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat != null) {
            predicaatId = predicaat.getId();
        } else {
            predicaatId = null;
        }
    }

    @Override
    public final PersoonSamengesteldeNaamHistorie kopieer() {
        return new PersoonSamengesteldeNaamHistorie(this);
    }

    /**
     * Vergelijkt twee {@link PersoonSamengesteldeNaamHistorie} objecten o.b.v. hun
     * inhoudelijkegegevens. Dus id en verantwoording worden niet meegenomen in de vergelijking.
     *
     * @param anderVoorkomen het voorkomen waarmee vergeleken wordt
     * @return true als dit voorkomen inhoudelijk gelijk is aan het andere voorkomen, anders false
     */
    public final boolean isInhoudelijkGelijkAan(final PersoonSamengesteldeNaamHistorie anderVoorkomen) {
        return anderVoorkomen != null && (anderVoorkomen == this
                || new EqualsBuilder().append(geslachtsnaamstam, anderVoorkomen.geslachtsnaamstam).append(indicatieAfgeleid, anderVoorkomen.indicatieAfgeleid)
                        .append(indicatieNamenreeks, anderVoorkomen.indicatieNamenreeks).append(scheidingsteken, anderVoorkomen.scheidingsteken)
                        .append(voornamen, anderVoorkomen.voornamen).append(voorvoegsel, anderVoorkomen.voorvoegsel)
                        .append(adellijkeTitelId, anderVoorkomen.adellijkeTitelId).append(predicaatId, anderVoorkomen.predicaatId).isEquals());
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
