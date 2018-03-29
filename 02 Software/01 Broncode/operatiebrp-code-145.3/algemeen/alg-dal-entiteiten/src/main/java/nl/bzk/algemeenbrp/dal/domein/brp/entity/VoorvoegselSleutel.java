/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Dit is de samengestelde sleutel voor {@link Voorvoegsel}.
 */
@Embeddable

public class VoorvoegselSleutel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(updatable = false, nullable = false)
    private char scheidingsteken;

    @Column(updatable = false, nullable = false)
    private String voorvoegsel;

    /**
     * JPA default constructor.
     */
    protected VoorvoegselSleutel() {}

    /**
     * Maak een nieuw paar van voorvoegsel/scheidingsteken.
     *
     * @param scheidingsteken het scheidingsteken
     * @param voorvoegsel het voorvoegsel
     */
    public VoorvoegselSleutel(final char scheidingsteken, final String voorvoegsel) {
        setScheidingsteken(scheidingsteken);
        setVoorvoegsel(voorvoegsel);
    }

    /**
     * Geef de waarde van scheidingsteken van Voorvoegsel.
     *
     * @return de waarde van scheidingsteken van Voorvoegsel
     */
    public char getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van Voorvoegsel.
     *
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van Voorvoegsel
     */
    public void setScheidingsteken(final char scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van voorvoegsel van Voorvoegsel.
     *
     * @return de waarde van voorvoegsel van Voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van Voorvoegsel.
     *
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van Voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpNullWaarden("voorvoegsel mag niet null zijn", voorvoegsel);
        ValidationUtils.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            final VoorvoegselSleutel other = (VoorvoegselSleutel) obj;
            return new EqualsBuilder().append(scheidingsteken, other.scheidingsteken).append(voorvoegsel, other.voorvoegsel).isEquals();

        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(scheidingsteken).append(voorvoegsel).toHashCode();
    }
}
