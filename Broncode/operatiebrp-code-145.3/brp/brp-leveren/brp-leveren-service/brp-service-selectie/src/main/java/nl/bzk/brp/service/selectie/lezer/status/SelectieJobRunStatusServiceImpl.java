/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.selectie.algemeen.JobEventStopType;
import nl.bzk.brp.service.selectie.algemeen.JobStopEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * SelectieJobRunStatusServiceImpl.
 */
@Service
public final class SelectieJobRunStatusServiceImpl implements SelectieJobRunStatusService, ApplicationListener<JobStopEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private SelectieJobRunStatus status = new SelectieJobRunStatus();

    private SelectieJobRunStatusServiceImpl() {
    }

    @Override
    public SelectieJobRunStatus getStatus() {
        return status;
    }

    @Override
    public SelectieJobRunStatus newStatus() {
        status = new SelectieJobRunStatus();
        return status;
    }


    @Override
    public void onApplicationEvent(JobStopEvent jobStopEvent) {
        LOGGER.info("stop ontvangen");
        status.setMoetStoppen(true);
        if (JobEventStopType.FOUT == jobStopEvent.getType()) {
            status.setError(true);
        }
    }

}
