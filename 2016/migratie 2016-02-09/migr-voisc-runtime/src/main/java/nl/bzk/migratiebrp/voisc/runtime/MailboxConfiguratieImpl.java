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

import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
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

    @Inject
    private RegisterService<GemeenteRegister> gemeenteService;
    @Inject
    private MailboxRepository mailboxRepository;
    @Inject
    @Named("teBedienenMailboxen")
    private String teBedienenMailboxen;

    /**
     * Bepaal mailboxen obv configuratie.
     * 
     * @return set aan mailboxen
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "voiscTransactionManager")
    public Set<Mailbox> bepaalMailboxen() {
        final Set<Mailbox> mailboxen = new TreeSet<>();

        bepaalMailboxenObvTeBedienenMailboxen(mailboxen);
        filterMailBoxenOpvGemeenteRegister(mailboxen);

        return mailboxen;
    }

    private void bepaalMailboxenObvTeBedienenMailboxen(final Set<Mailbox> mailboxen) {
        final String[] mailboxNummers =
                teBedienenMailboxen == null || teBedienenMailboxen.trim().isEmpty() ? new String[] {} : teBedienenMailboxen.trim().split(
                    ",");
        for (int i = 0; i < mailboxNummers.length; i++) {
            mailboxNummers[i] = mailboxNummers[i].trim();
        }

        final boolean initieelVullen;
        if (mailboxNummers.length == 0) {
            // Als we geen configuratie hebben dan doen we alle mailboxen
            initieelVullen = true;
        } else {
            if (mailboxNummers[0].startsWith(NOT)) {
                // Als we een '-' hebben dan doen we alle mailboxen minus degene die geconfigureerd zijn
                initieelVullen = true;
            } else {
                // Als we niet een '-' hebben dan doen we alleen de mailboxen die geconfigureerd zijn
                initieelVullen = false;
            }
        }

        if (initieelVullen) {
            vulMetAlleMailboxen(mailboxen);
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

    private void vulMetAlleMailboxen(final Set<Mailbox> mailboxen) {
        mailboxen.addAll(mailboxRepository.getCentraleMailboxes());
        mailboxen.addAll(mailboxRepository.getGemeenteMailboxes());
    }

    /**
     * Verwijder alle gemeentes die zich niet in het BRP stelsel bevinden.
     * 
     * @param mailBoxen
     *            mailbox verzameling (wordt aangepast)
     * @param gemeenteService
     *            gemeente service om het gemeente register op te halen
     */
    private void filterMailBoxenOpvGemeenteRegister(final Set<Mailbox> mailboxen) {
        final GemeenteRegister gemeenteRegister = gemeenteService.geefRegister();

        final Iterator<Mailbox> mailboxIterator = mailboxen.iterator();
        while (mailboxIterator.hasNext()) {
            final Mailbox mailbox = mailboxIterator.next();
            if (Mailbox.INSTANTIETYPE_GEMEENTE.equals(mailbox.getInstantietype())) {
                final Gemeente gemeente = gemeenteRegister.zoekGemeenteOpGemeenteCode(mailbox.getFormattedInstantiecode());

                if (gemeente == null || gemeente.getStelsel() != Stelsel.BRP) {
                    mailboxIterator.remove();
                }
            }
        }
    }

}
