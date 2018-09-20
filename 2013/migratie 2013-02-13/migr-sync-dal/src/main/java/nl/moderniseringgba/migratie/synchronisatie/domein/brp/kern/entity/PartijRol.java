/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.DynamischeStamtabel;

/**
 * The persistent class for the partijrol database table.
 * 
 */
@Entity
@Table(name = "partijrol", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PartijRol implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PARTIJROL_ID_GENERATOR", sequenceName = "KERN.SEQ_PARTIJROL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTIJROL_ID_GENERATOR")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "partijrolstatushis", nullable = false, length = 1)
    private String partijRolStatusHistorie;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "rol", nullable = false)
    private Integer rolId;

    public PartijRol() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getPartijRolStatusHistorie() {
        return partijRolStatusHistorie;
    }

    public void setPartijRolStatusHistorie(final String partijRolStatusHistorie) {
        this.partijRolStatusHistorie = partijRolStatusHistorie;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * @return
     */
    public Rol getRol() {
        return Rol.parseId(rolId);
    }

    /**
     * @param rol
     */
    public void setRol(final Rol rol) {
        if (rol == null) {
            rolId = null;
        } else {
            rolId = rol.getId();
        }
    }
}
