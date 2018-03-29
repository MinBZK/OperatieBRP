/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
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
@ManagedResource(objectName = VoiscJMXImpl.OBJECT_NAME, description = "JMX Service voor VOISC")
public final class VoiscJMXImpl implements VoiscJMX, ApplicationContextAware {

    /**
     * Object name to bind JMX object to.
     */
    static final String OBJECT_NAME = "nl.bzk.migratiebrp.voisc:name=VOISC";
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final ExecutorService EXECUTOR_SERVICE_ISC = Executors.newSingleThreadExecutor();
    private static final ExecutorService EXECUTOR_SERVICE_MAILBOX = Executors.newSingleThreadExecutor();
    private static final ExecutorService EXECUTOR_SERVICE_OPSCHONEN = Executors.newSingleThreadExecutor();
    private static final ExecutorService EXECUTOR_SERVICE_HERSTELLEN = Executors.newSingleThreadExecutor();
    private static final int DEFAULT_AANTAL_UREN_SINDS_VERWERKT = 75;
    private static final int DEFAULT_AANTAL_UREN_SINDS_IN_VERWERKING = 6;
    private static final String QUARTZ_SCHEDULER = "scheduler";
    private static final String INBOUND_LISTENER = "voiscBerichtListenerContainer";
    private final VoiscService voiscService;
    private final MailboxConfiguratie voiscConfiguratie;
    private ApplicationContext applicationContext;
    private int aantalUrenSindsVerwerkt = DEFAULT_AANTAL_UREN_SINDS_VERWERKT;

    private int aantalUrenSindsInVerwerking = DEFAULT_AANTAL_UREN_SINDS_IN_VERWERKING;

    /**
     * Constructor.
     * @param voiscService service
     * @param voiscConfiguratie configuratie
     */
    @Inject
    public VoiscJMXImpl(final VoiscService voiscService, final MailboxConfiguratie voiscConfiguratie) {
        this.voiscService = voiscService;
        this.voiscConfiguratie = voiscConfiguratie;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Zet de waarde van aantal uren sinds verwerkt.
     * @param aantalUrenSindsVerwerkt aantal uren sinds verwerkt
     */
    public void setAantalUrenSindsVerwerkt(final int aantalUrenSindsVerwerkt) {
        this.aantalUrenSindsVerwerkt = aantalUrenSindsVerwerkt;
    }

    /**
     * Zet de waarde van aantal uren sinds in verwerking.
     * @param aantalUrenSindsInVerwerking aantal uren sinds in verwerking
     */
    public void setAantalUrenSindsInVerwerking(final int aantalUrenSindsInVerwerking) {
        this.aantalUrenSindsInVerwerking = aantalUrenSindsInVerwerking;
    }

    @Override
    @ManagedOperation(description = "Verstuur berichten van VOISC (database) naar ISC.")
    public void berichtenVerzendenNaarIsc() {
        LOGGER.info("Verzenden berichten naar ISC.");
        EXECUTOR_SERVICE_ISC.submit(() -> {
            try {
                voiscService.berichtenVerzendenNaarIsc();
            } catch (final Exception e /* Catch throwable voor logging; wordt gerethrowed */) {
                LOGGER.error("Fout bij verzenden berichten naar ISC.", e);
                throw e;
            }
        });
    }

    @Override
    @ManagedOperation(description = "Verstuur berichten van VOISC (database) naar mailbox en ontvang berichten van mailbox.")
    public void berichtenVerzendenNaarEnOntvangenVanMailbox() {
        LOGGER.info("Verzenden naar en ontvangen van mailbox.");
        EXECUTOR_SERVICE_MAILBOX.submit(() -> {
            try {
                voiscService.berichtenVerzendenNaarEnOntvangenVanMailbox();
            } catch (final Exception e /* Catch throwable voor logging; wordt gerethrowed */) {
                LOGGER.error("Fout bij verzenden naar en ontvangen van mailbox.", e);
                throw e;
            }
        });
    }

    @Override
    @ManagedOperation(description = "Opschonen verzonden berichten.")
    public void opschonenVoiscBerichten() {
        LOGGER.info("Opschonen berichten.");
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -aantalUrenSindsVerwerkt);
        final Date ouderDan = cal.getTime();
        LOGGER.info("Controleren op berichten van voor {}.", ouderDan);

        EXECUTOR_SERVICE_OPSCHONEN.submit(() -> {
            try {
                voiscService.opschonenVoiscBerichten(ouderDan);
            } catch (final Exception e /* Catch throwable voor logging; wordt gerethrowed */) {
                LOGGER.error("Fout bij opschonen berichten.", e);
                throw e;
            }
        });
    }

    @Override
    @ManagedOperation(description = "Herstellen in verwerking zijnde berichten.")
    public void herstellenVoiscBerichten() {
        LOGGER.info("Herstellen berichten.");
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -aantalUrenSindsInVerwerking);
        final Date ouderDan = cal.getTime();

        EXECUTOR_SERVICE_HERSTELLEN.submit(() -> {
            try {
                voiscService.herstellenVoiscBerichten(ouderDan);
            } catch (final Exception e /* Catch throwable voor logging; wordt gerethrowed */) {
                LOGGER.error("Fout bij herstellen berichten.", e);
                throw e;
            }
        });
    }

    @Override
    @ManagedOperation(description = "VOISC mailbox configuratie.")
    public String toonMailboxConfiguratie() {
        return voiscConfiguratie.toonMailboxen();
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    @Override
    @ManagedOperation(description = "VOISC proces afsluiten.")
    public void afsluiten() {
        applicationContext.getBean(VoiscMain.BEAN_NAME, VoiscMain.class).stop();
    }

    /**
     * @return is voisc gestart.
     */
    @ManagedAttribute(description = "Applicatie actief")
    public boolean isGestart() {
        return true;
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    /**
     * @return is verwerking gestart.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedAttribute(description = "Versturen en ontvangen van berichten van de mailbox")
    public boolean isMailboxGestart() throws SchedulerException {
        final Scheduler scheduler = applicationContext.getBean(QUARTZ_SCHEDULER, Scheduler.class);
        return scheduler.isStarted() && !scheduler.isInStandbyMode();
    }

    /**
     * Start verwerking.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedOperation(description = "Start versturen en ontvangen van berichten van de mailbox")
    public void startMailbox() throws SchedulerException {
        applicationContext.getBean(QUARTZ_SCHEDULER, Scheduler.class).start();
    }

    /**
     * Stop verwerking.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedOperation(description = "Stop versturen en ontvangen van berichten van de mailbox")
    public void stopMailbox() throws SchedulerException {
        applicationContext.getBean(QUARTZ_SCHEDULER, Scheduler.class).standby();
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    /**
     * @return is verwerking gestart.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedAttribute(description = "Versturen van berichten naar ISC")
    public boolean isIscVersturenGestart() throws SchedulerException {
        final Scheduler scheduler = applicationContext.getBean(QUARTZ_SCHEDULER, Scheduler.class);
        return scheduler.isStarted() && !scheduler.isInStandbyMode();
    }

    /**
     * Start verwerking.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedOperation(description = "Start versturen van berichten naar ISC")
    public void startIscVersturen() throws SchedulerException {
        applicationContext.getBean(QUARTZ_SCHEDULER, Scheduler.class).start();
    }

    /**
     * Stop verwerking.
     * @throws SchedulerException Foutmelding bij het schedulen van een taak met Quartz.
     */
    @ManagedOperation(description = "Stop versturen van berichten naar ISC")
    public void stopIscVersturen() throws SchedulerException {
        applicationContext.getBean(QUARTZ_SCHEDULER, Scheduler.class).standby();
    }

    /* *************************************************************************************** */
    /* *************************************************************************************** */
    /* *************************************************************************************** */

    /**
     * @return is verwerking gestart.
     */
    @ManagedAttribute(description = "Ontvangen van berichten naar ISC")
    public boolean isIscOntvangenGestart() {
        return applicationContext.getBean(INBOUND_LISTENER, DefaultMessageListenerContainer.class).isRunning();
    }

    /**
     * Start verwerking.
     */
    @ManagedOperation(description = "Start ontvangen ISC berichten")
    public void startIscOntvangen() {
        applicationContext.getBean(INBOUND_LISTENER, DefaultMessageListenerContainer.class).start();
    }

    /**
     * Stop verwerking.
     */
    @ManagedOperation(description = "Stop ontvangen ISC berichten")
    public void stopIscOntvangen() {
        applicationContext.getBean(INBOUND_LISTENER, DefaultMessageListenerContainer.class).stop();
    }

    /**
     * @return het aantal verwerkers.
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor ISC berichten")
    public int getAantalIscOntvangenVerwerkers() {
        return applicationContext.getBean(INBOUND_LISTENER, DefaultMessageListenerContainer.class).getMaxConcurrentConsumers();
    }

    /**
     * Zet het aantal verwerkers.
     * @param aantal het aantal te zetten verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor ISC berichten")
    public void setAantalIscOntvangenVerwerkers(final int aantal) {
        applicationContext.getBean(INBOUND_LISTENER, DefaultMessageListenerContainer.class).setMaxConcurrentConsumers(aantal);
    }

}
