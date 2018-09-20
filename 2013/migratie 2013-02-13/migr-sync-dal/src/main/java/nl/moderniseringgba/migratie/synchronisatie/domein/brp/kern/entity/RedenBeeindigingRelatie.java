/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.DynamischeStamtabel;

/**
 * The persistent class for the rdnbeeindrelatie database table.
 * 
 */
@Entity
@Table(name = "rdnbeeindrelatie", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class RedenBeeindigingRelatie implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(insertable = false, updatable = false, nullable = false, length = 1)
    private String code;

    @Column(name = "oms", insertable = false, updatable = false, nullable = false, length = 250)
    private String omschrijving;

    public RedenBeeindigingRelatie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Als de code van twee RedenBeeindigingRelatie objecten gelijk zijn dan worden ze beschouwd als inhoudelijk gelijk.
     * 
     * @param andereRedenBeeindigingRelatie
     *            de andere RedenBeeindigingRelatie waarmee wordt vergeleken
     * @return true als de code gelijk is aan de code van de andere RedenBeeindigingRelatie
     */
    public boolean isInhoudelijkGelijkAan(final RedenBeeindigingRelatie andereRedenBeeindigingRelatie) {
        if (this == andereRedenBeeindigingRelatie) {
            return true;
        }
        if (andereRedenBeeindigingRelatie == null) {
            return false;
        }
        if (code == null) {
            if (andereRedenBeeindigingRelatie.code != null) {
                return false;
            }
        } else if (!code.equals(andereRedenBeeindigingRelatie.code)) {
            return false;
        }
        return true;
    }
}
