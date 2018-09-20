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

/**
 * The persistent class for the gegeveninonderzoek database table.
 * 
 */
@Entity
@Table(name = "gegeveninonderzoek", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class GegevenInOnderzoek implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "GEGEVENINONDERZOEK_ID_GENERATOR", sequenceName = "KERN.SEQ_GEGEVENINONDERZOEK",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEGEVENINONDERZOEK_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "ident", nullable = false)
    private Long identificatie;

    // bi-directional many-to-one association to DatabaseObject
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srtgegeven", nullable = false)
    private DatabaseObject databaseObject;

    // bi-directional many-to-one association to Onderzoek
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    public GegevenInOnderzoek() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(final Long identificatie) {
        this.identificatie = identificatie;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(final DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    public void setOnderzoek(final Onderzoek onderzoek) {
        this.onderzoek = onderzoek;
    }
}
