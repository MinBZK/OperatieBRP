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
 * The persistent class for the rdnverknlnation database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "rdnverknlnation", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "RedenVerkrijgingNLNationaliteit" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from RedenVerkrijgingNLNationaliteit")
public class RedenVerkrijgingNLNationaliteit extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;
    private static final String OMSCHRIJVING_MAG_NIET_NULL_ZIJN = "omschrijving mag niet null zijn";
    private static final String OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN = "omschrijving mag geen lege string zijn";
    private static final int CODE_LENGTE = 3;

    @Id
    @SequenceGenerator(name = "rdnverknlnation_id_generator", sequenceName = "kern.seq_rdnverknlnation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rdnverknlnation_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    @Column(name = "code", nullable = false, unique = true, length = 3)
    private String code;

    @Column(name = "oms", nullable = false, length = 250, unique = true)
    private String omschrijving;

    /**
     * JPA default constructor.
     */
    protected RedenVerkrijgingNLNationaliteit() {}

    /**
     * Maak een nieuwe reden verkrijging nl nationaliteit.
     *
     * @param code code
     * @param omschrijving omschrijving
     */
    public RedenVerkrijgingNLNationaliteit(final String code, final String omschrijving) {
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
     * Zet de waarden voor id van RedenVerkrijgingNLNationaliteit.
     *
     * @param id de nieuwe waarde voor id van RedenVerkrijgingNLNationaliteit
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van RedenVerkrijgingNLNationaliteit.
     *
     * @return de waarde van datum aanvang geldigheid van RedenVerkrijgingNLNationaliteit
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van RedenVerkrijgingNLNationaliteit.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van
     *        RedenVerkrijgingNLNationaliteit
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van RedenVerkrijgingNLNationaliteit.
     *
     * @return de waarde van datum einde geldigheid van RedenVerkrijgingNLNationaliteit
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van RedenVerkrijgingNLNationaliteit.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van
     *        RedenVerkrijgingNLNationaliteit
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van code van RedenVerkrijgingNLNationaliteit.
     *
     * @return de waarde van code van RedenVerkrijgingNLNationaliteit
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van RedenVerkrijgingNLNationaliteit.
     *
     * @param code de nieuwe waarde voor code van RedenVerkrijgingNLNationaliteit
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte van 3 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van omschrijving van RedenVerkrijgingNLNationaliteit.
     *
     * @return de waarde van omschrijving van RedenVerkrijgingNLNationaliteit
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van RedenVerkrijgingNLNationaliteit.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van RedenVerkrijgingNLNationaliteit
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        ValidationUtils.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
    }
}
