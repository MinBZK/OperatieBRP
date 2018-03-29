/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
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
@ManagedResource(objectName = JMXConstanten.OBJECT_NAME, description = "JMX Service voor Synchronisatie")
public final class JMXImpl implements JMX, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String SYNC_CONTAINER = "iscBerichtListenerContainer";
    private static final String ARCHIVERING_CONTAINER = "archiveringBerichtListenerContainer";
    private static final String PARTIJ_CONTAINER = "partijRegisterContainer";
    private static final String AFNEMERSINDICATIE_CONTAINER = "jmsContainerAfnemersindicaties";
    private static final String TOEVALLIGE_GEBEURTENISSEN_CONTAINER = "jmsContainerToevalligeGebeurtenissen";
    private static final String LEVERINGEN_CONTAINER = "jmsContainerLeveringen";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    @ManagedOperation(description = "Synchronisatie afsluiten.")
    public void afsluiten() {
        LOGGER.info("Afsluiten Synchronisatie via JMX");
        applicationContext.getBean(Main.BEAN_NAME, Main.class).stop();
    }

    /**
     * @return is routering gestart.
     */
    @ManagedAttribute(description = "Applicatie actief")
    public boolean isGestart() {
        return true;
    }

    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    /**
     * @return is sync verzoek verwerking gestart
     */
    @ManagedAttribute(description = "Verwerken van synchronisatie verzoek berichten.")
    public boolean isSyncGestart() {
        return isContainerGestart(SYNC_CONTAINER);
    }

    /**
     * Start sync verzoek verwerking.
     */
    @ManagedOperation(description = "Start verwerken van synchronisatie verzoek berichten.")
    public void startSync() {
        startContainer(SYNC_CONTAINER);
    }

    /**
     * Stop sync verzoek verwerking.
     */
    @ManagedOperation(description = "Stop verwerken van synchronisatie verzoek berichten.")
    public void stopSync() {
        stopContainer(SYNC_CONTAINER);
    }

    /**
     * Geef aantal sync verwerkers.
     * @return aantal sync verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor synchronisatie verzoek berichten")
    public int getAantalSyncVerwerkers() {
        return getMaxConcurrentConsumers(SYNC_CONTAINER);
    }

    /**
     * Zet aantal sync verwerkers.
     * @param aantal aantal sync verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor synchronisatie verzoek berichten")
    public void setAantalSyncVerwerkers(final int aantal) {
        setMaxConcurrentConsumers(SYNC_CONTAINER, aantal);
    }

    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    /**
     * Is verwerking gestart.
     * @return is verwerking gestart
     */
    @ManagedAttribute(description = "Verwerken van archivering verzoek berichten.")
    public boolean isArchiveringGestart() {
        return isContainerGestart(ARCHIVERING_CONTAINER);
    }

    /**
     * Start verwerking.
     */
    @ManagedOperation(description = "Start verwerken van archivering verzoek berichten.")
    public void startArchivering() {
        startContainer(ARCHIVERING_CONTAINER);
    }

    /**
     * Stop verwerking.
     */
    @ManagedOperation(description = "Stop verwerken van archivering verzoek berichten.")
    public void stopArchivering() {
        stopContainer(ARCHIVERING_CONTAINER);
    }

    /**
     * Geef aantal sync verwerkers.
     * @return aantal sync verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor archivering verzoek berichten")
    public int getAantalArchiveringVerwerkers() {
        return getMaxConcurrentConsumers(ARCHIVERING_CONTAINER);
    }

    /**
     * Zet aantal verwerkers.
     * @param aantal aantal verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor archivering verzoek berichten")
    public void setAantalArchiveringVerwerkers(final int aantal) {
        setMaxConcurrentConsumers(ARCHIVERING_CONTAINER, aantal);
    }

    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    /**
     * Is verwerking gestart.
     * @return is verwerking gestart
     */
    @ManagedAttribute(description = "Verwerken van partij register verzoek berichten.")
    public boolean isPartijRegisterGestart() {
        return isContainerGestart(PARTIJ_CONTAINER);
    }

    /**
     * Start verwerking.
     */
    @ManagedOperation(description = "Start verwerken van partij register verzoek berichten.")
    public void startPartijRegister() {
        startContainer(PARTIJ_CONTAINER);
    }

    /**
     * Stop verwerking.
     */
    @ManagedOperation(description = "Stop verwerken van partij register verzoek berichten.")
    public void stopPartijRegister() {
        stopContainer(PARTIJ_CONTAINER);
    }


    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    /**
     * Is verwerking gestart.
     * @return is verwerking gestart
     */
    @ManagedAttribute(description = "Verwerken van afnemersindicaties antwoord berichten.")
    public boolean isAfnemersindicatiesGestart() {
        return isContainerGestart(AFNEMERSINDICATIE_CONTAINER);
    }

    /**
     * Start verwerking.
     */
    @ManagedOperation(description = "Start verwerken van afnemersindicaties antwoord berichten.")
    public void startAfnemersindicaties() {
        startContainer(AFNEMERSINDICATIE_CONTAINER);
    }

    /**
     * Stop verwerking.
     */
    @ManagedOperation(description = "Stop verwerken van afnemersindicaties antwoord berichten.")
    public void stopAfnemersindicaties() {
        stopContainer(AFNEMERSINDICATIE_CONTAINER);
    }

    /**
     * Geef aantal sync verwerkers.
     * @return aantal sync verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor afnemersindicaties antwoord berichten")
    public int getAantalAfnemersindicatiesVerwerkers() {
        return getMaxConcurrentConsumers(AFNEMERSINDICATIE_CONTAINER);
    }

    /**
     * Zet aantal verwerkers.
     * @param aantal aantal verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor afnemersindicaties antwoord berichten")
    public void setAantalAfnemersindicatiesVerwerkers(final int aantal) {
        setMaxConcurrentConsumers(AFNEMERSINDICATIE_CONTAINER, aantal);
    }

    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    /**
     * Is verwerking gestart.
     * @return is verwerking gestart
     */
    @ManagedAttribute(description = "Verwerken van toevallige gebeurtenissen antwoord berichten.")
    public boolean isToevalligeGebeurtenissenGestart() {
        return isContainerGestart(TOEVALLIGE_GEBEURTENISSEN_CONTAINER);
    }

    /**
     * Start verwerking.
     */
    @ManagedOperation(description = "Start verwerken van toevallige gebeurtenissen antwoord berichten.")
    public void startToevalligeGebeurtenissen() {
        startContainer(TOEVALLIGE_GEBEURTENISSEN_CONTAINER);
    }

    /**
     * Stop verwerking.
     */
    @ManagedOperation(description = "Stop verwerken van toevallige gebeurtenissen antwoord berichten.")
    public void stopToevalligeGebeurtenissen() {
        stopContainer(TOEVALLIGE_GEBEURTENISSEN_CONTAINER);
    }

    /**
     * Geef aantal sync verwerkers.
     * @return aantal sync verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor toevallige gebeurtenissen antwoord berichten")
    public int getAantalToevalligeGebeurtenissenVerwerkers() {
        return getMaxConcurrentConsumers(TOEVALLIGE_GEBEURTENISSEN_CONTAINER);
    }

    /**
     * Zet aantal verwerkers.
     * @param aantal aantal verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor toevallige gebeurtenissen antwoord berichten")
    public void setAantalToevalligeGebeurtenissenerwerkers(final int aantal) {
        setMaxConcurrentConsumers(TOEVALLIGE_GEBEURTENISSEN_CONTAINER, aantal);
    }

    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    /**
     * Is verwerking gestart.
     * @return is verwerking gestart
     */
    @ManagedAttribute(description = "Verwerken van levering berichten.")
    public boolean isLeveringenGestart() {
        return isContainerGestart(LEVERINGEN_CONTAINER);
    }

    /**
     * Start verwerking.
     */
    @ManagedOperation(description = "Start verwerken van levering berichten.")
    public void startLeveringen() {
        startContainer(LEVERINGEN_CONTAINER);
    }

    /**
     * Stop verwerking.
     */
    @ManagedOperation(description = "Stop verwerken van levering berichten.")
    public void stopLeveringen() {
        stopContainer(LEVERINGEN_CONTAINER);
    }

    /**
     * Geef aantal sync verwerkers.
     * @return aantal sync verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor levering berichten")
    public int getAantalLeveringenVerwerkers() {
        return getMaxConcurrentConsumers(LEVERINGEN_CONTAINER);
    }

    /**
     * Zet aantal verwerkers.
     * @param aantal aantal verwerkers
     */
    @ManagedAttribute(description = "Maximum aantal verwerkers voor levering berichten")
    public void setAantalLeveringenVerwerkers(final int aantal) {
        setMaxConcurrentConsumers(LEVERINGEN_CONTAINER, aantal);
    }

    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */
    /*
     * *********************************************************************************************
     * ***********
     */

    private boolean isContainerGestart(final String beanName) {
        return applicationContext.getBean(beanName, DefaultMessageListenerContainer.class).isRunning();
    }

    private void startContainer(final String beanName) {
        try {
            final DefaultMessageListenerContainer container = applicationContext.getBean(beanName, DefaultMessageListenerContainer.class);
            container.start();
        } catch (final NoSuchBeanDefinitionException e) {
            throw new IllegalStateException("Kan container niet starten; container niet aanwezig.", e);
        }
    }

    private void stopContainer(final String beanName) {
        try {
            final DefaultMessageListenerContainer container = applicationContext.getBean(beanName, DefaultMessageListenerContainer.class);
            container.stop();
        } catch (final NoSuchBeanDefinitionException e) {
            throw new IllegalStateException("Kan container niet stoppen; container niet aanwezig.", e);
        }
    }

    private Integer getMaxConcurrentConsumers(final String beanName) {
        try {
            final DefaultMessageListenerContainer container = applicationContext.getBean(beanName, DefaultMessageListenerContainer.class);
            return container.getMaxConcurrentConsumers();
        } catch (final NoSuchBeanDefinitionException e) {
            LOGGER.debug("Bean niet gevonden", e);
            return null;
        }
    }

    private void setMaxConcurrentConsumers(final String beanName, final int aantal) {
        try {
            final DefaultMessageListenerContainer container = applicationContext.getBean(beanName, DefaultMessageListenerContainer.class);
            container.setMaxConcurrentConsumers(aantal);
        } catch (final NoSuchBeanDefinitionException e) {
            throw new IllegalStateException("Kan container niet configureren; container niet aanwezig.", e);
        }

    }

}
