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
 * The persistent class for the convaandinhingvermissingreis database table.
 */
@Entity
@Table(name = "convaandinhingvermissingreis", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "AanduidingInhoudingVermissingReisdocument" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "from AanduidingInhoudingVermissingReisdocument a left join fetch a.aanduidingInhoudingOfVermissingReisdocument")
public class AanduidingInhoudingVermissingReisdocument implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_MAG_NIET_NULL_ZIJN =
            "aanduidingInhoudingOfVermissingReisdocument mag niet null zijn";

    @Id
    @SequenceGenerator(name = "convaandinhingvermissingreis_id_generator", sequenceName = "conv.seq_convaandinhingvermissingreis", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convaandinhingvermissingreis_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr3570aandinhingdanwelverm", length = 1, nullable = false, unique = true)
    private char lo3AanduidingInhoudingOfVermissingReisdocument;

    @ManyToOne
    @JoinColumn(name = "aandinhingvermissingreisdoc", nullable = false, unique = true)
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;

    /**
     * JPA default constructor.
     */
    protected AanduidingInhoudingVermissingReisdocument() {}

    /**
     * Maak een nieuwe aanduiding inhouding vermissing reisdocument.
     * 
     * @param lo3AanduidingInhoudingOfVermissingReisdocument lo3 aanduiding inhouding of vermissing
     *        reisdocument
     * @param aanduidingInhoudingOfVermissingReisdocument aanduiding inhouding of vermissing
     *        reisdocument
     */
    public AanduidingInhoudingVermissingReisdocument(final char lo3AanduidingInhoudingOfVermissingReisdocument,
            final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument) {
        this.lo3AanduidingInhoudingOfVermissingReisdocument = lo3AanduidingInhoudingOfVermissingReisdocument;
        ValidationUtils.controleerOpNullWaarden(AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_MAG_NIET_NULL_ZIJN,
                aanduidingInhoudingOfVermissingReisdocument);
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Geef de waarde van id van AanduidingInhoudingVermissingReisdocument.
     * 
     * @return de waarde van id van AanduidingInhoudingVermissingReisdocument
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van AanduidingInhoudingVermissingReisdocument.
     * 
     * @param id de nieuwe waarde voor id van AanduidingInhoudingVermissingReisdocument
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 aanduiding inhouding of vermissing reisdocument van
     * AanduidingInhoudingVermissingReisdocument.
     * 
     * @return de waarde van lo3 aanduiding inhouding of vermissing reisdocument van
     *         AanduidingInhoudingVermissingReisdocument
     */
    public char getLo3AanduidingInhoudingOfVermissingReisdocument() {
        return lo3AanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarden voor lo3 aanduiding inhouding of vermissing reisdocument van
     * AanduidingInhoudingVermissingReisdocument.
     * 
     * @param lo3AanduidingInhoudingOfVermissingReisdocument de nieuwe waarde voor lo3 aanduiding
     *        inhouding of vermissing reisdocument van AanduidingInhoudingVermissingReisdocument
     */
    public void setLo3AanduidingInhoudingOfVermissingReisdocument(final char lo3AanduidingInhoudingOfVermissingReisdocument) {
        this.lo3AanduidingInhoudingOfVermissingReisdocument = lo3AanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Geef de waarde van aanduiding inhouding of vermissing reisdocument van
     * AanduidingInhoudingVermissingReisdocument.
     * 
     * @return de waarde van aanduiding inhouding of vermissing reisdocument van
     *         AanduidingInhoudingVermissingReisdocument
     */
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocument() {
        return aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarden voor aanduiding inhouding of vermissing reisdocument van
     * AanduidingInhoudingVermissingReisdocument.
     * 
     * @param aanduidingInhoudingOfVermissingReisdocument de nieuwe waarde voor aanduiding inhouding
     *        of vermissing reisdocument van AanduidingInhoudingVermissingReisdocument
     */
    public void setAanduidingInhoudingOfVermissingReisdocument(final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument) {
        ValidationUtils.controleerOpNullWaarden(AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_MAG_NIET_NULL_ZIJN,
                aanduidingInhoudingOfVermissingReisdocument);
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
    }
}
