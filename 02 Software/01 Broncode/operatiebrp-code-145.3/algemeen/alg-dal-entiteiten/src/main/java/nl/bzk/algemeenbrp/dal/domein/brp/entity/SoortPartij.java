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
 * The persistent class for the srtpartij database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "srtpartij", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "SoortPartij" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from SoortPartij")
public class SoortPartij extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "srtpartij_id_generator", sequenceName = "kern.seq_srtpartij", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "srtpartij_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(nullable = false, length = 80, unique = true)
    private String naam;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA no-args constructor.
     */
    protected SoortPartij() {}

    /**
     * Maak een nieuwe soort partij.
     *
     * @param naam naam
     */
    public SoortPartij(final String naam) {
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
     * Zet de waarden voor id van SoortPartij.
     *
     * @param id de nieuwe waarde voor id van SoortPartij
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van SoortPartij.
     *
     * @return de waarde van naam van SoortPartij
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van SoortPartij.
     *
     * @param naam de nieuwe waarde voor naam van SoortPartij
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van SoortPartij.
     *
     * @return de waarde van datum aanvang geldigheid van SoortPartij
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van SoortPartij.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van SoortPartij
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van SoortPartij.
     *
     * @return de waarde van datum einde geldigheid van SoortPartij
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van SoortPartij.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van SoortPartij
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }
}
