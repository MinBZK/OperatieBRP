/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;

import java.util.List;
import joptsimple.OptionSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test that asserts the configuration of our commandline options.
 */
@RunWith(JUnit4.class)
public class CliParserTest {

    private static final String PERSOONID1 = "12345";
    private static final String PERSOONID2 = "678910";
    private static final String PERSOONID3 = "111213";

    private CliParser parser = new CliParser();

    @Test
    public void testCliParserLangeOpties() {
        //given
        final String[] args = {"--scenario=specifieke-personen", "--persoonIdLijst", PERSOONID1, "--persoonIdLijst", PERSOONID2, "--persoonIdLijst", PERSOONID3};

        // when
        final OptionSet options = parser.parse(args);

        // then
        Assert.assertEquals(true, options.has(ContextParameterNames.PERSOON_ID_LIJST.getName()));
        Assert.assertTrue(((List<String>) options.valuesOf(ContextParameterNames.SCENARIO.getName())).contains("specifieke-personen"));
        Assert.assertTrue(((List<String>) options.valuesOf(ContextParameterNames.PERSOON_ID_LIJST.getName())).contains(PERSOONID1));
        Assert.assertTrue(((List<String>) options.valuesOf(ContextParameterNames.PERSOON_ID_LIJST.getName())).contains(PERSOONID2));
    }

    @Test
    public void testCliParserKorteOpties() {
        //given
        final String[] args = {"-s=specifieke-personen", "-p", PERSOONID1, "-p", PERSOONID2, "-p", PERSOONID3};

        // when
        final OptionSet options = parser.parse(args);

        // then
        Assert.assertEquals(true, options.has(ContextParameterNames.PERSOON_ID_LIJST.getName()));
        Assert.assertTrue(((List<String>) options.valuesOf(ContextParameterNames.SCENARIO.getName())).contains("specifieke-personen"));
        Assert.assertTrue(((List<String>) options.valuesOf(ContextParameterNames.PERSOON_ID_LIJST.getName())).contains(PERSOONID1));
        Assert.assertTrue(((List<String>) options.valuesOf(ContextParameterNames.PERSOON_ID_LIJST.getName())).contains(PERSOONID2));
    }

    @Test(expected = Exception.class)
    public void testCliParserZonderOpties() {
        //given
        final String[] args = {};

        // when
        parser.parse(args);
    }
}
