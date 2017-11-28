/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.job;

import javax.inject.Inject;
import nl.bzk.brp.service.selectie.lezer.job.SelectieRunJobService;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Jmx support.
 */
@Component
@ManagedResource(objectName = "selectie:name=SelectieJob",
        description = "Het starten en stoppen van selectieJob.")
public final class JmxSupportSelectieJob {

    @Inject
    private SelectieRunJobService selectieRunJobService;

    private JmxSupportSelectieJob() {
    }

    /**
     * Start selectieJob.
     */
    @ManagedOperation(description = "startSelectieJob")
    public void startSelectieJob() {
        selectieRunJobService.start();
    }

    /**
     * check running selectieJob.
     */
    @ManagedOperation(description = "isJobRunning")
    public boolean isJobRunning() {
        return selectieRunJobService.isRunning();
    }

    /**
     * Stop selectieJob.
     */
    @ManagedOperation(description = "stopSelectieJob")
    public void stopSelectieJob() {
        selectieRunJobService.stop();
    }

}
