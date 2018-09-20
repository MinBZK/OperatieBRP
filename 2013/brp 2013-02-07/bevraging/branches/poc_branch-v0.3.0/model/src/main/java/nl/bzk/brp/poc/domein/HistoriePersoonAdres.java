/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.domein;

import java.util.Date;
import javax.persistence.*;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;

/**
 * Persoon adres entity uit de C/D laag.
 */
@Entity @Table(name = "His_PersAdres", schema = "Kern") @Access(AccessType.FIELD)
public class HistoriePersoonAdres implements Cloneable {

    @SequenceGenerator(name = "HIS_PERSOON_ADRES_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_his_PersAdres")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_PERSOON_ADRES_SEQUENCE_GENERATOR")
    private Long            id;
    @ManyToOne
    @JoinColumn(name = "persadres")
    private PocPersoonAdres persoonAdres;
    @Column(name = "dataanvgel")
    private Integer         datumAanvangGeldigheid;
    @Column(name = "dateindegel")
    private Integer         datumEindeGeldigheid;
    @Column(name = "tsreg")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date            tijdstipRegistratie;
    @Column(name = "tsverval")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date            tijdstipVervallen;
    @Column(name = "srt")
    private FunctieAdres    soort;
    @Column(name = "rdnwijz")
    private RedenWijziging  redenWijziging;
    @ManyToOne
    @JoinColumn(name = "gem")
    private Partij          gemeente;
    @Column(name = "NOR")
    private String          naamOpenbareRuimte;
    @Column(name = "AfgekorteNOR")
    private String          afgekorteNaamOpenbareRuimte;
    @Column(name = "Gemdeel")
    private String          gemeenteDeel;
    @Column(name = "huisnr")
    private Integer         huisNummer;
    @Column(name = "Huisletter")
    private String          huisletter;
    @Column(name = "Huisnrtoevoeging")
    private String          huisnummertoevoeging;
    @Column(name = "postcode")
    private String          postcode;
    @ManyToOne
    @JoinColumn(name = "Wpl")
    private Plaats          woonplaats;
    @Column(name = "LoctovAdres")
    private String          locatieTOVAdres;
    @ManyToOne
    @JoinColumn(name = "land")
    private Land            land;
    @Column(name = "dataanvadresh")
    private Integer         datumAanvangAdresHouding;


    /**
     * No-arg constructor voor JPA.
     */
    protected HistoriePersoonAdres() {
    }

    public Long getId() {
        return id;
    }

    /**
     * Default constructor met verplichte velden.
     *
     * @param persoonAdres het persoonadres; het adres in combinatie met de persoon.
     */
    public HistoriePersoonAdres(final PocPersoonAdres persoonAdres) {
        this.persoonAdres = persoonAdres;
        this.soort = persoonAdres.getSoort();

        this.gemeente = persoonAdres.getGemeente();
        this.gemeenteDeel = persoonAdres.getGemeenteDeel();
        this.naamOpenbareRuimte = persoonAdres.getNaamOpenbareRuimte();
        this.afgekorteNaamOpenbareRuimte = persoonAdres.getAfgekorteNaamOpenbareRuimte();
        this.huisNummer = persoonAdres.getHuisNummer();
        this.huisletter = persoonAdres.getHuisletter();
        this.huisnummertoevoeging = persoonAdres.getHuisnummertoevoeging();
        this.postcode = persoonAdres.getPostcode();
        this.woonplaats = persoonAdres.getWoonplaats();
        this.land = persoonAdres.getLand();
        this.locatieTOVAdres = persoonAdres.getLocatieTOVAdres();
        this.datumAanvangAdresHouding = persoonAdres.getDatumAanvangAdresHouding();
        this.redenWijziging = persoonAdres.getRedenWijziging();
    }

    public PocPersoonAdres getPersoonAdres() {
        return persoonAdres;
    }

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public Date getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public void setTijdstipRegistratie(final Date tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    public Date getTijdstipVervallen() {
        return tijdstipVervallen;
    }

    public void setTijdstipVervallen(final Date tijdstipVervallen) {
        this.tijdstipVervallen = tijdstipVervallen;
    }

    public RedenWijziging getRedenWijziging() {
        return redenWijziging;
    }

    public void setRedenWijziging(final RedenWijziging redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    public FunctieAdres getSoort() {
        return soort;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    public String getGemeenteDeel() {
        return gemeenteDeel;
    }

    public Integer getHuisNummer() {
        return huisNummer;
    }

    public void setHuisNummer(final Integer huisNummer) {
        this.huisNummer = huisNummer;
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

    public String getLocatieTOVAdres() {
        return locatieTOVAdres;
    }

    public Partij getGemeente() {
        return gemeente;
    }

    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(final Land land) {
        this.land = land;
    }

    @Override
    public HistoriePersoonAdres clone() {
        HistoriePersoonAdres kopie = new HistoriePersoonAdres(persoonAdres);
        kopie.datumAanvangGeldigheid = datumAanvangGeldigheid;
        kopie.datumEindeGeldigheid = datumEindeGeldigheid;
        kopie.tijdstipRegistratie = tijdstipRegistratie;
        kopie.tijdstipVervallen = tijdstipVervallen;
        return kopie;
    }
}
