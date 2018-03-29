/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import static nl.bzk.brp.test.common.dsl.selectie.SelectietaakParser.DIENST_SLEUTEL;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.AbnormalProcessTerminationException;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.ComponentException;
import nl.bzk.brp.dockertest.component.DatabaseDocker;
import nl.bzk.brp.dockertest.component.Docker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.JMXSupport;
import nl.bzk.brp.dockertest.component.ProcessHelper;
import nl.bzk.brp.dockertest.jbehave.JBehaveState;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.DslSectieParser;
import nl.bzk.brp.test.common.dsl.selectie.SelectielijstParser;
import nl.bzk.brp.test.common.dsl.selectie.SelectietaakParser;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * SelectieService.
 */
public final class SelectieService {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("js");
    private static final int MAX_WACHT_TOT_KLAAR_POGINGEN = 5;

    private static final String JMX_ATTRIBUTE_ID = "SelectieRunId";
    private static final String JMX_ATTRIBUTE_ERROR = "Error";

    private static final String JMX_ATTRIBUTE_MOEST_STOPPEN = "MoestStoppen";
    private static final String NEW_LINE_SEPARATOR = "\n";


    private static final ThreadLocal<Map<String, Selectietaak>> SELECTIE_TAAK_THREAD_LOCAL = new ThreadLocal<>();
    private final BrpOmgeving brpOmgeving;
    private ObjectName objectNameSelectieJob;
    private ObjectName objectNameSelectieRunStatus;
    private ObjectName objectNameSelectieProtocollering;

    /**
     * @param brpOmgeving brpOmgeving
     */
    public SelectieService(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
        try {
            this.objectNameSelectieJob = new ObjectName("selectie-selectie:name=SelectieJob");
            this.objectNameSelectieRunStatus = new ObjectName("selectie-selectie:name=SelectieRunStatus");
            this.objectNameSelectieProtocollering = new ObjectName("selectie-protocollering-selectie:name=SelectieProtocolleringJob");
        } catch (MalformedObjectNameException e) {
            throw new ComponentException(e);
        }
    }

    /**
     * start selectie.
     */
    public void startSelectie() {
        LOGGER.info("start selectierun");
        brpOmgeving.cache().refresh();
        brpOmgeving.<JMXSupport>geefDocker(DockerNaam.SELECTIE).voerUit("startSelectieJob", objectNameSelectieJob);
    }

    /**
     * stop selectie.
     */
    public void stopSelectie() {
        LOGGER.info("stop selectierun");
        brpOmgeving.<JMXSupport>geefDocker(DockerNaam.SELECTIE).voerUit("stopSelectieJob", objectNameSelectieJob);
    }

    /**
     * @param minuten minuten.
     * @throws InterruptedException interrupted.
     */
    public void wachtTotSelectieKlaar(Integer minuten) throws InterruptedException {
        LOGGER.info(String.format("wacht %d minuten tot selectierun klaar en gestopt", minuten));
        final int slaapTijdSec = 5;
        final int pogingen = (int) TimeUnit.MINUTES.toSeconds(minuten) / slaapTijdSec;
        doWachtTotSelectieStatus(1, false, pogingen, slaapTijdSec);
    }

    /**
     * Wacht tot protocollering selectie gereed is.
     * @param minuten minuten wachttijd
     * @throws InterruptedException interrupted
     */
    public void wachtTotProtocollerenKlaar(final Integer minuten) throws InterruptedException {
        LOGGER.info(String.format("wacht %d minuten tot protocollering gestopt", minuten));
        final int slaapTijdSec = 5;
        final int maxAantalPogingen = (int) TimeUnit.MINUTES.toSeconds(minuten) / slaapTijdSec;
        doWachtTotProtocollerenKlaar(1, maxAantalPogingen, slaapTijdSec);
    }

    /**
     * @throws InterruptedException interrupted.
     */
    public void wachtTotSelectieGestart() throws InterruptedException {
        LOGGER.info("wacht tot selectierun gestart");
        doWachtTotSelectieStatus(1, true, MAX_WACHT_TOT_KLAAR_POGINGEN, 10);
    }

    /**
     * assertSelectieRunGestopt.
     */
    public void assertSelectieRunGestopt() {
        LOGGER.info("assertSelectieRunGestopt");
        final boolean gestopt = !JBehaveState.get().selectieService().selectieLoopt();
        LOGGER.info("selectie gestopt: " + gestopt);
        assertTrue(gestopt);
    }

    /**
     * Verwijdert inhoud van de target/selectie directory.
     */
    public void deleteTargetSelectieDirectory() throws IOException {
        LOGGER.info("delete inhoud target/selectie directory, m.u.v. target/selectie/selectiebestand");
        final Path selectiePad = Paths.get("").resolveSibling("target/selectie");
        if (selectiePad.toFile().exists()) {
            verwijderDirectoryInhoud(selectiePad);
        }
    }


    /**
     * Verwijdert de inhoud van de selectiebestand directory. De directory zelf blijft bestaan, ivm mounting.
     */
    public void deleteTargetSelectiebestandDirectory() throws IOException {
        LOGGER.info("delete inhoud van target/selectie/selectiebestand directory");
        final Path selectiePad = Paths.get("").resolveSibling("target/selectie");
        if (selectiePad.toFile().exists()) {
            verwijderSelectiebestandDirectory(selectiePad);
        }
    }


    /**
     * Voor nu controleert deze methode alleen datuitvoer.
     * @param rows rijen
     */
    public void waardeControleSelectietaak(List<Map<String, String>> rows) {
        brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
            for (Map<String, String> row : rows) {
                final String datuitvoer = row.get("datuitvoer");
                final Integer datumuitvoer = StringUtils.isEmpty(datuitvoer) ? null : DatumConstanten.getBasicIsoDateInt(datuitvoer);
                final String dienstSleutel = row.get(DIENST_SLEUTEL);
                final Selectietaak selectietaak = SELECTIE_TAAK_THREAD_LOCAL.get().get(dienstSleutel);
                final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
                final String sql =
                        "SELECT datuitvoer FROM autaut.his_seltaak WHERE seltaak = :seltaak AND tsverval is null";
                final Map<String, Object> parameters = new HashMap<>();
                parameters.put("seltaak", selectietaak.getId());
                final Integer datuitvoerRij = template.queryForObject(sql, parameters, Integer.class);
                Assert.isTrue(datumuitvoer != null ? datumuitvoer.equals(datuitvoerRij) : datuitvoerRij == null, "datum uitvoer niet gelijk");
            }
        });
    }

    /**
     * Kopieert de kern.perscache tabellen van de masterdb naar de selectiedb
     */
    public void kopieerPerscacheNaarSelectieDatabase() {
        LOGGER.info("kopieerPerscacheNaarSelectieDatabase");
        final PersoonCacheSqlVerzoek verzoek = new PersoonCacheSqlVerzoek();
        brpOmgeving.brpDatabase().template().readonly(verzoek);
        brpOmgeving.selectieDatabase().template().readonly(jdbcTemplate -> {
            for (Map<String, Object> row : verzoek.getPersoonCacheData()) {
                final Long id = (Long) row.get("id");
                final byte[] afnemerindicatiegegevens = (byte[]) row.get("afnemerindicatiegegevens");
                final Long lockversieafnemerindicatiege = (Long) row.get("lockversieafnemerindicatiege");

                final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);

                final String
                        sql =
                        "UPDATE kern.perscache SET afnemerindicatiegegevens = :afnemerindicatiegegevens, lockversieafnemerindicatiege = "
                                + ":lockversieafnemerindicatiege "
                                + "where "
                                + "id = :id";
                final Map<String, Object> parameters = new HashMap<>();
                parameters.put("afnemerindicatiegegevens", afnemerindicatiegegevens);
                parameters.put("lockversieafnemerindicatiege", lockversieafnemerindicatiege);
                parameters.put("id", id);
                final int rowsUpdated = template.update(sql, parameters);
                LOGGER.info(String.format("%s rijen geupdate in selectie daatabase", rowsUpdated));
            }
        });
    }

    /**
     * @param rows
     */
    public void assertSelectietakenStatus(List<Map<String, String>> rows) {
        LOGGER.info("assertSelectietakenStatus");
        brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
            for (Map<String, String> row : rows) {
                final String status = row.get("status");
                final String gewijzigddoor = row.get("gewijzigddoor");
                final String dienstSleutel = row.get(DIENST_SLEUTEL);
                final Selectietaak selectietaak = SELECTIE_TAAK_THREAD_LOCAL.get().get(dienstSleutel);
                final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
                final String
                        sql =
                        "SELECT status FROM autaut.his_seltaakstatus WHERE seltaak = :seltaak AND statusgewijzigddoor = :gewijzigddoor AND tsverval is null";
                final Map<String, Object> parameters = new HashMap<>();
                parameters.put("seltaak", selectietaak.getId());
                parameters.put("gewijzigddoor", gewijzigddoor);
                final Integer taakStatus = template.queryForObject(sql, parameters, Integer.class);
                final SelectietaakStatus selectietaakStatus = SelectietaakStatus.parseId(taakStatus);
                final SelectietaakStatus selectietaakStatusGiven = SelectietaakStatus.valueOf(status);
                Assert.isTrue(selectietaakStatusGiven == selectietaakStatus, "status niet gelijk: " + selectietaakStatus);
            }
        });
    }


    /**
     * Plaatst de gegeven selectietaken in de database.
     * @param resource een DSL bestand
     */
    public void maakSelectietaken(final Resource resource) {
        maakSelectietaken(DslSectieParser.parse(resource));
    }

    /**
     * Plaatst de gegeven selectietaken in de database.
     * @param sectieList lijst met DSL selectietaken
     */
    public void maakSelectietaken(final List<DslSectie> sectieList) {
        LOGGER.info("maakSelectietaken");
        final Map<String, Selectietaak> dienstsleutelSelectietaakMap = new HashMap<>();

        brpOmgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(entityManager -> {
            for (DslSectie taakDsl : sectieList) {
                final DienstSleutel dienstSleutel = taakDsl.geefStringValue(DIENST_SLEUTEL)
                        .map(DienstSleutel::new)
                        .orElseThrow(() -> new IllegalArgumentException("dienstSleutel is verplicht: " + taakDsl.toString()));
                final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());

                final Function<DienstSleutel, Dienst>
                        dienstResolver =
                        ref -> entityManager.getReference(Dienst.class, brpOmgeving.autorisaties().geefDienstVoorRef(dienstSleutel).getId());
                final Selectietaak taak = SelectietaakParser.parse(taakDsl,
                        dienstResolver,
                        ref -> entityManager.getReference(ToegangLeveringsAutorisatie.class,
                                brpOmgeving.autorisaties().geefToegangleveringsautorisatieVoorRef(dienstSleutel).getId()));
                final SelectietaakHistorie hisTaak = new SelectietaakHistorie(taak);
                hisTaak.setIndicatieSelectielijstGebruiken(taak.isIndicatieSelectielijstGebruiken());
                hisTaak.setPeilmomentFormeelResultaat(taak.getPeilmomentFormeelResultaat());
                hisTaak.setDatumPlanning(taak.getDatumPlanning());
                hisTaak.setPeilmomentMaterieel(taak.getPeilmomentMaterieel());
                hisTaak.setPeilmomentMaterieelResultaat(taak.getPeilmomentMaterieelResultaat());
                hisTaak.setDatumTijdRegistratie(nuTijd);
                taak.setDienst(entityManager.getReference(Dienst.class, dienstResolver.apply(dienstSleutel).getId()));
                taak.setSelectietaakHistorieSet(Sets.newHashSet(hisTaak));

                final SelectietaakStatusHistorie hisTaakStatus = new SelectietaakStatusHistorie(taak);
                hisTaakStatus.setDatumTijdRegistratie(nuTijd);
                hisTaakStatus.setStatusGewijzigdDoor("Systeem");
                hisTaakStatus.setStatus(taak.getStatus());
                taak.addSelectietaakStatusHistorieSet(hisTaakStatus);

                entityManager.persist(taak);
                entityManager.flush();
                final Integer taakId = taak.getId();
                if (taakId == null) {
                    throw new UnsupportedOperationException("Geen selectietaak id verkregen.");
                }
                dienstsleutelSelectietaakMap.put(dienstSleutel.getWaarde(), taak);
                SELECTIE_TAAK_THREAD_LOCAL.set(dienstsleutelSelectietaakMap);
            }
        });
    }

    /**
     * Assert dat de gegeven bestanden aanwezig zijn.
     * @param datumUitvoer datumUitvoer
     * @param dienstSleutel dienstsleutel van de taak
     * @param rows bestanden
     */
    public void assertFilesAanwezigMetExpectations(String dienstSleutel, String datumUitvoer, List<Map<String, String>> rows) throws Exception {
        LOGGER.info("assertFilesAanwezigMetExpectations");
        Integer selectieRunId = null;
        final int datumUitvoerAlsInteger = DatumConstanten.getBasicIsoDateInt(datumUitvoer);

        final AttributeList attributeList = voerUitAttributen(new String[]{JMX_ATTRIBUTE_ID, JMX_ATTRIBUTE_ERROR, JMX_ATTRIBUTE_MOEST_STOPPEN});
        for (Attribute attribute : attributeList.asList()) {
            if (JMX_ATTRIBUTE_ID.equals(attribute.getName())) {
                selectieRunId = (Integer) attribute.getValue();
            }
            if (JMX_ATTRIBUTE_MOEST_STOPPEN.equals(attribute.getName())) {
                final boolean moestStoppen = (Boolean) attribute.getValue();
                Assert.isTrue(!moestStoppen, "run is gestopt door request of fout");
            }
            if (JMX_ATTRIBUTE_ERROR.equals(attribute.getName())) {
                final boolean error = (Boolean) attribute.getValue();
                Assert.isTrue(!error, "run is gestopt met error");
            }
        }
        Assert.notNull(selectieRunId, "run id niet gevonden");
        final Selectietaak selectietaak = SELECTIE_TAAK_THREAD_LOCAL.get().get(dienstSleutel);
        final String folderNaam = "selectietaak_" + selectietaak.getId() + "_" + datumUitvoerAlsInteger;
        final Docker docker = brpOmgeving.geefDocker(DockerNaam.SELECTIE_SCHRIJVER);
        final String dockerContainerId = docker.getDockerContainerId();
        final Path selectiePad = maakTargetSelectiePad();
        final Path taakPad = selectiePad.resolve(folderNaam);
        // Mogelijk huidig taakpad verwijderen om false positives te voorkomen.
        FileUtils.deleteDirectory(taakPad.toFile());
        try {
            ProcessHelper.DEFAULT.startProces(brpOmgeving.getDockerCommandList("cp",
                    dockerContainerId + ":/selectie/" + folderNaam, "target/selectie/"));
        } catch (AbnormalProcessTerminationException e) {
            //deze fout negeren we voor nu. Op windows met laatste docker versie ontvangen we een process fout terwijl
            // de kopieer actie goed gaat
            LOGGER.info("fout bij kopieren selectie bestanden", e);
        }
        String[] files = selectiePad.resolve(folderNaam).toFile().list();
        Assert.notNull(files, "geen files aanwezig");
        final Set<String> persoonXmlFiles = Arrays.stream(files)
                .filter(p -> p.matches(".*personen.xml"))
                .collect(Collectors.toSet());
        final Set<String> totalenFiles = Arrays.stream(files)
                .filter(p -> p.matches(".*totalen.xml"))
                .collect(Collectors.toSet());
        final Set<String> controlebestanden = Arrays.stream(files)
                .filter(p -> p.contains("steekproef"))
                .collect(Collectors.toSet());
        for (final Map<String, String> expectation : rows) {
            final String type = expectation.get("type");
            final String aantal = expectation.get("aantal");
            if ("Resultaatset personen".equals(type)) {
                eval(String.valueOf(persoonXmlFiles.size()), aantal, type);
            } else if ("Resultaatset totalen".equals(type)) {
                eval(String.valueOf(totalenFiles.size()), aantal, type);
            } else if ("Controlebestand".equals(type)) {
                eval(String.valueOf(controlebestanden.size()), aantal, type);
            } else {
                throw new TestclientExceptie("onbekend type: " + type);
            }
        }
    }

    /**
     *
     * @param sectieList
     */
    public void maakSelectielijsten(List<DslSectie> sectieList) {
        LOGGER.info("maakSelectielijsten");
        final Function<DienstSleutel, Dienst> dienstResolver = ref -> brpOmgeving.autorisaties().geefDienstVoorRef(ref);
        try {
            //schoon de /selectiebestand volume
            final Docker volumeDocker = brpOmgeving.geefDocker(DockerNaam.SELECTIE_VOLUME);
            try {
                //schoon target directory
                ProcessHelper.DEFAULT.startProces(brpOmgeving.getDockerCommandList("exec",
                        volumeDocker.getDockerContainerId(), "rm", "-rf", "/selectiebestand/*"));
            } catch (AbnormalProcessTerminationException e) {
                //deze fout negeren we voor nu. Op windows met laatste docker versie ontvangen we een process fout terwijl
                // de kopieer actie goed gaat
                LOGGER.warn("fout bij verwijderen selectie bestanden", e);
            }

            final Path tempDirectory = Files.createTempDirectory(null);
            brpOmgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(entityManager ->
                    schrijfSelectiebestandenNaarDirectory(tempDirectory, sectieList, dienstResolver));
            //kopieer bestanden stuk voor stuk
            Arrays.asList(tempDirectory.toFile().listFiles()).forEach(file ->
                    ProcessHelper.DEFAULT.startProces(brpOmgeving.getDockerCommandList("cp", file.getAbsolutePath(),
                            volumeDocker.getDockerContainerId() + ":/selectiebestand/")));

        } catch (IOException e) {
            throw new IllegalStateException("Fout bij aanmaken directory structuur voor selectielijsten", e);
        }
    }

    /**
     *
     * @throws IOException
     */
    public void startProtocollerenVoorSelecties() throws IOException {
        LOGGER.info("start selectie protocollering");
        brpOmgeving.<JMXSupport>geefDocker(DockerNaam.SELECTIE_PROTOCOLLERING).
                voerUit("startProtocolleren", objectNameSelectieProtocollering);
    }

    /**
     *
     */
    public void reset() {
        if (brpOmgeving.bevat(DockerNaam.ROUTERINGCENTRALE)) {
            brpOmgeving.asynchroonBericht().purge();
        }
    }

    /**
     *
     * @param expectedAantalLevsaantek
     * @param expectedPersonen
     */
    public void assertGeprotocolleerdVoorSelectie(final int expectedAantalLevsaantek, final int expectedPersonen) {
        brpOmgeving.<DatabaseDocker>geefDocker(DockerNaam.PROTOCOLLERINGDB).template()
                .readonly(jdbcTemplate -> {
                            final Integer actualLevsaantek = jdbcTemplate.queryForObject("select count(*) from prot.levsaantek", Integer.class);
                            org.junit.Assert.assertEquals("Aanta levsaantekeningen komt niet overeen", expectedAantalLevsaantek, actualLevsaantek.intValue());
                            final Integer actualAantalPersonen = jdbcTemplate.queryForObject("select count(*) from prot.levsaantekpers", Integer.class);
                            org.junit.Assert.assertEquals("Aantal personen komt niet overeen", expectedPersonen, actualAantalPersonen.intValue());
                        }
                );
    }

    public void setStatusOpTeProtocolleren(final List<DslSectie> sectieList) {
        final List<String> selectietaakIds = new ArrayList<>();
        for (DslSectie taakDsl : sectieList) {
            final DienstSleutel dienstSleutel = taakDsl.geefStringValue(DIENST_SLEUTEL)
                    .map(DienstSleutel::new)
                    .orElseThrow(() -> new IllegalArgumentException("dienstSleutel is verplicht: " + taakDsl.toString()));
            selectietaakIds.add(SELECTIE_TAAK_THREAD_LOCAL.get().get(dienstSleutel.getWaarde()).getId().toString());
        }

        brpOmgeving.<DatabaseDocker>geefDocker(DockerNaam.BRPDB).template()
                .readwrite(
                        jdbcTemplate -> jdbcTemplate.update(String.format("UPDATE autaut.seltaak SET status = %d WHERE id IN (%s)",
                                SelectietaakStatus.TE_PROTOCOLLEREN.getId(), String.join(",", selectietaakIds)))

                );
    }

    private void doWachtTotSelectieStatus(final int poging, final boolean gestart, final int maxPogingen, final int slaaptijd) throws InterruptedException {
        LOGGER.info(String.format("wacht tot selectierun %s, poging %d ", gestart ? "gestart" : "gestopt", poging));
        final boolean selectieLoopt = selectieLoopt();
        LOGGER.info("selectie loopt: " + selectieLoopt);
        if (gestart == selectieLoopt) {
            LOGGER.info("selectie controle klaar");
            return;
        }
        if (poging == maxPogingen) {
            throw new TestclientExceptie("niet klaar");
        }
        TimeUnit.SECONDS.sleep(slaaptijd);
        doWachtTotSelectieStatus(poging + 1, gestart, maxPogingen, slaaptijd);
    }

    private void doWachtTotProtocollerenKlaar(final int poging, final int maxPogingen, final int slaaptijd) throws InterruptedException {
        LOGGER.info(String.format("wacht tot selectie-protocollering klaar, poging %d ", poging));
        final boolean protocolleringLoopt = (Boolean) brpOmgeving.<JMXSupport>geefDocker(DockerNaam.SELECTIE_PROTOCOLLERING).
                voerUit("wachtTotProtocollerenGestopt", objectNameSelectieProtocollering);
        LOGGER.info(String.format("selectie-protocollering loopt: %b", protocolleringLoopt));
        if (poging == maxPogingen) {
            throw new TestclientExceptie("selectie-protocollering selectie niet klaar");
        }
        if (!protocolleringLoopt) {
            LOGGER.info("selectie-protocollering klaar");
            return;
        }
        TimeUnit.SECONDS.sleep(slaaptijd);
        doWachtTotProtocollerenKlaar(poging + 1, maxPogingen, slaaptijd);
    }

    /**
     * @return indicatie dat de selectie loopt.
     */
    private boolean selectieLoopt() {
        return (Boolean) brpOmgeving.<JMXSupport>geefDocker(DockerNaam.SELECTIE).voerUit("isJobRunning", objectNameSelectieJob);
    }

    private Path maakTargetSelectiePad() throws IOException {
        final Path selectiePad = Paths.get("").resolveSibling("target/selectie");
        if (!selectiePad.toFile().exists()) {
            Files.createDirectories(selectiePad);
        }
        return selectiePad;
    }


    private void verwijderDirectoryInhoud(final Path root) throws IOException {
        LOGGER.info("verwijderDirectoryInhoud: " + root);
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!"selectielijst.csv".equals(file.getFileName().toString())) {
                    Files.delete(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (!dir.equals(root) && !(dir.toString().contains("selectiebestand"))) {
                    Files.delete(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void verwijderSelectiebestandDirectory(final Path root) throws IOException {

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if ("selectielijst.csv".equals(file.getFileName().toString())) {
                    LOGGER.info("verwijder selectiebestand  :" + file.toString());
                    Files.delete(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (dir.getParent().toString().contains("selectiebestand")) {
                    LOGGER.info("verwijder selectiebestand dienst-subdirectory : " + dir);
                    Files.delete(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void schrijfSelectiebestandenNaarDirectory(final Path root, List<DslSectie> sectieList, Function<DienstSleutel, Dienst> dienstResolver) {
        final Map<DienstSleutel, Map<String, List<String>>> lijstenPerDienst = SelectielijstParser.parse(sectieList, dienstResolver);
        try {
            for (Map.Entry<DienstSleutel, Map<String, List<String>>> entry : lijstenPerDienst.entrySet()) {
                final DienstSleutel dienstSleutel = entry.getKey();
                final Selectietaak selectietaak = SELECTIE_TAAK_THREAD_LOCAL.get().get(dienstSleutel.getWaarde());
                final Path dienstDir = root.resolve(Paths.get(String.valueOf(selectietaak.getDienst().getId()), String.valueOf(selectietaak.getId())));
                Files.createDirectories(dienstDir);
                final Path selectielijstBestand = dienstDir.resolve(Paths.get("selectielijst.csv"));
                try (BufferedWriter writer = Files.newBufferedWriter(selectielijstBestand, Charset.forName("UTF-8"))) {
                    LOGGER.info(String.format("Start schrijven selectiebestand, pad = %s", selectielijstBestand.toAbsolutePath().toString()));
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
                    LOGGER.info(
                            String.format("Selectielijst gecreerd voor dienstsleutel %s, met header : %s en identificatienrs : %s", dienstSleutel.getWaarde(),
                                    selectielijst.getKey(),
                                    String.join(",", selectielijst.getValue())));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Fout bij aanmaken directory structuur voor selectielijsten", e);
        }
    }

    private AttributeList voerUitAttributen(final String[] attributen) {
        try {
            return brpOmgeving.<JMXSupport>geefDocker(DockerNaam.SELECTIE).geefJMXAttributen(attributen, objectNameSelectieRunStatus);
        } catch (MBeanException | IOException | ReflectionException | InstanceNotFoundException e) {
            throw new ComponentException(String.format("Het is niet gelukt de selectie attributen op te vragen: %s", e.getMessage()), e);
        }
    }

    private void eval(final String input, final String controle, final String type) {
        try {
            final Boolean result = (Boolean) SCRIPT_ENGINE.eval(input + controle);
            Assert.isTrue(result, String.format("eval '%s' niet waar [%s%s]", type, input, controle));
        } catch (ScriptException e) {
            throw new TestclientExceptie(e);
        }
    }

    public void assertSelectietakenCorrectGeprotocolleerd(List<DslSectie> sectieList) {
        for (DslSectie taakDsl : sectieList) {
            final DienstSleutel dienstSleutel = taakDsl.geefStringValue(DIENST_SLEUTEL)
                    .map(DienstSleutel::new)
                    .orElseThrow(() -> new IllegalArgumentException("dienstSleutel is verplicht: " + taakDsl.toString()));
            final Selectietaak selectietaak = SELECTIE_TAAK_THREAD_LOCAL.get().get(dienstSleutel.getWaarde());

            final Map<String, String> filterMap = Maps.newHashMap();
            filterMap.put("dienst", String.valueOf(selectietaak.getDienst().getId()));
            final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = brpOmgeving.autorisaties().geefToegangleveringsautorisatieVoorRef(dienstSleutel);

            if (selectietaak.getPeilmomentFormeelResultaat() != null) {
                final List<Map<String, Object>>
                        lijst =
                        brpOmgeving.protocollering()
                                .geefProtocolleringRecords(String.valueOf(toegangLeveringsAutorisatie.getLeveringsautorisatie().getId()),
                                        String.valueOf(toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().getId()), filterMap);
                Assert.isTrue(lijst.size() == 1, "teveel protocollering records gevonden voor controle");
                final Timestamp tsaanvformeleperioderes = (Timestamp) lijst.get(0).get(ProtocolleringHelper.COL_TSAANVFORMELEPERIODERES);
                final Timestamp tseindeformeleperioderes = (Timestamp) lijst.get(0).get(ProtocolleringHelper.COL_TSEINDEFORMELEPERIODERES);
                Assert.isTrue(selectietaak.getPeilmomentFormeelResultaat().equals(tsaanvformeleperioderes), "tsaanvformeleperioderes niet correct");
                Assert.isTrue(selectietaak.getPeilmomentFormeelResultaat().equals(tseindeformeleperioderes), "tseindeformeleperioderes niet correct");
            }
        }
    }
}
