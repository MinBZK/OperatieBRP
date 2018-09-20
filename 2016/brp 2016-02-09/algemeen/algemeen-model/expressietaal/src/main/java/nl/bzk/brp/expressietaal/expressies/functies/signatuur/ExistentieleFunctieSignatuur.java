/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies.signatuur;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;

/**
 * Signatuur voor existentiele functies ALLE en ER_IS van de vorm (lijst, variabele, conditie).
 */
public class ExistentieleFunctieSignatuur implements Signatuur {

    private static final int AANTAL_ARGUMENTEN = 3;

    @Override
    public final boolean matchArgumenten(final List<Expressie> argumenten, final Context context) {
        boolean match = false;
        if (argumenten != null && argumenten.size() == AANTAL_ARGUMENTEN) {
            match = argumenten.get(0).isLijstExpressie() || argumenten.get(0).isNull(context);
            if (match) {
                match = argumenten.get(1).isVariabele();
            }
            if (match) {
                match = argumenten.get(2).getType(context) == ExpressieType.BOOLEAN
                    || argumenten.get(2).isNull(context);
            }
        }
        return match;
    }
}
