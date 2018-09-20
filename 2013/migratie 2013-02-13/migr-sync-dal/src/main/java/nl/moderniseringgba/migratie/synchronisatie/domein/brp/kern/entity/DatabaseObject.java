/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the dbobject database table.
 * 
 */
@Entity
@Table(name = "dbobject", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class DatabaseObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(name = "dataanvgel", insertable = false, updatable = false, precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dateindegel", insertable = false, updatable = false, precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(insertable = false, updatable = false, nullable = false, length = 80)
    private String javaIdentifier;

    @Column(insertable = false, updatable = false, nullable = false, length = 40)
    private String naam;

    // bi-directional many-to-one association to DatabaseObject
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ouder")
    private DatabaseObject databaseObject;

    // bi-directional many-to-one association to DatabaseObject
    @OneToMany(mappedBy = "databaseObject", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<DatabaseObject> kindSet = new LinkedHashSet<DatabaseObject>(0);

    // bi-directional many-to-one association to SoortDatabaseObject
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt", nullable = false)
    private SoortDatabaseObject soortDatabaseObject;

    public DatabaseObject() {
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

    public String getJavaIdentifier() {
        return javaIdentifier;
    }

    public void setJavaIdentifier(final String javaIdentifier) {
        this.javaIdentifier = javaIdentifier;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(final DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public Set<DatabaseObject> getKindSet() {
        return kindSet;
    }

    public void setKindSet(final Set<DatabaseObject> kindSet) {
        this.kindSet = kindSet;
    }

    public SoortDatabaseObject getSoortDatabaseObject() {
        return soortDatabaseObject;
    }

    public void setSoortDatabaseObject(final SoortDatabaseObject soortDatabaseObject) {
        this.soortDatabaseObject = soortDatabaseObject;
    }
}
