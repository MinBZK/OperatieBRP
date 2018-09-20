/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.quartz;

import java.lang.reflect.Field;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.utils.PropertiesParser;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Custom SchedulerFactoryBean om de job store aan te passen.
 */
public final class CustomSchedulerFactoryBean extends SchedulerFactoryBean {

    @Override
    protected Scheduler createScheduler(final SchedulerFactory schedulerFactory, final String schedulerName) throws SchedulerException {
        try {
            final Field cfgField = StdSchedulerFactory.class.getDeclaredField("cfg");
            cfgField.setAccessible(true);
            final PropertiesParser cfg = (PropertiesParser) cfgField.get(schedulerFactory);
            cfg.getUnderlyingProperties().setProperty(StdSchedulerFactory.PROP_JOB_STORE_CLASS, CustomDataSourceJobStore.class.getName());
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan jobStoreClass niet overschrijven", e);
        }

        return super.createScheduler(schedulerFactory, schedulerName);
    }

}
