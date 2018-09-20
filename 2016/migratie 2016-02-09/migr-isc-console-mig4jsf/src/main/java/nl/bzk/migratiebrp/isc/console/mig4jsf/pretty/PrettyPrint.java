/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pretty;

import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;

/**
 * Pretty print berichten.
 */
public final class PrettyPrint {

    private final PrettyBrp prettyBrp = new PrettyBrp();
    private final PrettyIsc prettyIsc = new PrettyIsc();
    private final PrettyLo3 prettyLo3 = new PrettyLo3();
    private final PrettySync prettySync = new PrettySync();

    /**
     * Pretty print.
     * 
     * @param bericht
     *            bericht
     * @return pretty printed bericht
     */
    public String prettyPrint(final Bericht bericht) {
        final String result;
        switch (bericht.getKanaal()) {
            case "BRP":
                result = prettyBrp.prettyPrint(bericht.getBericht());
                break;
            case "ISC":
                result = prettyIsc.prettyPrint(bericht.getBericht());
                break;
            case "Levering":
            case "VOSPG":
                result = prettyLo3.prettyPrint(bericht.getBericht());
                break;
            case "SYNC":
                result = prettySync.prettyPrint(bericht.getBericht());
                break;
            default:
                result = null;
                break;
        }
        return result;
    }
}
