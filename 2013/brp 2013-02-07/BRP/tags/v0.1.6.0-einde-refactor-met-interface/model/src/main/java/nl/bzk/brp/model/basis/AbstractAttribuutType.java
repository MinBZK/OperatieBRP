/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Basis klasse voor alle attributen.
 * @param <T> Het basis type van het attribuut.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
abstract class AbstractAttribuutType<T> implements AttribuutType<T> {
    private static final int PRIME_BASE = 197;
    private static final int PRIME_ADD = 227;

    private final T waarde;

    /**
     * De (op dit moment) enige constructor voor de immutable AttribuutType classen.
     * @param waarde de waarde
     */
    protected AbstractAttribuutType(final T waarde) {
        this.waarde = waarde;
    }

    @Override
    public T getWaarde() {
        return waarde;
    }

    /**
     * Vergelijk twee objecten, waarbij nul is gelijk aan nul.
     * @param one de ene waarde
     * @param two de ander waarde
     * @return true als beide gelijk
     */
    protected boolean isEqual(final Object one, final Object two) {
        boolean retval = true;
        if (one == null && two == null) {
            retval = true;
        } else if (one == null) {
            retval = false;
        } else {
            retval = one.equals(two);
        }
        return retval;
    }

    protected HashCodeBuilder getHashCodeBuilder() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD)
            .append(waarde);
    }

    /**
     * Vergelijk de waarde met de waarde van een ander attribuuttype.
     * {@linkplain http://javanotepad.blogspot.nl/2007/09/instanceof-doesnt-work-with-generics.html}
     * @param object de ander
     * @return true als gelijk (intern wordt waarde.equals() gebruikt), false anders
     */
    @Override
    public boolean equals(final Object object) {
        boolean retval = true;
        if ((object == null) || (getClass() != object.getClass())) {
            retval =  false;
        } else {
            @SuppressWarnings("unchecked")
            final AbstractAttribuutType<T> that = (AbstractAttribuutType<T>) object;
            retval = isEqual(this.waarde, that.waarde);
        }
        return retval;
    }


    @Override
    public int hashCode() {
        return getHashCodeBuilder().toHashCode();
    }
}
