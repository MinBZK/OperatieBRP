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
 * The persistent class for the soortnlreisdocument database table.
 */
@Entity
@Table(name = "convvoorvoegsel", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "VoorvoegselConversie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from VoorvoegselConversie")
public class VoorvoegselConversie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LO3_VOORVOEGSEL_MAG_NIET_NULL_ZIJN = "lo3Voorvoegsel mag niet null zijn";
    private static final String LO3_VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN = "lo3Voorvoegsel mag geen lege string zijn";
    private static final String VOORVOEGSEL_MAG_NIET_NULL_ZIJN = "voorvoegsel mag niet null zijn";
    private static final String VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN = "voorvoegsel mag geen lege string zijn";

    @Id
    @SequenceGenerator(name = "convvoorvoegsel_id_generator", sequenceName = "conv.seq_convvoorvoegsel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convvoorvoegsel_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr0231voorvoegsel", length = 10, nullable = false)
    private String lo3Voorvoegsel;

    @Column(name = "voorvoegsel", length = 80, nullable = false)
    private String voorvoegsel;

    @Column(name = "scheidingsteken", length = 1, nullable = false)
    private char scheidingsteken;

    /**
     * JPA default constructor.
     */
    protected VoorvoegselConversie() {}

    /**
     * Maak een nieuwe voorvoegsel conversie.
     *
     * @param lo3Voorvoegsel lo3 voorvoegsel
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     */
    public VoorvoegselConversie(final String lo3Voorvoegsel, final String voorvoegsel, final char scheidingsteken) {
        setLo3Voorvoegsel(lo3Voorvoegsel);
        setVoorvoegsel(voorvoegsel);
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van id van VoorvoegselConversie.
     *
     * @return de waarde van id van VoorvoegselConversie
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van VoorvoegselConversie.
     *
     * @param id de nieuwe waarde voor id van VoorvoegselConversie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 voorvoegsel van VoorvoegselConversie.
     *
     * @return de waarde van lo3 voorvoegsel van VoorvoegselConversie
     */
    public String getLo3Voorvoegsel() {
        return lo3Voorvoegsel;
    }

    /**
     * Zet de waarden voor lo3 voorvoegsel van VoorvoegselConversie.
     *
     * @param lo3Voorvoegsel de nieuwe waarde voor lo3 voorvoegsel van VoorvoegselConversie
     */
    public void setLo3Voorvoegsel(final String lo3Voorvoegsel) {
        ValidationUtils.controleerOpNullWaarden(LO3_VOORVOEGSEL_MAG_NIET_NULL_ZIJN, lo3Voorvoegsel);
        ValidationUtils.controleerOpLegeWaarden(LO3_VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, lo3Voorvoegsel);
        this.lo3Voorvoegsel = lo3Voorvoegsel;
    }

    /**
     * Geef de waarde van voorvoegsel van VoorvoegselConversie.
     *
     * @return de waarde van voorvoegsel van VoorvoegselConversie
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van VoorvoegselConversie.
     *
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van VoorvoegselConversie
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpNullWaarden(VOORVOEGSEL_MAG_NIET_NULL_ZIJN, voorvoegsel);
        ValidationUtils.controleerOpLegeWaarden(VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van VoorvoegselConversie.
     *
     * @return de waarde van scheidingsteken van VoorvoegselConversie
     */
    public char getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van VoorvoegselConversie.
     *
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van VoorvoegselConversie
     */
    public void setScheidingsteken(final char scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }
}
