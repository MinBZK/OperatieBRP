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
 * The persistent class for the srtnlreisdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "srtnlreisdoc", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "SoortNederlandsReisdocument" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from SoortNederlandsReisdocument")
public class SoortNederlandsReisdocument extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "srtnlreisdoc_id_generator", sequenceName = "kern.seq_srtnlreisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "srtnlreisdoc_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(nullable = false, length = 2, unique = true)
    private String code;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    @Column(name = "oms", nullable = false, length = 250)
    private String omschrijving;

    /**
     * JPA default constructor.
     */
    protected SoortNederlandsReisdocument() {}

    /**
     * Maak een nieuwe soort nederlands reisdocument.
     *
     * @param code code
     * @param omschrijving omschrijving
     */
    public SoortNederlandsReisdocument(final String code, final String omschrijving) {
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
     * Zet de waarden voor id van SoortNederlandsReisdocument.
     *
     * @param id de nieuwe waarde voor id van SoortNederlandsReisdocument
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van SoortNederlandsReisdocument.
     *
     * @return de waarde van code van SoortNederlandsReisdocument
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van SoortNederlandsReisdocument.
     *
     * @param code de nieuwe waarde voor code van SoortNederlandsReisdocument
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLegeWaarden("code mag geen lege string zijn", code);
        this.code = code;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van SoortNederlandsReisdocument.
     *
     * @return de waarde van datum aanvang geldigheid van SoortNederlandsReisdocument
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van SoortNederlandsReisdocument.
     *
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van
     *        SoortNederlandsReisdocument
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van SoortNederlandsReisdocument.
     *
     * @return de waarde van datum einde geldigheid van SoortNederlandsReisdocument
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van SoortNederlandsReisdocument.
     *
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van
     *        SoortNederlandsReisdocument
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van omschrijving van SoortNederlandsReisdocument.
     *
     * @return de waarde van omschrijving van SoortNederlandsReisdocument
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van SoortNederlandsReisdocument.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van SoortNederlandsReisdocument
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden("omschrijving mag niet null zijn", omschrijving);
        ValidationUtils.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }

    /**
     * Controleert of de meegegeven {@link SoortNederlandsReisdocument} inhoudelijk gelijk is aan
     * aan deze instantie.
     * 
     * @param andere
     * @return true als de meegegeven instantie gelijk is aan deze instantie
     */
    public boolean isInhoudelijkGelijkAan(final SoortNederlandsReisdocument andere) {
        return code.equals(andere.code);
    }
}
