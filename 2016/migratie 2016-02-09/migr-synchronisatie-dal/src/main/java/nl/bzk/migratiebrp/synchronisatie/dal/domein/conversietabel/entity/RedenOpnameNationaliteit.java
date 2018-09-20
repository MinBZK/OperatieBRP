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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the convrdnopnamenation database table.
 */
@Entity
@Table(name = "convrdnopnamenation", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class RedenOpnameNationaliteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr6310rdnopnamenation", insertable = false, updatable = false, nullable = false, unique = true)
    private String redenOpnameNationaliteit;

    @ManyToOne
    @JoinColumn(name = "rdnverk", nullable = true, unique = true)
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;

    /**
     * JPA default constructor.
     */
    protected RedenOpnameNationaliteit() {
    }

    /**
     * Maakt een nieuwe reden opname nationaliteit.
     * 
     * @param redenOpnameNationaliteit
     *            de LO3 reden opname nationaliteit
     * @param redenVerkrijgingNLNationaliteit
     *            de BRP reden verkrijging Nederlandse nationaliteit. Null als er geen BRP waarde voor is
     */
    public RedenOpnameNationaliteit(final String redenOpnameNationaliteit, final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        setRedenOpnameNationaliteit(redenOpnameNationaliteit);
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;

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
     * @return geeft de reden opname nationaliteit terug
     */
    public String getRedenOpnameNationaliteit() {
        return redenOpnameNationaliteit;
    }

    /**
     * Zet de waarde voor reden opname nationaliteit.
     * 
     * @param redenOpnameNationaliteit
     *            de reden opname nationaliteit
     */
    public void setRedenOpnameNationaliteit(final String redenOpnameNationaliteit) {
        ValidationUtils.controleerOpNullWaarden("De reden opname nationaliteit mag niet null zijn", redenOpnameNationaliteit);
        this.redenOpnameNationaliteit = redenOpnameNationaliteit;
    }

    /**
     * @return de reden verkrijging Nederlandse nationaliteit. Null als er geen BRP waarde is.
     */
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return redenVerkrijgingNLNationaliteit;
    }

    /**
     * Zet de waarde reden verkrijging Nederlandse nationaliteit. Null als er geen BRP waarde is.
     * 
     * @param redenVerkrijgingNLNationaliteit
     *            de reden verkrijging.
     */
    public void setRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;
    }
}
