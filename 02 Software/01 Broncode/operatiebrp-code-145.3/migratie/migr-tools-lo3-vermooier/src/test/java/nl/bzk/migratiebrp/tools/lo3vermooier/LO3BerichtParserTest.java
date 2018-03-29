/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.lo3vermooier;

import java.io.IOException;
import java.io.InputStream;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class LO3BerichtParserTest {

    private void print(final String resource) throws IOException, BerichtSyntaxException {
        try (InputStream input = LO3BerichtParserTest.class.getResourceAsStream(resource)) {
            final String tekst = IOUtils.toString(input, "UTF-8");
            System.out.println(tekst);
            final Lo3BerichtPrinter printer = new Lo3BerichtPrinter(tekst);
            System.out.println(printer);
            printer.print();
        }
    }

    @Test
    public void ag01() throws Exception {
        print("/ag01.txt");
    }

    @Test
    public void ap01() throws Exception {
        print("/ap01.txt");
    }

    @Test
    public void hq01() throws Exception {
        print("/hq01.txt");
    }

    @Test
    public void la01() throws Exception {
        print("/la01.txt");
    }

    @Test
    public void lg01() throws Exception {
        print("/lg01.txt");
    }

    @Test
    public void tb02() throws Exception {
        print("/tb02.txt");
    }

}
