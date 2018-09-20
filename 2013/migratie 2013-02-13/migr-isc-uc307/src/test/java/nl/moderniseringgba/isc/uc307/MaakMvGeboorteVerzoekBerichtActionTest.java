/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;

import org.junit.Assert;
import org.junit.Test;

public class MaakMvGeboorteVerzoekBerichtActionTest {

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    private final MaakMVGeboorteVerzoekBerichtAction maakMVGeboorteVerzoekBerichtAction =
            new MaakMVGeboorteVerzoekBerichtAction();

    private final Uc307Test uc307Test = new Uc307Test();

    @Test
    public void testHappyFlow() throws Exception {

        final BrpPersoonslijst kindPersoonslijst = uc307Test.createBrpPersoonslijst(true);
        final BrpPersoonslijst moederPersoonslijst = uc307Test.createBrpPersoonslijst(false);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", maakTb01Bericht());
        parameters.put(MaakMVGeboorteVerzoekBerichtAction.PL_KIND,
                maakConverteerNaarBrpAntwoordBericht(kindPersoonslijst));
        parameters.put(MaakMVGeboorteVerzoekBerichtAction.PL_MOEDER,
                maakLeesUitBrpAntwoordBericht(moederPersoonslijst));

        final Map<String, Object> result = maakMVGeboorteVerzoekBerichtAction.execute(parameters);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht =
                (MvGeboorteVerzoekBericht) result.get(MaakMVGeboorteVerzoekBerichtAction.MV_GEBOORTE_VERZOEK_BERICHT);

        Assert.assertNotNull("Bij HappyFlow hoort het MvGeboorteVerzoek bericht niet 'null' te zijn.",
                mvGeboorteVerzoekBericht);

        Assert.assertEquals("Brongemeente is incorrect.", BRONGEMEENTE, mvGeboorteVerzoekBericht.getLo3Gemeente()
                .getFormattedStringCode());

        Assert.assertEquals("Doelgemeente is incorrect.", DOELGEMEENTE, mvGeboorteVerzoekBericht.getBrpGemeente()
                .getFormattedStringCode());

        Assert.assertEquals("Persoonslijst kind is niet gelijk.", kindPersoonslijst,
                mvGeboorteVerzoekBericht.getKindPersoonslijst());

        Assert.assertEquals("Persoonslijst moeder is niet gelijk.", moederPersoonslijst,
                mvGeboorteVerzoekBericht.getMoederPersoonslijst());

    }

    private Object maakTb01Bericht() {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setBronGemeente(BRONGEMEENTE);
        tb01Bericht.setDoelGemeente(DOELGEMEENTE);

        return tb01Bericht;
    }

    private ConverteerNaarBrpAntwoordBericht maakConverteerNaarBrpAntwoordBericht(
            final BrpPersoonslijst brpPersoonsLijst) {
        final ConverteerNaarBrpAntwoordBericht converteerNaarBrpAntwoordBericht =
                new ConverteerNaarBrpAntwoordBericht();

        converteerNaarBrpAntwoordBericht.setBrpPersoonslijst(brpPersoonsLijst);

        return converteerNaarBrpAntwoordBericht;
    }

    private LeesUitBrpAntwoordBericht maakLeesUitBrpAntwoordBericht(final BrpPersoonslijst brpPersoonsLijst) {
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoord =
                new LeesUitBrpAntwoordBericht(new LeesUitBrpVerzoekBericht(), brpPersoonsLijst);

        return leesUitBrpAntwoord;
    }

}
