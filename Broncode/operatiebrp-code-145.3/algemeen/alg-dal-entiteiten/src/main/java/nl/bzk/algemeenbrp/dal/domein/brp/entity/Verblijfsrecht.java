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
 * The persistent class for the verblijfsr database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "aandverblijfsr", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "Verblijfsrecht" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Verblijfsrecht")
public class Verblijfsrecht extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    public static final short GEEN_VERBLIJFSTITEL = 98;
    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 2;

    @Id
    @SequenceGenerator(name = "aandverblijfsr_id_generator", sequenceName = "kern.seq_aandverblijfsr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aandverblijfsr_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "oms", nullable = false, length = 250, unique = true)
    private String omschrijving;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA default constructor.
     */
    protected Verblijfsrecht() {}

    /**
     * Maak een nieuwe verblijfsrecht.
     *
     * @param code code
     * @param omschrijving omschrijving
     */
    public Verblijfsrecht(final String code, final String omschrijving) {
        setCode(code);
        setOmschrijving(omschrijving);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Verblijfsrecht.
     *
     * @param id de nieuwe waarde voor id van Verblijfsrecht
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van Verblijfsrecht.
     *
     * @return de waarde van code van Verblijfsrecht
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van Verblijfsrecht.
     *
     * @param code de nieuwe waarde voor code van Verblijfsrecht
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet lengte 2 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van Verblijfsrecht.
     *
     * @return de waarde van datum aanvang geldigheid van Verblijfsrecht
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van Verblijfsrecht.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van
     *        Verblijfsrecht
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van Verblijfsrecht.
     *
     * @return de waarde van datum einde geldigheid van Verblijfsrecht
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van Verblijfsrecht.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van Verblijfsrecht
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van omschrijving van Verblijfsrecht.
     *
     * @return de waarde van omschrijving van Verblijfsrecht
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van Verblijfsrecht.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van Verblijfsrecht
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden("omschrijving mag niet null zijn", omschrijving);
        ValidationUtils.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }
}
