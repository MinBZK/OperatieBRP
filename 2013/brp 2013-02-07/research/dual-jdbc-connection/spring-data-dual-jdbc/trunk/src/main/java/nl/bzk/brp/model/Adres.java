/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name="Adres")
public class Adres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Integer getHuisNummer() {
        return huisNummer;
    }

    public void setHuisNummer(Integer huisNummer) {
        this.huisNummer = huisNummer;
    }

    public String getHuisNummerToevoeging() {
        return huisNummerToevoeging;
    }

    public void setHuisNummerToevoeging(String huisNummerToevoeging) {
        this.huisNummerToevoeging = huisNummerToevoeging;
    }

    private String straat;
    private Integer huisNummer;
    private String huisNummerToevoeging;

    public Adres(String straatNaam) {
        straat = straatNaam;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
