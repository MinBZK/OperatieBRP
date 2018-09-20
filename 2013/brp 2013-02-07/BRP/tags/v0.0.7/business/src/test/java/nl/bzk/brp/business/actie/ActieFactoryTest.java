/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de ActieFactory.
 */
@RunWith(MockitoJUnitRunner.class)
public class ActieFactoryTest {

    private ActieFactory actieFactory;

    @Before
    public void init() {
        actieFactory = new ActieFactoryImpl();
        ReflectionTestUtils.setField(actieFactory, "verhuizingUitvoerder", new VerhuizingUitvoerder());
        ReflectionTestUtils.setField(actieFactory, "inschrijvingGeboorteUitvoerder", new InschrijvingGeboorteUitvoerder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActieUitvoerderVoorDummy() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.DUMMY);

        actieFactory.getActieUitvoerder(actie);
    }

    @Test
    public void testGetActieUitvoerderVoorVerhuizing() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.VERHUIZING);

        ActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof VerhuizingUitvoerder);
    }

    @Test
    public void testGetActieUitvoerderVoorInschrijvingGeboorte() {
        BRPActie actie = new BRPActie();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);

        ActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof InschrijvingGeboorteUitvoerder);
    }
}
