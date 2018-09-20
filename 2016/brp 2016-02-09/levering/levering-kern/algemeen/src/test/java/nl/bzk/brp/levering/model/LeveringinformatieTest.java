/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.model;

import junit.framework.TestCase;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeveringinformatieTest extends TestCase{

    @Test
    public void test() {

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().maak();
        final Dienst dienst = TestDienstBuilder.dummy();
        final Leveringinformatie li = new Leveringinformatie(tla, dienst);

        assertEquals(dienst, li.getDienst());
        assertEquals(SoortDienst.DUMMY, dienst.getSoort());
        assertEquals(tla, li.getToegangLeveringsautorisatie());
    }
}
