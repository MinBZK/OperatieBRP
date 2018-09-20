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
 * The persistent class for the persindicatie database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persindicatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "srt" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonIndicatie extends AbstractDeltaEntiteit implements DeltaSubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persindicatie_id_generator", sequenceName = "kern.seq_persindicatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persindicatie_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "waarde")
    private Boolean waarde;

    // bi-directional many-to-one association to PersoonIndicatieHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonIndicatie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonIndicatieHistorie> persoonIndicatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "srt", nullable = false)
    private short soortIndicatieId;

    @Column(name = "migrRdnOpnameNation")
    private String migratieRedenOpnameNationaliteit;

    @Column(name = "migrRdnBeeindigenNation")
    private String migratieRedenBeeindigenNationaliteit;

    /**
     * JPA default constructor.
     */
    protected PersoonIndicatie() {
    }

    /**
     * Maak een nieuwe persoon indicatie.
     *
     * @param persoon
     *            persoon
     * @param soortIndicatie
     *            soort indicatie
     */
    public PersoonIndicatie(final Persoon persoon, final SoortIndicatie soortIndicatie) {
        setPersoon(persoon);
        setSoortIndicatie(soortIndicatie);
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
     * Geef de waarde van waarde.
     *
     * @return waarde
     */
    public Boolean getWaarde() {
        return waarde;
    }

    /**
     * Zet de waarde van waarde.
     *
     * @param waarde
     *            waarde
     */
    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }

    /**
     * Geef de waarde van persoon indicatie historie set.
     *
     * @return persoon indicatie historie set
     */
    public Set<PersoonIndicatieHistorie> getPersoonIndicatieHistorieSet() {
        return persoonIndicatieHistorieSet;
    }

    /**
     * Toevoegen van een persoon indicatie historie.
     *
     * @param persoonIndicatieHistorie
     *            persoon indicatie historie
     */
    public void addPersoonIndicatieHistorie(final PersoonIndicatieHistorie persoonIndicatieHistorie) {
        persoonIndicatieHistorie.setPersoonIndicatie(this);
        persoonIndicatieHistorieSet.add(persoonIndicatieHistorie);
    }

    @Override
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
     * Geef de waarde van soort indicatie.
     *
     * @return soort indicatie
     */
    public SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.parseId(soortIndicatieId);
    }

    /**
     * Zet de waarde van soort indicatie.
     *
     * @param soortIndicatie
     *            soort indicatie
     */
    public void setSoortIndicatie(final SoortIndicatie soortIndicatie) {
        ValidationUtils.controleerOpNullWaarden("soortIndicatie mag niet null zijn", soortIndicatie);
        soortIndicatieId = soortIndicatie.getId();
    }

    /**
     * @return de reden opname nationaliteit tbv migratie/conversie
     */
    public String getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet de migratie reden opname nationaliteit tbv migratie/conversie.
     *
     * @param migratieRedenOpnameNationaliteit
     *            de reden opname nationaliteit
     */
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * @return de reden beeindiging nationaliteit tbv migratie/conversie
     */
    public String getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet de migratie reden beeindiging nationaliteit tbv migratie/conversie.
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
        result.put("persoonIndicatieHistorieSet", Collections.unmodifiableSet(persoonIndicatieHistorieSet));
        return result;
    }
}
