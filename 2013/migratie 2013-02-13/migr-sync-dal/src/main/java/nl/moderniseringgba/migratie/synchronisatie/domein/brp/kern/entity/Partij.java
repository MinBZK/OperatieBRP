/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the partij database table.
 * 
 */
@Entity
@Table(name = "partij", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Partij implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PARTIJ_ID_GENERATOR", sequenceName = "KERN.SEQ_PARTIJ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTIJ_ID_GENERATOR")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "dataanv", precision = 8)
    private BigDecimal datumAanvang;

    @Column(name = "dateinde", precision = 8)
    private BigDecimal datumEinde;

    @Column(name = "code", precision = 8)
    private BigDecimal gemeentecode;

    @Column(name = "gemstatushis", nullable = false, length = 1)
    private String gemeenteStatusHistorie;

    @Column(nullable = false, length = 40)
    private String naam;

    @Column(name = "partijstatushis", nullable = false, length = 1)
    private String partijStatusHistorie;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderdeelvan")
    private Partij onderdeelVanPartij;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "voortzettendegem")
    private Partij voortzettendeGemeentePartij;

    // bi-directional many-to-one association to Sector
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "sector")
    private Sector sector;

    @Column(name = "srt")
    private Integer soortPartijId;

    public Partij() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BigDecimal getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final BigDecimal datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public BigDecimal getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final BigDecimal datumEinde) {
        this.datumEinde = datumEinde;
    }

    public BigDecimal getGemeentecode() {
        return gemeentecode;
    }

    public void setGemeentecode(final BigDecimal gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public String getGemeenteStatusHistorie() {
        return gemeenteStatusHistorie;
    }

    public void setGemeenteStatusHistorie(final String gemeenteStatusHistorie) {
        this.gemeenteStatusHistorie = gemeenteStatusHistorie;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public String getPartijStatusHistorie() {
        return partijStatusHistorie;
    }

    public void setPartijStatusHistorie(final String partijStatusHistorie) {
        this.partijStatusHistorie = partijStatusHistorie;
    }

    public Partij getOnderdeelVanPartij() {
        return onderdeelVanPartij;
    }

    public void setOnderdeelVanPartij(final Partij onderdeelVanPartij) {
        this.onderdeelVanPartij = onderdeelVanPartij;
    }

    public Partij getVoortzettendeGemeentePartij() {
        return voortzettendeGemeentePartij;
    }

    public void setVoortzettendeGemeentePartij(final Partij voortzettendeGemeentePartij) {
        this.voortzettendeGemeentePartij = voortzettendeGemeentePartij;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(final Sector sector) {
        this.sector = sector;
    }

    /**
     * @return
     */
    public SoortPartij getSoortPartij() {
        return SoortPartij.parseId(soortPartijId);
    }

    /**
     * @param soortPartij
     */
    public void setSoortPartij(final SoortPartij soortPartij) {
        if (soortPartij == null) {
            soortPartijId = null;
        } else {
            soortPartijId = soortPartij.getId();
        }
    }

    /**
     * Als de naam van twee partijen gelijk zijn dan worden ze als inhoudelijk gelijk beschouwd.
     * 
     * @param anderePartij
     *            de andere partij waaarme vergeleken wordt
     * @return true als de naam van deze partij gelijk is aan de andere partij, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Partij anderePartij) {
        if (this == anderePartij) {
            return true;
        }
        if (anderePartij == null) {
            return false;
        }
        if (naam == null) {
            if (anderePartij.naam != null) {
                return false;
            }
        } else if (!naam.equals(anderePartij.naam)) {
            return false;
        }
        return true;
    }
}
