/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * De conversietabel voor de converie van 'Woonplaatsnaam' naar 'BRP woonplaatsnaam' en vice versa.
 */
public class WoonplaatsnaamConversietabel implements Conversietabel<String, String> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final String LO3_NIET_VALIDE_UITZONDERING = "xx";

    @Override
    public String converteerNaarBrp(final String input) {
        return input;
    }

    @Override
    public String converteerNaarLo3(final String input) {
        return input;
    }

    @Override
    public boolean valideerLo3(final String input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final String input) {
        return true;
    }

}
