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

public class BrpOnderCurateleIndicatieInhoudTest {
    public static BrpOnderCurateleIndicatieInhoud createInhoud() {
        return new BrpOnderCurateleIndicatieInhoud(new BrpBoolean(Boolean.FALSE), null, null);
    }

    public static BrpStapel<BrpOnderCurateleIndicatieInhoud> createStapel() {
        List<BrpGroep<BrpOnderCurateleIndicatieInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpOnderCurateleIndicatieInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
