/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persadres database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persadres", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persadres", "tsreg", "dataanvgel"}))
public class PersoonAdresHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persadres_id_generator", sequenceName = "kern.seq_his_persadres", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persadres_id_generator")
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

    @Column(name = "huisletter", length = 1)
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

    @Column(name = "postcode", length = 6)
    private String postcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aangadresh")
    private Aangever aangeverAdreshouding;

    @Column(name = "srt", nullable = false)
    private int soortAdresId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebied", nullable = false)
    private LandOfGebied landOfGebied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gem")
    private Gemeente gemeente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persadres", nullable = false)
    private PersoonAdres persoonAdres;

    @Column(name = "wplnaam", length = 80)
    private String woonplaatsnaam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnwijz", nullable = false)
    private RedenWijzigingVerblijf redenWijziging;

    @Column(name = "indpersaangetroffenopadres")
    private Boolean indicatiePersoonAangetroffenOpAdres;

    /**
     * JPA default constructor.
     */
    protected PersoonAdresHistorie() {}

    /**
     * Maak een nieuwe persoon adres historie.
     *
     * @param persoonAdres persoon adres
     * @param soortAdres soort adres
     * @param landOfGebied land of gebied
     * @param redenWijzging reden wijziging verblijf
     */
    public PersoonAdresHistorie(final PersoonAdres persoonAdres, final SoortAdres soortAdres, final LandOfGebied landOfGebied,
            final RedenWijzigingVerblijf redenWijzging) {
        setPersoonAdres(persoonAdres);
        setSoortAdres(soortAdres);
        setLandOfGebied(landOfGebied);
        setRedenWijziging(redenWijzging);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonAdresHistorie(final PersoonAdresHistorie ander) {
        super(ander);
        identificatiecodeAdresseerbaarObject = ander.getIdentificatiecodeAdresseerbaarObject();
        afgekorteNaamOpenbareRuimte = ander.getAfgekorteNaamOpenbareRuimte();
        buitenlandsAdresRegel1 = ander.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2 = ander.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3 = ander.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4 = ander.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5 = ander.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6 = ander.getBuitenlandsAdresRegel6();
        datumAanvangAdreshouding = ander.getDatumAanvangAdreshouding();
        gemeentedeel = ander.getGemeentedeel();
        huisletter = ander.getHuisletter();
        huisnummer = ander.getHuisnummer();
        huisnummertoevoeging = ander.getHuisnummertoevoeging();
        identificatiecodeNummeraanduiding = ander.getIdentificatiecodeNummeraanduiding();
        locatieOmschrijving = ander.getLocatieOmschrijving();
        locatietovAdres = ander.getLocatietovAdres();
        naamOpenbareRuimte = ander.getNaamOpenbareRuimte();
        postcode = ander.getPostcode();
        aangeverAdreshouding = ander.getAangeverAdreshouding();
        soortAdresId = ander.getSoortAdres().getId();
        landOfGebied = ander.getLandOfGebied();
        gemeente = ander.getGemeente();
        persoonAdres = ander.getPersoonAdres();
        woonplaatsnaam = ander.getWoonplaatsnaam();
        redenWijziging = ander.getRedenWijziging();
        indicatiePersoonAangetroffenOpAdres = ander.getIndicatiePersoonAangetroffenOpAdres();
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
     * Zet de waarden voor id van PersoonAdresHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonAdresHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van identificatiecode adresseerbaar object van PersoonAdresHistorie.
     *
     * @return de waarde van identificatiecode adresseerbaar object van PersoonAdresHistorie
     */
    public String getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * Zet de waarden voor identificatiecode adresseerbaar object van PersoonAdresHistorie.
     *
     * @param adresseerbaarObject de nieuwe waarde voor identificatiecode adresseerbaar object van
     *        PersoonAdresHistorie
     */
    public void setIdentificatiecodeAdresseerbaarObject(final String adresseerbaarObject) {
        identificatiecodeAdresseerbaarObject = adresseerbaarObject;
    }

    /**
     * Geef de waarde van afgekorte naam openbare ruimte van PersoonAdresHistorie.
     *
     * @return de waarde van afgekorte naam openbare ruimte van PersoonAdresHistorie
     */
    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * Zet de waarden voor afgekorte naam openbare ruimte van PersoonAdresHistorie.
     *
     * @param afgekorteNaamOpenbareRuimte de nieuwe waarde voor afgekorte naam openbare ruimte van
     *        PersoonAdresHistorie
     */
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        ValidationUtils.controleerOpLegeWaarden("afgekorteNaamOpenbareRuimte mag geen lege string zijn", afgekorteNaamOpenbareRuimte);
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 van PersoonAdresHistorie.
     *
     * @return de waarde van buitenlands adres regel1 van PersoonAdresHistorie
     */
    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Zet de waarden voor buitenlands adres regel1 van PersoonAdresHistorie.
     *
     * @param buitenlandsAdresRegel1 de nieuwe waarde voor buitenlands adres regel1 van
     *        PersoonAdresHistorie
     */
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel1 mag geen lege string zijn", buitenlandsAdresRegel1);
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 van PersoonAdresHistorie.
     *
     * @return de waarde van buitenlands adres regel2 van PersoonAdresHistorie
     */
    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Zet de waarden voor buitenlands adres regel2 van PersoonAdresHistorie.
     *
     * @param buitenlandsAdresRegel2 de nieuwe waarde voor buitenlands adres regel2 van
     *        PersoonAdresHistorie
     */
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel2 mag geen lege string zijn", buitenlandsAdresRegel2);
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 van PersoonAdresHistorie.
     *
     * @return de waarde van buitenlands adres regel3 van PersoonAdresHistorie
     */
    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Zet de waarden voor buitenlands adres regel3 van PersoonAdresHistorie.
     *
     * @param buitenlandsAdresRegel3 de nieuwe waarde voor buitenlands adres regel3 van
     *        PersoonAdresHistorie
     */
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel3 mag geen lege string zijn", buitenlandsAdresRegel3);
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 van PersoonAdresHistorie.
     *
     * @return de waarde van buitenlands adres regel4 van PersoonAdresHistorie
     */
    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Zet de waarden voor buitenlands adres regel4 van PersoonAdresHistorie.
     *
     * @param buitenlandsAdresRegel4 de nieuwe waarde voor buitenlands adres regel4 van
     *        PersoonAdresHistorie
     */
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel4 mag geen lege string zijn", buitenlandsAdresRegel4);
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 van PersoonAdresHistorie.
     *
     * @return de waarde van buitenlands adres regel5 van PersoonAdresHistorie
     */
    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Zet de waarden voor buitenlands adres regel5 van PersoonAdresHistorie.
     *
     * @param buitenlandsAdresRegel5 de nieuwe waarde voor buitenlands adres regel5 van
     *        PersoonAdresHistorie
     */
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel5 mag geen lege string zijn", buitenlandsAdresRegel5);
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 van PersoonAdresHistorie.
     *
     * @return de waarde van buitenlands adres regel6 van PersoonAdresHistorie
     */
    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Zet de waarden voor buitenlands adres regel6 van PersoonAdresHistorie.
     *
     * @param buitenlandsAdresRegel6 de nieuwe waarde voor buitenlands adres regel6 van
     *        PersoonAdresHistorie
     */
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel6 mag geen lege string zijn", buitenlandsAdresRegel6);
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    /**
     * Geef de waarde van datum aanvang adreshouding van PersoonAdresHistorie.
     *
     * @return de waarde van datum aanvang adreshouding van PersoonAdresHistorie
     */
    public Integer getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * Zet de waarden voor datum aanvang adreshouding van PersoonAdresHistorie.
     *
     * @param datumAanvangAdreshouding de nieuwe waarde voor datum aanvang adreshouding van
     *        PersoonAdresHistorie
     */
    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    /**
     * Geef de waarde van gemeentedeel van PersoonAdresHistorie.
     *
     * @return de waarde van gemeentedeel van PersoonAdresHistorie
     */
    public String getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * Zet de waarden voor gemeentedeel van PersoonAdresHistorie.
     *
     * @param gemeentedeel de nieuwe waarde voor gemeentedeel van PersoonAdresHistorie
     */
    public void setGemeentedeel(final String gemeentedeel) {
        ValidationUtils.controleerOpLegeWaarden("gemeentedeel mag geen lege string zijn", gemeentedeel);
        this.gemeentedeel = gemeentedeel;
    }

    /**
     * Geef de waarde van huisletter van PersoonAdresHistorie.
     *
     * @return de waarde van huisletter van PersoonAdresHistorie
     */
    public Character getHuisletter() {
        return huisletter;
    }

    /**
     * Zet de waarden voor huisletter van PersoonAdresHistorie.
     *
     * @param huisletter de nieuwe waarde voor huisletter van PersoonAdresHistorie
     */
    public void setHuisletter(final Character huisletter) {
        this.huisletter = huisletter;
    }

    /**
     * Geef de waarde van huisnummer van PersoonAdresHistorie.
     *
     * @return de waarde van huisnummer van PersoonAdresHistorie
     */
    public Integer getHuisnummer() {
        return huisnummer;
    }

    /**
     * Zet de waarden voor huisnummer van PersoonAdresHistorie.
     *
     * @param huisnummer de nieuwe waarde voor huisnummer van PersoonAdresHistorie
     */
    public void setHuisnummer(final Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Geef de waarde van huisnummertoevoeging van PersoonAdresHistorie.
     *
     * @return de waarde van huisnummertoevoeging van PersoonAdresHistorie
     */
    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Zet de waarden voor huisnummertoevoeging van PersoonAdresHistorie.
     *
     * @param huisnummertoevoeging de nieuwe waarde voor huisnummertoevoeging van
     *        PersoonAdresHistorie
     */
    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        ValidationUtils.controleerOpLegeWaarden("huisnummertoevoeging mag geen lege string zijn", huisnummertoevoeging);
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    /**
     * Geef de waarde van identificatiecode nummeraanduiding van PersoonAdresHistorie.
     *
     * @return de waarde van identificatiecode nummeraanduiding van PersoonAdresHistorie
     */
    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Zet de waarden voor identificatiecode nummeraanduiding van PersoonAdresHistorie.
     *
     * @param identificatiecodeNummeraanduiding de nieuwe waarde voor identificatiecode
     *        nummeraanduiding van PersoonAdresHistorie
     */
    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        ValidationUtils.controleerOpLegeWaarden("identificatiecodeNummeraanduiding mag geen lege string zijn", identificatiecodeNummeraanduiding);
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    /**
     * Geef de waarde van locatie omschrijving van PersoonAdresHistorie.
     *
     * @return de waarde van locatie omschrijving van PersoonAdresHistorie
     */
    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * Zet de waarden voor locatie omschrijving van PersoonAdresHistorie.
     *
     * @param locatieOmschrijving de nieuwe waarde voor locatie omschrijving van
     *        PersoonAdresHistorie
     */
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        ValidationUtils.controleerOpLegeWaarden("locatieOmschrijving mag geen lege string zijn", locatieOmschrijving);
        this.locatieOmschrijving = locatieOmschrijving;
    }

    /**
     * Geef de waarde van locatietov adres van PersoonAdresHistorie.
     *
     * @return de waarde van locatietov adres van PersoonAdresHistorie
     */
    public String getLocatietovAdres() {
        return locatietovAdres;
    }

    /**
     * Zet de waarden voor locatietov adres van PersoonAdresHistorie.
     *
     * @param locatietovAdres de nieuwe waarde voor locatietov adres van PersoonAdresHistorie
     */
    public void setLocatietovAdres(final String locatietovAdres) {
        ValidationUtils.controleerOpLegeWaarden("locatietovAdres mag geen lege string zijn", locatietovAdres);
        this.locatietovAdres = locatietovAdres;
    }

    /**
     * Geef de waarde van naam openbare ruimte van PersoonAdresHistorie.
     *
     * @return de waarde van naam openbare ruimte van PersoonAdresHistorie
     */
    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * Zet de waarden voor naam openbare ruimte van PersoonAdresHistorie.
     *
     * @param naamOpenbareRuimte de nieuwe waarde voor naam openbare ruimte van PersoonAdresHistorie
     */
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        ValidationUtils.controleerOpLegeWaarden("naamOpenbareRuimte mag geen lege string zijn", naamOpenbareRuimte);
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    /**
     * Geef de waarde van postcode van PersoonAdresHistorie.
     *
     * @return de waarde van postcode van PersoonAdresHistorie
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Zet de waarden voor postcode van PersoonAdresHistorie.
     *
     * @param postcode de nieuwe waarde voor postcode van PersoonAdresHistorie
     */
    public void setPostcode(final String postcode) {
        ValidationUtils.controleerOpLegeWaarden("postcode mag geen lege string zijn", postcode);
        this.postcode = postcode;
    }

    /**
     * Geef de waarde van aangever adreshouding van PersoonAdresHistorie.
     *
     * @return de waarde van aangever adreshouding van PersoonAdresHistorie
     */
    public Aangever getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * Zet de waarden voor aangever adreshouding van PersoonAdresHistorie.
     *
     * @param aangeverAdreshouding de nieuwe waarde voor aangever adreshouding van
     *        PersoonAdresHistorie
     */
    public void setAangeverAdreshouding(final Aangever aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    /**
     * Geef de waarde van soort adres van PersoonAdresHistorie.
     *
     * @return de waarde van soort adres van PersoonAdresHistorie
     */
    public SoortAdres getSoortAdres() {
        return SoortAdres.parseId(soortAdresId);
    }

    /**
     * Zet de waarden voor soort adres van PersoonAdresHistorie.
     *
     * @param soortAdres de nieuwe waarde voor soort adres van PersoonAdresHistorie
     */
    public void setSoortAdres(final SoortAdres soortAdres) {
        ValidationUtils.controleerOpNullWaarden("soortAdres mag niet null zijn", soortAdres);
        soortAdresId = soortAdres.getId();
    }

    /**
     * Geef de waarde van land of gebied van PersoonAdresHistorie.
     *
     * @return de waarde van land of gebied van PersoonAdresHistorie
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebied;
    }

    /**
     * Zet de waarden voor land of gebied van PersoonAdresHistorie.
     *
     * @param landOfGebied de nieuwe waarde voor land of gebied van PersoonAdresHistorie
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        ValidationUtils.controleerOpNullWaarden("landOfGebied mag niet null zijn", landOfGebied);
        this.landOfGebied = landOfGebied;
    }

    /**
     * Geef de waarde van gemeente van PersoonAdresHistorie.
     *
     * @return de waarde van gemeente van PersoonAdresHistorie
     */
    public Gemeente getGemeente() {
        return gemeente;
    }

    /**
     * Zet de waarden voor gemeente van PersoonAdresHistorie.
     *
     * @param gemeente de nieuwe waarde voor gemeente van PersoonAdresHistorie
     */
    public void setGemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
    }

    /**
     * Geef de waarde van persoon adres van PersoonAdresHistorie.
     *
     * @return de waarde van persoon adres van PersoonAdresHistorie
     */
    public PersoonAdres getPersoonAdres() {
        return persoonAdres;
    }

    /**
     * Zet de waarden voor persoon adres van PersoonAdresHistorie.
     *
     * @param persoonAdres de nieuwe waarde voor persoon adres van PersoonAdresHistorie
     */
    public void setPersoonAdres(final PersoonAdres persoonAdres) {
        ValidationUtils.controleerOpNullWaarden("persoonAdres mag niet null zijn", persoonAdres);
        this.persoonAdres = persoonAdres;
    }

    /**
     * Geef de waarde van woonplaatsnaam van PersoonAdresHistorie.
     *
     * @return de waarde van woonplaatsnaam van PersoonAdresHistorie
     */
    public String getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * Zet de waarden voor woonplaatsnaam van PersoonAdresHistorie.
     *
     * @param woonplaatsnaam de nieuwe waarde voor woonplaatsnaam van PersoonAdresHistorie
     */
    public void setWoonplaatsnaam(final String woonplaatsnaam) {
        ValidationUtils.controleerOpLegeWaarden("woonplaatsnaam mag geen lege string zijn", woonplaatsnaam);
        this.woonplaatsnaam = woonplaatsnaam;
    }

    /**
     * Geef de waarde van reden wijziging van PersoonAdresHistorie.
     *
     * @return de waarde van reden wijziging van PersoonAdresHistorie
     */
    public RedenWijzigingVerblijf getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * Zet de waarden voor reden wijziging van PersoonAdresHistorie.
     *
     * @param redenWijziging de nieuwe waarde voor reden wijziging van PersoonAdresHistorie
     */
    public void setRedenWijziging(final RedenWijzigingVerblijf redenWijziging) {
        ValidationUtils.controleerOpNullWaarden("redenWijziging mag niet null zijn", redenWijziging);
        this.redenWijziging = redenWijziging;
    }

    /**
     * Geef de waarde van indicatie persoon aangetroffen op adres van PersoonAdresHistorie.
     *
     * @return de waarde van indicatie persoon aangetroffen op adres van PersoonAdresHistorie
     */
    public Boolean getIndicatiePersoonAangetroffenOpAdres() {
        return indicatiePersoonAangetroffenOpAdres;
    }

    /**
     * Zet de waarden voor indicatie persoon aangetroffen op adres van PersoonAdresHistorie.
     *
     * @param indicatiePersoonNietAangetroffenOpAdres de nieuwe waarde voor indicatie persoon
     *        aangetroffen op adres van PersoonAdresHistorie
     */
    public void setIndicatiePersoonAangetroffenOpAdres(final Boolean indicatiePersoonNietAangetroffenOpAdres) {
        indicatiePersoonAangetroffenOpAdres = indicatiePersoonNietAangetroffenOpAdres;
    }

    @Override
    public final PersoonAdresHistorie kopieer() {
        return new PersoonAdresHistorie(this);
    }

    @Override
    public Persoon getPersoon() {
        return getPersoonAdres().getPersoon();
    }
}
