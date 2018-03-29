/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.schrijvers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class DiffHandlerTest {

    private final DiffHandler diffHandler = new DiffHandler();

    @Test
    public void schrijfDiffFiltertWildcardMatch() throws URISyntaxException, IOException {
        Path xml1 = geefInput("1-expected.xml");
        Path xml2 = geefInput("1-actual.xml");

        Path out = geefOutput("diff2.diff");

        diffHandler.schrijfDiff(xml1, xml2, out, true);
        assertFalse(out.toFile().exists());
    }

    @Test
    public void schrijfDiffFiltertWildcardMatchOpTweeRegels() throws URISyntaxException, IOException {
        Path xml1 = geefInput("1-expected-2.xml");
        Path xml2 = geefInput("1-actual.xml");

        Path out = geefOutput("diff5.diff");

        diffHandler.schrijfDiff(xml1, xml2, out, true);
        assertFalse(out.toFile().exists());
    }

    @Test
    public void schrijfDiffFiltertOntbrekendeNodes() throws URISyntaxException, IOException {
        Path xml1 = geefInput("1-expected.xml");
        Path xml2 = geefInput("2-actual.xml");

        Path out = geefOutput("diff4.diff");

        diffHandler.schrijfDiff(xml1, xml2, out, true);
        assertFalse(out.toFile().exists());
    }

    @Test
    public void diffMetWerkelijkeVoorbeelden() throws IOException, URISyntaxException {
        Path xml1 = geefInput("BRLV0018-TC00-3-expected.xml");
        Path xml2 = geefInput("BRLV0018-TC00-3-actual.xml");

        Path out = geefOutput("BRLV0018-TC00-2.diff");

        diffHandler.schrijfDiff(xml1, xml2, out, true);
        assertTrue(out.toFile().exists());
    }

    private Path geefInput(final String name) throws URISyntaxException {
        return Paths.get(getClass().getResource("/xml/" + name).toURI());
    }

    private Path geefOutput(final String name) throws URISyntaxException {
        final Path path = Paths.get(getClass().getResource("/").toURI());
        return path.resolve(name);
    }
}
