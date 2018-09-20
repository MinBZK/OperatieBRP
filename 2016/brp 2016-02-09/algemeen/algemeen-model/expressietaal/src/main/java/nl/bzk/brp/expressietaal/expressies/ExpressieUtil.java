/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.OngelijkheidsoperatorExpressie;

/**
 */
public final class ExpressieUtil {
    private ExpressieUtil() {
    }

    /**
     * Geeft een expressie die aangeeft of twee waarden onderling verschillen. Als beiden NULL zijn, worden ze als niet verschillend beschouwd; als één van
     * beide NULL is, zijn ze wel verschillend. Als beide niet NULL zijn, wordt de standaard vergelijkingsoperator gebruikt.
     *
     * @param waarde1 De oude waarde.
     * @param waarde2 De nieuwe waarde.
     * @param context Context waarbinnen evaluatie plaatsvindt.
     * @return Resultaat van de vergelijking.
     */
    public static Expressie waardenVerschillend(final Expressie waarde1, final Expressie waarde2,
        final Context context)
    {
        final Expressie resultaat;
        if (waarde1.isNull(context) && waarde2.isNull(context)) {
            resultaat = BooleanLiteralExpressie.ONWAAR;
        } else if (waarde1.isNull(context) || waarde2.isNull(context)) {
            resultaat = BooleanLiteralExpressie.WAAR;
        } else if (waarde1.getType(context) == ExpressieType.DATUM && waarde2.getType(context) == ExpressieType.DATUM) {
            resultaat = BooleanLiteralExpressie.getExpressie(waarde1.alsInteger() != waarde2.alsInteger());
        } else {
            resultaat = new OngelijkheidsoperatorExpressie(waarde1, waarde2).evalueer(context);
        }
        return resultaat;
    }
}
