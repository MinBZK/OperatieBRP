/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * page object voor de details van de selectie taken
 */
public class SelectieTaakDienstDetails {

    // Lijst van rijen van de selectie taak gegevens
    @FindBy(how = How.XPATH, using= ".//*[@id='ngb-panel-dienst']")
    private List<WebElement> dienstdetails;

    // Lijst van labels van de selectie taak labels
    @FindBy(how = How.XPATH, using= ".//*[@id='ngb-panel-dienst']//label")
    private List<WebElement> getoondetaaklabels;

    // Lijst van waardes van de selectie taak gegevens
    @FindBy(how = How.XPATH, using= ".//*[@id='ngb-panel-dienst']/*/*")
    private List<WebElement> getoondetaakgegevens;


    // Methode om de gevonden detailgegevens terug te geven als een list map
    public Map<String, String> getSelectieTaakDetails(WebDriver driver, String veld, String waarde ){
        waitForElement(driver, dienstdetails.get(0));
        waitForElement(driver, getoondetaaklabels.get(0));
        waitForElement(driver, getoondetaakgegevens.get(0));

        System.out.println(getoondetaaklabels.get(0).getText());
        System.out.println(getoondetaakgegevens.get(0).getText());


        Map<String, String> selectietaakdetails = new HashMap<String,String>();


        for(int i=0; i<dienstdetails.size(); i++){
            selectietaakdetails.put(getoondetaaklabels.get(i).getText(),getoondetaakgegevens.get(i).getText());

        }
        selectietaakdetails.containsKey(veld);
        selectietaakdetails.containsValue(waarde);
        System.out.println(selectietaakdetails);
        return selectietaakdetails;

    }
    /** Methode om de detail gegevens van de selectietaak aan te passen
    public List<Map<String, String>> SetSelectieTaakDetails(){

    }
     */

    public void waitForElement(WebDriver driver, WebElement element){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(element));
    }
}
