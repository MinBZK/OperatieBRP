/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscDatabaseException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor database operaties.
 */
public final class VoiscDatabaseImpl implements VoiscDatabase {

    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";
    private static final String MELDING_GELOCKED = "[Bericht {}]: Status (exclusief) geupdate naar SENDING voor bericht.";
    private static final String MELDING_LOCK_GEFAALD = "[Bericht {}]: Lock op bericht reeds verkregen door andere transactie. Skipping ..";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final BerichtRepository berichtRepository;
    private final MailboxRepository mailboxRepository;

    /**
     * Constructor.
     * @param berichtRepository bericht repository
     * @param mailboxRepository mailbox repository
     */
    @Inject
    public VoiscDatabaseImpl(final BerichtRepository berichtRepository, final MailboxRepository mailboxRepository) {
        this.berichtRepository = berichtRepository;
        this.mailboxRepository = mailboxRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, value = VOISC_TRANSACTION_MANAGER)
    public Bericht saveBericht(final Bericht bericht) {
        return berichtRepository.save(bericht);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public List<Bericht> leesEnLockNaarIscTeVerzendenBericht(final int limit) {
        // Aangezien deze methode op Propagation.NEVER staat en getBerichtenToSendQueue op Propagation.REQUIRED
        // zal de getBerichtenToSendQueue in een eigen transactie draaien en 'dus' aan het einde een 'commit' hebben.
        final List<Bericht> candidates = berichtRepository.getBerichtenToSendQueue(limit);
        final List<Bericht> result = new ArrayList<>();

        for (final Bericht candidate : candidates) {
            try {
                // Aangezien deze method op Propagation.NEVER staat en save op Propagation.REQUIRED zal save in een
                // eigen transactie draaien en aan het einde van de save methode zal een commit worden uitgevoerd.
                // Tijdens deze commit zal de @Version voor de optimistic lock worden gecontroleerd en zal een
                // OptimisticLockException worden gegooid als een ander proces reeds een update heeft gedaan.
                candidate.setTijdstipInVerwerking(new Timestamp(System.currentTimeMillis()));
                candidate.setStatus(StatusEnum.SENDING_TO_ISC);
                final Bericht berichtToSend = berichtRepository.save(candidate);

                result.add(berichtToSend);
                LOGGER.debug(MELDING_GELOCKED, berichtToSend.getId());

            } catch (final HibernateOptimisticLockingFailureException | OptimisticLockException e) {
                LOGGER.debug(MELDING_LOCK_GEFAALD, candidate.getId(), e);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public List<Bericht> leesEnLockNaarMailboxTeVerzendenBericht(final String mailboxNr) {
        // Aangezien deze methode op Propagation.NEVER staat en getBerichtToSendMBS op Propagation.REQUIRED
        // zal de getBerichtenToSendQueue in een eigen transactie draaien en 'dus' aan het einde een 'commit' hebben.
        final List<Bericht> candidates = berichtRepository.getBerichtToSendMBS(mailboxNr);
        final List<Bericht> result = new ArrayList<>();

        for (final Bericht candidate : candidates) {
            try {
                // Aangezien deze method op Propagation.NEVER staat en save op Propagation.REQUIRED zal save in een
                // eigen transactie draaien en aan het einde van de save methode zal een commit worden uitgevoerd.
                // Tijdens deze commit zal de @Version voor de optimistic lock worden gecontroleerd en zal een
                // OptimisticLockException worden gegooid als een ander proces reeds een update heeft gedaan.
                candidate.setTijdstipInVerwerking(new Timestamp(System.currentTimeMillis()));
                candidate.setStatus(StatusEnum.SENDING_TO_MAILBOX);
                final Bericht berichtToSend = berichtRepository.save(candidate);

                result.add(berichtToSend);
                LOGGER.debug(MELDING_GELOCKED, berichtToSend.getId());

            } catch (final HibernateOptimisticLockingFailureException | OptimisticLockException e) {
                LOGGER.debug(MELDING_LOCK_GEFAALD, candidate.getId(), e);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, value = VOISC_TRANSACTION_MANAGER)
    public Mailbox getMailboxByPartijcode(final String partijcode) {
        return mailboxRepository.getMailboxByPartijcode(partijcode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public void saveMailbox(final Mailbox mailbox) {
        mailboxRepository.save(mailbox);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public int verwijderVerwerkteBerichtenOuderDan(final Date ouderDan, final Set<StatusEnum> statussen) throws VoiscDatabaseException {
        if (ouderDan == null) {
            throw new VoiscDatabaseException("'ouderDan' parameter mag niet 'null' zijn.");
        }
        if (statussen == null || statussen.isEmpty()) {
            throw new VoiscDatabaseException("'statussen' mag niet 'null' of 'leeg' zijn.");
        }

        return berichtRepository.verwijderVerzondenBerichtenOuderDan(ouderDan, statussen);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public int herstelBerichten(final Date ouderDan, final StatusEnum statusVan, final StatusEnum statusNaar) {
        return berichtRepository.herstelStatus(ouderDan, statusVan, statusNaar);
    }
}
