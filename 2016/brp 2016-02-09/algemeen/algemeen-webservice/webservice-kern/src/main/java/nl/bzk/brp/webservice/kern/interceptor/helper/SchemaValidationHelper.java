/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor.helper;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * Een helper class t.b.v. schema validaties van binnenkomende berichten.
 * LET OP: Schema is thread safe, en de opzet van deze klasse zorgt voor hergebruik.
 * <p/>
 * Zie {@link nl.bzk.brp.webservice.kern.interceptor.SchemaValidationInInterceptor}
 */
public final class SchemaValidationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaValidationHelper.class);

    private static final Schema SCHEMA;

    private static final String SCHEMA_BESTAND = "/xsd/master.xsd";

    /**
     * Eenmalige initialisatie van het schema.
     */
    static {
        SchemaFactory fact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        final SchemaValidationSchemaErrorHandler schemaValidationSchemaErrorHandler =
            new SchemaValidationSchemaErrorHandler();
        fact.setErrorHandler(schemaValidationSchemaErrorHandler);

        try {
            SCHEMA = fact.newSchema(SchemaValidationHelper.class.getResource(
                    SCHEMA_BESTAND).toURI().toURL());
        } catch (SAXException | URISyntaxException | IOException e) {
            LOGGER.error("Fout opgetreden tijdens initialisatie XSD validatie: " + e.getLocalizedMessage(), e);
            throw new IllegalStateException("Fout opgetreden tijdens initialisatie XSD validatie.", e);
        }
    }

    /** Constructor is private omdat dit een Helper class is. */
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
}
