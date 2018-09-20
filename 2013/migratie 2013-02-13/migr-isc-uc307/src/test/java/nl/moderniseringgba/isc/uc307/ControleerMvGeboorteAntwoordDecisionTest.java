/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.FoutCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

/**
 * De klasse ControleerMvGeboorteAntwoordDecision controleert alleen of er een antwoord op een MvGeboorteVerzoekBericht
 * is binnengekomen.
 */
public class ControleerMvGeboorteAntwoordDecisionTest {

    private final ControleerMVGeboorteAntwoordDecision controleerMvGeboorteAntwoordDecision =
            new ControleerMVGeboorteAntwoordDecision();

    @Test
    public void testHappyFlow() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("brpBericht", createMvGeboorteBerichtVoorHappyFlow(true));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertNull("Bij HappyFlow hoort de transition 'null' te zijn.", transition);
    }

    @Test
    public void testGeenMvGeboorteBericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT, transition);
    }

    @Test
    public void testFoutCodeA() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.A));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_A, transition);
    }

    @Test
    public void testFoutCodeG() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.G));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_G, transition);
    }

    @Test
    public void testFoutCodeU() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.U));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_U, transition);
    }

    @Test
    public void testFoutCodeB() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.B));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_B, transition);
    }

    @Test
    public void testFoutCodeO() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.O));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_O, transition);
    }

    @Test
    public void testFoutCodeE() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.E));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_E, transition);
    }

    @Test
    public void testFoutCodeM() {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brpBericht", maakMvGeboorteAntwoordBerichtMetFoutCode(FoutCode.M));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_M, transition);
    }

    @Test
    public void testFoutMvGeboorteBericht() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("brpBericht", createMvGeboorteBerichtVoorHappyFlow(false));

        final String transition = controleerMvGeboorteAntwoordDecision.execute(parameters);
        Assert.assertEquals(ControleerMVGeboorteAntwoordDecision.FOUT_CODE_OVERIG, transition);
    }

    private MvGeboorteAntwoordBericht maakMvGeboorteAntwoordBerichtMetFoutCode(final FoutCode foutCode) {
        final MvGeboorteAntwoordType antwoordType = new MvGeboorteAntwoordType();
        antwoordType.setANummer("1234567898");
        antwoordType.setStatus(StatusType.FOUT);
        antwoordType.setFoutCode(foutCode);
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht(antwoordType);

        return mvGeboorteAntwoordBericht;
    }

    private Object createMvGeboorteBerichtVoorHappyFlow(final boolean isOK) {
        final MvGeboorteAntwoordType mvGeboorteAntwoordType = new MvGeboorteAntwoordType();
        if (isOK) {
            mvGeboorteAntwoordType.setStatus(StatusType.OK);
        } else {
            mvGeboorteAntwoordType.setStatus(StatusType.FOUT);
            // GEEN foutcode
        }
        return new MvGeboorteAntwoordBericht(mvGeboorteAntwoordType);
    }

}
