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
import nl.bzk.migratiebrp.bericht.model.lo3.impl.StatusReport;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Verwerkt een status report.
 */
public final class VerwerkStatusReportAction implements Action {

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

        LOG.info("[Bericht: {}]: verwerkStatusReport", berichtId);

        final Bericht bericht = message.getBericht();
        LOG.info("[Bericht: {}]: controleer obv berichttype, berichttype = {}", bericht.getMessageId(), bericht.getBerichtType());

        if (!"StaR".equals(bericht.getBerichtType())) {
            LOG.info("[Bericht: {}]: geen statusReport", berichtId);
            return true;
        }

        final StatusReport deliveryReport = (StatusReport) bericht;
        final String foutmelding = "StatusReport ontvangen met NotificationType " + deliveryReport.getHeaderWaarde(Lo3HeaderVeld.NOTIFICATION_TYPE) + ".";
        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht oorspronkelijkProcesBericht =
                berichtenDao.zoekVraagBericht(bericht.getCorrelationId(), kanaal, originator, recipient);
        LOG.info(
                "Gevonden gecorreleerd bericht id={}, messageId={}, originator={}, recipient={} en inhoud={}",
                oorspronkelijkProcesBericht.getId(),
                oorspronkelijkProcesBericht.getMessageId(),
                oorspronkelijkProcesBericht.getVerzendendePartij(),
                oorspronkelijkProcesBericht.getOntvangendePartij(),
                oorspronkelijkProcesBericht.getBericht());

        final int aantalHerhalingen =
                berichtenDao.telBerichtenBehalveId(
                        oorspronkelijkProcesBericht.getMessageId(),
                        oorspronkelijkProcesBericht.getVerzendendePartij(),
                        oorspronkelijkProcesBericht.getOntvangendePartij(),
                        kanaal,
                        Direction.UITGAAND,
                        oorspronkelijkProcesBericht.getId());
        LOG.info("[Bericht: {}]: aantal berichten obv id, partijen, kanaal en richting = {}", berichtId, aantalHerhalingen);

        LOG.info("Start herhaal proces (aantal herhaling = {}).", aantalHerhalingen);
        if (aantalHerhalingen == 0) {
            LOG.info("Eerste StatusReport ontvangen, verstuur antwoord automatisch nogmaals.");
            // Stuur antwoord op status report
            final Long antwoordBerichtId =
                    berichtenDao.bewaar(
                            kanaal,
                            Direction.UITGAAND,
                            oorspronkelijkProcesBericht.getMessageId(),
                            oorspronkelijkProcesBericht.getCorrelationId(),
                            oorspronkelijkProcesBericht.getBericht(),
                            oorspronkelijkProcesBericht.getVerzendendePartij(),
                            oorspronkelijkProcesBericht.getOntvangendePartij(),
                            null,
                            oorspronkelijkProcesBericht.getRequestNonReceipt());
            LOG.info("Herhaald antwoord bewaard onder id {}", antwoordBerichtId);
            berichtenDao.updateProcessInstance(antwoordBerichtId, oorspronkelijkProcesBericht.getProcessInstance().getId());
            berichtenDao.updateNaam(antwoordBerichtId, oorspronkelijkProcesBericht.getNaam());
            berichtenDao.updateActie(berichtId, Acties.ACTIE_ANTWOORD_AUTOMATISCH_HERHAALD);

        } else {
            berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_HERHAALPROCES_GESTART);
        }

        final Long processInstanceId =
                jbpmService.startHerhaalProces(
                        bericht,
                        oorspronkelijkProcesBericht.getId(),
                        message.getOriginator(),
                        message.getRecipient(),
                        "StatusReport",
                        foutmelding,
                        aantalHerhalingen > 0);
        berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);
        message.setProcessInstanceId(processInstanceId);
        LOG.info("Herhaal proces gestart met id {}", processInstanceId);

        return false;

    }

}
