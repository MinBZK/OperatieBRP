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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the redenopschorting database table.
 */
@Entity
@Table(name = "convrdnopschorting", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "RedenOpschorting" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from RedenOpschorting")
public class RedenOpschorting implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "convrdnopschorting_id_generator", sequenceName = "conv.seq_convrdnopschorting", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convrdnopschorting_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr6720omsrdnopschortingbij", length = 1, nullable = false, unique = true)
    private char lo3OmschrijvingRedenOpschorting;

    @Column(name = "naderebijhaard", nullable = false, unique = true)
    private int redenOpschortingId;

    /**
     * JPA default constructor.
     */
    protected RedenOpschorting() {}

    /**
     * Maak een nieuwe reden opschorting.
     * 
     * @param lo3OmschrijvingRedenOpschorting lo3 omschrijving reden opschorting
     * @param redenOpschorting reden opschorting
     */
    public RedenOpschorting(final char lo3OmschrijvingRedenOpschorting, final NadereBijhoudingsaard redenOpschorting) {
        this.lo3OmschrijvingRedenOpschorting = lo3OmschrijvingRedenOpschorting;
        setRedenOpschorting(redenOpschorting);
    }

    /**
     * Geef de waarde van id van RedenOpschorting.
     * 
     * @return de waarde van id van RedenOpschorting
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RedenOpschorting.
     * 
     * @param id de nieuwe waarde voor id van RedenOpschorting
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 omschrijving reden opschorting van RedenOpschorting.
     * 
     * @return de waarde van lo3 omschrijving reden opschorting van RedenOpschorting
     */
    public char getLo3OmschrijvingRedenOpschorting() {
        return lo3OmschrijvingRedenOpschorting;
    }

    /**
     * Zet de waarden voor lo3 omschrijving reden opschorting van RedenOpschorting.
     * 
     * @param lo3OmschrijvingRedenOpschorting de nieuwe waarde voor lo3 omschrijving reden
     *        opschorting van RedenOpschorting
     */
    public void setLo3OmschrijvingRedenOpschorting(final char lo3OmschrijvingRedenOpschorting) {
        this.lo3OmschrijvingRedenOpschorting = lo3OmschrijvingRedenOpschorting;
    }

    /**
     * Geef de waarde van reden opschorting van RedenOpschorting.
     * 
     * @return de waarde van reden opschorting van RedenOpschorting
     */
    public NadereBijhoudingsaard getRedenOpschorting() {
        return NadereBijhoudingsaard.parseId(redenOpschortingId);
    }

    /**
     * Zet de waarden voor reden opschorting id van RedenOpschorting.
     * 
     * @param redenOpschorting de nieuwe waarde voor reden opschorting id van RedenOpschorting
     */
    public void setRedenOpschorting(final NadereBijhoudingsaard redenOpschorting) {
        ValidationUtils.controleerOpNullWaarden("redenOpschorting mag niet null zijn", redenOpschorting);
        redenOpschortingId = redenOpschorting.getId();
    }

}
