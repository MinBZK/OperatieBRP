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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import org.junit.Assert;
import org.junit.Test;

public class BrpIstRelatieGroepInhoudTest {
    @Test
    public void test(){
        final BrpIstRelatieGroepInhoud inhoud = this.createInhoud();
        Assert.assertEquals(20000101,inhoud.getRubriek6210DatumIngangFamilierechtelijkeBetrekking().getWaarde().intValue());
    }

    public static BrpIstRelatieGroepInhoud createInhoud() {
        return new BrpIstRelatieGroepInhoud(
                BrpIstStandaardGroepInhoudTestUtil.createInhoud(),
                new BrpInteger(20000101),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static BrpStapel<BrpIstRelatieGroepInhoud> createStapel() {
        List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIstRelatieGroepInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
