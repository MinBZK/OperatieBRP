/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.schrijvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileHandlerTest {
    FileHandler fileHandler;
    File path;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws URISyntaxException {
        fileHandler = new FileHandler();
    }

    @Test
    public void geeftOutputBestand() throws UnsupportedEncodingException {
        final Path output = fileHandler.geefOutputFile("not-1-expected.xml");
        assertFalse(output.toFile().exists());
    }

    @Test
    public void schrijftFileObvReferentie() throws IOException {
        final String content = "some context";
        fileHandler.schrijfFile("DATA/response/output.txt", content);

        Path verwacht = fileHandler.geefOutputFile("DATA/response/output.txt");
        assertTrue(verwacht.toFile().exists());
        final List<String> lines = Files.readAllLines(verwacht);
        assertEquals(1, lines.size());
        assertEquals(content, lines.get(0));
    }
}
