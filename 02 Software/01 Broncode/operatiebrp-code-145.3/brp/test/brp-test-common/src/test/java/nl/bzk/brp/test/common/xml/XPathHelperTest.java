/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.xml;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class XPathHelperTest {

    private String testBestand;
    private String testAntwoordBestand;
    private final XPathHelper xPathHelper = new XPathHelper();

    @Before
    public void setUp() throws Exception {
        testBestand = IOUtils.toString(XPathHelperTest.class.getResourceAsStream("/dummy.xml"));
        testAntwoordBestand= IOUtils.toString(XPathHelperTest.class.getResourceAsStream("/dummy_antwoord.xml"));
    }

    @Test
    public void assertNodeBestaat() {
        Assert.assertTrue(xPathHelper.isNodeAanwezig(testBestand, "//brp:actie"));
        Assert.assertTrue(xPathHelper.isNodeAanwezig(testBestand, "//brp:persoon"));
    }

    @Test
    public void assertNodeBestaatNiet() {
        xPathHelper.assertNodeBestaatNiet(testBestand, "//brp:dummy");
    }

    @Test
    public void assertLeafNodeBestaat() {
        xPathHelper.assertLeafNodeBestaat(testBestand, "//brp:actie/brp:soortNaam");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertLeafNodeBestaatNiet() {
        xPathHelper.assertLeafNodeBestaat(testBestand, "//brp:actie");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertWaardeNietGelijk() {
        xPathHelper.assertWaardeGelijk(testBestand, "number(//brp:synchronisatie/brp:code) = 123", "true");
    }

    @Test
    public void assertPlatteWaardeGelijk() {

    }

    @Test
    public void testMeldingBestaat() {
        xPathHelper.assertMeldingBestaat(testAntwoordBestand, "R1401", "Er bestaat geen geldige "
            + "afnemerindicatie voor deze persoon binnen de opgegeven leveringsautorisatie.");
    }

    @Test
    public void testAantalMeldingen() {
        xPathHelper.assertAantalMeldingenGelijk(testAntwoordBestand, 1);
    }

    @Test
    public void testAantalMeldingen2() {
        Assert.assertEquals(0, xPathHelper.getAantalElementen(testAntwoordBestand, "actie"));
        Assert.assertEquals(5, xPathHelper.getAantalElementen(testBestand, "actie"));
    }

    @Test
    public void assertVerantwoordingCorrect() {
        xPathHelper.assertVerantwoordingCorrect(testBestand);
    }

    @Test
    public void assertBerichtHeeftWaardes() {
        xPathHelper.assertBerichtHeeftWaardes(testBestand, "geboorte", "datum", Lists.newArrayList("1942-08-31", "1980-08-08", "1938-11-18", "1980-08-08",
            "1980-08-08", "1942-08-31", "1938-11-18", "1993-01-01"));
    }

    @Test
    public void assertBerichtHeeftAttribuutAanwezigheid() {
        xPathHelper.assertBerichtHeeftAttribuutAanwezigheid(testBestand, "geboorte", 1, "datum", true);
        xPathHelper.assertBerichtHeeftAttribuutAanwezigheid(testBestand, "geboorte", 1, "datum2", false);
    }

    @Test
    public void assertBerichtHeeftAttribuutWaarde() {
        xPathHelper.assertBerichtHeeftAttribuutWaarde(testBestand, "persoon", 1, "soortCode", "O");
        xPathHelper.assertBerichtHeeftAttribuutWaarde(testBestand, "persoon", 2, "soortCode", "I");
    }

    @Test
    public void assertBerichtVerwerkingssoortCorrect() {
        xPathHelper.assertBerichtVerwerkingssoortCorrect(testBestand, "geboorte", 1, Verwerkingssoort.IDENTIFICATIE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertBerichtVerwerkingssoortNietCorrect() {
        xPathHelper.assertBerichtVerwerkingssoortCorrect(testBestand, "geboorte", 1, Verwerkingssoort.WIJZIGING);
    }

}
