/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.constanten;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;


/** Bevat alleen maar constanten die binnen BRP gebruikt kan worden. */
public final class BrpConstanten {

    /** Constante voor nederlandse land code. */
    public static final Short    NL_LAND_CODE_SHORT  = 6030;
    /** Constante voor nederlandse land code. */
    public static final String   NL_LAND_CODE_STRING = "6030";
    /** Constante voor nederlandse land code. */
    public static final Landcode NL_LAND_CODE        =
        new Landcode(NL_LAND_CODE_SHORT);

    /** Constante voor nederlandse nationaliteit code. */
    public static final Nationaliteitcode NL_NATIONALITEIT_CODE =
        new Nationaliteitcode("0001");

    /** Constante voor reden wijzinging adres. */
    public static final String                  PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING = "P";
    /** Constante voor reden wijzinging adres. */
    public static final RedenWijzigingAdresCode PERSOON_REDEN_WIJZIGING_ADRES_CODE        =
        new RedenWijzigingAdresCode(PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING);

    /** Constante voor reden beeindiging relatie. */
    public static final String                      REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE_STRING = "O";
    /** Constante voor reden beeindiging relatie. */
    public static final RedenBeeindigingRelatieCode REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE        =
        new RedenBeeindigingRelatieCode(REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE_STRING);

    /** private constructor. */
    private BrpConstanten() {

    }
}
