/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.job;

import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;
import org.quartz.Scheduler;

/**
 * Alternatief voor {@link org.jbpm.msg.db.DbMessageServiceFactory} obv Quartz.
 */
public final class QuartzMessageServiceFactory implements ServiceFactory {
    private static final long serialVersionUID = 1L;

    private transient Scheduler scheduler;

    /**
     * Zet de scheduler. Aangeroepen door
     * {@link nl.bzk.migratiebrp.isc.runtime.jbpm.configuration.JbpmConfigurationFactoryBean}.
     * @param scheduler scheduler
     */
    public void setScheduler(final Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Service openService() {
        if (scheduler == null) {
            throw new IllegalStateException("QuartzMessageServiceFactory.openService() aangeroepen voordat Scheduler is geconfigureerd.");
        }

        return new QuartzMessageService(scheduler);
    }

    @Override
    public void close() {
        // Interface methode, hoeft niks te sluiten
    }

}
