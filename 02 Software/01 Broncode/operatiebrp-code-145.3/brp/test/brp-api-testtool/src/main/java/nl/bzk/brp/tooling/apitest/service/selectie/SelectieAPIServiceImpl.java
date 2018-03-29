/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.selectie.algemeen.Configuratie;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakStatusHistorieUtil;
import nl.bzk.brp.service.selectie.lezer.job.SelectieRunJobService;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import nl.bzk.brp.service.selectie.protocollering.SelectieProtocolleringService;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.DslSectieParser;
import nl.bzk.brp.test.common.dsl.selectie.SelectielijstParser;
import nl.bzk.brp.test.common.dsl.selectie.SelectietaakParser;
import nl.bzk.brp.test.common.xml.XPathHelper;
import nl.bzk.brp.test.common.xml.XmlUtils;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ProtocolleringStubService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link SelectieAPIService}
 */
@Component
final class SelectieAPIServiceImpl implements SelectieAPIService {

    private static final String PERSONEN_XML_FILENAME_PATTERN = "[0-9]*_.*personen\\.xml";
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Schema SCHEMA = SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgSelectie_Berichten.xsd");
    private static final String PROTOCOLLERING_BESTAND_PAD = "protocollering/protocolleringbestand.txt";
    private static final String NEW_LINE_SEPARATOR = "\n";


    @Inject
    private SelectieRunJobService selectieRunJobService;
    @Inject
    private SelectieFileService selectieFileService;
    @Inject
    private SelectieJobRunStatusService selectieJobRunStatusService;
    @Inject
    private SchemaValidatorService schemaValidatorService;
    @Inject
    private Configuratie configuratie;
    @Inject
    private TaakBeheer taakBeheer;
    @Inject
    private StoryService storyService;
    @Inject
    private BulkMode bulkMode;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieStubService;
    @Inject
    private SelectieProtocolleringService selectieProtocolleringService;
    @Inject
    private ProtocolleringStubService protocolleringStubService;
    @Inject
    private XPathHelper xPathHelper;

    private final Function<DienstSleutel, Dienst> dienstResolver = dienstSleutel -> autorisatieStubService.getDienst(dienstSleutel);

    SelectieAPIServiceImpl() {
    }

    @Override
    public void startRun() throws IOException, InterruptedException {
        configuratie.setBerichtResultaatFolder(new File(storyService.getOutputDir(), "sel" +
                DatumUtil.vanDateTimeNaarLong(DatumUtil.nuAlsZonedDateTime())).getAbsolutePath());
        //set property rootdir voor selectielijsten
        if (configuratie.getSelectiebestandFolder() == null
                || "${brp.selectie.verwerker.selectiebestandenfolder}".equals(configuratie.getSelectiebestandFolder())) {
            zetSelectiebestandRootFolder();
            Files.createDirectories(Paths.get(configuratie.getSelectiebestandFolder()));
        }
        selectieRunJobService.start();
    }

    @Override
    public void startRunSingleThreaded() throws IOException, InterruptedException {
        configuratie.setPoolSizeBlobBatchProducer(1);
        configuratie.setSchrijverPoolSize(1);
        configuratie.setVerwerkerPoolSize(1);
        startRun();
    }

    @Override
    public void assertFilesAanwezig(final Integer id, final String datumPlanning) throws IOException {
        final List<Path> paths = geefXmlResultaatFiles(id, datumPlanning);
        Assert.assertFalse("Er zijn geen files gevonden, dit werd wel verwacht", paths.isEmpty());
        for (Path path : paths) {
            final String xml = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            final Source xmlSource = new StreamSource(new StringReader(xml));
            schemaValidatorService.valideer(xmlSource, SCHEMA);
        }
    }

    @Override
    public void assertGeenFilesAanwezig(final Integer taakId, final String datumPlanning) throws IOException {
        final List<Path> paths = geefXmlResultaatFiles(taakId, datumPlanning);
        Assert.assertTrue("Er zijn wel files aanwezig", paths.isEmpty());
    }

    @Override
    public void assertFilesAanwezigMetAantalPersonen(final Integer id, final String datumPlanning, final Integer aantalPersonen) throws IOException {
        final SelectieRunResultaat selectieResultaat = controleerResultaatsetPersonen(id, datumPlanning);
        Assert.assertEquals("aantal personen niet gelijk", aantalPersonen.intValue(), selectieResultaat.aantalPersonen);
    }

    @Override
    public void assertFilesAanwezigMetAantalPersonenEnAantalFiles(Integer id, String datumPlanning,
                                                                  Integer expectedAantalPersonen, Integer expectedAantalFiles)
            throws IOException {
        final SelectieRunResultaat selectieResultaat = controleerResultaatsetPersonen(id, datumPlanning);
        Assert.assertEquals("aantal personen niet gelijk", expectedAantalPersonen.intValue(), selectieResultaat.aantalPersonen);
        Assert.assertEquals("aantal files niet gelijk", expectedAantalFiles.intValue(), selectieResultaat.aantalFiles);
    }

    @Override
    public void assertTotalenbestandVoorTaakGelijkAan(final Integer id, final String datumuitvoer, final String expectedPath) throws IOException {
        final String xml = geefResultatenbestand(id, datumuitvoer);
        vergelijkExpectationMetActual(expectedPath, xml);
    }

    @Override
    public void assertFilesAanwezigMetExpectations(final Integer id, final String datumPlanning, final List<Map<String, String>> expectationPaden)
            throws IOException {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(id, DatumConstanten.getBasicIsoDateInt(datumPlanning));
        Assert.assertTrue("Aantal ontvangen bestanden komt niet overeen met verwachtte aantal.",
                Files.list(selectiePath).filter(p -> p.getFileName().toString().matches(PERSONEN_XML_FILENAME_PATTERN)).count() == expectationPaden.size());
        for (final Map<String, String> expectation : expectationPaden) {
            final String volgnummer = expectation.get("volgnummer");
            final List<Path> bestanden = Files.list(selectiePath).filter(p -> p.getFileName().toString().startsWith(volgnummer)).collect(Collectors.toList());
            Assert.assertTrue("Geen (of meerdere) bestanden met (hetzelfde) volgnummer gevonden.", bestanden.size() == 1);
            final Path resultaatBestand = Iterables.getOnlyElement(bestanden);
            final String expectedPath = expectation.get("pad");
            vergelijkExpectationMetActual(expectedPath, new String(Files.readAllBytes(resultaatBestand), StandardCharsets.UTF_8));
        }
    }

    @Override
    public void assertNodeBestaatInPersoonBestanden(final Integer id, final String datumPlanning, final String xpathExpressie) throws IOException {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(id, DatumConstanten.getBasicIsoDateInt(datumPlanning));
        final List<Path>
                persoonFiles =
                Files.list(selectiePath).filter(p -> p.getFileName().toString().matches(PERSONEN_XML_FILENAME_PATTERN)).collect(Collectors.toList());
        boolean found = false;
        for (final Path persoonFile : persoonFiles) {
            final String xml = new String(Files.readAllBytes(persoonFile), StandardCharsets.UTF_8);
            if (xPathHelper.isNodeAanwezig(xml, xpathExpressie)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Element niet gevonden in een van de persoonbestanden.");
        }
    }

    @Override
    public void assertProtocolleringBestand(final Integer id, final String datumPlanning, final Boolean aanwezig) {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(id, DatumConstanten.getBasicIsoDateInt(datumPlanning));
        final Path protocolleringBestand = Paths.get(selectiePath.toString(), PROTOCOLLERING_BESTAND_PAD);
        Assert.assertTrue(aanwezig == protocolleringBestand.toFile().exists());
    }

    @Override
    public void verwijderProtocolleringbestand(final int selectietaakId, final String datumPlanning) {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(selectietaakId, DatumConstanten.getBasicIsoDateInt(datumPlanning));
        final Path protocolleringBestand = Paths.get(selectiePath.toString(), PROTOCOLLERING_BESTAND_PAD);
        try {
            LOGGER.info(String.format("Verwijder protocolleringsbestand voor selectietaak met id : %d en datum planning %s", selectietaakId, datumPlanning));
            if (!Files.deleteIfExists(protocolleringBestand)) {
                LOGGER.info("Protocolleringsbestand niet gevonden en dus niet verwijderd", selectietaakId, datumPlanning);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Het protocolleringsbetand kon niet verwijderd worden.", e);
        }
    }

    @Override
    public void maakProtocolleringsBestandInvalid(final int selectietaakId, final String datumPlanning) {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(selectietaakId, DatumConstanten.getBasicIsoDateInt(datumPlanning));
        final Path protocolleringBestand = Paths.get(selectiePath.toString(), PROTOCOLLERING_BESTAND_PAD);
        try {
            Files.deleteIfExists(protocolleringBestand);
            Files.createDirectory(protocolleringBestand);
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
    }


    @Override
    public void wachtAantalSecondenTotSelectieRunKlaar(final int aantalSeconden, final boolean inError) throws InterruptedException {
        //wacht tot asynchrone selectie run gestart
        TimeUnit.MILLISECONDS.sleep(25);
        wachtTotGestopt(aantalSeconden);
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        final boolean gestopt = status.isStopped();
        if (!gestopt) {
            selectieRunJobService.stop();
            wachtTotGestopt(aantalSeconden);
            if (!status.isStopped()) {
                LOGGER.error("selectie run niet gestopt na wachten");
            }
        }
        final boolean klaarEnZonderFouten = !status.isError() && !status.moetStoppen();
        Assert.assertTrue(gestopt);
        if (inError) {
            Assert.assertFalse(klaarEnZonderFouten);
        } else {
            Assert.assertTrue(klaarEnZonderFouten);
        }
        //wacht ook tot async thread uit pool is
        TimeUnit.MILLISECONDS.sleep(25);
    }

    @Override
    public void maakSelectietaken(final Resource resource) {
        maakSelectietaken(DslSectieParser.parse(resource));
    }

    @Override
    public void assertTakenHebbenEenSelectierunId(final List<DslSectie> sectieList) {
        final List<Selectietaak> takenlijst = maakSelectietakenObvDsl(sectieList);
        final Collection<Selectietaak> alleTaken = taakBeheer.getTaken();
        final Map<Dienst, Selectietaak> dienstTakenMap = new HashMap<>();
        for (Selectietaak selectietaak : alleTaken) {
            final Dienst dienst = selectietaak.getDienst();
            dienstTakenMap.put(dienst, selectietaak);
        }
        for (Selectietaak selectietaak : takenlijst) {
            final Selectietaak selectietaakGezet = dienstTakenMap.get(selectietaak.getDienst());
            Assert.assertNotNull(selectietaakGezet);
            Assert.assertEquals(selectietaakGezet.getDienst(), selectietaak.getDienst());
        }
    }

    @Override
    public void maakSelectietaken(final List<DslSectie> sectieList) {
        final List<Selectietaak> takenlijst = maakSelectietakenObvDsl(sectieList);
        taakBeheer.setTaken(takenlijst);
    }

    @Override
    public void maakSelectielijsten(List<DslSectie> sectieList) {
        zetSelectiebestandRootFolder();
        try {
            final Path root = Paths.get(configuratie.getSelectiebestandFolder());
            Files.createDirectories(root);
            final Map<String, Integer> dienstSelectietaakMap = new HashMap<>();
            for (DslSectie dslSectie : sectieList) {
                final Integer
                        selectieTaakId =
                        dslSectie.geefInteger(SelectielijstParser.SELECTIETAAK).orElseThrow(() -> new TestclientExceptie("taak is verplicht"));

                final DienstSleutel dienstSleutel = dslSectie.geefStringValue(SelectielijstParser.DIENST_SLEUTEL)
                        .map(DienstSleutel::new)
                        .orElseThrow(() -> new IllegalArgumentException("dienstSleutel is verplicht: " + dslSectie.toString()));
                dienstSelectietaakMap.put(dienstSleutel.getWaarde(), selectieTaakId);
            }
            final Map<DienstSleutel, Map<String, List<String>>> lijstenPerDienst = SelectielijstParser.parse(sectieList, dienstResolver);
            for (Map.Entry<DienstSleutel, Map<String, List<String>>> entry : lijstenPerDienst.entrySet()) {
                final String dienstSleutel = entry.getKey().getWaarde();
                final Selectietaak selectietaak = taakBeheer.getTaak(dienstSelectietaakMap.get(dienstSleutel));
                final Path dienstDir = root.resolve(Paths.get(String.valueOf(selectietaak.getDienst().getId()), String.valueOf(selectietaak.getId())));
                Files.createDirectories(dienstDir);
                final Path selectielijstBestand = dienstDir.resolve(Paths.get("selectielijst.csv"));
                try (BufferedWriter writer = Files.newBufferedWriter(selectielijstBestand, StandardCharsets.UTF_8)) {
                    final Map.Entry<String, List<String>> selectielijst = entry.getValue().entrySet().iterator().next();
                    //schrijf header : soort identificatienr
                    writer.append(selectielijst.getKey());
                    writer.append(NEW_LINE_SEPARATOR);
                    //schrijf identificatienrs
                    for (String identificatieNr : selectielijst.getValue()) {
                        writer.append(identificatieNr.trim());
                        writer.append(NEW_LINE_SEPARATOR);
                    }
                    writer.flush();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Fout bij aanmaken directory structuur voor selectielijsten", e);
        }
    }

    @Override
    public void assertStatusTransitie(final List<Map<String, String>> list) throws IOException {
        for (Map<String, String> map : list) {
            final int selectieTaakId = Integer.parseInt(map.get("selectieTaakId"));
            final String statusTransitie = map.get("statusTransitie");
            final Selectietaak selectietaak = taakBeheer.getTaak(selectieTaakId);

            final List<SelectietaakStatus>
                    selectietaakStatusLijst =
                    selectietaak.getSelectietaakStatusHistorieSet().stream().sorted(Comparator.comparing(SelectietaakStatusHistorie::getDatumTijdRegistratie))
                            .map(s -> SelectietaakStatus.parseId((int) s.getStatus())).collect(
                            Collectors.toList());
            Assert.assertEquals("Status transitie pad niet gelijk", statusTransitie, selectietaakStatusLijst.toString());
        }
    }

    @Override
    public void assertPeilmomentenSelectietaak(final List<Map<String, String>> list) throws IOException {
        for (Map<String, String> map : list) {
            final int selectieTaakId = Integer.parseInt(map.get("selectieTaakId"));

            Assert.assertTrue("Peilmoment materieel moet altijd een waarde hebben", map.containsKey("peilmomentMaterieelResultaat"));
            Assert.assertTrue("Peilmoment formeel moet altijd een waarde hebben", map.containsKey("peilmomentFormeelResultaat"));

            final Integer peilmomentMaterieelExpected = map.get("peilmomentMaterieelResultaat").equals("NULL") ? null :
                    DatumConstanten.getBasicIsoDateInt(map.get("peilmomentMaterieelResultaat"));
            final Integer peilmomentFormeelExpected = map.get("peilmomentFormeelResultaat").equals("NULL") ? null :
                    DatumConstanten.getBasicIsoDateInt(map.get("peilmomentFormeelResultaat"));

            final Selectietaak selectietaak = taakBeheer.getTaak(selectieTaakId);

            ZonedDateTime zonedDateTime = DatumUtil.vanTimestampNaarZonedDateTime(selectietaak.getPeilmomentFormeelResultaat());
            Integer peilmomentFormeelUitTaakAlsInteger = zonedDateTime == null ? null : Integer.valueOf(
                    String.format("%d%s%s", zonedDateTime.getYear(),
                            StringUtils.leftPad(String.valueOf(zonedDateTime.getMonthValue()), 2, "0"),
                            StringUtils.leftPad(String.valueOf(zonedDateTime.getDayOfMonth()), 2, "0")));
            Assert.assertEquals(peilmomentFormeelExpected, peilmomentFormeelUitTaakAlsInteger);
            Assert.assertEquals(peilmomentMaterieelExpected, selectietaak.getPeilmomentMaterieelResultaat());
        }
    }

    @Override
    public void assertControlebestand(final List<Map<String, String>> list) throws IOException {
        Assert.assertEquals("Test incompleet, enumereer alle selectietaken", taakBeheer.getTaken().size(), list.size());
        for (Map<String, String> map : list) {
            final int selectieTaakId = Integer.parseInt(map.get("selectieTaakId"));
            final Selectietaak selectietaak = taakBeheer.getTaak(selectieTaakId);

            final boolean controlebestandAanwezig = "ja".equals(map.get("aanwezig"));
            final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(selectieTaakId, selectietaak.getDatumUitvoer());
            final String steekproefBestand = String.format("steekproef_%s_%s_%s.txt",
                    selectietaak.getDatumUitvoer(), selectietaak.getDienst().getId(), selectieTaakId);
            final Path path = selectiePath.resolve(steekproefBestand);
            Assert.assertEquals("Aanwezigheid van controlebestand incorrect", path.toFile().exists(), controlebestandAanwezig);

            if (!controlebestandAanwezig) {
                continue;
            }
            final long verwachtAantalPersonen = Integer.parseInt(map.get("aantalPersonen"));
            final long gevondenAantalPersonen = Files.lines(path, StandardCharsets.UTF_8).count();
            Assert.assertEquals("Aantal personen in controlebestand komt niet overeen", verwachtAantalPersonen, gevondenAantalPersonen);
        }
    }

    @Override
    public void activeerBulkModus(final int aantalPersonen) {
        bulkMode.activeerBulkModus(aantalPersonen);
    }

    @Override
    public void startProtocollering() {
        selectieProtocolleringService.start();
        int pogingen = 0;
        while (selectieProtocolleringService.isRunning() && pogingen < 5) {
            try {
                TimeUnit.SECONDS.sleep(1);
                pogingen++;
            } catch (InterruptedException e) {
                throw new TestclientExceptie("Error wachten tot klaar met protocollering");
            }
        }
        selectieProtocolleringService.stop();
        if (selectieProtocolleringService.isRunning()) {
            throw new TestclientExceptie("Protocollering niet klaar binnen wacht tijd");
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new TestclientExceptie("Error wachten tot klaar met protocollering");
        }
    }

    @Override
    public void assertGeprotocolleerdVoorSelectie(int aantalLeveringsaantekening, int aantalPersonen) {
        final Multimap<Leveringsaantekening, LeveringsaantekeningPersoon> multimap = protocolleringStubService.getBulkProtocollering();
        Assert.assertEquals("Verwachting komt niet overeen", aantalLeveringsaantekening, multimap.keySet().size());
        Assert.assertEquals("Aantal geprotocolleerde personen komt niet overeen",
                aantalPersonen, multimap.values().size());
    }

    @Override
    public void setStatusOpTeProtocolleren(final int selectietaakId) {
        final Selectietaak selectietaak = taakBeheer.getTaak(selectietaakId);
        final SelectietaakStatus status = SelectietaakStatus.TE_PROTOCOLLEREN;
        selectietaak.setStatus((short) status.getId());
        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        SelectietaakStatusHistorieUtil.updateTaakStatus(selectietaak, nuTijd, status, null);
    }

    private void wachtTotGestopt(int aantalSeconden) throws InterruptedException {
        int aantalSecondenGewacht = 0;
        while (!selectieJobRunStatusService.getStatus().isStopped() && aantalSecondenGewacht < aantalSeconden) {
            TimeUnit.SECONDS.sleep(1);
            aantalSecondenGewacht++;
            LOGGER.info(String.format("Wachten tot selectierun beeindigd, %d van %d seconden.", aantalSeconden, aantalSecondenGewacht));
        }
    }

    private void vergelijkExpectationMetActual(final String expectedPath, final String xml) throws IOException {
        final Resource resource = storyService.resolvePath(expectedPath);
        final String expectedString = new String(Files.readAllBytes(Paths.get(resource.getURI())), StandardCharsets.UTF_8);
        XmlUtils.assertGelijk(null, expectedString, XmlUtils.ster(xml));
    }

    private List<Path> geefXmlResultaatFiles(final Integer selectieTaakId, final String datumUitvoer) throws IOException {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(selectieTaakId, DatumConstanten.getBasicIsoDateInt(datumUitvoer));
        if (selectiePath.toFile().exists()) {
            return Files.list(selectiePath)
                    .filter(p -> p.getFileName()
                            .toString().endsWith(".xml"))
                    .collect(Collectors.toList());
        } else {
            LOGGER.info(String.format("Geen xml resultaat files gevonden, geen bestaand pad : %s", selectiePath.toString()));
            return Lists.newArrayList();
        }
    }

    private SelectieRunResultaat controleerResultaatsetPersonen(Integer taakId, String datumPlannning) throws IOException {
        final Selectietaak selectietaak = taakBeheer.getTaak(taakId);
        final List<Path> paths = geefXmlResultaatFiles(taakId, datumPlannning);
        Assert.assertFalse("bestanden leeg", paths.isEmpty());
        int totaalAantalPersonen = 0;
        for (Path path : paths) {
            final byte[] bytes = Files.readAllBytes(path);
            if (selectietaak.getDienst().getMaxGrootteSelectiebestand() != null) {
                Assert.assertTrue("bestand groter dan toegestaan", bytes.length < (selectietaak.getDienst().getMaxGrootteSelectiebestand() * 1024 * 1024));
            }
            final String xml = new String(bytes, StandardCharsets.UTF_8);
            final Source xmlSource = new StreamSource(new StringReader(xml));
            final int personenInXml = xPathHelper.getAantalElementen(xml, "geselecteerdePersoon");
            if (selectietaak.getDienst().getMaxAantalPersonenPerSelectiebestand() != null) {
                Assert.assertTrue("meer personen in bestand dan toegestaan",
                        personenInXml <= selectietaak.getDienst().getMaxAantalPersonenPerSelectiebestand());
            }
            totaalAantalPersonen += personenInXml;
            schemaValidatorService.valideer(xmlSource, SCHEMA);
        }
        return new SelectieRunResultaat(totaalAantalPersonen, paths.size());
    }


    private List<Selectietaak> maakSelectietakenObvDsl(List<DslSectie> sectieList) {
        final Function<DienstSleutel, ToegangLeveringsAutorisatie> tlaResolver = dienstSleutel
                -> autorisatieStubService.getToegangleveringsautorisatie(dienstSleutel);
        final ArrayList<Selectietaak> takenlijst = Lists.newArrayList();
        for (DslSectie dslSectie : sectieList) {
            final Selectietaak selectietaak = SelectietaakParser.parse(dslSectie, dienstResolver, tlaResolver);

            final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
            //his records voor taak
            final SelectietaakHistorie selectietaakHistorie = new SelectietaakHistorie(selectietaak);
            selectietaakHistorie.setDatumTijdRegistratie(nuTijd);
            selectietaakHistorie.setIndicatieSelectielijstGebruiken(selectietaak.isIndicatieSelectielijstGebruiken());
            selectietaak.addSelectietaakHistorieSet(selectietaakHistorie);
            final SelectietaakStatusHistorie selectietaakStatusHistorie = new SelectietaakStatusHistorie(selectietaak);
            selectietaakStatusHistorie.setStatusGewijzigdDoor("Systeem");
            selectietaakStatusHistorie.setDatumTijdRegistratie(nuTijd);
            selectietaakStatusHistorie.setStatus(selectietaak.getStatus());
            selectietaak.addSelectietaakStatusHistorieSet(selectietaakStatusHistorie);

            takenlijst.add(selectietaak);
        }
        return takenlijst;
    }

    private static final class SelectieRunResultaat {
        int aantalPersonen;
        int aantalFiles;

        private SelectieRunResultaat(int aantalPersonen, int aantalFiles) {
            this.aantalPersonen = aantalPersonen;
            this.aantalFiles = aantalFiles;
        }
    }


    private String geefResultatenbestand(final int taakId, final String datumPlanning) throws IOException {
        final Path path = geefResultaatsetTotalenFile(taakId, datumPlanning);
        Assert.assertNotNull(path);
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private Path geefResultaatsetTotalenFile(final int selectieTaakId, final String datumUitvoer) throws IOException {
        final Path selectiePath = selectieFileService.getSelectietaakResultaatPath(selectieTaakId, DatumConstanten.getBasicIsoDateInt(datumUitvoer));
        if (selectiePath.toFile().exists()) {
            return Files.list(selectiePath)
                    .filter(p -> p.getFileName()
                            .toString().matches(".*totalen.xml"))
                    .findFirst().orElse(null);
        }
        return null;
    }

    private void zetSelectiebestandRootFolder() {
        configuratie.setSelectiebestandFolder(new File(storyService.getOutputDir(), "selectielijsten_" +
                DatumUtil.vanDateTimeNaarLong(DatumUtil.nuAlsZonedDateTime())).getAbsolutePath());
    }

}
