/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class om een match tussen 2 delta entiteiten vast te leggen. Deze class dwingt af dat een bestaande
 * {@link DeltaEntiteit} een id moet bevatten.
 */
public final class DeltaEntiteitPaar {

    private final DeltaEntiteit bestaand;
    private final DeltaEntiteit nieuw;
    private boolean wordtBestaandMRij;

    /**
     * Constructor voor {@link DeltaEntiteitPaar}. Deze constructor controleert of beide input niet null is en of de
     * bestaande {@link DeltaEntiteit} een ID bevat (uit een database komt).
     *
     * @param bestaand
     *            de (in database) bestaande entiteit
     * @param nieuw
     *            de (in memory opgebouwde) nieuwe entiteit
     * @throws NullPointerException
     *             als 1 of beide input null is
     * @throws IllegalArgumentException
     *             als bestaand entiteit geen ID bevat
     */
    public DeltaEntiteitPaar(final DeltaEntiteit bestaand, final DeltaEntiteit nieuw) {
        ValidationUtils.controleerOpNullWaarden("Input mag niet null zijn", bestaand, nieuw);
        this.bestaand = bestaand;
        this.nieuw = nieuw;
    }

    /**
     * Geef de nieuwe delta entiteit.
     * 
     * @return de nieuwe delta entiteit.
     */
    public DeltaEntiteit getNieuw() {
        return nieuw;
    }

    /**
     * Geef de bestaande delta entiteit.
     * 
     * @return de bestaande delta entiteit
     */
    public DeltaEntiteit getBestaand() {
        return bestaand;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DeltaEntiteitPaar)) {
            return false;
        }

        final DeltaEntiteitPaar otherDeltaEntiteitPaar = (DeltaEntiteitPaar) obj;
        return new EqualsBuilder().append(bestaand, otherDeltaEntiteitPaar.bestaand).append(nieuw, otherDeltaEntiteitPaar.nieuw).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bestaand).append(nieuw).toHashCode();
    }

    @Override
    public String toString() {
        return getBestaand().getClass().getSimpleName();
    }

    /**
     * Markeer dat de bestaande delta entiteit een M-rij gaat worden tijdens deltabepaling.
     */
    public void markeerBestaandAlsMRij() {
        this.wordtBestaandMRij = true;
    }

    /**
     * @return true als de bestaande delta entiteit een M-rij gaat worden
     */
    public boolean wordtBestaandMRij() {
        return wordtBestaandMRij;
    }
}
