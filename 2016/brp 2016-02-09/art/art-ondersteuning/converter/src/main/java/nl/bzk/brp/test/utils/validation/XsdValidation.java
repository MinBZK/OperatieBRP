/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.validation;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XsdValidation {

    private static Schema SCHEMA;

    static {
        try {
            SCHEMA = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(new File(
                    "src/test/resources/BMR26Conversie/XSD/BRP0100/BRP0100_Bevraging_Berichten.xsd")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String valideer(final Document document) throws Exception {
        try {
            final Validator validator = SCHEMA.newValidator();
            validator.validate(new DOMSource(document.getDocumentElement().getChildNodes().item(0)));
            return "Correct";
        } catch (SAXException e) {
            return "FOUT: " + e.getMessage();
        }
    }
}

