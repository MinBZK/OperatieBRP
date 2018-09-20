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
 * The persistent class for the persadres database table.
 * 
 */
@Entity
@Table(name = "persadres", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonAdres implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSADRES_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSADRES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSADRES_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(length = 16)
    private String adresseerbaarObject;

    @Column(name = "afgekortenor", length = 24)
    private String afgekorteNaamOpenbareRuimte;

    @Column(name = "bladresregel1", length = 40)
    private String buitenlandsAdresRegel1;

    @Column(name = "bladresregel2", length = 40)
    private String buitenlandsAdresRegel2;

    @Column(name = "bladresregel3", length = 40)
    private String buitenlandsAdresRegel3;

    @Column(name = "bladresregel4", length = 40)
    private String buitenlandsAdresRegel4;

    @Column(name = "bladresregel5", length = 40)
    private String buitenlandsAdresRegel5;

    @Column(name = "bladresregel6", length = 40)
    private String buitenlandsAdresRegel6;

    @Column(name = "dataanvadresh", precision = 8)
    private BigDecimal datumAanvangAdreshouding;

    @Column(name = "datvertrekuitnederland", precision = 8)
    private BigDecimal datumVertrekUitNederland;

    @Column(name = "gemdeel", length = 24)
    private String gemeentedeel;

    @Column(length = 1)
    private String huisletter;

    @Column(name = "huisnr", precision = 5)
    private BigDecimal huisnummer;

    @Column(name = "huisnrtoevoeging", length = 4)
    private String huisnummertoevoeging;

    @Column(name = "identcodenraand", length = 16)
    private String identificatiecodeNummeraanduiding;

    @Column(name = "locoms", length = 40)
    private String locatieOmschrijving;

    @Column(name = "loctovadres", length = 2)
    private String locatietovAdres;

    @Column(name = "nor", length = 80)
    private String naamOpenbareRuimte;

    @Enumerated(EnumType.STRING)
    @Column(name = "persadresstatushis", nullable = false, length = 1)
    private HistorieStatus persoonAdresStatusHistorie = HistorieStatus.X;

    @Column(length = 6)
    private String postcode;

    // bi-directional many-to-one association to PersoonAdresHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonAdres", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonAdresHistorie> persoonAdresHistorieSet = new LinkedHashSet<PersoonAdresHistorie>(0);

    // bi-directional many-to-one association to AangeverAdreshouding
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "aangadresh")
    private AangeverAdreshouding aangeverAdreshouding;

    @Column(name = "srt")
    private Integer functieAdresId;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "land")
    private Land land;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gem")
    private Partij partij;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wpl")
    private Plaats plaats;

    // bi-directional many-to-one association to RedenWijzigingAdres
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnwijz")
    private RedenWijzigingAdres redenWijzigingAdres;

    public PersoonAdres() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        this.adresseerbaarObject = adresseerbaarObject;
    }

    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    public BigDecimal getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    public void setDatumAanvangAdreshouding(final BigDecimal datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    public BigDecimal getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    public void setDatumVertrekUitNederland(final BigDecimal datumVertrekUitNederland) {
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    public String getGemeentedeel() {
        return gemeentedeel;
    }

    public void setGemeentedeel(final String gemeentedeel) {
        this.gemeentedeel = gemeentedeel;
    }

    public String getHuisletter() {
        return huisletter;
    }

    public void setHuisletter(final String huisletter) {
        this.huisletter = huisletter;
    }

    public BigDecimal getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(final BigDecimal huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        this.locatieOmschrijving = locatieOmschrijving;
    }

    public String getLocatietovAdres() {
        return locatietovAdres;
    }

    public void setLocatietovAdres(final String locatietovAdres) {
        this.locatietovAdres = locatietovAdres;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public HistorieStatus getPersoonAdresStatusHistorie() {
        return persoonAdresStatusHistorie;
    }

    public void setPersoonAdresStatusHistorie(final HistorieStatus persoonAdresStatusHistorie) {
        this.persoonAdresStatusHistorie = persoonAdresStatusHistorie;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    public Set<PersoonAdresHistorie> getPersoonAdresHistorieSet() {
        return persoonAdresHistorieSet;
    }

    /**
     * @param persoonAdresHistorie
     */
    public void addPersoonAdresHistorie(final PersoonAdresHistorie persoonAdresHistorie) {
        persoonAdresHistorie.setPersoonAdres(this);
        persoonAdresHistorieSet.add(persoonAdresHistorie);
    }

    public AangeverAdreshouding getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    public void setAangeverAdreshouding(final AangeverAdreshouding aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    /**
     * @return
     */
    public FunctieAdres getFunctieAdres() {
        return FunctieAdres.parseId(functieAdresId);
    }

    /**
     * @param functieAdres
     */
    public void setFunctieAdres(final FunctieAdres functieAdres) {
        if (functieAdres == null) {
            functieAdresId = null;
        } else {
            functieAdresId = functieAdres.getId();
        }
    }

    public Land getLand() {
        return land;
    }

    public void setLand(final Land land) {
        this.land = land;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Plaats getPlaats() {
        return plaats;
    }

    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

    public RedenWijzigingAdres getRedenWijzigingAdres() {
        return redenWijzigingAdres;
    }

    public void setRedenWijzigingAdres(final RedenWijzigingAdres redenWijzigingAdres) {
        this.redenWijzigingAdres = redenWijzigingAdres;
    }
}
