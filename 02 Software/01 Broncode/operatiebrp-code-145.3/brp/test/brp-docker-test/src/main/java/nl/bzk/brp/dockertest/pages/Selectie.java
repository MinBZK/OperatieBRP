/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.pages;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * PageObject voor pagina Selectie
 */
public class Selectie {

    @FindBy(how = How.ID, using = "zoeken")
    private WebElement zoeken;

    @FindBy(how = How.ID, using = "beginDatum")
    private WebElement beginDatum;

    @FindBy(how = How.ID, using = "eindDatum")
    private WebElement eindDatum;

    @FindBy(how = How.XPATH, using = ".//*[@title='Uit-/Inklappen']")
    private WebElement uitKlappenEersteRij;

    @FindBy(how = How.ID, using = "datumPlanning")
    private WebElement datumPlanning;

    @FindBy(how = How.ID, using = "datumPeilmomentMaterieelRes")
    private WebElement datumPeilmomentMaterieelRes;

    @FindBy(how = How.ID, using = "datumPeilmomentFormeelRes")
    private WebElement datumPeilmomentFormeelRes;

    @FindBy(how = How.ID, using = "taak-detail-selectietaak-status-toelichting")
    private WebElement taakdetailselectietaakstatustoelichting;

    @FindBy(how = How.CSS, using = "//button[contains(text(),'Submit')]")
    private WebElement opslaan;

    @FindBy(how = How.ID, using = "dagelijks-terugkerend")
    private WebElement dagelijksTerugkerendCheckbox;

    @FindBy(how = How.ID, using = "reeds-gepland")
    private WebElement reedsGeplandCheckbox;

    @FindBy(how = How.ID, using = "opnieuw-te-plannen")
    private WebElement opnieuwTePlannenCheckbox;

    @FindBy(how = How.CLASS_NAME, using = "empty-row")
    private WebElement melding;

    @FindBy(how = How.LINK_TEXT, using = "Dienst")
    private WebElement dienstgegevens;

    @FindBy(how = How.LINK_TEXT, using = "Toegang")
    private WebElement toeganggegevens;

    @FindBy(how = How.LINK_TEXT, using = "Taak")
    private WebElement taakgegevens;

    @FindBy(how = How.ID, using= "ngb-panel-taak")
    private List<WebElement> getoondetaakgegevens;


    /**
     *  Text x geselecteerd / x totaal in footer onder de taken tabel
     */
    @FindBy(how = How.XPATH, using = "//datatable-footer/div/div")
    private WebElement geselecteerdTotaal;

    public void klikOpZoeken(WebDriver driver){
        waitForElement(driver, zoeken);
        zoeken.click();

    }

    public boolean zoekenEnabled(){
        return zoeken.isEnabled();
    }

    public void beginDatumAanpassen(String datum){
        beginDatum.clear();
        beginDatum.sendKeys(datum + Keys.TAB);
    }

    public void eindDatumAanpassen(String datum){
        eindDatum.clear();
        eindDatum.sendKeys(datum + Keys.TAB);
    }

    public void uitklappenEersteRij(WebDriver driver){
        waitForElement(driver, uitKlappenEersteRij);
        uitKlappenEersteRij.click();
    }

    public void geselecteerdTotaalgetText(WebDriver driver, String elementValue){
        waitForElementValue(driver, geselecteerdTotaal, elementValue);
    }

    public void klikOpOpslaan(){
        opslaan.click();
    }

    public String datumPlanninggetText(){
        return datumPlanning.getAttribute("value");
    }

    public void waitForElement(WebDriver driver, WebElement element){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(element));
    }

    public void wachtTotMeldingAanwezig (WebDriver driver, String melding){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.className("empty-row"), melding));
    }

    public void waitForElementValue(WebDriver driver, WebElement element,  String elementValue){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<Boolean> condition = d -> elementValue.equals(element.getText());
        wait.until(condition);
    }

    public void klikOpCheckbox(String id) {
        for (Field field : this.getClass().getDeclaredFields()) {
            final FindBy findBy = field.getAnnotation(FindBy.class);
            if (findBy != null && findBy.how() == How.ID && findBy.using().equals(id)) {
                try {
                    field.setAccessible(true);
                    ((WebElement)field.get(this)).click();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String BeginDatumgetText(){return beginDatum.getAttribute("value");}

    public String EindDatumgetText(){return eindDatum.getAttribute("value");}

    public String GeefMeldingText(){
        return melding.getText();
    }

    public void geefFilterWaardeOp(WebDriver driver, String kolom, String filterwaarde){
        driver.findElement(By.id(kolom + "-header-filter")).sendKeys(filterwaarde + Keys.TAB);
    }
    public String kolomAanwezig (WebDriver driver, String kolom){
        String xpathkolomnaam = "//span[text()='" + kolom + "']";
        return driver.findElement(By.xpath(xpathkolomnaam)).getText();
    }

    public void klikOpDienstGegevens(){
        dienstgegevens.click();
    }

    public void klikOpToegangGegevens(){
        toeganggegevens.click();
    }

    public void klikOpTaakGegevens(){
        taakgegevens.click();
    }
    public List<WebElement> getGetoondeTaakGegevens (WebDriver driver){
        getoondetaakgegevens = driver.findElements(By.xpath(".//label"));
        //ArrayList<Object> getoondeTaakGegevens = Lists.newArrayList();
        for(WebElement e : getoondetaakgegevens){
            System.out.println(e.getText());

        }

        /**
        java.util.Iterator<WebElement> i = getoondetaakgegevens.iterator();
        while (i.hasNext()){
            WebElement getoondetaakgegeven = i.next();
            if(getoondetaakgegeven.getText() !=null) {
                System.out.println(getoondetaakgegeven.getText());
            }
        }
         */
        return this.getoondetaakgegevens;


    }

}
