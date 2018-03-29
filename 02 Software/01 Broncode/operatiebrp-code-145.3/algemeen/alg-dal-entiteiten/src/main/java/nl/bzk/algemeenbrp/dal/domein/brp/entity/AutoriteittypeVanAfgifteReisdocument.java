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

/**
 * The persistent class for the auttypevanafgiftereisdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "auttypevanafgiftereisdoc", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AutoriteittypeVanAfgifteReisdocument extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "auttypevanafgiftereisdoc_id_generator", sequenceName = "kern.seq_auttypevanafgiftereisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auttypevanafgiftereisdoc_id_generator")
    @Column(nullable = false, updatable = false)
    private Short id;

    @Column(nullable = false, length = 80, unique = true)
    private String naam;

    @Column(nullable = false, length = 80, unique = true)
    private String code;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA no-args constructor.
     */
    protected AutoriteittypeVanAfgifteReisdocument() {}

    /**
     * Maak een nieuwe soort partij.
     *
     * @param naam naam
     */
    public AutoriteittypeVanAfgifteReisdocument(final String naam) {
        setNaam(naam);
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
     * Zet de waarden voor id van AutoriteittypeVanAfgifteReisdocument.
     *
     * @param id de nieuwe waarde voor id van AutoriteittypeVanAfgifteReisdocument
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van AutoriteittypeVanAfgifteReisdocument.
     *
     * @return de waarde van naam van AutoriteittypeVanAfgifteReisdocument
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde voor naam van AutoriteittypeVanAfgifteReisdocument.
     *
     * @param naam de nieuwe waarde voor naam van AutoriteittypeVanAfgifteReisdocument
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van omschrijving van AutoriteittypeVanAfgifteReisdocument.
     *
     * @return de waarde van omschrijving van AutoriteittypeVanAfgifteReisdocument
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarde voor code van AutoriteittypeVanAfgifteReisdocument.
     *
     * @param code de nieuwe waarde voor code van AutoriteittypeVanAfgifteReisdocument
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van AutoriteittypeVanAfgifteReisdocument.
     *
     * @return de waarde van datum aanvang geldigheid van AutoriteittypeVanAfgifteReisdocument
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van AutoriteittypeVanAfgifteReisdocument.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van
     *        AutoriteittypeVanAfgifteReisdocument
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van AutoriteittypeVanAfgifteReisdocument.
     *
     * @return de waarde van datum einde geldigheid van AutoriteittypeVanAfgifteReisdocument
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van AutoriteittypeVanAfgifteReisdocument.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van
     *        AutoriteittypeVanAfgifteReisdocument
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }
}
