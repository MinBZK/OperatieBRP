/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Bevat de basis functionaliteit voor een BmrAttribuut.
 *
 * @param <T> het type van de waarde van dit attribuut
 */
public abstract class AbstractBmrAttribuut<T> extends AbstractElement implements BmrAttribuut<T> {

    private final T waarde;

    /**
     * Maakt een nieuw AbstractBmrAttribuut object.
     *
     * @param waarde de waarde van het attribuut, mag niet null zijn
     */
    protected AbstractBmrAttribuut(final T waarde) {
        ValidatieHelper.controleerOpNullWaarde(waarde, "waarde");
        this.waarde = waarde;
    }

    @Override
    public final T getWaarde() {
        return waarde;
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other || !(other == null || getClass() != other.getClass()) && getWaarde().equals(((BmrAttribuut) other).getWaarde());
    }

    @Override
    public final int hashCode() {
        return waarde.hashCode();
    }
}
