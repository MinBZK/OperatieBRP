/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.PrintWriter;

/**
 * Interface voor een sheetHandler.
 *
 */
public interface SheetHandler {

    /**
     * Handle sheet af.
     * @param printWriter de wrire waar de sql statements naartoe geschreven moet worden.
     * @return .
     */
    int print(PrintWriter printWriter);

//    void printHeader(PrintWriter printWriter);
//
//    void printFooter(PrintWriter printWriter);
}
