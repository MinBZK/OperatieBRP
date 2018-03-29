/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOngeldigBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.ProcesDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Correlatie;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Verwerk een bericht in een (lopend of nieuw) proces.
 */
public final class VerwerkInProcesAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private BerichtenDao berichtenDao;
    @Autowired
    private JbpmService jbpmService;
    @Autowired
    private ProcesDao procesDao;

    @Override
    public boolean verwerk(final Message message) {
        LOG.debug("[Bericht: {}]: process", message.getBerichtId());

        final Correlatie processData = message.getCorrelatie();
        LOG.debug("ProcessData: {}", processData);
        if (processData == null) {
            startNieuwProces(message);
        } else {
            // Vervolg bestaand proces
            vervolgBestaandProces(message, processData);
        }

        return true;
    }

    private void startNieuwProces(final Message message) {
        // Start nieuw proces
        final Bericht bericht = message.getBericht();
        final String cyclus = bericht.getStartCyclus();
        LOG.debug("Cyclus: {}", cyclus);

        final long processInstanceId;
        if (cyclus == null) {
            berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_FOUTAFHANDELING_GESTART);

            final String foutmelding;
            if (bericht instanceof AbstractOngeldigBericht) {
                foutmelding = ((AbstractOngeldigBericht) bericht).getMelding();
            } else {
                foutmelding = "Ontvangen bericht mag geen proces starten.";
            }

            processInstanceId =
                    jbpmService.startFoutmeldingProces(
                            bericht,
                            message.getBerichtId(),
                            message.getOriginator(),
                            message.getRecipient(),
                            "esb.start.ongeldig",
                            foutmelding,
                            false,
                            false);
        } else {
            berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_PROCES_GESTART);
            LOG.info("Start nieuw JBPM proces '{}'", cyclus);
            processInstanceId = jbpmService.startProces(cyclus, message.getBerichtId());
        }
        berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);
        message.setProcessInstanceId(processInstanceId);
    }

    private void vervolgBestaandProces(final Message message, final Correlatie processData) {
        final long nodeId = processData.getNodeId();
        final long tokenId = processData.getTokenId();
        final long processInstanceId = processData.getProcessInstance().getId();

        if (!jbpmService.vervolgProces(processInstanceId, tokenId, nodeId, message.getAttributes())) {
            // Proces bevond zich niet in de gecorreleerde status.
            berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_FOUTAFHANDELING_GESTART);
            final long foutProcessInstanceId =
                    jbpmService.startFoutmeldingProces(
                            message.getBericht(),
                            message.getBerichtId(),
                            message.getOriginator(),
                            message.getRecipient(),
                            "esb.proces.statusFout",
                            "Gecorreleerd bericht ontvangen, maar proces niet in status om bericht te ontvangen.",
                            true,
                            false);
            berichtenDao.updateProcessInstance(message.getBerichtId(), foutProcessInstanceId);
            procesDao.registreerProcesRelatie(processInstanceId, foutProcessInstanceId);

        }
    }

    @Override
    public void setKanaal(final String kanaal) {
        // Ignored
    }

}
