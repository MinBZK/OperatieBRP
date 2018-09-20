/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml", "classpath*:usecase-beans.xml" })
public class MaakConverteerNaarLo3VerzoekBerichtActionTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Inject
    private MaakConverteerNaarLo3VerzoekBerichtAction maakConverteerNaarLo3VerzoekBerichtAction;

    /**
     * ErkenningVerzoek
     */

    @Test
    public void testAanmakenConverteerNaarLo3VerzoekBerichtVanuitErkenningVerzoekBericht() throws IOException {

        final String origineel =
                IOUtils.toString(MaakConverteerNaarLo3VerzoekBerichtActionTest.class
                        .getResourceAsStream("uc308_erkenningVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        final Map<String, Object> result = maakConverteerNaarLo3VerzoekBerichtAction.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK));
        Assert.assertEquals(true,
                result.get(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK) instanceof ConverteerNaarLo3VerzoekBericht);
        Assert.assertNotNull(((ConverteerNaarLo3VerzoekBericht) result
                .get(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK)).getBrpPersoonslijst());
    }

    /**
     * ErkenningNotarieelVerzoek
     */

    @Test
    public void testAanmakenConverteerNaarLo3VerzoekBerichtVanuitErkenningNotarieelVerzoekBericht()
            throws IOException {

        final String origineel =
                IOUtils.toString(MaakConverteerNaarLo3VerzoekBerichtActionTest.class
                        .getResourceAsStream("uc308_erkenningNotarieelVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, bericht);
        final Map<String, Object> result = maakConverteerNaarLo3VerzoekBerichtAction.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK));
        Assert.assertEquals(true,
                result.get(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK) instanceof ConverteerNaarLo3VerzoekBericht);
        Assert.assertNotNull(((ConverteerNaarLo3VerzoekBericht) result
                .get(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK)).getBrpPersoonslijst());
    }
}
