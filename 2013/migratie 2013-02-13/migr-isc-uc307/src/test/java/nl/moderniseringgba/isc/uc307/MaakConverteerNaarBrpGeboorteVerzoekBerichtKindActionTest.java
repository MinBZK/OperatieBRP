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
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakConverteerNaarBrpGeboorteVerzoekBerichtKindActionTest {

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
     * LO3-factory voor het converteren van teletext naar Lo3-bericht.
     */
    private static final Lo3BerichtFactory LO3_FACTORY = new Lo3BerichtFactory();

    /**
     * Teletex string van het Tb01 bericht.
     */
    private static final String TB01_TELETEX =
            "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                    + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                    + "3100081989010103200040599033000460300410001M621000820121101";

    private final MaakConverteerNaarBrpVerzoekBerichtKindAction maakConverteerNaarBrpVerzoekBerichtKindAction =
            new MaakConverteerNaarBrpVerzoekBerichtKindAction();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC307Constants.TB01_BERICHT, maakTb01Bericht());

        final Map<String, Object> result = maakConverteerNaarBrpVerzoekBerichtKindAction.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het converteerNaarBrpVerzoek bericht niet 'null' te zijn.",
                result.get(UC307Constants.CONVERTEER_NAAR_BRP_VERZOEK));
    }

    private Object maakTb01Bericht() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(TB01_TELETEX);
        tb01_bericht.setMessageId(BERICHT_ID_TB01);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);

        return tb01_bericht;
    }
}
