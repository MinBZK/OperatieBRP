/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import nl.bzk.brp.delivery.algemeen.AbstractVerzoekParserTest;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import org.junit.Test;

/**
 * Unit test voor {@link ZoekPersoonVerzoekParser}.
 */
public class ZoekPersoonVerzoekParserTest extends AbstractVerzoekParserTest {

    @Test
    public void testParseLeegVerzoek() throws Exception {
        // Puur voor coverage, wordt verder gedekt door LeveringBevragingBerichtParserTest.
        final ZoekPersoonVerzoekParser parser = new ZoekPersoonVerzoekParser();
        final ZoekPersoonVerzoek verzoek = new ZoekPersoonVerzoek();

        parser.vulParameters(verzoek, node, xPath);
    }

}