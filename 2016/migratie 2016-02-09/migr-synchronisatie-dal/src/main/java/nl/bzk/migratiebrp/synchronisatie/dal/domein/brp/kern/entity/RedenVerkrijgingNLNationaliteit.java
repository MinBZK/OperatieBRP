/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the rdnverknlnation database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "rdnverknlnation", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class RedenVerkrijgingNLNationaliteit extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;
    private static final String OMSCHRIJVING_MAG_NIET_NULL_ZIJN = "omschrijving mag niet null zijn";
    private static final String OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN = "omschrijving mag geen lege string zijn";

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Short id;

    @Column(name = "dataanvgel", insertable = false, updatable = false)
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel", insertable = false, updatable = false)
    private Integer datumEindeGeldigheid;

    @Column(name = "code", insertable = false, updatable = false, nullable = false, unique = true)
    private short code;

    @Column(name = "oms", insertable = false, updatable = false, nullable = false, length = 250, unique = true)
    private String omschrijving;

    /**
     * JPA default constructor.
     */
    protected RedenVerkrijgingNLNationaliteit() {
    }

    /**
     * Maak een nieuwe reden verkrijging nl nationaliteit.
     *
     * @param code
     *            code
     * @param omschrijving
     *            omschrijving
     */
    public RedenVerkrijgingNLNationaliteit(final short code, final String omschrijving) {
        this.code = code;
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        Validatie.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
    }

    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid.
     *
     * @return datum aanvang geldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarde van datum aanvang geldigheid.
     *
     * @param datumAanvangGeldigheid
     *            datum aanvang geldigheid
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid.
     *
     * @return datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarde van datum einde geldigheid.
     *
     * @param datumEindeGeldigheid
     *            datum einde geldigheid
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van code.
     *
     * @return code
     */
    public short getCode() {
        return code;
    }

    /**
     * Zet de waarde van code.
     *
     * @param code
     *            code
     */
    public void setCode(final short code) {
        this.code = code;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarde van omschrijving.
     *
     * @param omschrijving
     *            omschrijving
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        Validatie.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
    }
}
