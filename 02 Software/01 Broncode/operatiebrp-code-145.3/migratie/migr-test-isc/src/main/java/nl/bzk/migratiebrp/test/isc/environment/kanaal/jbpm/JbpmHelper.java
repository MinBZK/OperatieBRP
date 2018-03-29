/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JBPM Helper.
 */
public final class JbpmHelper {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int HERHALINGEN = 10;
    private static final int TIMEOUT = 500;

    private static final String ID_COLUMN = "id_";
    private static final String SELECT_PROCESSINSTANCEID_OBV_MESSAGEID = "select distinct process_instance_id from mig_bericht "
            + "where message_id = ? and process_instance_id is not null";
    private static final String SELECT_MESSAGEID_OBV_PROCESSINSTANCEID_AND_NAAM = "select distinct message_id from mig_bericht "
            + "where process_instance_id = ? and naam = ? "
            + "and message_id is not null";

    private static final String SELECT_ROOTTOKEN_FROM_PROCESSINSTANCE = "select roottoken_ from jbpm_processinstance where id_= ?";

    @Inject
    private JdbcTemplate jdbcTemplate;

    private Long queryForLong(final String sql, final int maxCount, final Object... params) {
        return queryForObject(Long.class, sql, maxCount, params);
    }

    private String queryForString(final String sql, final int maxCount, final Object... params) {
        return queryForObject(String.class, sql, maxCount, params);
    }

    private <T> T queryForObject(final Class<T> clazz, final String sql, final int maxCount, final Object... params) {
        int count = 0;
        while (count < maxCount) {
            count++;
            try {
                return jdbcTemplate.queryForObject(sql, params, clazz);
            } catch (final EmptyResultDataAccessException e) {
                try {
                    TimeUnit.MILLISECONDS.sleep(TIMEOUT * count);
                } catch (final InterruptedException e1) {
                    LOG.debug("sleep() interrupted in queryForLong().", e1);
                }
            }
        }

        return null;
    }

    private List<Long> queryForLongs(final String sql, final int maxCount, final Object... params) {
        int count = 0;
        while (count < maxCount) {
            count++;
            final List<Long> result = jdbcTemplate.queryForList(sql, Long.class, params);
            if (!result.isEmpty()) {
                return result;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(TIMEOUT * count);
            } catch (final InterruptedException e1) {
                LOG.debug("sleep() interrupted in queryForLongs().", e1);
            }
        }

        return Collections.emptyList();
    }

    /**
     * Controleer of een proces 'wacht' op een handmatige actie (dus in een TaskNode zit).
     * @param berichtId bericht id (om juiste proces te selecteren)
     * @return true, als proces 'wacht' op een handmatig actie
     * @throws JbpmHelperException bij fouten
     */
    public boolean controleerOpHandmatigeActie(final String berichtId) throws JbpmHelperException {
        return getTaskId(berichtId) != null;
    }

    /**
     * Bepaal het task id. bericht id (om juiste proces te selecteren)
     * @param berichtId het Id van het bericht waarop de taak wordt gezocht
     * @return task id, als dit process in een task node zit (of null)
     * @throws JbpmHelperException bij fouten
     */
    public Long getTaskId(final String berichtId) throws JbpmHelperException {
        // Process instance id bepalen via database
        final Long processInstanceId = queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, HERHALINGEN, berichtId);
        LOG.info("Process instance id: {}", processInstanceId);

        final List<Long> taskIds = getTasks(processInstanceId, HERHALINGEN);

        if (taskIds.isEmpty()) {
            return null;
        } else if (taskIds.size() > 1) {
            throw new JbpmHelperException("Meer dan 1 beheerderstaak gevonden.");
        }

        LOG.info("Task id: " + taskIds.get(0));

        return taskIds.get(0);
    }

    private List<Long> getTasks(final Long processInstanceId, final int maxCount) {
        int count = 0;
        while (count < maxCount) {
            count++;

            final List<Long> processInstanceIds = getProcessInstances(processInstanceId);
            LOG.info("process instance ids: {}", processInstanceIds);
            final List<Long> taskIds = getTasks(processInstanceIds);

            if (!taskIds.isEmpty()) {
                return taskIds;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(TIMEOUT * count);
            } catch (final InterruptedException e1) {
                LOG.debug("sleep() interrupted in getTasks().", e1);
            }
        }

        return Collections.emptyList();
    }

    private List<Long> getProcessInstances(final Long processInstanceId) {
        final List<Long> result = new ArrayList<>();
        result.add(processInstanceId);

        final Long rootTokenId = queryForLong("select roottoken_ from jbpm_processinstance where id_ = ?", HERHALINGEN, processInstanceId);
        final List<Long> tokenIds = getTokens(rootTokenId);

        for (final Long tokenId : tokenIds) {
            final List<Map<String, Object>> subProcessInstanceIds;
            subProcessInstanceIds = jdbcTemplate.queryForList("select id_ from jbpm_processinstance where superprocesstoken_ = ?", tokenId);

            for (final Map<String, Object> subProcessInstanceId : subProcessInstanceIds) {
                result.addAll(getProcessInstances((Long) subProcessInstanceId.get(ID_COLUMN)));
            }
        }

        return result;
    }

    private List<Long> getTokens(final Long tokenId) {
        final List<Long> result = new ArrayList<>();
        result.add(tokenId);

        final List<Map<String, Object>> childTokens = jdbcTemplate.queryForList("select id_ from jbpm_token where parent_ =?", tokenId);

        for (final Map<String, Object> childToken : childTokens) {
            result.addAll(getTokens((Long) childToken.get(ID_COLUMN)));
        }

        return result;
    }

    private List<Long> getTasks(final List<Long> processInstanceIds) {
        final List<Long> result = new ArrayList<>();
        for (final Long processInstanceId : processInstanceIds) {
            result.addAll(getTasks(processInstanceId));
        }
        return result;
    }

    private List<Long> getTasks(final Long processInstanceId) {
        final List<Long> result = new ArrayList<>();

        final List<Map<String, Object>> childTokens
                = jdbcTemplate.queryForList("select id_ from jbpm_taskinstance where procinst_ = ? and end_ is null", processInstanceId);

        for (final Map<String, Object> childToken : childTokens) {
            LOG.info("Task id {} found for process instance id {}", childToken.get(ID_COLUMN), processInstanceId);
            result.add((Long) childToken.get(ID_COLUMN));
        }

        return result;
    }

    /**
     * Controleer of een proces zich in een bepaalde node bevind.
     * @param berichtId bericht id (om juiste proces te selecteren)
     * @param node node naam
     * @return true, als het proces zich in een node bevind met de gegeven naam.
     * @throws JbpmHelperException bij fouten
     */
    public boolean checkNodeActie(final String berichtId, final String node) throws JbpmHelperException {
        // Zoek proces in MIG_BERICHTEN
        LOG.info("checkNodeActie: berichtId: {}", berichtId);
        final Long processInstanceId = queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, 1, berichtId);
        LOG.info("checkNodeActie: procesInstanceId: {}", processInstanceId);
        final Long processTokenId = queryForLong(SELECT_ROOTTOKEN_FROM_PROCESSINSTANCE, 1, processInstanceId);
        LOG.info("checkNodeActie: processTokenId: {}", processTokenId);

        final Long nodeId
                = queryForLong("select node_ from jbpm_token, jbpm_node "
                + "where jbpm_token.id_= ? and jbpm_token.node_ = jbpm_node.id_ and jbpm_node.name_ = ?", 1, processTokenId, node);

        return nodeId != null;
    }

    /**
     * Opschonen jobs.
     */
    public void cleanJobs() {
        jdbcTemplate.execute("truncate jbpm_job");

    }

    /**
     * Opschonen anummer-locks.
     */
    public void cleanLocks() {
        //jdbcTemplate.execute("truncate mig_lock cascade");
    }

    /**
     * Bepaal alle processen.
     * @param messageIds bericht ids
     * @return lijst met proces ids
     */
    public Set<Long> bepaalAlleProcessen(final List<String> messageIds) {
        final Set<Long> result = new HashSet<>();

        for (final String messageId : messageIds) {
            final List<Long> berichtIds = queryForLongs("select distinct id from mig_bericht where message_id = ?", HERHALINGEN, messageId);

            for (final Long berichtId : berichtIds) {
                final Long procesId
                        = queryForLong(
                        "select process_instance_id from mig_bericht where id = ? and process_instance_id is not null",
                        HERHALINGEN,
                        berichtId);
                if (procesId != null) {
                    result.add(procesId);
                }
            }

        }

        return result;
    }

    /**
     * Bepaal proces obv bericht id.
     * @param berichtId bericht id
     * @return proces id
     */
    public Long bepaalProces(final String berichtId) {
        return queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, HERHALINGEN, berichtId);
    }

    /**
     * Bepaalt het token op basis van het bericht.
     * @param berichtId het bericht Id waarop wordt gezocht
     * @param bepaalTokenOpCorrelatie indicator of het een gecorreleerd bericht betreft
     * @return het ID van het token
     */
    public Long bepaalToken(final String berichtId, final boolean bepaalTokenOpCorrelatie) {
        final Long tokenOpCorrelatie = queryForLong("select token_id from mig_correlatie where message_id = ?", HERHALINGEN, berichtId);

        if (tokenOpCorrelatie != null) {
            return tokenOpCorrelatie;
        } else {
            final Long procesInstanceId = bepaalProces(berichtId);
            final Long tokenOpProces
                    = queryForLong("select id_ from jbpm_token where parent_ is null and processinstance_ = ?", HERHALINGEN, procesInstanceId);
            return tokenOpProces;
        }
    }

    /**
     * Bepaalt het bericht op basis van een proces instantie en het bericht type.
     * @param processInstanceId de proces instantie
     * @param berichtType het bericht type
     * @return string representatie van het messageID van het bericht
     */
    public String bepaalBericht(final Long processInstanceId, final String berichtType) {
        return queryForString(SELECT_MESSAGEID_OBV_PROCESSINSTANCEID_AND_NAAM, HERHALINGEN, processInstanceId, berichtType);
    }

    /**
     * Controleer of het proces is beeindigd.
     * @param processInstanceId proces id
     * @return true, als het proces gevonden en beeindigd is, anders false
     */
    public boolean controleerProcesBeeindigd(final Long processInstanceId) {
        if (processInstanceId == null) {
            throw new IllegalArgumentException("Proces ID mag niet null zijn.");
        }

        final Long endedProcessInstanceId
                = queryForLong("select id_ from jbpm_processinstance where id_= ? and end_ is not null", HERHALINGEN, processInstanceId);

        return endedProcessInstanceId != null;
    }

    /**
     * Controleer of een proces een bepaalde transitie heeft doorlopen.
     * @param berichtId bericht id
     * @param node node
     * @param transitie transitie
     * @param aantal aantal keer controleren
     * @return true, als de transitie is doorlopen
     * @throws JbpmHelperException bij fouten
     */
    public boolean checkTransActie(final String berichtId, final String node, final String transitie, final int aantal) throws JbpmHelperException {
        LOG.info("checkTransActie: berichtId: {}", berichtId);
        LOG.info("checkTransActie: node: {}", node);
        LOG.info("checkTransActie: transitie: {}", transitie);
        LOG.info("checkTransActie: aantal: {}", aantal);
        final Long processInstanceId = queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, HERHALINGEN, berichtId);
        LOG.info("checkTransActie: procesInstanceId: {}", processInstanceId);
        final Long processDefinitionId = queryForLong("select processdefinition_ from jbpm_processinstance where id_ = ?", 1, processInstanceId);
        LOG.info("checkTransActie: processDefinitionId: {}", processDefinitionId);
        final Long transitieId
                = queryForLong("select t.id_ from jbpm_node n, jbpm_transition t where n.processdefinition_ = ? "
                + "and n.id_ = t.from_ and n.name_ = ?  and COALESCE(t.name_, '') = ?", 1, processDefinitionId, node, transitie);
        LOG.info("checkTransActie: transitieId: {}", transitieId);

        final Long processTokenId = queryForLong(SELECT_ROOTTOKEN_FROM_PROCESSINSTANCE, 1, processInstanceId);
        LOG.info("checkTransActie: processTokenId: {}", processTokenId);

        final List<Long> logRegelIds
                = queryForLongs("select id_ from jbpm_log where class_ = 'T' and token_ = ? and transition_ = ?", HERHALINGEN, processTokenId, transitieId);

        return logRegelIds.size() == aantal;
    }

}
