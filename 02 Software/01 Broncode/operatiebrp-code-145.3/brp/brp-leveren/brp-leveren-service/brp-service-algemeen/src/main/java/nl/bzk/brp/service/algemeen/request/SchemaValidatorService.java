/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.xml.sax.SAXException;

/**
 * SchemaValidatorService.
 */
@Bedrijfsregel(Regel.R1268)
@Bedrijfsregel(Regel.R1997)
public interface SchemaValidatorService {

    /**
     * Maakt een {@link Schema} voor het gegeven bestand.
     *
     * @param classpathFile een schema bestand
     * @return een {@link Schema} instantie.
     * @throws SchemaNietGevondenException als het schema niet geladen kan worden
     */
    static Schema maakSchema(final String classpathFile) {
        final SchemaFactory fact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            final URL resource = SchemaValidatorService.class.getResource(classpathFile);
            if (resource == null) {
                throw new IOException("Bestand bestaat niet: " + classpathFile);
            }
            return fact.newSchema(resource.toURI().toURL());
        } catch (SAXException | URISyntaxException | IOException e) {
            throw new SchemaNietGevondenException(classpathFile, e);
        }
    }

    /**
     * Valideert de gegeven {@link Source} tegen het {@link Schema}.
     *
     * @param xmlSource xml een te valideren (XML) bestand
     * @param schema een {@link Schema} instantie
     * @throws SchemaValidatieException als het valideren faalt
     */
    default void valideer(final Source xmlSource, final Schema schema) {
        doValideerTegenSchema(xmlSource, schema);
    }

    /**
     * Default implementatie voor het valideren van het schema bestand.
     *
     * @param xmlSource xml een te valideren (XML) bestand
     * @param schema een {@link Schema} instantie
     */
    static void doValideerTegenSchema(final Source xmlSource, final Schema schema) {
        final Validator validator = schema.newValidator();
        try {
            validator.validate(xmlSource);
        } catch (SAXException | IOException e) {
            throw new SchemaValidatieException("XML niet XSD valide: " + xmlSource.toString(), e);
        }
    }

    /**
     * Exceptie wordt gegooid als het schemabestand niet gevonden kan worden.
     */
    class SchemaNietGevondenException extends BrpServiceRuntimeException {
        private static final long serialVersionUID = -2076964825538314548L;

        /**
         * @param schemaBestand het schemabestand dat niet gevonden kan worden
         * @param cause cause
         */
        SchemaNietGevondenException(final String schemaBestand, final Exception cause) {
            super(String.format("Schema %s kan niet gevonden worden", schemaBestand), cause);
        }
    }

    /**
     * Exceptie als het valideren van faalt.
     */
    class SchemaValidatieException extends BrpServiceRuntimeException {
        private static final long serialVersionUID = -3952311959170392767L;

        /**
         * @param message message
         * @param cause cause
         */
        SchemaValidatieException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
