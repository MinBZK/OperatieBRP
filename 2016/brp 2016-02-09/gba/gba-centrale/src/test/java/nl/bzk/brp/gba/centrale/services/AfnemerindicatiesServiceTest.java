/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesMetRegelsService;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.internbericht.RegelMelding;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatiesServiceTest {

    @Mock
    private AfnemerindicatiesMetRegelsService afnemerindicatiesService;
    @Mock
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;
    @Mock
    private BlobifierService blobifierService;
    @Mock
    private BerichtFactory berichtFactory;
    @Mock
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private ConversietabelFactory conversietabelFactory;
    @InjectMocks
    private AfnemerindicatiesService subject;

    @Mock
    private Bericht bericht;

    @Before
    public void setupRegelConversietabel() {
        Mockito.when(conversietabelFactory.createRegelConversietabel()).thenReturn(new TestRegelConversietabel());
    }

    @Test
    public void plaatsenAfnemersindicatie() {
        // Setup
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = new ToegangLeveringsautorisatie() {
        };
        final Partij partij = new Partij(null, null, new PartijCodeAttribuut(3333), null, null, null, null, null, null);
        final PartijRol geautoriseerde = new PartijRol(partij, Rol.AFNEMER, null, null);
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "geautoriseerde", geautoriseerde);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie() {
        };
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "leveringsautorisatie", leveringsautorisatie);

        final Dienstbundel dienstbundelSpontaan = new Dienstbundel() {
        };
        final Dienst dienstMutatieLev = new Dienst() {
        };
        ReflectionTestUtils.setField(dienstMutatieLev, "dienstbundel", dienstbundelSpontaan);
        ReflectionTestUtils.setField(dienstMutatieLev, "soort", SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        ReflectionTestUtils.setField(dienstMutatieLev, "datumIngang", DatumAttribuut.gisteren());
        final Set<Dienst> dienstenSpontaan = new HashSet<>();
        dienstenSpontaan.add(dienstMutatieLev);
        ReflectionTestUtils.setField(dienstbundelSpontaan, "diensten", dienstenSpontaan);
        final Set<Dienstbundel> dienstbundels = new HashSet<>();
        dienstbundels.add(dienstbundelSpontaan);
        ReflectionTestUtils.setField(leveringsautorisatie, "dienstbundels", dienstbundels);

        Mockito.when(toegangLeveringsautorisatieRepository.haalToegangLeveringsautorisatieOp(1)).thenReturn(toegangLeveringsautorisatie);

        final BewerkAfnemerindicatieResultaat bewerkAfnemerindicatieResultaat = new BewerkAfnemerindicatieResultaat();
        Mockito.when(afnemerindicatiesService.gbaPlaatsAfnemerindicatie(toegangLeveringsautorisatie, 3, 2, null, null))
               .thenReturn(bewerkAfnemerindicatieResultaat);

        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl() {
        };
        Mockito.when(blobifierService.leesBlob(3)).thenReturn(persoon);
        Mockito.when(berichtFactory.maakAg01Bericht(persoon)).thenReturn(bericht);

        final List<String> rubrieken = Arrays.asList("1", "2");
        Mockito.when(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(dienstbundelSpontaan)).thenReturn(rubrieken);
        Mockito.when(bericht.maakUitgaandBericht()).thenReturn("MijnAg01Bericht");

        // Execute
        final String resultaat =
                subject.verwerk(
                    "{\"effectAfnemerindicatie\":\"PLAATSING\",\"toegangLeveringsautorisatieId\":1,\"dienstId\":2,\"persoonId\":3,\"referentienummer\":\"4\"}",
                    "5");

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository, Mockito.times(1)).haalToegangLeveringsautorisatieOp(1);
        Mockito.verify(afnemerindicatiesService).gbaPlaatsAfnemerindicatie(toegangLeveringsautorisatie, 3, 2, null, null);
        Mockito.verify(blobifierService).leesBlob(3);
        Mockito.verify(berichtFactory).maakAg01Bericht(persoon);
        Mockito.verify(bericht).converteerNaarLo3(Matchers.<ConversieCache>any());
        Mockito.verify(lo3FilterRubriekRepository).haalLo3FilterRubriekenVoorDienstbundel(dienstbundelSpontaan);
        Mockito.verify(bericht).filterRubrieken(rubrieken);
        Mockito.verify(bericht).maakUitgaandBericht();
        final ArgumentCaptor<MessageCreator> ag01Captor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate).send(Matchers.eq("AFNEMER-3333"), ag01Captor.capture());
        Mockito.verifyNoMoreInteractions(
            afnemerindicatiesService,
            toegangLeveringsautorisatieRepository,
            blobifierService,
            berichtFactory,
            lo3FilterRubriekRepository,
            jmsTemplate,
            bericht);

        final AfnemerindicatiesService.Ag01Bericht ag01 = (AfnemerindicatiesService.Ag01Bericht) ag01Captor.getValue();
        final String uitgaandBericht = (String) ReflectionTestUtils.getField(ag01, "uitgaandBericht");
        Assert.assertEquals("MijnAg01Bericht", uitgaandBericht);

        Assert.assertEquals("{\"referentienummer\":\"4\"}", resultaat);
    }

    @Test
    public void plaatsenAfnemersindicatieZonderSpontaan() {
        // Setup
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = new ToegangLeveringsautorisatie() {
        };
        final Partij partij = new Partij(null, null, new PartijCodeAttribuut(3333), null, null, null, null, null, null);
        final PartijRol geautoriseerde = new PartijRol(partij, Rol.AFNEMER, null, null);
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "geautoriseerde", geautoriseerde);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie() {
        };
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "leveringsautorisatie", leveringsautorisatie);

        Mockito.when(toegangLeveringsautorisatieRepository.haalToegangLeveringsautorisatieOp(1)).thenReturn(toegangLeveringsautorisatie);

        // Execute
        final String resultaat =
                subject.verwerk(
                    "{\"effectAfnemerindicatie\":\"PLAATSING\",\"toegangLeveringsautorisatieId\":1,\"dienstId\":2,\"persoonId\":3,\"referentienummer\":\"4\"}",
                    "5");

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository).haalToegangLeveringsautorisatieOp(1);
        Mockito.verifyNoMoreInteractions(
            afnemerindicatiesService,
            toegangLeveringsautorisatieRepository,
            blobifierService,
            berichtFactory,
            lo3FilterRubriekRepository,
            jmsTemplate,
            bericht);

        Assert.assertEquals("{\"foutcode\":\"R\",\"referentienummer\":\"4\"}", resultaat);
    }

    @Test
    public void plaatsenAfnemersindicatieMetMeldingen() {
        // Setup
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = new ToegangLeveringsautorisatie() {
        };
        final Partij partij = new Partij(null, null, new PartijCodeAttribuut(3333), null, null, null, null, null, null);
        final PartijRol geautoriseerde = new PartijRol(partij, Rol.AFNEMER, null, null);
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "geautoriseerde", geautoriseerde);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie() {
        };
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "leveringsautorisatie", leveringsautorisatie);

        final Dienstbundel dienstbundelSpontaan = new Dienstbundel() {
        };
        final Dienst dienstMutatieLev = new Dienst() {
        };
        ReflectionTestUtils.setField(dienstMutatieLev, "dienstbundel", dienstbundelSpontaan);
        ReflectionTestUtils.setField(dienstMutatieLev, "soort", SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        ReflectionTestUtils.setField(dienstMutatieLev, "datumIngang", DatumAttribuut.gisteren());
        final Set<Dienst> dienstenSpontaan = new HashSet<>();
        dienstenSpontaan.add(dienstMutatieLev);
        ReflectionTestUtils.setField(dienstbundelSpontaan, "diensten", dienstenSpontaan);
        final Set<Dienstbundel> dienstbundels = new HashSet<>();
        dienstbundels.add(dienstbundelSpontaan);
        ReflectionTestUtils.setField(leveringsautorisatie, "dienstbundels", dienstbundels);

        Mockito.when(toegangLeveringsautorisatieRepository.haalToegangLeveringsautorisatieOp(1)).thenReturn(toegangLeveringsautorisatie);

        final BewerkAfnemerindicatieResultaat bewerkAfnemerindicatieResultaat = new BewerkAfnemerindicatieResultaat();
        bewerkAfnemerindicatieResultaat.setMeldingen(new ArrayList<RegelMelding>());
        final RegelMelding regelMelding =
                new RegelMelding(new RegelAttribuut(Regel.R1257), new SoortMeldingAttribuut(SoortMelding.FOUT), new MeldingtekstAttribuut("TEST"));
        bewerkAfnemerindicatieResultaat.getMeldingen().add(regelMelding);
        Mockito.when(afnemerindicatiesService.gbaPlaatsAfnemerindicatie(toegangLeveringsautorisatie, 3, 2, null, null))
               .thenReturn(bewerkAfnemerindicatieResultaat);

        // Execute
        final String resultaat =
                subject.verwerk(
                    "{\"effectAfnemerindicatie\":\"PLAATSING\",\"toegangLeveringsautorisatieId\":1,\"dienstId\":2,\"persoonId\":3,\"referentienummer\":\"4\"}",
                    "5");

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository).haalToegangLeveringsautorisatieOp(1);
        Mockito.verify(afnemerindicatiesService).gbaPlaatsAfnemerindicatie(toegangLeveringsautorisatie, 3, 2, null, null);
        Mockito.verifyNoMoreInteractions(
            afnemerindicatiesService,
            toegangLeveringsautorisatieRepository,
            blobifierService,
            berichtFactory,
            lo3FilterRubriekRepository,
            jmsTemplate,
            bericht);

        Assert.assertEquals("{\"foutcode\":\"X\",\"referentienummer\":\"4\"}", resultaat);
    }

    @Test
    public void verwijderenAfnemersindicatie() {
        // Setup
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = new ToegangLeveringsautorisatie() {
        };
        final Partij partij = new Partij(null, null, new PartijCodeAttribuut(3333), null, null, null, null, null, null);
        final PartijRol geautoriseerde = new PartijRol(partij, Rol.AFNEMER, null, null);
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "geautoriseerde", geautoriseerde);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie() {
        };
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "leveringsautorisatie", leveringsautorisatie);

        Mockito.when(toegangLeveringsautorisatieRepository.haalToegangLeveringsautorisatieOp(1)).thenReturn(toegangLeveringsautorisatie);

        final BewerkAfnemerindicatieResultaat bewerkAfnemerindicatieResultaat = new BewerkAfnemerindicatieResultaat();
        Mockito.when(afnemerindicatiesService.gbaVerwijderAfnemerindicatie(toegangLeveringsautorisatie, 3, 2)).thenReturn(bewerkAfnemerindicatieResultaat);

        // Execute
        final String resultaat =
                subject.verwerk(
                    "{\"effectAfnemerindicatie\":\"VERWIJDERING\",\"toegangLeveringsautorisatieId\":1,\"dienstId\":2,\"persoonId\":3,\"referentienummer\":\"4\"}",
                    "5");

        // Verify
        Mockito.verify(toegangLeveringsautorisatieRepository).haalToegangLeveringsautorisatieOp(1);
        Mockito.verify(afnemerindicatiesService).gbaVerwijderAfnemerindicatie(toegangLeveringsautorisatie, 3, 2);
        Mockito.verifyNoMoreInteractions(
            afnemerindicatiesService,
            toegangLeveringsautorisatieRepository,
            blobifierService,
            berichtFactory,
            lo3FilterRubriekRepository,
            jmsTemplate,
            bericht);

        Assert.assertEquals("{\"referentienummer\":\"4\"}", resultaat);
    }

    public static final class TestRegelConversietabel implements Conversietabel<Character, String> {

        @Override
        public String converteerNaarBrp(final Character input) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean valideerLo3(final Character input) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Character converteerNaarLo3(final String input) {
            switch (input) {
                case "R1257":
                    return 'X';
                default:
                    return null;
            }
        }

        @Override
        public boolean valideerBrp(final String input) {
            return true;
        }
    }
}
