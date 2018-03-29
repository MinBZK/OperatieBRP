/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect voor het volgen van de verwerking.
 */
@Aspect
public class AspectForMDC {

    private static final String VERWERKING_CODE = "verwerking";
    private static final String JMS_MESSAGE_ID = "jms_message_id";
    private static final String BERICHT_REFERENTIE = "iscBerichtReferentie";
    private static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";
    private static final String SYNC_BERICHT_REFERENTIE = "berichtReferentie";
    private static final String SYNC_CORRELATIE_REFERENTIE = "correlatieReferentie";
    /**
     * De pointcut voor het toevoegen van MDC informatie.
     */
    @Pointcut("execution(* onMessage(..))")
    public void jmsBerichtLezer() {
        // voor definitie van de pointcut zelf.
    }

    /**
     * Het advies uit te voeren op de pointcut.
     * @param message parameter van de methode
     * @throws JMSException als iets in de pointcut fout gaat
     */
    @Before("nl.bzk.algemeenbrp.util.common.logging.AspectForMDC.jmsBerichtLezer() && args(message)")
    public void voegMDCInformatieToe(Message message) throws JMSException {
        if (message.getStringProperty(MDC.JMS_VERWERKING_CODE) != null) {
            org.slf4j.MDC.put(VERWERKING_CODE, message.getStringProperty(MDC.JMS_VERWERKING_CODE));
        } else {
            org.slf4j.MDC.put(VERWERKING_CODE, UUID.randomUUID().toString());
        }
        org.slf4j.MDC.put(JMS_MESSAGE_ID, message.getJMSMessageID());
        if (message.getStringProperty(BERICHT_REFERENTIE) != null) {
            org.slf4j.MDC.put(SYNC_BERICHT_REFERENTIE, message.getStringProperty(BERICHT_REFERENTIE));
        } else {
            org.slf4j.MDC.remove(SYNC_BERICHT_REFERENTIE);
        }
        if (message.getStringProperty(CORRELATIE_REFERENTIE) != null) {
            org.slf4j.MDC.put(SYNC_CORRELATIE_REFERENTIE, message.getStringProperty(CORRELATIE_REFERENTIE));
        } else {
            org.slf4j.MDC.remove(SYNC_CORRELATIE_REFERENTIE);
        }
    }

}
