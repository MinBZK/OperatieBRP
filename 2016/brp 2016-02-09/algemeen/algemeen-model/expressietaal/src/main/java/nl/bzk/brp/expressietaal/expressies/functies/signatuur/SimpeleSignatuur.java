/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies.signatuur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;

/**
 * Signatuur met een vast aantal argumenten van een vooraf bekend type.
 */
public class SimpeleSignatuur implements Signatuur {

    private List<ExpressieType> types;

    /**
     * Constructor.
     *
     * @param aTypes Lijst van verwachte types van argumenten.
     */
    public SimpeleSignatuur(final List<ExpressieType> aTypes) {
        if (aTypes != null) {
            this.types = aTypes;
        } else {
            this.types = new ArrayList<ExpressieType>();
        }
    }

    /**
     * Constructor.
     *
     * @param aTypes Verwachte types van argumenten.
     */
    public SimpeleSignatuur(final ExpressieType... aTypes) {
        this.types = new ArrayList<ExpressieType>();
        Collections.addAll(this.types, aTypes);
    }

    @Override
    public final boolean matchArgumenten(final List<Expressie> argumenten, final Context context) {
        boolean result = false;
        if (argumenten != null && argumenten.size() == types.size()) {
            result = true;
            for (int i = 0; i < types.size() && result; i++) {
                final ExpressieType argumentType = argumenten.get(i).getType(context);
                result = types.get(i) == ExpressieType.ONBEKEND_TYPE
                    || types.get(i) == argumentType
                    || argumentType == ExpressieType.ONBEKEND_TYPE
                    || argumentType == ExpressieType.NULL;
            }

        }
        return result;
    }
}
