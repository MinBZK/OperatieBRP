/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Dit element wordt gebruikt voor alle BMR attributen die integer waardes bevatten.
 */
public final class IntegerElement extends AbstractBmrAttribuut<Integer> {

    /**
     * Maakt een nieuw IntegerElement object.
     * @param waarde waarde, mag niet null zijn
     */
    public IntegerElement(final Integer waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        return getWaarde().toString();
    }

    /**
     * Maakt een {@link IntegerElement} obv de gegeven string.
     * @param waarde de waarde, mag niet null zijn
     * @return IntegerElement ee nieuw IntegerElement
     * @throws OngeldigeWaardeException wanneer de gegeven string een ongeldige waarde bevat
     */
    public static IntegerElement parseWaarde(final String waarde) throws OngeldigeWaardeException {
        ValidatieHelper.controleerOpNullWaarde(waarde, "waarde");
        try {
            return new IntegerElement(Integer.parseInt(waarde));
        } catch (NumberFormatException e) {
            throw new OngeldigeWaardeException(String.format("De waarde '%s' is geen geldig IntegerElement.", waarde), e);
        }
    }
}
