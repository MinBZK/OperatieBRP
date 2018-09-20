/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the redenopschorting database table.
 * 
 */
@Entity
@Table(name = "redenopschorting", schema = "conversietabel")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class RedenOpschorting implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lo3code", insertable = false, updatable = false, unique = true, nullable = false, length = 1)
    private String lo3Code;

    @Column(name = "brpcode", insertable = false, updatable = false, unique = true, length = 1)
    private String brpCode;

    public String getLo3Code() {
        return lo3Code;
    }

    public String getBrpCode() {
        return brpCode;
    }
}
