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
 * The persistent class for the convrdnopnamenation database table.
 */
@Entity
@Table(name = "convrdnopnamenation", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RedenOpnameNationaliteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "convrdnopnamenation_id_generator", sequenceName = "conv.seq_convrdnopnamenation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convrdnopnamenation_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr6310rdnopnamenation", nullable = false, unique = true)
    private String redenOpnameNationaliteit;

    @ManyToOne
    @JoinColumn(name = "rdnverk", nullable = true, unique = true)
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;

    /**
     * JPA default constructor.
     */
    protected RedenOpnameNationaliteit() {}

    /**
     * Maakt een nieuwe reden opname nationaliteit.
     *
     * @param redenOpnameNationaliteit de LO3 reden opname nationaliteit
     * @param redenVerkrijgingNLNationaliteit de BRP reden verkrijging Nederlandse nationaliteit.
     *        Null als er geen BRP waarde voor is
     */
    public RedenOpnameNationaliteit(final String redenOpnameNationaliteit, final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        setRedenOpnameNationaliteit(redenOpnameNationaliteit);
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;

    }

    /**
     * Geef de waarde van id van RedenOpnameNationaliteit.
     *
     * @return de waarde van id van RedenOpnameNationaliteit
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RedenOpnameNationaliteit.
     *
     * @param id de nieuwe waarde voor id van RedenOpnameNationaliteit
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van reden opname nationaliteit van RedenOpnameNationaliteit.
     *
     * @return de waarde van reden opname nationaliteit van RedenOpnameNationaliteit
     */
    public String getRedenOpnameNationaliteit() {
        return redenOpnameNationaliteit;
    }

    /**
     * Zet de waarden voor reden opname nationaliteit van RedenOpnameNationaliteit.
     *
     * @param redenOpnameNationaliteit de nieuwe waarde voor reden opname nationaliteit van
     *        RedenOpnameNationaliteit
     */
    public void setRedenOpnameNationaliteit(final String redenOpnameNationaliteit) {
        ValidationUtils.controleerOpNullWaarden("De reden opname nationaliteit mag niet null zijn", redenOpnameNationaliteit);
        this.redenOpnameNationaliteit = redenOpnameNationaliteit;
    }

    /**
     * Geef de waarde van reden verkrijging nl nationaliteit van RedenOpnameNationaliteit.
     *
     * @return de waarde van reden verkrijging nl nationaliteit van RedenOpnameNationaliteit
     */
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return redenVerkrijgingNLNationaliteit;
    }

    /**
     * Zet de waarden voor reden verkrijging nl nationaliteit van RedenOpnameNationaliteit.
     *
     * @param redenVerkrijgingNLNationaliteit de nieuwe waarde voor reden verkrijging nl
     *        nationaliteit van RedenOpnameNationaliteit
     */
    public void setRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;
    }
}
