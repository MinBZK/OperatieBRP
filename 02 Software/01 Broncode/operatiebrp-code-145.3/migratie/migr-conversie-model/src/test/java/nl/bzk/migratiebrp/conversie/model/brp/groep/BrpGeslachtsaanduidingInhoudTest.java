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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import org.junit.Assert;
import org.junit.Test;

public class BrpGeslachtsaanduidingInhoudTest {

    @Test
    public void test(){
        final BrpGeslachtsaanduidingInhoud inhoud = BrpGeslachtsaanduidingInhoudTest.createInhoud();
        Assert.assertEquals(BrpGeslachtsaanduidingCode.MAN,inhoud.getGeslachtsaanduidingCode());
    }

    public static BrpGeslachtsaanduidingInhoud createInhoud() {
        return new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN);
    }

    public static BrpStapel<BrpGeslachtsaanduidingInhoud> createStapel() {
        List<BrpGroep<BrpGeslachtsaanduidingInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpGeslachtsaanduidingInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
