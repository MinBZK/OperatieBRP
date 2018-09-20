/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * File based mailbox.
 */
public final class FileMailbox extends AbstractMailbox implements Mailbox {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String SEPARATOR = ",";
    private static final int INDEX_ORIGINATOR = 0;
    private static final int INDEX_MSSEQUENCENR = 1;
    private static final int INDEX_STATUS = 2;
    private static final int INDEX_MESSAGE_ID = 3;
    private static final int INDEX_CROSS_REFERENCE = 4;
    private static final int INDEX_MESG = 5;
    private static final int AANTAL_VALUES = 6;

    private static final String PROPERTY_STATUS = "status";
    private static final String PROPERTY_PASSWORD = "password";

    private static final int LOCK_SLEEP_TIME = 1000;
    private static final int LOCK_RETRIES = 25;

    private final File lockFile;
    private final File configFile;
    private final File dataFile;

    private String sessionId;

    /**
     * Constructor.
     *
     * @param factory
     *            De Mailboxfactory
     * @param baseDirectory
     *            base directory
     * @param mailboxnr
     *            mailboxnr
     */
    public FileMailbox(final MailboxFactory factory, final File baseDirectory, final String mailboxnr) {
        super(factory, mailboxnr);
        lockFile = new File(baseDirectory, mailboxnr + ".lock");
        configFile = new File(baseDirectory, mailboxnr + ".config");
        dataFile = new File(baseDirectory, mailboxnr + ".csv");
    }

    @Override
    public void open() throws MailboxException {
        acquireLock();
        readConfig();
        readEntries();
    }

    @Override
    public void save() throws MailboxException {
        checkLock();
        writeEntries();
        writeConfig();
    }

    @Override
    public void close() {
        try {
            checkLock();
            releaseLock();
        } catch (final MailboxException e) {
            LOGGER.error("Lock niet correct bij afsluiten mailbox.", e);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void readEntries() throws MailboxException {
        LOGGER.debug("Reading data for mailbox {} ({}; {} bytes).", new Object[] {getMailboxnr(), dataFile.getAbsolutePath(), dataFile.length() });

        final SortedMap<Integer, MailboxEntry> entries = new TreeMap<>();
        if (dataFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
                String line = reader.readLine();
                while (line != null) {
                    // Skip empty lines
                    if (!line.trim().isEmpty()) {
                        final String[] values = line.split(SEPARATOR, AANTAL_VALUES);
                        if (values.length != AANTAL_VALUES) {
                            LOGGER.error("Line in mailbox is invalid (wrong number of values): {}", line);
                        } else {
                            final MailboxEntry entry = new MailboxEntry();
                            entry.setOriginatorOrRecipient(unescape(values[INDEX_ORIGINATOR]));
                            entry.setMsSequenceNr(Integer.parseInt(values[INDEX_MSSEQUENCENR]));
                            entry.setStatus(Integer.parseInt(values[INDEX_STATUS]));
                            entry.setMessageId(unescape(values[INDEX_MESSAGE_ID]));
                            entry.setCrossReference(unescape(values[INDEX_CROSS_REFERENCE]));
                            entry.setMesg(unescape(values[INDEX_MESG]));

                            entries.put(entry.getMsSequenceId(), entry);
                        }
                    }

                    line = reader.readLine();
                }
            } catch (final IOException e) {
                throw new MailboxException("Kan mailbox entries niet lezen.", e);
            }
        }
        LOGGER.debug("Read data for mailbox {}; {} entries", getMailboxnr(), entries.size());

        setEntries(entries);
    }

    private void writeEntries() throws MailboxException {
        LOGGER.debug("writing data for mailbox {}; {} entries ({}).", new Object[] {getMailboxnr(), getEntries().size(), dataFile.getAbsolutePath(), });
        try (Writer writer = new FileWriter(dataFile, false)) {
            for (final MailboxEntry entry : getEntries().values()) {
                final StringBuilder line = new StringBuilder();
                line.append(escape(entry.getOriginatorOrRecipient())).append(SEPARATOR);
                line.append(entry.getMsSequenceId()).append(SEPARATOR);
                line.append(entry.getStatus()).append(SEPARATOR);
                line.append(escape(entry.getMessageId())).append(SEPARATOR);
                line.append(escape(entry.getCrossReference())).append(SEPARATOR);
                line.append(escape(entry.getMesg()));
                line.append("\n");
                writer.write(line.toString());

            }
        } catch (final IOException e) {
            throw new MailboxException("Kan mailbox entries niet schrijven.", e);
        }
    }

    /* ************************************************************************************************************* */

    private String escape(final String input) {
        return StringEscapeUtils.escapeJava(input);
    }

    private String unescape(final String input) {
        return StringEscapeUtils.unescapeJava(input);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void readConfig() throws MailboxException {
        if (configFile.exists()) {
            final Properties properties = new Properties();
            try (Reader reader = new FileReader(configFile)) {
                properties.load(reader);
            } catch (final IOException e) {
                throw new MailboxException("Kan mailbox configuratie niet lezen", e);
            }

            setStatus(Integer.parseInt(properties.getProperty(PROPERTY_STATUS)));
            setPassword(properties.getProperty(PROPERTY_PASSWORD));
        } else {
            setStatus(STATUS_OPEN);
            setPassword("");
        }
    }

    private void writeConfig() throws MailboxException {
        final Properties properties = new Properties();
        properties.setProperty(PROPERTY_STATUS, Integer.toString(getStatus()));
        properties.setProperty(PROPERTY_PASSWORD, getPassword());

        try (final Writer writer = new FileWriter(configFile, false)) {
            properties.store(writer, "Mailbox configuratie");
        } catch (final IOException e) {
            throw new MailboxException("Kan mailbox configuratie niet schrijven", e);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void acquireLock() throws MailboxException {
        sessionId = UUID.randomUUID().toString();

        // lock file check en maak
        int retryCount = 0;
        while (lockFile.exists()) {
            LOGGER.debug("Mailbox is gelocked.");
            retryCount++;

            if (retryCount > LOCK_RETRIES) {
                throw new MailboxException("Kan mailbox lock niet verkrijgen.");
            }

            try {
                Thread.sleep(LOCK_SLEEP_TIME);
            } catch (final InterruptedException ex) {
                // Ignore
                LOGGER.warn("Lock wait sleep interrupted", ex);
            }
        }

        // lock file is weg, maak een nieuwe lockfile
        LOGGER.debug("Mailbox lock aanmaken");
        try {
            try (FileWriter writer = new FileWriter(lockFile, false)) {
                writer.write(sessionId);
            }
        } catch (final IOException e) {
            throw new MailboxException("Kan mailbox lockfile niet aanmaken.", e);
        }
    }

    private void checkLock() throws MailboxException {
        if (!lockFile.exists()) {
            throw new MailboxException("Mailbox lockfile bestaat niet?");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(lockFile))) {
            final String lockFileSessionId = reader.readLine();

            if (!lockFileSessionId.equals(sessionId)) {
                throw new MailboxException("Mailbox lockfile session id is niet correct?");
            }
        } catch (final
            IOException
            | NullPointerException e)
        {
            throw new MailboxException("Mailbox lockfile kan niet worden gelezen.");
        }
    }

    private void releaseLock() {
        if (!lockFile.delete()) {
            LOGGER.error("Mailbox lockfile kan niet worden verwijderd?");
        }
    }

}
