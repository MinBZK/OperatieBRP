/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.basis.BurgerservicenummerBasis;
import nl.bzk.brp.model.basis.Nullable;
import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.basis.SoortNull;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Burgerservicenummer.
 */
@Embeddable
public final class Burgerservicenummer extends BurgerservicenummerBasis implements Nullable, Onderzoekbaar {

    private static final int MAX_LENGTE_BSN = 9;
    private static final int PRIME_BASE     = 197;
    private static final int PRIME_ADD      = 227;

    private boolean   inOnderzoek;
    private SoortNull soortNull;
    /**
     * Private constructor t.b.v. Hibernate.
     */
    private Burgerservicenummer() {
        super(null);
    }

    /**
     * Constructor voor burgerservicenummer met een string om tijdelijk de integratie makkelijker te maken.
     *
     * @deprecated
     * @param waarde de waarde
     */
    @Deprecated
    public Burgerservicenummer(final String waarde) {
        super(Integer.valueOf(waarde));
    }
    /**
     * Constructor waarbij geen controle wordt uitgevoerd op de waarde en de waarde in principe dus alles
     * kan zijn (zo ook bijvoorbeeld tekst). Daarnaast wordt in deze constructor ook direct gezet of het attribuut in
     * onderzoek is en/of de reden waarom de waarde <code>null</code> is.
     *
     * @param waarde de waarde
     * @param inOnderzoek of de waarde in onderzoek staat
     * @param soortNull de reden waarom de waarde nuill is.
     */
    public Burgerservicenummer(final Integer waarde, final boolean inOnderzoek, final SoortNull soortNull) {
        super(waarde);
        this.inOnderzoek = inOnderzoek;
        this.soortNull = soortNull;
    }

    /**
     * Retourneert of dit burgerservicenummer in onderzoek staat.
     *
     * @return of dit burgerservicenummer in onderzoek staat.
     */
    @Override
    @Transient
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

    /**
     * Retourneert de reden waarom het burgerservicenummer <code>null</code> is.
     *
     * @return de reden waarom het burgerservicenummer <code>null</code> is.
     */
    @Transient
    @Override
    public SoortNull getNullWaarde() {
        return soortNull;
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
        return new EqualsBuilder().append(getWaarde(), rhs.getWaarde()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD).append(getWaarde()).toHashCode();
    }

    @Override
    public String toString() {
        return String.format("%09d", getWaarde());
    }


}