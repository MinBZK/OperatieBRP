/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.junit.Assert;
import org.junit.Test;

public class BrpNummerverwijzingInhoudTest {

    private static final String VORIGE_A_NUMMER = "3164698657";
    private static final String VOLDENDE_A_NUMMER = "5256891937";
    private static final String VORIGE_BSN_NUMMER = "102533891";
    private static final String VOLDENDE_BSN_NUMMER = "657184457";

    @Test
    public void test(){
        final BrpNummerverwijzingInhoud inhoud = createInhoud();
        Assert.assertEquals(VOLDENDE_A_NUMMER,inhoud.getVolgendeAdministratienummer().getWaarde());
    }

    public static BrpNummerverwijzingInhoud createInhoud() {
        return new BrpNummerverwijzingInhoud(
                new BrpString(VORIGE_A_NUMMER),
                new BrpString(VOLDENDE_A_NUMMER),
                new BrpString(VORIGE_BSN_NUMMER),
                new BrpString(VOLDENDE_BSN_NUMMER));
    }

    public static BrpStapel<BrpNummerverwijzingInhoud> createStapel() {
        List<BrpGroep<BrpNummerverwijzingInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpNummerverwijzingInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }
}
