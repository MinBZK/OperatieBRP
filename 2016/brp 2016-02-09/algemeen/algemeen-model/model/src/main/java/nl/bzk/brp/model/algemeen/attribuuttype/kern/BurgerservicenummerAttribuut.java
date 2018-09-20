/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Attribuut wrapper klasse voor Burgerservicenummer.
 */
@Embeddable
public class BurgerservicenummerAttribuut extends AbstractBurgerservicenummerAttribuut {

    private static final int MAX_LENGTE_BSN = 9;
    private static final int PRIME_BASE     = 197;
    private static final int PRIME_ADD      = 227;

    /**
     * Lege private constructor voor BurgerservicenummerAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private BurgerservicenummerAttribuut() {
        super();
    }

    /**
     * Constructor voor BurgerservicenummerAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public BurgerservicenummerAttribuut(final Integer waarde) {
        super(waarde);
    }

    /**
     * Extra constructor met een string als argument.
     *
     * @param waarde de waarde.
     */
    public BurgerservicenummerAttribuut(final String waarde) {
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
        final BurgerservicenummerAttribuut rhs = (BurgerservicenummerAttribuut) obj;
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
