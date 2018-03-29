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
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the redenontbindinghuwelijkpartnerschap database table.
 */
@Entity
@Table(name = "convrdnontbindinghuwelijkger", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "RedenOntbindingHuwelijkPartnerschap" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "from RedenOntbindingHuwelijkPartnerschap r left join fetch r.redenBeeindigingRelatie")
public class RedenOntbindingHuwelijkPartnerschap implements Serializable {
    /**
     * Melding indien de reden beeindiging null is.
     */
    public static final String REDEN_BEEINDIGING_RELATIE_MAG_NIET_NULL_ZIJN = "redenBeeindigingRelatie mag niet null zijn";

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "convrdnontbindinghuwelijkger_id_generator", sequenceName = "conv.seq_convrdnontbindinghuwelijkger", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convrdnontbindinghuwelijkger_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr0741rdnontbindinghuwelij", length = 1, nullable = false, unique = true)
    private char lo3RedenOntbindingHuwelijkGp;

    @ManyToOne
    @JoinColumn(name = "rdneinderelatie", nullable = false, unique = true)
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    /**
     * JPA default constructor.
     */
    protected RedenOntbindingHuwelijkPartnerschap() {}

    /**
     * Maak een nieuwe reden ontbinding huwelijk partnerschap.
     * 
     * @param lo3RedenOntbindingHuwelijkGp lo3 reden ontbinding huwelijk gp
     * @param redenBeeindigingRelatie reden beeindiging relatie
     */
    public RedenOntbindingHuwelijkPartnerschap(final char lo3RedenOntbindingHuwelijkGp, final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.lo3RedenOntbindingHuwelijkGp = lo3RedenOntbindingHuwelijkGp;
        ValidationUtils.controleerOpNullWaarden(REDEN_BEEINDIGING_RELATIE_MAG_NIET_NULL_ZIJN, redenBeeindigingRelatie);
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van id van RedenOntbindingHuwelijkPartnerschap.
     * 
     * @return de waarde van id van RedenOntbindingHuwelijkPartnerschap
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RedenOntbindingHuwelijkPartnerschap.
     * 
     * @param id de nieuwe waarde voor id van RedenOntbindingHuwelijkPartnerschap
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 reden ontbinding huwelijk gp van RedenOntbindingHuwelijkPartnerschap.
     * 
     * @return de waarde van lo3 reden ontbinding huwelijk gp van
     *         RedenOntbindingHuwelijkPartnerschap
     */
    public char getLo3RedenOntbindingHuwelijkGp() {
        return lo3RedenOntbindingHuwelijkGp;
    }

    /**
     * Zet de waarden voor lo3 reden ontbinding huwelijk gp van RedenOntbindingHuwelijkPartnerschap.
     * 
     * @param lo3RedenOntbindingHuwelijkGp de nieuwe waarde voor lo3 reden ontbinding huwelijk gp
     *        van RedenOntbindingHuwelijkPartnerschap
     */
    public void setLo3RedenOntbindingHuwelijkGp(final char lo3RedenOntbindingHuwelijkGp) {
        this.lo3RedenOntbindingHuwelijkGp = lo3RedenOntbindingHuwelijkGp;
    }

    /**
     * Geef de waarde van reden beeindiging relatie van RedenOntbindingHuwelijkPartnerschap.
     * 
     * @return de waarde van reden beeindiging relatie van RedenOntbindingHuwelijkPartnerschap
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarden voor reden beeindiging relatie van RedenOntbindingHuwelijkPartnerschap.
     * 
     * @param redenBeeindigingRelatie de nieuwe waarde voor reden beeindiging relatie van
     *        RedenOntbindingHuwelijkPartnerschap
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        ValidationUtils.controleerOpNullWaarden(REDEN_BEEINDIGING_RELATIE_MAG_NIET_NULL_ZIJN, redenBeeindigingRelatie);
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }
}
