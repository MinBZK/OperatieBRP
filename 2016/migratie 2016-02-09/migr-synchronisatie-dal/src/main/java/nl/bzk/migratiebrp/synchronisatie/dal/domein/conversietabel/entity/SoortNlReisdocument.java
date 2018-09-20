/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the soortnlreisdocument database table.
 * 
 */
@Entity
@Table(name = "convsrtnlreisdoc", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class SoortNlReisdocument implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LO3_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN = "lo3NederlandsReisdocument mag niet null zijn";
    private static final String LO3_NEDERLANDS_REISDOCUMENT_MAG_GEEN_LEGE_STRING_ZIJN = "lo3NederlandsReisdocument mag geen lege string zijn";
    private static final String SOORT_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN = "soortNederlandsReisdocument mag niet null zijn";

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr3511nlreisdoc", insertable = false, updatable = false, length = 2, nullable = false, unique = true)
    private String lo3NederlandsReisdocument;

    @ManyToOne
    @JoinColumn(name = "srtnlreisdoc", nullable = false, unique = true)
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    /**
     * JPA default constructor.
     */
    protected SoortNlReisdocument() {
    }

    /**
     * Maak een nieuwe soort nl reisdocument.
     *
     * @param lo3NederlandsReisdocument
     *            lo3 nederlands reisdocument
     * @param soortNederlandsReisdocument
     *            soort nederlands reisdocument
     */
    public SoortNlReisdocument(final String lo3NederlandsReisdocument, final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden(LO3_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN, lo3NederlandsReisdocument);
        Validatie.controleerOpLegeWaarden(LO3_NEDERLANDS_REISDOCUMENT_MAG_GEEN_LEGE_STRING_ZIJN, lo3NederlandsReisdocument);
        this.lo3NederlandsReisdocument = lo3NederlandsReisdocument;
        ValidationUtils.controleerOpNullWaarden(SOORT_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN, soortNederlandsReisdocument);
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 nederlands reisdocument.
     *
     * @return lo3 nederlands reisdocument
     */
    public String getLo3NederlandsReisdocument() {
        return lo3NederlandsReisdocument;
    }

    /**
     * Zet de waarde van lo3 nederlands reisdocument.
     *
     * @param lo3NederlandsReisdocument
     *            lo3 nederlands reisdocument
     */
    public void setLo3NederlandsReisdocument(final String lo3NederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden(LO3_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN, lo3NederlandsReisdocument);
        Validatie.controleerOpLegeWaarden(LO3_NEDERLANDS_REISDOCUMENT_MAG_GEEN_LEGE_STRING_ZIJN, lo3NederlandsReisdocument);
        this.lo3NederlandsReisdocument = lo3NederlandsReisdocument;
    }

    /**
     * Geef de waarde van soort nederlands reisdocument.
     *
     * @return soort nederlands reisdocument
     */
    public SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * Zet de waarde van soort nederlands reisdocument.
     *
     * @param soortNederlandsReisdocument
     *            soort nederlands reisdocument
     */
    public void setSoortNederlandsReisdocument(final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden(SOORT_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN, soortNederlandsReisdocument);
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
    }
}
