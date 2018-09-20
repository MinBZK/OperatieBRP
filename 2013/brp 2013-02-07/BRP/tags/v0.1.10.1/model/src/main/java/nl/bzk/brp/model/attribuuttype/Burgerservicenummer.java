/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.basis.Nullable;
import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.basis.SoortNull;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/** Burgerservicenummer. */
@Embeddable
@Access(AccessType.PROPERTY)
public final class Burgerservicenummer implements AttribuutType<String>, Nullable, Onderzoekbaar {

    private static final int MAX_LENGTE_BSN = 9;
    private static final int PRIME_BASE     = 197;
    private static final int PRIME_ADD      = 227;

    private String    waarde;
    private boolean   inOnderzoek;
    private SoortNull soortNull;


    /** Private constructor t.b.v. Hibernate. */
    private Burgerservicenummer() {
    }

    /**
     * De basis constructor waarbij geen controle wordt uitgevoerd op de waarde en de waarde in principe dus alles
     * kan zijn (zo ook bijvoorbeeld tekst).
     *
     * @param waarde de waarde
     */
    public Burgerservicenummer(final String waarde) {
        this(waarde, false, null);
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
    public Burgerservicenummer(final String waarde, final boolean inOnderzoek, final SoortNull soortNull) {
        this.waarde = waarde;
        this.inOnderzoek = inOnderzoek;
        this.soortNull = soortNull;
    }

    /**
     * Retourneert de waarde van het burgerservicenummer.
     *
     * @return de waarde van het burgerservicenummer.
     */
    @Transient
    @Override
    public String getWaarde() {
        return waarde;
    }

    /**
     * Retourneert de waarde van het burgerservicenummer als getal.
     *
     * @return de waarde van het burgerservicenummer als getal.
     */
    @Column(name = "waarde")
    protected Integer getWaardeAlsInteger() {
        final Integer bsnAlsInteger;
        if (waarde == null || StringUtils.isEmpty(waarde)) {
            bsnAlsInteger = null;
        } else {
            bsnAlsInteger = Integer.parseInt(waarde);
        }
        return bsnAlsInteger;
    }

    /**
     * Zet de waarde van het burgerservicenummer op basis van een getal. Hiervoor wordt de waarde eventueel aangevuld
     * met nullen tot de vereiste 9 cijfers.
     *
     * @param waardeAlsInteger de waarde van het burgerservicenummer als getal.
     */
    protected void setWaardeAlsInteger(final Integer waardeAlsInteger) {
        waarde = waardeAlsInteger.toString();
        waarde = StringUtils.leftPad(waarde, MAX_LENGTE_BSN, "0");
    }

    /**
     * Retourneert of dit burgerservicenummer in onderzoek staat.
     *
     * @return of dit burgerservicenummer in onderzoek staat.
     */
    @Transient
    @Override
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
        return new EqualsBuilder().append(waarde, rhs.waarde).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD).append(waarde).toHashCode();
    }
}