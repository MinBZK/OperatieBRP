/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

public class MailboxFactory {
    private static final String DATA_LOCATION = "data";
    private static final String MB_ACTIVE_LOCATION = DATA_LOCATION + "/active";
    private static final String MB_NOT_PRESENT_LOCATION = DATA_LOCATION + "/inactive";
    private static final String MB_TEMP_BLOCKED_LOCATION = DATA_LOCATION + "/tempBlocked";
    private static final String MB_ENTRIES_TEMPLATE = DATA_LOCATION + "/templateMailboxEntries.csv";

    private static final String IN_OUT_IN = "I";
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final File dataDirectory;
    private final File mbActiveDirectory;
    private final File mbNotPresentDirectory;
    private final File mbTempBlockedDirectory;

    public MailboxFactory() {
        // Controleer of de benodigde directories bestaan, maak ze anders aan

        dataDirectory = new File(DATA_LOCATION);
        mbActiveDirectory = new File(MB_ACTIVE_LOCATION);
        mbNotPresentDirectory = new File(MB_NOT_PRESENT_LOCATION);
        mbTempBlockedDirectory = new File(MB_TEMP_BLOCKED_LOCATION);

        if (!dataDirectory.exists()) {
            dataDirectory.mkdir();
        }
        if (!mbActiveDirectory.exists()) {
            mbActiveDirectory.mkdir();
        }
        if (!mbNotPresentDirectory.exists()) {
            mbNotPresentDirectory.mkdir();
        }
        if (!mbTempBlockedDirectory.exists()) {
            mbTempBlockedDirectory.mkdir();
        }
    }

    public final Mailbox loadMailbox(final String mailboxnr) {
        return this.loadMailbox(mailboxnr, true);
    }

    public final Mailbox loadMailbox(final String mailboxnr, final boolean useTemplate) {
        Mailbox mailbox = null;

        File file = getFileFromDisk(mbActiveDirectory, mailboxnr);
        if (file != null) {
            mailbox = readMailboxFromFile(file);
        }

        if (mailbox == null) {
            file = getFileFromDisk(mbTempBlockedDirectory, mailboxnr);
            if (file != null) {
                mailbox = readMailboxFromFile(file, Mailbox.STATUS_TEMP_BLOCKED);
            } else {
                file = getFileFromDisk(mbNotPresentDirectory, mailboxnr);
                if (file != null) {
                    mailbox = readMailboxFromFile(file, Mailbox.STATUS_NOT_PRESENT);
                } else if (useTemplate) {
                    mailbox = readMailboxFromFile(new File(MB_ENTRIES_TEMPLATE));
                } else {
                    mailbox = new Mailbox();
                }
            }
        }

        if (mailbox.getWachtwoord().isEmpty()) {
            mailbox.setWachtwoord(mailboxnr);
        }
        mailbox.setNummer(mailboxnr);
        return mailbox;
    }

    public void storeMailbox(final Mailbox mailbox) throws IOException {
        BufferedWriter bw = null;
        try {
            File dir = mbActiveDirectory;
            if (mailbox.isNotPresent()) {
                dir = mbNotPresentDirectory;
            }
            if (mailbox.isTempBlocked()) {
                dir = mbTempBlockedDirectory;
            }
            bw = new BufferedWriter(new FileWriter(dir + "/" + mailbox.getNummer() + ".csv"));

            bw.write("#originator,id,inout,status,mesgId,crossRef,msg");
            bw.newLine();
            bw.write("##" + mailbox.getWachtwoord());
            bw.newLine();
            bw.flush();

            final Collection<MailboxEntry> inboxEntries = mailbox.getInbox().values();
            writeEntriesToFile(bw, inboxEntries);

            bw.flush();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (final IOException e) {
                    // Do nothing
                }
            }
        }
    }

    private void writeEntriesToFile(final BufferedWriter bw, final Collection<MailboxEntry> entries)
            throws IOException {
        for (final MailboxEntry entry : entries) {
            final StringBuilder sb = new StringBuilder();
            sb.append(entry.getOriginatorOrRecipient()).append(",");
            sb.append(entry.getMsSequenceId()).append(",");
            sb.append(IN_OUT_IN).append(",");
            sb.append(entry.getStatus()).append(",");
            sb.append(entry.getMessageId()).append(",");
            sb.append(entry.getCrossReference()).append(",");
            sb.append(entry.getMesg());

            bw.write(sb.toString());
            bw.newLine();
        }
    }

    private Mailbox readMailboxFromFile(final File file) {
        return this.readMailboxFromFile(file, Mailbox.STATUS_OPEN);
    }

    private Mailbox readMailboxFromFile(final File file, final int status) {
        int highestMsSeqNr = 0;
        final Mailbox mailbox = new Mailbox();
        final Map<Integer, MailboxEntry> inboxEntries = new TreeMap<Integer, MailboxEntry>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            line = br.readLine();
            while (line != null) {
                if (line.startsWith("#")) {
                    if (line.startsWith("##")) {
                        // Password position in the file
                        mailbox.setWachtwoord(line.substring(2));
                    }
                } else {
                    final String[] values = line.split(",", -2);
                    final int msSequenceNr = new Integer(values[1]);
                    final MailboxEntry entry = new MailboxEntry();
                    entry.setOriginatorOrRecipient(values[0]);
                    entry.setMsSequenceNr(msSequenceNr);
                    entry.setStatus(new Integer(values[3]));
                    entry.setMessageId(values[4]);

                    entry.setCrossReference(values[5]);
                    entry.setMesg(values[6]);

                    if (IN_OUT_IN.equals(values[2])) {
                        inboxEntries.put(msSequenceNr, entry);
                    }
                    if (msSequenceNr > highestMsSeqNr) {
                        highestMsSeqNr = msSequenceNr;
                    }
                }
                line = br.readLine();
            }
        } catch (final IOException e) {
            LOGGER.debug("Fout tijdens inlezen van mailbox bestand", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (final IOException e) {
                    // do nothing;
                }
            }
        }
        mailbox.setInbox(inboxEntries);
        mailbox.setNextMSSequenceNr(highestMsSeqNr + 1);
        mailbox.setStatus(status);
        return mailbox;
    }

    private File getFileFromDisk(final File directory, final String mailboxnr) {
        File file = null;
        final FilenameFilter mailboxFilter = new MailboxFileFilter(mailboxnr);
        final File[] files = directory.listFiles(mailboxFilter);
        if (files.length == 1) {
            file = files[0];
        }
        return file;
    }

    class MailboxFileFilter implements FilenameFilter {
        private final String mailboxNr;

        public MailboxFileFilter(final String mailboxNr) {
            this.mailboxNr = mailboxNr;
        }

        @Override
        public boolean accept(final File dir, final String name) {
            final String filename = name.toLowerCase();
            return filename.endsWith(mailboxNr + ".csv");
        }
    }
}
