/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Type voor alle gegevensattributen die eventueel in onderzoek kunnen staan of een null waarde kunnen hebben.
 *
 * @param <T> Basis type van het attribuut.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGegevensAttribuutType<T> extends nl.bzk.copy.model.basis.AbstractAttribuutType<T>
        implements Nullable,
        Onderzoekbaar
{

    @Transient
    private boolean inOnderzoek;

    @Transient
    private SoortNull soortNull;

    /**
     * De (op dit moment) enige constructor voor de immutable AttribuutType classen.
     *
     * @param waarde de waarde
     */
    protected AbstractGegevensAttribuutType(final T waarde) {
        this(waarde, false, null);
    }

    /**
     * Ondersteunend constructor met 3 parameters.
     *
     * @param waarde      de waarde
     * @param inOnderzoek attribbut is in onderzoek
     * @param soortNull   welk type null is dit.
     */
    protected AbstractGegevensAttribuutType(final T waarde, final boolean inOnderzoek, final SoortNull soortNull) {
        super(waarde);
        this.inOnderzoek = inOnderzoek;
        this.soortNull = soortNull;
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

    @Override
    public SoortNull getNullWaarde() {
        return soortNull;
    }

    @Override
    public boolean equals(final Object object) {
        boolean retval = super.equals(object);
        if (retval) {
            @SuppressWarnings("unchecked")
            final AbstractGegevensAttribuutType<T> that = (AbstractGegevensAttribuutType<T>) object;
            retval = (isEqual(this.inOnderzoek, that.inOnderzoek) && isEqual(this.soortNull, that.soortNull));
        }
        return retval;
    }

    @Override
    protected HashCodeBuilder getHashCodeBuilder() {
        return super.getHashCodeBuilder()
                    .append(inOnderzoek)
                    .append(soortNull);
    }

    @Override
    public int hashCode() {
        return getHashCodeBuilder().toHashCode();
    }

}
