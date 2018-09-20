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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the convrdnbeeindigingnation database table.
 */
@Entity
@Table(name = "convrdnbeeindigennation", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class RedenBeeindigingNationaliteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr6410rdnbeeindigennation", insertable = false, updatable = false, nullable = false, unique = true)
    private String redenBeeindigingNationaliteit;

    @ManyToOne
    @JoinColumn(name = "rdnverlies", nullable = true, unique = true)
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;

    /**
     * JPA default constructor.
     */
    protected RedenBeeindigingNationaliteit() {
    }

    /**
     * Maakt een nieuwe reden beeindiging nationaliteit.
     *
     * @param redenBeeindigingNationaliteit
     *            de LO3 reden beeindiging nationaliteit
     * @param redenVerliesNLNationaliteit
     *            de BRP reden verlies Nederlandse nationaliteit. Null als er geen BRP waarde voor is
     */
    public RedenBeeindigingNationaliteit(final String redenBeeindigingNationaliteit, final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        setRedenBeeindigingNationaliteit(redenBeeindigingNationaliteit);
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;

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
     * @return geeft de reden beeindiging nationaliteit terug
     */
    public String getRedenBeeindigingNationaliteit() {
        return redenBeeindigingNationaliteit;
    }

    /**
     * Zet de waarde voor reden beeindiging nationaliteit.
     * 
     * @param redenBeeindigingNationaliteit
     *            de reden beeindiging nationaliteit
     */
    public void setRedenBeeindigingNationaliteit(final String redenBeeindigingNationaliteit) {
        ValidationUtils.controleerOpNullWaarden("De reden beeindiging nationaliteit mag niet null zijn", redenBeeindigingNationaliteit);
        this.redenBeeindigingNationaliteit = redenBeeindigingNationaliteit;
    }

    /**
     * @return de reden verlies Nederlandse nationaliteit. Null als er geen BRP waarde is.
     */
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    /**
     * Zet de waarde reden verlies Nederlandse nationaliteit. Null als er geen BRP waarde is.
     * 
     * @param redenVerliesNLNationaliteit
     *            de reden verlies.
     */
    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }
}
