/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.BlokkeringRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.Lo3BerichtRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.StamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging.LoggingService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging.LoggingServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de BrpDalService.
 */
@Service
public final class BrpDalServiceImpl implements BrpDalService {

    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";

    private final StamtabelRepository stamtabelRepository;
    private final BlokkeringRepository blokkeringRepository;

    private final LoggingService loggingService;

    /**
     * Default constructor.
     * @param berichtLogRepository de repository voor het bevragen en opslaan van BerichtLog objecten
     * @param blokkeringRepository de repository voor het bevragen en opslaan van blokkering.
     * @param stamtabelRepository de repository met stam gegevens.
     */
    @Inject
    BrpDalServiceImpl(
            final Lo3BerichtRepository berichtLogRepository,
            final BlokkeringRepository blokkeringRepository,
            final StamtabelRepository stamtabelRepository) {
        this.blokkeringRepository = blokkeringRepository;
        this.stamtabelRepository = stamtabelRepository;

        loggingService = new LoggingServiceImpl(berichtLogRepository);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public Lo3Bericht persisteerLo3Bericht(final Lo3Bericht bericht) {
        return loggingService.persisteerLo3Bericht(bericht);
    }

    @Transactional(value = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Lo3Bericht zoekLo3PeroonslijstBerichtOpAnummer(final String administratienummer) {
        return loggingService.zoekLo3PersoonslijstBerichtOpAnummer(administratienummer);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Set<String> zoekBerichtLogAnrs(final Date vanaf, final Date tot) {
        return loggingService.zoekBerichtLogAnrs(vanaf, tot);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public Blokkering persisteerBlokkering(final Blokkering blokkering) {

        if (blokkeringRepository.statusBlokkering(blokkering.getaNummer()) != null) {
            throw new IllegalStateException("De persoonlijst met het opgegeven aNummer is al geblokkeerd.");
        }

        return blokkeringRepository.blokkeerPersoonslijst(blokkering);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Blokkering vraagOpBlokkering(final String aNummer) {

        if (aNummer == null || aNummer.isEmpty()) {
            throw new IllegalStateException("Er is geen aNummer opgegeven.");
        }

        return blokkeringRepository.statusBlokkering(aNummer);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void verwijderBlokkering(final Blokkering teVerwijderenBlokkering) {

        final Blokkering controleBlokkering = blokkeringRepository.statusBlokkering(teVerwijderenBlokkering.getaNummer());

        if (controleBlokkering == null) {
            throw new IllegalStateException("De persoonlijst met het opgegeven aNummer is niet geblokkeerd.");
        }

        if (!teVerwijderenBlokkering.getProcessId().equals(controleBlokkering.getProcessId())) {
            throw new IllegalStateException("Het process ID komt niet overeen met het process ID waarmee de persoonslijst is geblokkeerd.");
        }

        if (!teVerwijderenBlokkering.getRegistratieGemeente().equals(controleBlokkering.getRegistratieGemeente())) {
            throw new IllegalStateException(
                    "De registratiegemeente komt niet overeen met de registratiegemeent die de persoonslijst " + "heeft geblokkeerd.");
        }

        blokkeringRepository.deblokkeerPersoonslijst(teVerwijderenBlokkering);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Collection<Gemeente> geefAlleGemeenten() {
        return stamtabelRepository.findAllGemeentes();
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Collection<Partij> geefAllePartijen() {
        return stamtabelRepository.findAllPartijen();
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Partij geefPartij(final BrpPartijCode partijCode) {
        if (partijCode == null) {
            return null;
        } else {
            return stamtabelRepository.findPartijByCode(partijCode.getWaarde());
        }
    }
}
