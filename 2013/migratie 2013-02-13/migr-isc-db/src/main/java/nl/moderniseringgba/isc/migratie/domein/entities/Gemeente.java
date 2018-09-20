/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.domein.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The persistent class for the gemeente database table.
 */
@Entity
@Table(name = "gemeente", schema = "migratietabel", uniqueConstraints = @UniqueConstraint(columnNames = {
        "gemeenteCode", "datumBrp" }))
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd t.b.v. het datamodel en bevat daarom geen javadoc, daarnaast mogen entities
 * en de methoden van entities niet final zijn.
 */
public class Gemeente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "gemeenteCode", insertable = true, updatable = true, unique = true, nullable = false)
    private Integer gemeenteCode;

    @Column(name = "datumBrp", insertable = true, updatable = true)
    private Integer datumBrp;

    public Integer getGemeenteCode() {
        return gemeenteCode;
    }

    public void setGemeenteCode(final Integer gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    public Integer getDatumBrp() {
        return datumBrp;
    }

    public void setDatumBrp(final Integer datumBrp) {
        this.datumBrp = datumBrp;
    }

}
