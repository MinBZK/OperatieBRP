/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;

import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test klasse ten behoeve van het testen van de functionaliteit zoals geboden in de {@link nl.bzk.brp.model.hisvolledig.util.RelatieHisVolledigUtil}
 * klasse.
 */
public class RelatieHisVolledigUtilTest {

    private PersoonHisVolledigImpl                      opa;
    private PersoonHisVolledigImpl                      oma;
    private PersoonHisVolledigImpl                      vader;
    private PersoonHisVolledigImpl                      moeder;
    private PersoonHisVolledigImpl                      kind;
    private FamilierechtelijkeBetrekkingHisVolledigImpl familieRelatieOpaOmaVader;
    private FamilierechtelijkeBetrekkingHisVolledigImpl familieRelatieVaderMoederKind;

    @Before
    public void init() {
        final ActieModel actie1 =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                        new DatumEvtDeelsOnbekendAttribuut(
                                19400909), null, new DatumTijdAttribuut(new Date()), null);
        final ActieModel actie2 =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                        new DatumEvtDeelsOnbekendAttribuut(
                                19700606), null, new DatumTijdAttribuut(new Date()), null);
        final ActieModel actie3 =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                        new DatumEvtDeelsOnbekendAttribuut(
                                20120101), null, new DatumTijdAttribuut(new Date()), null);

        opa =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwIdentificatienummersRecord(actie1)
                        .burgerservicenummer(999999999).eindeRecord().build();
        oma =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwIdentificatienummersRecord(actie1)
                        .burgerservicenummer(888888888).eindeRecord().build();
        vader =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwIdentificatienummersRecord(actie2)
                        .burgerservicenummer(123456789).eindeRecord().build();
        moeder =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwIdentificatienummersRecord(actie2)
                        .burgerservicenummer(987654321).eindeRecord().build();
        kind =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwIdentificatienummersRecord(actie3)
                        .burgerservicenummer(111111111).eindeRecord().build();

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(opa, oma, vader, 19700606, actie2);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(vader, moeder, kind, 20100101, actie3);

        familieRelatieVaderMoederKind =
                (FamilierechtelijkeBetrekkingHisVolledigImpl) moeder.getBetrokkenheden().iterator().next().getRelatie();
        familieRelatieOpaOmaVader =
                (FamilierechtelijkeBetrekkingHisVolledigImpl) oma.getBetrokkenheden().iterator().next().getRelatie();

        // Zet op een van de relaties de ID zodat verschillende bomen in if statement worden geraakt
        ReflectionTestUtils.setField(familieRelatieOpaOmaVader, "iD", 2);
    }

    @Test
    public void testHaalKindUitFamilierechtelijkeBetrekking() {
        Assert.assertSame(kind,
                RelatieHisVolledigUtil.haalKindUitFamilierechtelijkeBetrekking(familieRelatieVaderMoederKind));
        Assert.assertSame(vader,
                RelatieHisVolledigUtil.haalKindUitFamilierechtelijkeBetrekking(familieRelatieOpaOmaVader));
    }

    @Test
    public void testHeeftBetrokkenheidNaarRelatie() {
        Assert.assertTrue(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(kind, familieRelatieVaderMoederKind));
        Assert.assertTrue(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(vader, familieRelatieVaderMoederKind));
        Assert.assertTrue(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(moeder, familieRelatieVaderMoederKind));
        Assert.assertFalse(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(opa, familieRelatieVaderMoederKind));
        Assert.assertFalse(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(oma, familieRelatieVaderMoederKind));

        Assert.assertFalse(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(kind, familieRelatieOpaOmaVader));
        Assert.assertTrue(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(vader, familieRelatieOpaOmaVader));
        Assert.assertFalse(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(moeder, familieRelatieOpaOmaVader));
        Assert.assertTrue(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(opa, familieRelatieOpaOmaVader));
        Assert.assertTrue(RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(oma, familieRelatieOpaOmaVader));
    }

    @Test
    public void testBepaalBetrokkenheidVanPersoonInRelatie() {
        BetrokkenheidHisVolledigImpl vaderVaderBetrokkenheid = null;
        BetrokkenheidHisVolledigImpl vaderKindBetrokkenheid = null;

        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : vader.getBetrokkenheden()) {
            if (betrokkenheidHisVolledig.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                vaderVaderBetrokkenheid = betrokkenheidHisVolledig;
            } else {
                vaderKindBetrokkenheid = betrokkenheidHisVolledig;
            }
        }

        Assert.assertSame(vaderVaderBetrokkenheid,
                RelatieHisVolledigUtil.bepaalBetrokkenheidVanPersoonInRelatie(vader, familieRelatieVaderMoederKind));
        Assert.assertSame(vaderKindBetrokkenheid,
                RelatieHisVolledigUtil.bepaalBetrokkenheidVanPersoonInRelatie(vader, familieRelatieOpaOmaVader));
        Assert.assertNull(RelatieHisVolledigUtil
                .bepaalBetrokkenheidVanPersoonInRelatie(kind, familieRelatieOpaOmaVader));
    }

    @Test
    public void testHaalOuderBetrokkenhedenUitKind() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

        final List<OuderHisVolledigImpl> resultaat = RelatieHisVolledigUtil.haalOuderBetrokkenhedenUitKind(persoonHisVolledig);

        assertEquals(1, resultaat.size());
    }

    @Test
    public void testHaalPartnerBetrokkenhedenUitPersoon() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

        final List<PartnerHisVolledigImpl> resultaat = RelatieHisVolledigUtil.haalPartnerBetrokkenhedenUitPersoon(persoonHisVolledig);

        assertEquals(1, resultaat.size());
        assertEquals(4, resultaat.get(0).getID().intValue());
    }

}
