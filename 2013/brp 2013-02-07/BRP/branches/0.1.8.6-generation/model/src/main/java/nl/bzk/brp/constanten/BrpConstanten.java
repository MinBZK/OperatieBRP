/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.constanten;

import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;


/**
 *
 * Bevat alleen maar constanten die binnen BRP gebruikt kan worden.
 */
public final class BrpConstanten {

    /** constante voor nederlandse land code. */
    public static final Landcode          NL_LAND_CODE          = new Landcode((short) 6030);

    /** constante voor nederlandse nationaliteit code. */
    public static final Nationaliteitcode NL_NATIONALITEIT_CODE = new Nationaliteitcode((short) 1);

    /**
     * private constructor.
     */
    private BrpConstanten() {

    }
}
