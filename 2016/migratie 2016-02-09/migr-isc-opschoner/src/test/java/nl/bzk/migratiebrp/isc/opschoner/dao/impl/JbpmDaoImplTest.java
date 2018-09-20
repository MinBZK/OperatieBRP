/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.JbpmDao;
import org.junit.Assert;
import org.junit.Test;

public class JbpmDaoImplTest extends AbstractDaoTest {

    private static final Long PROCES_ID = (long) 3367;
    private static final Long GERELATEERD_PROCES_ID = (long) 3369;
    private static final Long NIET_BEEINDIGD_PROCES_ID = (long) 3107;

    @Inject
    private JbpmDao jbpmDao;

    @Test
    public void testJbpmDaoVerwijderenProcesGegevens() throws SQLException {

        Timestamp einddatumProces = jbpmDao.haalEinddatumProcesOp(PROCES_ID);

        Assert.assertNotNull(einddatumProces);

        einddatumProces = jbpmDao.haalEinddatumProcesOp(GERELATEERD_PROCES_ID);

        Assert.assertNotNull(einddatumProces);

        einddatumProces = jbpmDao.haalEinddatumProcesOp(NIET_BEEINDIGD_PROCES_ID);

        Assert.assertNull(einddatumProces);

        List<Long> subProcessenIds = jbpmDao.selecteerSubProcessenVoorProces(PROCES_ID);

        Assert.assertEquals(1, subProcessenIds.size());

        subProcessenIds = jbpmDao.selecteerSubProcessenVoorProces(GERELATEERD_PROCES_ID);

        Assert.assertEquals(0, subProcessenIds.size());

        verwijderProcesGegevens(GERELATEERD_PROCES_ID);
        verwijderProcesGegevens(PROCES_ID);

        final Long aantalByteBlocks = geefQueryResultaat("select count(*) from jbpm_byteblock", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalByteBlocks);

        final Long aantalLogs = geefQueryResultaat("select count(*) from jbpm_log", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalLogs);

        final Long aantalComments = geefQueryResultaat("select count(*) from jbpm_comment", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalComments);

        final Long aantalVariableInstances = geefQueryResultaat("select count(*) from jbpm_variableinstance", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalVariableInstances);

        final Long aantalTokenVariableMap = geefQueryResultaat("select count(*) from jbpm_tokenvariablemap", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalTokenVariableMap);

        final Long aantalByteArrays = geefQueryResultaat("select count(*) from jbpm_bytearray", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalByteArrays);

        final Long aantalSwimlaneInstances = geefQueryResultaat("select count(*) from jbpm_swimlaneinstance", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalSwimlaneInstances);

        final Long aantalTaskActorPools = geefQueryResultaat("select count(*) from jbpm_taskactorpool", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalTaskActorPools);

        final Long aantalRuntimeActions = geefQueryResultaat("select count(*) from jbpm_runtimeaction", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalRuntimeActions);

        final Long aantalTaskInstances = geefQueryResultaat("select count(*) from jbpm_taskinstance", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalTaskInstances);

        final Long aantalJobs = geefQueryResultaat("select count(*) from jbpm_job", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalJobs);

        final Long aantalModuleInstances = geefQueryResultaat("select count(*) from jbpm_moduleinstance", Long.class);
        Assert.assertEquals(Long.valueOf(0), aantalModuleInstances);

        final Long aantalProcessInstances = geefQueryResultaat("select count(*) from jbpm_processinstance", Long.class);
        Assert.assertEquals(Long.valueOf(1), aantalProcessInstances);

        final Long aantalTokens = geefQueryResultaat("select count(*) from jbpm_token", Long.class);
        Assert.assertEquals(Long.valueOf(1), aantalTokens);
    }

    private void verwijderProcesGegevens(final Long procesId) {
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
    }

}
