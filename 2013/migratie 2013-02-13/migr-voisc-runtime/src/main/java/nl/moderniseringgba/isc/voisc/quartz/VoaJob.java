/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.quartz;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.isc.voisc.VoaService;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;
import nl.moderniseringgba.isc.voisc.utils.StringUtil;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.beans.factory.DisposableBean;

/**
 * 
 */
public class VoaJob implements StatefulJob, DisposableBean {

    private static final int GEMEENTE_CODE_LENGTH = 4;
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private GemeenteRepository gemeenteRepo;
    private VoiscDbProxy voiscDbProxy;
    private boolean stopJob;

    @Override
    public final void execute(final JobExecutionContext arg0) throws JobExecutionException {
        stopJob = false;
        LOGGER.info("VoaJob started");
        final JobDataMap jdm = arg0.getMergedJobDataMap();
        voiscDbProxy = (VoiscDbProxy) jdm.get("voiscDbProxy");
        gemeenteRepo = (GemeenteRepository) jdm.get("gemeenteRepo");
        final VoaService voiscService = (VoaService) jdm.get("voiscService");

        // a. Ophalen gemeentelijst
        final Map<String, Mailbox> mailboxes = getActiveMailboxes();

        // b. SSL verbinding opbouwen naar MBS
        voiscService.connectToMailboxServer();

        // Per gemeente:
        for (final Mailbox mailbox : mailboxes.values()) {
            if (stopJob) {
                break;
            }

            final LogboekRegel regel = new LogboekRegel();
            regel.setStartDatumTijd(Calendar.getInstance().getTime());
            regel.setMailbox(mailbox);

            try {
                // 1. uitlezen tabel voaBericht met nog te versturen berichten.
                final List<Bericht> berichten = voiscService.getMessagesToSend(mailbox);

                // 2. Inloggen op de Mailbox van de gemeente
                voiscService.login(regel, mailbox);

                if (!regel.hasFoutmelding()) {
                    // 3. Versturen berichten naar Mailbox
                    voiscService.sendMessagesToMailbox(regel, berichten);
                    // 4, 5. Berichten uit mailbox ophalen en opslaan.
                    voiscService.receiveAndStoreMessagesFromMailbox(mailbox, regel);
                }
            } catch (final VoaException ve) {
                regel.setFoutmelding(ve.getMessage());
                // CHECKSTYLE:OFF Proces mag er niet uit gaan vanwege een onverwachte fout
            } catch (final Throwable t) { // NOSONAR
                // CHECKSYTLE:ON
                LOGGER.error("Onverwachte error opgetreden: ", t);
            } finally {
                // Sla de logboekregel op
                voiscDbProxy.saveLogboekRegel(regel);
            }
        }

        voiscService.logout();
        LOGGER.info("VoaJob ended");
    }

    private Map<String, Mailbox> getActiveMailboxes() {
        final List<Gemeente> gemeenten = gemeenteRepo.getBrpActiveGemeente();
        final Map<String, Mailbox> mailboxes = new TreeMap<String, Mailbox>();

        for (final Gemeente gemeente : gemeenten) {
            final String gemeentecode = StringUtil.zeroPadded(gemeente.getGemeenteCode(), GEMEENTE_CODE_LENGTH);
            final Mailbox mailbox = voiscDbProxy.getMailboxByGemeentecode(gemeentecode);

            if (mailbox != null) {
                mailboxes.put(gemeentecode, mailbox);
            }
        }
        return mailboxes;
    }

    @Override
    public final void destroy() {
        stopJob = true;
    }
}
