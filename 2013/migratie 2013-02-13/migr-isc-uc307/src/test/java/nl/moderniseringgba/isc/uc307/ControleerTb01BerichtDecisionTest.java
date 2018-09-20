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

public class ControleerTb01BerichtDecisionTest {

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    private static final String BERICHT_ID_TB01 = "132456789";

    private static final String BERICHT_NAAM_TB01 = "tb01_bericht";

    /**
     * LO3-factory voor het converteren van teletext naar Lo3-bericht.
     */
    private static final Lo3BerichtFactory LO3_FACTORY = new Lo3BerichtFactory();

    /**
     * Teletex string van het Tb01 bericht.
     */
    private static final String TB01_TELETEX_CORRECT =
            "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                    + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                    + "3100081989010103200040599033000460300410001M621000820121101";

    private final ControleerTb01BerichtDecision controleerTb01BerichtDecision = new ControleerTb01BerichtDecision();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM_TB01, createVolledigTb01Bericht());

        final String transition = controleerTb01BerichtDecision.execute(parameters);
        Assert.assertNull("Bij HappyFlow hoort de transition 'null' te zijn.", transition);
    }

    @Test
    public void testFoutGeenTb01Bericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final String transition = controleerTb01BerichtDecision.execute(parameters);
        Assert.assertEquals(UC307Constants.INSCHRIJVING_OP_MOEDER_MISLUKT, transition);
    }

    private Object createVolledigTb01Bericht() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(TB01_TELETEX_CORRECT);
        tb01_bericht.setMessageId(BERICHT_ID_TB01);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        return tb01_bericht;
    }

}
