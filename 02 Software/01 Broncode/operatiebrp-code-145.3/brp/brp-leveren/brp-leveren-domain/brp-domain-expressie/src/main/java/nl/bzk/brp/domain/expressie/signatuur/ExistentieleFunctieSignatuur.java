/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.signatuur;

import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;

/**
 * Signatuur voor existentiele functies ALLE en ER_IS van de vorm (lijst, variabele, conditie).
 */
public final class ExistentieleFunctieSignatuur implements Signatuur {

    private static final int AANTAL_ARGUMENTEN = 3;

    @Override
    public boolean test(final List<Expressie> argumenten, final Context context) {
        boolean match = false;
        if (argumenten != null && argumenten.size() == AANTAL_ARGUMENTEN) {
            match = argumenten.get(1).isVariabele();
        }
        return match;
    }
}
