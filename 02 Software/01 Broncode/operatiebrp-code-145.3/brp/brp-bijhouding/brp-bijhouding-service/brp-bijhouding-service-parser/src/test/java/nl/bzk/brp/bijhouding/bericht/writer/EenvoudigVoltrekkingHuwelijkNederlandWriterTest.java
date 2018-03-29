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

import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.EenvoudigVoltrekkingHuwelijkNederlandParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;

/**
 * Testen voor BijhoudingVerzoekBerichtWriter.
 */
public class EenvoudigVoltrekkingHuwelijkNederlandWriterTest {

    @Test
    public void testWriteBijhoudingBericht() throws ParseException, WriteException, IOException {
        final String expectedBerichtString = leesBerichtAlsString().trim();
        final BijhoudingVerzoekBericht bericht = leesBericht();
        final BijhoudingVerzoekBerichtWriter writer = new BijhoudingVerzoekBerichtWriter();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        writer.write(bericht, stream);
        final String actualBerichtString = new String(stream.toByteArray());
        Assert.assertEquals(expectedBerichtString.replaceAll("\\r\\n", "\n"), actualBerichtString.replaceAll("\\r\\n", "\n"));
    }

    private BijhoudingVerzoekBericht leesBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        return parser.parse(EenvoudigVoltrekkingHuwelijkNederlandParserTest.class
                .getResourceAsStream(EenvoudigVoltrekkingHuwelijkNederlandParserTest.EENVOUDIG_BIJHOUDING_BERICHT));
    }

    private String leesBerichtAlsString() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(EenvoudigVoltrekkingHuwelijkNederlandParserTest.class
                .getResourceAsStream(EenvoudigVoltrekkingHuwelijkNederlandParserTest.EENVOUDIG_BIJHOUDING_BERICHT)))) {
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
            return result.toString();
        }
    }
}
