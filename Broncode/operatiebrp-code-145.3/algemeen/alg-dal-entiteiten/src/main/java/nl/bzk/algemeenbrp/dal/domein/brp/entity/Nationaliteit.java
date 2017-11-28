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
 * The persistent class for the nation database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "nation", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "Nationaliteit" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Nationaliteit")
public class Nationaliteit extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    public static final String NEDERLANDSE = "0001";
    public static final String ONBEKEND = "0000";
    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 4;

    @Id
    @SequenceGenerator(name = "nation_id_generator", sequenceName = "kern.seq_nation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nation_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String naam;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA default constructor.
     */
    protected Nationaliteit() {}

    /**
     * Maak een nieuwe nationaliteit.
     *
     * @param naam naam
     * @param code code
     */
    public Nationaliteit(final String naam, final String code) {
        setNaam(naam);
        setCode(code);
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
     * Zet de waarden voor id van Nationaliteit.
     *
     * @param id de nieuwe waarde voor id van Nationaliteit
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van Nationaliteit.
     *
     * @return de waarde van naam van Nationaliteit
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Nationaliteit.
     *
     * @param naam de nieuwe waarde voor naam van Nationaliteit
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van code van Nationaliteit.
     *
     * @return de waarde van code van Nationaliteit
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van Nationaliteit.
     *
     * @param code de nieuwe waarde voor code van Nationaliteit
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte van 4 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van Nationaliteit.
     *
     * @return de waarde van datum aanvang geldigheid van Nationaliteit
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van Nationaliteit.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van
     *        Nationaliteit
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van Nationaliteit.
     *
     * @return de waarde van datum einde geldigheid van Nationaliteit
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van Nationaliteit.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van Nationaliteit
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Indien Nationaliteit Nederlandse is (1) word true gereturneerd.
     *
     * @return True als getCode() == 1
     */
    public boolean isNederlandse() {
        return NEDERLANDSE.equals(getCode());
    }

    /**
     * Indien Nationaliteit Onbekend is (0) word true gereturneerd.
     *
     * @return True als getCode() == 0
     */
    public boolean isOnbekend() {
        return ONBEKEND.equals(getCode());
    }
}
