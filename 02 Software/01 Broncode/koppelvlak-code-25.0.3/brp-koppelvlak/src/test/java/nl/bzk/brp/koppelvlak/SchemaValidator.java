/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.koppelvlak;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 */
enum SchemaValidator {

    BIJHOUDING_SCHEMA_BESTAND("/xsd/bijhouding.xsd"),
    VRIJBERICHT_SCHEMA_BESTAND("/xsd/BRP0200/brp0200_vrbVrijBericht_Berichten.xsd"),
    AFNMERINDICATIE_SCHEMA_BESTAND("/xsd/BRP0200/brp0200_lvgAfnemerindicatie_Berichten.xsd"),
    BEVRAGING_SCHEMA("/xsd/BRP0200/brp0200_lvgBevraging_Berichten.xsd"),
    SYNCHRONISATIE_SCHEMA("/xsd/BRP0200/brp0200_lvgSynchronisatie_Berichten.xsd"),
    ISC_SCHEMA("/xsd/BRP0200/brp0200_iscMigratievoorziening_Berichten.xsd"),
    SELECTIE_SCHEMA("/xsd/BRP0200/brp0200_lvgSelectie_Berichten.xsd"),
    STUF_SCHEMA_BESTAND("/xsd/BRP0200/brp0200_stvStufbgVertaling_Berichten.xsd");

    Schema schema;

    SchemaValidator(final String xsd) {
        schema = maakSchema(xsd);
    }

    void valideer(final StreamSource streamSource) {
        valideerTegenSchema(streamSource);
    }

    /**
     * @param xmlSource xml
     */
    private void valideerTegenSchema(final Source xmlSource) {
        final Validator validator = schema.newValidator();
        try {
            validator.validate(xmlSource);
        } catch (Exception e) {
            throw new RuntimeException("XML niet XSD valide: " + xmlSource.toString(), e);
        }
    }

    private static Schema maakSchema(final String schemaBestand) {
        final SchemaFactory fact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            return fact.newSchema(SchemaValidator.class.getResource(
                    schemaBestand).toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException(schemaBestand, e);
        }
    }
}
