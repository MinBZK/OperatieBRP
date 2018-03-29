/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the autvanafgifteblpersnr database table.
 */
@Entity
@Table(name = "autvanafgifteblpersnr", schema = "kern")
@NamedQuery(name = "AutoriteitAfgifteBuitenlandsPersoonsnummer" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "SELECT a FROM AutoriteitAfgifteBuitenlandsPersoonsnummer a")
public class AutoriteitAfgifteBuitenlandsPersoonsnummer implements DynamischeStamtabel, Serializable {
    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 4;

    @Id
    @SequenceGenerator(name = "autvanafgifteblpersnr_id_generator", sequenceName = "autaut.seq_autvanafgifteblpersnr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autvanafgifteblpersnr_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "code", nullable = false, length = 4)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landgebied")
    private LandOfGebied landOfGebied;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nation")
    private Nationaliteit nationaliteit;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    /**
     * JPA no-args constructor.
     */
    protected AutoriteitAfgifteBuitenlandsPersoonsnummer() {}

    /**
     * Maakt een nieuw AutoriteitAfgifteBuitenlandsPersoonsnummer object.
     *
     * @param code code, mag niet null zijn
     */
    public AutoriteitAfgifteBuitenlandsPersoonsnummer(final String code) {
        setCode(code);
    }

    /**
     * Geef de waarde van id van Bijhoudingsautorisatie.
     *
     * @return de waarde van id van Bijhoudingsautorisatie
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Bijhoudingsautorisatie.
     *
     * @param id de nieuwe waarde voor id van Bijhoudingsautorisatie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code.
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarde van code.
     *
     * @param code code
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte hebben van 4", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van landOfGebied.
     *
     * @return landOfGebied
     */
    public LandOfGebied getLandOfGebied() {
        return landOfGebied;
    }

    /**
     * Zet de waarde van landOfGebied.
     *
     * @param landOfGebied landOfGebied
     */
    public void setLandOfGebied(final LandOfGebied landOfGebied) {
        this.landOfGebied = landOfGebied;
    }

    /**
     * Geef de waarde van nationaliteit.
     *
     * @return nationaliteit
     */
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    /**
     * Zet de waarde van nationaliteit.
     *
     * @param nationaliteit nationaliteit
     */
    public void setNationaliteit(final Nationaliteit nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    /**
     * Geef de waarde van datumAanvangGeldigheid.
     *
     * @return datumAanvangGeldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarde van datumAanvangGeldigheid.
     *
     * @param datumAanvangGeldigheid datumAanvangGeldigheid
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datumEindeGeldigheid.
     *
     * @return datumEindeGeldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarde van datumEindeGeldigheid.
     *
     * @param datumEindeGeldigheid datumEindeGeldigheid
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }
}
