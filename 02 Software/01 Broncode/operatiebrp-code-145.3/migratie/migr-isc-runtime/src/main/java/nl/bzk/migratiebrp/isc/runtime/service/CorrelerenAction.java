/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.CorrelatieDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Correlatie;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Correleer het binnengekomen bericht aan een lopende proces.
 */
public final class CorrelerenAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger();

    private String kanaal;

    @Autowired
    private BerichtenDao berichtenDao;
    @Autowired
    private CorrelatieDao correlatieDao;
    @Autowired
    private JbpmService jbpmService;

    @Override
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    @Override
    public boolean verwerk(final Message message) {
        LOG.debug("[Bericht: {}]: correleren", message.getBerichtId());
        final String correlatieId = message.getCorrelatieId();
        LOG.debug("[Bericht: {}]: correlatieId={}", message.getBerichtId(), correlatieId);

        // Correleer bericht
        if (correlatieId != null && controleerGeenDelRepStaRepBericht(message)) {
            final Correlatie processData = correlatieDao.zoeken(correlatieId, kanaal, message.getOriginator(), message.getRecipient());
            LOG.debug("[Bericht: {}]: processData", message.getBerichtId(), processData);

            if (processData == null) {
                LOG.info("[Bericht: {}]: geen proces data gevonden, start foutmelding proces", message.getBerichtId());

                // Zoek gecorreleerd bericht obv correlatie, kanaal, originator en recipient
                final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht gecorreleerdBericht =
                        berichtenDao.zoekVraagBericht(correlatieId, kanaal, message.getOriginator(), message.getRecipient());
                LOG.info(
                        "[Bericht: {}]: gecorreleerdBericht={}",
                        message.getBerichtId(),
                        gecorreleerdBericht == null ? null : gecorreleerdBericht.getId());

                if (gecorreleerdBericht != null) {
                    message.setAttribute(Message.ATTRIBUTE_GECORRELEERD_BERICHT, gecorreleerdBericht);
                }

                // Start foutmelding
                berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_FOUTAFHANDELING_GESTART);
                final Long processInstanceId =
                        jbpmService.startFoutmeldingProces(
                                message.getBericht(),
                                message.getBerichtId(),
                                message.getRecipient(),
                                message.getOriginator(),
                                "esb.geen.processdata",
                                "Correlatie opgegeven, maar geen lopend proces gevonden.",
                                false,
                                false);
                berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);

                // Stop verwerking van deze ESB service
                return false;
            }

            message.setCorrelatie(processData);
            berichtenDao.updateProcessInstance(message.getBerichtId(), processData.getProcessInstance().getId());
        }

        LOG.debug("[Bericht: {}]: correleren ok", message.getBerichtId());
        return true;
    }

    private boolean controleerGeenDelRepStaRepBericht(final Message message) {
        return !("StaR".equals(message.getBerichtType()) || "DelR".equals(message.getBerichtType()));
    }
}
