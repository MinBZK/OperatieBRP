/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.ArrayList;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 */
public class TussenRelatieTestUtil {

    public static TussenRelatie createTussenRelatie(BrpSoortRelatieCode sr, BrpSoortBetrokkenheidCode sb) {
        Lo3Herkomst her = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        ArrayList<TussenBetrokkenheid> betrokkenheid = new ArrayList<>();
        betrokkenheid.add(TussenBetrokkenheidTest.createTussenBetrokkenheid(sb));
        return new TussenRelatie(
                sr,
                sb,
                betrokkenheid,
                (TussenStapel<BrpRelatieInhoud>) TussenTestUtil.createTussenStapel(BrpRelatieInhoudTestUtil.createInhoud(), her),
                (TussenStapel<BrpIstRelatieGroepInhoud>) TussenTestUtil.createTussenStapel(BrpIstRelatieGroepInhoudTest.createInhoud(), her),
                (TussenStapel<BrpIstRelatieGroepInhoud>) TussenTestUtil.createTussenStapel(BrpIstRelatieGroepInhoudTest.createInhoud(), her),
                null,
                null,
                null);
    }

}
