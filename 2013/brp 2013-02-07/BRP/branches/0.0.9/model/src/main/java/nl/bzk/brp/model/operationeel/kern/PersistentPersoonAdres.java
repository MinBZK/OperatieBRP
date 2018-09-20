/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.validatie.constraint.Datum;
//import nl.bzk.brp.model.validatie.constraint.Postcode;

/**
 * Een actueel adres in het operationeel model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "PersAdres", schema = "Kern")
public class PersistentPersoonAdres {

    @Id
    @SequenceGenerator(name = "PERSOONADRES", sequenceName = "Kern.seq_PersAdres")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOONADRES")
    private Long id;

    @ManyToOne(targetEntity = PersistentPersoon.class)
    @JoinColumn(name = "Pers")
    private PersistentPersoon persoon;

    @Column(name = "Srt")
    private FunctieAdres soort;

    @Column(name = "DatAanvAdresh")
    @Datum
    private Integer datumAanvangAdreshouding;

    private String adresseerbaarObject;

    @Column(name = "IdentcodeNraand")
    private String identificatiecodeNummeraanduiding;

    @ManyToOne(targetEntity = Partij.class)
    @JoinColumn(name = "Gem")
    private Partij gemeente;

    @Column(name = "NOR")
    private String naamOpenbareRuimte;

    @Column(name = "AfgekorteNOR")
    private String afgekorteNaamOpenbareRuimte;

    @Column(name = "Gemdeel")
    private String gemeentedeel;

    @Column(name = "Huisnr")
    private String huisnummer;

    private String huisletter;

    @Column(name = "Huisnrtoevoeging")
    private String huisnummertoevoeging;

	//@Postcode, voorlopig de validatie uitgezet, omdat we nog geen melding hebben.
    private String postcode;

    @ManyToOne(targetEntity = Plaats.class)
    @JoinColumn(name = "Wpl")
    private Plaats woonplaats;

    @Column(name = "LoctovAdres")
    private String locatietovAdres;

    @Column(name = "LocOms")
    private String locatieOmschrijving;

    @Column(name = "BLAdresRegel1")
    private String buitenlandsAdresRegel1;

    @Column(name = "BLAdresRegel2")
    private String buitenlandsAdresRegel2;

    @Column(name = "BLAdresRegel3")
    private String buitenlandsAdresRegel3;

    @Column(name = "BLAdresRegel4")
    private String buitenlandsAdresRegel4;

    @Column(name = "BLAdresRegel5")
    private String buitenlandsAdresRegel5;

    @Column(name = "BLAdresRegel6")
    private String buitenlandsAdresRegel6;

    @ManyToOne(targetEntity = Land.class)
    @JoinColumn(name = "Land")
    private Land land;

    @Column(name = "DatVertrekUitNederland")
    @Datum
    private Integer datumVertrekUitNederland;

    @Column(name = "PersAdresStatusHis")
    private String persoonAdresStatusHis = "A";

    /**
     * No-args constructor, vereist voor JPA.
     */
    public PersistentPersoonAdres() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public FunctieAdres getSoort() {
        return soort;
    }

    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    public Integer getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    public String getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        this.adresseerbaarObject = adresseerbaarObject;
    }

    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    public Partij getGemeente() {
        return gemeente;
    }

    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    public String getGemeentedeel() {
        return gemeentedeel;
    }

    public void setGemeentedeel(final String gemeentedeel) {
        this.gemeentedeel = gemeentedeel;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(final String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisletter() {
        return huisletter;
    }

    public void setHuisletter(final String huisletter) {
        this.huisletter = huisletter;
    }

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    public Plaats getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(final Plaats woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getLocatietovAdres() {
        return locatietovAdres;
    }

    public void setLocatietovAdres(final String locatietovAdres) {
        this.locatietovAdres = locatietovAdres;
    }

    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        this.locatieOmschrijving = locatieOmschrijving;
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

    public Land getLand() {
        return land;
    }

    public void setLand(final Land land) {
        this.land = land;
    }

    public Integer getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    public void setDatumVertrekUitNederland(final Integer datumVertrekUitNederland) {
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    public String getPersoonAdresStatusHis() {
        return persoonAdresStatusHis;
    }
}
