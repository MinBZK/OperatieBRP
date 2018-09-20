/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.domein.ProefSynchronisatieBericht;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

/**
 * Verwerker die berichten naar de mailbox stuurt.
 */
public final class MailboxProefSynchronisatieBerichtVerwerker extends AbstractProefSynchronisatieBerichtVerwerker
        implements BerichtVerwerker<ProefSynchronisatieBericht>
{
    private static final Logger LOG = LoggerFactory.getLogger();

    private final MailboxRepository mailboxRepository;
    private final MailboxClient mailboxClient;

    /**
     * Constructor.
     *
     * @param executor
     *            executor
     * @param proefSynchronisatieRepository
     *            proefsync repo
     * @param mailboxRepository
     *            mailbox repo
     * @param mailboxClient
     *            amilbox client
     */
    public MailboxProefSynchronisatieBerichtVerwerker(
        final ThreadPoolExecutor executor,
        final ProefSynchronisatieRepository proefSynchronisatieRepository,
        final MailboxRepository mailboxRepository,
        final MailboxClient mailboxClient)
    {
        super(executor, proefSynchronisatieRepository);
        this.mailboxRepository = mailboxRepository;
        this.mailboxClient = mailboxClient;
    }

    @Override
    protected List<ProefSynchronisatieBericht> verstuurProefSynchronisatieBerichten(final List<ProefSynchronisatieBericht> teVersturenBerichten) {
        // Map berichten naar setjes per mailbox
        final Map<Integer, List<ProefSynchronisatieBericht>> berichten = orderBerichten(teVersturenBerichten);
        final List<ProefSynchronisatieBericht> result = new ArrayList<>();
        // Dit is een beetje tegenstrijdig met het feit dat we threaded werken... maar mailboxClient is singlethreaded,
        // nog even over na denken. Misschien een mailboxclient factory? Maar dan hebben we ook een connection factory
        // nodig voor de @inject in de mailbox client.
        // Of we maken de verwerker single threaded en openen de connectie en doen per bericht een logon/putMessage.
        synchronized (mailboxClient) {
            mailboxClient.connect();
            try {
                for (final Map.Entry<Integer, List<ProefSynchronisatieBericht>> berichtenSet : berichten.entrySet()) {
                    final Mailbox mailbox = mailboxRepository.getMailboxByInstantiecode(berichtenSet.getKey());

                    try {
                        if (mailbox == null) {
                            LOG.warn("Mailbox {} niet gevonden. {} berichten worden genegeerd", berichtenSet.getKey(), berichtenSet.getValue().size());
                            continue;
                        }
                        mailboxClient.logOn(mailbox);
                        for (final ProefSynchronisatieBericht bericht : berichtenSet.getValue()) {
                            try {
                                mailboxClient.putMessage(maakBericht(bericht));
                                result.add(bericht);
                            } catch (final SpdProtocolException e) {
                                LOG.warn("Versturen van bericht (id=" + bericht.getBerichtId() + ") gefaald", e);
                            }
                        }
                    } catch (final
                        SpdProtocolException
                        | MailboxServerPasswordException exception)
                    {
                        LOG.warn("Inloggen op mailbox gefaald", exception);
                    } catch (final Exception exception) {
                        // EXPLAIN: Vang overige excepties af om het verzenden robuust te maken en te voorkomen dat de
                        // gehele batch niet wordt geupdate,
                        // terwijl een deel wel al is verstuurd naar de mailbox.
                        LOG.warn("Probleem opgetreden bij verbinden met/versturen naar mailbox. ", exception);
                    }
                }
            } catch (final Exception exception) {
                // EXPLAIN: Exceptie gegooid in de mailbox komen niet naar boven indien we niet rethrowen.
                LOG.warn("Probleem opgetreden bij verbinden met/versturen naar mailbox.", exception);
                throw exception;
            } finally {
                try {
                    mailboxClient.logOff();
                } catch (final SpdProtocolException e) {
                    LOG.warn("Mailbox logoff mislukt", e);
                }
            }
        }

        return result;
    }

    private Bericht maakBericht(final ProefSynchronisatieBericht bericht) {
        final Bericht result = new Bericht();

        result.setBerichtInhoud(bericht.getBericht());
        result.setMessageId(Long.toString(bericht.getBerichtId()));
        result.setCorrelationId(null);
        // Centrale mailbox
        result.setRecipient("3000200");

        return result;
    }

    private Map<Integer, List<ProefSynchronisatieBericht>> orderBerichten(final List<ProefSynchronisatieBericht> teVersturenBerichten) {
        final Map<Integer, List<ProefSynchronisatieBericht>> result = new HashMap<>();

        for (final ProefSynchronisatieBericht teVersturenBericht : teVersturenBerichten) {
            if (!result.containsKey(teVersturenBericht.getAfzender())) {
                result.put(teVersturenBericht.getAfzender(), new ArrayList<ProefSynchronisatieBericht>());
            }

            result.get(teVersturenBericht.getAfzender()).add(teVersturenBericht);
        }

        return result;
    }
}
