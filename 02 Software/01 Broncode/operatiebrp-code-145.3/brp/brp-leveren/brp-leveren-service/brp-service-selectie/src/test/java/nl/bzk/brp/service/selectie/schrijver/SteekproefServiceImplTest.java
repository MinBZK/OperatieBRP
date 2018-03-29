/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import com.google.common.collect.Lists;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class SteekproefServiceImplTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @InjectMocks
    private SteekproefServiceImpl steekproefService;

    @Mock
    private SelectieFileService fileService;
    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;

    @Captor
    private ArgumentCaptor<List<String>> steekproefCaptor;


    private MaakSelectieResultaatTaak taak;
    private Dienst dienst;


    @Before
    public void setUp() {
        dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setId(1);
        dienst.setIndicatieSelectieresultaatControleren(true);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        final ToegangLeveringsAutorisatie toegang1 = autorisatiebundel.getToegangLeveringsautorisatie();

        taak = new MaakSelectieResultaatTaak();
        taak.setToegangLeveringsAutorisatieId(toegang1.getId());
        taak.setDienstId(dienst.getId());

        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(1)).thenReturn(toegang1);
    }

    /**
     * Als {@link Dienst#indicatieSelectieresultaatControleren} false of null is dan wordt
     * er geen steekproefbestand gemaakt.
     */
    @Test
    public void testGeenSteekproef() throws SelectieResultaatVerwerkException {
        dienst.setIndicatieSelectieresultaatControleren(null);
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verifyZeroInteractions(fileService);

        dienst.setIndicatieSelectieresultaatControleren(false);
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verifyZeroInteractions(fileService);
    }

    /**
     * Als er geen fragment bestanden zijn, zal de steekproef een leeg bestand opleveren.
     */
    @Test
    public void testGeenFragmentBestanden() throws IOException, SelectieResultaatVerwerkException {
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Collections.emptyList());
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[]", personenInSteekproef.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testNietBestaandeFragmentBestanden() throws IOException, SelectieResultaatVerwerkException {
        final Path fragmentA = maakFile("A", 1);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Lists.newArrayList(fragmentA));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[]", personenInSteekproef.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testMetOngeldigeFragmentBestanden() throws IOException, SelectieResultaatVerwerkException {
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Lists.newArrayList(Paths.get("dummy")));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[]", personenInSteekproef.toString());
    }

    /**
     * Cornercase: als er 1 bestand is met 0 personen dan levert dit een leeg steekproefbestand op.
     */
    @Test
    public void test1Bestand0Personen() throws IOException, SelectieResultaatVerwerkException {
        final int aantalPersonen = 0;
        final Path fragmentA = maakFile("A", aantalPersonen);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Collections.singletonList(fragmentA));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[]", personenInSteekproef.toString());
    }

    /**
     * Cornercase: als er 1 bestand bestaat met 1 persoon, dan levert dit een steekproefbestand op met 1 persoon.
     */
    @Test
    public void test1Bestand1Persoon() throws IOException, SelectieResultaatVerwerkException {
        final int aantalPersonen = 1;
        final Path fragmentA = maakFile("A", aantalPersonen);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Collections.singletonList(fragmentA));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[A0]", personenInSteekproef.toString());
    }

    /**
     * Als er meer personen zijn dan {@link SteekproefService#maxPersonenInSteekproef()}, dan wordt de steekproef
     * random genomen uit de totale populatie personen. De omvang van de steekproef
     * blijft gelijk aan {@link SteekproefService#maxPersonenInSteekproef()}
     */
    @Test
    public void test1BestandGroterDanMax() throws IOException, SelectieResultaatVerwerkException {
        final Path fragmentA = maakFile("A", steekproefService.maxPersonenInSteekproef() + 1);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Collections.singletonList(fragmentA));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals(personenInSteekproef.toString(), steekproefService.maxPersonenInSteekproef(), personenInSteekproef.size());
    }

    /**
     * Als het totaal aantal personen uit 1 bestand gelijk is aan {@link SteekproefService#maxPersonenInSteekproef()}
     * dan worden alle personen overgenomen in het steekproefbestand.
     */
    @Test
    public void test1BestandGelijkAanMax() throws IOException, SelectieResultaatVerwerkException {
        final Path fragmentA = maakFile("A", steekproefService.maxPersonenInSteekproef());
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Collections.singletonList(fragmentA));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        LOG.debug(personenInSteekproef.toString());
        Assert.assertEquals(personenInSteekproef.toString(), "[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, "
                + "A19, A20, A21, A22, A23, A24, A25, A26, A27, A28, A29, A30, A31, A32, A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, "
                + "A43, A44, A45, A46, A47, A48, A49]" , personenInSteekproef.toString());
    }

    /**
     * Cornercase: twee lege bestanden levert een leeg steekproefbestand op.
     */
    @Test
    public void test2Bestanden0Regels() throws IOException, SelectieResultaatVerwerkException {
        final int aantalPersonen = 0;
        final Path fragmentA = maakFile("A", aantalPersonen);
        final Path fragmentB = maakFile("B", aantalPersonen);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Arrays.asList(fragmentA, fragmentB));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[]",personenInSteekproef.toString());
    }

    /**
     * Als het totaal aantal personen uit meerdere bestanden kleiner of gelijk is aan {@link SteekproefService#maxPersonenInSteekproef()}
     * dan worden alle personen overgenomen in het steekproefbestand.
     */
    @Test
    public void test2BestandenKleinerDanMax() throws IOException, SelectieResultaatVerwerkException {
        final int aantalPersonen = 1;
        final Path fragmentA = maakFile("A", aantalPersonen);
        final Path fragmentB = maakFile("B", aantalPersonen);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Arrays.asList(fragmentA, fragmentB));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[A0, B0]",personenInSteekproef.toString());
    }

    /**
     * Als het totaal aantal personen uit meerdere bestanden kleiner of gelijk is aan {@link SteekproefService#maxPersonenInSteekproef()}
     * dan worden alle personen overgenomen in het steekproefbestand.
     */
    @Test
    public void test2BestandenGelijkAanMax() throws IOException, SelectieResultaatVerwerkException {
        final Path fragmentA = maakFile("A", steekproefService.maxPersonenInSteekproef() / 2);
        final Path fragmentB = maakFile("B", steekproefService.maxPersonenInSteekproef() / 2);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Arrays.asList(fragmentA, fragmentB));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, A23, A24, "
                + "B0, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24]",
                personenInSteekproef.toString());
    }

    /**
     * Als het totaal aantal personen uit meerdere bestanden groter is dan {@link SteekproefService#maxPersonenInSteekproef()}
     * dan wordt een random steekproef genomen.
     */
    @Test
    public void test2BestandenGroterDanMax() throws IOException, SelectieResultaatVerwerkException {
        final Path fragmentA = maakFile("A", steekproefService.maxPersonenInSteekproef() * 2);
        final Path fragmentB = maakFile("B", steekproefService.maxPersonenInSteekproef() * 2);
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(Arrays.asList(fragmentA, fragmentB));
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals(steekproefService.maxPersonenInSteekproef(), personenInSteekproef.size());
    }

    /**
     * Cornercase: Als het totaal aantal personen uit meerdere bestanden (met elk 1 persoon) gelijk is aan {@link SteekproefService#maxPersonenInSteekproef()}
     * dan worden.
     */
    @Test
    public void testMaxBestandenMet1RegelGelijkAanMax() throws IOException, SelectieResultaatVerwerkException {
        final List<Path> pathList = IntStream.range(0, steekproefService.maxPersonenInSteekproef())
                .mapToObj(i -> maakFile("#" + i + "#", 1)).collect(Collectors.toList());
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(pathList);
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals("[#0#0, #1#0, #2#0, #3#0, #4#0, #5#0, #6#0, #7#0, #8#0, #9#0, #10#0, #11#0, #12#0, #13#0, #14#0, #15#0, #16#0, "
                + "#17#0, #18#0, #19#0, #20#0, #21#0, #22#0, #23#0, #24#0, #25#0, #26#0, #27#0, #28#0, #29#0, #30#0, #31#0, #32#0, #33#0, #34#0, #35#0, "
                + "#36#0, #37#0, #38#0, #39#0, #40#0, #41#0, #42#0, #43#0, #44#0, #45#0, #46#0, #47#0, #48#0, #49#0]", personenInSteekproef.toString());
    }

    /**
     * Test dat het maken van een steekproef met veel data geen problemen geeft.
     */
    @Test
    public void testVeelData() throws IOException, SelectieResultaatVerwerkException {
        int aantalPersonenPersbestand = (int) 1e6;
        final List<Path> pathList = IntStream.range(0, 10)
                .mapToObj(i -> maakFile("#" + i + "#", aantalPersonenPersbestand)).collect(Collectors.toList());
        Mockito.when(fileService.geefFragmentFiles(taak)).thenReturn(pathList);
        steekproefService.maakSteekproefBestand(taak);
        Mockito.verify(fileService).schrijfSteekproefBestand(Mockito.eq(taak), steekproefCaptor.capture());
        final List<String> personenInSteekproef = steekproefCaptor.getValue();
        Assert.assertEquals(steekproefService.maxPersonenInSteekproef(), personenInSteekproef.size());
    }


    private static Path maakFile(String prefix, int aantalPersonen) {
        final File file;
        try {
            file = File.createTempFile("steekproeftest", ".txt");
            file.deleteOnExit();
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
                for (int i = 0 ; i < aantalPersonen ; i++) {
                    pw.println(prefix + i);
                }
            }
            return file.toPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
