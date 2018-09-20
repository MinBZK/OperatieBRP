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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.DynamischeStamtabel;

/**
 * The persistent class for the sector database table.
 * 
 */
@Entity
@Table(name = "sector", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Sector implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SECTOR_ID_GENERATOR", sequenceName = "KERN.SEQ_SECTOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SECTOR_ID_GENERATOR")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "dataanvgel", precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dateindegel", precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(nullable = false, length = 40)
    private String naam;

    // bi-directional many-to-one association to PartijHistorie
    @OneToMany(mappedBy = "sector", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<PartijHistorie> partijHistorieSet = new LinkedHashSet<PartijHistorie>(0);

    // bi-directional many-to-one association to Partij
    @OneToMany(mappedBy = "sector", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<Partij> partijSet = new LinkedHashSet<Partij>(0);

    public Sector() {
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

    public Set<PartijHistorie> getPartijHistorieSet() {
        return partijHistorieSet;
    }

    /**
     * @param partijHistorie
     */
    public void addPartijHistorie(final PartijHistorie partijHistorie) {
        partijHistorie.setSector(this);
        partijHistorieSet.add(partijHistorie);
    }

    public Set<Partij> getPartijSet() {
        return partijSet;
    }

    /**
     * @param partij
     */
    public void addPartij(final Partij partij) {
        partij.setSector(this);
        partijSet.add(partij);
    }
}
