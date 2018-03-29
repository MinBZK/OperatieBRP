/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * Conversietabel voor Soort Selectie.
 */
public class SoortSelectieConversietabel implements Conversietabel<Integer, SoortSelectie> {

    public static final int INT_2 = 2;
    public static final int INT_4 = 4;

    @Override
    public SoortSelectie converteerNaarBrp(final Integer input) {
        final SoortSelectie result;
        if(input == null) {
            result = null;
        } else if (input == 0) {
            result = SoortSelectie.STANDAARD_SELECTIE;
        } else if (input == 1) {
            result = SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE;
        } else if (input >= INT_2 && input <= INT_4) {
            result = SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE;
        } else {
            throw new IllegalArgumentException("Ongeldige waarde voor lo3 soort selectie (voor conversie naar brp): " + input);
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Integer input) {
        return input == null || (input >= 0 && input <= INT_4);
    }

    @Override
    public Integer converteerNaarLo3(final SoortSelectie input) {
        final Integer result;
        switch (input) {
            case STANDAARD_SELECTIE:
                result = null;
                break;
            case SELECTIE_MET_PLAATSING_AFNEMERINDICATIE:
                result = 1;
                break;
            case SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE:
                result = INT_2;
                break;
            default:
                throw new IllegalArgumentException("Ongeldige waarde voor brp soort selectie (voor conversie naar lo3): " + input);
        }
        return result;
    }

    @Override
    public boolean valideerBrp(final SoortSelectie input) {
        // Alle boolean waarden zijn geldig
        return true;
    }
}
