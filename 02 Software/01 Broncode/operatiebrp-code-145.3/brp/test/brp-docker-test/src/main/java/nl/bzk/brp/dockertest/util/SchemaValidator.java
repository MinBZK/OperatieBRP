/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.util;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.xml.sax.SAXException;

/**
 * SchemaValidatorServiceImpl.
 */
public final class SchemaValidator {

    private static final String LEVERING_SYNCHRONISATIE_SCHEMA_BESTAND = "/xsd/BRP0200/brp0200_lvgSynchronisatie_Berichten.xsd";
    private static final String LEVERING_BEVRAGING_SCHEMA_BESTAND = "/xsd/BRP0200/brp0200_lvgBevraging_Berichten.xsd";
    private static final String AFNMERINDICATIE_SCHEMA_BESTAND = "/xsd/BRP0200/brp0200_lvgAfnemerindicatie_Berichten.xsd";
    private static final String VRIJBERICHT_SCHEMA_BESTAND = "/xsd/BRP0200/brp0200_vrbVrijBericht_Berichten.xsd";
    private static final String MASTER_SCHEMA_BESTAND = "/xsd/master.xsd";
    private static final Map<String, Schema> SCHEMA_MAP = Maps.newHashMap();

    static {
        SCHEMA_MAP.put(LEVERING_SYNCHRONISATIE_SCHEMA_BESTAND, maakSchema(LEVERING_SYNCHRONISATIE_SCHEMA_BESTAND));
        SCHEMA_MAP.put(LEVERING_BEVRAGING_SCHEMA_BESTAND, maakSchema(LEVERING_BEVRAGING_SCHEMA_BESTAND));
        SCHEMA_MAP.put(VRIJBERICHT_SCHEMA_BESTAND, maakSchema(VRIJBERICHT_SCHEMA_BESTAND));
        SCHEMA_MAP.put(AFNMERINDICATIE_SCHEMA_BESTAND, maakSchema(AFNMERINDICATIE_SCHEMA_BESTAND));
        SCHEMA_MAP.put(MASTER_SCHEMA_BESTAND, maakSchema(MASTER_SCHEMA_BESTAND));
    }

    /**
     * @param xmlSource xml
     */
    public static void valideerTegenMasterSchema(final Source xmlSource) {
        valideerTegenSchema(xmlSource, MASTER_SCHEMA_BESTAND);
    }

    /**
     * @param xmlSource xml
     */
    public static void valideerTegenAfnemerindicatieSchema(final Source xmlSource) {
        valideerTegenSchema(xmlSource, AFNMERINDICATIE_SCHEMA_BESTAND);
    }

    /**
     * @param xmlSource xml
     */
    public static void valideerTegenSynchronisatieSchema(final Source xmlSource) {
        valideerTegenSchema(xmlSource, LEVERING_SYNCHRONISATIE_SCHEMA_BESTAND);
    }

    /**
     * @param xmlSource xml
     */
    public static void valideerTegenBevragingSchema(final Source xmlSource) {
        valideerTegenSchema(xmlSource, LEVERING_BEVRAGING_SCHEMA_BESTAND);
    }

    /**
     * @param xmlSource xml
     */
    public static void valideerTegenVrijBerichtSchema(final Source xmlSource) {
        valideerTegenSchema(xmlSource, VRIJBERICHT_SCHEMA_BESTAND);
    }


    /**
     * @param xmlSource xml
     * @param schemaBestand schemaBestand
     */
    private static void valideerTegenSchema(final Source xmlSource, final String schemaBestand) {
        final Schema schema = SCHEMA_MAP.get(schemaBestand);
        if (schema == null) {
            throw new TestclientExceptie("kan geen schema validatie vinden voor " + schemaBestand);
        }
        final Validator validator = schema.newValidator();
        try {
            validator.validate(xmlSource);
        } catch (SAXException | IOException e) {
            throw new TestclientExceptie("XML niet XSD valide: " + xmlSource.toString(), e);
        }
    }

    private static Schema maakSchema(final String schemaBestand) {
        final SchemaFactory fact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            return fact.newSchema(SchemaValidator.class.getResource(
                    schemaBestand).toURI().toURL());
        } catch (SAXException | URISyntaxException | IOException e) {
            throw new TestclientExceptie(schemaBestand, e);
        }
    }
}
