/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import java.io.File;

/**
 * Strategie interface voor de databaseinteractie mbt de afnemerindicaties.
 */
interface AfnemerindicatieDatabaseInteractieStrategy {

    void dumpAfnemerindicatieTabelNaarFile(final File outputFile);

    void vulAfnemerindicatieTabel(final File inputFile);

    void dumpHisAfnemerindicatieTabel(final File outputFile);

    void vulHisAfnemerindicatieTabel(final File inputFile);
}
