/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import java.io.IOException;
import java.io.Writer;

/**
 * Interface voor het wegschrijven van SQL.
 */
public interface SQLWriter {

    /**
     * Schrijft de SQL
     *
     * @param writer de writer om naar toe schrijven
     * @throws IOException als het schrijven fout gaat
     */
    void toSQL(Writer writer) throws IOException;
}
