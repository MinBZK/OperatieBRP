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
 * The persistent class for the AdellijkeTitelPredikaat database table.
 * 
 */
@Entity
@Table(name = "adellijketitelpredikaat", schema = "conversietabel", uniqueConstraints = @UniqueConstraint(
        columnNames = { "brpadellijketitel", "brppredikaat", "brpgeslachtsaanduiding" }))
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class AdellijkeTitelPredikaat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lo3AdellijkeTitelPredikaat", insertable = false, updatable = false, nullable = false, length = 2)
    private String lo3AdellijkeTitelPredikaat;

    @Column(name = "brpadellijketitel", insertable = false, updatable = false, nullable = true, length = 1)
    private Character brpAdellijkeTitel;

    @Column(name = "brppredikaat", insertable = false, updatable = false, nullable = true, length = 1)
    private Character brpPredikaat;

    @Column(name = "brpgeslachtsaanduiding", insertable = false, updatable = false, nullable = false, length = 1)
    private Integer brpgeslachtsaanduidingId;

    public String getLo3AdellijkeTitelPredikaat() {
        return lo3AdellijkeTitelPredikaat;
    }

    public Character getBrpAdellijkeTitel() {
        return brpAdellijkeTitel;
    }

    public Character getBrpPredikaat() {
        return brpPredikaat;
    }

    public Integer getBrpGeslachtsaanduidingId() {
        return brpgeslachtsaanduidingId;
    }
}
