/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc.environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.sql.DataSource;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.isc.ProcessenTestCasus;
import nl.moderniseringgba.migratie.test.isc.TestException;
import nl.moderniseringgba.migratie.test.isc.util.DBUnit;

import org.dbunit.DatabaseUnitException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Test tegen een externe container.
 */
// CHECKSTYLE:OFF - Fanout complexity
public final class ExternalTestEnvironment extends TestEnvironment {
    // CHCEKSTYLE:ON

    private static final int HERHALINGEN = 10;
    private static final String RESTART_VARIABELE_NAME = "restart";
    private static final String ID_COLUMN = "id_";

    private static final Logger LOG = LoggerFactory.getLogger();

    /* SQL */
    private static final String INSERT_NODE_JOB =
            "insert into jbpm_job(id_,class_,version_,duedate_,processinstance_,token_,graphelementtype_,"
                    + "graphelement_,issuspended_,isexclusive_,retries_,transitionname_) "
                    + "values (nextval('hibernate_sequence'), 'T', 0, ?, ?, ?, 'org.jbpm.graph.def.Node',"
                    + " ?, false, false,3, ?)";

    private static final String SELECT_CORRELATIE_OBV_MESSAGEID =
            "select process_instance_id, token_id, node_id from mig_correlatie where message_id = ?";

    private static final String SELECT_PROCESSINSTANCEID_OBV_MESSAGEID =
            "select distinct process_instance_id from mig_berichten "
                    + "where message_id = ? and process_instance_id is not null";

    private static final String SELECT_ENDED_PROCESSINSTANCE_OBV_ID =
            "select id_ from jbpm_processinstance where id_= ? and end_ is not null";

    private static final String SELECT_TOKENVARIABLEMAP =
            "select distinct tokenvariablemap_ from jbpm_variableinstance where token_ = ? and processinstance_ = ?";

    private static final String SELECT_VARIABLEID =
            "select id_ from jbpm_variableinstance where name_ = ? and token_ = ? and tokenvariablemap_ = ? and processinstance_ = ?";

    private static final String INSERT_VARIABLE =
            "insert into jbpm_variableinstance(id_,class_,version_,name_,token_, tokenvariablemap_, "
                    + "processinstance_, stringvalue_) values (nextval('hibernate_sequence'),'S',0,?,?,?,?,?)";

    private static final String UPDATE_VARIABLE =
            "update jbpm_variableinstance set stringvalue_ = ?, class_ = 'S' where id_ = ?";

    /* TIMEOUT */
    private static final int TIMEOUT = 500;

    private ConfigurableApplicationContext context;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("brpOntvangstQueue")
    private Destination brpOntvangst;

    @Inject
    @Named("brpVerzendenQueue")
    private Destination brpVerzenden;

    @Inject
    @Named("vospgOntvangstQueue")
    private Destination vospgOntvangst;

    @Inject
    @Named("vospgVerzendenQueue")
    private Destination vospgVerzenden;

    @Inject
    @Named("syncRequestQueue")
    private Destination syncRequest;

    @Inject
    @Named("syncResponseQueue")
    private Destination syncResponse;

    @Inject
    @Named("mviOntvangstQueue")
    private Destination mviOntvangst;

    @Inject
    @Named("mviVerzendenQueue")
    private Destination mviVerzenden;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    @Named("dataSourceISC")
    private DataSource datasource;

    @Override
    public void start() {
        LOG.info("Starting application context");
        context = new ClassPathXmlApplicationContext("classpath:/migr-isc-test-infra.xml");
        context.getAutowireCapableBeanFactory().autowireBean(this);

        jdbcTemplate.execute("truncate jbpm_job");
    }

    @Override
    public void shutdown() {
        jdbcTemplate.execute("truncate jbpm_job");

        context.close();
    }

    @Override
    public void verzendBericht(final String kanaal, final Bericht bericht) {
        LOG.info("verzendBericht(kanaal={}, bericht={})", kanaal, bericht);
        if (KANAAL_BRP.equalsIgnoreCase(kanaal)) {
            sendMessage(brpOntvangst, bericht);
        } else if (KANAAL_VOSPG.equalsIgnoreCase(kanaal)) {
            sendMessage(vospgOntvangst, bericht);
        } else if (KANAAL_SYNC.equalsIgnoreCase(kanaal)) {
            sendMessage(syncResponse, bericht);
        } else if (KANAAL_MVI.equalsIgnoreCase(kanaal)) {
            sendMessage(mviOntvangst, bericht);
        } else {
            throw new RuntimeException("Onverwacht kanaal om bericht te verzenden: " + kanaal);
        }
    }

    private void sendMessage(final Destination destination, final Bericht bericht) {
        LOG.info("Sending message (id={}, correlatie={}): {}", new Object[] { bericht.messageId,
                bericht.correlatieId, bericht.inhoud, });

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(bericht.inhoud);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.messageId);
                message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.correlatieId);
                if (bericht.bronGemeente != null) {
                    message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, bericht.bronGemeente);
                }
                if (bericht.doelGemeente != null) {
                    message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, bericht.doelGemeente);
                }

                return message;
            }
        });
    }

    @Override
    public Bericht ontvangBericht(final String kanaal, final String correlatieId) {
        String selector;
        if (correlatieId != null && !"".equals(correlatieId)) {
            selector = JMSConstants.CORRELATIE_REFERENTIE + "  = '" + correlatieId + "'";
        } else {
            selector = null;
        }

        final Bericht result;
        if (KANAAL_BRP.equalsIgnoreCase(kanaal)) {
            result = ontvangBericht(brpVerzenden, selector);
        } else if (KANAAL_VOSPG.equalsIgnoreCase(kanaal)) {
            result = ontvangBericht(vospgVerzenden, selector);
        } else if (KANAAL_SYNC.equalsIgnoreCase(kanaal)) {
            result = ontvangBericht(syncRequest, selector);
        } else if (KANAAL_MVI.equalsIgnoreCase(kanaal)) {
            result = ontvangBericht(mviVerzenden, selector);
        } else {
            throw new RuntimeException("Onverwacht kanaal bij ontvangen bericht: " + kanaal);
        }
        return result;
    }

    private Bericht ontvangBericht(final Destination destination, final String messageSelector) {
        final Message message = jmsTemplate.receiveSelected(destination, messageSelector);

        if (message == null) {
            return null;
        }

        final Bericht bericht = new Bericht();

        try {
            bericht.messageId = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            bericht.correlatieId = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);
            if (vospgVerzenden.equals(destination)) {
                bericht.doelGemeente = message.getStringProperty(JMSConstants.BERICHT_ORIGINATOR);
                bericht.bronGemeente = message.getStringProperty(JMSConstants.BERICHT_RECIPIENT);
            }

            if (message instanceof TextMessage) {
                bericht.inhoud = ((TextMessage) message).getText();
            } else {
                throw new RuntimeException("Onbekend bericht type: " + message.getClass().getName());
            }
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }

        LOG.info("Received message: {}", bericht.inhoud);

        return bericht;
    }

    @Override
    public void beforeTestCase(final ProcessenTestCasus testCase) {
        // Clear queues
        clearQueue(mviVerzenden);
        clearQueue(mviOntvangst);
        clearQueue(brpVerzenden);
        clearQueue(brpOntvangst);
        clearQueue(vospgVerzenden);
        clearQueue(vospgOntvangst);
        clearQueue(syncResponse);
        clearQueue(syncRequest);
    }

    private void clearQueue(final Destination destination) {
        final long timeout = jmsTemplate.getReceiveTimeout();

        try {
            jmsTemplate.setReceiveTimeout(TIMEOUT);
            while (jmsTemplate.receive(destination) != null) {
                LOG.error("Message on queue before test case!!!!");
            }
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }
    }

    @Override
    public boolean afterTestCase(final ProcessenTestCasus testCase) {
        boolean ok = true;

        // CHECKSTYLE:OFF - Boolean complexity
        if (!dumpQueue(mviVerzenden, testCase.getOutputDirectory(), "9999-in-mvi-")
                | !dumpQueue(mviOntvangst, testCase.getOutputDirectory(), "9999-uit-mvi-")
                | !dumpQueue(brpVerzenden, testCase.getOutputDirectory(), "9999-in-brp-")
                | !dumpQueue(brpOntvangst, testCase.getOutputDirectory(), "9999-uit-brp-")
                | !dumpQueue(vospgVerzenden, testCase.getOutputDirectory(), "9999-in-vospg-")
                | !dumpQueue(vospgOntvangst, testCase.getOutputDirectory(), "9999-uit-vospg-")
                | !dumpQueue(syncResponse, testCase.getOutputDirectory(), "9999-uit-sync-")
                | !dumpQueue(syncRequest, testCase.getOutputDirectory(), "9999-in-sync-")) {
            // CHECKSTYLE:ON
            ok = false;
        }

        return ok;
    }

    private boolean dumpQueue(final Destination destination, final File outputDirectory, final String prefix) {
        final long timeout = jmsTemplate.getReceiveTimeout();

        long volgnummer = 0;

        try {
            jmsTemplate.setReceiveTimeout(TIMEOUT);
            Message message;
            while ((message = jmsTemplate.receive(destination)) != null) {
                LOG.info("Onverwacht bericht op queue na testcase");

                final File outputFile = new File(outputDirectory, prefix + volgnummer++ + ".txt");

                FileOutputStream fis = null;
                try {
                    outputFile.getParentFile().mkdirs();
                    fis = new FileOutputStream(outputFile);
                    final PrintWriter writer = new PrintWriter(fis);
                    writer.print(((TextMessage) message).getText());
                    writer.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                } catch (final JMSException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        // CHECKSTYLE:OFF
                        try {
                            fis.close();
                        } catch (final IOException e) {
                            // Ignore
                        }
                        // CHECKSTYLE:ON
                    }
                }
            }
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }

        return volgnummer == 0;
    }

    @Override
    public void verwerkTransitieActie(final String berichtId, final String transitie) {
        // Zoek correlatie in MIG_CORRELATIE
        final Map<String, Object> map = jdbcTemplate.queryForMap(SELECT_CORRELATIE_OBV_MESSAGEID, berichtId);

        // Process instance id bepalen via database (distinct vanwege herhaal berichten)
        final Long processInstanceId = (Long) map.get("process_instance_id");
        final Long processTokenId = (Long) map.get("token_id");
        final Long nodeId = (Long) map.get("node_id");

        jdbcTemplate.update(INSERT_NODE_JOB, new java.util.Date(), processInstanceId, processTokenId, nodeId,
                transitie);
    }

    @Override
    public void verwerkDatabaseActie(final String inhoud) throws TestException {

        try {
            final DBUnit dbUnit = new DBUnit(datasource);
            dbUnit.insert(inhoud);
        } catch (final DatabaseUnitException e) {
            e.printStackTrace();
            throw new TestException("Fout (dbunit) bij het zetten van de database tabellen.", e);
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new TestException("Fout (sql) bij het zetten van de database tabellen.", e);
        }

    }

    @Override
    public boolean controleerProcesBeeindigd(final String gerelateerdeBerichtId) {
        // Process instance id bepalen via database (distinct vanwege herhaal berichten)
        final Long processInstanceId =
                queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, 10, gerelateerdeBerichtId);

        if (processInstanceId == null) {
            throw new RuntimeException("ISC Proces kan niet gevonden worden (gerelateerdeBerichtId="
                    + gerelateerdeBerichtId + ")");
        }

        final Long endedProcessInstanceId = queryForLong(SELECT_ENDED_PROCESSINSTANCE_OBV_ID, 10, processInstanceId);

        return endedProcessInstanceId != null;
    }

    private Long queryForLong(final String sql, final int maxCount, final Object... params) {
        int count = 0;
        while (count < maxCount) {
            count++;
            try {
                return jdbcTemplate.queryForLong(sql, params);
            } catch (final EmptyResultDataAccessException e) {
                try {
                    Thread.sleep(TIMEOUT * count);
                    // CHECKSTYLE:OFF - Hier kunnen we niets mee, ignore is niet erg
                } catch (final InterruptedException e1) {
                    // CHECKSTYLE:ON

                    // Ignore
                }
            }
        }

        return null;
    }

    @Override
    public Long getProcesInstanceId(final String berichtId) {
        // Process instance id bepalen via database (distinct vanwege herhaal berichten)
        return queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, HERHALINGEN, berichtId);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public void controleerOpHandmatigeActie(final String berichtId) throws TestException {
        getTaskId(berichtId);
    }

    @Override
    public void verwerkHandmatigeActie(final String berichtId, final String pad) throws TestException {
        final Long taskId = getTaskId(berichtId);
        verwerkHandmatigeActie(taskId, pad);
    }

    private Long getTaskId(final String berichtId) throws TestException {
        // Process instance id bepalen via database
        final Long processInstanceId = queryForLong(SELECT_PROCESSINSTANCEID_OBV_MESSAGEID, 10, berichtId);
        final List<Long> taskIds = getTasks(processInstanceId, 10);

        if (taskIds.isEmpty()) {
            throw new TestException("Geen beheerderstaak gevonden.");
        } else if (taskIds.size() > 1) {
            throw new TestException("Meer dan 1 beheerderstaak gevonden.");
        }
        return taskIds.get(0);
    }

    private List<Long> getTasks(final Long processInstanceId, final int maxCount) {
        int count = 0;
        while (count < maxCount) {
            count++;

            final List<Long> processInstanceIds = getProcessInstances(processInstanceId);
            final List<Long> taskIds = getTasks(processInstanceIds);

            if (!taskIds.isEmpty()) {
                return taskIds;
            }

            try {
                Thread.sleep(TIMEOUT * count);
                // CHECKSTYLE:OFF - Hier kunnen we niets mee, ignore is niet erg
            } catch (final InterruptedException e1) {
                // CHECKSTYLE:ON

                // Ignore
            }
        }

        return Collections.emptyList();
    }

    private List<Long> getProcessInstances(final Long processInstanceId) {
        final List<Long> result = new ArrayList<Long>();
        result.add(processInstanceId);

        final Long rootTokenId =
                queryForLong("select roottoken_ from jbpm_processinstance where id_ = ?", 10, processInstanceId);
        final List<Long> tokenIds = getTokens(rootTokenId);

        for (final Long tokenId : tokenIds) {
            final List<Map<String, Object>> subProcessInstanceIds =
                    jdbcTemplate.queryForList("select id_ from jbpm_processinstance where superprocesstoken_ = ?",
                            tokenId);

            for (final Map<String, Object> subProcessInstanceId : subProcessInstanceIds) {
                result.addAll(getProcessInstances((Long) subProcessInstanceId.get(ID_COLUMN)));
            }
        }

        return result;
    }

    private List<Long> getTokens(final Long tokenId) {
        final List<Long> result = new ArrayList<Long>();
        result.add(tokenId);

        final List<Map<String, Object>> childTokens =
                jdbcTemplate.queryForList("select id_ from jbpm_token where parent_ =?", tokenId);

        for (final Map<String, Object> childToken : childTokens) {
            result.addAll(getTokens((Long) childToken.get(ID_COLUMN)));
        }

        return result;
    }

    private List<Long> getTasks(final List<Long> processInstanceIds) {
        final List<Long> result = new ArrayList<Long>();
        for (final Long processInstanceId : processInstanceIds) {
            result.addAll(getTasks(processInstanceId));
        }
        return result;
    }

    private List<Long> getTasks(final Long processInstanceId) {
        final List<Long> result = new ArrayList<Long>();

        final List<Map<String, Object>> childTokens =
                jdbcTemplate.queryForList("select id_ from jbpm_taskinstance where procinst_ = ? and end_ is null",
                        processInstanceId);

        for (final Map<String, Object> childToken : childTokens) {
            result.add((Long) childToken.get(ID_COLUMN));
        }

        return result;
    }

    private void verwerkHandmatigeActie(final Long taskId, final String pad) {
        final Map<String, Object> task =
                jdbcTemplate.queryForMap("select token_, procinst_ from jbpm_taskinstance where id_ = ?", taskId);
        final Long tokenId = (Long) task.get("token_");
        final Long processInstanceId = (Long) task.get("procinst_");

        setTaskVariabele(taskId, tokenId, processInstanceId, pad);

        // Set task ended
        final Date now = new Date();
        jdbcTemplate.update("update jbpm_taskinstance set start_ = ?, end_ = ? where id_ = ?", now, now, taskId);

        // Geef task een schop via een job
        jdbcTemplate.update(
                "insert into jbpm_job(id_,class_,version_,duedate_,processinstance_,token_,taskinstance_,"
                        + "issuspended_,isexclusive_,retries_,transitionname_) "
                        + "values (nextval('hibernate_sequence'), 'T', 0, ?, ?, ?, ?, false, false,3, ?)",
                new java.util.Date(), processInstanceId, tokenId, taskId, "ok");
    }

    private void setTaskVariabele(
            final Long taskId,
            final Long taskTokenId,
            final Long taskProcessInstanceId,
            final String pad) {
        final Long tokenVariableMapId = queryForLong(SELECT_TOKENVARIABLEMAP, 1, taskTokenId, taskProcessInstanceId);
        // Zet pad variabele: lezen en eventueel updaten
        final Long variableId =
                queryForLong(SELECT_VARIABLEID, 1, RESTART_VARIABELE_NAME, taskTokenId, tokenVariableMapId,
                        taskProcessInstanceId);
        if (variableId == null) {
            jdbcTemplate.update(INSERT_VARIABLE, RESTART_VARIABELE_NAME, taskTokenId, tokenVariableMapId,
                    taskProcessInstanceId, pad);
        } else {
            jdbcTemplate.update(UPDATE_VARIABLE, pad, variableId);
        }
    }

}
