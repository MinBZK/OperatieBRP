/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.opschoner.exception.NietVerwijderbareProcesInstantieException;
import nl.bzk.migratiebrp.isc.opschoner.service.OpschonerService;
import nl.bzk.migratiebrp.isc.opschoner.service.ProcesService;
import nl.bzk.migratiebrp.isc.opschoner.service.RuntimeService;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * De algemene ingang in de services die Opschonen tellingen biedt.
 */
public final class OpschonerServiceImpl implements OpschonerService {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String RUNTIME_NAAM = "opschonen";
    private static final int MINIMALE_WACHTTIJD = 24;

    private final RuntimeService runtimeService;
    private final ProcesService procesService;
    private final PlatformTransactionManager transactionManager;

    /**
     * Constructor.
     * @param runtimeService runtime service
     * @param procesService proces service
     * @param transactionManager transaction manager
     */
    @Inject
    public OpschonerServiceImpl(final RuntimeService runtimeService, final ProcesService procesService,
                                final PlatformTransactionManager transactionManager) {
        this.runtimeService = runtimeService;
        this.procesService = procesService;
        this.transactionManager = transactionManager;
    }

    @Override
    public void opschonenProcessen(final Timestamp datumTot, final Integer wachtTijdInUren) {
        LOG.info("Start opschonen verwerkte tellingen");
        if (datumTot == null) {
            LOG.error("Datum 'datumTot' mag niet 'null' zijn.");
            return;
        }

        if (lockRuntime()) {
            try {
                // Opschonen bestaat uit de volgende stappen:
                // Stap 1: Bepaal beeindigde processen op basis van de proces extracties.
                // Stap 2: Controleer of de gevonden processen verwijderbaar zijn.
                // Stap 3a: Verwijder de processen die verwijderbaar zijn.
                // Stap 3b: Wijzig de waarde van verwachteVerwijderDatum voor processen die niet verwijderbaar zijn.

                final List<Long> verwijderdeProcesIds = new ArrayList<>();
                final List<Long> uitgesteldeProcesIds = new ArrayList<>();

                List<Long> procesIdsOpBasisVanBeeindigdeProcesExtracties = procesService.selecteerIdsVanOpTeSchonenProcessen(datumTot);

                while (!procesIdsOpBasisVanBeeindigdeProcesExtracties.isEmpty()) {
                    // Stap 1:
                    schoonProcessenOp(procesIdsOpBasisVanBeeindigdeProcesExtracties, wachtTijdInUren, verwijderdeProcesIds, uitgesteldeProcesIds);
                    procesIdsOpBasisVanBeeindigdeProcesExtracties = procesService.selecteerIdsVanOpTeSchonenProcessen(datumTot);
                }

                LOG.info(
                        FunctioneleMelding.ISC_OPSCHONER_UITGEVOERD,
                        "Er zijn {} processen opgeschoond. Voor {} processen is het opgeschonen uitgesteld.",
                        verwijderdeProcesIds.size(),
                        uitgesteldeProcesIds.size());

            } finally {

                unlockRuntime();
            }
        }
        LOG.info("Einde opschonen verwerkte berichten");
    }

    // Call self problem
    // @Transactional(value = "opschonerTransactionManager", propagation = Propagation.REQUIRED)
    private boolean lockRuntime() {
        return new TransactionTemplate(transactionManager).execute(status -> {
            try {
                runtimeService.lockRuntime(RUNTIME_NAAM);
                return true;
            } catch (final DataAccessException exceptie) {
                LOG.error("Opschoner is al actief. Huidige instantie wordt beeindigd.", exceptie);
                return false;
            }
        });
    }

    // Call self problem
    // @Transactional(value = "opschonerTransactionManager", propagation = Propagation.REQUIRED)
    private void unlockRuntime() {
        new TransactionTemplate(transactionManager).execute(status -> {
            runtimeService.unlockRuntime(RUNTIME_NAAM);
            return null;
        });
    }

    /**
     * De uitvoer van deze methode vindt binnen één transactie plaats aangezien dit per 100 processen gaat.
     */
    // Call self problem
    // @Transactional(value = "opschonerTransactionManager", propagation = Propagation.REQUIRED)
    private void schoonProcessenOp(
            final List<Long> procesIdsOpBasisVanBeeindigdeProcesExtracties,
            final Integer wachtTijdInUren,
            final List<Long> verwijderdeProcesIds,
            final List<Long> uitgesteldeProcesIds) {
        new TransactionTemplate(transactionManager).execute(status -> {
            for (final Long huidigProcesId : procesIdsOpBasisVanBeeindigdeProcesExtracties) {
                if (!verwijderdeProcesIds.contains(huidigProcesId)) {
                    try {
                        procesService.controleerProcesVerwijderbaar(huidigProcesId, new ArrayList<Long>(), verwijderdeProcesIds);
                        procesService.verwijderProces(huidigProcesId, new ArrayList<Long>(), verwijderdeProcesIds);
                        verwijderdeProcesIds.add(huidigProcesId);
                    } catch (final NietVerwijderbareProcesInstantieException exceptie) {
                        final Calendar laatsteDatumActiviteit = new GregorianCalendar();
                        laatsteDatumActiviteit.setTimeInMillis(exceptie.getLaatsteActiviteitDatum().getTime());
                        laatsteDatumActiviteit.add(Calendar.HOUR_OF_DAY, Math.max(wachtTijdInUren, MINIMALE_WACHTTIJD));
                        final Timestamp verwachteVerwijderDatum = new Timestamp(laatsteDatumActiviteit.getTimeInMillis());

                        procesService.updateVerwachteVerwijderDatumProces(huidigProcesId, verwachteVerwijderDatum);
                        uitgesteldeProcesIds.add(huidigProcesId);
                    }
                }
            }
            return null;
        });
    }
}
