/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;


public class RelatieUtilsTest {

    @Test
    public void testHaalMoederUitPersoon() {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();

        final PersoonBericht kind = new PersoonBericht();
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setRelatie(familierechtelijkeBetrekking);
        kindBetr.setPersoon(kind);

        final PersoonBericht moeder = new PersoonBericht();
        final OuderBericht moederBetr = new OuderBericht();
        moederBetr.setPersoon(moeder);
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);

        familierechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);

        kind.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        kind.getBetrokkenheden().add(kindBetr);

        Assert.assertEquals(moeder, RelatieUtils.haalMoederUitPersoon(kind));

        // Geen indicatie zou niks moeten opleveren.
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(null);
        Assert.assertNull(RelatieUtils.haalMoederUitPersoon(kind));

        // Geem ouderschap zou niks moeten opleveren
        moederBetr.setOuderschap(null);
        Assert.assertNull(RelatieUtils.haalMoederUitPersoon(kind));
    }

    @Test
    public void testHaalAlleMoedersUitRelatie() {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        Assert.assertTrue(RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());

        final PersoonBericht kind = new PersoonBericht();
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setRelatie(familierechtelijkeBetrekking);
        kindBetr.setPersoon(kind);

        final PersoonBericht moeder = new PersoonBericht();
        final OuderBericht moederBetr = new OuderBericht();
        moederBetr.setPersoon(moeder);
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);

        familierechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);

        kind.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        kind.getBetrokkenheden().add(kindBetr);

        Assert.assertEquals(moeder, RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).get(0));

        // Geen indicatie zou niks moeten opleveren.
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(null);

        Assert.assertTrue(RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());

        // Geem ouderschap zou niks moeten opleveren
        moederBetr.setOuderschap(null);
        Assert.assertTrue(RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());
    }

    @Test
    public void testHaalAlleNietMoedersUitRelatie() {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        Assert.assertTrue(RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());

        final PersoonBericht kind = new PersoonBericht();
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setRelatie(familierechtelijkeBetrekking);
        kindBetr.setPersoon(kind);

        final PersoonBericht moeder = new PersoonBericht();
        final OuderBericht moederBetr = new OuderBericht();
        moederBetr.setPersoon(moeder);
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);

        familierechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);

        kind.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        kind.getBetrokkenheden().add(kindBetr);

        Assert.assertTrue(RelatieUtils.haalAlleNietMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());

        // Indicatie weghalen
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(null);
        Assert.assertEquals(moeder, RelatieUtils.haalAlleNietMoedersUitRelatie(familierechtelijkeBetrekking).get(0));
    }

    @Test
    public void testHaalKindUitRelatie() {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        Assert.assertTrue(RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());

        final PersoonBericht kind = new PersoonBericht();
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setRelatie(familierechtelijkeBetrekking);
        kindBetr.setPersoon(kind);

        final PersoonBericht moeder = new PersoonBericht();
        final OuderBericht moederBetr = new OuderBericht();
        moederBetr.setPersoon(moeder);
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);

        familierechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);

        kind.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        kind.getBetrokkenheden().add(kindBetr);

        Assert.assertEquals(kind, RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekking));
    }

    @Test
    public void testHaalOudersUitRelatie() {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        Assert.assertTrue(RelatieUtils.haalAlleMoedersUitRelatie(familierechtelijkeBetrekking).isEmpty());

        final PersoonBericht kind = new PersoonBericht();
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setRelatie(familierechtelijkeBetrekking);
        kindBetr.setPersoon(kind);

        final PersoonBericht moeder = new PersoonBericht();
        final OuderBericht moederBetr = new OuderBericht();
        moederBetr.setPersoon(moeder);
        moederBetr.setOuderschap(new OuderOuderschapGroepBericht());
        moederBetr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);

        final PersoonBericht vader = new PersoonBericht();
        final OuderBericht vaderBetr = new OuderBericht();
        vaderBetr.setPersoon(vader);

        familierechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);
        familierechtelijkeBetrekking.getBetrokkenheden().add(vaderBetr);

        kind.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        kind.getBetrokkenheden().add(kindBetr);

        Assert.assertEquals(2, RelatieUtils.haalOudersUitRelatie(familierechtelijkeBetrekking).size());
        Assert.assertEquals(2, RelatieUtils.haalOuderBetrokkenhedenUitRelatie(familierechtelijkeBetrekking).size());
    }

    @Test
    public void testZijnPersonenGehuwd() {
        final PersoonHisVolledigImpl johnnyJordaan = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigImpl partner =
            johnnyJordaan.getHuwelijkGeregistreerdPartnerschappen().iterator().next().geefPartnerVan(johnnyJordaan).getPersoon();

        final PersoonView johnnyView = new PersoonView(johnnyJordaan);
        final PersoonView partnerView = new PersoonView(partner);
        final boolean resultaat = RelatieUtils.zijnPersonenGehuwd(johnnyView, partnerView);

        Assert.assertFalse(resultaat);
    }

    @Test
    public void testZijnPersonenGeregistreerdPartner() {
        final PersoonHisVolledigImpl johnnyJordaan = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigImpl roxanne =
            johnnyJordaan.getHuwelijkGeregistreerdPartnerschappen().iterator().next().geefPartnerVan(johnnyJordaan).getPersoon();

        final PersoonView johnnyView = new PersoonView(johnnyJordaan);
        final PersoonView roxanneView = new PersoonView(roxanne);
        final boolean resultaat = RelatieUtils.zijnPersonenGeregistreerdPartner(johnnyView, roxanneView);

        Assert.assertTrue(resultaat);
    }

    @Test
    public void testHebbenPersonenRelatieVanSoort() {
        final PersoonHisVolledigImpl johnnyJordaan = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigImpl roxanne =
            johnnyJordaan.getHuwelijkGeregistreerdPartnerschappen().iterator().next().geefPartnerVan(johnnyJordaan).getPersoon();

        final PersoonView johnnyView = new PersoonView(johnnyJordaan);
        final PersoonView roxanneView = new PersoonView(roxanne);
        final boolean resultaat = RelatieUtils.hebbenPersonenRelatieVanSoort(johnnyView, roxanneView, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        Assert.assertTrue(resultaat);
    }

}
