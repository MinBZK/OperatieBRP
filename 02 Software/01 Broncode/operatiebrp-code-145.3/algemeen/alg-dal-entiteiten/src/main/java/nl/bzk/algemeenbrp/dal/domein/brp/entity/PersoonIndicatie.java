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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persindicatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persindicatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "srt"}))
public class PersoonIndicatie extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persindicatie_id_generator", sequenceName = "kern.seq_persindicatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persindicatie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "waarde")
    private Boolean waarde;

    // bi-directional many-to-one association to PersoonIndicatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonIndicatie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonIndicatieHistorie> persoonIndicatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "srt", nullable = false)
    private int soortIndicatieId;

    @Column(name = "migrRdnOpnameNation")
    private String migratieRedenOpnameNationaliteit;

    @Column(name = "migrRdnBeeindigenNation")
    private String migratieRedenBeeindigenNationaliteit;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    /**
     * JPA default constructor.
     */
    protected PersoonIndicatie() {}

    /**
     * Maak een nieuwe persoon indicatie.
     *
     * @param persoon persoon
     * @param soortIndicatie soort indicatie
     */
    public PersoonIndicatie(final Persoon persoon, final SoortIndicatie soortIndicatie) {
        setPersoon(persoon);
        setSoortIndicatie(soortIndicatie);
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
     * Zet de waarden voor id van PersoonIndicatie.
     *
     * @param id de nieuwe waarde voor id van PersoonIndicatie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van waarde van PersoonIndicatie.
     *
     * @return de waarde van waarde van PersoonIndicatie
     */
    public Boolean getWaarde() {
        return waarde;
    }

    /**
     * Zet de waarden voor waarde van PersoonIndicatie.
     *
     * @param waarde de nieuwe waarde voor waarde van PersoonIndicatie
     */
    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }

    /**
     * Geef de waarde van persoon indicatie historie set van PersoonIndicatie.
     *
     * @return de waarde van persoon indicatie historie set van PersoonIndicatie
     */
    public Set<PersoonIndicatieHistorie> getPersoonIndicatieHistorieSet() {
        return persoonIndicatieHistorieSet;
    }

    /**
     * Toevoegen van een persoon indicatie historie.
     *
     * @param persoonIndicatieHistorie persoon indicatie historie
     */
    public void addPersoonIndicatieHistorie(final PersoonIndicatieHistorie persoonIndicatieHistorie) {
        persoonIndicatieHistorie.setPersoonIndicatie(this);
        persoonIndicatieHistorieSet.add(persoonIndicatieHistorie);
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
     * Zet de waarden voor persoon van PersoonIndicatie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonIndicatie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van soort indicatie van PersoonIndicatie.
     *
     * @return de waarde van soort indicatie van PersoonIndicatie
     */
    public SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.parseId(soortIndicatieId);
    }

    /**
     * Zet de waarden voor soort indicatie van PersoonIndicatie.
     *
     * @param soortIndicatie de nieuwe waarde voor soort indicatie van PersoonIndicatie
     */
    public void setSoortIndicatie(final SoortIndicatie soortIndicatie) {
        ValidationUtils.controleerOpNullWaarden("soortIndicatie mag niet null zijn", soortIndicatie);
        soortIndicatieId = soortIndicatie.getId();
    }

    /**
     * Geef de waarde van migratie reden opname nationaliteit van PersoonIndicatie.
     *
     * @return de waarde van migratie reden opname nationaliteit van PersoonIndicatie
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden opname nationaliteit van PersoonIndicatie.
     *
     * @param migratieRedenOpnameNationaliteit de nieuwe waarde voor migratie reden opname
     *        nationaliteit van PersoonIndicatie
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * Geef de waarde van migratie reden beeindigen nationaliteit van PersoonIndicatie.
     *
     * @return de waarde van migratie reden beeindigen nationaliteit van PersoonIndicatie
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de waarden voor migratie reden beeindigen nationaliteit van PersoonIndicatie.
     *
     * @param migratieRedenBeeindigenNationaliteit de nieuwe waarde voor migratie reden beeindigen
     *        nationaliteit van PersoonIndicatie
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
        result.put("persoonIndicatieHistorieSet", Collections.unmodifiableSet(persoonIndicatieHistorieSet));
        return result;
    }
}
