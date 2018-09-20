/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc306-test-beans.xml" })
public class MaakTb01ActionTest {

    @Test
    public void testMaakTb01Action() throws Exception {
        // given
        // een geboortebericht onder de key 'input' in de parameters
        // een 'converteerResponseBericht' in de parameters, met daarin de Lo3 PL
        // een 'adresbepalendeOuder' in de parameters, met daarin 'M' of 'V' (moeder|vader)

        final GeboorteVerzoekBericht geboorteBericht = createGeboorteBericht();
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord = createConverteerNaarLo3AntwoordBericht();
        final String adresbepalendeOuder = "M";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", geboorteBericht);
        parameters.put("converteerNaarLo3Antwoord", converteerNaarLo3Antwoord);
        parameters.put(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER, adresbepalendeOuder);

        final MaakTb01Action action = new MaakTb01Action();
        parameters = action.execute(parameters);

        final Tb01Bericht tb01Bericht = (Tb01Bericht) parameters.get("tb01Bericht");
        assertNotNull(tb01Bericht);

        final String messageId = tb01Bericht.getMessageId();

        // // test herhaling
        // parameters.put("tb01Herhaling", "yep");
        // parameters.put("converteerResponseBericht", converteerResponseBericht);
        // parameters.put("input", geboorteBericht);
        // action.execute(parameters);
        // tb01Bericht = (Tb01Bericht) parameters.get("tb01Bericht");
        assertEquals(messageId, tb01Bericht.getMessageId());
    }

    private ConverteerNaarLo3AntwoordBericht createConverteerNaarLo3AntwoordBericht() {
        final Lo3Persoonslijst persoonslijst = null;
        final ConverteerNaarLo3AntwoordBericht converteerResponseBericht =
                new ConverteerNaarLo3AntwoordBericht("123", persoonslijst);
        return converteerResponseBericht;
    }

    private GeboorteVerzoekBericht createGeboorteBericht() throws IOException, BerichtSyntaxException,
            BerichtOnbekendException, BerichtInhoudException {
        final String origineel = IOUtils.toString(getClass().getResourceAsStream("uc306_goed.xml"));
        final BrpBericht bericht = BrpBerichtFactory.SINGLETON.getBericht(origineel);
        final GeboorteVerzoekBericht geboorteBericht = (GeboorteVerzoekBericht) bericht;
        return geboorteBericht;
    }
}
