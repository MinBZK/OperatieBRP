/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.fileaccess;

import static nl.bzk.brp.delivery.selectie.fileaccess.SelectieFileServiceImpl.FRAGMENT_FOLDER;
import static nl.bzk.brp.delivery.selectie.fileaccess.SelectieFileServiceImpl.PART_FOLDER;
import static nl.bzk.brp.delivery.selectie.fileaccess.SelectieFileServiceImpl.PART_POSTFIX;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieFileServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieFileServiceImplTest {

    private static final int SELECTIE_TAAK_ID = 1;
    private static final int DIENST_ID = 1;
    private static final int DATUM_UITVOER = 20110101;

    private static final MaakSelectieResultaatTaak maakSelectieResultaatTaak;
    private static final MaakSelectieResultaatTaak maakSelectieResultaatTaakOngeldig;

    static {
        maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setSelectietaakId(SELECTIE_TAAK_ID);
        maakSelectieResultaatTaak.setDatumUitvoer(DATUM_UITVOER);
        maakSelectieResultaatTaak.setDienstId(DIENST_ID);
        maakSelectieResultaatTaakOngeldig = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaakOngeldig.setSelectietaakId(SELECTIE_TAAK_ID);
        maakSelectieResultaatTaakOngeldig.setDatumUitvoer(DATUM_UITVOER);
        maakSelectieResultaatTaakOngeldig.setDienstId(DIENST_ID);
        maakSelectieResultaatTaakOngeldig.setOngeldig(true);
    }

    @Mock
    private ConfiguratieService configuratieService;

    @InjectMocks
    private SelectieFileServiceImpl selectieFileService;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        Mockito.when(configuratieService.getBerichtResultaatFolder()).thenReturn(folder.getRoot().toString());
    }


    @Test(expected = BrpServiceRuntimeException.class)
    public void testSchijfProtocolleringPersonenIOFout() throws IOException {
        Mockito.when(configuratieService.getBerichtResultaatFolder()).thenReturn("/&\\0");
        final SelectieFragmentSchrijfBericht bericht = new SelectieFragmentSchrijfBericht();
        bericht.setSelectietaakId(SELECTIE_TAAK_ID);
        bericht.setSelectietaakDatumUitvoer(DATUM_UITVOER);

        selectieFileService.initSchrijfOpslag(bericht);
        selectieFileService.schijfProtocolleringPersonen(bericht, Lists.newArrayList("A", "B"));

    }


    @Test
    public void schoonResultaatDirectory_FragmentFilePadIsNietGemaakt() throws IOException {
        selectieFileService.schoonResultaatDirectory(maakSelectieResultaatTaak);

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
    }

    @Test
    public void schoonResultaatDirectory_InclusiefFragmentFiles() throws IOException {
        File fragmentFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/fragment/test.fragment", SELECTIE_TAAK_ID, DATUM_UITVOER));
        fragmentFile.getParentFile().mkdirs();
        fragmentFile.createNewFile();
        File xmlFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/test.xml", SELECTIE_TAAK_ID, DATUM_UITVOER));
        xmlFile.createNewFile();

        selectieFileService.schoonResultaatDirectory(maakSelectieResultaatTaak);

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
        Assert.assertFalse(fragmentFile.exists());
        Assert.assertTrue(xmlFile.exists());
    }

    @Test
    public void schoonResultaatDirectoryNaTaakOngeldig_InclusiefFragmentFiles() throws IOException {
        File fragmentFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/fragment/test.fragment", SELECTIE_TAAK_ID, DATUM_UITVOER));
        fragmentFile.getParentFile().mkdirs();
        fragmentFile.createNewFile();
        File xmlFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/test.xml", SELECTIE_TAAK_ID, DATUM_UITVOER));
        xmlFile.createNewFile();
        selectieFileService.schoonResultaatDirectory(maakSelectieResultaatTaakOngeldig);

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
        Assert.assertFalse(fragmentFile.exists());
        Assert.assertFalse(xmlFile.exists());
    }


    @Test
    public void testGeefFragmentFiles_FileBestaatNiet() throws IOException {
        Mockito.when(configuratieService.getBerichtResultaatFolder()).thenReturn("test");

        List<Path> files = selectieFileService.geefFragmentFiles(maakSelectieResultaatTaak);

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
        Assert.assertTrue(files.isEmpty());
    }


    @Test
    public void testGeefFragmentFiles_FileBestaat() throws IOException {
        File fragmentFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/fragment/test.fragment", SELECTIE_TAAK_ID, DATUM_UITVOER));
        fragmentFile.getParentFile().mkdirs();
        fragmentFile.createNewFile();

        List<Path> files = selectieFileService.geefFragmentFiles(maakSelectieResultaatTaak);

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
        Assert.assertTrue(files.size() == 1);
    }

    @Test
    public void leesFragmentRegels() throws IOException {
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = maakSelectieResultaatTaak();

        selectieFileService.initSchrijfOpslag(selectieSchrijfTaak);

        final Path path = Paths.get(selectieFileService.getSelectietaakResultaatPath(maakSelectieResultaatTaak.getSelectietaakId(),
                maakSelectieResultaatTaak.getDatumUitvoer()).toString(), FRAGMENT_FOLDER);
        try (OutputStream out = Files.newOutputStream(Paths.get(path.toString(), "test.fragment"), StandardOpenOption.CREATE)) {
            out.write("testfragmentregel".getBytes());
        }
        List<Path> paths = selectieFileService.geefFragmentFiles(maakSelectieResultaatTaak);
        Stream<String> result = selectieFileService.leesFragmentRegels(paths.get(0));

        //assert
        Assert.assertEquals("testfragmentregel", Iterables.getOnlyElement(result.collect(Collectors.toList())));
    }


    @Test
    public void initSchrijfOpslag_GeenBestaandPad() throws IOException {
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        selectieFileService.initSchrijfOpslag(selectieSchrijfTaak);

        Mockito.verify(configuratieService, Mockito.times(2)).getBerichtResultaatFolder();
    }


    @Test
    public void initSchrijfOpslag_BestaandPad() throws IOException {
        File fragmentFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d", SELECTIE_TAAK_ID, DATUM_UITVOER));
        fragmentFile.mkdirs();

        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        selectieSchrijfTaak.setSelectietaakId(SELECTIE_TAAK_ID);
        selectieSchrijfTaak.setSelectietaakDatumUitvoer(DATUM_UITVOER);

        final Path path = selectieFileService.initSchrijfOpslag(selectieSchrijfTaak);

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
        Assert.assertEquals(fragmentFile.toPath(), path);
    }


    @Test
    public void schrijfPart() throws IOException {
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        selectieFileService.initSchrijfOpslag(selectieSchrijfTaak);

        List<byte[]> berichtenEncoded = Lists.newArrayList("test".getBytes());
        selectieFileService.schrijfDeelFragment(berichtenEncoded, selectieSchrijfTaak);

        final Path path =
                selectieFileService.getSelectietaakResultaatPath(selectieSchrijfTaak.getSelectietaakId(), selectieSchrijfTaak.getSelectietaakDatumUitvoer());
        final List<Path> paths = Files.list(Paths.get(path.toString(), PART_FOLDER))
                .filter(p -> p.getFileName()
                        .toString().endsWith(PART_POSTFIX))
                .collect(Collectors.toList());

        Assert.assertEquals(1, paths.size());
    }


    @Test
    public void testConcatLaatsteParts() throws IOException {
        Mockito.when(configuratieService.getConcatPartsCount()).thenReturn(100);
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = maakSelectieResultaatTaak();
        selectieFileService.initSchrijfOpslag(selectieSchrijfTaak);

        List<byte[]> berichtenEncoded = Lists.newArrayList("test1".getBytes());
        selectieFileService.schrijfDeelFragment(berichtenEncoded, selectieSchrijfTaak);
        selectieFileService.schrijfDeelFragment(berichtenEncoded, selectieSchrijfTaak);

        final Path
                path =
                selectieFileService.getSelectietaakResultaatPath(selectieSchrijfTaak.getSelectietaakId(), selectieSchrijfTaak.getSelectietaakDatumUitvoer());
        selectieFileService.concatLaatsteDeelFragmenten(maakSelectieResultaatTaak);

        final List<Path> paths = Files.list(Paths.get(path.toString(), PART_FOLDER))
                .filter(p -> p.getFileName()
                        .toString().endsWith(PART_POSTFIX))
                .collect(Collectors.toList());
        Assert.assertTrue(paths.isEmpty());

        final List<Path> fragmenten = selectieFileService.geefFragmentFiles(maakSelectieResultaatTaak);
        Assert.assertEquals(1, fragmenten.size());
    }


    @Test
    public void testConcatParts() throws IOException {
        Mockito.when(configuratieService.getConcatPartsCount()).thenReturn(2);
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = maakSelectieResultaatTaak();

        selectieFileService.initSchrijfOpslag(selectieSchrijfTaak);

        List<byte[]> berichtenEncoded = Lists.newArrayList("test1".getBytes());
        selectieFileService.schrijfDeelFragment(berichtenEncoded, selectieSchrijfTaak);
        selectieFileService.schrijfDeelFragment(berichtenEncoded, selectieSchrijfTaak);

        final Path path =
                selectieFileService.getSelectietaakResultaatPath(selectieSchrijfTaak.getSelectietaakId(), selectieSchrijfTaak.getSelectietaakDatumUitvoer());
        selectieFileService.concatDeelFragmenten(selectieSchrijfTaak);

        final List<Path> paths = Files.list(Paths.get(path.toString(), PART_FOLDER))
                .filter(p -> p.getFileName()
                        .toString().endsWith(PART_POSTFIX))
                .collect(Collectors.toList());
        Assert.assertTrue(paths.isEmpty());

        final List<Path> fragmenten = selectieFileService.geefFragmentFiles(maakSelectieResultaatTaak);
        Assert.assertEquals(1, fragmenten.size());
    }


    @Test
    public void testConcatGeenPartFolder() throws IOException {
        Mockito.when(configuratieService.getConcatPartsCount()).thenReturn(2);
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = maakSelectieFragmentSchrijfbericht();
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = maakSelectieResultaatTaak();
        selectieFileService.concatDeelFragmenten(selectieSchrijfTaak);
    }

    @Test
    public void testSchijfProtocolleringPersonen() throws IOException {
        final SelectieFragmentSchrijfBericht bericht = new SelectieFragmentSchrijfBericht();
        bericht.setSelectietaakId(SELECTIE_TAAK_ID);
        bericht.setSelectietaakDatumUitvoer(DATUM_UITVOER);

        selectieFileService.initSchrijfOpslag(bericht);
        selectieFileService.schijfProtocolleringPersonen(bericht, Lists.newArrayList("A", "B"));

        File fragmentFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/protocollering/protocolleringbestand.txt", SELECTIE_TAAK_ID, DATUM_UITVOER));

        Assert.assertTrue(fragmentFile.exists());
    }


    @Test
    public void schrijfSteekproefbestand() throws IOException {
        selectieFileService.schrijfSteekproefBestand(maakSelectieResultaatTaak, Lists.newArrayList("rgl"));

        File fragmentFile = new File(folder.getRoot(),
                String.format("selectietaak_%d_%d/steekproef_%d_%d_%d.txt",
                        SELECTIE_TAAK_ID, DATUM_UITVOER, DATUM_UITVOER, DIENST_ID, SELECTIE_TAAK_ID));

        Mockito.verify(configuratieService, Mockito.times(1)).getBerichtResultaatFolder();
        Assert.assertTrue(fragmentFile.exists());
    }


    private MaakSelectieResultaatTaak maakSelectieResultaatTaak() {
        final MaakSelectieResultaatTaak maakSelectieResultaatTaak = new MaakSelectieResultaatTaak();
        maakSelectieResultaatTaak.setDienstId(1);
        maakSelectieResultaatTaak.setSelectieRunId(1);
        maakSelectieResultaatTaak.setToegangLeveringsAutorisatieId(1);
        return maakSelectieResultaatTaak;
    }

    private SelectieFragmentSchrijfBericht maakSelectieFragmentSchrijfbericht() {
        SelectieFragmentSchrijfBericht selectieSchrijfTaak = new SelectieFragmentSchrijfBericht();
        selectieSchrijfTaak.setDienstId(1);
        selectieSchrijfTaak.setSelectieRunId(1);
        selectieSchrijfTaak.setToegangLeveringsAutorisatieId(1);
        return selectieSchrijfTaak;
    }
}
