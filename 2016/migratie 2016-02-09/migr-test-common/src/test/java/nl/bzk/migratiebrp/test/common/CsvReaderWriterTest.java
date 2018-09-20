/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nl.bzk.migratiebrp.test.common.reader.CsvReader;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkSql;
import nl.bzk.migratiebrp.test.common.writer.CsvWriter;
import org.junit.Test;

public class CsvReaderWriterTest {

    @Test
    public void testCsvReaderWriter() throws IOException {
        final File tempFile = File.createTempFile("csvReaderWriterTest-", ".csv");
        final List<Map<String, Object>> origin = new ArrayList<>();
        final Map<String, Object> rij1 = new TreeMap<>();
        rij1.put("Kolom1", "Waarde1");
        rij1.put("Kolom2", 2);

        final Map<String, Object> rij2 = new TreeMap<>();
        rij2.put("Kolom1", "Waarde3");
        rij2.put("Kolom2", 4);

        origin.add(rij1);
        origin.add(rij2);

        final CsvWriter writer = new CsvWriter();
        writer.writeSqlResultToFile(tempFile, origin);

        final CsvReader reader = new CsvReader();
        final List<Map<String, Object>> results = reader.readFileAsSqlOutput(tempFile);

        final StringBuilder verschillenLog = new StringBuilder();
        assertTrue(VergelijkSql.vergelijkSqlResultaten(verschillenLog, results, origin));
    }
}
