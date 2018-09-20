/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersonenType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersoonType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakLeesUitBrpVerzoekBerichtMoederActionTest {

    private static final String ANUMMER = "132456789";

    private static final String BERICHT_NAAM = "brpBericht";

    private final MaakLeesUitBrpVerzoekBerichtMoederAction maakQueryBerichtMoederAction = new MaakLeesUitBrpVerzoekBerichtMoederAction();

    @Test
    public void testHappyFlow() throws Exception {

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(BERICHT_NAAM, zoekPersoonAntwoordBericht());

        final Map<String, Object> result = maakQueryBerichtMoederAction.execute(parameters);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                (LeesUitBrpVerzoekBericht) result.get("leesUitBrpVerzoekBericht");

        Assert.assertNotNull("Bij HappyFlow hoort het leesUitBrpVerzoekBericht niet 'null' te zijn.",
                leesUitBrpVerzoekBericht);

        Assert.assertEquals("Anummer is niet gelijk.", ANUMMER, leesUitBrpVerzoekBericht.getANummer().toString());

    }

    private Object zoekPersoonAntwoordBericht() {

        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon = new GevondenPersoonType();
        gevondenPersoon.setANummer(ANUMMER);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon);

        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonen);
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);

        return zoekPersoonAntwoordBericht;
    }

}
