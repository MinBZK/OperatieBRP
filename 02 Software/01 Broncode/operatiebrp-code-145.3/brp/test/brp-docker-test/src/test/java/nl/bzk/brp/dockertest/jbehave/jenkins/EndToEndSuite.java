/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AfnemerindicatieEndToEndIT.class,
        BeheerSelectieEndToEndIT.class,
        BrpIntegratieEndToEndIT.class,
        ControleerPersoonSelectieEndToEndIT.class,
        GeefDetailsPersoonEndToEndIT.class,
        GeefMedebewonersEndToEndIT.class,
        OverigeTestenEndToEndIT.class,
        SelectieEndToEndIT.class,
        SelectieMetPlaatsenEndToEndIT.class,
        SelectieMetVerwijderingEndToEndIT.class,
        StufEndToEndIT.class,
        SynchronisatieEndToEndIT.class,
        VrijBerichtEndToEndIT.class,
        ZoekPersoonEndToEndIT.class,
        ZoekPersoonOpAdresgegevensEndToEndIT.class
})
public class EndToEndSuite {
}
