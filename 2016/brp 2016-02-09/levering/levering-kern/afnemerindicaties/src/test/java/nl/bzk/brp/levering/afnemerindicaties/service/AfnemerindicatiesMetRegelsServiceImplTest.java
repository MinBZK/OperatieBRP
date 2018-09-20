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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisAfnemerindicatieBlobRepository;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisPersTabelRepository;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.context.AfnemerindicatieRegelContext;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.afnemerindicaties.regels.AfnemerindicatiesBedrijfsregelManager;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatiesMetRegelsServiceImplTest {

    private static final int PERSOON_ID = 12345;
    private static final int DIENST_ID  = 1;

    @Mock
    private PersoonHisVolledigRepository                                        persoonHisVolledigRepository;
    @Mock
    private BlobifierService                                                    blobifierService;
    @Mock
    private AfnemerIndicatieBlobifierService                                    afnemerIndicatieBlobifierService;
    @Mock
    private ToegangLeveringsautorisatieService                                  toegangLeveringsautorisatieService;
    @Mock
    private AfnemerindicatiesBedrijfsregelManager                               bedrijfsregelManager;
    @Mock
    private HisPersTabelRepository                                              hisPersTabelRepository;
    @Mock
    private HisAfnemerindicatieBlobRepository                                   hisAfnemerindicatieBlobRepository;
    @Mock
    private nl.bzk.brp.dataaccess.repository.HisAfnemerindicatieTabelRepository hisAfnemerindicatieTabelRepository;

    @InjectMocks
    private final AfnemerindicatiesZonderRegelsService afnemerindicatiesZonderRegelsService = new AfnemerindicatiesZonderRegelsServiceImpl();
    @InjectMocks
    private final AfnemerindicatiesMetRegelsService afnemerindicatiesMetRegelsService = new AfnemerindicatiesMetRegelsServiceImpl();

    private final int toegangLeveringautorisatieId = 1234;
    private final int partijCode            = 5324;

    private final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();

    private Leveringsautorisatie leveringsautorisatie;
    private ToegangLeveringsautorisatie toegangLeveringsautorisatie;
    private final List<Bedrijfsregel> voorVerwerkingRegels = new ArrayList<>();

    private final Partij partij = TestPartijBuilder.maker().metCode(partijCode).maak();
    private Bedrijfsregel voorVerwerkingRegel;

    private Bedrijfsregel voorVerwerkingAutorisatieRegel;

    private final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerIndicaties = new HashSet<>();

    @Before
    public final void setup() {
        leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(toegangLeveringautorisatieId).maak();
        toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(leveringsautorisatie).
                metGeautoriseerde(new PartijRol(partij, Rol.AFNEMER, DatumAttribuut.vandaag(), null)).maak();
        final Dienst dienstInhoudEnVerval = TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
        ReflectionTestUtils.setField(dienstInhoudEnVerval, "iD", DIENST_ID);
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienstInhoudEnVerval).maak();
        leveringsautorisatie.setDienstbundels(dienstbundel);

        when(persoonHisVolledigRepository.leesGenormalizeerdModel(PERSOON_ID)).thenReturn(persoonHisVolledig);
        when(toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId)).thenReturn(toegangLeveringsautorisatie);
        when(toegangLeveringsautorisatieService.geefToegangleveringautorisaties(anyInt(), anyInt()))
            .thenReturn(toegangLeveringsautorisatie);

        doReturn(voorVerwerkingRegels).when(bedrijfsregelManager).getUitTeVoerenRegelsVoorVerwerking(EffectAfnemerindicaties.PLAATSING);
        doReturn(voorVerwerkingRegels).when(bedrijfsregelManager).getUitTeVoerenRegelsVoorVerwerking(EffectAfnemerindicaties.VERWIJDERING);

        final RegelParameters regelParameters = new RegelParameters(new MeldingtekstAttribuut(""), SoortMelding.DUMMY, Regel.BRLV0001, null,
            SoortFout.VERWERKING_KAN_DOORGAAN);
        when(bedrijfsregelManager.getRegelParametersVoorRegel(any(Bedrijfsregel.class)))
            .thenReturn(regelParameters);

        when(hisPersTabelRepository.bestaatPersoonMetId(PERSOON_ID)).thenReturn(Boolean.TRUE);

        when(hisAfnemerindicatieBlobRepository.leesGenormaliseerdModelVoorNieuweBlob(PERSOON_ID)).thenReturn(afnemerIndicaties);
        afnemerIndicaties.addAll(persoonHisVolledig.getAfnemerindicaties());
        persoonHisVolledig.setAfnemerindicaties(afnemerIndicaties);

        final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerIndicatie =
            new PersoonAfnemerindicatieHisVolledigImpl(persoonHisVolledig, new PartijAttribuut(partij),
                new LeveringsautorisatieAttribuut(leveringsautorisatie));
        when(hisAfnemerindicatieTabelRepository.maakNieuweAfnemerIndicatie(any(Integer.class), any(PartijAttribuut.class),
            any(LeveringsautorisatieAttribuut.class))).thenReturn(persoonAfnemerIndicatie);
    }

    @Test
    public final void testPlaatsAfnemerindicatieMetRegels() throws BrpLockerExceptie {
        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.plaatsAfnemerindicatie(toegangLeveringsautorisatie, PERSOON_ID, DIENST_ID, null, null);

        voorVerwerkingRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        assertEquals(0, resultaat.getMeldingen().size());
        assertEquals(5, persoonHisVolledig.getAfnemerindicaties().size());
        PersoonAfnemerindicatieHisVolledigImpl nieuweAfnemerindicatieHisVolledig = null;
        for (final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig : persoonHisVolledig.getAfnemerindicaties()) {
            if (afnemerindicatieHisVolledig != null && afnemerindicatieHisVolledig.getLeveringsautorisatie().getWaarde().equals(leveringsautorisatie)
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

    @Test
    public final void testPlaatsAfnemerindicatieMetRegelsEmptyResultDataAccessException() throws BrpLockerExceptie {
        when(toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId))
            .thenThrow(new EmptyResultDataAccessException(1));
        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null);

        voorVerwerkingRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(4, persoonHisVolledig.getAfnemerindicaties().size());
    }

    @Test
    public final void testPlaatsAfnemerindicatieMetRegelsMetFout() throws BrpLockerExceptie {
        voorVerwerkingRegel = mockVoorVerwerkingRegel(true, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null);

        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(4, persoonHisVolledig.getAfnemerindicaties().size());
        Mockito.verifyZeroInteractions(blobifierService);
    }

    @Test
    public final void testPlaatsAfnemerindicatieMetRegelsMetAutorisatieFout() throws BrpLockerExceptie {
        voorVerwerkingRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(true, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null);

        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(4, persoonHisVolledig.getAfnemerindicaties().size());
        Mockito.verifyZeroInteractions(blobifierService);
    }

    @Test
    public final void testVerwijderAfnemerindicatieMetRegels() throws BrpLockerExceptie {
        afnemerindicatiesZonderRegelsService
            .plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null, DatumTijdAttribuut.nu());

        voorVerwerkingRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID);

        assertEquals(0, resultaat.getMeldingen().size());
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
        assertNotNull(verwijderdeAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next().getDatumTijdVerval());

        verify(afnemerIndicatieBlobifierService, times(2)).blobify(any(Integer.class), eq(afnemerIndicaties));
    }

    @Test
    public final void testVerwijderAfnemerindicatieMetRegelsEmptyResultDataAccessException() throws BrpLockerExceptie {
        afnemerindicatiesZonderRegelsService
            .plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null, DatumTijdAttribuut.nu());

        voorVerwerkingRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        when(toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId))
            .thenThrow(new EmptyResultDataAccessException(1));

        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID);

        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(5, persoonHisVolledig.getAfnemerindicaties().size());
    }

    @Test
    public final void testVerwijderAfnemerindicatieMetRegelsMetFout() throws BrpLockerExceptie {
        voorVerwerkingRegel = mockVoorVerwerkingRegel(false, Regel.BRLV0001);
        voorVerwerkingAutorisatieRegel = mockVoorVerwerkingRegel(true, Regel.BRLV0014);

        voorVerwerkingRegels.add(voorVerwerkingRegel);
        voorVerwerkingRegels.add(voorVerwerkingAutorisatieRegel);

        afnemerindicatiesZonderRegelsService
            .plaatsAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID, null, null, DatumTijdAttribuut.nu());

        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, PERSOON_ID, DIENST_ID);

        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(5, persoonHisVolledig.getAfnemerindicaties().size());
        verify(afnemerIndicatieBlobifierService, times(1)).blobify(PERSOON_ID, afnemerIndicaties);

    }

    private Bedrijfsregel mockVoorVerwerkingRegel(final boolean falend, final Regel regel) {
        final Bedrijfsregel voorVerwerkingBedrijfsRegel = Mockito.mock(Bedrijfsregel.class);
        when(voorVerwerkingBedrijfsRegel.getContextType()).thenReturn(AfnemerindicatieRegelContext.class);
        when(voorVerwerkingBedrijfsRegel.getRegel()).thenReturn(regel);
        if (falend) {
            when(voorVerwerkingBedrijfsRegel.valideer(any(AfnemerindicatieRegelContext.class))).thenReturn(Bedrijfsregel.INVALIDE);
        } else {
            when(voorVerwerkingBedrijfsRegel.valideer(any(AfnemerindicatieRegelContext.class))).thenReturn(Bedrijfsregel.VALIDE);
        }
        return voorVerwerkingBedrijfsRegel;
    }

}
