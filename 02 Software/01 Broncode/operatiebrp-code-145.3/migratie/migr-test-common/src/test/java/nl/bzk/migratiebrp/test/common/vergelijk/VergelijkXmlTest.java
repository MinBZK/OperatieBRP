/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.MigratieXml;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class VergelijkXmlTest {

    @Test
    public void test() {
        final String xml1 = "<bla><x>1</x><y>2</y></bla>";
        final String xml2 = "<bla><y>2</y><x>1</x></bla>";

        assertTrue(VergelijkXml.vergelijkXml(xml1, xml2));
    }

    @Test
    public void test2() throws IOException {
        final String xml1 = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("/xml/xml1.txt"));
        final String xml2 = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("/xml/xml2.txt"));

        assertTrue(VergelijkXml.vergelijkXml(xml2, xml1));
    }

    @Test
    public void testActie() {
        // @formatter:off
        final String xml1 = "<bla>"
                + "<x>"
                + "<actieInhoud id=\"{decimal:1}\"><actieMeuk>asdsadasd</actieMeuk></actieInhoud>"
                + "<value>1</value>"
                + "</x>"
                + "<y>"
                + "<actieInhoud ref=\"{decimal:1}\"/>"
                + "<value>2</value>"
                + "</y>"
                + "</bla>";
        final String xml2 = "<bla>"
                + "<x>"
                + "<actieInhoud id=\"3\"><actieMeuk>asdsadasd</actieMeuk></actieInhoud>"
                + "<value>1</value>"
                + "</x>"
                + "<y>"
                + "<actieInhoud ref=\"3\"/>"
                + "<value>2</value>"
                + "</y>"
                + "</bla>";
        final String xml3 = "<bla>"
                + "<y>"
                + "<actieInhoud id=\"3\"><actieMeuk>asdsadasd</actieMeuk></actieInhoud>"
                + "<value>2</value>"
                + "</y>"
                + "<x>"
                + "<actieInhoud ref=\"3\"/>"
                + "<value>1</value>"
                + "</x>"
                + "</bla>";
        final String xml4 = "<bla>"
                + "<x>"
                + "<actieInhoud id=\"3\"><actieMeuk>sfsdfsdfds</actieMeuk></actieInhoud>"
                + "<value>1</value>"
                + "</x>"
                + "<y>"
                + "<actieInhoud ref=\"3\"/>"
                + "<value>2</value>"
                + "</y>"
                + "</bla>";
        final String xml5 = "<bla>"
                + "<y>"
                + "<actieInhoud id=\"3\"><actieMeuk>dsfgdfgdf</actieMeuk></actieInhoud>"
                + "<value>2</value>"
                + "</y>"
                + "<x>"
                + "<actieInhoud ref=\"3\"/>"
                + "<value>1</value>"
                + "</x>"
                + "</bla>";
        // @formatter:on

        assertTrue(VergelijkXml.vergelijkXml(xml1, xml2));
        assertTrue(VergelijkXml.vergelijkXmlMetActies(xml1, xml2));

        Assert.assertFalse(VergelijkXml.vergelijkXml(xml1, xml3));
        assertTrue(VergelijkXml.vergelijkXmlMetActies(xml1, xml3));

        Assert.assertFalse(VergelijkXml.vergelijkXmlMetActies(xml1, xml4));
        Assert.assertFalse(VergelijkXml.vergelijkXmlMetActies(xml1, xml5));
    }

    @Test
    public void testReal() throws IOException {
        final String expected = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("0002-in-sync-converteren.expected.txt"));
        final String actueel = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("0002-in-sync-converteren.txt"));

        assertTrue(VergelijkXml.vergelijkXmlMetActies(expected, actueel));
    }

    @Test
    public void testWhiteSpace() throws IOException {
        final String zonderWhiteSpace = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("/xml/xml3.txt"));
        final String metWhiteSpace = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("/xml/xml4.txt"));

        assertTrue(VergelijkXml.vergelijkXml(zonderWhiteSpace, zonderWhiteSpace));
        assertTrue(VergelijkXml.vergelijkXml(zonderWhiteSpace, metWhiteSpace));
        assertTrue(VergelijkXml.vergelijkXml(metWhiteSpace, zonderWhiteSpace));
        assertTrue(VergelijkXml.vergelijkXml(metWhiteSpace, metWhiteSpace));
    }

    @Test
    public void testPraktijk() throws IOException {
        final String expected = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("0021-in0020-bzm-response.expected.xml"));
        final String actual = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("0021-in0020-bzm-response.xml"));

        assertTrue(VergelijkXml.vergelijkXml(expected, actual));
    }

    @Test
    public void testPraktijkPersoonslijst() throws IOException, XmlException {
        final String persoonMemory = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("persoonMemory.xml"));
        final String persoonDatabase = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("persoonDatabase.xml"));
        try (StringReader memReader = new StringReader(persoonMemory); final StringReader dbReader = new StringReader(persoonDatabase)) {
            final BrpPersoonslijst memoryPL = MigratieXml.decode(BrpPersoonslijst.class, memReader);
            final BrpPersoonslijst dbPL = MigratieXml.decode(BrpPersoonslijst.class, dbReader);
            final StringBuilder verschillenLog = new StringBuilder();
            assertTrue(VergelijkXml.vergelijkXmlNegeerActieId(memoryPL, dbPL, true, verschillenLog));
            assertTrue(verschillenLog.length() == 0);
        }
    }

    @Test
    public void testPraktijkSortering() throws IOException {
        final String expected = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("/xml/xml6.txt"));
       final String actual = IOUtils.toString(VergelijkXmlTest.class.getResourceAsStream("/xml/xml5.txt"));

        //System.out.println("Expected:\n" + expected);
        final String expectedSorted =SorteerXml.sorteer(expected);
        //System.out.println("Expected (sorted):\n" + expectedSorted);

        //System.out.println("Actual:\n" + actual);
        final String actualSorted = SorteerXml.sorteer(actual);
        //System.out.println("Expected (sorted):\n" + actualSorted);

        assertTrue(VergelijkXml.vergelijkXml(expectedSorted, actualSorted));
    }
}
