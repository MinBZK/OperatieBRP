/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.FileUtils;

/**
 * Factory for file based mailboxes.
 */
public final class FileMailboxFactory implements MailboxFactory {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String DEFAULT_DATA_LOCATION = "data";
    private static final String PROPERTY_MS_SEQUENCE_NUMBER = "msSequenceNumber";
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final File dataDirectory;
    private final File configFile;
    private AtomicInteger msSequenceNumber;

    /**
     * Constructor.
     *
     * @throws MailboxException
     *             wordt gegooid als de benodigde directory niet bestaat of aangemaakt kan worden.
     */
    public FileMailboxFactory() throws MailboxException {
        this(DEFAULT_DATA_LOCATION);

    }

    /**
     * Constructor.
     *
     * @param dataLocation
     *            data files locatie
     * @throws MailboxException
     *             wordt gegooid als de benodigde directory niet bestaat of aangemaakt kan worden.
     */
    public FileMailboxFactory(final String dataLocation) throws MailboxException {
        LOG.info("Starting FILE mailbox factory...");
        dataDirectory = new File(dataLocation);
        initDataDirectory();
        configFile = new File(dataDirectory, "factory.config");
        readConfig();
    }

    private void initDataDirectory() throws MailboxException {
        try {
            FileUtils.forceMkdir(dataDirectory);
        } catch (final IOException e) {
            throw new MailboxException(String.format("Kon data directory (%s) niet aanmaken", getPath(dataDirectory)), e);
        }
    }

    private String getPath(final File directory) {
        try {
            return directory.getCanonicalPath();
        } catch (final IOException e) {
            return directory.getPath();
        }
    }

    @Override
    public void deleteAll() throws MailboxException {
        try {
            FileUtils.deleteDirectory(dataDirectory);
        } catch (final IOException e) {
            throw new MailboxException("Kon data directory niet verwijderen.", e);
        }
        initDataDirectory();
        writeConfig();
    }

    @Override
    public Mailbox getMailbox(final String mailboxnr) {
        return new FileMailbox(this, dataDirectory, mailboxnr);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory#getNextMsSequenceNr()
     */
    @Override
    public int getNextMsSequenceNr() {
        final int result = msSequenceNumber.getAndIncrement();
        writeConfig();
        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void readConfig() {
        if (configFile.exists()) {
            final Properties properties = new Properties();
            try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), EncodingConstants.CHARSET))) {
                properties.load(reader);

                msSequenceNumber = new AtomicInteger(Integer.parseInt(properties.getProperty(PROPERTY_MS_SEQUENCE_NUMBER)));

            } catch (final IOException e) {
                LOGGER.error("Fout tijdens lezen configuratie", e);
            }
        }

        if (msSequenceNumber == null) {
            msSequenceNumber = new AtomicInteger(1);
        }
    }

    private void writeConfig() {
        final Properties properties = new Properties();
        properties.setProperty(PROPERTY_MS_SEQUENCE_NUMBER, Integer.toString(msSequenceNumber.get()));

        try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), EncodingConstants.CHARSET))) {
            properties.store(writer, "Factory configuratie");
            writer.flush();
        } catch (final IOException e) {
            LOGGER.error("Fout tijdens schrijven configuratie", e);
        }
    }
}
