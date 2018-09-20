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
 * The persistent class for the land database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "landgebied", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class LandOfGebied extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(name = "code", insertable = true, updatable = true, nullable = false, unique = true)
    private short code;

    @Column(insertable = true, updatable = true, nullable = false, length = 80)
    private String naam;

    @Column(insertable = true, updatable = true, length = 2)
    private String iso31661Alpha2;

    @Column(name = "dataanvgel", insertable = false, updatable = false)
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel", insertable = true, updatable = false)
    private Integer datumEindeGeldigheid;

    /**
     * JPA default constructor.
     */
    protected LandOfGebied() {
    }

    /**
     * Maak een nieuwe land of gebied.
     *
     * @param code
     *            code
     * @param naam
     *            naam
     */
    public LandOfGebied(final short code, final String naam) {
        this.code = code;
        setNaam(naam);
    }

    @Override
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
     * Geef de waarde van iso31661 alpha2.
     *
     * @return iso31661 alpha2
     */
    public String getIso31661Alpha2() {
        return iso31661Alpha2;
    }

    /**
     * Zet de waarde van iso31661 alpha2.
     *
     * @param iso31661Alpha2
     *            iso31661 alpha2
     */
    public void setIso31661Alpha2(final String iso31661Alpha2) {
        this.iso31661Alpha2 = iso31661Alpha2;
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Wanneer de landcode van twee landen gelijk zijn dan worden ze beschouwd als inhoudelijk gelijk.
     *
     * @param anderLandOfGebied
     *            het land waarmee vergeleken wordt
     * @return true als de landcode van dit land gelijk is aan de landcode van het andere land, anders false
     */
    public boolean isInhoudelijkGelijkAan(final LandOfGebied anderLandOfGebied) {
        if (this == anderLandOfGebied) {
            return true;
        }
        if (anderLandOfGebied == null) {
            return false;
        }
        return getCode() == anderLandOfGebied.getCode();
    }
}
