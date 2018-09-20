/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.io.Serializable;

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


/**
 * Het adres zoals gedefinieerd in artikel 1.1. van de Wet BRP.
 */
@Entity
@Table(name = "PersAdres", schema = "Kern")
@Access(AccessType.FIELD)
public class PersoonAdres implements Serializable {

    @SequenceGenerator(name = "PERSOON_ADRES_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_PersAdres")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON_ADRES_SEQUENCE_GENERATOR")
    private Long         id;
    @SuppressWarnings("unused")
    @ManyToOne
    @JoinColumn(name = "Pers", insertable = false, updatable = false)
    private Persoon      persoon;
    @Column(name = "Srt")
    private FunctieAdres soort;
    @Column(name = "NOR")
    private String       naamOpenbareRuimte;
    @Column(name = "AfgekorteNOR")
    private String       afgekorteNaamOpenbareRuimte;
    @Column(name = "Gemdeel")
    private String       gemeenteDeel;
    @Column(name = "huisnr")
    private Integer      huisNummer;
    @Column(name = "Huisletter")
    private String       huisletter;
    @Column(name = "Huisnrtoevoeging")
    private String       huisnummertoevoeging;
    @Column(name = "postcode")
    private String       postcode;
    @Column(name = "LoctovAdres")
    private String       locatieTOVAdres;
    @ManyToOne
    @JoinColumn(name = "gem")
    private Partij       gemeente;

    /**
     * No-arg constructor voor JPA.
     */
    protected PersoonAdres() {
    }

    /**
     * Default constructor met verplichte velden.
     *
     * @param persoon de persoon waarvoor dit adres geldt.
     * @param soort de soort/type van het adres.
     */
    public PersoonAdres(final Persoon persoon, final FunctieAdres soort) {
        this.persoon = persoon;
        this.soort = soort;
    }

    public Long getId() {
        return id;
    }

    public Partij getGemeente() {
        return gemeente;
    }

    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }

    public Integer getHuisNummer() {
        return huisNummer;
    }

    public void setHuisNummer(final Integer huisNummer) {
        this.huisNummer = huisNummer;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    public String getGemeenteDeel() {
        return gemeenteDeel;
    }

    public void setGemeenteDeel(final String gemeenteDeel) {
        this.gemeenteDeel = gemeenteDeel;
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

    public String getLocatieTOVAdres() {
        return locatieTOVAdres;
    }

    public void setLocatieTOVAdres(final String locatieTOVAdres) {
        this.locatieTOVAdres = locatieTOVAdres;
    }

    public FunctieAdres getSoort() {
        return soort;
    }

    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }
}
