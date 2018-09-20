/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.DynamischeStamtabel;

/**
 * The persistent class for the land database table.
 * 
 */
@Entity
@Table(name = "land", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Land implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(name = "dataanvgel", insertable = false, updatable = false, precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dateindegel", insertable = true, updatable = false, precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(insertable = true, updatable = true, length = 2)
    private String iso31661Alpha2;

    @Column(name = "code", insertable = true, updatable = true, nullable = false, length = 5, unique = true)
    private BigDecimal landcode;

    @Column(insertable = true, updatable = true, nullable = false, length = 40)
    private String naam;

    public Land() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BigDecimal getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final BigDecimal datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public BigDecimal getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final BigDecimal datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public String getIso31661Alpha2() {
        return iso31661Alpha2;
    }

    public void setIso31661Alpha2(final String iso31661Alpha2) {
        this.iso31661Alpha2 = iso31661Alpha2;
    }

    public BigDecimal getLandcode() {
        return landcode;
    }

    public void setLandcode(final BigDecimal landcode) {
        this.landcode = landcode;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Wanneer de landcode van twee landen gelijk zijn dan worden ze beschouwd als inhoudelijk gelijk.
     * 
     * @param anderLand
     *            het land waarmee vergeleken wordt
     * @return true als de landcode van dit land gelijk is aan de landcode van het andere land, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Land anderLand) {
        if (this == anderLand) {
            return true;
        }
        if (anderLand == null) {
            return false;
        }
        if (landcode == null) {
            if (anderLand.landcode != null) {
                return false;
            }
        } else if (!landcode.equals(anderLand.landcode)) {
            return false;
        }
        return true;
    }
}
