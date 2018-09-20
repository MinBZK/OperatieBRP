/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.domein;

import javax.persistence.*;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;

/**
 * Persoon model class tbv POC bijhouding.
 */
@Entity @Table(name = "PersAdres", schema = "Kern") @Access(AccessType.FIELD)
public class PocPersoonAdres {

    @SequenceGenerator(name = "PERSOON_ADRES_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_PersAdres")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON_ADRES_SEQUENCE_GENERATOR")
    private Long           id;
    @ManyToOne
    @JoinColumn(name = "Pers", updatable = false)
    private PocPersoon     persoon;
    @Column(name = "Srt")
    private FunctieAdres   soort;
    @Column(name = "NOR")
    private String         naamOpenbareRuimte;
    @Column(name = "AfgekorteNOR")
    private String         afgekorteNaamOpenbareRuimte;
    @Column(name = "Gemdeel")
    private String         gemeenteDeel;
    @Column(name = "huisnr")
    private Integer        huisNummer;
    @Column(name = "Huisletter")
    private String         huisletter;
    @Column(name = "Huisnrtoevoeging")
    private String         huisnummertoevoeging;
    @Column(name = "postcode")
    private String         postcode;
    @ManyToOne
    @JoinColumn(name = "Wpl")
    private Plaats         woonplaats;
    @Column(name = "LoctovAdres")
    private String         locatieTOVAdres;
    @ManyToOne
    @JoinColumn(name = "gem")
    private Partij         gemeente;
    @ManyToOne
    @JoinColumn(name = "land")
    private Land           land;
    @Column(name = "persadresstatushis")
    private String         persAdresStatusHistorie;
    @Column(name = "dataanvadresh")
    private Integer        datumAanvangAdresHouding;
    @Column(name = "rdnwijz")
    private RedenWijziging redenWijziging;

    /**
     * No-arg constructor voor JPA.
     */
    public PocPersoonAdres() {
    }

    /**
     * Default constructor met verplichte velden.
     *
     * @param pocPersoon de persoon waarvoor dit adres geldt.
     * @param soort de soort/type van het adres.
     */
    public PocPersoonAdres(final PocPersoon pocPersoon, final FunctieAdres soort) {
        this.persoon = pocPersoon;
        this.soort = soort;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PocPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PocPersoon persoon) {
        this.persoon = persoon;
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

    public String getPersAdresStatusHistorie() {
        return persAdresStatusHistorie;
    }

    public void setPersAdresStatusHistorie(final String status) {
        this.persAdresStatusHistorie = status;
    }

    public Integer getDatumAanvangAdresHouding() {
        return datumAanvangAdresHouding;
    }

    public void setDatumAanvangAdresHouding(final Integer datumAanvangAdresHouding) {
        this.datumAanvangAdresHouding = datumAanvangAdresHouding;
    }

    public RedenWijziging getRedenWijziging() {
        return redenWijziging;
    }

    public void setRedenWijziging(final RedenWijziging redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    public void setGemeenteDeel(final String gemeenteDeel) {
        this.gemeenteDeel = gemeenteDeel;
    }

    public void setLocatieTOVAdres(final String locatieTOVAdres) {
        this.locatieTOVAdres = locatieTOVAdres;
    }
}
