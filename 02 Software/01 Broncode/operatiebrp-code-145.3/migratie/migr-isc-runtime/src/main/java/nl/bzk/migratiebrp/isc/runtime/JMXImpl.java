/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * JMX Service.
 */
@UseDynamicDomain
@ManagedResource(objectName = JMXConstanten.OBJECT_NAME, description = "JMX Service voor ISC")
public final class JMXImpl implements JMX, ApplicationContextAware {

    private static final String JBPM_QUARTZ_SCHEDULER = "jbpmQuartzScheduler";
    private static final String SYNC_INBOUND_LISTENER = "syncInboundListener";
    private static final String VOISC_INBOUND_LISTENER = "voiscInboundListener";
    private static final String LEVERING_INBOUND_LISTENER = "leveringInboundListener";

    private ApplicationContext applicationContext;

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    @Override
    @ManagedOperation(description = "ISC proces afsluiten.")
    public void afsluiten() {
        applicationContext.getBean(Main.BEAN_NAME, Main.class).stop();
    }

    /**
     * @return is isc gestart.
     */
    @ManagedAttribute(description = "Applicatie actief")
    public boolean isGestart() {
        return true;
    }

    /**
     * @return is JBPM taken verwerking gestart.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedAttribute(description = "Uitvoeren van geplande JBPM taken")
    public boolean isJbpmGestart() throws SchedulerException {
        return applicationContext.getBean(JBPM_QUARTZ_SCHEDULER, Scheduler.class).isStarted();
    }

    /**
     * Start JBPM taken verwerking.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedOperation(description = "Start uitvoeren geplande JBPM taken")
    public void startJbpm() throws SchedulerException {
        applicationContext.getBean(JBPM_QUARTZ_SCHEDULER, Scheduler.class).start();
    }

    /**
     * Stop JBPM taken verwerking.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedOperation(description = "Stop uitvoeren geplande JBPM taken")
    public void stopJbpm() throws SchedulerException {
        applicationContext.getBean(JBPM_QUARTZ_SCHEDULER, Scheduler.class).standby();
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    /**
     * @return is Levering gestart
     */
    @ManagedAttribute(description = "Ontvangen van leveringsberichten")
    public boolean isLeveringGestart() {
        return applicationContext.getBean(LEVERING_INBOUND_LISTENER, DefaultMessageListenerContainer.class).isRunning();
    }

    /**
     * Start levering.
     */
    @ManagedOperation(description = "Start ontvangen leveringsberichten")
    public void startLevering() {
        applicationContext.getBean(LEVERING_INBOUND_LISTENER, DefaultMessageListenerContainer.class).start();
    }

    /**
     * Stop levering.
     */
    @ManagedOperation(description = "Stop ontvangen leveringsberichten")
    public void stopLevering() {
        applicationContext.getBean(LEVERING_INBOUND_LISTENER, DefaultMessageListenerContainer.class).stop();
    }

    /**
     * @return het aantal verwerkers voor levering.
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor leveringsberichten")
    public int getAantalLeveringVerwerkers() {
        return applicationContext.getBean(LEVERING_INBOUND_LISTENER, DefaultMessageListenerContainer.class).getMaxConcurrentConsumers();
    }

    /**
     * Zet het aantal verwerkers voor levering.
     * @param aantal het aantal te zetten verwerkers voor levering
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor leveringsberichten")
    public void setAantalLeveringVerwerkers(final int aantal) {
        applicationContext.getBean(LEVERING_INBOUND_LISTENER, DefaultMessageListenerContainer.class).setMaxConcurrentConsumers(aantal);
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    /**
     * @return is VOISC gestart
     */
    @ManagedAttribute(description = "Ontvangen van VOISC berichten")
    public boolean isVoiscGestart() {
        return applicationContext.getBean(VOISC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).isRunning();
    }

    /**
     * Start de VOISC queues.
     */
    @ManagedOperation(description = "Start ontvangen VOISC berichten")
    public void startVoisc() {
        applicationContext.getBean(VOISC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).start();
    }

    /**
     * Stop de VOISC queues.
     */
    @ManagedOperation(description = "Stop ontvangen VOISC berichten")
    public void stopVoisc() {
        applicationContext.getBean(VOISC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).stop();
    }

    /**
     * @return het aantal verwerkers voor VOISC.
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor VOISC berichten")
    public int getAantalVoiscVerwerkers() {
        return applicationContext.getBean(VOISC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).getMaxConcurrentConsumers();
    }

    /**
     * Zet het aantal verwerkers voor VOISC.
     * @param aantal het aantal te zetten verwerkers voor VOISC
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor VOISC berichten")
    public void setAantalVoiscVerwerkers(final int aantal) {
        applicationContext.getBean(VOISC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).setMaxConcurrentConsumers(aantal);
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    /**
     * @return is SYNC gestart
     */
    @ManagedAttribute(description = "Ontvangen van SYNC berichten")
    public boolean isSyncGestart() {
        return applicationContext.getBean(SYNC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).isRunning();
    }

    /**
     * Start de SYNC queues.
     */
    @ManagedOperation(description = "Start ontvangen SYNC berichten")
    public void startSync() {
        applicationContext.getBean(SYNC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).start();
    }

    /**
     * Stop de SYNC queues.
     */
    @ManagedOperation(description = "Stop ontvangen SYNC berichten")
    public void stopSync() {
        applicationContext.getBean(SYNC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).stop();
    }

    /**
     * @return het aantal verwerkers voor SYNC.
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor SYNC berichten")
    public int getAantalSyncVerwerkers() {
        return applicationContext.getBean(SYNC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).getMaxConcurrentConsumers();
    }

    /**
     * Zet het aantal verwerkers voor SYNC.
     * @param aantal het aantal te zetten verwerkers voor SYNC
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor SYNC berichten")
    public void setAantalSyncVerwerkers(final int aantal) {
        applicationContext.getBean(SYNC_INBOUND_LISTENER, DefaultMessageListenerContainer.class).setMaxConcurrentConsumers(aantal);
    }


}
