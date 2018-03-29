/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * Conversietbabel voor berichtaanduiding.
 */
public class BerichtaanduidingConversietabel implements Conversietabel<Integer, Boolean> {

    @Override
    public Boolean converteerNaarBrp(Integer input) {
        if (input == null || input == 0) {
            return null;
        } else if(input == 1) {
            return true;
        } else {
            throw new IllegalArgumentException("Ongeldige waarde voor conversie naar brp: " + input);
        }
    }

    @Override
    public boolean valideerLo3(Integer input) {
        return input == null || (input >= 0 && input <=1);
    }

    @Override
    public Integer converteerNaarLo3(Boolean input) {
        if (Boolean.TRUE.equals(input)) {
            return 1;
        } else {
            return null;
        }
    }

    @Override
    public boolean valideerBrp(Boolean input) {
        return true;
    }
}
