/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import junit.framework.Assert;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
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
        ReflectionTestUtils.setField(actieFactory, "registratieNationaliteitUitvoerder", new RegistratieNationaliteitUitvoerder());
        ReflectionTestUtils.setField(actieFactory, "huwelijkPartnerschapUitvoerder", new HuwelijkPartnerschapUitvoerder());
        ReflectionTestUtils.setField(actieFactory, "wijzigNaamgebruikUitvoerder", new WijzigNaamgebruikUitvoerder());
        ReflectionTestUtils.setField(actieFactory, "correctieAdresNLUitvoerder", new CorrectieAdresNLUitvoerder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActieUitvoerderVoorDummy() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.DUMMY);

        actieFactory.getActieUitvoerder(actie);
    }

    @Test
    public void testGetActieUitvoerderVoorVerhuizing() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.VERHUIZING);

        AbstractActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof VerhuizingUitvoerder);
    }

    @Test
    public void testGetActieUitvoerderVoorCorrectieAdresNL() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.CORRECTIE_ADRES_NL);

        AbstractActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof CorrectieAdresNLUitvoerder);
    }

    @Test
    public void testGetActieUitvoerderVoorInschrijvingGeboorte() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);

        AbstractActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof InschrijvingGeboorteUitvoerder);
    }

    @Test
    public void testGetActieUitvoerderVoorRegistratieNationaliteit() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);

        AbstractActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof RegistratieNationaliteitUitvoerder);
    }

    @Test
    public void testGetActieUitvoerderVoorHuwelijk() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.HUWELIJK);

        AbstractActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof HuwelijkPartnerschapUitvoerder);
    }

    @Test
    public void testGetActieWijzigNaamgebruik() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.WIJZIGING_NAAMGEBRUIK);

        AbstractActieUitvoerder actieUitvoerder = actieFactory.getActieUitvoerder(actie);

        Assert.assertTrue(actieUitvoerder instanceof WijzigNaamgebruikUitvoerder);
    }

}
