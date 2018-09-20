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
import javax.persistence.UniqueConstraint;

/**
 * The persistent class for the Voorvoegsel database table.
 * 
 */
@Entity
@Table(name = "voorvoegsel", schema = "conversietabel", uniqueConstraints = @UniqueConstraint(columnNames = {
        "brpVoorvoegsel", "brpScheidingsteken" }))
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Voorvoegsel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lo3Voorvoegsel", insertable = false, updatable = false, nullable = false, length = 10)
    private String lo3Voorvoegsel;

    @Column(name = "brpVoorvoegsel", insertable = false, updatable = false, nullable = false, length = 10)
    private String brpVoorvoegsel;

    @Column(name = "brpScheidingsteken", insertable = false, updatable = false, nullable = false, length = 1)
    private Character brpScheidingsteken;

    public String getLo3Voorvoegsel() {
        return lo3Voorvoegsel;
    }

    public String getBrpVoorvoegsel() {
        return brpVoorvoegsel;
    }

    public Character getBrpScheidingsteken() {
        return brpScheidingsteken;
    }
}
