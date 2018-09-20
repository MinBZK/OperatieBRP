/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the onderzoek database table.
 * 
 */
@Entity
@Table(name = "onderzoek", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Onderzoek implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ONDERZOEK_ID_GENERATOR", sequenceName = "KERN.SEQ_ONDERZOEK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ONDERZOEK_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "datbegin", precision = 8)
    private BigDecimal datumBegin;

    @Column(name = "dateinde", precision = 8)
    private BigDecimal datumEinde;

    @Column(name = "oms")
    private String omschrijving;

    @Column(name = "onderzoekstatushis", nullable = false, length = 1)
    private String onderzoekStatusHistorie;

    // bi-directional many-to-one association to GegevenInOnderzoek
    @OneToMany(mappedBy = "onderzoek", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<GegevenInOnderzoek> gegevenInOnderzoekSet = new LinkedHashSet<GegevenInOnderzoek>(0);

    // bi-directional many-to-one association to OnderzoekHistorie
    @OneToMany(mappedBy = "onderzoek", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<OnderzoekHistorie> onderzoekHistorieSet = new LinkedHashSet<OnderzoekHistorie>(0);

    public Onderzoek() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatumBegin() {
        return datumBegin;
    }

    public void setDatumBegin(final BigDecimal datumBegin) {
        this.datumBegin = datumBegin;
    }

    public BigDecimal getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final BigDecimal datumEinde) {
        this.datumEinde = datumEinde;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOnderzoekStatusHistorie() {
        return onderzoekStatusHistorie;
    }

    public void setOnderzoekStatusHistorie(final String onderzoekStatusHistorie) {
        this.onderzoekStatusHistorie = onderzoekStatusHistorie;
    }

    public Set<GegevenInOnderzoek> getGegevenInOnderzoekSet() {
        return gegevenInOnderzoekSet;
    }

    /**
     * @param gegevenInOnderzoek
     */
    public void addGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        gegevenInOnderzoek.setOnderzoek(this);
        gegevenInOnderzoekSet.add(gegevenInOnderzoek);
    }

    public Set<OnderzoekHistorie> getOnderzoekHistorieSet() {
        return onderzoekHistorieSet;
    }

    /**
     * @param onderzoekHistorie
     */
    public void addOnderzoekHistorie(final OnderzoekHistorie onderzoekHistorie) {
        onderzoekHistorieSet.add(onderzoekHistorie);
    }
}
