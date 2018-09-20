/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor.helper;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * Een helper class t.b.v. schema validaties van binnenkomende berichten. Deze klasse houdt per SOAP actie bij welk
 * schema daarbij hoort, omdat elke actie een eigen xsd kent.
 * LET OP!! Schema is thread safe, en de opzet van deze klasse zorgt voor hergebruik.
 * LET OP!! De reden dat er een mapping gebruikt wordt tussen SOAP Action en schema's is:
 * https://issues.apache.org/jira/browse/XERCESJ-1130.
 *
 * Zie {@link nl.bzk.brp.web.interceptor.SchemaValidationInInterceptor}
 */
public final class SchemaValidationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaValidationHelper.class);

    /**
     * De mapping van SOAP actie naar schema.
     * LET OP!! Schema is thread safe, en de opzet van deze klasse zorgt voor hergebruik.
     */
    private static final Map<String/**SOAPAction**/, Schema/**Schema**/> SOAPACTION_TO_SCHEMAMAPPING =
            new HashMap<String, Schema>();

    /**
     * Eenmalige initialisatie van de mapping. Moet wellicht aangepast worden door Spring beans te gebruiken.
     */
    static {
        SchemaFactory fact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        synchronized (SOAPACTION_TO_SCHEMAMAPPING) {
            try {

                //BIJHOUDING
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "inschrijvingGeboorte",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Afstamming_Berichten.xsd").toURI()))));
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "verhuizing",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Migratie_Berichten.xsd").toURI()))));
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "correctieAdresNL",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Migratie_Berichten.xsd").toURI()))));
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "registreerHuwelijkEnPartnerschap",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_HuwelijkPartnerschap_Berichten.xsd").toURI()))));
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "registreerOverlijden",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Overlijden_Berichten.xsd").toURI()))));
                //BEVRAGING
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "vraagDetailsPersoon",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Bevraging_Berichten.xsd").toURI()))));
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "vraagPersonenOpAdresInclusiefBetrokkenheden",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Bevraging_Berichten.xsd").toURI()))));
                SOAPACTION_TO_SCHEMAMAPPING.put(
                        "vraagKandidaatVader",
                        fact.newSchema(new StreamSource(new File(SchemaValidationHelper.class.getResource(
                                "/xsd/BRP_Bevraging_Berichten.xsd").toURI()))));
            } catch (SAXException e) {
                LOGGER.error("Fout opgetreden tijdens initialisatie XSD validatie.", e);
            } catch (URISyntaxException e) {
                LOGGER.error("Fout opgetreden tijdens initialisatie XSD validatie.", e);
            }
        }
    }

    /**
     * Constructor is private omdat dit een Helper class is.
     */
    private SchemaValidationHelper() {
    }

    /**
     * Retourneert het schema dat gebruikt moet worden (voor xsd validatie) voor de betreffende soapAction.
     *
     * @param soapAction De SOAP actie die wordt aangeroepen.
     * @return Het te gebruiken schema voor xsd validatie.
     */
    public static Schema getSchema(final String soapAction) {
        return SOAPACTION_TO_SCHEMAMAPPING.get(soapAction);
    }
}
