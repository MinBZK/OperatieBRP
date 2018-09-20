/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisAfnemerindicatieBlobRepository;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisPersTabelRepository;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieReedsAanwezigExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatiesZonderRegelsServiceImplTest {

    private static final int    PERSOON_ID      = 12345;
    private static final int    DIENST_DUMMY_ID = 1;
    private static final int    DIENST_ID       = 2;
    private static final String ID              = "iD";

    @Mock
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Mock
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Mock
    private PartijService partijService;

    @Mock
    private BlobifierService blobifierService;

    @Mock
    private AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService;

    @Mock
    private HisPersTabelRepository hisPersTabelRepository;

    @Mock
    private HisAfnemerindicatieBlobRepository hisAfnemerindicatieBlobRepository;

    @Mock
    private nl.bzk.brp.dataaccess.repository.HisAfnemerindicatieTabelRepository hisAfnemerindicatieTabelRepository;

    @InjectMocks
    private final AfnemerindicatiesZonderRegelsService afnemerindicatiesZonderRegelsService = new AfnemerindicatiesZonderRegelsServiceImpl();

    private final int leveringautorisatieId = 12345;
    private final int toegangLeveringautorisatieId = 12345;
    private final int partijCode            = 5324;

    private final PersoonHisVolledigImpl persoonHisVolledig   = TestPersoonJohnnyJordaan.maak();
    private final Partij                                      partij            = TestPartijBuilder.maker().metCode(partijCode).maak();
    private final Leveringsautorisatie   leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(leveringautorisatieId).maak();
    private final ToegangLeveringsautorisatie   toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metId(toegangLeveringautorisatieId).
            metLeveringsautorisatie(leveringsautorisatie).metGeautoriseerde(new PartijRol(partij, Rol.AFNEMER, DatumAttribuut.gisteren(), null)).maak();
    private final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerIndicaties = new HashSet<>();

    @Before
    public final void setup() {
        when(persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID)).thenReturn(persoonHisVolledig);
        when(toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId)).thenReturn(toegangLeveringsautorisatie);

        final Dienst dienstInhoudEnVerval = TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
        Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienstInhoudEnVerval).maak();
        ReflectionTestUtils.setField(dienstInhoudEnVerval, ID, DIENST_ID);
        final Dienst dienstDummy = TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
        ReflectionTestUtils.setField(dienstDummy, ID, DIENST_DUMMY_ID);
        leveringsautorisatie.setDienstbundels(dienstbundel);

        when(hisPersTabelRepository.bestaatPersoonMetId(PERSOON_ID)).thenReturn(Boolean.TRUE);

        when(hisAfnemerindicatieBlobRepository.leesGenormaliseerdModelVoorNieuweBlob(PERSOON_ID)).thenReturn(afnemerIndicaties);
        afnemerIndicaties.addAll(persoonHisVolledig.getAfnemerindicaties());
        persoonHisVolledig.setAfnemerindicaties(afnemerIndicaties);

        final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerIndicatie =
            new PersoonAfnemerindicatieHisVolledigImpl(persoonHisVolledig, new PartijAttribuut(partij),
                new LeveringsautorisatieAttribuut(leveringsautorisatie));
        when(hisAfnemerindicatieTabelRepository
            .maakNieuweAfnemerIndicatie(any(Integer.class), any(PartijAttribuut.class), any(LeveringsautorisatieAttribuut.class)))
            .thenReturn(persoonAfnemerIndicatie);
    }

    @Test
    public final void testPlaatsAfnemerindicatieZonderRegels() throws BrpLockerExceptie {
        afnemerindicatiesZonderRegelsService
            .plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null, DatumTijdAttribuut.nu());

        assertEquals(5, persoonHisVolledig.getAfnemerindicaties().size());
        PersoonAfnemerindicatieHisVolledigImpl nieuweAfnemerindicatieHisVolledig = null;
        for (final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig : persoonHisVolledig.getAfnemerindicaties()) {
            if (afnemerindicatieHisVolledig.getLeveringsautorisatie().getWaarde().equals(leveringsautorisatie)
                && afnemerindicatieHisVolledig.getAfnemer().getWaarde().equals(partij)
                && afnemerindicatieHisVolledig.getPersoon().equals(persoonHisVolledig))
            {
                nieuweAfnemerindicatieHisVolledig = afnemerindicatieHisVolledig;
            }
        }
        assertNotNull(nieuweAfnemerindicatieHisVolledig);
        assertEquals(1, nieuweAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getHistorie().size());
        assertNotNull(nieuweAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getActueleRecord());
        assertNull(nieuweAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next().getDatumTijdVerval());
        verify(afnemerIndicatieBlobifierService).blobify(PERSOON_ID, afnemerIndicaties);
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testPlaatsAfnemerindicatieZonderRegelsPersoonIsNull() throws BrpLockerExceptie {
        when(hisPersTabelRepository.bestaatPersoonMetId(PERSOON_ID)).thenReturn(Boolean.FALSE);

        afnemerindicatiesZonderRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null,
            null, DatumTijdAttribuut.nu());
    }

    @Test(expected = AfnemerindicatieReedsAanwezigExceptie.class)
    public final void testPlaatsAfnemerindicatieZonderRegelsIndicatieBestaatAl() throws BrpLockerExceptie {
        afnemerindicatiesZonderRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null,
            null, DatumTijdAttribuut.nu());
        afnemerindicatiesZonderRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null,
            null, DatumTijdAttribuut.nu());
    }

    @Test
    public final void testVerwijderAfnemerindicatieZonderRegels() throws BrpLockerExceptie {
        afnemerindicatiesZonderRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null,
            null, DatumTijdAttribuut.nu());
        afnemerindicatiesZonderRegelsService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID);

        assertEquals(5, persoonHisVolledig.getAfnemerindicaties().size());
        PersoonAfnemerindicatieHisVolledigImpl verwijderdeAfnemerindicatieHisVolledig = null;
        for (final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig : persoonHisVolledig.getAfnemerindicaties()) {
            if (afnemerindicatieHisVolledig.getLeveringsautorisatie().getWaarde().equals(leveringsautorisatie)
                && afnemerindicatieHisVolledig.getAfnemer().getWaarde().equals(partij)
                && afnemerindicatieHisVolledig.getPersoon().equals(persoonHisVolledig))
            {
                verwijderdeAfnemerindicatieHisVolledig = afnemerindicatieHisVolledig;
            }
        }
        assertNotNull(verwijderdeAfnemerindicatieHisVolledig);
        assertEquals(1, verwijderdeAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getHistorie().size());
        assertNull(verwijderdeAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getActueleRecord());
        assertNotNull(verwijderdeAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next()
            .getDatumTijdVerval());
        verify(afnemerIndicatieBlobifierService, times(2)).blobify(any(Integer.class), eq(afnemerIndicaties));
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testVerwijderAfnemerindicatieZonderRegelsNietGevondenLeveringsautorisatie() throws BrpLockerExceptie {
        afnemerindicatiesZonderRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID,
            null, null, DatumTijdAttribuut.nu());

        when(toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(Mockito.anyInt()))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LEVERINGSAUTORISATIENAAM, null, null));
        afnemerindicatiesZonderRegelsService.verwijderAfnemerindicatie(-10,
            PERSOON_ID, DIENST_ID);
    }
}
