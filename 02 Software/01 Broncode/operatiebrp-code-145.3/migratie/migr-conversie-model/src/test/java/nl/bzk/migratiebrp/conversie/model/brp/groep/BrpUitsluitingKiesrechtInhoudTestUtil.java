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

public class BrpUitsluitingKiesrechtInhoudTestUtil {

    public static BrpUitsluitingKiesrechtInhoud createInhoud() {
        return new BrpUitsluitingKiesrechtInhoud(BrpBooleanTest.BRP_FALSE, null);
    }

    public static BrpStapel<BrpUitsluitingKiesrechtInhoud> createStapel() {
        List<BrpGroep<BrpUitsluitingKiesrechtInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpUitsluitingKiesrechtInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
