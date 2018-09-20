/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Als het gegevensmodel wijzigt, wordt de JavaScript van de Viewer, evenals deze test, niet automatisch
 * mee-gerefactord. Als deze test omvalt is dit dus een indicatie dat het gegevensmodel gewijzigd is, en dat niet alleen
 * deze test, maar ook de JavaScript van de Viewer nog zal moeten worden aangepast.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-viewer-beans.xml" })
public class GegevensModelTest {
    @Inject
    private ViewerController viewerController;

    private final String INPUT_FILE_NAME = "Omzetting.txt";
    private final String EXPECTED_FILE_NAME = "Omzetting.JSON";

    @Test
    public void testGegevensModel() throws IOException {
        final ClassLoader classLoader = this.getClass().getClassLoader();

        String result =
                viewerController.uploadRequest(INPUT_FILE_NAME,
                        IOUtils.toByteArray(classLoader.getResourceAsStream(INPUT_FILE_NAME))).getBody();

        String expected = IOUtils.toString(classLoader.getResourceAsStream(EXPECTED_FILE_NAME), "UTF-8");

        // De volgorde van categorieen in een Set wil nog weleens wisselen in Jackson.
        // Vandaar deze hack. Dit verdwijnt waarschijnlijk zodra categorieen niet meer in een
        // Set worden opgeslagen. Wiljan is hiermee bezig.
        expected = expected.replace("CATEGORIE_01", "CATEGORIE_XX");
        expected = expected.replace("CATEGORIE_07", "CATEGORIE_XX");
        result = result.replace("CATEGORIE_01", "CATEGORIE_XX");
        result = result.replace("CATEGORIE_07", "CATEGORIE_XX");

        expected = expected.replaceAll("[\r]", "");
        result = result.replaceAll("[\r]", "");

        // NOOT: Helaas wordt de BCM niet gecheckt (de BcmService wordt gemockt).
        // Dit omdat we momenteel niet kunnen verwachten dat Jenkins en de developer beide
        // dezelfde (valide) BCM configuratie hebben (enabled / disabled).

        /*
         * LET OP: als deze vergelijking breekt is waarschijnlijk het model veranderd en zal de JavaScript van de Viewer
         * dus ook breken. Refactor de JS dus eerst aan de hand van de verschillen voordat je deze test weer groen
         * maakt.
         */
        assertEquals(expected, result);
        assertTrue(expected.equals(result));
    }
}
