/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.simulation;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Mailbox {
    public static final int STATUS_OPEN = 0;
    public static final int STATUS_TEMP_BLOCKED = 1;
    public static final int STATUS_NOT_PRESENT = 2;

    private String nummer = "";
    private String wachtwoord = "";
    private Map<Integer, MailboxEntry> inbox = new TreeMap<Integer, MailboxEntry>();
    private int nextMsSequenceNr = 0;
    private int nextFilteredMSSequenceId = 0;
    private int status = STATUS_OPEN;

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(final String mailboxPwd) {
        wachtwoord = mailboxPwd;
    }

    public Map<Integer, MailboxEntry> getInbox() {
        return inbox;
    }

    public void setInbox(final Map<Integer, MailboxEntry> inbox) {
        this.inbox = inbox;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(final String nummer) {
        this.nummer = nummer;
    }

    public int getNextMsSequenceNr() {
        return nextMsSequenceNr;
    }

    public void setNextMSSequenceNr(final int id) {
        nextMsSequenceNr = id;
    }

    // TODO aanpassen zodat je vanaf een opgegeven sequenceNr kan zoeken.
    public Set<MailboxEntry> filterInbox(final String msStatus, final int limit) {
        final Set<MailboxEntry> filteredList = new TreeSet<MailboxEntry>();
        final boolean fetchNew = msStatus.contains("0");
        final boolean fetchListed = msStatus.contains("1");
        final boolean fetchProcessed = msStatus.contains("2");
        for (final MailboxEntry entry : inbox.values()) {
            if (filteredList.size() == limit) {
                nextFilteredMSSequenceId = entry.getMsSequenceId();
                break;
            }

            if (fetchNew && MailboxEntry.STATUS_NEW == entry.getStatus()) {
                filteredList.add(entry);
                entry.setStatus(MailboxEntry.STATUS_LISTED);
            }
            if (fetchListed && MailboxEntry.STATUS_LISTED == entry.getStatus()) {
                if (!filteredList.contains(entry)) {
                    filteredList.add(entry);
                }
            }
            if (fetchProcessed && MailboxEntry.STATUS_PROCESSED == entry.getStatus()) {
                filteredList.add(entry);
            }
        }

        return filteredList;
    }

    public int getNextFilteredMSSequenceNr() {
        return nextFilteredMSSequenceId;
    }

    public MailboxEntry getMsgFormInbox(final Integer msSequenceNr) {
        if (inbox.containsKey(msSequenceNr)) {
            final MailboxEntry entry = inbox.get(msSequenceNr);
            entry.setStatus(MailboxEntry.STATUS_PROCESSED);
            return entry;
        }
        return null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public boolean isNotPresent() {
        return STATUS_NOT_PRESENT == status;
    }

    public boolean isTempBlocked() {
        return STATUS_TEMP_BLOCKED == status;
    }
}
