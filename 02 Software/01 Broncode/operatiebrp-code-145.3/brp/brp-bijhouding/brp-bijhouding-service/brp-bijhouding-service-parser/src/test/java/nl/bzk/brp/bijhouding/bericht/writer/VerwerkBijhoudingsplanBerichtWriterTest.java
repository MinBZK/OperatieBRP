/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBericht;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.parser.VerwerkBijhoudingsplanBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.VerwerkBijhoudingsplanBerichtParserTest;

/**
 * Testen voor {@link VerwerkBijhoudingsplanBerichtWriter}.
 */
public class VerwerkBijhoudingsplanBerichtWriterTest {

    @Test
    public void testWriteVerwerkBijhoudingsplanBericht() throws ParseException, WriteException, IOException {
        final String expectedBerichtString = leesBerichtAlsString().trim();
        final VerwerkBijhoudingsplanBericht bericht = leesBericht();
        final VerwerkBijhoudingsplanBerichtWriter writer = new VerwerkBijhoudingsplanBerichtWriter();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        writer.write(bericht, stream);
        final String actualBerichtString = new String(stream.toByteArray());
        Assert.assertEquals(expectedBerichtString.replaceAll("\\r\\n", "\n"), actualBerichtString.replaceAll("\\r\\n", "\n"));
    }

    private VerwerkBijhoudingsplanBericht leesBericht() throws ParseException {
        final VerwerkBijhoudingsplanBerichtParser parser = new VerwerkBijhoudingsplanBerichtParser();
        return parser.parse(VerwerkBijhoudingsplanBerichtParserTest.class.getResourceAsStream(VerwerkBijhoudingsplanBerichtParserTest.BERICHT));
    }

    private String leesBerichtAlsString() throws IOException {
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(VerwerkBijhoudingsplanBerichtParserTest.class.getResourceAsStream(VerwerkBijhoudingsplanBerichtParserTest.BERICHT)))) {
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
            return result.toString();
        }
    }
}
