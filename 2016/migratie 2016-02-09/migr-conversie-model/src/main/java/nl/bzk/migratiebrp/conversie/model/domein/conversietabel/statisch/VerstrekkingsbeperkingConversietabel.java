/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * Verstrekkingsbeperking conversie tabel.
 */
public final class VerstrekkingsbeperkingConversietabel implements Conversietabel<Integer, BrpProtocolleringsniveauCode> {

    @Override
    public BrpProtocolleringsniveauCode converteerNaarBrp(final Integer input) {
        if (input == null) {
            return null;
        }

        final BrpProtocolleringsniveauCode result;
        switch (input) {
            case 0:
            case 1:
                result = BrpProtocolleringsniveauCode.GEEN_BEPERKINGEN;
                break;
            case 2:
                result = BrpProtocolleringsniveauCode.GEHEIM;
                break;
            default:
                throw new IllegalArgumentException(String.format("Kan de LO3 verstrekkingsbeperking '%s' niet converteren naar BRP", input.toString()));
        }
        return result;
    }

    @Override
    public Integer converteerNaarLo3(final BrpProtocolleringsniveauCode input) {
        if (input == null) {
            return null;
        }

        return Integer.valueOf(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Integer input) {
        return input == null || input >= 0 && input <= 2;
    }

    @Override
    public boolean valideerBrp(final BrpProtocolleringsniveauCode input) {
        // Enum
        return true;
    }
}
