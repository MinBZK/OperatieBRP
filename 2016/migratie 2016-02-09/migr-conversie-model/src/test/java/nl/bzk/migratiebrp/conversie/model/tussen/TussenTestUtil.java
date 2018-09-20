/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 */
public class TussenTestUtil {

    public static TussenStapel<? extends BrpGroepInhoud> createTussenStapel(final BrpGroepInhoud inh,final Lo3Herkomst her){
        Lo3Historie his = new Lo3Historie(null,new Lo3Datum(20010101),new Lo3Datum(20010101));
        TussenGroep<BrpGroepInhoud> tussenGroep = new TussenGroep<>(inh, his,null,her);
        List<TussenGroep<BrpGroepInhoud>> groepen = new ArrayList<>();
        groepen.add(tussenGroep);
        return new TussenStapel<>(groepen);
    }
}
