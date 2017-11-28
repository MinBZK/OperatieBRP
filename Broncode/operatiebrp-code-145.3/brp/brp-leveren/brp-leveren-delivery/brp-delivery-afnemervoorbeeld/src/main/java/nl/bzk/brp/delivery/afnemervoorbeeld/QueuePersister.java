/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemervoorbeeld;

import javax.annotation.PostConstruct;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Queue opslag tbv testtooling
 */
@Component
final class QueuePersister implements Persister {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JmsTemplate afnemerOntvangenBerichtTemplate;
    private JmsTemplate afnemerOntvangenVrijBerichtTemplate;
    private JmsTemplate afnemerOntvangenNotificatieBerichtTemplate;

    @Value("${brp.afnemervoorbeeld.isqueuepersistent:false}")
    private boolean isQueuePersistent;

    @PostConstruct
    public void naConstructie() {
        LOGGER.warn("Berichten opslaan in queue: {}", isQueuePersistent);
        if (isQueuePersistent) {
            final ClassPathXmlApplicationContext cp = new ClassPathXmlApplicationContext("afnemervoorbeeld-queuepersist-context.xml");
            cp.registerShutdownHook();
            afnemerOntvangenBerichtTemplate = (JmsTemplate) cp.getBean("afnemerOntvangenBerichtTemplate");
            afnemerOntvangenVrijBerichtTemplate = (JmsTemplate) cp.getBean("afnemerOntvangenVrijBerichtTemplate");
            afnemerOntvangenNotificatieBerichtTemplate = (JmsTemplate) cp.getBean("afnemerOntvangenNotificatieBerichtTemplate");
        }
    }

    @Override
    public void persistPersoonBericht(final String request) {
        if (!isQueuePersistent) {
            LOGGER.warn("Bericht wordt niet gepersisteerd in de queue");
            return;
        }
        LOGGER.info("Persisteer SynchronisatieVerwerkPersoon in queue");
        afnemerOntvangenBerichtTemplate.convertAndSend("queue://AfnemerOntvangenBerichtQueue", request);
    }

    @Override
    public void persistVrijBericht(final String request) {
        if (!isQueuePersistent) {
            LOGGER.warn("Vrijbericht wordt niet gepersisteerd in de queue");
            return;
        }
        LOGGER.info("Persisteer VrijBericht in queue");
        afnemerOntvangenVrijBerichtTemplate.convertAndSend("queue://AfnemerOntvangenVrijBerichtQueue", request);
    }

    @Override
    public void persistNotificatieBericht(final String request) {
        if (!isQueuePersistent) {
            LOGGER.warn("Notificatiebericht wordt niet gepersisteerd in de queue");
            return;
        }
        LOGGER.info("Persisteer NotificatieBericht in queue");
        afnemerOntvangenNotificatieBerichtTemplate.convertAndSend("queue://AfnemerOntvangenNotificatieBerichtQueue", request);
    }
}
