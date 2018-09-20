/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc306-test-beans.xml", "classpath*:usecase-beans.xml" })
public class ConverteerBrpPersoonslijstActionTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private ConverteerBrpGeboorteberichtAction converteerBrpPersoonslijstAction;

    @Test
    public void testConversieGeboorteberichtNaarBrpPersoonslijst() throws IOException, BerichtSyntaxException,
            BerichtInhoudException {

        final BrpBerichtFactory berichtFactory = BrpBerichtFactory.SINGLETON;
        GeboorteVerzoekBericht geboorteBericht;

        geboorteBericht =
                (GeboorteVerzoekBericht) berichtFactory.getBericht(IOUtils.toString(Uc306Test.class
                        .getResourceAsStream("uc306_goed.xml")));

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", geboorteBericht); // geboortebericht

        final Map<String, Object> result = converteerBrpPersoonslijstAction.execute(parameters);

        Assert.assertNotNull("Conversie zou succesvol moeten zijn.", result.get("converteerBericht"));

    }
}
