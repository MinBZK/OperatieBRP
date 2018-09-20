/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor definitie regel BRBY0009.
 */
public class BRBY0009Test {

    @Mock
    private BijhoudingsBericht bericht;

    private List<ActieBericht> acties;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        acties = new ArrayList<>();
        final AdministratieveHandelingBericht administratieveHandeling = new HandelingGeboorteInNederlandBericht();
        Mockito.when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        ReflectionTestUtils.setField(administratieveHandeling, "acties", acties);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBepaalHoofdPersonenHoofdActieOntbreekt() {
        new BRBY0009().bepaalHoofdPersonen(bericht);
    }

    @Test
    public void testBepaalHoofdPersonenHoofdActieHeeftPersoonAlsRootObject() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setRootObject(persoonBericht);
        acties.add(actie);

        final List<PersoonBericht> persoonBerichts = new BRBY0009().bepaalHoofdPersonen(bericht);
        Assert.assertFalse(persoonBerichts.isEmpty());
        Assert.assertEquals(persoonBericht, persoonBerichts.get(0));
    }

    @Test
    public void testBepaalHoofdPersonenHoofdActieHeeftHuwelijkGeregistreerdPartnerschapAlsRootObject() {
        final PersoonBericht partner1 = new PersoonBericht();
        partner1.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht partner2 = new PersoonBericht();
        partner2.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PartnerBericht partner1Betr = new PartnerBericht();
        partner1Betr.setPersoon(partner1);

        final PartnerBericht partner2Betr = new PartnerBericht();
        partner2Betr.setPersoon(partner2);

        final HuwelijkBericht huwelijk = new HuwelijkBericht();
        huwelijk.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        huwelijk.getBetrokkenheden().add(partner1Betr);
        huwelijk.getBetrokkenheden().add(partner2Betr);

        final ActieBericht actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie.setRootObject(huwelijk);

        acties.add(actie);

        final List<PersoonBericht> persoonBerichts = new BRBY0009().bepaalHoofdPersonen(bericht);
        Assert.assertTrue(persoonBerichts.size() == 2);
        Assert.assertEquals(partner1, persoonBerichts.get(0));
        Assert.assertEquals(partner2, persoonBerichts.get(1));
    }

    @Test
    public void
    testBepaalHoofdPersonenHoofdActieHeeftHuwelijkGeregistreerdPartnerschapAlsRootObjectEenPartnerNietIngeschrevene()
    {
        final PersoonBericht partner1 = new PersoonBericht();
        partner1.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        final PersoonBericht partner2 = new PersoonBericht();
        partner2.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PartnerBericht partner1Betr = new PartnerBericht();
        partner1Betr.setPersoon(partner1);

        final PartnerBericht partner2Betr = new PartnerBericht();
        partner2Betr.setPersoon(partner2);

        final HuwelijkBericht huwelijk = new HuwelijkBericht();
        huwelijk.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        huwelijk.getBetrokkenheden().add(partner1Betr);
        huwelijk.getBetrokkenheden().add(partner2Betr);

        final ActieBericht actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie.setRootObject(huwelijk);

        acties.add(actie);

        final List<PersoonBericht> persoonBerichts = new BRBY0009().bepaalHoofdPersonen(bericht);
        Assert.assertTrue(persoonBerichts.size() == 1);
        Assert.assertEquals(partner2, persoonBerichts.get(0));
    }

    @Test
    public void testBepaalHoofdPersonenHoofdActieHeeftFamilieRechtelijkeBetrekkingAlsRootObject() {
        final PersoonBericht ouder1 = new PersoonBericht();
        ouder1.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht ouder2 = new PersoonBericht();
        ouder2.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht kind = new PersoonBericht();
        kind.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final OuderBericht ouder1Betr = new OuderBericht();
        ouder1Betr.setPersoon(ouder1);
        final OuderBericht ouder2Betr = new OuderBericht();
        ouder2Betr.setPersoon(ouder2);
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setPersoon(kind);

        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(ouder1Betr);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(ouder2Betr);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObject(familierechtelijkeBetrekkingBericht);

        acties.add(actie);

        final List<PersoonBericht> persoonBerichts = new BRBY0009().bepaalHoofdPersonen(bericht);
        Assert.assertTrue(persoonBerichts.size() == 1);
        Assert.assertEquals(kind, persoonBerichts.get(0));
    }

    @Test
    public void
    testBepaalHoofdPersonenHoofdActieHeeftFamilieRechtelijkeBetrekkingAlsRootObjectKindIsNietIngeschrevene()
    {
        final PersoonBericht ouder1 = new PersoonBericht();
        ouder1.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht kind = new PersoonBericht();
        kind.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final OuderBericht ouder1Betr = new OuderBericht();
        ouder1Betr.setPersoon(ouder1);
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setPersoon(kind);

        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(ouder1Betr);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObject(familierechtelijkeBetrekkingBericht);

        acties.add(actie);

        final List<PersoonBericht> persoonBerichts = new BRBY0009().bepaalHoofdPersonen(bericht);
        Assert.assertTrue(persoonBerichts.size() == 1);
        Assert.assertEquals(ouder1, persoonBerichts.get(0));
    }

}
