/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.configuration;

import java.lang.reflect.Field;
import java.util.Map;

import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.event.JbpmConfiguredEvent;
import nl.bzk.migratiebrp.isc.runtime.jbpm.job.QuartzMessageServiceFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.job.QuartzSchedulerServiceFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.persistence.Hibernate4PersistenceServiceFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.quartz.Scheduler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

/**
 * JBPM Configuration factory bean. Configureert JBPM met de gegeven componenten.
 */
public final class JbpmConfigurationFactoryBean implements FactoryBean<JbpmConfiguration>, InitializingBean, ApplicationListener<ContextRefreshedEvent>,
        ApplicationContextAware, ApplicationEventPublisherAware, DisposableBean
{

    private static final String MESSAGE_SERVICE_FACTORY = "message";

    private static final String SCHEDULER_SERVICE_FACTORY = "scheduler";

    private static final String PERSISTENCE_SERVICE_FACTORY = "persistence";

    private static final String JBPM_PERSISTENCE_FACTORY_MELDING = "JBPM persistence factory: {}";

    private static final String JBPM_CONFIG = "isc-jbpm-algemeen-jbpm.cfg.xml";

    /** Logger for this class. */
    private static final Logger LOG = LoggerFactory.getLogger();

    /** The singleton object that this factory produces */
    private JbpmConfiguration jbpmConfiguration;

    /** The Hibernate session factory used by jBPM and the application */
    private SessionFactory sessionFactory;

    private Scheduler scheduler;

    private ApplicationContext applicationContext;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public JbpmConfiguration getObject() {
        return jbpmConfiguration;
    }

    @Override
    public Class<JbpmConfiguration> getObjectType() {
        return JbpmConfiguration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Zet de sessie factory.
     *
     * @param sessionFactory
     *            De te zetten sessie factory.
     */
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Zet de scheduler.
     *
     * @param scheduler
     *            De te zetten scheduler.
     */
    @Required
    public void setScheduler(final Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() {
        LOG.info("Initializing the jBPM configuration");

        // Create jbpm Config object
        jbpmConfiguration = JbpmConfiguration.getInstance(JBPM_CONFIG);
        LOG.info("JBPM configuration loaded");

        // Inject session factory
        final Hibernate4PersistenceServiceFactory persistenceServiceFactory =
                (Hibernate4PersistenceServiceFactory) jbpmConfiguration.getServiceFactory(PERSISTENCE_SERVICE_FACTORY);
        LOG.info(JBPM_PERSISTENCE_FACTORY_MELDING, persistenceServiceFactory);
        persistenceServiceFactory.setSessionFactory(sessionFactory);
        LOG.info("JBPM hibernate4 persistence service injected");

        // Inject scheduler
        final QuartzSchedulerServiceFactory schedulerServiceFactory =
                (QuartzSchedulerServiceFactory) jbpmConfiguration.getServiceFactory(SCHEDULER_SERVICE_FACTORY);
        LOG.info(JBPM_PERSISTENCE_FACTORY_MELDING, schedulerServiceFactory);
        schedulerServiceFactory.setScheduler(scheduler);
        final QuartzMessageServiceFactory messageServiceFactory =
                (QuartzMessageServiceFactory) jbpmConfiguration.getServiceFactory(MESSAGE_SERVICE_FACTORY);
        LOG.info(JBPM_PERSISTENCE_FACTORY_MELDING, messageServiceFactory);
        messageServiceFactory.setScheduler(scheduler);
        LOG.info("JBPM quartz scheduler and message service injected");

        // Inject spring bean factory
        final SpringServiceFactory springServiceFactory = (SpringServiceFactory) jbpmConfiguration.getServiceFactory(SpringServiceFactory.SERVICE_NAME);
        LOG.info(JBPM_PERSISTENCE_FACTORY_MELDING, springServiceFactory);
        springServiceFactory.setBeanFactory(applicationContext);
        LOG.info("JBPM spring service injected");

        // Overschrijf default configuratie
        overwriteDefaultJbpmConfiguration(jbpmConfiguration);

        LOG.info("jBPM configuration initialized.");
    }

    private static void overwriteDefaultJbpmConfiguration(final JbpmConfiguration theConfiguration) {
        try {
            final Field defaultInstanceField = JbpmConfiguration.class.getDeclaredField("DEFAULT_RESOURCE");
            defaultInstanceField.setAccessible(true);
            final String defaultInstance = (String) defaultInstanceField.get(null);
            final Field instancesField = JbpmConfiguration.class.getDeclaredField("instances");
            instancesField.setAccessible(true);
            final Map instances = (Map) instancesField.get(null);
            if (theConfiguration != null) {
                instances.put(defaultInstance, theConfiguration);
            } else {
                instances.remove(defaultInstance);
            }
            LOG.info("JBPM Default configuratie overschreven");
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan default configuratie niet overschrijven", e);
        }

    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent applicationEvent) {
        // Install process definitions
        LOG.info("Installing process instances");
        final Map<String, JbpmProcessInstaller> installers = applicationContext.getBeansOfType(JbpmProcessInstaller.class);
        for (final Map.Entry<String, JbpmProcessInstaller> installer : installers.entrySet()) {
            LOG.info("Installing {}", installer.getKey());
            installer.getValue().deployJbpmProcesses(jbpmConfiguration);
        }
        LOG.info("Process instances installed");

        LOG.info("Publishing event: JbpmConfiguredEvent");
        applicationEventPublisher.publishEvent(new JbpmConfiguredEvent(this));
        LOG.info("Event published (JbpmConfiguredEvent)");
    }

    @Override
    public void destroy() {
        LOG.info("Closing the jBPM configuration");
        LOG.info(JBPM_PERSISTENCE_FACTORY_MELDING, jbpmConfiguration.getServiceFactory(PERSISTENCE_SERVICE_FACTORY));
        LOG.info("JBPM scheduler factory: {}", jbpmConfiguration.getServiceFactory(SCHEDULER_SERVICE_FACTORY));
        LOG.info("JBPM message factory: {}", jbpmConfiguration.getServiceFactory(MESSAGE_SERVICE_FACTORY));
        LOG.info("JBPM spring factory: {}", jbpmConfiguration.getServiceFactory(SpringServiceFactory.SERVICE_NAME));
        jbpmConfiguration.close();
        overwriteDefaultJbpmConfiguration(null);
        LOG.info("jBPM configuration closed.");
    }
}
