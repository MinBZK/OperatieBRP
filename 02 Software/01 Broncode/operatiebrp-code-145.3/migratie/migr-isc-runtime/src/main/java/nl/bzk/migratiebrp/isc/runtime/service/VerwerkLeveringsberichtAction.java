/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.MessageId;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.SessionBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.VirtueelProcesDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;

/**
 * Verwerk leveringsbericht.
 */
public final class VerwerkLeveringsberichtAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CENTRALE_VOORZIENING_PARTIJ_CODE = "199902";

    @Autowired
    private BerichtenDao berichtenDao;

    @Autowired
    private VirtueelProcesDao virtueelProcesDao;

    private JmsTemplate jmsTemplate;

    /**
     * Zet het JMS Template.
     * @param jmsTemplate het te zetten JMS Template
     */
    @Required
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void setKanaal(final String kanaal) {
        // Ignore
    }

    @Override
    public boolean verwerk(final Message message) {
        LOG.info("[Bericht: {}]: process", message.getBerichtId());

        final Long leveringsBerichtId = message.getBerichtId();
        berichtenDao.updateNaam(leveringsBerichtId, "Levering");
        final String iscReferentienummer = message.getHeader(JMSConstants.CORRELATIE_REFERENTIE);

        // Sla het bericht als VOISC bericht op.
        final String leveringsBericht = message.getContent();
        final Long voiscBerichtId =
                berichtenDao.bewaar("VOISC", Direction.UITGAAND, null, null, leveringsBericht, CENTRALE_VOORZIENING_PARTIJ_CODE, message.getRecipient(), null,
                        true);
        LOG.info("[Bericht: {}]: voiscBerichtId: {}", message.getBerichtId(), voiscBerichtId);

        LOG.info("[Bericht: {}]: referentienummer: {}", message.getBerichtId(), iscReferentienummer);
        if (iscReferentienummer != null) {
            // Ag01 als antwoord op een eerder ontvangen Ap01
            berichtenDao.updateCorrelatieId(voiscBerichtId, iscReferentienummer);
            final Bericht vraagBericht =
                    berichtenDao
                            .zoekVraagBericht(iscReferentienummer, SessionBerichtenDao.KANAAL_VOISC, CENTRALE_VOORZIENING_PARTIJ_CODE, message.getRecipient());

            if (vraagBericht != null) {
                LOG.info(
                        "[Bericht: {}]: bericht gekoppeld via referentienummer: id={}, processInstanceId={}",
                        message.getBerichtId(),
                        vraagBericht.getId(),
                        vraagBericht.getProcessInstance().getId());
                berichtenDao.updateProcessInstance(leveringsBerichtId, vraagBericht.getProcessInstance().getId());
                berichtenDao.updateProcessInstance(voiscBerichtId, vraagBericht.getProcessInstance().getId());
            }

        } else {
            // Ander leveringsbericht

            // Maak virtueel proces
            final long virtueelProcesId = virtueelProcesDao.maak();
            LOG.info("[Bericht: {}]: virtueel proces: {}", message.getBerichtId(), virtueelProcesId);
            berichtenDao.updateVirtueelProcesId(leveringsBerichtId, virtueelProcesId);

            // Voeg administratieve handeling toe
            final String administratieveHandelingId = message.getAdministratieveHandelingId();
            if (administratieveHandelingId != null && !"".equals(administratieveHandelingId)) {
                virtueelProcesDao.toevoegenGerelateerdeGegevens(virtueelProcesId, "ADH", administratieveHandelingId);
            }

            // Koppel het virtuele proces
            berichtenDao.updateVirtueelProcesId(voiscBerichtId, virtueelProcesId);
        }

        // Bepaal het message id
        final String voiscBerichtMessageId = MessageId.bepaalMessageId(voiscBerichtId);
        berichtenDao.updateMessageId(voiscBerichtId, voiscBerichtMessageId);

        LOG.info("[Bericht: {}]: versturen bericht naar LO3", message.getBerichtId());
        jmsTemplate.send(session -> {
            final javax.jms.Message result = session.createTextMessage(leveringsBericht);
            result.setStringProperty(JMSConstants.BERICHT_REFERENTIE, voiscBerichtMessageId);
            if (iscReferentienummer != null) {
                result.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, iscReferentienummer);
            }
            result.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, CENTRALE_VOORZIENING_PARTIJ_CODE);
            result.setStringProperty(JMSConstants.BERICHT_RECIPIENT, message.getRecipient());
            return result;
        });

        LOG.info("[Bericht: {}]: gereed", message.getBerichtId());
        return true;
    }
}
