/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DatabaseDocker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * JBehave steps tbv persoondata.
 */
public class PersoonSteps extends Steps {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Converteer Excel PL
     * @param filenaam de te converteren file
     */
    @Given("enkel initiele vulling uit bestand $filenaam")
    public void laadPersoonInMasterDatabase(String filenaam)
            throws ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException, IOException,
            TeLeverenAdministratieveHandelingenAanwezigException, InterruptedException, ExecutionException {
        final ClassPathResource resource = new ClassPathResource(filenaam);
        laadPersonen(Lists.newArrayList(resource), false);
    }

    /**
     * Converteer Excel PL's
     * @param filenamen de te converteren files
     */
    @Given("selectie personen uit bestanden: $filenamen")
    public void laadSelectiePersonenUitBestanden(ExamplesTable filenamen) throws Exception {
        final List<Map<String, String>> rows = filenamen.getRows();
        final List<Resource> resources = Lists.newArrayList();
        for (Map<String, String> row : rows) {
            final String filenaam = row.get("filenaam");
            resources.add(new ClassPathResource(filenaam));
        }
        laadPersonen(resources, true);
    }

    /**
     * Converteer Excel PL's
     * @param filenamen de te converteren files
     */
    @Given("personen uit bestanden: $filenamen")
    public void laadPersonenUitBestanden(ExamplesTable filenamen) throws Exception {
        final List<Map<String, String>> rows = filenamen.getRows();
        final List<Resource> resources = Lists.newArrayList();
        for (Map<String, String> row : rows) {
            final String filenaam = row.get("filenaam");
            resources.add(new ClassPathResource(filenaam));
        }
        laadPersonen(resources, false);
    }

    /**
     * Converteer Excel PL
     * @param filenaam de te converteren file
     */
    @Given("selectiepersonen uit bestand $filenaam")
    public void laadPersoonInSelectieDatabase(String filenaam) throws Exception {
        final ClassPathResource resource = new ClassPathResource(filenaam);
        laadPersonen(Lists.newArrayList(resource), true);
    }

    /**
     * Verwijdert alle personen uit de database middels een truncate cascade delete.
     */
    @Given("alle personen zijn verwijderd")
    public void verwijderAllePersonen() {
        JBehaveState.get().testdata().verwijderAllePersonen();
    }

    /**
     * Verwijdert alle personen uit de database middels een truncate cascade delete.
     */
    @Given("alle selectie personen zijn verwijderd")
    public void verwijderAlleSelectiePersonen() {
        JBehaveState.get().testdata().verwijderAlleSelectiePersonen();
    }

    /**
     * Vervangt, bij alle betrokkenheden waarbij het opgegeven anr van de pseudo persoon is geregistreerd, door de met
     * anr opgegevens ingeschreven persoon.
     * @param anrPseudo anr van de pseudo persoon die vervangen moet worden
     * @param anrIngeschreven anr van de ingeschreven persoon
     */
    @Given("Pseudo-persoon $anrPseudo is vervangen door ingeschreven persoon $anrIngeschrevene")
    public void vervangPseudoPersoonDoorIngeschrevenPersoon(final String anrPseudo, final String anrIngeschreven) {
        JBehaveState.get().testdata().vervangPseudoPersoonDoorIngeschrevenPersoon(anrPseudo, anrIngeschreven);
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
    public void persoonBetrokkenBijRelatie(String bsn, String betrokkenheidSoort, String relatieSoort) {
        Assert.assertEquals(1, JBehaveState.get().testdata().geefVanPersoonAantalBetrokkenhedenBijRelatie(bsn, betrokkenheidSoort, relatieSoort));
    }

    /**
     * Then log persoonsbeelden.
     * @param prefix the prefix
     * @throws IOException the io exception
     * @throws BlobException the blob exception
     */
    @Then("log persoonsbeelden $prefix")
    public void thenLogPersoonsbeelden(final String prefix) throws Exception {
        JBehaveState.get().testdata().thenLogPersoonsbeelden(prefix, null);
    }

    /**
     * Then log persoonsbeeld voor persoon met bsn.
     * @param bsn bsn
     * @param prefix the prefix
     * @throws IOException the io exception
     * @throws BlobException the blob exception
     */
    @Then("log persoonsbeeld voor persoon met bsn $bsn met prefix $prefix")
    public void thenLogPersoonsbeelden(final String bsn, final String prefix) throws Exception {
        JBehaveState.get().testdata().thenLogPersoonsbeelden(prefix, bsn);
    }

    private void laadPersonen(List<Resource> resources, boolean ookInSelectieDb) throws ExecutionException, InterruptedException {
        final BrpOmgeving brpOmgeving = JBehaveState.get();
        Thread.currentThread().setName(brpOmgeving.getNaam());
        final CompletableFuture<Void> masterFuture = CompletableFuture.runAsync(() -> {
            Thread.currentThread().setName(brpOmgeving.getNaam());
            ((DatabaseDocker.ExcelSupport) brpOmgeving.brpDatabase()).converteerExcel(resources.toArray(new Resource[resources.size()]));
        });
        final CompletableFuture<Void> selectieFuture =
                ookInSelectieDb ?
                CompletableFuture.runAsync(() -> {
                        Thread.currentThread().setName(brpOmgeving.getNaam());
                        ((DatabaseDocker.ExcelSupport) brpOmgeving.selectieDatabase()).converteerExcel(resources.toArray(new Resource[resources.size()]));
                }) : CompletableFuture.completedFuture(null);

        CompletableFuture.allOf(masterFuture, selectieFuture).get();

        int aantalBrp = (int) brpOmgeving.<DatabaseDocker>geefDocker(DockerNaam.BRPDB).template()
                .readonlyStream(jdbcTemplate -> jdbcTemplate.queryForList("select * from kern.perscache")).count();
        LOG.info("brp personen: " + aantalBrp);
        if(ookInSelectieDb) {
            int aantalSel = (int) brpOmgeving.<DatabaseDocker>geefDocker(DockerNaam.SELECTIEBLOB_DATABASE)
                    .template().readonlyStream(jdbcTemplate -> jdbcTemplate.queryForList("select * from kern.perscache")).count();

            LOG.info("selectie personen: " + aantalSel);
            Assert.assertEquals("aantal personen in brp en replica niet gelijk", aantalBrp, aantalSel);
        }
        Assert.assertTrue("geen personen geladen", aantalBrp > 0);
    }
}
