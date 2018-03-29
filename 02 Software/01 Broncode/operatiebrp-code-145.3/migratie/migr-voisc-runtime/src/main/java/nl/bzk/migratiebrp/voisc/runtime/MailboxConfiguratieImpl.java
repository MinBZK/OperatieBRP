/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mailbox configuratie.
 */
public final class MailboxConfiguratieImpl implements MailboxConfiguratie {

    private static final String NOT = "-";

    private RegisterService<PartijRegister> partijService;
    private MailboxRepository mailboxRepository;
    private String teBedienenMailboxen;

    /**
     * Constructor.
     * @param partijService partij service
     * @param mailboxRepository mailbox repository
     * @param teBedienenMailboxen te bedienen mailboxen
     */
    @Inject
    public MailboxConfiguratieImpl(final RegisterService<PartijRegister> partijService,
                                   final MailboxRepository mailboxRepository,
                                   @Named("teBedienenMailboxen") final String teBedienenMailboxen) {
        this.partijService = partijService;
        this.mailboxRepository = mailboxRepository;
        this.teBedienenMailboxen = teBedienenMailboxen;
    }


    /**
     * Bepaal mailboxen obv configuratie.
     * @return set aan mailboxen
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "voiscTransactionManager")
    public Set<Mailbox> bepaalMailboxen() {
        final Set<Mailbox> mailboxen = new TreeSet<>();

        bepaalMailboxenObvTeBedienenMailboxen(mailboxen);
        filterMailboxenOpvRegister(mailboxen);

        return mailboxen;
    }

    private void bepaalMailboxenObvTeBedienenMailboxen(final Set<Mailbox> mailboxen) {
        final String[] mailboxNummers =
                teBedienenMailboxen == null || teBedienenMailboxen.trim().isEmpty() ? new String[]{} : teBedienenMailboxen.trim().split(",");
        for (int i = 0; i < mailboxNummers.length; i++) {
            mailboxNummers[i] = mailboxNummers[i].trim();
        }

        // Als we geen configuratie hebben dan doen we alle mailboxen
        // Als we een '-' hebben dan doen we alle mailboxen minus degene die geconfigureerd zijn
        // Als we niet een '-' hebben dan doen we alleen de mailboxen die geconfigureerd zijn
        if (mailboxNummers.length == 0 || mailboxNummers[0].startsWith(NOT)) {
            mailboxen.addAll(mailboxRepository.getAllMailboxen());
        }

        for (final String mailboxNummer : mailboxNummers) {
            if (mailboxNummer.startsWith(NOT)) {
                verwijderMailbox(mailboxen, mailboxNummer.substring(1));
            } else {
                voegMailboxToe(mailboxen, mailboxNummer);
            }
        }
    }

    private void voegMailboxToe(final Set<Mailbox> mailboxen, final String mailboxNummer) {
        final Mailbox mailbox = mailboxRepository.getMailboxByNummer(mailboxNummer);
        if (mailbox != null) {
            mailboxen.add(mailbox);
        }
    }

    private void verwijderMailbox(final Set<Mailbox> mailboxen, final String mailboxNummer) {
        final Iterator<Mailbox> mailboxIterator = mailboxen.iterator();
        while (mailboxIterator.hasNext()) {
            final Mailbox mailbox = mailboxIterator.next();
            if (mailboxNummer.equals(mailbox.getMailboxnr())) {
                mailboxIterator.remove();
            }
        }
    }

    /**
     * Verwijder alle partijen die zich niet in het BRP stelsel bevinden.
     * @param ongefiltererdeMailboxen mailbox verzameling
     */
    private void filterMailboxenOpvRegister(final Set<Mailbox> ongefiltererdeMailboxen) {
        ongefiltererdeMailboxen.removeIf(mailbox -> {
            Partij registerPartij = partijService.geefRegister().zoekPartijOpPartijCode(mailbox.getPartijcode());
            return registerPartij == null || registerPartij.getStelsel() != Stelsel.BRP;
        });

    }

    @Override
    public String toonMailboxen() {
        final Set<Mailbox> mailboxen = bepaalMailboxen();
        final StringBuilder result = new StringBuilder();
        result.append("Mailboxen (" + mailboxen.size() + "): ");

        for (final Mailbox mailbox : mailboxen) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(mailbox.getMailboxnr());
        }
        result.append("\n");

        return result.toString();
    }

}
