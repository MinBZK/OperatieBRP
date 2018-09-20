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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

public class BrpVerificatieInhoudTest {

    public static BrpVerificatieInhoud createInhoud() {
        return new BrpVerificatieInhoud(BrpPartijCode.MINISTER, new BrpString("een soort"), new BrpDatum(20110101, null));
    }

    public static BrpVerificatieInhoud createInhoud(BrpPartijCode partij ) {
        return new BrpVerificatieInhoud(partij, new BrpString("een soort"), new BrpDatum(20110101, null));
    }

    public static BrpStapel<BrpVerificatieInhoud> createStapel() {
        List<BrpGroep<BrpVerificatieInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpVerificatieInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static List<BrpStapel<BrpVerificatieInhoud>> createList() {
        List<BrpStapel<BrpVerificatieInhoud>> lijst = new ArrayList<>();
        lijst.add(createStapel());
        return lijst;
    }
}
