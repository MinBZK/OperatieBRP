/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.jms.JMSException;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.util.common.Version;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.spd.impl.SslConnectionFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Voisc main.
 */
public final class VoiscMain {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static String[] configuratieFiles = new String[] {"classpath:voisc-runtime.xml" };
    private static ConfigurableApplicationContext context;
    private static final KeepAlive KEEPALIVE = new KeepAlive();

    private VoiscMain() {
        // Niet instantieerbaar.
    }

    /**
     * @param argv
     *            argumenten om de Voa mee te configureren. Wordt nu niet gebruikt
     * @throws IOException
     *             wordt gegooid als Voisc de password file niet kan lezen.
     * @throws SchedulerException
     *             bij scheduler fouten
     * @throws JMSException
     *             bij jms fouten
     */
    public static void main(final String[] argv) throws IOException, SchedulerException, JMSException {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.voisc", "migr-voisc-runtime");
        LOG.info(
            FunctioneleMelding.VOISC_STARTEN_APPLICATIE,
            "Starten applicatie ({}) ...\nComponenten:\n{}",
            version.toString(),
            version.toDetailsString());
        // Kickstart the VOA.
        String host = null;
        Integer port = null;

        switch (argv.length) {
            case 0:
                // Then defaults are used
                break;
            case 1:
                host = argv[0];
                break;
            case 2:
                host = argv[0];
                port = Integer.valueOf(argv[1]);
                break;
            default:
                LOG.error(FunctioneleMelding.VOISC_ONGELDIGE_PARAMETERS, "Usage: nl.bzk.isc.voisc.VoiscMain [<host> [<port>]]");
                throw new IllegalArgumentException("Ongeldige parameters");
        }

        context = new ClassPathXmlApplicationContext(configuratieFiles);
        context.registerShutdownHook();

        final StartConfiguration config = new StartConfiguration();
        context.getAutowireCapableBeanFactory().autowireBean(config);

        LOG.info(FunctioneleMelding.VOISC_CONFIGUREREN_SSL);
        configureSsl(host, port);
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
        new Thread(KEEPALIVE).start();
        LOG.info(
            FunctioneleMelding.VOISC_APPLICATIE_CORRECT_GESTART,
            "Applicatie ({}) gestart om {}",
            version.toString(),
            Calendar.getInstance().getTime());
    }

    /**
     * Stop de applicatie.
     */
    public static void stop() {
        LOG.debug("Stopping applicatie ...");
        context.close();
        LOG.info("Applicatie gestopt ...");
        KEEPALIVE.stop();
    }

    /**
     * Geef de context.
     *
     * @return de context
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    private static void configureSsl(final String host, final Integer port) throws IOException {
        final SslConnectionFactory connectionFactory = context.getBean(SslConnectionFactory.class);
        final char[] pwdArray = VoiscMain.getPasswd();
        connectionFactory.setPassword(new String(pwdArray));
        if (host != null) {
            connectionFactory.setHostname(host);
        }
        if (port != null) {
            connectionFactory.setPortNumber(port);
        }

    }

    /**
     * Geef de waarde van passwd.
     *
     * @return passwd
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static char[] getPasswd() throws IOException {
        // readPassword method call.
        char[] pwdArray = new char[] {};
        final File voiscPwd = new File(".voiscPwd");
        if (voiscPwd.exists()) {
            final BufferedReader br = new BufferedReader(new FileReader(voiscPwd));
            final String pwd = br.readLine();
            if (pwd != null) {
                pwdArray = pwd.toCharArray();
            }
            br.close();
        } else {
            final Console console = System.console();
            final String pwdPrompt = "%1$26s";

            if (console == null) {
                LOG.error("Console Object is not available.");
                throw new IllegalArgumentException("Geen wachtwoord");
            } else {
                pwdArray = console.readPassword(pwdPrompt, "Voer het SSL wachtwoord in: ");
            }
        }
        return pwdArray;
    }

    private static void checkMailbox() {
        LOG.info(FunctioneleMelding.VOISC_CONNECTIE_MAILBOX_TESTEN, "Controleren SSL verbinding ...");
        final VoiscMailbox voiscMailbox = context.getBean(VoiscMailbox.class);
        try {
            voiscMailbox.connectToMailboxServer();
            voiscMailbox.logout();
        } catch (final VoiscMailboxException ce) {
            LOG.error("ConnectionException bij opzetten van verbinding naar mailbox: ", ce);
            throw new IllegalArgumentException("Kan geen verbinding maken met de mailbox", ce);
        }
    }

    private static void toonMailboxen() {
        LOG.info(FunctioneleMelding.VOISC_CONFIGURATIE_MAILBOX_TESTEN, "Controleren mailbox configuratie ...");
        final MailboxConfiguratie mailboxConfiguratie = context.getBean(MailboxConfiguratie.class);
        final Set<Mailbox> mailboxen = mailboxConfiguratie.bepaalMailboxen();
        final Set<Mailbox> centraleMailboxen = filterMailboxen(mailboxen, "C");
        final Set<Mailbox> gemeenteMailboxen = filterMailboxen(mailboxen, "G");
        final Set<Mailbox> afnemerMailboxen = filterMailboxen(mailboxen, "A");

        LOG.info("Deze VOISC configuratie verwerkt {} mailboxen (Let op: dit kan dynamisch wijzigen).", mailboxen.size());
        LOG.info("Centrale mailboxen ({}): {}", centraleMailboxen.size(), listMailboxen(centraleMailboxen));
        LOG.info("Gemeente mailboxen ({}): {}", gemeenteMailboxen.size(), listMailboxen(gemeenteMailboxen));
        LOG.info("Afnemer mailboxen ({}): {}", afnemerMailboxen.size(), listMailboxen(afnemerMailboxen));
    }

    private static Set<Mailbox> filterMailboxen(final Set<Mailbox> mailboxen, final String type) {
        final Set<Mailbox> result = new TreeSet<>();
        for (final Mailbox mailbox : mailboxen) {
            if (type.equals(mailbox.getInstantietype())) {
                result.add(mailbox);
            }
        }
        return result;
    }

    private static String listMailboxen(final Set<Mailbox> mailboxen) {
        final StringBuilder result = new StringBuilder();
        for (final Mailbox mailbox : mailboxen) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(mailbox.getMailboxnr());
        }

        return result.toString();
    }

    /**
     * TEST: Set configuratiebestanden.
     *
     * @param inputConfiguratieFiles
     *            configuatiebestanden
     */
    static void setConfiguratieFiles(final String[] inputConfiguratieFiles) {
        VoiscMain.configuratieFiles = Arrays.copyOf(inputConfiguratieFiles, inputConfiguratieFiles.length);
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
