/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.starter;

import javax.inject.Named;

import nl.bzk.migratiebrp.isc.runtime.jbpm.event.JbpmConfiguredEvent;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Start de quartz scheduler (nadat JBPM is gestart).
 */
public final class QuartzSchedulerStarter implements ApplicationListener<JbpmConfiguredEvent> {

    // Start scheduler nadat processen zijn geinstalleerd

    @Autowired
    @Named("jbpmQuartzScheduler")
    private Scheduler scheduler;

    private boolean autostartScheduler;

    /**
     * Zet de indicator voor het automatisch starten van de scheduler.
     * 
     * @param autostartScheduler
     *            De te zetten indicator.
     */
    public void setAutostartScheduler(final boolean autostartScheduler) {
        this.autostartScheduler = autostartScheduler;
    }

    @Override
    @Transactional(value = "iscTransactionManager", propagation = Propagation.NOT_SUPPORTED)
    public void onApplicationEvent(final JbpmConfiguredEvent event) {
        if (autostartScheduler) {
            try {
                scheduler.start();
            } catch (final SchedulerException e) {
                throw new IllegalStateException("Kon scheduler niet starten", e);
            }
        }
    }
}
