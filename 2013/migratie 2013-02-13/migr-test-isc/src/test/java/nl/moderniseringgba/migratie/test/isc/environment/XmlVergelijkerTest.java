/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc.environment;

import java.io.IOException;

import nl.moderniseringgba.migratie.test.isc.TestException;
import nl.moderniseringgba.migratie.test.vergelijk.VergelijkTest;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class XmlVergelijkerTest {

    @Test
    public void test() throws TestException {
        final String xml1 = "<bla><x>1</x><y>2</y></bla>";
        final String xml2 = "<bla><y>2</y><x>1</x></bla>";

        Assert.assertTrue(XmlVergelijker.vergelijkXml(xml1, xml2));
    }

    @Test
    public void test2() throws IOException, TestException {
        final String xml1 = IOUtils.toString(XmlVergelijkerTest.class.getResourceAsStream("/xml/xml1.txt"));
        final String xml2 = IOUtils.toString(XmlVergelijkerTest.class.getResourceAsStream("/xml/xml2.txt"));

        Assert.assertTrue(XmlVergelijker.vergelijkXml(xml2, xml1));
    }

    @Test
    public void testActie() throws TestException {
        //@formatter:off
        final String xml1 =
                "<bla>" 
                    + "<x>" 
                        + "<actieInhoud id=\"{decimal:1}\"><actieMeuk>asdsadasd</actieMeuk></actieInhoud>" 
                        + "<value>1</value>" 
                    + "</x>" 
                    + "<y>" 
                        + "<actieInhoud ref=\"{decimal:1}\"/>" 
                        + "<value>2</value>" 
                    + "</y>" 
                + "</bla>";
        final String xml2 =
                "<bla>" 
                    + "<x>" 
                        + "<actieInhoud id=\"3\"><actieMeuk>asdsadasd</actieMeuk></actieInhoud>" 
                        + "<value>1</value>" 
                    + "</x>" 
                    + "<y>" 
                        + "<actieInhoud ref=\"3\"/>" 
                        + "<value>2</value>" 
                    + "</y>" 
                + "</bla>";        
        final String xml3 = 
                 "<bla>" 
                        + "<y>" 
                        + "<actieInhoud id=\"3\"><actieMeuk>asdsadasd</actieMeuk></actieInhoud>" 
                        + "<value>2</value>" 
                    + "</y>" 
                        + "<x>" 
                        + "<actieInhoud ref=\"3\"/>" 
                            + "<value>1</value>" 
                        + "</x>" 
                  + "</bla>";   
        final String xml4 =
                "<bla>" 
                    + "<x>" 
                        + "<actieInhoud id=\"3\"><actieMeuk>sfsdfsdfds</actieMeuk></actieInhoud>" 
                        + "<value>1</value>" 
                    + "</x>" 
                    + "<y>" 
                        + "<actieInhoud ref=\"3\"/>" 
                        + "<value>2</value>" 
                    + "</y>" 
                 + "</bla>";           
        final String xml5 = 
                  "<bla>" 
                     + "<y>" 
                        + "<actieInhoud id=\"3\"><actieMeuk>dsfgdfgdf</actieMeuk></actieInhoud>" 
                        + "<value>2</value>" 
                     + "</y>" 
                     + "<x>" 
                        + "<actieInhoud ref=\"3\"/>" 
                            + "<value>1</value>" 
                     + "</x>" 
                   + "</bla>";           
        //@formatter:on

        Assert.assertTrue(XmlVergelijker.vergelijkXml(xml1, xml2));
        Assert.assertTrue(XmlVergelijker.vergelijkXmlMetActies(xml1, xml2));

        Assert.assertFalse(XmlVergelijker.vergelijkXml(xml1, xml3));
        Assert.assertTrue(XmlVergelijker.vergelijkXmlMetActies(xml1, xml3));

        Assert.assertFalse(XmlVergelijker.vergelijkXmlMetActies(xml1, xml4));
        Assert.assertFalse(XmlVergelijker.vergelijkXmlMetActies(xml1, xml5));
    }

    @Test
    public void testReal() throws IOException, TestException {
        final String expected =
                IOUtils.toString(XmlVergelijkerTest.class
                        .getResourceAsStream("0002-in-sync-converteren.expected.txt"));
        final String actueel =
                IOUtils.toString(XmlVergelijkerTest.class.getResourceAsStream("0002-in-sync-converteren.txt"));

        Assert.assertTrue(XmlVergelijker.vergelijkXmlMetActies(expected, actueel));
    }

    @Test
    public void testWhiteSpace() throws IOException, TestException {
        final String zonderWhiteSpace = IOUtils.toString(VergelijkTest.class.getResourceAsStream("/xml/xml3.txt"));
        final String metWhiteSpace = IOUtils.toString(VergelijkTest.class.getResourceAsStream("/xml/xml4.txt"));

        Assert.assertTrue(XmlVergelijker.vergelijkXml(zonderWhiteSpace, zonderWhiteSpace));
        Assert.assertTrue(XmlVergelijker.vergelijkXml(zonderWhiteSpace, metWhiteSpace));
        Assert.assertTrue(XmlVergelijker.vergelijkXml(metWhiteSpace, zonderWhiteSpace));
        Assert.assertTrue(XmlVergelijker.vergelijkXml(metWhiteSpace, metWhiteSpace));
    }

}
