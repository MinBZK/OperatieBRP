/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc306-test-beans.xml", "classpath*:usecase-beans.xml" })
public class ConverteerBrpGeboorteberichtActionTest {
    @Inject
    private ConverteerBrpGeboorteberichtAction converteerBrpGeboorteberichtAction;

    @Test
    public void testConverteerBrpGeboortebericht() throws Exception {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        final GeboorteVerzoekBericht geboorteBericht = maakGeboorteBericht("uc306_goed.xml");
        parameters.put("input", geboorteBericht);
        parameters.putAll(converteerBrpGeboorteberichtAction.execute(parameters));
        final ConverteerNaarLo3VerzoekBericht converteerBericht =
                (ConverteerNaarLo3VerzoekBericht) parameters.get("converteerBericht");

        assertNotNull(converteerBericht);
        Assert.assertEquals(geboorteBericht.getBrpPersoonslijst(), converteerBericht.getBrpPersoonslijst());
    }

    private GeboorteVerzoekBericht maakGeboorteBericht(final String xmlResource) throws BerichtSyntaxException,
            BerichtOnbekendException, BerichtInhoudException, IOException {
        return (GeboorteVerzoekBericht) BrpBerichtFactory.SINGLETON.getBericht(IOUtils.toString(getClass()
                .getResourceAsStream(xmlResource)));

    }
}
