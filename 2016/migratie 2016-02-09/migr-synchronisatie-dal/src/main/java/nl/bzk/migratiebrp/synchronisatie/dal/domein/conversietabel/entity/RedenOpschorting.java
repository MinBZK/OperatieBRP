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
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the redenopschorting database table.
 * 
 */
@Entity
@Table(name = "convrdnopschorting", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class RedenOpschorting implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr6720omsrdnopschortingbij", insertable = false, updatable = false, length = 1, nullable = false, unique = true)
    private char lo3OmschrijvingRedenOpschorting;

    @Column(name = "naderebijhaard", nullable = false, unique = true)
    private short redenOpschortingId;

    /**
     * JPA default constructor.
     */
    protected RedenOpschorting() {
    }

    /**
     * Maak een nieuwe reden opschorting.
     *
     * @param lo3OmschrijvingRedenOpschorting
     *            lo3 omschrijving reden opschorting
     * @param redenOpschorting
     *            reden opschorting
     */
    public RedenOpschorting(final char lo3OmschrijvingRedenOpschorting, final NadereBijhoudingsaard redenOpschorting) {
        this.lo3OmschrijvingRedenOpschorting = lo3OmschrijvingRedenOpschorting;
        setRedenOpschortingId(redenOpschorting);
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
     * Geef de waarde van lo3 omschrijving reden opschorting.
     *
     * @return lo3 omschrijving reden opschorting
     */
    public char getLo3OmschrijvingRedenOpschorting() {
        return lo3OmschrijvingRedenOpschorting;
    }

    /**
     * Zet de waarde van lo3 omschrijving reden opschorting.
     *
     * @param lo3OmschrijvingRedenOpschorting
     *            lo3 omschrijving reden opschorting
     */
    public void setLo3OmschrijvingRedenOpschorting(final char lo3OmschrijvingRedenOpschorting) {
        this.lo3OmschrijvingRedenOpschorting = lo3OmschrijvingRedenOpschorting;
    }

    /**
     * Geef de waarde van reden opschorting.
     *
     * @return reden opschorting
     */
    public NadereBijhoudingsaard getRedenOpschorting() {
        return NadereBijhoudingsaard.parseId(redenOpschortingId);
    }

    /**
     * Zet de waarde van reden opschorting id.
     *
     * @param redenOpschorting
     *            reden opschorting id
     */
    public void setRedenOpschortingId(final NadereBijhoudingsaard redenOpschorting) {
        if (redenOpschorting == null) {
            throw new NullPointerException("redenOpschorting mag niet null zijn.");
        } else {
            ValidationUtils.controleerOpNullWaarden("redenOpschorting mag niet null zijn", redenOpschorting);
            this.redenOpschortingId = redenOpschorting.getId();
        }
    }

}
