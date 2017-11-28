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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persadres database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persadres", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class PersoonAdres extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persadres_id_generator", sequenceName = "kern.seq_persadres", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persadres_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "identcodeadresseerbaarobject", length = 16)
    private String identificatiecodeAdresseerbaarObject;

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

    @Column(name = "dataanvadresh")
    private Integer datumAanvangAdreshouding;

    @Column(name = "gemdeel", length = 24)
    private String gemeentedeel;

    @Column(length = 1)
    private Character huisletter;

    @Column(name = "huisnr")
    private Integer huisnummer;

    @Column(name = "huisnrtoevoeging", length = 4)
    private String huisnummertoevoeging;

    @Column(name = "identcodenraand", length = 16)
    private String identificatiecodeNummeraanduiding;

    @Column(name = "locoms", length = 40)
    private String locatieOmschrijving;

    @Column(name = "loctenopzichtevanadres", length = 2)
    private String locatietovAdres;

    @Column(name = "nor", length = 80)
    private String naamOpenbareRuimte;

    @Column(length = 6)
    private String postcode;

    // bi-directional many-to-one association to PersoonAdresHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonAdres", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<PersoonAdresHistorie> persoonAdresHistorieSet = new LinkedHashSet<>(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aangadresh")
    private Aangever aangeverAdreshouding;

    @Column(name = "srt")
    private Integer soortAdresId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebied")
    private LandOfGebied landOfGebied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gem")
    private Gemeente gemeente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "wplnaam")
    private String woonplaatsnaam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnwijz")
    private RedenWijzigingVerblijf redenWijziging;

    @Column(name = "indpersaangetroffenopadres")
    private Boolean indicatiePersoonAangetroffenOpAdres;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    /**
     * JPA default constructor.
     */
    protected PersoonAdres() {}

    /**
     * Maak een nieuwe persoon adres.
     *
     * @param persoon persoon
     */
    public PersoonAdres(final Persoon persoon) {
        setPersoon(persoon);
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
     * Zet de waarden voor id van PersoonAdres.
     *
     * @param id de nieuwe waarde voor id van PersoonAdres
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van identificatiecode adresseerbaar object van PersoonAdres.
     *
     * @return de waarde van identificatiecode adresseerbaar object van PersoonAdres
     */
    public String getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * Zet de waarden voor identificatiecode adresseerbaar object van PersoonAdres.
     *
     * @param adresseerbaarObject de nieuwe waarde voor identificatiecode adresseerbaar object van
     *        PersoonAdres
     */
    public void setIdentificatiecodeAdresseerbaarObject(final String adresseerbaarObject) {
        identificatiecodeAdresseerbaarObject = adresseerbaarObject;
    }

    /**
     * Geef de waarde van afgekorte naam openbare ruimte van PersoonAdres.
     *
     * @return de waarde van afgekorte naam openbare ruimte van PersoonAdres
     */
    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * Zet de waarden voor afgekorte naam openbare ruimte van PersoonAdres.
     *
     * @param afgekorteNaamOpenbareRuimte de nieuwe waarde voor afgekorte naam openbare ruimte van
     *        PersoonAdres
     */
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        ValidationUtils.controleerOpLegeWaarden("afgekorteNaamOpenbareRuimte mag geen lege string zijn", afgekorteNaamOpenbareRuimte);
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 van PersoonAdres.
     *
     * @return de waarde van buitenlands adres regel1 van PersoonAdres
     */
    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Zet de waarden voor buitenlands adres regel1 van PersoonAdres.
     *
     * @param buitenlandsAdresRegel1 de nieuwe waarde voor buitenlands adres regel1 van PersoonAdres
     */
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel1 mag geen lege string zijn", buitenlandsAdresRegel1);
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 van PersoonAdres.
     *
     * @return de waarde van buitenlands adres regel2 van PersoonAdres
     */
    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Zet de waarden voor buitenlands adres regel2 van PersoonAdres.
     *
     * @param buitenlandsAdresRegel2 de nieuwe waarde voor buitenlands adres regel2 van PersoonAdres
     */
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel2 mag geen lege string zijn", buitenlandsAdresRegel2);
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 van PersoonAdres.
     *
     * @return de waarde van buitenlands adres regel3 van PersoonAdres
     */
    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Zet de waarden voor buitenlands adres regel3 van PersoonAdres.
     *
     * @param buitenlandsAdresRegel3 de nieuwe waarde voor buitenlands adres regel3 van PersoonAdres
     */
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel3 mag geen lege string zijn", buitenlandsAdresRegel3);
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 van PersoonAdres.
     *
     * @return de waarde van buitenlands adres regel4 van PersoonAdres
     */
    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Zet de waarden voor buitenlands adres regel4 van PersoonAdres.
     *
     * @param buitenlandsAdresRegel4 de nieuwe waarde voor buitenlands adres regel4 van PersoonAdres
     */
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel4 mag geen lege string zijn", buitenlandsAdresRegel4);
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 van PersoonAdres.
     *
     * @return de waarde van buitenlands adres regel5 van PersoonAdres
     */
    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Zet de waarden voor buitenlands adres regel5 van PersoonAdres.
     *
     * @param buitenlandsAdresRegel5 de nieuwe waarde voor buitenlands adres regel5 van PersoonAdres
     */
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel5 mag geen lege string zijn", buitenlandsAdresRegel5);
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 van PersoonAdres.
     *
     * @return de waarde van buitenlands adres regel6 van PersoonAdres
     */
    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Zet de waarden voor buitenlands adres regel6 van PersoonAdres.
     *
     * @param buitenlandsAdresRegel6 de nieuwe waarde voor buitenlands adres regel6 van PersoonAdres
     */
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel6 mag geen lege string zijn", buitenlandsAdresRegel6);
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    /**
     * Geef de waarde van datum aanvang adreshouding van PersoonAdres.
     *
     * @return de waarde van datum aanvang adreshouding van PersoonAdres
     */
    public Integer getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * Zet de waarden voor datum aanvang adreshouding van PersoonAdres.
     *
     * @param datumAanvangAdreshouding de nieuwe waarde voor datum aanvang adreshouding van
     *        PersoonAdres
     */
    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    /**
     * Geef de waarde van gemeentedeel van PersoonAdres.
     *
     * @return de waarde van gemeentedeel van PersoonAdres
     */
    public String getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * Zet de waarden voor gemeentedeel van PersoonAdres.
     *
     * @param gemeentedeel de nieuwe waarde voor gemeentedeel van PersoonAdres
     */
    public void setGemeentedeel(final String gemeentedeel) {
        ValidationUtils.controleerOpLegeWaarden("gemeentedeel mag geen lege string zijn", gemeentedeel);
        this.gemeentedeel = gemeentedeel;
    }

    /**
     * Geef de waarde van huisletter van PersoonAdres.
     *
     * @return de waarde van huisletter van PersoonAdres
     */
    public Character getHuisletter() {
        return huisletter;
    }

    /**
     * Zet de waarden voor huisletter van PersoonAdres.
     *
     * @param huisletter de nieuwe waarde voor huisletter van PersoonAdres
     */
    public void setHuisletter(final Character huisletter) {
        this.huisletter = huisletter;
    }

    /**
     * Geef de waarde van huisnummer van PersoonAdres.
     *
     * @return de waarde van huisnummer van PersoonAdres
     */
    public Integer getHuisnummer() {
        return huisnummer;
    }

    /**
     * Zet de waarden voor huisnummer van PersoonAdres.
     *
     * @param huisnummer de nieuwe waarde voor huisnummer van PersoonAdres
     */
    public void setHuisnummer(final Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Geef de waarde van huisnummertoevoeging van PersoonAdres.
     *
     * @return de waarde van huisnummertoevoeging van PersoonAdres
     */
    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Zet de waarden voor huisnummertoevoeging van PersoonAdres.
     *
     * @param huisnummertoevoeging de nieuwe waarde voor huisnummertoevoeging van PersoonAdres
     */
    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        ValidationUtils.controleerOpLegeWaarden("huisnummertoevoeging mag geen lege string zijn", huisnummertoevoeging);
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    /**
     * Geef de waarde van identificatiecode nummeraanduiding van PersoonAdres.
     *
     * @return de waarde van identificatiecode nummeraanduiding van PersoonAdres
     */
    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Zet de waarden voor identificatiecode nummeraanduiding van PersoonAdres.
     *
     * @param identificatiecodeNummeraanduiding de nieuwe waarde voor identificatiecode
     *        nummeraanduiding van PersoonAdres
     */
    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        ValidationUtils.controleerOpLegeWaarden("identificatiecodeNummeraanduiding mag geen lege string zijn", identificatiecodeNummeraanduiding);
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    /**
     * Geef de waarde van locatie omschrijving van PersoonAdres.
     *
     * @return de waarde van locatie omschrijving van PersoonAdres
     */
    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * Zet de waarden voor locatie omschrijving van PersoonAdres.
     *
     * @param locatieOmschrijving de nieuwe waarde voor locatie omschrijving van PersoonAdres
     */
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        ValidationUtils.controleerOpLegeWaarden("locatieOmschrijving mag geen lege string zijn", locatieOmschrijving);
        this.locatieOmschrijving = locatieOmschrijving;
    }

    /**
     * Geef de waarde van locatietov adres van PersoonAdres.
     *
     * @return de waarde van locatietov adres van PersoonAdres
     */
    public String getLocatietovAdres() {
        return locatietovAdres;
    }

    /**
     * Zet de waarden voor locatietov adres van PersoonAdres.
     *
     * @param locatietovAdres de nieuwe waarde voor locatietov adres van PersoonAdres
     */
    public void setLocatietovAdres(final String locatietovAdres) {
        ValidationUtils.controleerOpLegeWaarden("locatietovAdres mag geen lege string zijn", locatietovAdres);
        this.locatietovAdres = locatietovAdres;
    }

    /**
     * Geef de waarde van naam openbare ruimte van PersoonAdres.
     *
     * @return de waarde van naam openbare ruimte van PersoonAdres
     */
    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * Zet de waarden voor naam openbare ruimte van PersoonAdres.
     *
     * @param naamOpenbareRuimte de nieuwe waarde voor naam openbare ruimte van PersoonAdres
     */
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        ValidationUtils.controleerOpLegeWaarden("naamOpenbareRuimte mag geen lege string zijn", naamOpenbareRuimte);
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    /**
     * Geef de waarde van postcode van PersoonAdres.
     *
     * @return de waarde van postcode van PersoonAdres
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Zet de waarden voor postcode van PersoonAdres.
     *
     * @param postcode de nieuwe waarde voor postcode van PersoonAdres
     */
    public void setPostcode(final String postcode) {
        ValidationUtils.controleerOpLegeWaarden("postcode mag geen lege string zijn", postcode);
        this.postcode = postcode;
    }

    /**
     * Geef de waarde van persoon adres historie set van PersoonAdres.
     *
     * @return de waarde van persoon adres historie set van PersoonAdres
     */
    public Set<PersoonAdresHistorie> getPersoonAdresHistorieSet() {
        return persoonAdresHistorieSet;
    }

    /**
     * Toevoegen van een persoon adres historie.
     *
     * @param persoonAdresHistorie persoon adres historie
     */
    public void addPersoonAdresHistorie(final PersoonAdresHistorie persoonAdresHistorie) {
        persoonAdresHistorie.setPersoonAdres(this);
        persoonAdresHistorieSet.add(persoonAdresHistorie);
    }

    /**
     * Geef de waarde van aangever adreshouding van PersoonAdres.
     *
     * @return de waarde van aangever adreshouding van PersoonAdres
     */
    public Aangever getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * Zet de waarden voor aangever adreshouding van PersoonAdres.
     *
     * @param aangeverAdreshouding de nieuwe waarde voor aangever adreshouding van PersoonAdres
     */
    public void setAangeverAdreshouding(final Aangever aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    /**
     * Geef de waarde van soort adres van PersoonAdres.
     *
     * @return de waarde van soort adres van PersoonAdres
     */
    public SoortAdres getSoortAdres() {
        return SoortAdres.parseId(soortAdresId);
    }

    /**
     * Zet de waarden voor soort adres van PersoonAdres.
     *
     * @param soortAdres de nieuwe waarde voor soort adres van PersoonAdres
     */
    public void setSoortAdres(final SoortAdres soortAdres) {
        if (soortAdres == null) {
            soortAdresId = null;
        } else {
            soortAdresId = soortAdres.getId();
        }
    }

    /**
     * Geef de waarde van land of gebied van PersoonAdres.
     *
     * @return de waarde van land of gebied van PersoonAdres
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebied;
    }

    /**
     * Zet de waarden voor land of gebied van PersoonAdres.
     *
     * @param landOfGebied de nieuwe waarde voor land of gebied van PersoonAdres
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        this.landOfGebied = landOfGebied;
    }

    /**
     * Geef de waarde van gemeente van PersoonAdres.
     *
     * @return de waarde van gemeente van PersoonAdres
     */
    public Gemeente getGemeente() {
        return gemeente;
    }

    /**
     * Zet de waarden voor gemeente van PersoonAdres.
     *
     * @param gemeente de nieuwe waarde voor gemeente van PersoonAdres
     */
    public void setGemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
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
     * Zet de waarden voor persoon van PersoonAdres.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonAdres
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van woonplaatsnaam van PersoonAdres.
     *
     * @return de waarde van woonplaatsnaam van PersoonAdres
     */
    public String getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * Zet de waarden voor woonplaatsnaam van PersoonAdres.
     *
     * @param woonplaatsnaam de nieuwe waarde voor woonplaatsnaam van PersoonAdres
     */
    public void setWoonplaatsnaam(final String woonplaatsnaam) {
        this.woonplaatsnaam = woonplaatsnaam;
    }

    /**
     * Geef de waarde van reden wijziging van PersoonAdres.
     *
     * @return de waarde van reden wijziging van PersoonAdres
     */
    public RedenWijzigingVerblijf getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * Zet de waarden voor reden wijziging van PersoonAdres.
     *
     * @param redenWijziging de nieuwe waarde voor reden wijziging van PersoonAdres
     */
    public void setRedenWijziging(final RedenWijzigingVerblijf redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    /**
     * Geef de waarde van indicatie persoon aangetroffen op adres van PersoonAdres.
     *
     * @return de waarde van indicatie persoon aangetroffen op adres van PersoonAdres
     */
    public Boolean getIndicatiePersoonAangetroffenOpAdres() {
        return indicatiePersoonAangetroffenOpAdres;
    }

    /**
     * Zet de waarden voor indicatie persoon aangetroffen op adres van PersoonAdres.
     *
     * @param indicatiePersoonNietAangetroffenOpAdres de nieuwe waarde voor indicatie persoon
     *        aangetroffen op adres van PersoonAdres
     */
    public void setIndicatiePersoonAangetroffenOpAdres(final Boolean indicatiePersoonNietAangetroffenOpAdres) {
        indicatiePersoonAangetroffenOpAdres = indicatiePersoonNietAangetroffenOpAdres;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("persoonAdresHistorieSet", Collections.unmodifiableSet(persoonAdresHistorieSet));
        return result;
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
}
