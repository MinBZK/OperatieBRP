/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarLo3AntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml", "classpath*:usecase-beans.xml" })
public class ControleerConverteerNaarLo3AntwoordBerichtDecisionTest {

    /**
     * LO3 persoonslijst als teletext string.
     */
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Inject
    private ControleerConverteerNaarLo3AntwoordBerichtDecision controleerConverteerNaarLo3AntwoordBerichtDecision;

    @Test
    public void testOkAntwoord() {

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.OK);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final String result = controleerConverteerNaarLo3AntwoordBerichtDecision.execute(parameters);

        Assert.assertNull(result);

    }

    @Test
    public void testFoutAntwoord() {

        final ConverteerNaarLo3AntwoordType type = new ConverteerNaarLo3AntwoordType();
        type.setStatus(StatusType.FOUT);
        type.setLo3Pl(LO3_PL_STRING);
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(type);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD, converteerNaarLo3AntwoordBericht);

        final String result = controleerConverteerNaarLo3AntwoordBerichtDecision.execute(parameters);

        Assert.assertNotNull(result);
        Assert.assertEquals(UC308Constants.CONVERSIE_MISLUKT, result);
    }
}
