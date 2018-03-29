/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsaantekeningRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.LeveringsaantekeningService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie van LeveringsaantekeningService.
 */
@Service
public final class LeveringsaantekeningServiceImpl implements LeveringsaantekeningService {

    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";
    private final LeveringsaantekeningRepository repository;

    /**
     * @param repository leveringsaantekening repository
     */
    @Inject
    public LeveringsaantekeningServiceImpl(final LeveringsaantekeningRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public void persisteerLeveringsaantekening(final Leveringsaantekening leveringsaantekening) {
        repository.save(leveringsaantekening);
    }

    @Override
    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    public Leveringsaantekening bevraagLeveringsaantekening(final Long id) {
        return repository.find(id);
    }
}
