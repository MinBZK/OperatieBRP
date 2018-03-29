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
 * The persistent class for the convrnideelnemer database table.
 */
@Entity
@Table(name = "convrnideelnemer", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "RNIDeelnemer" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from RNIDeelnemer d join fetch d.partij p left join fetch p.hisPartijen "
        + "left join fetch p.partijBijhoudingHistorieSet left join fetch p.partijRolSet")
public class RNIDeelnemer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 4;

    @Id
    @SequenceGenerator(name = "convrnideelnemer_id_generator", sequenceName = "conv.seq_convrnideelnemer", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convrnideelnemer_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr8811codernideelnemer", nullable = false, unique = true, length = 4)
    private String lo3CodeRNIDeelnemer;

    @ManyToOne
    @JoinColumn(name = "partij", nullable = false, unique = true)
    private Partij partij;

    /**
     * JPA default constructor.
     */
    protected RNIDeelnemer() {}

    /**
     * Maak een nieuwe RNI deelnemer.
     * 
     * @param lo3CodeRNIDeelnemer lo3 code rni deelnemer
     * @param partij partij
     */
    public RNIDeelnemer(final String lo3CodeRNIDeelnemer, final Partij partij) {
        setLo3CodeRNIDeelnemer(lo3CodeRNIDeelnemer);
        setPartij(partij);
    }

    /**
     * Geef de waarde van id van RNIDeelnemer.
     * 
     * @return de waarde van id van RNIDeelnemer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RNIDeelnemer.
     * 
     * @param id de nieuwe waarde voor id van RNIDeelnemer
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 code rni deelnemer van RNIDeelnemer.
     * 
     * @return de waarde van lo3 code rni deelnemer van RNIDeelnemer
     */
    public String getLo3CodeRNIDeelnemer() {
        return lo3CodeRNIDeelnemer;
    }

    /**
     * Zet de waarden voor lo3 code rni deelnemer van RNIDeelnemer.
     * 
     * @param lo3CodeRNIDeelnemer de nieuwe waarde voor lo3 code rni deelnemer van RNIDeelnemer
     */
    public void setLo3CodeRNIDeelnemer(final String lo3CodeRNIDeelnemer) {
        ValidationUtils.controleerOpNullWaarden("lo3CodeRNIDeelnemer mag niet null zijn", lo3CodeRNIDeelnemer);
        ValidationUtils.controleerOpLengte("lo3CodeRNIDeelnemer moet lengte 4 hebben", lo3CodeRNIDeelnemer, CODE_LENGTE);
        this.lo3CodeRNIDeelnemer = lo3CodeRNIDeelnemer;
    }

    /**
     * Geef de waarde van partij van RNIDeelnemer.
     * 
     * @return de waarde van partij van RNIDeelnemer
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van RNIDeelnemer.
     * 
     * @param partij de nieuwe waarde voor partij van RNIDeelnemer
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

}
