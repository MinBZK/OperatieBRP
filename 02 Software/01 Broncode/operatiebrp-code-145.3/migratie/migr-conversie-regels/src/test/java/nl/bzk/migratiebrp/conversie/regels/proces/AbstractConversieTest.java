/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractConversieTest extends AbstractComponentTest {

    @Inject
    protected ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Inject
    protected ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    @Inject
    protected PreconditiesService preconditiesService;

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

}
