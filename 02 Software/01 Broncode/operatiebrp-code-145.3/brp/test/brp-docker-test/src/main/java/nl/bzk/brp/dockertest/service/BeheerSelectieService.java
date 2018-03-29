/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Maps;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.pages.Selectie;
import nl.bzk.brp.dockertest.pages.SelectieTaakDetails;
import nl.bzk.brp.dockertest.service.webdriver.ChromeWebDriverProvider;
import nl.bzk.brp.dockertest.service.webdriver.PhantomJsWebDriverProvider;
import nl.bzk.brp.dockertest.service.webdriver.WebDriverProvider;
import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.util.Assert;

/**
 * Test service voor beheer selectie.
 */
public class BeheerSelectieService {

    private static final Map<String, WebDriverProvider> WEB_DRIVER_PROVIDER_MAP = Maps.newHashMap();

    static {
        WEB_DRIVER_PROVIDER_MAP.put("phantomjs", new PhantomJsWebDriverProvider());
        WEB_DRIVER_PROVIDER_MAP.put("chrome", new ChromeWebDriverProvider());
    }

    private final BrpOmgeving brpOmgeving;
    private WebDriver driver;
    private Selectie selectie;
    private SelectieTaakDetails selectietaakdetails;
    private boolean shouldCloseDriver;

    /**
     * @param brpOmgeving brpOmgeving
     */
    public BeheerSelectieService(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }

    private String geefBeheerSelectieFrontendUrl() {
        String urlTemplate = "http://%s:%s";
        final String host = brpOmgeving.getDockerHostname();
        final Integer poort = brpOmgeving.geefDocker(DockerNaam.BEHEER_SELECTIE_FRONTEND).getPoortMap().get(80);
        return String.format(urlTemplate, host, poort);
    }

    /**
     * Setup van Selenium WebDriver.
     * @param driverSoort het soort driver dat gebruikt moet worden
     * @param binaryPath het pad naar de webdriver implementatie
     * @param shouldCloseDriver vlag om aan te geven of de driver afgesloten moet worden
     */
    public void setupWebDriver(String driverSoort, String binaryPath, boolean shouldCloseDriver) {
        this.shouldCloseDriver = shouldCloseDriver;
        if (driverSoort == null) {
            String property = System.getProperty("phantomjs.binary");
            driver = WEB_DRIVER_PROVIDER_MAP.get("phantomjs").provide(property);
        } else {
            Assert.isTrue(WEB_DRIVER_PROVIDER_MAP.containsKey(driverSoort), "Driver wordt niet ondersteund.");
            String property = System.getProperty(binaryPath);
            driver = WEB_DRIVER_PROVIDER_MAP.get(driverSoort).provide(property);
        }
        driver.get(geefBeheerSelectieFrontendUrl());
        selectie = PageFactory.initElements(driver, Selectie.class);
        selectietaakdetails = PageFactory.initElements(driver, SelectieTaakDetails.class);
    }

    /**
     * Stop Selenium WebDriver.
     */
    public void quitWebDriver() {
        if (driver != null && shouldCloseDriver) {
            driver.quit();
        }
    }

    /**
     * BeginDatum aanpassen naar een opgegen datum
     */
    public void klikOpZoeken() {
        selectie.klikOpZoeken(driver);
    }

    /**
     * BeginDatum aanpassen naar een opgegeven datum
     * @param datum als beginDatum
     */
    public void beginDatumIsAangepastNaar(String datum) {
        selectie.beginDatumAanpassen(datum);
    }

    /**
     * EindDatum aanpassen naar een opgegeven datum
     * @param datum als EinddatumDatum
     */
    public void eindDatumIsAangepastNaar(String datum) {
        selectie.eindDatumAanpassen(datum);
    }


    /**
     * Controleer of de opgegeven datum overeenkomt met de datumPlanning
     * @param datum als Expected DatumPlanning
     */
    public void controleDatumPlanning(String datum) {
        assertEquals(datum, selectie.datumPlanninggetText());
    }


    /**
     * Klik om de details van de eerste selectietaak te openen
     */
    public void openEersteSelectieTaak() {
        selectie.uitklappenEersteRij(driver);
    }


    /**
     * In de methode wordt gewacht op de expected value.
     * @param aantalTaken aantal taken welke in de footer getoond worden na zoeken
     */
    public void geselecteerdTotaalgetText(String aantalTaken){
        String expectedValue = "0 geselecteerd / " + aantalTaken + " totaal";
        selectie.geselecteerdTotaalgetText(driver,  expectedValue);
    }

    /**
     * Vink checkboxen aan.
     * @param checkboxen de checkboxen
     */
    public void vinkCheckboxenAan(List<Map<String, String>> checkboxen) {
        for (Map<String, String> checkboxMap : checkboxen) {
            selectie.klikOpCheckbox(checkboxMap.get("id"));
        }
    }

    /**
     * Check of de opgegeven kolom aanwezig is in het taak overzicht
     */

    public void isKolomAanwezigInTaakOverzicht(String kolom){
        assertEquals("kolom niet gevonden in overzicht", kolom, selectie.kolomAanwezig(driver, kolom));
    }

    /**
     * Check op standaard zoekperiode
     * Begindatum vandaag minus een maand, Einddatum vandaag plus drie maanden
     */
    public void checkStandaardZoekPeriode(){
        LocalDate expBeginDatum = LocalDate.now().minusMonths(1);
        DateTimeFormatter  dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String ExpectedBeginDatum= dateFormat.format(expBeginDatum);
        assertEquals(ExpectedBeginDatum, selectie.BeginDatumgetText());

        LocalDate expEindDatum = LocalDate.now().plusMonths(3);
        String ExpectedEindDatum= dateFormat.format(expEindDatum);
        assertEquals(ExpectedEindDatum, selectie.EindDatumgetText());

    }
    /**
     * Check dat de Zoek button diabled is
     */
    public void assertZoekenDisabled(){
        assertFalse(selectie.zoekenEnabled());
    }
    /**
     *  Check of de correcte melding aanwezig is
     */
    public void assertMeldingdsTekstAanwezig(String meldingstekst){
        assertEquals(meldingstekst, selectie.GeefMeldingText());
    }

    /**
     * Wacht tot melding aanwezig is op scherm
     */
    public void wachtOpMelding (String meldingstekst){
        selectie.wachtTotMeldingAanwezig(driver, meldingstekst);
    }
    /**
     * Geef een filterwaarde op voor het overzicht met selectie taken
     */
    public void geefFilterWaardeOp (String kolom, String filterwaarde){
        selectie.geefFilterWaardeOp(driver, kolom, filterwaarde);
    }
    public void toonTaakDetails (){
        selectie.klikOpTaakGegevens();

    }

    public void toonDienstDetails (){
        selectie.klikOpDienstGegevens();
    }

    public void toonToegangDetails (){
        selectie.klikOpToegangGegevens();
    }

    public void controleerDetailGegevens(List<Map<String, String>> taakGegevens){
        for (Map<String, String> taakgegegevensMap : taakGegevens)
         selectietaakdetails.getSelectieTaakDetails(driver, taakgegegevensMap.get("veld"), taakgegegevensMap.get("waarde"));
    }

}
