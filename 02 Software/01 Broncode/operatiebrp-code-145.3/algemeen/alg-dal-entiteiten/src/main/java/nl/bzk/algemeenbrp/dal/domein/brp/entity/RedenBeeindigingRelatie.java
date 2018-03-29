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
 * The persistent class for the rdnbeeindrelatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "rdneinderelatie", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "RedenBeeindigingRelatie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from RedenBeeindigingRelatie")
public class RedenBeeindigingRelatie extends AbstractEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;
    private static final String OMSCHRIJVING_MAG_NIET_NULL_ZIJN = "omschrijving mag niet null zijn";
    private static final String OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN = "omschrijving mag geen lege string zijn";

    @Id
    @SequenceGenerator(name = "rdneinderelatie_id_generator", sequenceName = "kern.seq_rdneinderelatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rdneinderelatie_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(nullable = false, length = 1, unique = true)
    private char code;

    @Column(name = "oms", nullable = false, length = 250, unique = true)
    private String omschrijving;

    /**
     * JPA default constructor.
     */
    protected RedenBeeindigingRelatie() {}

    /**
     * Maak een nieuwe reden beeindiging relatie.
     *
     * @param code code
     * @param omschrijving omschrijving
     */
    public RedenBeeindigingRelatie(final char code, final String omschrijving) {
        this.code = code;
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        ValidationUtils.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
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
     * Zet de waarden voor id van RedenBeeindigingRelatie.
     *
     * @param id de nieuwe waarde voor id van RedenBeeindigingRelatie
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van RedenBeeindigingRelatie.
     *
     * @return de waarde van code van RedenBeeindigingRelatie
     */
    public char getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van RedenBeeindigingRelatie.
     *
     * @param code de nieuwe waarde voor code van RedenBeeindigingRelatie
     */
    public void setCode(final char code) {
        this.code = code;
    }

    /**
     * Geef de waarde van omschrijving van RedenBeeindigingRelatie.
     *
     * @return de waarde van omschrijving van RedenBeeindigingRelatie
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van RedenBeeindigingRelatie.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van RedenBeeindigingRelatie
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        ValidationUtils.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
    }

    /**
     * Als de code van twee RedenBeeindigingRelatie objecten gelijk zijn dan worden ze beschouwd als
     * inhoudelijk gelijk.
     *
     * @param andereRedenBeeindigingRelatie de andere RedenBeeindigingRelatie waarmee wordt
     *        vergeleken
     * @return true als de code gelijk is aan de code van de andere RedenBeeindigingRelatie
     */
    public boolean isInhoudelijkGelijkAan(final RedenBeeindigingRelatie andereRedenBeeindigingRelatie) {
        return this == andereRedenBeeindigingRelatie || andereRedenBeeindigingRelatie != null && getCode() == andereRedenBeeindigingRelatie.getCode();
    }
}
