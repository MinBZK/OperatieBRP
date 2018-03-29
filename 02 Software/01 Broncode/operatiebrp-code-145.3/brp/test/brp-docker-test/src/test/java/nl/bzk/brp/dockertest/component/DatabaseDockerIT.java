/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Lists;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import junit.framework.TestCase;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.service.AutorisatieDataSqlVerzoek;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaAttribuutLiteral;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonData;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstFactory;
import nl.bzk.brp.test.common.dsl.autorisatie.AutorisatieDslVerzoek;
import nl.bzk.brp.test.common.dsl.autorisatie.InsertVolledigeGBAAutorisatie;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(JUnit4.class)
public class DatabaseDockerIT extends TestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testMinimaleOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();
        omgeving.brpDatabase().template().readonly(new AutorisatieDataSqlVerzoek());
        omgeving.stop();
    }

    @Test
    public void testDataLezen() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();

        omgeving.brpDatabase().entityManagerVerzoek().voerUit(entityManager ->  {
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                LOGGER.info("Aantal gemeenten : {}", resultList.size());
                assertFalse(resultList.isEmpty());
        });
        omgeving.stop();
    }

    @Test
    public void testDataSchrijven() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();

        //read uncommitted
        omgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(entityManager -> {
                entityManager.createNativeQuery("delete from kern.gem").executeUpdate();
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                assertTrue(resultList.isEmpty());
        });

        //read committed
        omgeving.brpDatabase().entityManagerVerzoek().voerUit(entityManager -> {
                final List<Gemeente> resultList = entityManager.createQuery("from Gemeente", Gemeente.class).getResultList();
                assertTrue(resultList.isEmpty());
        });

        omgeving.stop();
    }

    @Test
    public void testSqlScript() throws InterruptedException {

        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();
        omgeving.brpDatabase().template().readonly(jdbcTemplate -> {
                final List list = jdbcTemplate.queryForList("select * from autaut.levsautorisatie");
                assertTrue(list.isEmpty());
        });
        omgeving.autorisaties().laadAutorisaties(Lists.newArrayList(
                new ClassPathResource("/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding")), omgeving.brpDatabase());
        omgeving.brpDatabase().template().readonly(jdbcTemplate -> {
                final List list = jdbcTemplate.queryForList("select * from autaut.levsautorisatie");
                assertFalse(list.isEmpty());
        });
        omgeving.stop();
    }

    @Test
    public void testInsertAutorisatie() throws InterruptedException, IOException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();
        omgeving.brpDatabase().template().readonly(new AutorisatieDataSqlVerzoek());
        omgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(
                new AutorisatieDslVerzoek(new ClassPathResource("levering_autorisaties/Bewerker_autorisatie"), "afleverpunt"));
        omgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(
                new AutorisatieDslVerzoek(new ClassPathResource("levering_autorisaties/Pop_bep_geboortedatum"), "afleverpunt"));
        omgeving.stop();
    }

    @Test
    public void testInsertLO3Autorisatie() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();
        omgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(new InsertVolledigeGBAAutorisatie());

        final List collect = (List) omgeving.brpDatabase().template().readonlyStream(jdbcTemplate ->
                jdbcTemplate.queryForList("select * from autaut.dienstbundello3rubriek")).collect(
                        Collectors.toList());

        Assert.assertFalse(collect.isEmpty());

        omgeving.stop();
    }


    @Test
    public void testAutorisatieDataSqlVerzoek() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();
        omgeving.brpDatabase().template().readwrite(new AutorisatieDataSqlVerzoek());
        omgeving.stop();
    }

    @Test
    public void testCommit() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();

        Function<JdbcTemplate, Collection> countTemplate = jdbcTemplate -> jdbcTemplate.queryForList("select * from kern.partij");
        omgeving.start();
        Assert.assertTrue(omgeving.brpDatabase().template().readonlyStream(countTemplate).count() > 0);
        omgeving.brpDatabase().template().readonly(jdbcTemplate -> jdbcTemplate.update("truncate kern.partij cascade"));
        Assert.assertFalse(omgeving.brpDatabase().template().readonlyStream(countTemplate).count() == 0);
        omgeving.brpDatabase().template().readwrite(jdbcTemplate -> jdbcTemplate.update("truncate kern.partij cascade"));
        Assert.assertTrue(omgeving.brpDatabase().template().readonlyStream(countTemplate).count() == 0);
        omgeving.stop();
    }

    @Test
    public void testMeerdereOmgevingenParallelPersonenInladen() throws ExecutionException, InterruptedException {
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                            try {
                                this.testInlezenBeideDatabases();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                ,
                CompletableFuture.runAsync(() -> {
                            try {
                                TimeUnit.SECONDS.sleep(5);
                                this.testInlezenBeideDatabases();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
        ).get();
    }

    @Test
    public void testInlezenBeideDatabases() throws ExecutionException, InterruptedException {
        final String filenaam = "/LO3PL/R2286/Kim.xls";
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB, DockerNaam.SELECTIEBLOB_DATABASE).build();
        omgeving.start();

        try {
            IntStream.range(0, 100).forEach(value -> {
                LOGGER.info("LOOP {}", value + 1);
                try {
                    omgeving.testdata().verwijderAlleSelectiePersonen();
                    CompletableFuture.allOf(
                            CompletableFuture.runAsync(() -> ((DatabaseDocker.ExcelSupport) omgeving.brpDatabase())
                                    .converteerExcel(new ClassPathResource(filenaam))),
                            CompletableFuture.runAsync(() -> ((DatabaseDocker.ExcelSupport) omgeving.selectieDatabase())
                                    .converteerExcel(new ClassPathResource(filenaam)))
                    ).get();

                    int aantalBrp = (int) omgeving.<DatabaseDocker>geefDocker(DockerNaam.BRPDB).template()
                            .readonlyStream(jdbcTemplate -> jdbcTemplate.queryForList("select * from kern.perscache")).count();
                    int aantalSel = (int) omgeving.<DatabaseDocker>geefDocker(DockerNaam.SELECTIEBLOB_DATABASE)
                            .template().readonlyStream(jdbcTemplate -> jdbcTemplate.queryForList("select * from kern.perscache")).count();
                    Assert.assertEquals(aantalBrp, aantalSel);
                    Assert.assertTrue(aantalBrp > 0);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            omgeving.stop();
        }
    }

    @Test
    public void testInlezenDatabases() throws ExecutionException, InterruptedException {
        final String filenaam = "/LO3PL/R2286/Kim.xls";
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();

        try {
            IntStream.range(0, 100).forEach(value -> {
                LOGGER.info("LOOP {}", value + 1);
                try {
                    omgeving.testdata().verwijderAllePersonen();
                    CompletableFuture.allOf(
                            CompletableFuture.runAsync(() -> ((DatabaseDocker.ExcelSupport) omgeving.brpDatabase())
                                    .converteerExcel(new ClassPathResource(filenaam)))
                    ).get();

                    int aantalBrp = (int) omgeving.<DatabaseDocker>geefDocker(DockerNaam.BRPDB).template()
                            .readonlyStream(jdbcTemplate -> jdbcTemplate.queryForList("select * from kern.perscache")).count();

                    Assert.assertTrue(aantalBrp > 0);

                } catch (Exception e) {
                    LOGGER.error("fout opgetreden", e);
                    throw new RuntimeException(e);
                }
            });
        } finally {
            omgeving.stop();
        }
    }


    @Test
    public void tempReproROOD2398() throws InterruptedException, IOException, BlobException {
        final List<String> lines = Files.lines(Paths.get("/home/dennis/brp/operatiebrp-code/brp/test/brp-docker-test/src/test/resources/ROOD2398.sql"))
                        .collect(Collectors.toList());
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();

        final String filenaam = "/LO3PL/R2286/Kim.xls";

        final DatabaseDocker databaseDocker = omgeving.brpDatabase();
        ((DatabaseDocker.ExcelSupport) databaseDocker).converteerExcel(new ClassPathResource(filenaam));
        databaseDocker.template().readwrite(jdbcTemplate -> {
            final String[] strings = lines.toArray(new String[lines.size()]);
            jdbcTemplate.batchUpdate(strings);
        });
        databaseDocker.entityManagerVerzoek().voerUitTransactioneel(entityManager -> {
            final Persoon persoon = entityManager.find(Persoon.class, 1L);
            final PersoonBlob persoonBlob = Blobber.maakBlob(persoon);

            try (OutputStream os = new FileOutputStream("/tmp/testtest")){
                final byte[] bytes = Blobber.toJsonBytes(persoonBlob);
                IOUtils.write(bytes, os);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void tempReproROOD2398_2() {

        try (InputStream inputStream = new FileInputStream("/tmp/testtest")){

            final byte[] bytes1 = IOUtils.toByteArray(inputStream);
            System.out.println(new String(bytes1));
            PersoonBlob persoonBlob = Blobber.deserializeNaarPersoonBlob(bytes1);

            final PersoonData persData = new PersoonData(persoonBlob, null, null);
            final Persoonslijst persoonslijst = PersoonslijstFactory.maak(persData);
            //System.out.println(persoonslijst.toStringVolledig());

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
            final Persoonslijst persoonslijst1 = persoonslijst.beeldVan().vorigeHandeling();
            //System.out.println(persoonslijst1.toStringVolledig());

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
            final Persoonslijst persoonslijst2 = persoonslijst1.beeldVan().vorigeHandeling();
            System.out.println(persoonslijst2.toStringVolledig());

            final Set<MetaGroep> metaGroeps = persoonslijst2.getModelIndex().geefGroepenVanElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING));
//            final Optional<MetaRecord> actueleRecord = persoonslijst2.getActueleRecord(metaGroeps.iterator().next());

            final LijstExpressie lijstExpressie =
                    (LijstExpressie) BRPExpressies.evalueer(ExpressieParser.parse("Persoon.Bijhouding.DatumAanvangGeldigheid WAARBIJ attribuutalswaarde = ONWAAR"), persoonslijst2);

            for (Expressie expressie : lijstExpressie) {
                MetaAttribuutLiteral metaAttribuutLiteral = (MetaAttribuutLiteral) expressie;
                System.out.println(metaAttribuutLiteral.getMetaAttribuut().getParentRecord().getVoorkomensleutel());
            }

            System.out.println(lijstExpressie);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    @Ignore
    public void tempPrepINTEST2851() throws InterruptedException {

        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BRPDB).build();
        omgeving.start();


        omgeving.<DatabaseDocker.ExcelSupport>geefDocker(DockerNaam.BRPDB).
                converteerExcel(new FileSystemResource("/home/dennis/Desktop/IV_LG01-115-P1.xls"));
        omgeving.<DatabaseDocker.ExcelSupport>geefDocker(DockerNaam.BRPDB).
                synchroniseer(new FileSystemResource("/home/dennis/Desktop/M01_LG01-115-P1.xls"));

        omgeving.brpDatabase().entityManagerVerzoek().voerUitTransactioneel(entityManager -> {
            final Persoon persoon = entityManager.find(Persoon.class, 1L);
            final PersoonBlob persoonBlob = Blobber.maakBlob(persoon);

            try (OutputStream os = new FileOutputStream("/tmp/testtest")){
                final byte[] bytes = Blobber.toJsonBytes(persoonBlob);
                IOUtils.write(bytes, os);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
