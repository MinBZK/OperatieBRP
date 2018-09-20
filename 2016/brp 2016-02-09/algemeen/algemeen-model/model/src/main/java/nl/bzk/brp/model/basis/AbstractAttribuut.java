/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Basis klasse voor alle attributen.
 *
 * @param <T> Het type van de waarde van het attribuut.
 */
@MappedSuperclass
public abstract class AbstractAttribuut<T> implements Attribuut<T>, Onderzoekbaar, Comparable<Attribuut<T>> {

    /**
     * Constante voor de naam van het waarde veld, dit is nodig omdat hiernaar verwijst wordt via de {@link javax.persistence.AssociationOverride}
     * annotaties in Entity klasen.
     */
    public static final String WAARDE_VELD_NAAM = "waarde";

    private static final int PRIME_BASE = 197;
    private static final int PRIME_ADD  = 227;

    // Protected vanwege JiBX binding.
    @Transient
    private T waarde;

    @Transient
    private boolean inOnderzoek;

    @Transient
    private boolean magGeleverdWorden;

    @Transient
    private Groep groep;

    /**
     * De (op dit moment) enige constructor voor de immutable attributen.
     *
     * @param waarde de waarde
     */
    protected AbstractAttribuut(final T waarde) {
        this.waarde = waarde;
    }

    @Override
    public T getWaarde() {
        return waarde;
    }

    /**
     * Functie t.b.v. Hibernate property access.
     * @param waarde de waarde
     */
    protected void setWaarde(final T waarde) {
        this.waarde = waarde;
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

    @Override
    public void setInOnderzoek(final boolean inOnderzoek) {
        this.inOnderzoek = inOnderzoek;
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return magGeleverdWorden;
    }

    @Override
    public void setMagGeleverdWorden(final boolean magGeleverdWorden) {
        this.magGeleverdWorden = magGeleverdWorden;
    }

    @Override
    public boolean heeftWaarde() {
        return this.waarde != null;
    }

    /**
     * Controleert op het attribuut geen waarde heeft.
     *
     * @return true als er geen waarde is
     */
    public boolean heeftGeenWaarde() {
        return this.waarde == null;
    }

    @Override
    public void setGroep(final Groep groep) {
        this.groep = groep;
    }

    @Override
    public Groep getGroep() {
        return this.groep;
    }

    /**
     * Vergelijk twee objecten, waarbij null is gelijk aan null.
     *
     * @param one de ene waarde
     * @param two de ander waarde
     * @return true als beide gelijk
     */
    protected boolean isEqual(final Object one, final Object two) {
        final boolean retval;
        if (one == null && two == null) {
            retval = true;
        } else if (one == null) {
            retval = false;
        } else {
            retval = one.equals(two);
        }
        return retval;
    }

    /**
     * Geeft een hash code builder.
     *
     * @return de hash code builder
     */
    private HashCodeBuilder maakHashCodeBuilder() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD).append(waarde);
    }

    /**
     * Vergelijk de waarde met de waarde van een ander attribuut.
     *
     * @param object de ander
     * @return true als gelijk (intern wordt waarde.equals() gebruikt), false anders
     * @see <a href="http://javanotepad.blogspot.nl/2007/09/instanceof-doesnt-work-with-generics.html"> instanceof-doesnt-work-with-generics</a>
     */
    @Override
    public boolean equals(final Object object) {
        final boolean retval;
        if (object == null || getClass() != object.getClass()) {
            retval = false;
        } else {
            @SuppressWarnings("unchecked")
            final Attribuut<T> that = (Attribuut<T>) object;
            retval = isEqual(this.waarde, that.getWaarde());
        }
        return retval;
    }

    @Override
    public int hashCode() {
        return maakHashCodeBuilder().toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Attribuut<T> o) {
        final int vergelijk;

        if (o == null || o.getWaarde() == null) {
            vergelijk = -1;
        } else if (waarde == null) {
            vergelijk = 1;
        } else {
            vergelijk = new CompareToBuilder().append(waarde, o.getWaarde()).toComparison();
        }
        return vergelijk;
    }

    @Override
    public String toString() {
        return waarde.toString();
    }
}
