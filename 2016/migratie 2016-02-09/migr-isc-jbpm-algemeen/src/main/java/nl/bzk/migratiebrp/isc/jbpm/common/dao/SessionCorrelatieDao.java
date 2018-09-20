/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Correlatie;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Correlatie dao implementatie obv hibernate session factory.
 */
@Component
public final class SessionCorrelatieDao implements CorrelatieDao {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void opslaan(
        final String messageId,
        final String kanaal,
        final String recipient,
        final String originator,
        final Long processInstanceId,
        final Long tokenId,
        final Long nodeId)
    {
        final Correlatie correlatie = new Correlatie();
        correlatie.setMessageId(messageId);
        correlatie.setKanaal(kanaal);
        correlatie.setOntvangendePartij(recipient);
        correlatie.setVerzendendePartij(originator);

        final Session session = sessionFactory.getCurrentSession();
        final ProcessInstance processInstance = (ProcessInstance) session.get(ProcessInstance.class, processInstanceId);
        if (processInstance == null) {
            throw new IllegalArgumentException("Proces instance " + processInstanceId + " onbekend");
        }

        correlatie.setProcessInstance(processInstance);
        correlatie.setTokenId(tokenId);
        correlatie.setNodeId(nodeId);

        session.merge(correlatie);
    }

    @Override
    public Correlatie zoeken(
        final String receivedCorrelationId,
        final String receivedKanaal,
        final String receivedOriginator,
        final String receivedRecipient)
    {
        final Session session = sessionFactory.getCurrentSession();

        LOG.info("Zoek op correlatie opgeslagen onder message id: {}", receivedCorrelationId);
        Correlatie correlatie = (Correlatie) session.get(Correlatie.class, receivedCorrelationId);

        if (correlatie == null) {
            LOG.info("Zoek op correlatie opgeslagen onder message id: {} -> niet gevonden", receivedCorrelationId);
        } else {
            LOG.info("Zoek op correlatie opgeslagen onder message id: {} -> gevonden", receivedCorrelationId);
            LOG.info("Vergelijk ontvangen kanaal {} met opgeslagen kanaal {}", receivedKanaal, correlatie.getKanaal());
            LOG.info("Vergelijk ontvangen originator {} met opgeslagen recipient {}", receivedOriginator, correlatie.getOntvangendePartij());
            LOG.info("Vergelijk ontvangen recipient{} met opgeslagen originator {}", receivedRecipient, correlatie.getVerzendendePartij());

            if (!isEqual(receivedKanaal, correlatie.getKanaal())
                || !isEqual(receivedOriginator, correlatie.getOntvangendePartij())
                || !isEqual(receivedRecipient, correlatie.getVerzendendePartij()))
            {
                LOG.info("Incorrecte correlatie");
                correlatie = null;
            } else {
                LOG.info("Correcte correltie: proces -> {}", correlatie.getProcessInstance().getId());
            }
        }

        return correlatie;
    }

    private static boolean isEqual(final String value1, final String value2) {
        return value1 == null ? value2 == null : value1.equals(value2);
    }
}
