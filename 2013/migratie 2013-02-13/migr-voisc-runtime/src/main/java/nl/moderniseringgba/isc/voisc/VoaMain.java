/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

import nl.moderniseringgba.isc.voisc.exceptions.ConnectionException;
import nl.moderniseringgba.isc.voisc.exceptions.SslPasswordException;
import nl.moderniseringgba.isc.voisc.mailbox.impl.SslConnectionImpl;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public final class VoaMain {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int SYS_EXIT_1 = 1;
    private static final int SYS_EXIT_2 = 2;
    private static final int SYS_EXIT_3 = 3;
    private static final int SYS_EXIT_4 = 4;
    private static final int SYS_EXIT_5 = 5;
    private static final int SYS_EXIT_6 = 6;

    private static String[] configuratieFiles = new String[] { "classpath:voisc.xml", "classpath:voisc-jms.xml",
            "classpath:voisc-db.xml", "classpath:voice-db-cache.xml", "classpath:voisc-datasource.xml",
            "classpath:isc-db.xml", };

    /**
     * 
     */
    private VoaMain() {
    }

    /**
     * @param argv
     *            argumenten om de Voa mee te configureren. Wordt nu niet gebruikt
     * @throws IOException
     *             wordt gegooid als Voisc de password file niet kan lezen.
     */
    public static void main(final String[] argv) throws IOException {
        // Kickstart the VOA.
        String host = null;
        Integer port = null;
        try {
            LOG.info("Starting application context");
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
                    System.err.println("Usage: nl.moderniseringgba.isc.voisc.VoaMain [<host> [<port>]]"); // NOSONAR
                    System.exit(SYS_EXIT_1);
            }
            final char[] pwdArray = getPasswd();

            final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(configuratieFiles);
            final SslConnectionImpl conn = (SslConnectionImpl) context.getBean("sslConnection");
            conn.setPassword(new String(pwdArray));
            if (host != null) {
                conn.setHostname(host);
            }
            if (port != null) {
                conn.setPortNumber(port);
            }

            checkProperties(context);
            checkMailbox(context);

            context.registerShutdownHook();
            try {
                final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                final JobDetail detail = (JobDetail) context.getBean("voaJob");
                final Trigger trigger = (CronTrigger) context.getBean("cronTrigger");
                scheduler.scheduleJob(detail, trigger);
                scheduler.start();
            } catch (final SchedulerException se) {
                LOG.error("Fout tijdens starten van de scheduling", se);
                exit(SYS_EXIT_3, context);
            }
            LOG.info("Application started op " + Calendar.getInstance().getTime());
        } catch (final BeansException e) {
            LOG.error("Error loading Voa: ", e);
            exit(SYS_EXIT_4, null);
        }
    }

    private static char[] getPasswd() throws IOException {
        final Console console = System.console();
        final String pwdPrompt = "%1$26s";

        // readPassword method call.
        char[] pwdArray = new char[] {};
        if (console == null) {
            final File voiscPwd = new File(".voiscPwd");
            if (voiscPwd.exists()) {
                final BufferedReader br = new BufferedReader(new FileReader(voiscPwd));
                final String pwd = br.readLine();
                pwdArray = pwd.toCharArray();
                br.close();
            } else {
                System.err.println("Console Object is not available.");// NOSONAR
                exit(SYS_EXIT_2, null);
            }
        } else {
            pwdArray = console.readPassword(pwdPrompt, "Voer het SSL wachtwoord in: ");
        }
        return pwdArray;
    }

    private static void checkMailbox(final ConfigurableApplicationContext context) {
        final VoaService voiscService = (VoaService) context.getBean("voiscService");
        try {
            voiscService.connectToMailboxServer();
            voiscService.logout();
        } catch (final SslPasswordException spe) {
            LOG.error("SSLPasswordException bij opzetten van verbinding naar mailbox: ", spe);
            exit(SYS_EXIT_5, context);
        } catch (final ConnectionException ce) {
            LOG.error("ConnectionException bij opzetten van verbinding naar mailbox: ", ce);
            exit(SYS_EXIT_5, context);
        }
    }

    // CHECKSTYLE:OFF Controle op bestaan van properties is nodig
    private static void checkProperties(final ConfigurableApplicationContext context) {
        final Properties properties = ((VoiscPropertyPlaceHolder) context.getBean("voiscProperties")).getProperties();
        final boolean propertiesOk =
                exists(properties.getProperty("voajob.cron.expression"))
                        && exists(properties.getProperty("toqueue.cron.expression"))
                        && exists(properties.getProperty("voisc.queue.host"))
                        && exists(properties.getProperty("voisc.queue.receivetimeout"))
                        && exists(properties.getProperty("voisc.queue.verzenden"))
                        && exists(properties.getProperty("voisc.queue.ontvangst"))
                        && exists(properties.getProperty("voisc.database.reconnects"))
                        && exists(properties.getProperty("voisc.mailbox.hostname"))
                        && exists(properties.getProperty("voisc.mailbox.portnumber"))
                        && exists(properties.getProperty("jdbc.driverClassName"))
                        && exists(properties.getProperty("jdbc.url"))
                        && exists(properties.getProperty("jdbc.username"))
                        && exists(properties.getProperty("jdbc.password"))
                        && exists(properties.getProperty("voisc.database.sleepTime"));
        if (!propertiesOk) {
            LOG.error("Error loading config properties from database.");
            exit(SYS_EXIT_6, context);
        }
    }

    // CHECKSTYLE:ON

    private static boolean exists(final String property) {
        return property != null && !property.isEmpty();
    }

    private static void exit(final int exitCode, final ConfigurableApplicationContext context) {
        if (context != null) {
            context.close();
        }
        System.exit(exitCode);

    }

    /**
     * TEST: Set configuratiebestanden.
     * 
     * @param inputConfiguratieFiles
     *            configuatiebestanden
     */
    static void setConfiguratieFiles(final String[] inputConfiguratieFiles) {
        VoaMain.configuratieFiles = Arrays.copyOf(inputConfiguratieFiles, inputConfiguratieFiles.length);
    }
}
