/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.sync.generated.LeesUitBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakConverteerNaarBrpGeboorteVerzoekBerichtMoederActionTest {

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    private static final String BERICHT_ID_TB01 = "132456789";
    /**
     * LO3 persoonslijst als teletext string.
     */
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private final MaakConverteerNaarBrpVerzoekBerichtMoederAction maakConverteerNaarBrpVerzoekBerichtMoederAction =
            new MaakConverteerNaarBrpVerzoekBerichtMoederAction();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("syncBericht", maakLeesUitBrpAntwoordBericht());

        final Map<String, Object> result = maakConverteerNaarBrpVerzoekBerichtMoederAction.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het converteerNaarBrpVerzoek bericht niet 'null' te zijn.",
                result.get(UC307Constants.CONVERTEER_NAAR_BRP_VERZOEK));
    }

    private Object maakLeesUitBrpAntwoordBericht() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        final LeesUitBrpAntwoordType leesUitBrpAntwoordType = new LeesUitBrpAntwoordType();
        leesUitBrpAntwoordType.setStatus(StatusType.OK);
        leesUitBrpAntwoordType.setLo3Pl(LO3_PL_STRING);

        return new LeesUitBrpAntwoordBericht(leesUitBrpAntwoordType);
    }

}
