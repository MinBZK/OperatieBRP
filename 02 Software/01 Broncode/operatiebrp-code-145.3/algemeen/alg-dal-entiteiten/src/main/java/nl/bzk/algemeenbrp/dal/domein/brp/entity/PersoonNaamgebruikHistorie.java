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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the his_persaanschr database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persnaamgebruik", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonNaamgebruikHistorie extends AbstractFormeleHistorie implements Serializable {
    /**
     * Veldnaam van nadere geslachtsnaamstamNaamgebruik tbv verschil verwerking.
     */
    public static final String GESLACHTSNAAMSTAM = "geslachtsnaamstamNaamgebruik";

    /**
     * Veldnaam van nadere scheidingstekenNaamgebruik tbv verschil verwerking.
     */
    public static final String SCHEIDINGSTEKEN = "scheidingstekenNaamgebruik";

    /**
     * Veldnaam van nadere voorvoegselNaamgebruik tbv verschil verwerking.
     */
    public static final String VOORVOEGSEL = "voorvoegselNaamgebruik";

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "his_persaanschr_id_generator", sequenceName = "kern.seq_his_persnaamgebruik", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persaanschr_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "geslnaamstamnaamgebruik", nullable = false, length = 200)
    private String geslachtsnaamstamNaamgebruik;

    @Column(name = "indnaamgebruikafgeleid", nullable = false)
    private boolean indicatieNaamgebruikAfgeleid;

    @Column(name = "scheidingstekennaamgebruik", length = 1)
    private Character scheidingstekenNaamgebruik;

    @Column(name = "voornamennaamgebruik", length = 200)
    private String voornamenNaamgebruik;

    @Column(name = "voorvoegselnaamgebruik", length = 10)
    private String voorvoegselNaamgebruik;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predicaatnaamgebruik")
    private Integer predicaatNaamgebruikId;

    @Column(name = "adellijketitelnaamgebruik")
    private Integer adellijkeTitelNaamgebruikId;

    @Column(name = "naamgebruik", nullable = false)
    private int naamgebruikId;

    /**
     * JPA default constructor.
     */
    protected PersoonNaamgebruikHistorie() {}

    /**
     * Maak een nieuwe persoon naamgebruik historie.
     *
     * @param persoon persoon
     * @param geslachtsnaamstamNaamgebruik geslachtsnaamstam naamgebruik
     * @param indicatieNaamgebruikAfgeleid indicatie naamgebruik afgeleid
     * @param naamgebruik naamgebruik
     */
    public PersoonNaamgebruikHistorie(final Persoon persoon, final String geslachtsnaamstamNaamgebruik, final boolean indicatieNaamgebruikAfgeleid,
            final Naamgebruik naamgebruik) {
        setPersoon(persoon);
        setGeslachtsnaamstamNaamgebruik(geslachtsnaamstamNaamgebruik);
        setIndicatieNaamgebruikAfgeleid(indicatieNaamgebruikAfgeleid);
        setNaamgebruik(naamgebruik);
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
     * Zet de waarden voor id van PersoonNaamgebruikHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonNaamgebruikHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van geslachtsnaamstam naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van geslachtsnaamstam naamgebruik van PersoonNaamgebruikHistorie
     */
    public String getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Zet de waarden voor geslachtsnaamstam naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @param geslachtsnaamstamNaamgebruik de nieuwe waarde voor geslachtsnaamstam naamgebruik van
     *        PersoonNaamgebruikHistorie
     */
    public void setGeslachtsnaamstamNaamgebruik(final String geslachtsnaamstamNaamgebruik) {
        ValidationUtils.controleerOpNullWaarden("geslachtsnaamstamNaamgebruik mag niet null zijn", geslachtsnaamstamNaamgebruik);
        ValidationUtils.controleerOpLegeWaarden("geslachtsnaamstamNaamgebruik mag geen lege string zijn", geslachtsnaamstamNaamgebruik);
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geef de waarde van indicatie naamgebruik afgeleid van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van indicatie naamgebruik afgeleid van PersoonNaamgebruikHistorie
     */
    public boolean getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * Zet de waarden voor indicatie naamgebruik afgeleid van PersoonNaamgebruikHistorie.
     *
     * @param indicatieNaamgebruikAfgeleid de nieuwe waarde voor indicatie naamgebruik afgeleid van
     *        PersoonNaamgebruikHistorie
     */
    public void setIndicatieNaamgebruikAfgeleid(final boolean indicatieNaamgebruikAfgeleid) {
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
    }

    /**
     * Geef de waarde van scheidingsteken naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van scheidingsteken naamgebruik van PersoonNaamgebruikHistorie
     */
    public Character getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * Zet de waarden voor scheidingsteken naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @param scheidingstekenNaamgebruik de nieuwe waarde voor scheidingsteken naamgebruik van
     *        PersoonNaamgebruikHistorie
     */
    public void setScheidingstekenNaamgebruik(final Character scheidingstekenNaamgebruik) {
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
    }

    /**
     * Geef de waarde van voornamen naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van voornamen naamgebruik van PersoonNaamgebruikHistorie
     */
    public String getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * Zet de waarden voor voornamen naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @param voornamenNaamgebruik de nieuwe waarde voor voornamen naamgebruik van
     *        PersoonNaamgebruikHistorie
     */
    public void setVoornamenNaamgebruik(final String voornamenNaamgebruik) {
        ValidationUtils.controleerOpLegeWaarden("voornamenNaamgebruik mag geen lege string zijn", voornamenNaamgebruik);
        this.voornamenNaamgebruik = voornamenNaamgebruik;
    }

    /**
     * Geef de waarde van voorvoegsel naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van voorvoegsel naamgebruik van PersoonNaamgebruikHistorie
     */
    public String getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * Zet de waarden voor voorvoegsel naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @param voorvoegselNaamgebruik de nieuwe waarde voor voorvoegsel naamgebruik van
     *        PersoonNaamgebruikHistorie
     */
    public void setVoorvoegselNaamgebruik(final String voorvoegselNaamgebruik) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegselNaamgebruik mag geen lege string zijn", voorvoegselNaamgebruik);
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
    }

    /**
     * Geef de waarde van persoon van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van persoon van PersoonNaamgebruikHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonNaamgebruikHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonNaamgebruikHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van predicaat van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van predicaat van PersoonNaamgebruikHistorie
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatNaamgebruikId);
    }

    /**
     * Zet de waarden voor predicaat van PersoonNaamgebruikHistorie.
     *
     * @param predicaat de nieuwe waarde voor predicaat van PersoonNaamgebruikHistorie
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatNaamgebruikId = null;
        } else {
            predicaatNaamgebruikId = predicaat.getId();
        }
    }

    /**
     * Geef de waarde van adellijke titel van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van adellijke titel van PersoonNaamgebruikHistorie
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelNaamgebruikId);
    }

    /**
     * Zet de waarden voor adellijke titel van PersoonNaamgebruikHistorie.
     *
     * @param adellijkeTitel de nieuwe waarde voor adellijke titel van PersoonNaamgebruikHistorie
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelNaamgebruikId = null;
        } else {
            adellijkeTitelNaamgebruikId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @return de waarde van naamgebruik van PersoonNaamgebruikHistorie
     */
    public Naamgebruik getNaamgebruik() {
        return Naamgebruik.parseId(naamgebruikId);
    }

    /**
     * Zet de waarden voor naamgebruik van PersoonNaamgebruikHistorie.
     *
     * @param naamgebruik de nieuwe waarde voor naamgebruik van PersoonNaamgebruikHistorie
     */
    public void setNaamgebruik(final Naamgebruik naamgebruik) {
        ValidationUtils.controleerOpNullWaarden("naamgebruik mag niet null zijn", naamgebruik);
        naamgebruikId = naamgebruik.getId();
    }

    /**
     * Vergelijkt twee {@link PersoonNaamgebruikHistorie} objecten o.b.v. hun inhoudelijkegegevens.
     * Dus id en verantwoording worden niet meegenomen in de vergelijking.
     *
     * @param anderVoorkomen het voorkomen waarmee vergeleken wordt
     * @return true als dit voorkomen inhoudelijk gelijk is aan het andere voorkomen, anders false
     */
    public final boolean isInhoudelijkGelijkAan(final PersoonNaamgebruikHistorie anderVoorkomen) {
        return anderVoorkomen != null && (anderVoorkomen == this || new EqualsBuilder()
                .append(geslachtsnaamstamNaamgebruik, anderVoorkomen.geslachtsnaamstamNaamgebruik)
                .append(indicatieNaamgebruikAfgeleid, anderVoorkomen.indicatieNaamgebruikAfgeleid).append(naamgebruikId, anderVoorkomen.naamgebruikId)
                .append(scheidingstekenNaamgebruik, anderVoorkomen.scheidingstekenNaamgebruik).append(voornamenNaamgebruik, anderVoorkomen.voornamenNaamgebruik)
                .append(voorvoegselNaamgebruik, anderVoorkomen.voorvoegselNaamgebruik).append(voornamenNaamgebruik, anderVoorkomen.voornamenNaamgebruik)
                .append(adellijkeTitelNaamgebruikId, anderVoorkomen.adellijkeTitelNaamgebruikId)
                .append(predicaatNaamgebruikId, anderVoorkomen.predicaatNaamgebruikId).isEquals());
    }
}
