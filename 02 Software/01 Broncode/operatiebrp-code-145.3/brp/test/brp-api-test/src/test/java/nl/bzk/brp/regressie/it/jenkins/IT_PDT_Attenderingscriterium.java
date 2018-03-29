/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.jenkins;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.core.io.ClassPathResource;

/**
 * Parsed de nadere populatiebeperking expressie van de PDT autorisaties.
 */
@RunWith(Parameterized.class)
public class IT_PDT_Attenderingscriterium {

    private final String expressie;

    public IT_PDT_Attenderingscriterium(final String expressie) {
        this.expressie = expressie;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<String> data() throws Exception {
        try (final InputStream inputStream = new ClassPathResource("/pdtexpressie/dienst-attcrit.txt").getInputStream()) {
            return IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
        }
    }

    @Test
    public void parse() throws ExpressieException {
        ExpressieParser.parse(expressie);
    }
}
