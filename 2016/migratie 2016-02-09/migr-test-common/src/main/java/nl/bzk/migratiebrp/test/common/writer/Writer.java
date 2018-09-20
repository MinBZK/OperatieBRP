/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.writer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Publieke interface voor de Writer objecten.
 */
public interface Writer {

    /**
     * Schrijft de opgegeven content naar een bestand weg. Deze methode kan bv gebruikt worden om een lijst van
     * fingerprints van de verschil-analayse weg te schrijven.
     * 
     * @param file
     *            het betsand waar toe weg geschreven moet worden
     * @param contents
     *            de content die weg geschreven moet worden
     * @throws IOException
     *             Kan gegooid worden als de onderliggende implementatie een IOException krijgt.
     */
    void writeToFile(final File file, final List<String> contents) throws IOException;

    /**
     * Schrijft de opgegeven content naar een bestand weg. Deze methode kan bv gebruikt worden voor het wegschrijven van
     * het resultaat van een database-query.
     * 
     * @param file
     *            het bestand waar naar toe weg geschreven moet worden
     * @param contents
     *            de content die weg geschreven moet worden.
     * @throws IOException
     *             Kan gegooid worden als de onderliggende implementatie een IOException krijgt.
     */
    void writeSqlResultToFile(File file, List<Map<String, Object>> contents) throws IOException;
}
