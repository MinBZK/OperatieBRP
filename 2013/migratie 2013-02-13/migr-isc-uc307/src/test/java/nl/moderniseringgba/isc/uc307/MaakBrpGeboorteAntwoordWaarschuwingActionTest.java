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
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakBrpGeboorteAntwoordWaarschuwingActionTest {

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    private static final String MESSAGE_ID = "132456789";

    private static final String MESSAGE_NAME = "brpGeboorteAntwoordWaarschuwingBericht";

    private final MaakBrpGeboorteAntwoordWaarschuwingAction maakBrpGeboorteAntwoordWaarschuwingAction =
            new MaakBrpGeboorteAntwoordWaarschuwingAction();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC307Constants.TV01_ANTWOORD_BERICHT, createTf11BerichtVoorHappyFlow(true));

        final Map<String, Object> result = maakBrpGeboorteAntwoordWaarschuwingAction.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het brpGeboorteAntwoordWaarschuwingBericht niet 'null' te zijn.",
                result.get(MESSAGE_NAME));

        final GeboorteAntwoordBericht geboorteAntwoordBericht = (GeboorteAntwoordBericht) result.get(MESSAGE_NAME);
        Assert.assertEquals(MESSAGE_ID, geboorteAntwoordBericht.getCorrelationId());

    }

    @Test
    public void testHappyFlowZonderGemeenten() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(UC307Constants.TV01_ANTWOORD_BERICHT, createTf11BerichtVoorHappyFlow(false));

        final Map<String, Object> result = maakBrpGeboorteAntwoordWaarschuwingAction.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het brpGeboorteAntwoordWaarschuwingBericht niet 'null' te zijn.",
                result.get("brpGeboorteAntwoordWaarschuwingBericht"));

        final GeboorteAntwoordBericht geboorteAntwoordBericht = (GeboorteAntwoordBericht) result.get(MESSAGE_NAME);
        Assert.assertEquals(MESSAGE_ID, geboorteAntwoordBericht.getCorrelationId());

    }

    private Object createTf11BerichtVoorHappyFlow(final boolean vulGemeenten) throws BerichtSyntaxException,
            BerichtOnbekendException, BerichtInhoudException {
        final Tf11Bericht tf11_bericht = new Tf11Bericht();
        tf11_bericht.setMessageId(MESSAGE_ID);
        if (vulGemeenten) {
            tf11_bericht.setBronGemeente(BRONGEMEENTE);
            tf11_bericht.setDoelGemeente(DOELGEMEENTE);
        }
        return tf11_bericht;
    }

}
