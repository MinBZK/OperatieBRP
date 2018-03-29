/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import static org.junit.Assert.assertEquals;

import com.jcabi.aspects.RetryOnFailure;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView;
import nl.bzk.brp.funqmachine.jbehave.context.DefaultScenarioRunContext;
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioResult;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import nl.bzk.brp.funqmachine.processors.SqlProcessor;
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Parameter;
import org.jbehave.core.annotations.Then;

/**
 * JBehave steps voor alles wat met de Database te maken heeft.
 */
@Steps
public class DatabaseSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final DefaultScenarioRunContext runContext;
    private final BevraagbaarContextView contextView;
    private Path blobDir;
    private int scenarioState;
    private Map<String, String> variabelen = new HashMap<>();

    /**
     * Constructor.
     * @param runContext de run context van JBehave
     * @param contextView de context view
     */
    @Inject
    public DatabaseSteps(final DefaultScenarioRunContext runContext, final BevraagbaarContextView contextView) {
        this.runContext = runContext;
        this.contextView = contextView;
    }

    /**
     * BeforeStories: De blobs worden verwijderd voor elke test run.
     * @throws IOException als er een I/O probleem optreedt.
     */
    @BeforeStories
    public void deleteBlobs() throws IOException {
        final Path targetPath = FileSystems.getDefault().getPath("./target");

        // Verwijderen van de testgevallen in de jbehave folder
        blobDir = targetPath.resolve("blobs");
        if (blobDir.toFile().exists()) {
            // Verwijderen van de vorige blobs en aanmaken
            Files.walkFileTree(blobDir, new BlobFolderRemover());
        }
        blobDir = Files.createDirectory(blobDir);

    }

    /**
     * Voor story.
     */
    @BeforeStory
    public void voorStory() {
        variabelen.clear();

        SqlProcessor.getInstance().voerBatchUpdateUit(
                "truncate kern.admhnd CASCADE",
                "truncate kern.betr cascade",
                "truncate kern.relatie cascade",
                "truncate kern.onderzoek cascade",
                "truncate kern.pers cascade",
                "truncate verconv.lo3melding cascade",
                "truncate verconv.lo3voorkomen cascade",
                "truncate verconv.lo3ber cascade",
                "truncate ist.autorisatietabel cascade",
                "truncate ist.stapelrelatie cascade",
                "truncate ist.stapelvoorkomen cascade",
                "truncate ist.stapel cascade");
    }

    /**
     * BeforeScenario: voor elk scenario wordt er een counter gereset.
     */
    @BeforeScenario
    public void resetScenarioState() {
        scenarioState = 0;
    }

    /**
     * AfterScenario: De persoonsbeelden worden vastgelegd.
     * @throws IOException Als er een I/O probleem optreedt
     */
    @AfterScenario
    public void logPersoonbeeldenNaScenario() throws IOException {
        logPersoonsbeelden("nascenario");
    }

    void logPersoonsbeelden(final String prefix) throws IOException {
        if (AbstractSpringJBehaveStories.isBlobsMaken()) {
            LOGGER.info("Start log persoonsbeelden");

            final String filePrefix = prefix != null && !prefix.isEmpty() ? prefix : "default";

            scenarioState++;
            final ScenarioResult scenarioResult = new ScenarioResult();
            scenarioResult.voorContext(contextView);

            final Path scenarioDirectory = blobDir.resolve(scenarioResult.getPath());
            Path dbStateDirectory = scenarioDirectory.resolve(String.format("dbstate%03d", scenarioState));
            if (!dbStateDirectory.toFile().exists()) {
                dbStateDirectory = Files.createDirectories(dbStateDirectory);
            }

            final SqlProcessor sqlProcessor = SqlProcessor.getInstance();
            // maak blobs van alle personen
            final List<Map<String, Object>> persIdList = sqlProcessor.voerQueryUit("SELECT id FROM kern.pers");
            LOGGER.debug("Aantal gevonden personen {}", persIdList.size());

            // sla blobs op van alle personen
            final String query = "SELECT pers, pershistorievollediggegevens, afnemerindicatiegegevens FROM kern.perscache";
            final List<Map<String, Object>> blobs = sqlProcessor.voerQueryUit(query);
            LOGGER.debug("Aantal gevonden blobs {}", blobs.size());

            for (final Map<String, Object> blob : blobs) {
                final Number persId = (Number) blob.get("pers");
                final byte[] persoonBytes = (byte[]) blob.get("pershistorievollediggegevens");
                final byte[] afnemerindicatieBytes = (byte[]) blob.get("afnemerindicatiegegevens");

                final Path persoonBlobFile = dbStateDirectory.resolve(String.format("%d-%s-persoon.blob.json", persId.intValue(), filePrefix));
                Files.write(persoonBlobFile, persoonBytes);

                if (afnemerindicatieBytes != null) {
                    final Path afnemerindicatiesBlobFile =
                            dbStateDirectory.resolve(String.format("%d-%s-afnemerindicatie.blob.json", persId.intValue(), filePrefix));
                    Files.write(afnemerindicatiesBlobFile, afnemerindicatieBytes);
                }

                final StepResult stepResult = new StepResult(StepResult.Soort.BLOB);
                stepResult.setId(persId);
                stepResult.setExpected(new String(persoonBytes, Charset.defaultCharset()));
                runContext.voegStepResultaatToe(stepResult);
            }
        }
    }

    /**
     * ==== Database prepareren met sql statement
     * Geeft de mogelijkheid om de database aan te passen. Dat kan in dit geval
     * door middel van een opgegeven SQL statement. Aangezien het een _Given_ step is, wordt
     * dit gebruikt als equivalent van een _PreQuery_, voor/aan het begin van het uitvoeren
     * van een scenario. +
     * NB: Met het resultaat van een select statement wordt niets gedaan.
     * @param statement het SQL statement dat wordt uitgevoerd
     */
    @Given("de database is aangepast met: $statement")
    public void preQueryStatement(final String statement) {
        try {
            LOGGER.info("Uitvoeren query: " + statement);
            SqlProcessor.getInstance().voerUpdateUit(statement);
            logPersoonsbeelden("sql");
        } catch (final SQLException | IOException e) {
            throw new StepException(e);
        }
    }

    /**
     * ==== Personen verwijderen
     * Verwijdert alle personen uit de database middels een truncate cascade delete.
     */
    @Given("alle personen zijn verwijderd")
    public void verwijderAllePersonen() {
        LOGGER.debug("Schoon alle personen");
        final SqlProcessor sqlProcessorKern = SqlProcessor.getInstance();
        sqlProcessorKern.verwijderAllePersonen();
        sqlProcessorKern.verwijderAlleBlokkeringen();
        final SqlProcessor sqlProcessorArchivering = SqlProcessor.getInstance("ber");
        sqlProcessorArchivering.verwijderAlleArchivering();
    }

    /**
     * ==== Aanwezigheid van een actuele betrokkenheid bij een relatie controleren
     * Verifieert voor een persoon de actuele betrokkenheid bij een relatie.
     * @param bsn het burgerservicenummer van de persoon, waarvan de betrokkenheid/relatie gecontroleerd moet worden.
     * @param betrokkenheidSoort de soort betrokkenheid (bijv. PARTNER)
     * @param relatieSoort de soort relatie (bijv. HUWELIJK)
     */
    // LET OP we gebruiken het woordje 'wel' voor het onderscheid met de 'niet'-step.
    //        Als je het woordje 'wel' weglaat (en de niet-variant staat in deze klasse ná de wel-variant) ontstaat er een conflict.
    //        JBehave interpreteert dan de waarde '$bsn niet' als '$bsn' en gebruikt dan onterecht de wel-variant.
    @Then("is in de database de persoon met bsn $bsn wel als $betrokkenheidSoort betrokken bij een $relatieSoort")
    public void persoonBetrokkenBijRelatie(final String bsn, final String betrokkenheidSoort, final String relatieSoort) {
        assertEquals(1, geefVanPersoonAantalBetrokkenhedenBijRelatie(bsn, betrokkenheidSoort, relatieSoort));
    }

    /**
     * ==== Afwezigheid van een actuele betrokkenheid bij een relatie controleren
     * Verifieert voor een persoon de afwezigheid actuele betrokkenheid bij een relatie.
     * @param bsn het burgerservicenummer van de persoon, waarvan de betrokkenheid/relatie gecontroleerd moet worden.
     * @param betrokkenheidSoort de soort betrokkenheid (bijv. PARTNER)
     * @param relatieSoort de soort relatie (bijv. HUWELIJK)
     */
    @Then("is in de database de persoon met bsn $bsn niet als $betrokkenheidSoort betrokken bij een $relatieSoort")
    public void persoonNietBetrokkenBijRelatie(final String bsn, final String betrokkenheidSoort, final String relatieSoort) {
        assertEquals(0, geefVanPersoonAantalBetrokkenhedenBijRelatie(bsn, betrokkenheidSoort, relatieSoort));
    }

    private int geefVanPersoonAantalBetrokkenhedenBijRelatie(final String bsnString, final String betrokkenheidSoort, final String relatieSoort) {
        final String[] persoon = bsnString.split(":");
        final String bsn;
        final String soortPersoon;
        if (persoon.length > 1) {
            bsn = persoon[1];
            if ("p".equalsIgnoreCase(persoon[0])) {
                soortPersoon = " and p.srt = 2";
            } else if ("i".equalsIgnoreCase(persoon[0])) {
                soortPersoon = " and p.srt = 1";
            } else {
                soortPersoon = "";
            }
        } else {
            bsn = bsnString;
            soortPersoon = "";
        }
        String query = "Select b.relatie from kern.pers p, kern.betr b, kern.relatie r, kern.his_relatie hr, kern.his_betr hb" +
                " where p.id=b.pers" +
                " and r.id=b.relatie" +
                " and hr.relatie=r.id" +
                " and hb.betr=b.id" +
                " and p.bsn= ?" +
                " and b.rol= ?" +
                " and r.srt= ?" +
                " and hr.tsverval IS NULL" +
                " and hb.tsverval IS NULL" +
                " and r.dateinde IS NULL" +
                soortPersoon;
        try {
            return SqlProcessor.getInstance()
                    .geefAantal(query, bsn, SoortBetrokkenheid.valueOf(betrokkenheidSoort).getId(), SoortRelatie.valueOf(relatieSoort).getId());
        } catch (final SQLException e) {
            throw new StepException(e);
        }
    }

    /**
     * Geeft de lijst met variabelen die gevuld zijn adhv queries.
     * @return de lijst met variabelen
     */
    public Map<String, String> getVariabelen() {
        return Collections.unmodifiableMap(variabelen);
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met verwerkingssoort aanduiding.
     */
    @AsParameters
    public static class GegevensRegels {
        @Parameter(name = "veld")
        String field;
        @Parameter(name = "waarde")
        String value;
    }

    @Then("heeft $variabele de waarde van de volgende query: $query")
    public void executeDatabaseQueryAndStoreResult(final String variabele, final String query) {
        try (ResultSet resultSet = SqlProcessor.getInstance().firstRow(query)) {
            if (!resultSet.next()) {
                throw new AssertionMisluktError("geen database gegevens teruggekregen");
            }
            variabelen.put(variabele, resultSet.getString(1));
        } catch (final SQLException e) {
            throw new StepException(e);
        }
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     * @param database de database waarop de query uitgevoerd dient te worden
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @RetryOnFailure(attempts = 5, delay = 2L, unit = TimeUnit.SECONDS)
    @Then("in $database heeft $query de volgende gegevens: $gegevensRegels")
    public void executeDatabaseQueryAndCheckTheResult(final String database, final String query, final List<GegevensRegels> gegevensRegels) {
        try (ResultSet resultSet = SqlProcessor.getInstance(database).firstRow(query)) {
            if (!resultSet.next()) {
                throw new AssertionMisluktError("geen database gegevens teruggekregen");
            }
            controleerGegevensRegels(gegevensRegels, resultSet);
        } catch (final SQLException e) {
            throw new StepException(e);
        }
    }

    private void controleerGegevensRegels(final List<GegevensRegels> gegevensRegels, final ResultSet resultSet) throws SQLException {
        final List<String> foutmeldingen = new ArrayList<>();
        for (final GegevensRegels gegevensRegel : gegevensRegels) {
            if ("----".equals(gegevensRegel.field)) {
                if (!resultSet.next()) {
                    throw new AssertionError("geen volgende database rij teruggekregen");
                }
            } else {
                controleerGegevenRegel(foutmeldingen, resultSet, gegevensRegel);
            }
        }
        if (!foutmeldingen.isEmpty()) {
            throw new AssertionError(foutmeldingen);
        }
    }

    private void controleerGegevenRegel(final List<String> foutmeldingen, final ResultSet resultSet,
                                        final GegevensRegels gegevensRegel) throws SQLException {
        final String field = gegevensRegel.field;
        final String actualValue = resultSet.getString(field);
        final CharSequence expectedValue = Objects.equals("NULL", gegevensRegel.value) ? null : gegevensRegel.value;

        if (actualValue != null && actualValue.length() == 1 && ("true".equals(expectedValue) || "false".equals(expectedValue))) {
            if (!StringUtils.startsWith(gegevensRegel.value, actualValue)) {
                foutmeldingen.add(String.format("Veld %s: Verkregen waarde %s start niet met verwachte waarde %s", field, expectedValue, actualValue));
            }
        } else if (!StringUtils.equals(actualValue, expectedValue)) {
            foutmeldingen.add(String.format("Veld %s: Verkregen waarde %s is niet gelijk aan verwachte waarde %s", field, actualValue, expectedValue));
        }
    }

    /**
     * Blokkeert een persoon obv anummer voor bv een verhuizing in de database.
     * @param anummer het anummer van de persoon
     */
    @Given("persoon met anummer $anummer is geblokkeerd")
    public void persoonIsGeblokkeerd(final String anummer) {
        try {
            SqlProcessor.getInstance().voerUpdateUit("insert into migblok.blokkering (anr, tsreg) values (" + anummer + ", now())");
        } catch (final SQLException e) {
            throw new StepException(e);
        }
    }

    private static class BlobFolderRemover extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
