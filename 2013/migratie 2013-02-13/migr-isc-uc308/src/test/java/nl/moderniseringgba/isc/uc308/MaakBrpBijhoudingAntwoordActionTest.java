/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningNotarieelVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVerzoekBericht;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml", "classpath*:usecase-beans.xml" })
public class MaakBrpBijhoudingAntwoordActionTest {

    @Inject
    private MaakBrpBijhoudingAntwoordAction maakBrpBijhoudingAntwoordAction;

    @Test
    public void testAanmakenErkenningAntwoord() {

        final BrpBijhoudingVerzoekBericht erkenningVerzoekBericht = new ErkenningVerzoekBericht();

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningVerzoekBericht);
        final Map<String, Object> result = maakBrpBijhoudingAntwoordAction.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.BRP_BIJHOUDING_ANTWOORD_BERICHT));
        Assert.assertEquals(true,
                result.get(UC308Constants.BRP_BIJHOUDING_ANTWOORD_BERICHT) instanceof BrpBijhoudingAntwoordBericht);
        Assert.assertEquals(StatusType.OK, ((BrpBijhoudingAntwoordBericht) result
                .get(UC308Constants.BRP_BIJHOUDING_ANTWOORD_BERICHT)).getStatus());
    }

    @Test
    public void testAanmakenErkenningNotarieelAntwoord() {

        final BrpBijhoudingVerzoekBericht erkenningNotarieelVerzoekBericht = new ErkenningNotarieelVerzoekBericht();

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT, erkenningNotarieelVerzoekBericht);
        final Map<String, Object> result = maakBrpBijhoudingAntwoordAction.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(UC308Constants.BRP_BIJHOUDING_ANTWOORD_BERICHT));
        Assert.assertEquals(true,
                result.get(UC308Constants.BRP_BIJHOUDING_ANTWOORD_BERICHT) instanceof BrpBijhoudingAntwoordBericht);
        Assert.assertEquals(StatusType.OK, ((BrpBijhoudingAntwoordBericht) result
                .get(UC308Constants.BRP_BIJHOUDING_ANTWOORD_BERICHT)).getStatus());
    }
}
