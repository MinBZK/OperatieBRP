/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.DeliveryReport;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Verwerk een delivery report.
 */
public final class VerwerkDeliveryReportAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private BerichtenDao berichtenDao;
    @Autowired
    private JbpmService jbpmService;

    private String kanaal;

    @Override
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    @Override
    public boolean verwerk(final Message message) {
        final Long berichtId = message.getBerichtId();
        final String originator = message.getOriginator();
        final String recipient = message.getRecipient();

        LOG.info("[Bericht: {}]: verwerkDeliveryReport", berichtId);

        final Bericht bericht = message.getBericht();
        LOG.info("[Bericht: {}]: controleer obv berichttype, berichttype = {}", bericht.getMessageId(), bericht.getBerichtType());

        if (!"DelR".equals(bericht.getBerichtType())) {
            LOG.info("[Bericht: {}]: geen deliveryReport", berichtId);
            return true;
        }

        LOG.info("Delivery report ontvangen, start herhaal proces.");
        berichtenDao.updateActie(berichtId, Acties.ACTIE_HERHAALPROCES_GESTART);

        final DeliveryReport deliveryReport = (DeliveryReport) bericht;
        final String foutmelding = "DeliveryReport ontvangen met NonDeliveryReason " + deliveryReport.getHeaderWaarde(Lo3HeaderVeld.NON_DELIVERY_REASON) + ".";
        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht oorspronkelijkProcesBericht =
                berichtenDao.zoekVraagBericht(bericht.getCorrelationId(), kanaal, originator, recipient);
        LOG.info("Gevonden gecorreleerd bericht id={} en inhoud={}", oorspronkelijkProcesBericht.getId(), oorspronkelijkProcesBericht.getBericht());

        final Long processInstanceId =
                jbpmService.startHerhaalProces(
                        bericht,
                        oorspronkelijkProcesBericht.getId(),
                        message.getOriginator(),
                        message.getRecipient(),
                        "NonDeliveryReport",
                        foutmelding,
                        true);
        berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);
        message.setProcessInstanceId(processInstanceId);
        LOG.info("Herhaal proces gestart met id {}", processInstanceId);

        return false;
    }

}
