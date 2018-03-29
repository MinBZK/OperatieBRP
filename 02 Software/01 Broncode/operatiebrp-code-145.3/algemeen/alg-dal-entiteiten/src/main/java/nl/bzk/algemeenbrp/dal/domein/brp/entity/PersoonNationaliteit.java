/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persnation database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persnation", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "nation"}))
public class PersoonNationaliteit extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";
    private static final String NATIONALITEIT_MAG_NIET_NULL_ZIJN = "nationaliteit mag niet null zijn";

    @Id
    @SequenceGenerator(name = "persnation_id_generator", sequenceName = "kern.seq_persnation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persnation_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to PersoonNationaliteitHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonNationaliteit", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonNationaliteitHistorie> persoonNationaliteitHistorieSet = new LinkedHashSet<>(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation", nullable = false)
    private Nationaliteit nationaliteit;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnverk")
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    /**
     * JPA default constructor.
     */
    protected PersoonNationaliteit() {}

    /**
     * Maak een nieuwe persoon nationaliteit.
     *
     * @param persoon persoon
     * @param nationaliteit nationaliteit
     */
    public PersoonNationaliteit(final Persoon persoon, final Nationaliteit nationaliteit) {
        setPersoon(persoon);
        setNationaliteit(nationaliteit);
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
     * Zet de waarden voor id van PersoonNationaliteit.
     *
     * @param id de nieuwe waarde voor id van PersoonNationaliteit
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoon nationaliteit historie set van PersoonNationaliteit.
     *
     * @return de waarde van persoon nationaliteit historie set van PersoonNationaliteit
     */
    public Set<PersoonNationaliteitHistorie> getPersoonNationaliteitHistorieSet() {
        return persoonNationaliteitHistorieSet;
    }

    /**
     * Toevoegen van een persoon nationaliteit historie.
     *
     * @param persoonNationaliteitHistorie persoon nationaliteit historie
     */
    public void addPersoonNationaliteitHistorie(final PersoonNationaliteitHistorie persoonNationaliteitHistorie) {
        persoonNationaliteitHistorie.setPersoonNationaliteit(this);
        persoonNationaliteitHistorieSet.add(persoonNationaliteitHistorie);
    }

    /**
     * Geef de waarde van nationaliteit van PersoonNationaliteit.
     *
     * @return de waarde van nationaliteit van PersoonNationaliteit
     */
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    /**
     * Zet de waarden voor nationaliteit van PersoonNationaliteit.
     *
     * @param nationaliteit de nieuwe waarde voor nationaliteit van PersoonNationaliteit
     */
    public void setNationaliteit(final Nationaliteit nationaliteit) {
        ValidationUtils.controleerOpNullWaarden(NATIONALITEIT_MAG_NIET_NULL_ZIJN, nationaliteit);
        this.nationaliteit = nationaliteit;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaSubRootEntiteit#getPersoon()
     */
    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonNationaliteit.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonNationaliteit
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van reden verkrijging nl nationaliteit van PersoonNationaliteit.
     *
     * @return de waarde van reden verkrijging nl nationaliteit van PersoonNationaliteit
     */
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return redenVerkrijgingNLNationaliteit;
    }

    /**
     * Zet de waarden voor reden verkrijging nl nationaliteit van PersoonNationaliteit.
     *
     * @param redenVerkrijgingNLNationaliteit de nieuwe waarde voor reden verkrijging nl
     *        nationaliteit van PersoonNationaliteit
     */
    public void setRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;
    }

    /**
     * Geef de waarde van reden verlies nl nationaliteit van PersoonNationaliteit.
     *
     * @return de waarde van reden verlies nl nationaliteit van PersoonNationaliteit
     */
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    /**
     * Zet de waarden voor reden verlies nl nationaliteit van PersoonNationaliteit.
     *
     * @param redenVerliesNLNationaliteit de nieuwe waarde voor reden verlies nl nationaliteit van
     *        PersoonNationaliteit
     */
    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }

    /**
     * Geef de waarde van indicatie bijhouding beeindigd van PersoonNationaliteit.
     *
     * @return de waarde van indicatie bijhouding beeindigd van PersoonNationaliteit
     */
    public Boolean getIndicatieBijhoudingBeeindigd() {
        return indicatieBijhoudingBeeindigd;
    }

    /**
     * Zet de waarden voor indicatie bijhouding beeindigd van PersoonNationaliteit.
     *
     * @param indicatieBijhoudingBeeindigd de nieuwe waarde voor indicatie bijhouding beeindigd van
     *        PersoonNationaliteit
     */
    public void setIndicatieBijhoudingBeeindigd(final Boolean indicatieBijhoudingBeeindigd) {
        this.indicatieBijhoudingBeeindigd = indicatieBijhoudingBeeindigd;
    }

    /**
     * Geef de waarde van migratie datum einde bijhouding van PersoonNationaliteit.
     *
     * @return de waarde van migratie datum einde bijhouding van PersoonNationaliteit
     */
    public Integer getMigratieDatumEindeBijhouding() {
        return migratieDatumEindeBijhouding;
    }

    /**
     * Zet de waarden voor migratie datum einde bijhouding van PersoonNationaliteit.
     *
     * @param migratieDatumEindeBijhouding de nieuwe waarde voor migratie datum einde bijhouding van
     *        PersoonNationaliteit
     */
    public void setMigratieDatumEindeBijhouding(final Integer migratieDatumEindeBijhouding) {
        this.migratieDatumEindeBijhouding = migratieDatumEindeBijhouding;
    }

    /**
     * Geef de waarde van migratie reden opname nationaliteit van PersoonNationaliteit.
     *
     * @return de waarde van migratie reden opname nationaliteit van PersoonNationaliteit
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden opname nationaliteit van PersoonNationaliteit.
     *
     * @param migratieRedenOpnameNationaliteit de nieuwe waarde voor migratie reden opname
     *        nationaliteit van PersoonNationaliteit
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * Geef de waarde van migratie reden beeindigen nationaliteit van PersoonNationaliteit.
     *
     * @return de waarde van migratie reden beeindigen nationaliteit van PersoonNationaliteit
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden beeindigen nationaliteit van PersoonNationaliteit.
     *
     * @param migratieRedenBeeindigenNationaliteit de nieuwe waarde voor migratie reden beeindigen
     *        nationaliteit van PersoonNationaliteit
     */
    public void setMigratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit) {
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("persoonNationaliteitHistorieSet", Collections.unmodifiableSet(persoonNationaliteitHistorieSet));
        return result;
    }
}
