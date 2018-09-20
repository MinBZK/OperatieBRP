/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.util;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractAttribuutType;


public final class VergelijkingsUtil {

    public static boolean zijnAttributenGelijkOfNietGedefinieerd(final AbstractAttribuutType type1,
                                                                 final AbstractAttribuutType type2)
    {
        if (type1 == null || type2 == null) {
            return type1 == null && type2 == null;
        }
        if (type1.getWaarde() == null) {
            return type2.getWaarde() == null;
        }

        return type1.getWaarde().equals(type2.getWaarde());
    }
}
