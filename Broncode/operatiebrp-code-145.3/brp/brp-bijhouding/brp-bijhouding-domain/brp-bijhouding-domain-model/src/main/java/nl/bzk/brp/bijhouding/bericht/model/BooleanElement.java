/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Dit element wordt gebruikt voor alle BMR attributen die J (Ja) of N (Nee) waarden bevatten.
 */
public final class BooleanElement extends AbstractBmrAttribuut<Boolean> {

    /** Ja. */
    public static final BooleanElement JA = new BooleanElement(Boolean.TRUE);
    /** Nee. */
    public static final BooleanElement NEE = new BooleanElement(Boolean.FALSE);

    private static final String STRING_JA = "J";
    private static final String STRING_NEE = "N";

    /**
     * Maakt een nieuw BooleanElement object.
     *
     * @param waarde de waarde van het attribuut, mag niet null zijn
     */
    public BooleanElement(final Boolean waarde) {
        super(waarde);
    }

    /**
     * Maakt een BooleanElement obv de gegeven string.
     *
     * @param waarde de waarde, mag niet null zijn
     * @return BooleanElement
     * @throws OngeldigeWaardeException wanneer de gegeven string een ongeldige waarde bevat
     */
    public static BooleanElement parseWaarde(final String waarde) throws OngeldigeWaardeException {
        ValidatieHelper.controleerOpNullWaarde(waarde, "waarde");
        switch (waarde) {
            case STRING_JA:
                return JA;
            case STRING_NEE:
                return NEE;
            default:
                throw new OngeldigeWaardeException(String.format("De waarde '%s' is geen geldige Ja / Nee waarde.", waarde));
        }
    }

    @Override
    public String toString() {
        if (getWaarde()) {
            return STRING_JA;
        } else {
            return STRING_NEE;
        }
    }
}
