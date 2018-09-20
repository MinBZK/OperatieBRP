/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.quartz;

import java.util.List;

import nl.moderniseringgba.isc.voisc.VoaService;
import nl.moderniseringgba.isc.voisc.entities.Bericht;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job die berichten ophaalt uit de berichten tabel welke naar de ESB gestuurd moeten worden.
 */
public class ToQueueJob implements Job {

    @Override
    public final void execute(final JobExecutionContext arg0) throws JobExecutionException {
        // TODO logging en fout afhandeling?
        final JobDataMap jdm = arg0.getMergedJobDataMap();
        final VoaService voiscService = (VoaService) jdm.get("voiscService");
        final List<Bericht> berichten = voiscService.getMessagesToSendToQueue(1000);
        voiscService.sendMessagesToEsb(berichten);
    }
}
