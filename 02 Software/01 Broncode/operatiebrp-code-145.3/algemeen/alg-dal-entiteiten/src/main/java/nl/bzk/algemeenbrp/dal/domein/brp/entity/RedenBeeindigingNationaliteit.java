/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the convrdnbeeindigingnation database table.
 */
@Entity
@Table(name = "convrdnbeeindigennation", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RedenBeeindigingNationaliteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "convrdnbeeindigennation_id_generator", sequenceName = "conv.seq_convrdnbeeindigennation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convrdnbeeindigennation_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr6410rdnbeeindigennation", nullable = false, unique = true)
    private String redenBeeindigingNationaliteit;

    @ManyToOne
    @JoinColumn(name = "rdnverlies", nullable = true, unique = true)
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;

    /**
     * JPA default constructor.
     */
    protected RedenBeeindigingNationaliteit() {}

    /**
     * Maakt een nieuwe reden beeindiging nationaliteit.
     *
     * @param redenBeeindigingNationaliteit de LO3 reden beeindiging nationaliteit
     * @param redenVerliesNLNationaliteit de BRP reden verlies Nederlandse nationaliteit. Null als
     *        er geen BRP waarde voor is
     */
    public RedenBeeindigingNationaliteit(final String redenBeeindigingNationaliteit, final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        setRedenBeeindigingNationaliteit(redenBeeindigingNationaliteit);
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;

    }

    /**
     * Geef de waarde van id van RedenBeeindigingNationaliteit.
     *
     * @return de waarde van id van RedenBeeindigingNationaliteit
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RedenBeeindigingNationaliteit.
     *
     * @param id de nieuwe waarde voor id van RedenBeeindigingNationaliteit
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van reden beeindiging nationaliteit van RedenBeeindigingNationaliteit.
     *
     * @return de waarde van reden beeindiging nationaliteit van RedenBeeindigingNationaliteit
     */
    public String getRedenBeeindigingNationaliteit() {
        return redenBeeindigingNationaliteit;
    }

    /**
     * Zet de waarden voor reden beeindiging nationaliteit van RedenBeeindigingNationaliteit.
     *
     * @param redenBeeindigingNationaliteit de nieuwe waarde voor reden beeindiging nationaliteit
     *        van RedenBeeindigingNationaliteit
     */
    public void setRedenBeeindigingNationaliteit(final String redenBeeindigingNationaliteit) {
        ValidationUtils.controleerOpNullWaarden("De reden beeindiging nationaliteit mag niet null zijn", redenBeeindigingNationaliteit);
        this.redenBeeindigingNationaliteit = redenBeeindigingNationaliteit;
    }

    /**
     * Geef de waarde van reden verlies nl nationaliteit van RedenBeeindigingNationaliteit.
     *
     * @return de waarde van reden verlies nl nationaliteit van RedenBeeindigingNationaliteit
     */
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    /**
     * Zet de waarden voor reden verlies nl nationaliteit van RedenBeeindigingNationaliteit.
     *
     * @param redenVerliesNLNationaliteit de nieuwe waarde voor reden verlies nl nationaliteit van
     *        RedenBeeindigingNationaliteit
     */
    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }
}
