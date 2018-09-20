/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor;

import java.io.IOException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import nl.bzk.brp.webservice.kern.interceptor.helper.SchemaValidationHelper;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Deze Interceptor valideert ieder binnenkomend bericht tegen het XSD schema.
 */
public class SchemaValidationInInterceptor extends AbstractSoapInterceptor {

    private static final String XSD_VALIDATIE_SYSTEM_PROPERTY = "nl.bzk.brp.webservice.xsd.validatie";
    private static final String XSD_VALIDATIE_WAARDE_UIT      = "uit";

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaValidationInInterceptor.class);

    /**
     * Constructor die de Interceptor in juiste phase plaatst direct achter de SAAJInInterceptor.
     */
    public SchemaValidationInInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    /**
     * De daadwerkelijke validatie van het bericht tegen XSD.
     *
     * @param message Het binnenkomende bericht.
     */
    @Override
    public void handleMessage(final SoapMessage message) {
        // TANGO-191: Sla XSD validatie over bij specifieke system property waarde,
        // voor het testen van regels die anders niet af kunnen gaan door de XSD validatie.
        final String xsdValidatieSystemProperty = System.getProperty(XSD_VALIDATIE_SYSTEM_PROPERTY);
        if (!XSD_VALIDATIE_WAARDE_UIT.equals(xsdValidatieSystemProperty)) {
            try {
                final Schema schema = SchemaValidationHelper.getSchema();
                if (schema == null) {
                    throw new SAXException("kan geen schema validatie vinden voor " + message.get("SOAPAction"));
                }
                final Validator validator = schema.newValidator();
                SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
                if (soapMessage == null) {
                    SAAJInInterceptor saajInInterceptor = new SAAJInInterceptor();
                    saajInInterceptor.handleMessage(message);
                    soapMessage = message.getContent(SOAPMessage.class);
                }
                final SOAPBody soapBody = soapMessage.getSOAPBody();
                final SOAPBody bodyCopy = (SOAPBody) soapBody.cloneNode(true);
                validator.validate(new DOMSource(bodyCopy.extractContentAsDocument()));
            } catch (final SAXException e) {
                LOGGER.error("Fout tijdens XSD validatie binnenkomend bericht.", e);
                throw new Fault(e);
            } catch (IOException | SOAPException e) {
                throw new Fault(e);
            }
        }
    }
}
