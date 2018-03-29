/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.validation;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * Een helper class t.b.v. schema validaties van binnenkomende berichten. LET OP: Schema is thread safe, en de opzet van
 * deze klasse zorgt voor hergebruik.
 */
public final class SchemaValidationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Schema SCHEMA;
    private static final Schema ISC_SCHEMA;

    private static final String SCHEMA_BESTAND = "/xsd/bijhouding.xsd";
    private static final String ISC_SCHEMA_BESTAND = "/xsd/bijhoudinggba.xsd";

    /**
     * Eenmalige initialisatie van het schema.
     */
    static {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        final SchemaValidationSchemaErrorHandler schemaValidationSchemaErrorHandler = new SchemaValidationSchemaErrorHandler();
        factory.setErrorHandler(schemaValidationSchemaErrorHandler);

        try {
            SCHEMA = factory.newSchema(SchemaValidationHelper.class.getResource(SCHEMA_BESTAND).toURI().toURL());
            ISC_SCHEMA = factory.newSchema(SchemaValidationHelper.class.getResource(ISC_SCHEMA_BESTAND).toURI().toURL());
        } catch (
                SAXException
                        | URISyntaxException
                        | IOException e) {
            LOGGER.error("Fout opgetreden tijdens initialisatie XSD validatie: " + e.getLocalizedMessage(), e);
            throw new IllegalStateException("Fout opgetreden tijdens initialisatie XSD validatie.", e);
        }
    }

    /**
     * Constructor is private omdat dit een Helper class is.
     */
    private SchemaValidationHelper() {
    }

    /**
     * Retourneert het schema dat gebruikt moet worden (voor xsd validatie).
     *
     * @return Het te gebruiken schema voor xsd validatie.
     */
    public static Schema getSchema() {
        return SCHEMA;
    }

    /**
     * Retourneert het schema dat gebruikt moet worden voor ISC bericht validatie.
     *
     * @return Het te gebruiken schema voor ISC xsd validatie.
     */
    public static Schema getIscSchema() {
        return ISC_SCHEMA;
    }
}
