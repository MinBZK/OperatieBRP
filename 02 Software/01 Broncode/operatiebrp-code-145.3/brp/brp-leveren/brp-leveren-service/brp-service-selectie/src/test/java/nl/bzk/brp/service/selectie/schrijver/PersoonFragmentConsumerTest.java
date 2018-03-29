/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * PersoonFragmentVerwerkerTaakTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonFragmentConsumerTest {

    @Mock
    private SelectieResultaatWriterFactory selectieResultaatWriterFactory;

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;
    @Mock
    private PartijService partijService;
    //de dienst voor test
    private Dienst dienst;

    @Before
    public void setUp() throws SelectieResultaatVerwerkException {
        this.dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        final ToegangLeveringsAutorisatie tla = autorisatiebundel.getToegangLeveringsautorisatie();
        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(Mockito.anyInt())).thenReturn(tla);


        final SelectieResultaatWriterFactory.PersoonBestandWriter persoonBestandWriter = Mockito.mock(SelectieResultaatWriterFactory.PersoonBestandWriter.class);
        Mockito.when(selectieResultaatWriterFactory.persoonWriterBrp(Mockito.any(), Mockito.any())).thenReturn(persoonBestandWriter);
        final SelectieResultaatWriterFactory.TotalenBestandWriter totalenBestandWriter = Mockito.mock(SelectieResultaatWriterFactory.TotalenBestandWriter.class);
        Mockito.when(selectieResultaatWriterFactory.totalenWriterBrp(Mockito.any(), Mockito.any())).thenReturn(totalenBestandWriter);

    }

    @Test
    public void testAantalPersonenInBericht() throws Exception {
        //
        int aantalPersonen = 5;
        final AtomicInteger counter = new AtomicInteger();
        final PersoonFragmentConsumer persoonXmlFragmentConsumer = new PersoonFragmentConsumer();
        final BlockingQueue<String> queue = initQueue(aantalPersonen, String.valueOf("test").getBytes(StandardCharsets.UTF_8));
        persoonXmlFragmentConsumer.setQueue(queue);
        persoonXmlFragmentConsumer.setCounter(counter);
        persoonXmlFragmentConsumer.setPartijService(partijService);
        persoonXmlFragmentConsumer.setLeveringsautorisatieService(leveringsautorisatieService);

        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setSelectieRunId(1);
        maakSelectieResultaatTaak.setDienstId(1);
        maakSelectieResultaatTaak.setToegangLeveringsAutorisatieId(1);
        persoonXmlFragmentConsumer.setMaakSelectieResultaatTaak(maakSelectieResultaatTaak);

        final int maxAantalPersonenPerSelectiebestand = 1;
        dienst.setMaxAantalPersonenPerSelectiebestand(maxAantalPersonenPerSelectiebestand);

        persoonXmlFragmentConsumer.setSelectieResultaatWriterFactory(selectieResultaatWriterFactory);

        persoonXmlFragmentConsumer.call();
        //5 personen totaal, 1 persoon per bestand levert 5 bestanden op
        Mockito.verify(selectieResultaatWriterFactory, Mockito.times(5)).persoonWriterBrp(Mockito.any(), Mockito.any());

        //reset en test met aantal personen totaal en aantal per bestand gelijk. 1 bestand
        dienst.setMaxAantalPersonenPerSelectiebestand(aantalPersonen);
        persoonXmlFragmentConsumer.setQueue(initQueue(aantalPersonen, String.valueOf("test").getBytes(StandardCharsets.UTF_8)));

        persoonXmlFragmentConsumer.call();
        Mockito.verify(selectieResultaatWriterFactory, Mockito.times(6)) /* == effectief +1, mock telt door,,,*/.persoonWriterBrp(Mockito.any(), Mockito.any());

    }

    @Test
    public void testAantalBytesInBericht() throws Exception {
        //max file size 1 mb. 2 personen van 1 mb minus 1 byte. Dit zou tot 2 files moeten leiden.
        final int aantalPersonen = 2;
        int maxMb = 1;
        int overheadBytes = 1024;
        final int maxBytes = 1024 * 1024 * maxMb - overheadBytes;
        final AtomicInteger counter = new AtomicInteger();
        final PersoonFragmentConsumer persoonXmlFragmentConsumer = new PersoonFragmentConsumer();
        //voor de berekening van bytes nemen we de header en footer van bericht niet mee. Dus -1 is voldoende
        final String persoon = maakString(maxBytes - 1);
        final BlockingQueue<String> queue = initQueue(aantalPersonen, persoon.getBytes(StandardCharsets.UTF_8));
        persoonXmlFragmentConsumer.setQueue(queue);
        persoonXmlFragmentConsumer.setPartijService(partijService);
        persoonXmlFragmentConsumer.setCounter(counter);
        persoonXmlFragmentConsumer.setLeveringsautorisatieService(leveringsautorisatieService);

        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setSelectieRunId(1);
        maakSelectieResultaatTaak.setDienstId(1);
        maakSelectieResultaatTaak.setToegangLeveringsAutorisatieId(1);
        persoonXmlFragmentConsumer.setMaakSelectieResultaatTaak(maakSelectieResultaatTaak);

        dienst.setMaxAantalPersonenPerSelectiebestand(Integer.MAX_VALUE);
        //1mb
        dienst.setMaxGrootteSelectiebestand(maxMb);

        persoonXmlFragmentConsumer.setSelectieResultaatWriterFactory(selectieResultaatWriterFactory);


        persoonXmlFragmentConsumer.call();

        Mockito.verify(selectieResultaatWriterFactory, Mockito.times(2)).persoonWriterBrp(Mockito.any(), Mockito.any());
        //reset en max grootte per bestand groter dan totaal aantal personen. 1 bestand

        dienst.setMaxGrootteSelectiebestand(2);
        persoonXmlFragmentConsumer.setQueue(initQueue(aantalPersonen, persoon.getBytes(StandardCharsets.UTF_8)));
        persoonXmlFragmentConsumer.call();
        Mockito.verify(selectieResultaatWriterFactory, Mockito.times(3)) /* == effectief +1, mock telt door,,,*/.persoonWriterBrp(Mockito.any(), Mockito.any());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAantalBytesInBerichtMaxKleinerDanPl() throws Exception {
        //max file size 1 mb. 2 personen van 1 mb minus 1 byte. Dit zou tot 2 files moeten leiden.
        final int aantalPersonen = 2;
        int maxMb = 1;
        int overheadBytes = 1024;
        final int maxBytes = 1024 * 1024 * maxMb;
        final AtomicInteger counter = new AtomicInteger();
        final PersoonFragmentConsumer persoonXmlFragmentConsumer = new PersoonFragmentConsumer();
        //voor de berekening van bytes nemen we de header en footer van bericht niet mee. Dus -1 is voldoende
        final String persoon = maakString(maxBytes - 1);
        final BlockingQueue<String> queue = initQueue(aantalPersonen, persoon.getBytes(StandardCharsets.UTF_8));
        persoonXmlFragmentConsumer.setQueue(queue);
        persoonXmlFragmentConsumer.setPartijService(partijService);
        persoonXmlFragmentConsumer.setCounter(counter);
        persoonXmlFragmentConsumer.setLeveringsautorisatieService(leveringsautorisatieService);

        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setSelectieRunId(1);
        maakSelectieResultaatTaak.setDienstId(1);
        maakSelectieResultaatTaak.setToegangLeveringsAutorisatieId(1);
        persoonXmlFragmentConsumer.setMaakSelectieResultaatTaak(maakSelectieResultaatTaak);

        dienst.setMaxAantalPersonenPerSelectiebestand(Integer.MAX_VALUE);
        //1mb
        dienst.setMaxGrootteSelectiebestand(maxMb);

        persoonXmlFragmentConsumer.setSelectieResultaatWriterFactory(selectieResultaatWriterFactory);

        persoonXmlFragmentConsumer.call();

    }

    private String maakString(final int maxBytes) {
        final String test = "test";
        final byte[] utf8Bytes = test.getBytes(StandardCharsets.UTF_8);
        int bytes = 0;
        final StringBuilder builder = new StringBuilder();
        while (bytes < maxBytes) {
            builder.append(test);
            bytes = bytes + utf8Bytes.length;
        }
        return builder.toString();
    }

    private BlockingQueue<String> initQueue(final int aantalPersonen, final byte[] utf8Bytes) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        for (int i = 0; i < aantalPersonen; i++) {
            final byte[] encoded = Base64.getEncoder().encode(utf8Bytes);
            queue.add(new String(encoded, Charset.defaultCharset()));
        }
        queue.add(PersoonFragmentConsumer.POISON);
        return queue;
    }
}
