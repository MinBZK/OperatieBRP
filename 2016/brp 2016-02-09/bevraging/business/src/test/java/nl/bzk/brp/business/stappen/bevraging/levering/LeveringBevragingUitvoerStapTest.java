/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.ArrayList;
import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.business.stappen.bevraging.TestDataOpvragenPersoonBerichtUitvoerStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LeveringBevragingUitvoerStapTest {

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private BlobifierService blobifierService;

    @Mock
    private BevragingBerichtContextBasis context;

    @Mock
    private ObjectSleutelService objectSleutelService;

    @InjectMocks
    private final LeveringBevragingUitvoerStap stap =
        new LeveringBevragingUitvoerStap();

    @Before
    public final void init() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie, NietUniekeAnummerExceptie {
        Mockito.when(blobifierService.leesBlob(
            Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        Mockito.when(blobifierService.leesBlob(
            Matchers.any(AdministratienummerAttribuut.class)))
            .thenReturn(TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        Mockito.when(blobifierService.leesBlob(
            Matchers.any(Integer.class)))
            .thenReturn(TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        Mockito.when(context.getPartij()).thenReturn(
            new PartijAttribuut(TestPartijBuilder.maker().metCode(new PartijCodeAttribuut(1)).maak()));


        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.DUMMY);
        final ToegangLeveringsautorisatie maak = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie info = new Leveringinformatie(maak, la.geefDienst(SoortDienst.DUMMY));
//
        Mockito.when(context.getLeveringinformatie()).thenReturn(info);

        Mockito.when(objectSleutelService.genereerObjectSleutelString(Mockito.anyInt(),
            Mockito.anyInt())).thenReturn("foobar");
    }

    @Test
    public final void testZoekPersoonPersoonBsnGevonden() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        stap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakZoekPersoonBerichtVoorLeveringMetBsn(), context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testZoekPersoonPersoonAnrGevonden() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        stap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakZoekPersoonBerichtVoorLeveringMetAnummer(), context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testZoekPersoonPersoonPostcodeVindtNiets() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        stap.voerStapUit(
            nl.bzk.brp.business.stappen.bevraging.TestDataOpvragenPersoonBerichtUitvoerStap
                .maakZoekPersoonBerichtVoorBijhoudingMetPostcode(), context, resultaat);

        assertNietsGevondenMelding(resultaat);
    }

    @Test
    public final void testZoekPersoonPersoonBsnNietGevonden() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        Mockito.when(blobifierService.leesBlob(
            Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(null);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        stap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakZoekPersoonBerichtVoorBijhoudingMetBsn(), context, resultaat);

        assertNietsGevondenMelding(resultaat);
    }

    @Test
    public final void testZoekPersoonPersoonAnrNietGevonden() throws PersoonNietAanwezigExceptie, NietUniekeAnummerExceptie {
        Mockito.when(blobifierService.leesBlob(
            Matchers.any(AdministratienummerAttribuut.class)))
            .thenReturn(null);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        stap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakZoekPersoonBerichtVoorBijhoudingMetAnummer(), context, resultaat);

        assertNietsGevondenMelding(resultaat);

    }

    @Test
    public final void testVoerStapUitDetailsPersoon() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        stap.voerStapUit(maakGeefDetailsPersoonBericht(123456782), context, resultaat);
        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVoerStapUitZoekPersoon() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        stap.voerStapUit(maakZoekPersoonBericht(123456782), context, resultaat);
        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVoerStapUitOnbekendBericht() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        stap.voerStapUit(new nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonBericht(), context, resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public final void testObjectSleutelsZoekPersoon() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        stap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakZoekPersoonBerichtVoorLeveringMetBsn(), context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());

        //Verifieer aanroep objectSleutel service.
        Mockito.verify(objectSleutelService, Mockito.atLeast(1)).genereerObjectSleutelString(Mockito.anyInt(),
            Mockito.anyInt());

        final PersoonHisVolledigView gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        Assert.assertEquals("foobar", gevondenPersoon.getObjectSleutel());

        //Verifieer objectsleutels op de betrokken personen
        int i = 0;
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : gevondenPersoon.getBetrokkenheden()) {
            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                Assert.assertEquals("foobar", ((PersoonHisVolledigView) (betrokkenheidHisVolledig.getPersoon())).getObjectSleutel());
                i++;
            }
        }
        Assert.assertEquals("3 betrokkenheden.", 3, i);
    }

    private void assertNietsGevondenMelding(final BevragingResultaat resultaat) {
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
        Assert.assertEquals(0, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    private GeefDetailsPersoonBericht maakGeefDetailsPersoonBericht(final int bsn) {
        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        final BerichtZoekcriteriaPersoonGroepBericht criteria = new BerichtZoekcriteriaPersoonGroepBericht();
        criteria.setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
        bericht.setZoekcriteriaPersoon(criteria);
        return bericht;
    }

    private ZoekPersoonBericht maakZoekPersoonBericht(final int bsn) {
        final ZoekPersoonBericht bericht = new ZoekPersoonBericht();
        final BerichtZoekcriteriaPersoonGroepBericht criteria = new BerichtZoekcriteriaPersoonGroepBericht();
        criteria.setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
        bericht.setZoekcriteriaPersoon(criteria);
        return bericht;
    }

}
