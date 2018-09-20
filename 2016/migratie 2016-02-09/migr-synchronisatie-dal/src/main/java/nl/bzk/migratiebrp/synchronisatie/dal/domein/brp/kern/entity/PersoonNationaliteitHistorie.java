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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persnation database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persnation", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persnation", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonNationaliteitHistorie extends AbstractMaterieleHistorie implements Serializable {
    /**
     * Veldnaam van einde bijhouding.
     */
    public static final String EINDE_BIJHOUDING = "indicatieBijhoudingBeeindigd";
    /**
     * Veldnaam van migratie datum.
     */
    public static final String MIGRATIE_DATUM = "migratieDatumEindeBijhouding";
    /**
     * Veldnaam van migratie reden beeindiging nationaliteit.
     */
    public static final String MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT = "migratieRedenBeeindigenNationaliteit";
    /**
     * Veldnaam van reden verlies NL nationaliteit.
     */
    public static final String VELD_REDEN_VERLIES_NL_NATIONALITEIT = "redenVerliesNLNationaliteit";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "his_persnation_id_generator", sequenceName = "kern.seq_his_persnation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persnation_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to PersoonNationaliteit
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persnation", nullable = false)
    private PersoonNationaliteit persoonNationaliteit;

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
    protected PersoonNationaliteitHistorie() {
    }

    /**
     * Maak een nieuwe persoon nationaliteit historie.
     *
     * @param persoonNationaliteit
     *            persoon nationaliteit
     */
    public PersoonNationaliteitHistorie(final PersoonNationaliteit persoonNationaliteit) {
        setPersoonNationaliteit(persoonNationaliteit);
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
     * Geef de waarde van persoon nationaliteit.
     *
     * @return persoon nationaliteit
     */
    public PersoonNationaliteit getPersoonNationaliteit() {
        return persoonNationaliteit;
    }

    /**
     * Zet de waarde van persoon nationaliteit.
     *
     * @param persoonNationaliteit
     *            persoon nationaliteit
     */
    public void setPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        ValidationUtils.controleerOpNullWaarden("persoonNationaliteit mag niet null zijn", persoonNationaliteit);
        this.persoonNationaliteit = persoonNationaliteit;
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
}
