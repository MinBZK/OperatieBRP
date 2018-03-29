/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Dit element wordt gebruikt voor alle BMR attributen die karakter (string lengte == 1) waardes bevatten.
 */
public final class CharacterElement extends AbstractBmrAttribuut<Character> {
    /**
     * Maakt een nieuw {@link CharacterElement} object.
     * 
     * @param waarde
     *            waarde, mag niet null zijn
     */
    public CharacterElement(final Character waarde) {
        super(waarde);
    }

    /**
     * Maakt een {@link CharacterElement} obv de gegeven string.
     *
     * @param waarde
     *            de waarde, mag niet null zijn
     * @return {@link CharacterElement}
     * @throws OngeldigeWaardeException
     *             wanneer de gegeven string een ongeldige waarde bevat
     */
    public static CharacterElement parseWaarde(final String waarde) throws OngeldigeWaardeException {
        ValidatieHelper.controleerOpNullWaarde(waarde, "waarde");
        if (waarde.length() != 1) {
            throw new OngeldigeWaardeException(String.format("de waarde '%s' is geen geldig karakter ", waarde));
        }
        return new CharacterElement(waarde.charAt(0));
    }

    @Override
    public String toString() {
        return getWaarde().toString();
    }
}
