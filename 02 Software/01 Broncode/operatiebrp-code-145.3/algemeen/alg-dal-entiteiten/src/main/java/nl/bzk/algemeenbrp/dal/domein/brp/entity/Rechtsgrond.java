/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the rechtsgrond database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "rechtsgrond", schema = "kern")
@NamedQuery(name = "Rechtsgrond" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Rechtsgrond")
public class Rechtsgrond extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 3;

    @Id
    @SequenceGenerator(name = "rechtsgrond_id_generator", sequenceName = "kern.seq_rechtsgrond", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rechtsgrond_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(unique = true, nullable = false, length = 3)
    private String code;

    @Column(name = "oms", nullable = false, length = 250)
    private String omschrijving;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    @Column(name = "indleidttotstrijdigheid")
    private Boolean indicatieLeidtTotStrijdigheid;

    /**
     * JPA default constructor.
     */
    protected Rechtsgrond() {
    }

    /**
     * Maak een nieuwe rechtsgrond.
     * @param code code
     * @param omschrijving omschrijving
     */
    public Rechtsgrond(final String code, final String omschrijving) {
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
     * Zet de waarden voor id van Rechtsgrond.
     * @param id de nieuwe waarde voor id van Rechtsgrond
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van Rechtsgrond.
     * @return de waarde van code van Rechtsgrond
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van Rechtsgrond.
     * @param code de nieuwe waarde voor code van Rechtsgrond
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte van 3 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van omschrijving van Rechtsgrond.
     * @return de waarde van omschrijving van Rechtsgrond
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van Rechtsgrond.
     * @param omschrijving de nieuwe waarde voor omschrijving van Rechtsgrond
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden("omschrijving mag niet null zijn", omschrijving);
        ValidationUtils.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van Rechtsgrond.
     * @return de waarde van datum aanvang geldigheid van Rechtsgrond
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van Rechtsgrond.
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van Rechtsgrond
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van Rechtsgrond.
     * @return de waarde van datum einde geldigheid van Rechtsgrond
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van Rechtsgrond.
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van Rechtsgrond
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van indicatieLeidtTotStrijdigheid.
     * @return indicatieLeidtTotStrijdigheid
     */
    public Boolean getIndicatieLeidtTotStrijdigheid() {
        return indicatieLeidtTotStrijdigheid;
    }

    /**
     * Zet de waarde van indicatieLeidtTotStrijdigheid.
     * @param indicatieLeidtTotStrijdigheid indicatieLeidtTotStrijdigheid
     */
    public void setIndicatieLeidtTotStrijdigheid(final Boolean indicatieLeidtTotStrijdigheid) {
        this.indicatieLeidtTotStrijdigheid = indicatieLeidtTotStrijdigheid;
    }
}
