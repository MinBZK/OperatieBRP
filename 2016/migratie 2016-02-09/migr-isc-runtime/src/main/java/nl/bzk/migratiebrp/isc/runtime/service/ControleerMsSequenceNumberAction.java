/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import java.util.List;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmService;
import nl.bzk.migratiebrp.isc.runtime.message.Acties;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controleer MsSequenceNumber.
 */
public final class ControleerMsSequenceNumberAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private BerichtenDao berichtenDao;
    @Autowired
    private JbpmService jbpmService;

    @Override
    public void setKanaal(final String kanaal) {
        // Ignore
    }

    @Override
    public boolean verwerk(final Message message) {
        LOG.debug("[Bericht: {}]: process", message.getBerichtId());
        final Long msSequenceNumber = message.getMsSequenceNumber();
        LOG.info("[Bericht: {}]: msSequenceNumber {}", message.getBerichtId(), msSequenceNumber);

        if (msSequenceNumber == null) {
            LOG.info("[Bericht: {}]: msSequenceNumber null, start foutafhandeling", message.getBerichtId());
            berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_FOUTAFHANDELING_GESTART);
            final Long processInstanceId =
                    jbpmService.startFoutmeldingProces(
                        message.getBericht(),
                        message.getBerichtId(),
                        message.getRecipient(),
                        message.getOriginator(),
                        "esb.mssequencenumber.leeg",
                        "Geen MSSequenceNumber ontvangen",
                        true,
                        true);
            berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);
            return false;
        }

        final List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht> berichten =
                berichtenDao.zoekOpMsSequenceNumberBehalveId(msSequenceNumber, message.getBerichtId());
        if (berichten.size() > 0) {
            final String originator = message.getOriginator();
            final String messageId = message.getMessageId();
            final String berichtType = message.getBerichtType();

            // Controleer dat alle gevonden berichten dezelfde afzender, msgid en berichttype hebben.
            for (final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht : berichten) {
                if (!isEqual(originator, bericht.getVerzendendePartij())
                    || !isEqual(messageId, bericht.getMessageId())
                    || !isEqual(berichtType, bericht.getNaam()))
                {
                    LOG.info("[Bericht: {}]: Alarm obv mssequencenumber", message.getBerichtId());
                    // Alarm
                    berichtenDao.updateActie(message.getBerichtId(), Acties.ACTIE_FOUTAFHANDELING_GESTART);
                    final Long processInstanceId =
                            jbpmService.startFoutmeldingProces(
                                message.getBericht(),
                                message.getBerichtId(),
                                message.getRecipient(),
                                message.getOriginator(),
                                "esb.mssequencenumber.fout",
                                "MSSequenceNumber ontvangen wat al " + "ontvangen is met andere originator, message-id of berichttype",
                                true,
                                true);
                    berichtenDao.updateProcessInstance(message.getBerichtId(), processInstanceId);
                    return false;
                }
            }

        }

        LOG.debug("[Bericht: {}]: ok", message.getBerichtId());
        return true;
    }

    private static boolean isEqual(final String value1, final String value2) {
        return value1 == null ? value2 == null : value1.equals(value2);
    }
}
