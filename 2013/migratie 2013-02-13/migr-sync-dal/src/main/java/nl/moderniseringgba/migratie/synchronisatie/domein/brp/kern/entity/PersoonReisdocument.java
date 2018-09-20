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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the persreisdoc database table.
 * 
 */
@Entity
@Table(name = "persreisdoc", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonReisdocument implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSREISDOC_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSREISDOC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSREISDOC_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "datinhingvermissing", precision = 8)
    private BigDecimal datumInhoudingVermissing;

    @Column(name = "datingangdoc", precision = 8)
    private BigDecimal datumIngang;

    @Column(name = "datuitgifte", precision = 8)
    private BigDecimal datumUitgifte;

    @Column(name = "datvoorzeeindegel", precision = 8)
    private BigDecimal datumVoorzieneEindeGeldigheid;

    @Column(precision = 3)
    private BigDecimal lengteHouder;

    @Column(name = "nr", length = 9)
    private String nummer;

    @Enumerated(EnumType.STRING)
    @Column(name = "persreisdocstatushis", nullable = false, length = 1)
    private HistorieStatus persoonReisdocumentStatusHistorie = HistorieStatus.X;

    // bi-directional many-to-one association to PersoonReisdocumentHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonReisdocument", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonReisdocumentHistorie> persoonReisdocumentHistorieSet =
            new LinkedHashSet<PersoonReisdocumentHistorie>(0);

    // bi-directional many-to-one association to AutoriteitVanAfgifteReisdocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "autvanafgifte")
    private AutoriteitVanAfgifteReisdocument autoriteitVanAfgifteReisdocument;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to RedenVervallenReisdocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnvervallen")
    private RedenVervallenReisdocument redenVervallenReisdocument;

    // bi-directional many-to-one association to SoortNederlandsReisdocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt", nullable = false)
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    public PersoonReisdocument() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    public void setDatumInhoudingVermissing(final BigDecimal datumInhoudingVermissing) {
        this.datumInhoudingVermissing = datumInhoudingVermissing;
    }

    public BigDecimal getDatumIngang() {
        return datumIngang;
    }

    public void setDatumIngang(final BigDecimal datumIngang) {
        this.datumIngang = datumIngang;
    }

    public BigDecimal getDatumUitgifte() {
        return datumUitgifte;
    }

    public void setDatumUitgifte(final BigDecimal datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    public BigDecimal getDatumVoorzieneEindeGeldigheid() {
        return datumVoorzieneEindeGeldigheid;
    }

    public void setDatumVoorzieneEindeGeldigheid(final BigDecimal datumVoorzieneEindeGeldigheid) {
        this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
    }

    public BigDecimal getLengteHouder() {
        return lengteHouder;
    }

    public void setLengteHouder(final BigDecimal lengteHouder) {
        this.lengteHouder = lengteHouder;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(final String nummer) {
        this.nummer = nummer;
    }

    public HistorieStatus getPersoonReisdocumentStatusHistorie() {
        return persoonReisdocumentStatusHistorie;
    }

    public void setPersoonReisdocumentStatusHistorie(final HistorieStatus persoonReisdocumentStatusHistorie) {
        this.persoonReisdocumentStatusHistorie = persoonReisdocumentStatusHistorie;
    }

    public Set<PersoonReisdocumentHistorie> getPersoonReisdocumentHistorieSet() {
        return persoonReisdocumentHistorieSet;
    }

    /**
     * @param persoonReisdocumentHistorie
     */
    public void addPersoonReisdocumentHistorieSet(final PersoonReisdocumentHistorie persoonReisdocumentHistorie) {
        persoonReisdocumentHistorie.setPersoonReisdocument(this);
        persoonReisdocumentHistorieSet.add(persoonReisdocumentHistorie);
    }

    public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifteReisdocument() {
        return autoriteitVanAfgifteReisdocument;
    }

    /**
     * @param autoriteitVanAfgifteReisdocument
     */
    public void setAutoriteitVanAfgifteReisdocument(
            final AutoriteitVanAfgifteReisdocument autoriteitVanAfgifteReisdocument) {
        this.autoriteitVanAfgifteReisdocument = autoriteitVanAfgifteReisdocument;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public RedenVervallenReisdocument getRedenVervallenReisdocument() {
        return redenVervallenReisdocument;
    }

    public void setRedenVervallenReisdocument(final RedenVervallenReisdocument redenVervallenReisdocument) {
        this.redenVervallenReisdocument = redenVervallenReisdocument;
    }

    public SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    public void setSoortNederlandsReisdocument(final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
    }
}
