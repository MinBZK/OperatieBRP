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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import org.junit.Assert;
import org.junit.Test;

public class BrpMigratieInhoudTest {

    @Test
    public void test(){
        final BrpMigratieInhoud inhoud = createInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.IMMIGRATIE,inhoud.getSoortMigratieCode());
    }

    public static BrpMigratieInhoud createInhoud() {
        return new BrpMigratieInhoud(BrpSoortMigratieCode.IMMIGRATIE, null, null, null, null, null, null, null, null, null);
    }

    public static BrpStapel<BrpMigratieInhoud> createStapel() {
        List<BrpGroep<BrpMigratieInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpMigratieInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
