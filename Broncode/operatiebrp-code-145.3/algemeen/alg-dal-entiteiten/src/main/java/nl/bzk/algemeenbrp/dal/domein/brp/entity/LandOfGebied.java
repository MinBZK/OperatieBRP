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
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the land database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "landgebied", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "LandOfGebied" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from LandOfGebied")
public class LandOfGebied extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {

    /**
     * Code voor Nederland.
     */
    public static final String CODE_NEDERLAND = "6030";
    /**
     * Code voor Onbekend.
     */
    public static final String CODE_ONBEKEND = "0000";
    /**
     * Code voor Internationaal.
     */
    public static final String CODE_INTERNATIONAAL = "9999";

    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 4;

    @Id
    @SequenceGenerator(name = "landgebied_id_generator", sequenceName = "kern.seq_landgebied", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "landgebied_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(nullable = false, length = 80)
    private String naam;

    @Column(length = 2)
    private String iso31661Alpha2;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA default constructor.
     */
    protected LandOfGebied() {}

    /**
     * Maak een nieuwe land of gebied.
     *
     * @param code code
     * @param naam naam
     */
    public LandOfGebied(final String code, final String naam) {
        setCode(code);
        setNaam(naam);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van LandOfGebied.
     *
     * @param id de nieuwe waarde voor id van LandOfGebied
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van LandOfGebied.
     *
     * @return de waarde van datum aanvang geldigheid van LandOfGebied
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van LandOfGebied.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van LandOfGebied
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van LandOfGebied.
     *
     * @return de waarde van datum einde geldigheid van LandOfGebied
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van LandOfGebied.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van LandOfGebied
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van iso31661 alpha2 van LandOfGebied.
     *
     * @return de waarde van iso31661 alpha2 van LandOfGebied
     */
    public String getIso31661Alpha2() {
        return iso31661Alpha2;
    }

    /**
     * Zet de waarden voor iso31661 alpha2 van LandOfGebied.
     *
     * @param iso31661Alpha2 de nieuwe waarde voor iso31661 alpha2 van LandOfGebied
     */
    public void setIso31661Alpha2(final String iso31661Alpha2) {
        this.iso31661Alpha2 = iso31661Alpha2;
    }

    /**
     * Geef de waarde van code van LandOfGebied.
     *
     * @return de waarde van code van LandOfGebied
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van LandOfGebied.
     *
     * @param code de nieuwe waarde voor code van LandOfGebied
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte van 4 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van naam van LandOfGebied.
     *
     * @return de waarde van naam van LandOfGebied
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van LandOfGebied.
     *
     * @param naam de nieuwe waarde voor naam van LandOfGebied
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Wanneer de landcode van twee landen gelijk zijn dan worden ze beschouwd als inhoudelijk
     * gelijk.
     *
     * @param anderLandOfGebied het land waarmee vergeleken wordt
     * @return true als de landcode van dit land gelijk is aan de landcode van het andere land,
     *         anders false
     */
    public boolean isInhoudelijkGelijkAan(final LandOfGebied anderLandOfGebied) {
        return this == anderLandOfGebied || anderLandOfGebied != null && getCode().equals(anderLandOfGebied.getCode());
    }
}
