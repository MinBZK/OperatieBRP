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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCodeTest;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.junit.Assert;
import org.junit.Test;

public class BrpReisdocumentInhoudTest {

    @Test
    public void test(){
        final BrpReisdocumentInhoud inhoud = createInhoud();
        Assert.assertEquals("EK",inhoud.getSoort().getWaarde());
    }

    public static BrpReisdocumentInhoud createInhoud() {
        return new BrpReisdocumentInhoud(
                new BrpSoortNederlandsReisdocumentCode("EK"),
                new BrpString("123456789"),
                new BrpDatum(new Integer(20020506), null),
                new BrpDatum(new Integer(20020506), null),
                BrpReisdocumentAutoriteitVanAfgifteCodeTest.Burgemeester(),
                null,
                null,
                null);
    }


    public static BrpStapel<BrpReisdocumentInhoud> createStapel() {
        List<BrpGroep<BrpReisdocumentInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpReisdocumentInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static List<BrpStapel<BrpReisdocumentInhoud>> createList() {
        List<BrpStapel<BrpReisdocumentInhoud>> lijst = new ArrayList<>();
        lijst.add(createStapel());
        return lijst;
    }

}
