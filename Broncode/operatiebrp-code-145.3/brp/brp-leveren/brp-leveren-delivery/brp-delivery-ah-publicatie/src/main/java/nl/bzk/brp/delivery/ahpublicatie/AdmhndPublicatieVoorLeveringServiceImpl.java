/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.ahpublicatie;


import java.util.Set;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.ahpublicatie.AdmhndPublicatieVoorLeveringService;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * AdmhnPublicatieVoorLeveringService.
 */
final class AdmhndPublicatieVoorLeveringServiceImpl implements AdmhndPublicatieVoorLeveringService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;

    /**
     * Zet de connection factory.
     * @param jmsTemplatePublicatie jmsTemplate
     */
    @Required
    public void setPublicatieTemplate(final JmsTemplate jmsTemplatePublicatie) {
        this.jmsTemplate = jmsTemplatePublicatie;
    }

    @Override
    public void publiceerHandelingen(final Set<String> handelingenVoorPublicatie) {
        try {
            jmsTemplate.execute((final Session session, final MessageProducer producer) -> {
                for (final String handelingVoorPublicatie : handelingenVoorPublicatie) {
                    producer.send(session.createTextMessage(handelingVoorPublicatie));
                }
                return null;
            });
        } catch (final JmsException e) {
            LOGGER.error("fout in verwerking berichten naar admhnd publicatie queue", e);
            throw new BrpServiceRuntimeException(e);
        }
    }
}
