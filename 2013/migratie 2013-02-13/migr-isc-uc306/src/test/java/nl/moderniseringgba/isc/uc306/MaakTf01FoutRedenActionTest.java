/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.FOUT_REDEN;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_AL_AANWEZIG;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_EMIGRATIE;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_GEBLOKKEERD;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_MIN_BESLUIT;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_NIET_GEVONDEN;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_NIET_UNIEK;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_OVERLEDEN;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.REDEN_VERHUISD;
import static nl.moderniseringgba.isc.uc306.MaakTf01FoutredenAction.TF01_FOUT_REDEN;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht.Foutreden;

import org.junit.Test;

public class MaakTf01FoutRedenActionTest {

    @Test
    public void testTf01A() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000001Tf01A12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.A, REDEN_AL_AANWEZIG), fout_reden);
    }

    @Test
    public void testTf01B() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000002Tf01B12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.B, REDEN_GEBLOKKEERD), fout_reden);
    }

    @Test
    public void testTf01E() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000003Tf01E12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.E, REDEN_EMIGRATIE), fout_reden);
    }

    @Test
    public void testTf01G() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000004Tf01G12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.G, REDEN_NIET_GEVONDEN), fout_reden);
    }

    @Test
    public void testTf01M() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000005Tf01M12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.M, REDEN_MIN_BESLUIT), fout_reden);
    }

    @Test
    public void testTf01O() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000006Tf01O12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.O, REDEN_OVERLEDEN), fout_reden);
    }

    @Test
    public void testTf01U() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000007Tf01U12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.U, REDEN_NIET_UNIEK), fout_reden);
    }

    @Test
    public void testTf01V() throws BerichtSyntaxException, BerichtInhoudException {
        final String lo3 = "00000008Tf01V12340123456789000190101481200071234567";
        final String fout_reden = (String) executeTf01Action(lo3).get(TF01_FOUT_REDEN);
        Assert.assertEquals(String.format(FOUT_REDEN, Foutreden.V, REDEN_VERHUISD), fout_reden);
    }

    private Map<String, Object> executeTf01Action(final String lo3Bericht) throws BerichtSyntaxException,
            BerichtInhoudException {

        final Tf01Bericht tf01 = new Tf01Bericht();
        tf01.parse(lo3Bericht);

        final MaakTf01FoutredenAction maakTf01FoutredenAction = new MaakTf01FoutredenAction();

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tf01Bericht", tf01);
        return maakTf01FoutredenAction.execute(parameters);
    }
}
