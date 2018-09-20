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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the convrnideelnemer database table.
 * 
 */
@Entity
@Table(name = "convrnideelnemer", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class RNIDeelnemer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr8811codernideelnemer", insertable = false, updatable = false, nullable = false, unique = true)
    private short lo3CodeRNIDeelnemer;

    @ManyToOne
    @JoinColumn(name = "partij", nullable = false, unique = true)
    private Partij partij;

    /**
     * JPA default constructor.
     */
    protected RNIDeelnemer() {
    }

    /**
     * Maak een nieuwe RNI deelnemer.
     *
     * @param lo3CodeRNIDeelnemer
     *            lo3 code rni deelnemer
     * @param partij
     *            partij
     */
    public RNIDeelnemer(final short lo3CodeRNIDeelnemer, final Partij partij) {
        setLo3CodeRNIDeelnemer(lo3CodeRNIDeelnemer);
        setPartij(partij);
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
     * Geef de waarde van lo3 code rni deelnemer.
     *
     * @return lo3 code rni deelnemer
     */
    public short getLo3CodeRNIDeelnemer() {
        return lo3CodeRNIDeelnemer;
    }

    /**
     * Zet de waarde van lo3 code rni deelnemer.
     *
     * @param lo3CodeRNIDeelnemer
     *            lo3 code rni deelnemer
     */
    public void setLo3CodeRNIDeelnemer(final short lo3CodeRNIDeelnemer) {
        ValidationUtils.controleerOpNullWaarden("lo3CodeRNIDeelnemer mag niet null zijn", lo3CodeRNIDeelnemer);
        this.lo3CodeRNIDeelnemer = lo3CodeRNIDeelnemer;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

}
