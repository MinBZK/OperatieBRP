/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.constanten;

import nl.bzk.copy.model.attribuuttype.Landcode;
import nl.bzk.copy.model.attribuuttype.Nationaliteitcode;
import nl.bzk.copy.model.attribuuttype.RedenWijzigingAdresCode;


/**
 * Bevat alleen maar constanten die binnen BRP gebruikt kan worden.
 */
public final class BrpConstanten {

    /**
     * constante voor nederlandse land code.
     */
    public static final String NL_LAND_CODE_STRING = "6030";
    /**
     * constante voor nederlandse land code.
     */
    public static final Landcode NL_LAND_CODE = new Landcode(Short.valueOf(NL_LAND_CODE_STRING));

    /**
     * constante voor nederlandse nationaliteit code.
     */
    public static final Nationaliteitcode NL_NATIONALITEIT_CODE = new Nationaliteitcode((short) 1);

    /**
     * constante voor reden wijzinging adres.
     */
    public static final String PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING = "P";
    /**
     * constante voor reden wijzinging adres.
     */
    public static final RedenWijzigingAdresCode PERSOON_REDEN_WIJZIGING_ADRES_CODE =
            new RedenWijzigingAdresCode(PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING);

    /**
     * private constructor.
     */
    private BrpConstanten() {

    }
}
