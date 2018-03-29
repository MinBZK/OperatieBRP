/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service.impl;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.ExtractieDao;
import nl.bzk.migratiebrp.isc.opschoner.dao.JbpmDao;
import nl.bzk.migratiebrp.isc.opschoner.dao.MigDao;
import nl.bzk.migratiebrp.isc.opschoner.exception.NietVerwijderbareProcesInstantieException;
import nl.bzk.migratiebrp.isc.opschoner.service.ProcesService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie klasse voor de proces service interface.
 */
public final class ProcesServiceImpl implements ProcesService {

    private static final String OPSCHONER_TRANSACTION_MANAGER = "opschonerTransactionManager";

    private final JbpmDao jbpmDao;
    private final MigDao migDao;
    private final ExtractieDao extractieDao;

    /**
     * Constructor.
     * @param jbpmDao jbpm dao
     * @param migDao mig dao
     * @param extractieDao extractie dao
     */
    @Inject
    public ProcesServiceImpl(final JbpmDao jbpmDao, final MigDao migDao, final ExtractieDao extractieDao) {
        this.jbpmDao = jbpmDao;
        this.migDao = migDao;
        this.extractieDao = extractieDao;
    }

    @Override
    @Transactional(value = OPSCHONER_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public List<Long> selecteerIdsVanOpTeSchonenProcessen(final Timestamp datumTot) {
        return extractieDao.haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(datumTot);
    }

    @Override
    @Transactional(value = OPSCHONER_TRANSACTION_MANAGER, propagation = Propagation.SUPPORTS)
    public void controleerProcesVerwijderbaar(
            final Long procesId,
            final List<Long> verwerkteProcesIds,
            final List<Long> verwijderdeProcesIds) throws NietVerwijderbareProcesInstantieException {

        if (!verwijderdeProcesIds.contains(procesId)) {

            verwerkteProcesIds.add(procesId);

            final Timestamp einddatumProces = jbpmDao.haalEinddatumProcesOp(procesId);
            final Timestamp datumLaatsteBericht = migDao.bepaalDatumLaatsteBerichtOntvangenVoorProces(procesId);

            if (einddatumProces != null && datumLaatsteBericht != null && !einddatumProces.before(datumLaatsteBericht)) {
                controleerSubProcessenVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);
                controleerGerelateerdeProcessenVerwijderbaar(procesId, verwerkteProcesIds, verwijderdeProcesIds);
            } else if (einddatumProces == null) {
                throw new NietVerwijderbareProcesInstantieException(
                        "Het proces (id="
                                + procesId
                                + " )kan niet worden verwijderd aangezien er nog een of meerdere sub- of gerelateerd(e) "
                                + "proces(sen) nog niet is/zijn beeindigd).",
                        new Timestamp(System.currentTimeMillis()));
            } else if (datumLaatsteBericht != null) {
                throw new NietVerwijderbareProcesInstantieException(
                        "Het proces (id= "
                                + procesId
                                + " ) kan niet worden verwijderd (er is nog een bericht binnengekomen nadat het proces is "
                                + "beeindigd).",
                        datumLaatsteBericht);
            }
        }
    }

    private void controleerGerelateerdeProcessenVerwijderbaar(
            final Long procesId,
            final List<Long> verwerkteProcesIds,
            final List<Long> verwijderdeProcesIds) throws NietVerwijderbareProcesInstantieException {
        final List<Long> gerelateerdeProcesIds = migDao.selecteerGerelateerdeProcessenVoorProces(procesId);
        for (final Long gerelateerdeProcesId : gerelateerdeProcesIds) {
            if (!verwerkteProcesIds.contains(gerelateerdeProcesId)) {
                controleerProcesVerwijderbaar(gerelateerdeProcesId, verwerkteProcesIds, verwijderdeProcesIds);
            }
        }
    }

    private void controleerSubProcessenVerwijderbaar(
            final Long procesId,
            final List<Long> verwerkteProcesIds,
            final List<Long> verwijderdeProcesIds) throws NietVerwijderbareProcesInstantieException {
        final List<Long> subProcesIds = jbpmDao.selecteerSubProcessenVoorProces(procesId);
        for (final Long subProcesId : subProcesIds) {
            if (!verwerkteProcesIds.contains(subProcesId)) {
                controleerProcesVerwijderbaar(subProcesId, verwerkteProcesIds, verwijderdeProcesIds);
            }
        }
    }

    @Override
    @Transactional(value = OPSCHONER_TRANSACTION_MANAGER, propagation = Propagation.SUPPORTS)
    public void verwijderProces(final Long procesId, final List<Long> verwerkteProcesIds, final List<Long> verwijderdeProcesIds) {

        if (!verwerkteProcesIds.contains(procesId)) {
            verwerkteProcesIds.add(procesId);

            // Bepaal subprocessen en gerelateerde processen.
            final List<Long> subprocessen = jbpmDao.selecteerSubProcessenVoorProces(procesId);
            final List<Long> gerelateerdeProcessen = migDao.selecteerGerelateerdeProcessenVoorProces(procesId);

            // Verwijder eerste de informatie van de gerelateerde processen.
            for (final Long huidigGerelateerdProcesId : gerelateerdeProcessen) {

                if (!verwijderdeProcesIds.contains(huidigGerelateerdProcesId) && !verwerkteProcesIds.contains(huidigGerelateerdProcesId)) {
                    verwijderProces(huidigGerelateerdProcesId, verwerkteProcesIds, verwijderdeProcesIds);
                    verwijderdeProcesIds.add(huidigGerelateerdProcesId);
                    migDao.verwijderGerelateerdProcesVerwijzingVoorProces(procesId, huidigGerelateerdProcesId);
                }
            }

            // Verwijder de informatie van de subprocessen.
            for (final Long huidigSubProcesId : subprocessen) {
                if (!verwijderdeProcesIds.contains(huidigSubProcesId) && !verwerkteProcesIds.contains(huidigSubProcesId)) {
                    verwijderProces(huidigSubProcesId, verwerkteProcesIds, verwijderdeProcesIds);
                    verwijderdeProcesIds.add(huidigSubProcesId);
                }
            }

            // Verwijder de informatie van het rootproces.
            verwijderProcesInformatie(procesId);
        }

    }

    private void verwijderProcesInformatie(final Long procesId) {

        final List<Long> tokens = jbpmDao.bepaalTokensVoorProces(procesId);
        final List<Long> taskInstances = jbpmDao.bepaalTaskInstancesVoorProces(procesId);
        final List<Long> variableInstances = jbpmDao.bepaalVariableInstancesVoorTokens(tokens);
        final List<Long> byteArrays = jbpmDao.bepaalByteArraysVoorVariableInstances(variableInstances);

        // Deel 1: Verwijderen op basis van tokens.
        jbpmDao.verwijderByteBlocks(byteArrays);
        jbpmDao.elimineerReferentieLogNaarLogVoorTokens(tokens);
        jbpmDao.verwijderLogsVoorTokens(tokens);
        jbpmDao.verwijderCommentsVoorTokens(tokens);
        jbpmDao.verwijderVariableInstancesVoorTokens(tokens);
        jbpmDao.verwijderTokenVariableMapVoorToken(tokens);
        jbpmDao.verwijderByteArrays(byteArrays);
        jbpmDao.elimineerReferentieTokenNaarTokenVoorTokens(tokens);

        // Deel 2: Verwijder op basis van task instance.
        jbpmDao.verwijderSwimlaneInstancesVoorTaskInstances(taskInstances);
        jbpmDao.verwijderTaskActorPoolsVoorTaskInstances(taskInstances);

        // Deel 3: Verwijder op basis van proces instance.
        jbpmDao.verwijderRuntimeActionsVoorProces(procesId);
        jbpmDao.verwijderTaskInstancesVoorProces(procesId);
        jbpmDao.verwijderJobsVoorProces(procesId);
        jbpmDao.verwijderModuleInstancesVoorProces(procesId);
        jbpmDao.elimineerReferentieTokenNaarProcesInstantie(tokens);

        // Deel 4: Ruim de proces instantie zelf op.
        jbpmDao.verwijderProcessInstancesVoorProces(procesId);

        // Deel 5: Verwijder ook de eerder ontkoppelde tokens.
        jbpmDao.verwijderTokens(tokens);

        // Deel 6: Ruim de mig-tabellen op.
        migDao.verwijderBerichtenVanProces(procesId);
    }

    @Override
    @Transactional(value = OPSCHONER_TRANSACTION_MANAGER, propagation = Propagation.SUPPORTS)
    public void updateVerwachteVerwijderDatumProces(final Long procesId, final Timestamp verwachteVerwijderDatum) {
        extractieDao.updateVerwachteVerwijderDatum(procesId, verwachteVerwijderDatum);

    }

}
