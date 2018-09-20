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
 * The persistent class for the plaats database table.
 * 
 */
@Entity
@Table(name = "plaats", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Plaats implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(name = "dataanvgel", insertable = false, updatable = false, precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dateindegel", insertable = false, updatable = false, precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(insertable = false, updatable = false, nullable = false, length = 40)
    private String naam;

    @Column(name = "code", insertable = false, updatable = false, nullable = false, length = 4, unique = true)
    private String woonplaatscode;

    public Plaats() {
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

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public String getWoonplaatscode() {
        return woonplaatscode;
    }

    public void setWoonplaatscode(final String woonplaatscode) {
        this.woonplaatscode = woonplaatscode;
    }

    /**
     * Wanneer de woonplaatscode van twee woonplaatsen gelijk zijn dan worden ze beschouwd als inhoudelijk gelijk.
     * 
     * @param andereWoonplaats
     *            de andere woonplaats waarmee vergeleken wordt
     * @return true als de woonplaatscode gelijk is aan de woonplaatscode van de andere woonplaats, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Plaats andereWoonplaats) {
        if (this == andereWoonplaats) {
            return true;
        }
        if (andereWoonplaats == null) {
            return false;
        }
        if (woonplaatscode == null) {
            if (andereWoonplaats.woonplaatscode != null) {
                return false;
            }
        } else if (!woonplaatscode.equals(andereWoonplaats.woonplaatscode)) {
            return false;
        }
        return true;
    }
}
