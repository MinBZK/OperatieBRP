/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.basis.AbstractBurgerservicenummer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;



/**
 *
 *
 */
@Embeddable
public class Burgerservicenummer extends AbstractBurgerservicenummer {

    private static final int MAX_LENGTE_BSN = 9;
    private static final int PRIME_BASE     = 197;
    private static final int PRIME_ADD      = 227;

    /**
     * Lege (value-object) constructor voor Burgerservicenummer, niet gedeclareerd als public om te voorkomen dat
     * objecten
     * zonder waarde worden geïnstantieerd.
     *
     */
    private Burgerservicenummer() {
        super();
    }

    /**
     * Constructor voor Burgerservicenummer die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    @JsonCreator
    public Burgerservicenummer(final Integer waarde) {
        super(waarde);
    }


    /**
     * Extra constructor met een string als argument.
     *
     * @param waarde de waarde.
     */
    public Burgerservicenummer(final String waarde) {
        super(Integer.parseInt(waarde));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Burgerservicenummer rhs = (Burgerservicenummer) obj;
        return new EqualsBuilder().append(this.getWaarde(), rhs.getWaarde()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD).append(this.getWaarde()).toHashCode();
    }

    @Override
    public String toString() {
        String resultaat;
        if (this.getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = this.getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, MAX_LENGTE_BSN, '0');
        }
        return resultaat;
    }

}
