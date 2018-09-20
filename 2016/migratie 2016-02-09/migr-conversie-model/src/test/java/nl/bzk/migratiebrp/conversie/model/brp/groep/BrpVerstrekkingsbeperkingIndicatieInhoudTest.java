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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBooleanTest;

public class BrpVerstrekkingsbeperkingIndicatieInhoudTest {
    public static BrpVerstrekkingsbeperkingIndicatieInhoud createInhoud() {
        return new BrpVerstrekkingsbeperkingIndicatieInhoud(BrpBooleanTest.BRP_FALSE, null, null);
    }

    public static BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> createStapel() {
        List<BrpGroep<BrpVerstrekkingsbeperkingIndicatieInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpVerstrekkingsbeperkingIndicatieInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }
}
