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
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc306-test-beans.xml", "classpath*:usecase-beans.xml" })
public class ControleerConverteerResponseBerichtDecisionTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private ControleerConverteerResponseBerichtDecision converteerBrpPersoonslijstDecision;

    @Test
    public void testConversieGeboorteberichtNaarBrpPersoonslijst() throws IOException, BerichtSyntaxException,
            BerichtInhoudException {

        ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                new ConverteerNaarLo3AntwoordBericht("1234", "De gevraagde persoonslijst kon niet worden opgehaald.");
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("converteerNaarLo3Antwoord", converteerNaarLo3Antwoord); // geboortebericht

        String result = converteerBrpPersoonslijstDecision.execute(parameters);

        Assert.assertEquals("Conversie zou succesvol moeten zijn.", "4b-2. Fout", result);

        converteerNaarLo3Antwoord =
                new ConverteerNaarLo3AntwoordBericht("1234",
                        Lo3PersoonslijstTestUtils.maakGeboorte(Lo3PersoonslijstTestUtils.maakLo3PersoonInhoud()));

        parameters.put("converteerNaarLo3Antwoord", converteerNaarLo3Antwoord); // geboortebericht

        result = converteerBrpPersoonslijstDecision.execute(parameters);

        Assert.assertEquals("Conversie zou succesvol moeten zijn.", null, result);
    }
}
