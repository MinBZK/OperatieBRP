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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import org.junit.Assert;
import org.junit.Test;

public class BrpDeelnameEuVerkiezingenInhoudTest {

    @Test
    public void test(){
        final BrpDeelnameEuVerkiezingenInhoud inhoud = BrpDeelnameEuVerkiezingenInhoudTest.createInhoud();
        Assert.assertFalse(inhoud.getIndicatieDeelnameEuVerkiezingen().getWaarde());
    }

    public static BrpDeelnameEuVerkiezingenInhoud createInhoud() {
        return new BrpDeelnameEuVerkiezingenInhoud(new BrpBoolean(Boolean.FALSE), new BrpDatum(20000101, null), null);
    }

    public static BrpStapel<BrpDeelnameEuVerkiezingenInhoud> createStapel() {
        List<BrpGroep<BrpDeelnameEuVerkiezingenInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpDeelnameEuVerkiezingenInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        BrpStapel<BrpDeelnameEuVerkiezingenInhoud> result = new BrpStapel<>(groepen);
        return result;
    }

}
