/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persaanschr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persnaamgebruik", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonNaamgebruikHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persaanschr_id_generator", sequenceName = "kern.seq_his_persnaamgebruik", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persaanschr_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predicaatnaamgebruik")
    private Short predicaatId;

    @Column(name = "adellijketitelnaamgebruik")
    private Short adellijkeTitelId;

    @Column(name = "naamgebruik", nullable = false)
    private short naamgebruikId;

    /**
     * JPA default constructor.
     */
    protected PersoonNaamgebruikHistorie() {
    }

    /**
     * Maak een nieuwe persoon naamgebruik historie.
     *
     * @param persoon
     *            persoon
     * @param geslachtsnaamstamNaamgebruik
     *            geslachtsnaamstam naamgebruik
     * @param indicatieNaamgebruikAfgeleid
     *            indicatie naamgebruik afgeleid
     * @param naamgebruik
     *            naamgebruik
     */
    public PersoonNaamgebruikHistorie(
        final Persoon persoon,
        final String geslachtsnaamstamNaamgebruik,
        final boolean indicatieNaamgebruikAfgeleid,
        final Naamgebruik naamgebruik)
    {
        setPersoon(persoon);
        setGeslachtsnaamstamNaamgebruik(geslachtsnaamstamNaamgebruik);
        setIndicatieNaamgebruikAfgeleid(indicatieNaamgebruikAfgeleid);
        setNaamgebruik(naamgebruik);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van geslachtsnaamstam naamgebruik.
     *
     * @return geslachtsnaamstam naamgebruik
     */
    public String getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Zet de waarde van geslachtsnaamstam naamgebruik.
     *
     * @param geslachtsnaamstamNaamgebruik
     *            geslachtsnaamstam naamgebruik
     */
    public void setGeslachtsnaamstamNaamgebruik(final String geslachtsnaamstamNaamgebruik) {
        ValidationUtils.controleerOpNullWaarden("geslachtsnaamstamNaamgebruik mag niet null zijn", geslachtsnaamstamNaamgebruik);
        Validatie.controleerOpLegeWaarden("geslachtsnaamstamNaamgebruik mag geen lege string zijn", geslachtsnaamstamNaamgebruik);
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geef de waarde van indicatie naamgebruik afgeleid.
     *
     * @return indicatie naamgebruik afgeleid
     */
    public Boolean getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * Zet de waarde van indicatie naamgebruik afgeleid.
     *
     * @param indicatieNaamgebruikAfgeleid
     *            indicatie naamgebruik afgeleid
     */
    public void setIndicatieNaamgebruikAfgeleid(final boolean indicatieNaamgebruikAfgeleid) {
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
    }

    /**
     * Geef de waarde van scheidingsteken naamgebruik.
     *
     * @return scheidingsteken naamgebruik
     */
    public Character getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * Zet de waarde van scheidingsteken naamgebruik.
     *
     * @param scheidingstekenNaamgebruik
     *            scheidingsteken naamgebruik
     */
    public void setScheidingstekenNaamgebruik(final Character scheidingstekenNaamgebruik) {
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
    }

    /**
     * Geef de waarde van voornamen naamgebruik.
     *
     * @return voornamen naamgebruik
     */
    public String getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * Zet de waarde van voornamen naamgebruik.
     *
     * @param voornamenNaamgebruik
     *            voornamen naamgebruik
     */
    public void setVoornamenNaamgebruik(final String voornamenNaamgebruik) {
        Validatie.controleerOpLegeWaarden("voornamenNaamgebruik mag geen lege string zijn", voornamenNaamgebruik);
        this.voornamenNaamgebruik = voornamenNaamgebruik;
    }

    /**
     * Geef de waarde van voorvoegsel naamgebruik.
     *
     * @return voorvoegsel naamgebruik
     */
    public String getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * Zet de waarde van voorvoegsel naamgebruik.
     *
     * @param voorvoegselNaamgebruik
     *            voorvoegsel naamgebruik
     */
    public void setVoorvoegselNaamgebruik(final String voorvoegselNaamgebruik) {
        Validatie.controleerOpLegeWaarden("voorvoegselNaamgebruik mag geen lege string zijn", voorvoegselNaamgebruik);
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van predicaat.
     *
     * @return predicaat
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarde van predicaat.
     *
     * @param predicaat
     *            predicaat
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarde van adellijke titel.
     *
     * @param adellijkeTitel
     *            adellijke titel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van naamgebruik.
     *
     * @return naamgebruik
     */
    public Naamgebruik getNaamgebruik() {
        return Naamgebruik.parseId(naamgebruikId);
    }

    /**
     * Zet de waarde van naamgebruik.
     *
     * @param naamgebruik
     *            naamgebruik
     */
    public void setNaamgebruik(final Naamgebruik naamgebruik) {
        ValidationUtils.controleerOpNullWaarden("naamgebruik mag niet null zijn", naamgebruik);
        naamgebruikId = naamgebruik.getId();
    }
}
