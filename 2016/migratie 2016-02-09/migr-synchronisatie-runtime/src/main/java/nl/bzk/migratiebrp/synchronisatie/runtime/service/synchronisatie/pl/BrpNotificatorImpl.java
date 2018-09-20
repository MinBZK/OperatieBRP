/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp.GbaBijhoudingNotificatieBericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * Brp notificatie van administratieve handelingen.
 */
@Component(value = "brpNotificator")
public final class BrpNotificatorImpl implements BrpNotificator {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    @Named(value = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Inject
    @Named(value = "gbaBijhoudingen")
    private Destination destination;

    @Override
    // @Transactional(transactionManager = "syncDalTransactionManager", propagation = Propagation.NOT_SUPPORTED)
    @SuppressWarnings("PMD.PreserveStackTrace")
    public void stuurGbaBijhoudingBerichten(final Set<AdministratieveHandeling> administratieveHandelingen, final Integer persoonslijstId) {
        try {
            for (final AdministratieveHandeling administratieveHandeling : administratieveHandelingen) {
                jmsTemplate.send(destination, new MessageCreator() {
                    @Override
                    public Message createMessage(final Session session) throws JMSException {
                        final List<Integer> persoonIds = new ArrayList<>(1);
                        persoonIds.add(persoonslijstId);
                        final GbaBijhoudingNotificatieBericht bericht = new GbaBijhoudingNotificatieBericht(administratieveHandeling.getId(), persoonIds);
                        final ObjectMapper jsonMapper = new ObjectMapper();
                        final String berichtTekst;
                        try {
                            berichtTekst = jsonMapper.writeValueAsString(bericht);
                        } catch (final IOException e) {
                            final JMSException jmsException = new JMSException("Kan bericht niet serializeren", e.getMessage());
                            jmsException.setLinkedException(e);
                            throw jmsException;
                        }
                        return session.createTextMessage(berichtTekst);
                    }
                });
            }
        } catch (final JmsException e) {
            LOG.info("Verzenden van GbaBijhoudingBericht is mislukt.", e);
        }
    }
}
