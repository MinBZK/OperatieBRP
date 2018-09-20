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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the persadres database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persadres", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonAdres extends AbstractDeltaEntiteit implements DeltaSubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persadres_id_generator", sequenceName = "kern.seq_persadres", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persadres_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonAdres", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonAdresHistorie> persoonAdresHistorieSet = new LinkedHashSet<>(0);

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "aangadresh")
    private Aangever aangeverAdreshouding;

    @Column(name = "srt")
    private Short soortAdresId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebied")
    private LandOfGebied landOfGebied;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gem")
    private Gemeente gemeente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "wplnaam")
    private String woonplaatsnaam;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnwijz")
    private RedenWijzigingVerblijf redenWijziging;

    @Column(name = "indpersaangetroffenopadres")
    private Boolean indicatiePersoonAangetroffenOpAdres;

    /**
     * JPA default constructor.
     */
    protected PersoonAdres() {
    }

    /**
     * Maak een nieuwe persoon adres.
     *
     * @param persoon
     *            persoon
     */
    public PersoonAdres(final Persoon persoon) {
        setPersoon(persoon);
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
     * Geef de waarde van identificatiecode adresseerbaar object.
     *
     * @return identificatiecode adresseerbaar object
     */
    public String getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * Zet de waarde van identificatiecode adresseerbaar object.
     *
     * @param adresseerbaarObject
     *            identificatiecode adresseerbaar object
     */
    public void setIdentificatiecodeAdresseerbaarObject(final String adresseerbaarObject) {
        this.identificatiecodeAdresseerbaarObject = adresseerbaarObject;
    }

    /**
     * Geef de waarde van afgekorte naam openbare ruimte.
     *
     * @return afgekorte naam openbare ruimte
     */
    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * Zet de waarde van afgekorte naam openbare ruimte.
     *
     * @param afgekorteNaamOpenbareRuimte
     *            afgekorte naam openbare ruimte
     */
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        Validatie.controleerOpLegeWaarden("afgekorteNaamOpenbareRuimte mag geen lege string zijn", afgekorteNaamOpenbareRuimte);
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    /**
     * Geef de waarde van buitenlands adres regel1.
     *
     * @return buitenlands adres regel1
     */
    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Zet de waarde van buitenlands adres regel1.
     *
     * @param buitenlandsAdresRegel1
     *            buitenlands adres regel1
     */
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel1 mag geen lege string zijn", buitenlandsAdresRegel1);
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2.
     *
     * @return buitenlands adres regel2
     */
    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Zet de waarde van buitenlands adres regel2.
     *
     * @param buitenlandsAdresRegel2
     *            buitenlands adres regel2
     */
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel2 mag geen lege string zijn", buitenlandsAdresRegel2);
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3.
     *
     * @return buitenlands adres regel3
     */
    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Zet de waarde van buitenlands adres regel3.
     *
     * @param buitenlandsAdresRegel3
     *            buitenlands adres regel3
     */
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel3 mag geen lege string zijn", buitenlandsAdresRegel3);
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4.
     *
     * @return buitenlands adres regel4
     */
    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Zet de waarde van buitenlands adres regel4.
     *
     * @param buitenlandsAdresRegel4
     *            buitenlands adres regel4
     */
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel4 mag geen lege string zijn", buitenlandsAdresRegel4);
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5.
     *
     * @return buitenlands adres regel5
     */
    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Zet de waarde van buitenlands adres regel5.
     *
     * @param buitenlandsAdresRegel5
     *            buitenlands adres regel5
     */
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel5 mag geen lege string zijn", buitenlandsAdresRegel5);
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6.
     *
     * @return buitenlands adres regel6
     */
    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Zet de waarde van buitenlands adres regel6.
     *
     * @param buitenlandsAdresRegel6
     *            buitenlands adres regel6
     */
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel6 mag geen lege string zijn", buitenlandsAdresRegel6);
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    /**
     * Geef de waarde van datum aanvang adreshouding.
     *
     * @return datum aanvang adreshouding
     */
    public Integer getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * Zet de waarde van datum aanvang adreshouding.
     *
     * @param datumAanvangAdreshouding
     *            datum aanvang adreshouding
     */
    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    /**
     * Geef de waarde van gemeentedeel.
     *
     * @return gemeentedeel
     */
    public String getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * Zet de waarde van gemeentedeel.
     *
     * @param gemeentedeel
     *            gemeentedeel
     */
    public void setGemeentedeel(final String gemeentedeel) {
        Validatie.controleerOpLegeWaarden("gemeentedeel mag geen lege string zijn", gemeentedeel);
        this.gemeentedeel = gemeentedeel;
    }

    /**
     * Geef de waarde van huisletter.
     *
     * @return huisletter
     */
    public Character getHuisletter() {
        return huisletter;
    }

    /**
     * Zet de waarde van huisletter.
     *
     * @param huisletter
     *            huisletter
     */
    public void setHuisletter(final Character huisletter) {
        this.huisletter = huisletter;
    }

    /**
     * Geef de waarde van huisnummer.
     *
     * @return huisnummer
     */
    public Integer getHuisnummer() {
        return huisnummer;
    }

    /**
     * Zet de waarde van huisnummer.
     *
     * @param huisnummer
     *            huisnummer
     */
    public void setHuisnummer(final Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Geef de waarde van huisnummertoevoeging.
     *
     * @return huisnummertoevoeging
     */
    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Zet de waarde van huisnummertoevoeging.
     *
     * @param huisnummertoevoeging
     *            huisnummertoevoeging
     */
    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        Validatie.controleerOpLegeWaarden("huisnummertoevoeging mag geen lege string zijn", huisnummertoevoeging);
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    /**
     * Geef de waarde van identificatiecode nummeraanduiding.
     *
     * @return identificatiecode nummeraanduiding
     */
    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Zet de waarde van identificatiecode nummeraanduiding.
     *
     * @param identificatiecodeNummeraanduiding
     *            identificatiecode nummeraanduiding
     */
    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        Validatie.controleerOpLegeWaarden("identificatiecodeNummeraanduiding mag geen lege string zijn", identificatiecodeNummeraanduiding);
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    /**
     * Geef de waarde van locatie omschrijving.
     *
     * @return locatie omschrijving
     */
    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * Zet de waarde van locatie omschrijving.
     *
     * @param locatieOmschrijving
     *            locatie omschrijving
     */
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        Validatie.controleerOpLegeWaarden("locatieOmschrijving mag geen lege string zijn", locatieOmschrijving);
        this.locatieOmschrijving = locatieOmschrijving;
    }

    /**
     * Geef de waarde van locatietov adres.
     *
     * @return locatietov adres
     */
    public String getLocatietovAdres() {
        return locatietovAdres;
    }

    /**
     * Zet de waarde van locatietov adres.
     *
     * @param locatietovAdres
     *            locatietov adres
     */
    public void setLocatietovAdres(final String locatietovAdres) {
        Validatie.controleerOpLegeWaarden("locatietovAdres mag geen lege string zijn", locatietovAdres);
        this.locatietovAdres = locatietovAdres;
    }

    /**
     * Geef de waarde van naam openbare ruimte.
     *
     * @return naam openbare ruimte
     */
    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * Zet de waarde van naam openbare ruimte.
     *
     * @param naamOpenbareRuimte
     *            naam openbare ruimte
     */
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        Validatie.controleerOpLegeWaarden("naamOpenbareRuimte mag geen lege string zijn", naamOpenbareRuimte);
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    /**
     * Geef de waarde van postcode.
     *
     * @return postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Zet de waarde van postcode.
     *
     * @param postcode
     *            postcode
     */
    public void setPostcode(final String postcode) {
        Validatie.controleerOpLegeWaarden("postcode mag geen lege string zijn", postcode);
        this.postcode = postcode;
    }

    /**
     * Geef de waarde van persoon adres historie set.
     *
     * @return persoon adres historie set
     */
    public Set<PersoonAdresHistorie> getPersoonAdresHistorieSet() {
        return persoonAdresHistorieSet;
    }

    /**
     * Toevoegen van een persoon adres historie.
     *
     * @param persoonAdresHistorie
     *            persoon adres historie
     */
    public void addPersoonAdresHistorie(final PersoonAdresHistorie persoonAdresHistorie) {
        persoonAdresHistorie.setPersoonAdres(this);
        persoonAdresHistorieSet.add(persoonAdresHistorie);
    }

    /**
     * Geef de waarde van aangever adreshouding.
     *
     * @return aangever adreshouding
     */
    public Aangever getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * Zet de waarde van aangever adreshouding.
     *
     * @param aangeverAdreshouding
     *            aangever adreshouding
     */
    public void setAangeverAdreshouding(final Aangever aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    /**
     * Geef de waarde van soort adres.
     *
     * @return soort adres
     */
    public FunctieAdres getSoortAdres() {
        return FunctieAdres.parseId(soortAdresId);
    }

    /**
     * Zet de waarde van soort adres.
     *
     * @param soortAdres
     *            soort adres
     */
    public void setSoortAdres(final FunctieAdres soortAdres) {
        if (soortAdres == null) {
            soortAdresId = null;
        } else {
            soortAdresId = soortAdres.getId();
        }
    }

    /**
     * Geef de waarde van land of gebied.
     *
     * @return land of gebied
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebied;
    }

    /**
     * Zet de waarde van land of gebied.
     *
     * @param landOfGebied
     *            land of gebied
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        this.landOfGebied = landOfGebied;
    }

    /**
     * Geef de waarde van gemeente.
     *
     * @return gemeente
     */
    public Gemeente getGemeente() {
        return gemeente;
    }

    /**
     * Zet de waarde van gemeente.
     *
     * @param gemeente
     *            gemeente
     */
    public void setGemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
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
     * Geef de waarde van woonplaatsnaam.
     *
     * @return woonplaatsnaam
     */
    public String getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * Zet de waarde van woonplaatsnaam.
     *
     * @param woonplaatsnaam
     *            woonplaatsnaam
     */
    public void setWoonplaatsnaam(final String woonplaatsnaam) {
        this.woonplaatsnaam = woonplaatsnaam;
    }

    /**
     * Geef de waarde van reden wijziging.
     *
     * @return reden wijziging
     */
    public RedenWijzigingVerblijf getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * Zet de waarde van reden wijziging.
     *
     * @param redenWijziging
     *            reden wijziging
     */
    public void setRedenWijziging(final RedenWijzigingVerblijf redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    /**
     * Geef de waarde van indicatie persoon aangetroffen op adres.
     *
     * @return indicatie persoon aangetroffen op adres
     */
    public Boolean getIndicatiePersoonAangetroffenOpAdres() {
        return indicatiePersoonAangetroffenOpAdres;
    }

    /**
     * Zet de waarde van indicatie persoon aangetroffen op adres.
     *
     * @param indicatiePersoonNietAangetroffenOpAdres
     *            indicatie persoon aangetroffen op adres
     */
    public void setIndicatiePersoonAangetroffenOpAdres(final Boolean indicatiePersoonNietAangetroffenOpAdres) {
        this.indicatiePersoonAangetroffenOpAdres = indicatiePersoonNietAangetroffenOpAdres;
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();
        result.put("persoonAdresHistorieSet", Collections.unmodifiableSet(persoonAdresHistorieSet));
        return result;
    }
}
