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
 * The persistent class for the his_perssamengesteldenaam database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_perssamengesteldenaam", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonSamengesteldeNaamHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_perssamengesteldenaam_id_generator", sequenceName = "kern.seq_his_perssamengesteldenaam", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_perssamengesteldenaam_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
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
    private Short adellijkeTitelId;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predicaat")
    private Short predicaatId;

    /**
     * JPA default constructor.
     */
    protected PersoonSamengesteldeNaamHistorie() {
    }

    /**
     * Maak een nieuwe persoon samengestelde naam historie.
     *
     * @param persoon
     *            persoon
     * @param geslachtsnaamstam
     *            geslachtsnaamstam
     * @param indicatieAlgoritmischAfgeleid
     *            indicatie algoritmisch afgeleid
     * @param indicatieNamenreeks
     *            indicatie namenreeks
     */
    public PersoonSamengesteldeNaamHistorie(
        final Persoon persoon,
        final String geslachtsnaamstam,
        final boolean indicatieAlgoritmischAfgeleid,
        final boolean indicatieNamenreeks)
    {
        setPersoon(persoon);
        setGeslachtsnaamstam(geslachtsnaamstam);
        setIndicatieAfgeleid(indicatieAlgoritmischAfgeleid);
        setIndicatieNamenreeks(indicatieNamenreeks);
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
     * Geef de waarde van geslachtsnaamstam.
     *
     * @return geslachtsnaamstam
     */
    public String getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet de waarde van geslachtsnaamstam.
     *
     * @param geslachtsnaamstam
     *            geslachtsnaamstam
     */
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        ValidationUtils.controleerOpNullWaarden("geslachtsnaamstam mag niet null zijn", geslachtsnaamstam);
        Validatie.controleerOpLegeWaarden("geslachtsnaamstam mag geen lege string zijn", geslachtsnaamstam);
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van indicatie afgeleid.
     *
     * @return indicatie afgeleid
     */
    public boolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Zet de waarde van indicatie afgeleid.
     *
     * @param indicatieAfgeleid
     *            indicatie afgeleid
     */
    public void setIndicatieAfgeleid(final boolean indicatieAfgeleid) {
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    /**
     * Geef de waarde van indicatie namenreeks.
     *
     * @return indicatie namenreeks
     */
    public boolean getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Zet de waarde van indicatie namenreeks.
     *
     * @param indicatieNamenreeks
     *            indicatie namenreeks
     */
    public void setIndicatieNamenreeks(final boolean indicatieNamenreeks) {
        this.indicatieNamenreeks = indicatieNamenreeks;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarde van scheidingsteken.
     *
     * @param scheidingsteken
     *            scheidingsteken
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van voornamen.
     *
     * @return voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarde van voornamen.
     *
     * @param voornamen
     *            voornamen
     */
    public void setVoornamen(final String voornamen) {
        Validatie.controleerOpLegeWaarden("voornamen mag geen lege string zijn", voornamen);
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarde van voorvoegsel.
     *
     * @param voorvoegsel
     *            voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        Validatie.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
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
        if (adellijkeTitel != null) {
            adellijkeTitelId = adellijkeTitel.getId();
        } else {
            adellijkeTitelId = null;
        }
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
        if (predicaat != null) {
            predicaatId = predicaat.getId();
        } else {
            predicaatId = null;
        }
    }
}
