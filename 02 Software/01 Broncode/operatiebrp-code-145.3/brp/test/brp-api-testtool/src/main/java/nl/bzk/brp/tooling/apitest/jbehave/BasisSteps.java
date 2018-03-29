/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.tooling.apitest.StoryOmgeving;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;
import org.springframework.util.Assert;

/**
 * De basis steps.
 */
public final class BasisSteps extends Steps {
    private static final Logger LOGGER = LoggerFactory.getLogger();


    /**
     * Zet het datum/tijdstip van leveren.
     * @param datum datum
     * @throws ParseException exceptie indien parsen van datum faalt
     */
    @Given("tijdstipleveren is $datum")
    public void zetTijd(final String datum) throws ParseException {
        BrpNu.set(DatumConstanten.getDate(datum));
    }

    /**
     * Reset BRP nu.
     */
    @BeforeScenario
    public void resetTijd() {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
    }

    /**
     * Laadt de personen.
     * @param path een directory
     */
    @Given("persoonsbeelden uit $path")
    public void givenPersoonsbeeld(final String path) throws IOException {
        givenPersonenUit(Lists.newArrayList(path));
    }

    /**
     * Laadt de personen.
     * @param paths een lijst met blob directories
     */
    @Given("personen uit $paths")
    public void givenPersonenUit(final List<String> paths) throws IOException {
        getStoryOmgeving().getAfnemerindicatieStubService().reset();
        getStoryOmgeving().getPersoonDataStubService().laadPersonen(paths);
    }

    /**
     * Verwijdert alle personen.
     */
    @Given("alle personen zijn verwijderd")
    public void verwijderAllePersonen() {
        getStoryOmgeving().getPersoonDataStubService().reset();
    }

    /**
     * Plaats direct (oftewel niet middels een verzoek, maar door direct persoonCache te muteren) een afnemerindicatie op een persoon.
     * @param table table met keys als header, die dienen als parameters voor toe te voegen afnemerindicatie. Mogelijke keys zijn : bsn,
     * leveringsautorisatieNaam, partijNaam, datumEindeVolgen, datumAanvangMaterielePeriode,tsReg, dienstId, dienstIdVerval, tsVerval
     */
    @Given("afnemerindicatie op de persoon : $table")
    public void zetAfnemerindicatieOpPersoon(final ExamplesTable table) {
        getStoryOmgeving().getBlobMutatieService().zetAfnemerindicatiesOpPersoon(converteerParameterTabel(table));
    }

    /**
     * Verwijder (door direct persoonCache te muteren) alle afnemerindicaties op een persoon.
     * @param table key-value pairs die dienen als parameters voor toe te voegen afnemerindicatie. Mogelijke keys zijn : bsn
     */
    @Given("alle afnemerindicaties op de persoon zijn verwijderd : $table")
    public void verwijderAfnemerindicatiesVanPersoon(final ExamplesTable table) {
        getStoryOmgeving().getBlobMutatieService().verwijderAlleAfnemerindicatiesVanPersoon(converteerParameterTabel(table));
    }

    /**
     * Wijzig tijdstip laatste wijziging GBA systematiek.
     * @param table key-value pairs die dienen als parameters Mogelijke keys zijn : bsn, tsLaatsteWijzigingGbaSystematiek
     */
    @Given("tijdstip laatste wijziging GBA systematiek op de persoon : $table")
    public void wijzigTijdstipLaatsteWijzigingGBASystematiekVanPersoon(final ExamplesTable table) {
        getStoryOmgeving().getBlobMutatieService().wijzigTijdstipLaatsteWijzigingGBASystematiekVanPersoon(converteerParameterTabel(table));
    }

    /**
     * Wijzig een element
     * @param bsn bsn van de beoogde persoon
     * @param attribuut de elementnaam van het attribuut van welke de waarde aangepast moet worden
     * @param waarde de nieuwe waarde
     */
    @Given("blob voor persoon $bsn en attribuut $attribuut is aangepast met waarde $waarde")
    public void blobVoorPersoonEnAttribuutAanpassenMetWaarde(final String bsn, final String attribuut, final String waarde) {
        getStoryOmgeving().getBlobMutatieService().pasBlobVoorPersoonEnAttribuutAanMetWaarde(bsn, attribuut, waarde);
    }

    /**
     * givenLeveringautorisatieUitBestand.
     * @param bestanden lijst met autorisatiebestanden
     */
    @Given("leveringsautorisatie uit $bestanden")
    public void laadAutorisatiesUitBestand(final List<String> bestanden) {
        getStoryOmgeving().laadAutorisaties(bestanden);
    }

    /**
     * Assert dat de gegeven afnemerindicatie is geplaatst.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringsautorisatienaam waarvoor een afnemerindicatie is geplaatst
     * @param partijnaam de partijnaam waarvoor een afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijnaam een afnemerindicatie geplaatst")
    public void assertAfnemerindicatieGeplaatst(final String bsn, final String leveringautorisatienaam, final String partijnaam) {
        final Integer leveringsautorisatieId = getStoryOmgeving().getAutorisatieService().getLeveringsautorisatie(leveringautorisatienaam).getId();
        final Partij partij = Partijen.getPartij(partijnaam);
        getStoryOmgeving().getAfnemerindicatieStubService().assertAfnemerindicatieGeplaatst(bsn, leveringsautorisatieId, partij.getId());
    }

    /**
     * Assert dat de gegeven afnemerindicatie niet is geplaatst.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringsautorisatienaam waarvoor een afnemerindicatie is geplaatst
     * @param partijnaam de partijnaam waarvoor een afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijnaam geen afnemerindicatie geplaatst")
    public void assertGeenAfnemerindicatieGeplaatst(final String bsn, final String leveringautorisatienaam, final String partijnaam) {
        final Integer leveringsautorisatieId = getStoryOmgeving().getAutorisatieService().getLeveringsautorisatie(leveringautorisatienaam).getId();
        final Partij partij = Partijen.getPartij(partijnaam);
        getStoryOmgeving().getAfnemerindicatieStubService().assertAfnemerindicatieNietGeplaatst(bsn, leveringsautorisatieId, partij.getId());
    }

    /**
     * Assert dat de gegeven afnemerindicatie is verwijderd.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam adonnementnaam waarvoor een afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijnaam de afnemerindicatie verwijderd")
    public void assertAfnemerindicatieVerwijderd(final String bsn, final String leveringautorisatienaam, final String partijnaam) {
        final Integer leveringsautorisatieId = getStoryOmgeving().getAutorisatieService().getLeveringsautorisatie(leveringautorisatienaam).getId();
        final Partij partij = Partijen.getPartij(partijnaam);
        getStoryOmgeving().getAfnemerindicatieStubService().assertAfnemerindicatieVerwijderd(bsn, leveringsautorisatieId, partij.getId());
    }

    /**
     * Assert dat de gegeven afnemerindicatie is verwijderd.
     * @param bsn het bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam adonnementnaam waarvoor een afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijnaam de afnemerindicatie niet verwijderd")
    public void assertAfnemerindicatieNietVerwijderd(final String bsn, final String leveringautorisatienaam, final String partijnaam) {
        final Integer leveringsautorisatieId = getStoryOmgeving().getAutorisatieService().getLeveringsautorisatie(leveringautorisatienaam).getId();
        final Partij partij = Partijen.getPartij(partijnaam);
        getStoryOmgeving().getAfnemerindicatieStubService().assertAfnemerindicatieNietVerwijderd(bsn, leveringsautorisatieId, partij.getId());
    }


    /**
     * @param code regelcode
     */
    @Then("is er een autorisatiefout gelogd met regelcode $code")
    public void assertAutorisatiefoutGelogdMetRegel(final String code) {
        Assert.isTrue(getStoryController().getStoryOmgeving().getLogControleService()
                .heeftLogEventOntvangenMetCode(code), "Geen autorisatieregel gelogd met code " + code);
    }

    /**
     * @param regex message regex
     */
    @Then("is er een $logLevel message gelogd die matcht met regex $regex")
    public void assertMessageGelogdMetRegex(final String logLevel, final String regex) {
        Assert.isTrue(getStoryController().getStoryOmgeving().getLogControleService()
                .heeftLogEventOntvangenMetMessage(logLevel, regex), String.format("Geen %s message gelogd die matcht met %s", logLevel, regex));
    }

    /**
     * Pas een attribuutwaarde in een gegeven van een stamtabel aan.
     * @param stamtabel de stamtabel
     * @param gegeven het gegeven
     * @param attribuut het attribuut
     * @param waarde de nieuwe waarde
     */
    @Given("stamtabel $stamtabel gegeven $gegeven met attribuut $attribuut is aangepast met $waarde")
    public void pasStamtabelGegevenAttribuutAan(final String stamtabel, final String gegeven, final String attribuut, final String waarde) {
        StoryController.getOmgeving().getStamtabelStub().pasStamtabelGegevenAttribuutAanMetWaarde(stamtabel, gegeven, attribuut, waarde);
    }

    /***
     * Before story stap.
     */
    @BeforeStory
    public void voorStory() {
        getStoryController().voorStory();
    }

    /**
     * After story stap waarbij de BRP omgeving gestopt wordt.
     */
    @AfterStory
    public void naStory() {
        getStoryController().naStory();
    }

    /**
     * After story stap waarbij de BRP omgeving gestopt wordt.
     */
    @AfterScenario
    public void naScenario() {
        getStoryController().naScenario();
    }

    private StoryController getStoryController() {
        return StoryController.get();
    }

    private StoryOmgeving getStoryOmgeving() {
        return getStoryController().getStoryOmgeving();
    }

    /**
     * After scenario stap als het resultaat van de test een {@link org.jbehave.core.annotations.AfterScenario.Outcome#FAILURE} is.
     */
    @AfterScenario(uponOutcome = AfterScenario.Outcome.FAILURE)
    public void naGefaaldScenario() {
        LOGGER.error("Scenario gefaald");
    }

    private List<Map<String, String>> converteerParameterTabel(final ExamplesTable table) {
        final List<Map<String, String>> paramMaps = new ArrayList<>();
        for (final Map<String, String> rowMap : table.getRows()) {
            paramMaps.add(rowMap);
        }
        return paramMaps;
    }


}
