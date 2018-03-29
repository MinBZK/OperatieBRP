/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class om een match tussen 2 delta entiteiten vast te leggen. Deze class dwingt af dat een bestaande
 * {@link Entiteit} een id moet bevatten.
 */
public final class DeltaEntiteitPaar {

    private final Entiteit bestaand;
    private final Entiteit nieuw;
    private boolean wordtBestaandMRij;

    /**
     * Constructor voor {@link DeltaEntiteitPaar}. Deze constructor controleert of beide input niet null is en of de
     * bestaande {@link Entiteit} een ID bevat (uit een database komt).
     * @param bestaand de (in database) bestaande entiteit
     * @param nieuw de (in memory opgebouwde) nieuwe entiteit
     * @throws NullPointerException als 1 of beide input null is
     * @throws IllegalArgumentException als bestaand entiteit geen ID bevat
     */
    public DeltaEntiteitPaar(final Entiteit bestaand, final Entiteit nieuw) {
        ValidationUtils.controleerOpNullWaarden("Input mag niet null zijn", bestaand, nieuw);
        this.bestaand = bestaand;
        this.nieuw = nieuw;
    }

    /**
     * Geef de waarde van nieuw van EntiteitPaar.
     * @return de waarde van nieuw van EntiteitPaar
     */
    public Entiteit getNieuw() {
        return nieuw;
    }

    /**
     * Geef de waarde van bestaand van EntiteitPaar.
     * @return de waarde van bestaand van EntiteitPaar
     */
    public Entiteit getBestaand() {
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

        final DeltaEntiteitPaar otherEntiteitPaar = (DeltaEntiteitPaar) obj;
        return new EqualsBuilder().append(bestaand, otherEntiteitPaar.bestaand).append(nieuw, otherEntiteitPaar.nieuw).isEquals();
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
     * Demarkeer dat de bestaande delta entiteit geen M-rij gaat worden tijdens deltabepaling.
     */
    public void demarkeerBestaandAlsMRij() {
        this.wordtBestaandMRij = false;
    }

    /**
     * @return true als de bestaande delta entiteit een M-rij gaat worden
     */
    public boolean wordtBestaandMRij() {
        return wordtBestaandMRij;
    }
}
