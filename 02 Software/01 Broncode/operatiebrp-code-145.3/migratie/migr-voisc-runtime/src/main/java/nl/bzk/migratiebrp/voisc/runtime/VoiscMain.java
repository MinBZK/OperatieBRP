/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Calendar;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Voisc main.
 */
public final class VoiscMain {
    static final String BEAN_NAME = "main";
    private static final Logger LOG = LoggerFactory.getLogger();
    private final PropertySource<?> configuration;
    private final KeepAlive keepAlive = new KeepAlive();
    private GenericXmlApplicationContext context;

    /**
     * Constructor.
     * @param configuration configuratie
     */
    public VoiscMain(final PropertySource<?> configuration) {
        this.configuration = configuration;
        if (this.configuration == null) {
            throw new IllegalArgumentException("Configuratie verplicht");
        }
    }

    /**
     * Start de applicatie.
     * @param args argumenten (ongebruikt)
     */
    public static void main(final String[] args) {
        new VoiscMain(new PropertiesPropertySource("config", new ClassPathResource("/voisc-runtime.properties"))).start();
    }

    /**
     * Start.
     */
    public void start() {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.voisc", "migr-voisc-runtime");
        LOG.info(FunctioneleMelding.VOISC_STARTEN_APPLICATIE, "Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(),
                version.toDetailsString());

        final DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton(BEAN_NAME, this);
        try (final GenericApplicationContext parentContext = new GenericApplicationContext(parentBeanFactory)) {

            parentContext.refresh();

            context = new GenericXmlApplicationContext();
            context.setParent(parentContext);
            context.load("classpath:voisc-runtime.xml");
            context.getEnvironment().getPropertySources().addLast(configuration);
            context.refresh();

            // Try to shutdown neatly even if stop() is not called
            context.registerShutdownHook();

            final StartConfiguration config = new StartConfiguration();
            context.getAutowireCapableBeanFactory().autowireBean(config);

            LOG.info(FunctioneleMelding.VOISC_CONFIGUREREN_SSL);
            if (config.checkMailbox) {
                checkMailbox();
            } else {
                LOG.info(FunctioneleMelding.VOISC_CONNECTIE_MAILBOX_NIET_GETEST);
            }

            // Starten registers
            @SuppressWarnings("rawtypes")
            final Map<String, RegisterService> registerServices = context.getBeansOfType(RegisterService.class);
            for (final RegisterService<?> registerService : registerServices.values()) {
                registerService.refreshRegister();
            }

            if (config.checkConfig) {
                toonMailboxen();
            } else {
                LOG.info(FunctioneleMelding.VOISC_CONFIGURATIE_MAILBOX_NIET_GETEST);
            }

            LOG.info(FunctioneleMelding.VOISC_STARTEN_JOBS);
            final Scheduler scheduler = context.getBean("scheduler", Scheduler.class);
            scheduler.start();

            LOG.info(FunctioneleMelding.VOISC_STARTEN_QUEUELISTENERS);
            final DefaultMessageListenerContainer voiscBerichtListenerContainer =
                    context.getBean("voiscBerichtListenerContainer", DefaultMessageListenerContainer.class);
            voiscBerichtListenerContainer.start();

            // Start keep-alive
            new Thread(keepAlive).start();
            LOG.info(FunctioneleMelding.VOISC_APPLICATIE_CORRECT_GESTART, "Applicatie ({}) gestart om {}", version.toString(),
                    Calendar.getInstance().getTime());
        } catch (final SchedulerException e) {
            LOG.error("Fout tijdens uitvoeren", e);
        }
    }

    /**
     * Stop de applicatie.
     */
    public void stop() {
        LOG.debug("Stopping applicatie ...");
        if (context != null) {
            context.close();
        }
        LOG.info("Applicatie gestopt ...");
        keepAlive.stop();
    }

    private void checkMailbox() {
        LOG.info(FunctioneleMelding.VOISC_CONNECTIE_MAILBOX_TESTEN, "Controleren SSL verbinding ...");
        final VoiscMailbox voiscMailbox = context.getBean(VoiscMailbox.class);
        try (final Connection connection = voiscMailbox.connectToMailboxServer()) {
            voiscMailbox.logout(connection);
        } catch (final VoiscMailboxException ce) {
            LOG.error("ConnectionException bij opzetten van verbinding naar mailbox: ", ce);
            throw new IllegalArgumentException("Kan geen verbinding maken met de mailbox", ce);
        }
    }

    private void toonMailboxen() {
        LOG.info(FunctioneleMelding.VOISC_CONFIGURATIE_MAILBOX_TESTEN, "Controleren mailbox configuratie ...");
        final MailboxConfiguratie mailboxConfiguratie = context.getBean(MailboxConfiguratie.class);
        LOG.info("Deze VOISC configuration verwerkt de volgende mailboxen:\n{}", mailboxConfiguratie.toonMailboxen());
    }

    /**
     * Configuration bean.
     */
    private static class StartConfiguration {
        @Value("${voisc.start.check.mailbox:true}")
        private boolean checkMailbox;

        @Value("${voisc.start.check.config:true}")
        private boolean checkConfig;

    }
}
