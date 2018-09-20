/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import javax.jms.JMSException;
import javax.jms.Session;

import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.MessageId;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.SessionBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.VirtueelProcesDao;
import nl.bzk.migratiebrp.isc.jbpm.common.verzender.VerzendendeInstantieDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Verwerk leveringsbericht.
 */
public final class VerwerkLeveringsberichtAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private BerichtenDao berichtenDao;
    @Autowired
    private VerzendendeInstantieDao verzenderDao;
    @Autowired
    private VirtueelProcesDao virtueelProcesDao;
    @Autowired
    private JbpmService jbpmService;

    private JmsTemplate jmsTemplate;

    /**
     * Zet het JMS Template.
     *
     * @param jmsTemplate
     *            het te zetten JMS Template
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
        final String recipient = message.getRecipient();
        final Long originator = recipient == null ? null : verzenderDao.bepaalVerzendendeInstantie(Long.parseLong(recipient));
        final String iscReferentienummer = message.getHeader("referentienummer");

        if (originator == null) {
            LOG.info("[Bericht: {}]: kan geen verzendende partij bepalen", message.getBerichtId());
            final Long processInstanceId =
                    jbpmService.startFoutmeldingProces(
                        message.getBericht(),
                        message.getBerichtId(),
                        message.getRecipient(),
                        message.getOriginator(),
                        "esb.levering.geenVerzender",
                        "Kon geen verzendende partij bepalen voor recipient '" + recipient + "'.",
                        true,
                        true);
            berichtenDao.updateActie(leveringsBerichtId, Acties.ACTIE_FOUTAFHANDELING_GESTART);
            berichtenDao.updateProcessInstance(leveringsBerichtId, processInstanceId);
            return false;
        }

        // Sla het bericht als VOSPG bericht op.
        final String leveringsBericht = message.getContent();
        final Long vospgBerichtId = berichtenDao.bewaar("VOSPG", Direction.UITGAAND, null, null, leveringsBericht, originator.toString(), recipient, null);
        LOG.info("[Bericht: {}]: vospgBerichtId: {}", message.getBerichtId(), vospgBerichtId);

        LOG.info("[Bericht: {}]: referentienummer: {}", message.getBerichtId(), iscReferentienummer);
        if (iscReferentienummer != null) {
            // Ag01 als antwoord op een eerder ontvangen Ap01
            berichtenDao.updateCorrelatieId(vospgBerichtId, iscReferentienummer);
            final Bericht vraagBericht =
                    berichtenDao.zoekVraagBericht(iscReferentienummer, SessionBerichtenDao.KANAAL_VOSPG, originator.toString(), recipient);

            if (vraagBericht != null) {
                LOG.info(
                    "[Bericht: {}]: bericht gekoppeld via referentienummer: id={}, processInstanceId={}",
                    message.getBerichtId(),
                    vraagBericht.getId(),
                    vraagBericht.getProcessInstance().getId());
                berichtenDao.updateProcessInstance(leveringsBerichtId, vraagBericht.getProcessInstance().getId());
                berichtenDao.updateProcessInstance(vospgBerichtId, vraagBericht.getProcessInstance().getId());
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
            berichtenDao.updateVirtueelProcesId(vospgBerichtId, virtueelProcesId);
        }

        // Bepaal het message id
        final String vospgBerichtMessageId = MessageId.bepaalMessageId(vospgBerichtId);
        berichtenDao.updateMessageId(vospgBerichtId, vospgBerichtMessageId);

        LOG.info("[Bericht: {}]: versturen bericht naar LO3", message.getBerichtId());
        jmsTemplate.send(new MessageCreator() {
            @Override
            public javax.jms.Message createMessage(final Session session) throws JMSException {
                final javax.jms.Message result = session.createTextMessage(leveringsBericht);
                result.setStringProperty(JMSConstants.BERICHT_REFERENTIE, vospgBerichtMessageId);
                if (iscReferentienummer != null) {
                    result.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, iscReferentienummer);
                }
                result.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, originator.toString());
                result.setStringProperty(JMSConstants.BERICHT_RECIPIENT, recipient);
                return result;
            }
        });

        LOG.info("[Bericht: {}]: gereed", message.getBerichtId());
        return true;
    }
}
