/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * Conversietabel voor Soort Selectie.
 */
public class LeverwijzeSelectieConversietabel implements Conversietabel<String, LeverwijzeSelectie> {

    private static final String ALTERNATIEF_MEDIUM = "A";
    private static final String NETWERK = "N";

    @Override
    public LeverwijzeSelectie converteerNaarBrp(final String input) {
        final LeverwijzeSelectie result;
        if (input == null) {
            result = null;
        } else if (ALTERNATIEF_MEDIUM.equalsIgnoreCase(input)) {
            result = LeverwijzeSelectie.STANDAARD;
        } else if (NETWERK.equalsIgnoreCase(input)) {
            result = LeverwijzeSelectie.BERICHT;
        } else {
            throw new IllegalArgumentException("Ongeldige waarde voor lo3 medium selectie (voor conversie naar brp): " + input);
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final String input) {
        return input == null || input.length() == 1;
    }

    @Override
    public String converteerNaarLo3(final LeverwijzeSelectie input) {
        final String result;
        switch (input) {
            case STANDAARD:
                result = ALTERNATIEF_MEDIUM;
                break;
            case BERICHT:
                result = NETWERK;
                break;
            default:
                throw new IllegalArgumentException("Ongeldige waarde voor brp medium selectie (voor conversie naar lo3): " + input);
        }
        return result;
    }

    @Override
    public boolean valideerBrp(final LeverwijzeSelectie input) {
        // Alle boolean waarden zijn geldig
        return true;
    }
}
