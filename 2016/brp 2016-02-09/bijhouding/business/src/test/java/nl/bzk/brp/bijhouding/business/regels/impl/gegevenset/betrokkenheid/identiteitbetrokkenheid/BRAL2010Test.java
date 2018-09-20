/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.betrokkenheid.identiteitbetrokkenheid;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BRAL2010Test {

    private BRAL2010 bral2010;

    @Before
    public void init() {
        bral2010 = new BRAL2010();
    }

    @Test
    public void testRegistrateerGeboorteCompleet() {
        final Actie actie = maakActie(20120101);
        final RelatieBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht kind = maakPersoonBericht(20120101);
        final KindBericht kindBericht = new KindBericht();
        kindBericht.setPersoon(kind);

        final PersoonBericht ouder1 = maakPersoonBericht(19830101);
        final OuderBericht ouder1Bericht = new OuderBericht();
        ouder1Bericht.setPersoon(ouder1);

        final PersoonBericht ouder2 = maakPersoonBericht(19840505);
        final OuderBericht ouder2Bericht = new OuderBericht();
        ouder2Bericht.setPersoon(ouder2);

        relatie.getBetrokkenheden().add(kindBericht);
        relatie.getBetrokkenheden().add(ouder1Bericht);
        relatie.getBetrokkenheden().add(ouder2Bericht);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, relatie, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRegistrateerGeboorteMetBetrokkenheidZonderPersoon() {
        final Actie actie = maakActie(20120101);
        final RelatieBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht kind = maakPersoonBericht(20120101);
        final KindBericht kindBericht = new KindBericht();
        kindBericht.setPersoon(kind);

        // betrokkenheid ouder zonder verwijzing naar persoon
        final OuderBericht ouder1Bericht = new OuderBericht();

        relatie.getBetrokkenheden().add(kindBericht);
        relatie.getBetrokkenheden().add(ouder1Bericht);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, relatie, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRegistrateerHuwelijkCompleet() {
        final Actie actie = maakActie(20120101);
        final RelatieBericht relatie = new HuwelijkBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht bruid = maakPersoonBericht(19830101);
        final PartnerBericht bruidBericht = new PartnerBericht();
        bruidBericht.setPersoon(bruid);

        final PersoonBericht bruidegom = maakPersoonBericht(19840505);
        final PartnerBericht bruidegomBericht = new PartnerBericht();
        bruidegomBericht.setPersoon(bruidegom);

        relatie.getBetrokkenheden().add(bruidBericht);
        relatie.getBetrokkenheden().add(bruidegomBericht);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, relatie, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRegistrateerHuwelijkMetBetrokkenheidZonderPersoon() {
        final Actie actie = maakActie(20120101);
        final RelatieBericht relatie = new HuwelijkBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht bruid = maakPersoonBericht(19830101);
        final PartnerBericht bruidBericht = new PartnerBericht();
        bruidBericht.setPersoon(bruid);

        // betrokkenheid ouder zonder verwijzing naar persoon
        final PartnerBericht bruidegomBericht = new PartnerBericht();

        relatie.getBetrokkenheden().add(bruidBericht);
        relatie.getBetrokkenheden().add(bruidegomBericht);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, relatie, actie, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
        Assert.assertEquals(bruidegomBericht, berichtEntiteits.get(0));
    }


    @Test
    public void testGetRegel() throws Exception {
        Assert.assertEquals(Regel.BRAL2010, bral2010.getRegel());
    }

    /**************************************************************************
     * Test gevallen voor het dekken van ongeldige condities.
     **************************************************************************/

    @Test
    public void testBerichtZonderBetrokkenheden() {
        final Actie actie = maakActie(20120101);
        final RelatieBericht relatie = new HuwelijkBericht();
        relatie.setBetrokkenheden(null);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, relatie, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonBericht() {
        final Actie actie = maakActie(20120101);
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, persoon, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonGeenBetrokkenheden() {
        final Actie actie = maakActie(20120101);
        final PersoonBericht persoon = new PersoonBericht();

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, persoon, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonMetBetrokkenhedenGeenRelatie() {
        final Actie actie = maakActie(20120101);
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        persoon.getBetrokkenheden().add(new KindBericht());

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, persoon, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonMetBetrokkenhedenMetRelatieGeenBetrokkenheden() {
        final Actie actie = maakActie(20120101);
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final KindBericht kind = new KindBericht();
        kind.setRelatie(new FamilierechtelijkeBetrekkingBericht());
        persoon.getBetrokkenheden().add(kind);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, persoon, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonMetBetrokkenhedenMetRelatieWelBetrokkenheden() {
        final Actie actie = maakActie(20120101);
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final KindBericht kind = new KindBericht();
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        kind.setRelatie(familie);
        persoon.getBetrokkenheden().add(kind);

        final List<BerichtEntiteit> berichtEntiteits =
                bral2010.voerRegelUit(null, persoon, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigTypeRootObject() {
        bral2010.voerRegelUit(null, Mockito.mock(BerichtRootObject.class), null, null);
    }


    /**
     * Maak een persoon bericht.
     *
     * @param datumGeboorte geboorte datum van persoon
     * @return een persoonbericht met de geboorte groep en datum
     */
    private PersoonBericht maakPersoonBericht(final Integer datumGeboorte) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
        return persoon;
    }

    /**
     * Maakt een actie.
     *
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @return actie
     */
    private Actie maakActie(final Integer datumAanvangGeldigheid) {
        final ActieBericht actie = new ActieRegistratieNaamgebruikBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        return actie;
    }

}
