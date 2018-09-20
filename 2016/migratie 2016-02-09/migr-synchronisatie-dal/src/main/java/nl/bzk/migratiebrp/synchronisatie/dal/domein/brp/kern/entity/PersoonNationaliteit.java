/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the persnation database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persnation", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "nation" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonNationaliteit extends AbstractDeltaEntiteit implements DeltaSubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";
    private static final String NATIONALITEIT_MAG_NIET_NULL_ZIJN = "nationaliteit mag niet null zijn";

    @Id
    @SequenceGenerator(name = "persnation_id_generator", sequenceName = "kern.seq_persnation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persnation_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to PersoonNationaliteitHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonNationaliteit", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonNationaliteitHistorie> persoonNationaliteitHistorieSet = new LinkedHashSet<>(0);

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "nation", nullable = false)
    private Nationaliteit nationaliteit;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnverk")
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnverlies")
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;

    @Column(name = "indbijhoudingbeeindigd")
    private Boolean indicatieBijhoudingBeeindigd;

    @Column(name = "migrdateindebijhouding")
    private Integer migratieDatumEindeBijhouding;

    @Column(name = "migrrdnopnamenation")
    private String migratieRedenOpnameNationaliteit;

    @Column(name = "migrrdnbeeindigennation")
    private String migratieRedenBeeindigenNationaliteit;

    /**
     * JPA default constructor.
     */
    protected PersoonNationaliteit() {
    }

    /**
     * Maak een nieuwe persoon nationaliteit.
     *
     * @param persoon
     *            persoon
     * @param nationaliteit
     *            nationaliteit
     */
    public PersoonNationaliteit(final Persoon persoon, final Nationaliteit nationaliteit) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
        ValidationUtils.controleerOpNullWaarden(NATIONALITEIT_MAG_NIET_NULL_ZIJN, nationaliteit);
        this.nationaliteit = nationaliteit;
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
     * Geef de waarde van persoon nationaliteit historie set.
     *
     * @return persoon nationaliteit historie set
     */
    public Set<PersoonNationaliteitHistorie> getPersoonNationaliteitHistorieSet() {
        return persoonNationaliteitHistorieSet;
    }

    /**
     * Toevoegen van een persoon nationaliteit historie.
     *
     * @param persoonNationaliteitHistorie
     *            persoon nationaliteit historie
     */
    public void addPersoonNationaliteitHistorie(final PersoonNationaliteitHistorie persoonNationaliteitHistorie) {
        persoonNationaliteitHistorie.setPersoonNationaliteit(this);
        persoonNationaliteitHistorieSet.add(persoonNationaliteitHistorie);
    }

    /**
     * Geef de waarde van nationaliteit.
     *
     * @return nationaliteit
     */
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    /**
     * Zet de waarde van nationaliteit.
     *
     * @param nationaliteit
     *            nationaliteit
     */
    public void setNationaliteit(final Nationaliteit nationaliteit) {
        ValidationUtils.controleerOpNullWaarden(NATIONALITEIT_MAG_NIET_NULL_ZIJN, nationaliteit);
        this.nationaliteit = nationaliteit;
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
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van reden verkrijging nl nationaliteit.
     *
     * @return reden verkrijging nl nationaliteit
     */
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return redenVerkrijgingNLNationaliteit;
    }

    /**
     * Zet de waarde van reden verkrijging nl nationaliteit.
     *
     * @param redenVerkrijgingNLNationaliteit
     *            reden verkrijging nl nationaliteit
     */
    public void setRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;
    }

    /**
     * Geef de waarde van reden verlies nl nationaliteit.
     *
     * @return reden verlies nl nationaliteit
     */
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    /**
     * Zet de waarde van reden verlies nl nationaliteit.
     *
     * @param redenVerliesNLNationaliteit
     *            reden verlies nl nationaliteit
     */
    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }

    /**
     * @return geeft de einde bijhouding terug. true als de nationaliteit niet meer bij gehouden wordt, anders null.
     */
    public Boolean getIndicatieBijhoudingBeeindigd() {
        return indicatieBijhoudingBeeindigd;
    }

    /**
     * Zet de einde bijhouding van deze nationaliteit.
     *
     * @param indicatieBijhoudingBeeindigd
     *            true als de nationaliteit niet meer bijgehouden wordt
     */
    public void setIndicatieBijhoudingBeeindigd(final Boolean indicatieBijhoudingBeeindigd) {
        this.indicatieBijhoudingBeeindigd = indicatieBijhoudingBeeindigd;
    }

    /**
     * @return Geeft de datum terug welke tijdens conversie niet wordt geconverteerd. Dit veld representeerd 85.10 van
     *         de LO3.
     */
    public Integer getMigratieDatumEindeBijhouding() {
        return migratieDatumEindeBijhouding;
    }

    /**
     * Zet de migratie datum.
     *
     * @param migratieDatumEindeBijhouding
     *            de datum 85.10 van de nationaliteit die niet meer bij gehouden wordt.
     */
    public void setMigratieDatumEindeBijhouding(final Integer migratieDatumEindeBijhouding) {
        this.migratieDatumEindeBijhouding = migratieDatumEindeBijhouding;
    }

    /**
     * @return Geeft de reden opname nationaliteit terug waar geen BRP variant voor is.
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de reden opname nationaliteit waar geen BRP variant voor is.
     *
     * @param migratieRedenOpnameNationaliteit
     *            de reden opname nationaliteit
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * @return Geeft de reden beeindiging nationaliteit terug waar geen BRP variant voor is en alleen tbv is voor de
     *         migratie.
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de reden beeindiging nationaliteit waar geen BRP variant voor is.
     *
     * @param migratieRedenBeeindigenNationaliteit
     *            de reden beeindiging nationaliteit
     */
    public void setMigratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit) {
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();
        result.put("persoonNationaliteitHistorieSet", Collections.unmodifiableSet(persoonNationaliteitHistorieSet));
        return result;
    }
}
