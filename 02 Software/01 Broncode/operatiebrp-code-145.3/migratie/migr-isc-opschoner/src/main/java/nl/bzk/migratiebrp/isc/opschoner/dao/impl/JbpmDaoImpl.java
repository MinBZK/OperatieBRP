/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.opschoner.dao.JbpmDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DAO voor JBPM.
 */
public final class JbpmDaoImpl implements JbpmDao {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final JdbcTemplate template;

    /**
     * Constructor.
     * @param template jdbc template
     */
    @Inject
    public JbpmDaoImpl(final JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Long> selecteerSubProcessenVoorProces(final Long procesId) {

        final List<Long> superProcessTokens = bepaalTokensVoorProces(procesId);

        final List<Long> result = new ArrayList<>();
        for (final Long superProcessToken : superProcessTokens) {
            final List<Long> ids = selecteerProcesInstantiesOpBasisVanSuperProcessToken(superProcessToken);

            if (!ids.isEmpty()) {
                result.addAll(ids);
            }
        }
        return result;
    }

    @Override
    public Timestamp haalEinddatumProcesOp(final Long procesId) {
        final String query = "select end_ from jbpm_processinstance pi where pi.id_ = ?";

        try {
            return template.queryForObject(query, Timestamp.class, procesId);
        } catch (final EmptyResultDataAccessException e) {
            LOGGER.debug("Geen resultaten gevonden voor eindedatum", e);
            return null;
        }

    }

    // **********************************************************
    //
    //
    // Methodes voor verwijderen van proces instantie onderdelen.
    //
    //
    // **********************************************************

    @Override
    public List<Long> bepaalTaskInstancesVoorProces(final Long procesId) {
        final String query = "select id_ from jbpm_taskinstance where procinst_ = ?";

        return template.queryForList(query, Long.class, procesId);
    }

    @Override
    public void verwijderTaskInstancesVoorProces(final Long procesId) {
        final String query = "delete from jbpm_taskinstance where procinst_ = ?";

        template.update(query, procesId);
    }

    @Override
    public List<Long> bepaalVariableInstancesVoorTokens(final List<Long> tokens) {

        final List<Long> result = new ArrayList<>();

        final String query = "select id_ from jbpm_variableinstance where token_ = ?";

        for (final Long token : tokens) {
            final List<Long> variableInstanceList = template.queryForList(query, Long.class, token);
            if (!variableInstanceList.isEmpty()) {
                result.addAll(variableInstanceList);
            }
        }

        return result;

    }

    @Override
    public void verwijderVariableInstancesVoorTokens(final List<Long> tokens) {
        final String query = "delete from jbpm_variableinstance where token_ = ?";

        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public void verwijderProcessInstancesVoorProces(final Long procesId) {
        final String query = "delete from jbpm_processinstance where id_ = ?";

        template.update(query, procesId);
    }

    @Override
    public List<Long> bepaalByteArraysVoorVariableInstances(final List<Long> variableInstances) {

        final List<Long> result = new ArrayList<>();

        final String query = "select bytearrayvalue_ from jbpm_variableinstance where id_ = ?";

        for (final Long variableInstance : variableInstances) {
            final List<Long> byteArrayList = template.queryForList(query, Long.class, variableInstance);
            if (!byteArrayList.isEmpty()) {
                result.addAll(byteArrayList);
            }
        }

        return result;
    }

    @Override
    public void verwijderByteArrays(final List<Long> byteArrays) {
        final String query = "delete from jbpm_bytearray where id_ = ?";

        for (final Long byteArray : byteArrays) {
            template.update(query, byteArray);
        }
    }

    @Override
    public void elimineerReferentieLogNaarLogVoorTokens(final List<Long> tokens) {
        final String query = "update jbpm_log set parent_ = null where token_ = ?";

        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public void elimineerReferentieTokenNaarTokenVoorTokens(final List<Long> tokens) {
        final String query = "update jbpm_token set parent_ = null where id_ = ?";

        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public void verwijderLogsVoorTokens(final List<Long> tokens) {
        final String query = "delete from jbpm_log where token_ = ?";

        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public void verwijderJobsVoorProces(final Long procesId) {
        final String query = "delete from jbpm_job where processinstance_ = ?";

        template.update(query, procesId);
    }

    @Override
    public void verwijderModuleInstancesVoorProces(final Long procesId) {
        final String query = "delete from jbpm_moduleinstance where processinstance_ = ?";

        template.update(query, procesId);
    }

    @Override
    public List<Long> bepaalTokensVoorProces(final Long procesId) {
        final String query = "select id_ from jbpm_token where processinstance_ = ?";

        return template.queryForList(query, Long.class, procesId);
    }

    @Override
    public void verwijderTokens(final List<Long> tokens) {
        final String query = "delete from jbpm_token where id_ = ?";

        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public List<Long> selecteerProcesInstantiesOpBasisVanSuperProcessToken(final Long token) {

        final String query = "select id_ from jbpm_processinstance where superprocesstoken_ = ?";

        return template.queryForList(query, Long.class, token);
    }

    @Override
    public void elimineerReferentieTokenNaarProcesInstantie(final List<Long> tokens) {
        for (final Long superProcessToken : tokens) {
            final Set<Long> uniekeProcesInstanties = new HashSet<>();
            uniekeProcesInstanties.addAll(selecteerProcesInstantiesOpBasisVanSuperProcessToken(superProcessToken));

            final String queryElimineerSuperProcessToken = "update jbpm_processinstance set superprocesstoken_ = null where id_ = ?";
            for (final Long procesInstantie : uniekeProcesInstanties) {
                template.update(queryElimineerSuperProcessToken, procesInstantie);
            }

            final String queryElimineerSubProcessen = "update jbpm_token set processinstance_ = null where id_ = ?";
            template.update(queryElimineerSubProcessen, superProcessToken);
        }
    }

    @Override
    public void verwijderTokenVariableMapVoorToken(final List<Long> tokens) {
        final String query = "delete from jbpm_tokenvariablemap where token_ = ?";
        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public void verwijderByteBlocks(final List<Long> byteArrays) {
        final String query = "delete from jbpm_byteblock where processfile_ = ?";

        for (final Long byteArray : byteArrays) {
            template.update(query, byteArray);
        }
    }

    @Override
    public void verwijderCommentsVoorTokens(final List<Long> tokens) {
        final String query = "delete from jbpm_comment where token_ = ?";

        for (final Long token : tokens) {
            template.update(query, token);
        }
    }

    @Override
    public void verwijderSwimlaneInstancesVoorTaskInstances(final List<Long> taskInstances) {
        final String query = "delete from jbpm_swimlaneinstance where taskmgmtinstance_ = ?";

        for (final Long taskinstance : taskInstances) {
            template.update(query, taskinstance);
        }
    }

    @Override
    public void verwijderTaskActorPoolsVoorTaskInstances(final List<Long> taskInstances) {
        final String query = "delete from jbpm_taskactorpool where taskinstance_ = ?";

        for (final Long taskinstance : taskInstances) {
            template.update(query, taskinstance);
        }
    }

    @Override
    public void verwijderRuntimeActionsVoorProces(final Long procesId) {
        final String query = "delete from jbpm_runtimeaction where processinstance_ = ?";

        template.update(query, procesId);
    }

}
