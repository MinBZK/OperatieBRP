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

import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingAntwoordBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.parser.VoltrekkingHuwelijkNederlandAntwoordParserTest;

/**
 * Testen voor BijhoudingAntwoordBerichtWriter.
 */
public class VoltrekkingHuwelijkNederlandAntwoordWriterTest {

    @Test
    public void testWriteBijhoudingBericht() throws ParseException, WriteException, IOException {
        final String expectedBerichtString = leesBerichtAlsString().trim();
        final BijhoudingAntwoordBericht bericht = leesBericht();
        final BijhoudingAntwoordBerichtWriter writer = new BijhoudingAntwoordBerichtWriter();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        writer.write(bericht, stream);
        final String actualBerichtString = new String(stream.toByteArray());
        Assert.assertEquals(expectedBerichtString.replaceAll("\\r\\n", "\n"), actualBerichtString.replaceAll("\\r\\n", "\n"));
    }

    private BijhoudingAntwoordBericht leesBericht() throws ParseException {
        final BijhoudingAntwoordBerichtParser parser = new BijhoudingAntwoordBerichtParser();
        return parser.parse(VoltrekkingHuwelijkNederlandAntwoordParserTest.class
                .getResourceAsStream(VoltrekkingHuwelijkNederlandAntwoordParserTest.EENVOUDIG_BIJHOUDING_ANTWOORD_BERICHT));
    }

    private String leesBerichtAlsString() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(VoltrekkingHuwelijkNederlandAntwoordParserTest.class
                .getResourceAsStream(VoltrekkingHuwelijkNederlandAntwoordParserTest.EENVOUDIG_BIJHOUDING_ANTWOORD_BERICHT)))) {
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
            return result.toString();
        }
    }
}
