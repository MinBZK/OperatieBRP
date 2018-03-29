/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.xml;

import com.google.common.collect.Sets;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class XmlUtilsTest {


    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor onderzoek & partner betrokkenheden.
     */
    @Test
    public void testBericht_OnderzoekPartnerVerschillendeSortering() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekEnPartnerActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekEnPartnerExpected.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("onderzoek", "partner"));
    }


    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor onderzoek & partner betrokkenheden.
     * Verschil in waarden voor onderzoek
     */
    @Test(expected = AssertionError.class)
    public void testBericht_OnderzoekPartnerVerschillendeSortering_VerschillendeWaardenOnderzoek() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekEnPartnerActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekEnPartnerExpected2.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("onderzoek", "partner"));
    }


    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor onderzoek & partner betrokkenheden.
     * Verschil in waarden voor partner
     */
    @Test(expected = AssertionError.class)
    public void testBericht_OnderzoekPartnerVerschillendeSortering_VerschillendeWaardenPartner() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekEnPartnerActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekEnPartnerExpected3.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("onderzoek", "partner"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor gegeven in onderzoek.
     */
    @Test
    public void testBericht_GegevenInOnderzoekVerschillendeSortering() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/GegevensInOnderzoekActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/GegevensInOnderzoekExpected.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("gegevenInOnderzoek"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor gegeven in onderzoek.
     * Verschil in waarden voor gegeven in onderzoek
     */
    @Test(expected = AssertionError.class)
    public void testBericht_GegevenInOnderzoekVerschillendeSortering_VerschillendeWaardenGegevenInOnderzoek() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/GegevensInOnderzoekActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/GegevensInOnderzoekExpected2.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("gegevenInOnderzoek"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor bronnen en acties.
     */
    @Test
    public void testBericht_BronnenEnActiesVerschillendeSortering() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/VerantwoordingActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/VerantwoordingExpected.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("bron", "actie"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor bronnen en acties.
     * Verschil in waarden actie
     */
    @Test(expected = AssertionError.class)
    public void testBericht_BronnenEnActiesVerschillendeSortering_WaardeverschilActie() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/VerantwoordingActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/VerantwoordingExpected2.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("bron", "actie"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor bronnen en acties.
     * Verschil in waarden bron
     */
    @Test(expected = AssertionError.class)
    public void testBericht_BronnenEnActiesVerschillendeSortering_WaardeverschilBron() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/VerantwoordingActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/VerantwoordingExpected3.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("bron", "actie"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor ouder.
     */
    @Test
    public void testBericht_OuderVerschillendeSortering() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OuderActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OuderExpected.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("ouder"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor ouder.
     * Verscil in waarden ouder
     */
    @Test(expected = AssertionError.class)
    public void testBericht_OuderVerschillendeSortering_WaardeVerschil() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OuderActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OuderExpected2.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("ouder"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor onderzoek.
     */
    @Test
    public void testBericht_OnderzoekVerschillendeSortering() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekGenestGegevenInOnderzoekActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekGenestGegevenInOnderzoekExpected.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("onderzoek"));
    }

    /**
     * Vergelijk twee bericht samples met verschillende sorteervolgorde voor onderzoek.
     * Verschil in waarden voor onderzoek
     */
    @Test(expected = AssertionError.class)
    public void testBericht_OnderzoekVerschillendeSortering_WaardeVerschil() throws IOException {
        String actual = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekGenestGegevenInOnderzoekActual.xml"));
        String expected = IOUtils.toString(XmlUtilsTest.class.getResourceAsStream("/OnderzoekGenestGegevenInOnderzoekExpected2.xml"));

        XmlUtils.assertGelijkNegeerVolgorde(expected, actual, Sets.newHashSet("onderzoek"));
    }

}
