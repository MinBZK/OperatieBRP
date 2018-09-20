/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import nl.bzk.brp.web.interceptor.helper.SchemaValidationHelper;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Deze Interceptor valideert ieder binnenkomend bericht tegen het XSD schema.
 */
public class SchemaValidationInInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaValidationInInterceptor.class);

    /**
     * Constructor die de Interceptor in juiste phase plaatst direct achter de SAAJInInterceptor.
     */
    public SchemaValidationInInterceptor() {
        super(Phase.PRE_PROTOCOL);
        addAfter(SAAJInInterceptor.class.getName());
    }

    /**
     * De daadwerkelijke validatie van het bericht tegen XSD.
     * @param message Het binnenkomende bericht.
     */
    @Override
    public void handleMessage(final Message message) {
        try {
            final Schema schema = SchemaValidationHelper.getSchema((String) message.get("SOAPAction"));
            if (schema == null) {
                throw new SAXException("kan geen schema validatie vinden voor " + (String) message.get("SOAPAction"));
            }
            final Validator validator = schema.newValidator();
            final SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            final SOAPBody soapBody = soapMessage.getSOAPBody();
            final SOAPBody bodyCopy = (SOAPBody) soapBody.cloneNode(true);
            validator.validate(new DOMSource(bodyCopy.extractContentAsDocument()));
        } catch (SAXException e) {
            LOGGER.error("Fout tijdens XSD validatie binnenkomend bericht.", e);
            throw new Fault(e);
        } catch (IOException e) {
            throw new Fault(e);
        } catch (SOAPException e) {
            throw new Fault(e);
        }
    }
}
