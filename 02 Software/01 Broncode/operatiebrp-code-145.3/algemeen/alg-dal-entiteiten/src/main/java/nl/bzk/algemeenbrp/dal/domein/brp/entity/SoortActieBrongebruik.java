/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * De persistent class voor database tabel SrtActieBrongebruik.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "srtactiebrongebruik", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"srtactie", "srtadmhnd", "srtdoc"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "SoortActieBrongebruik" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from SoortActieBrongebruik")
public class SoortActieBrongebruik implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "srtactiebrongebruik_id_generator", sequenceName = "kern.seq_srtactiebrongebruik", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "srtactiebrongebruik_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Embedded
    private SoortActieBrongebruikSleutel soortActieBrongebruikSleutel;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA default constructor.
     */
    protected SoortActieBrongebruik() {}

    /**
     * Maakt een nieuw SoortActieBrongebruik object.
     *
     * @param soortActieBrongebruikSleutel de samengestelde sleutel voor deze entiteit
     */
    public SoortActieBrongebruik(final SoortActieBrongebruikSleutel soortActieBrongebruikSleutel) {
        setSoortActieBrongebruikSleutel(soortActieBrongebruikSleutel);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.DynamischeStamtabel#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van SoortActieBrongebruik.
     *
     * @param id de nieuwe waarde voor id van SoortActieBrongebruik
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * @return geeft de samengestelde sleutel voor deze entiteit terug
     */
    public SoortActieBrongebruikSleutel getSoortActieBrongebruikSleutel() {
        return soortActieBrongebruikSleutel;
    }

    /**
     * Zet de samengestelde sleutel voor deze entiteit.
     *
     * @param soortActieBrongebruikSleutel de samengestelde sleutel
     */
    public void setSoortActieBrongebruikSleutel(final SoortActieBrongebruikSleutel soortActieBrongebruikSleutel) {
        ValidationUtils.controleerOpNullWaarden("SoortActieBrongebruikSleutel mag niet null zijn", soortActieBrongebruikSleutel);
        this.soortActieBrongebruikSleutel = soortActieBrongebruikSleutel;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van SoortActieBrongebruik.
     *
     * @return de waarde van datum aanvang geldigheid van SoortActieBrongebruik
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van SoortActieBrongebruik.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van
     *        SoortActieBrongebruik
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van SoortActieBrongebruik.
     *
     * @return de waarde van datum einde geldigheid van SoortActieBrongebruik
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van SoortActieBrongebruik.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van
     *        SoortActieBrongebruik
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }
}
