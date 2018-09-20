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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the convaandinhingvermissingreis database table.
 * 
 */
@Entity
@Table(name = "convaandinhingvermissingreis", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class AanduidingInhoudingVermissingReisdocument implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_MAG_NIET_NULL_ZIJN =
            "aanduidingInhoudingOfVermissingReisdocument mag niet null zijn";

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr3570aandinhingdanwelverm", insertable = false, updatable = false, length = 1, nullable = false, unique = true)
    private char lo3AanduidingInhoudingOfVermissingReisdocument;

    @ManyToOne
    @JoinColumn(name = "aandinhingvermissingreisdoc", nullable = false, unique = true)
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;

    /**
     * JPA default constructor.
     */
    protected AanduidingInhoudingVermissingReisdocument() {
    }

    /**
     * Maak een nieuwe aanduiding inhouding vermissing reisdocument.
     *
     * @param lo3AanduidingInhoudingOfVermissingReisdocument
     *            lo3 aanduiding inhouding of vermissing reisdocument
     * @param aanduidingInhoudingOfVermissingReisdocument
     *            aanduiding inhouding of vermissing reisdocument
     */
    public AanduidingInhoudingVermissingReisdocument(
        final char lo3AanduidingInhoudingOfVermissingReisdocument,
        final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument)
    {
        this.lo3AanduidingInhoudingOfVermissingReisdocument = lo3AanduidingInhoudingOfVermissingReisdocument;
        ValidationUtils.controleerOpNullWaarden(
            AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_MAG_NIET_NULL_ZIJN,
            aanduidingInhoudingOfVermissingReisdocument);
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
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
     * Geef de waarde van lo3 aanduiding inhouding of vermissing reisdocument.
     *
     * @return lo3 aanduiding inhouding of vermissing reisdocument
     */
    public char getLo3AanduidingInhoudingOfVermissingReisdocument() {
        return lo3AanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarde van lo3 aanduiding inhouding of vermissing reisdocument.
     *
     * @param lo3AanduidingInhoudingOfVermissingReisdocument
     *            lo3 aanduiding inhouding of vermissing reisdocument
     */
    public void setLo3AanduidingInhoudingOfVermissingReisdocument(final char lo3AanduidingInhoudingOfVermissingReisdocument) {
        this.lo3AanduidingInhoudingOfVermissingReisdocument = lo3AanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Geef de waarde van aanduiding inhouding of vermissing reisdocument.
     *
     * @return aanduiding inhouding of vermissing reisdocument
     */
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocument() {
        return aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarde van aanduiding inhouding of vermissing reisdocument.
     *
     * @param aanduidingInhoudingOfVermissingReisdocument
     *            aanduiding inhouding of vermissing reisdocument
     */
    public void setAanduidingInhoudingOfVermissingReisdocument(
        final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument)
    {
        ValidationUtils.controleerOpNullWaarden(
            AANDUIDING_INHOUDING_OF_VERMISSING_REISDOCUMENT_MAG_NIET_NULL_ZIJN,
            aanduidingInhoudingOfVermissingReisdocument);
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
    }
}
