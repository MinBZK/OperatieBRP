/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.IOException;
import java.util.List;
import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * Abstract Sch based kanaal.
 */
public abstract class AbstractSchKanaal extends AbstractKanaal implements InitializingBean, Kanaal {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final com.jcraft.jsch.Logger JSCH_LOG = new JschLogger(LOG);

    private static final int SESSION_CONNECT_TIMEOUT = 30000;

    private String naam;
    private String host;
    private String username;
    private String password;

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public final void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Zet de waarde van host.
     *
     * @param host
     *            host
     */
    public final void setHost(final String host) {
        this.host = host;
    }

    /**
     * Zet de waarde van username.
     *
     * @param username
     *            username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Zet de waarde van password.
     *
     * @param password
     *            password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public final void afterPropertiesSet() {
        if (naam == null || "".equals(naam)) {
            throw new IllegalArgumentException("Property 'naam' moet zijn gezet.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
     */
    @Override
    public final String getKanaal() {
        return naam;
    }

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        executeInSession(bericht);
    }

    private void executeInSession(final Bericht bericht) throws KanaalException {
        LOG.info("Starting secure communications");
        final JSch jsch = new JSch();
        JSch.setLogger(JSCH_LOG);
        Session session = null;
        try {
            LOG.info("Creating session");
            session = jsch.getSession(username, host);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            LOG.info("Connecting session...");
            // Normale instelling:
            // gssapi-with-mic,publickey,keyboard-interactive,password
            // Maar het kerberos (gssapi-with-mic) gaat stuk op jenkins
            session.setConfig("PreferredAuthentications", "keyboard-interactive");
            session.setTimeout(SESSION_CONNECT_TIMEOUT);
            session.connect(SESSION_CONNECT_TIMEOUT);
            LOG.info("Session connected.");

            doInSession(session, bericht);

        } catch (final
            IOException
            | JSchException e)
        {
            LOG.info("Error encountered during session", e);
            throw new KanaalException("Kon SSH niet uitvoeren", e);
        } finally {
            if (session != null) {
                LOG.info("Disconnecting session...");
                session.disconnect();
            }
        }
        LOG.info("Secure communications completed.");
    }

    @Override
    public final List<Bericht> naTestcase(final TestCasusContext testcasus) {
        final File naTestCaseFile = new File(testcasus.getInputDirectory(), "9999-uit-sch.naTestCase");

        if (naTestCaseFile.exists() && naTestCaseFile.isFile() && naTestCaseFile.canRead()) {
            final TestBericht testBericht = new TestBericht(naTestCaseFile, testcasus.getOutputDirectory());
            final Bericht bericht = new Bericht(testBericht);
            bericht.setInhoud(testBericht.getInhoud());
            try {
                executeInSession(bericht);
            } catch (final KanaalException e) {
                LOG.error("Fout opgetreden bij naTestCase Sch-Kanaal:\n" + ExceptionUtils.getStackTrace(e));
            }
        }

        return null;
    }

    /**
     * Execute in the JSch session.
     *
     * @param session
     *            JSch session
     * @param bericht
     *            bericht
     * @throws IOException
     *             on IO exceptions
     * @throws JSchException
     *             on Jsc exceptions
     * @throws KanaalException
     *             on other exceptions
     */
    protected abstract void doInSession(final Session session, final Bericht bericht) throws IOException, JSchException, KanaalException;

    /**
     * Port JSCH logging to migratie logger.
     */
    public static final class JschLogger implements com.jcraft.jsch.Logger {

        private final Logger log;

        /**
         * Constructor.
         *
         * @param log
         *            migratie logger
         */
        public JschLogger(final Logger log) {
            this.log = log;
        }

        @Override
        public boolean isEnabled(final int level) {
            return true;
        }

        @Override
        public void log(final int level, final String message) {
            log.info(message);
        }
    }

}
