/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import org.springframework.beans.factory.annotation.Value;

/**
 * Berichten amlezer.
 */
public final class BerichtLezer {

    @Value("${afnemers:}")
    private String afnemers;

    @Inject
    private MailboxRepository mailboxRepository;

    @Inject
    private MailboxVerwerker mailboxVerwerker;

    @Value("${data.directory:.}")
    private File directory;

    /**
     * Lees de berichten voor de geconfigureerde mailboxen en schrijf deze naar AM-bestanden.
     */
    public void execute() {
        final Set<Mailbox> mailboxen = bepaalMailboxen();
        for (final Mailbox mailbox : mailboxen) {
            mailboxVerwerker.verwerkBerichten(mailbox, new AMSchrijverBerichtCallback(mailbox, directory));
        }

    }

    private Set<Mailbox> bepaalMailboxen() {
        final Set<Mailbox> mailboxen = new TreeSet<>();

        final String[] afnemersNummers = afnemers == null || "".equals(afnemers) ? new String[]{} : afnemers.split(",");
        for (final String afnemersNummer : afnemersNummers) {
            final Mailbox mailbox = mailboxRepository.getMailboxByPartijcode(afnemersNummer);
            if (mailbox != null) {
                mailboxen.add(mailbox);
            }
        }

        return mailboxen;
    }
}
