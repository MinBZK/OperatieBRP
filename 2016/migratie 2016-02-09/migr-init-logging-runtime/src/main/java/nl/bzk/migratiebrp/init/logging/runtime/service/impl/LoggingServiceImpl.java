/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.service.impl;

import javax.inject.Inject;

import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.runtime.repository.InitVullingLogRepository;
import nl.bzk.migratiebrp.init.logging.runtime.service.LoggingService;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.VerschilAnalyseService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de LoggingService.
 */
@Service
public final class LoggingServiceImpl implements LoggingService {

    private static final String LOGGING_TRANSACTION_MANAGER = "loggingTransactionManager";
    private final InitVullingLogRepository logRepository;
    private final VerschilAnalyseService verschilAnalyseService;

    /**
     * Default constructor.
     *
     * @param logRepository
     *            log repository voor het bevragen en opslaan van log regels voor een persoonslijst
     * @param verschilAnalyseService
     *            de bepaalt de verschillen tussen de PL-en
     */
    @Inject
    LoggingServiceImpl(final InitVullingLogRepository logRepository, final VerschilAnalyseService verschilAnalyseService) {
        this.logRepository = logRepository;
        this.verschilAnalyseService = verschilAnalyseService;
    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void persisteerInitVullingLog(final InitVullingLog log) {
        logRepository.saveInitVullingLogPersoon(log);
    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void persisteerInitVullingAutorisatie(final InitVullingAutorisatie autorisaties) {
        logRepository.saveInitVullingAutorisatie(autorisaties);

    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void persisteerInitVullingAfnemerindicatie(final InitVullingAfnemersindicatie afnemerindicatie) {
        logRepository.saveInitVullingAfnemersindicatie(afnemerindicatie);

    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void bepalenEnOpslaanVerschillen(final InitVullingLog vullingLog) {
        verschilAnalyseService.bepaalVerschillen(vullingLog);
        logRepository.saveInitVullingLogPersoon(vullingLog);
    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public InitVullingLog zoekInitVullingLog(final Long anummer) {
        return logRepository.findInitVullingLogPersoon(anummer);
    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public InitVullingAutorisatie zoekInitVullingAutorisatie(final Integer afnemerCode) {
        return logRepository.findInitVullingAutorisatie(afnemerCode);
    }

    @Transactional(value = LOGGING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public InitVullingAfnemersindicatie zoekInitVullingAfnemerindicatie(final Long anummer) {
        return logRepository.findInitVullingAfnemersindicatie(anummer);
    }
}
