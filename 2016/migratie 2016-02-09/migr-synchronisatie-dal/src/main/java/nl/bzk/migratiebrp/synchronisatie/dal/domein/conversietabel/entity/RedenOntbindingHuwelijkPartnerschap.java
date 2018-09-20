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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the redenontbindinghuwelijkpartnerschap database table.
 *
 */
@Entity
@Table(name = "convrdnontbindinghuwelijkger", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class RedenOntbindingHuwelijkPartnerschap implements Serializable {
    /**
     * Melding indien de reden beeindiging null is.
     */
    public static final String REDEN_BEEINDIGING_RELATIE_MAG_NIET_NULL_ZIJN = "redenBeeindigingRelatie mag niet null zijn";

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr0741rdnontbindinghuwelij", insertable = false, updatable = false, length = 1, nullable = false, unique = true)
    private char lo3RedenOntbindingHuwelijkGp;

    @ManyToOne
    @JoinColumn(name = "rdneinderelatie", nullable = false, unique = true)
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    /**
     * JPA default constructor.
     */
    protected RedenOntbindingHuwelijkPartnerschap() {
    }

    /**
     * Maak een nieuwe reden ontbinding huwelijk partnerschap.
     *
     * @param lo3RedenOntbindingHuwelijkGp
     *            lo3 reden ontbinding huwelijk gp
     * @param redenBeeindigingRelatie
     *            reden beeindiging relatie
     */
    public RedenOntbindingHuwelijkPartnerschap(final char lo3RedenOntbindingHuwelijkGp, final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.lo3RedenOntbindingHuwelijkGp = lo3RedenOntbindingHuwelijkGp;
        ValidationUtils.controleerOpNullWaarden(REDEN_BEEINDIGING_RELATIE_MAG_NIET_NULL_ZIJN, redenBeeindigingRelatie);
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
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
     * Geef de waarde van lo3 reden ontbinding huwelijk gp.
     *
     * @return lo3 reden ontbinding huwelijk gp
     */
    public char getLo3RedenOntbindingHuwelijkGp() {
        return lo3RedenOntbindingHuwelijkGp;
    }

    /**
     * Zet de waarde van lo3 reden ontbinding huwelijk gp.
     *
     * @param lo3RedenOntbindingHuwelijkGp
     *            lo3 reden ontbinding huwelijk gp
     */
    public void setLo3RedenOntbindingHuwelijkGp(final char lo3RedenOntbindingHuwelijkGp) {
        this.lo3RedenOntbindingHuwelijkGp = lo3RedenOntbindingHuwelijkGp;
    }

    /**
     * Geef de waarde van reden beeindiging relatie.
     *
     * @return reden beeindiging relatie
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarde van reden beeindiging relatie.
     *
     * @param redenBeeindigingRelatie
     *            reden beeindiging relatie
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        ValidationUtils.controleerOpNullWaarden(REDEN_BEEINDIGING_RELATIE_MAG_NIET_NULL_ZIJN, redenBeeindigingRelatie);
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }
}
