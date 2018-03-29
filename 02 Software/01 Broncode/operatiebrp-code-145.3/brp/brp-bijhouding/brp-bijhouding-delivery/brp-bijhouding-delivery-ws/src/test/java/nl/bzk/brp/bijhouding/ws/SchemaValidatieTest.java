/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Test of het Java validation framework ons schema's kan parsen (grammar) en xsd validatie goed gaat.
 */
public class SchemaValidatieTest {

    private static final String XSD_BESTAND = "/xsd/master.xsd";

    @Test
    public void testLaadSchemaInEnValideer() throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL schemaBestand = SchemaValidatieTest.class.getResource(XSD_BESTAND);
        Schema schema = schemaFactory.newSchema(schemaBestand);
        final Validator validator = schema.newValidator();

        final InputStream xmlBestandStream = SchemaValidatieTest.class.getResourceAsStream("afs_RegistreerGeboorte.xml");
        validator.validate(new StreamSource(xmlBestandStream));
    }

    @Test(expected = SAXParseException.class)
    public void testValideerFoutBestand() throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL schemaBestand = SchemaValidatieTest.class.getResource(XSD_BESTAND);
        Schema schema = schemaFactory.newSchema(schemaBestand);
        final Validator validator = schema.newValidator();

        // Stuurgegevens ontbreken.
        final InputStream xmlBestandStream = SchemaValidatieTest.class.getResourceAsStream("afs_registreerGeboorte_schema_invalid.xml");
        validator.validate(new StreamSource(xmlBestandStream));
    }
}
