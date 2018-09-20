/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie.AbstractSynchronisatieIntegratieTest;
import org.junit.Before;

public abstract class AbstractSynchronisatieServiceIntegratieTest extends AbstractSynchronisatieIntegratieTest {

    protected boolean komtAnummerHistorischVoor(final long anummer) {
        final List<BrpPersoonslijst> result = getBrpDalService().zoekPersonenOpHistorischAnummer(anummer);
        return result != null && !result.isEmpty();
    }

    @Before
    public void setupSynchronisatieLogging() {
        SynchronisatieLogging.init();
    }

}
