/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.bpm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.mvi.MviBericht;
import nl.moderniseringgba.isc.esb.message.mvi.MviBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.jbpm.spring.NoSignal;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.isc.jbpm.spring.SpringActionHandler;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecisionHandler;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.dom4j.tree.DefaultElement;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.services.jbpm.JBpmObjectMapper;
import org.jboss.soa.esb.services.jbpm.actionhandlers.EsbActionHandler;
import org.jboss.soa.esb.services.jbpm.actionhandlers.EsbNotifier;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Node.NodeType;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.Decision;
import org.jbpm.graph.node.DecisionHandler;
import org.jbpm.jpdl.xml.JpdlXmlReader;
import org.jbpm.jpdl.xml.Problem;
import org.jbpm.jpdl.xml.ProblemListener;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.InputSource;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:usecase-beans.xml")
public abstract class AbstractJbpmTest {

    protected static final Logger LOG = LoggerFactory.getLogger();
    private static final int BRP_TIMEOUT = 4;
    private static final int BRP_MAX_HERHALINGEN = 2;
    private static final int VOSPG_TIMEOUT = 8;
    private static final int VOSPG_MAX_HERHALINGEN = 5;
    private static final int SYNC_TIMEOUT = 12;
    private static final int SYNC_MAX_HERHALINGEN = 3;

    @Inject
    private ApplicationContext applicationContext;

    private final List<MviBericht> mviBerichten = new ArrayList<MviBericht>();
    private final List<Lo3Bericht> vospgBerichten = new ArrayList<Lo3Bericht>();
    private final List<BrpBericht> brpBerichten = new ArrayList<BrpBericht>();
    private final List<SyncBericht> syncBerichten = new ArrayList<SyncBericht>();

    private final Map<String, Long> correlatie = new HashMap<String, Long>();

    private final String processDefinitionXml;
    private String processName;
    private Long processInstanceId;

    public AbstractJbpmTest(final String processDefinitionXml) {
        this.processDefinitionXml = processDefinitionXml;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Before
    public void setupJbpm() {
        TestSpringActionHandler.testRef = this;
        TestSpringDecisionHandler.testRef = this;
        TestEsbActionHandler.testRef = this;

        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final String[] processDefXmls = processDefinitionXml.split(",");

            for (final String processDefXml : processDefXmls) {
                LOG.info("Deploying process definition: " + processDefXml);

                // Setup process definition
                final JpdlXmlReader jpdlReader =
                        new JpdlXmlReader(new InputSource(this.getClass().getResourceAsStream(processDefXml)),
                                new ProblemListener() {
                                    @Override
                                    public void addProblem(final Problem problem) {
                                        LOG.error(problem.toString());
                                    }
                                });
                final ProcessDefinition processDefinition = jpdlReader.readProcessDefinition();

                preProcessDefinitionNodes(processDefinition.getNodes());

                jbpmContext.deployProcessDefinition(processDefinition);
                // Take first to do test on
                if (processName == null) {
                    processName = processDefinition.getName();
                }
            }

            // Setup database
            @SuppressWarnings("deprecation")
            final Connection connection = jbpmContext.getSession().connection();
            try {
                final Statement statement = connection.createStatement();

                // @formatter:off
                // CHECKSTYLE:OFF Ignoring failure on DROP TABLE heeft 'eigen' try/catch nodig
                try {
                    statement.execute("DROP TABLE mig_configuratie");
                 } catch (final SQLException e) {
                     LOG.debug("Ignoring failure on DROP TABLE mig_configuratie...");
                 }
                // CHECKSTYLE:ON
                
                statement.execute("CREATE TABLE mig_configuratie(configuratie varchar(20) not null, waarde varchar(20), constraint cfg_pk primary key(configuratie))");
                statement.execute("insert into mig_configuratie(configuratie, waarde) values ('brp.timeout', '" + BRP_TIMEOUT + " hours')");
                statement.execute("insert into mig_configuratie(configuratie, waarde) values ('brp.herhalingen', '" + BRP_MAX_HERHALINGEN + "')");
                statement.execute("insert into mig_configuratie(configuratie, waarde) values ('vospg.timeout', '" + VOSPG_TIMEOUT+ " hours')");
                statement.execute("insert into mig_configuratie(configuratie, waarde) values ('vospg.herhalingen', '" + VOSPG_MAX_HERHALINGEN + "')");
                statement.execute("insert into mig_configuratie(configuratie, waarde) values ('sync.timeout', '" + SYNC_TIMEOUT + " hours')");
                statement.execute("insert into mig_configuratie(configuratie, waarde) values ('sync.herhalingen', '" + SYNC_MAX_HERHALINGEN + "')");
                // @formatter:on

                // @formatter:off
                // CHECKSTYLE:OFF Ignoring failure on DROP TABLE heeft 'eigen' try/catch nodig
                try {
                    statement.execute("DROP TABLE mig_fouten");
                } catch (final SQLException e) {
                    LOG.debug("Ignoring failure on DROP TABLE mig_configuratie...");
                }
                // CHECKSTYLE:ON

                statement.execute("CREATE TABLE mig_fouten("
                   + "id                    serial,"
                   + "tijdstip              timestamp     not null,"
                   + "proces                varchar(30)   not null,"
                   + "process_instance_id   bigint        not null,"
                   + "code                  varchar(60)   not null,"
                   + "melding               text,"
                   + "resolutie             varchar(30),"
                   + "proces_init_gemeente  varchar(4),"
                   + "proces_doel_gemeente  varchar(4),"
                   + "constraint fout_pk primary key(id))");

                statement.execute("CREATE INDEX on mig_fouten(proces)");
                statement.execute("CREATE INDEX on mig_fouten(code)");                
                // @formatter:on

                statement.close();
            } catch (final SQLException e) {
                Assert.fail("Could not create configuration table in database: " + e.getMessage());
            }
        } finally {
            jbpmContext.close();
        }
    }

    @After
    public void cleanupJbpm() {
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            // Drop database
            @SuppressWarnings("deprecation")
            final Connection connection = jbpmContext.getSession().connection();
            try {
                final Statement statement = connection.createStatement();

                statement.execute("DROP TABLE mig_configuratie");
                statement.close();
            } catch (final SQLException e) {
                Assert.fail("Could not drop configuration table in database: " + e.getMessage());
            }
        } finally {
            jbpmContext.close();
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void preProcessDefinitionNodes(final Collection<Node> nodes) {
        if (nodes != null) {
            for (final Node node : nodes) {

                LOG.info(node.getNodeType() + ": " + node.getClass().getName());
                LOG.info(node.toString());

                if (node instanceof Decision) {
                    preProcessDefinitionDecision((Decision) node);
                }

                preProcessDefinitionAction(node.getAction());
                preProcessDefinitionTransitions(node.getLeavingTransitions());

                if (node.hasEvents()) {
                    preProcessDefinitionEvents(node.getEvents().values());
                }
                preProcessDefinitionNodes(node.getNodes());

            }
        }
    }

    private void preProcessDefinitionTransitions(final Collection<Transition> transitions) {
        if (transitions != null) {
            for (final Transition transition : transitions) {
                if (transition.hasEvents()) {
                    preProcessDefinitionEvents(transition.getEvents().values());
                }
            }
        }
    }

    private void preProcessDefinitionEvents(final Collection<Event> events) {
        for (final Event event : events) {
            preProcessDefinitionActions(event.getActions());
        }

    }

    private void preProcessDefinitionActions(final List<Action> actions) {
        if (actions != null) {
            for (final Action action : actions) {
                preProcessDefinitionAction(action);
            }
        }
    }

    private void preProcessDefinitionAction(final Action action) {
        if (action != null) {

            if (action.getActionDelegation() != null) {
                final String actionDelegationClassName = action.getActionDelegation().getClassName();

                if (actionDelegationClassName.equals(SpringActionHandler.class.getName())) {
                    action.getActionDelegation().setClassName(TestSpringActionHandler.class.getName());
                } else if (actionDelegationClassName.equals(EsbActionHandler.class.getName())) {
                    action.getActionDelegation().setClassName(TestEsbActionHandler.class.getName());
                } else if (actionDelegationClassName
                        .equals(nl.moderniseringgba.isc.jbpm.actionhandler.EsbNotifier.class.getName())) {
                    action.getActionDelegation().setClassName(TestEsbActionHandler.class.getName());
                } else if (actionDelegationClassName.equals(EsbNotifier.class.getName())) {
                    throw new IllegalArgumentException(
                            "nl.moderniseringgba.isc.jbpm.actionhandler.EsbNotifier dient gebruikt te worden ipv org.jboss.soa.esb.services.jbpm.actionhandlers.EsbNotifier (vanwege de process correlatie naar berichten)");
                }
            }
        }
    }

    private void preProcessDefinitionDecision(final Decision decision) {
        if (decision != null) {
            if (decision.getDecisionDelegation() != null) {
                final String decisionDelegationClassName = decision.getDecisionDelegation().getClassName();

                if (decisionDelegationClassName.equals(SpringDecisionHandler.class.getName())) {
                    decision.getDecisionDelegation().setClassName(TestSpringDecisionHandler.class.getName());
                }
            }
        }

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public void startProcess(final Map<String, Object> variabelen) {

        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate(processName);
            processInstanceId = processInstance.getId();
            for (final Map.Entry<String, Object> entry : variabelen.entrySet()) {
                processInstance.getContextInstance().setVariable(entry.getKey(), entry.getValue());
            }

            processInstance.signal();
        } finally {

            jbpmContext.close();
        }

    }

    public void startProcess(final Bericht bericht) {
        testBerichtFormat(bericht);
        /*
         * De variabele mapping moet overeen komen met het starten van een JBPM process in de jboss-esb.xml <mapping
         * esb="BODY_CONTENT" bpm="input" />
         */

        final Map<String, Object> variabelen = new HashMap<String, Object>();
        variabelen.put("input", bericht);

        startProcess(variabelen);
    }

    public void signalProcess() {
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            processInstance.signal();
        } finally {
            jbpmContext.close();
        }
    }

    public void signalBrp(final Bericht bericht) {
        signalProcess(bericht, "brpBericht");
    }

    public void signalVospg(final Bericht bericht) {
        signalProcess(bericht, "vospgBericht");
    }

    public void signalSync(final Bericht bericht) {
        signalProcess(bericht, "syncBericht");
    }

    public void signalProcess(final Bericht bericht, final String jbpmVariable) {
        testBerichtFormat(bericht);
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance;
            if (bericht != null) {
                processInstance = jbpmContext.loadProcessInstanceForUpdate(processInstanceId);
                // Bericht toevoegen als variabele
                processInstance.getContextInstance().setVariable(jbpmVariable, bericht);
            } else {
                processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            }

            final Long tokenId;
            if (bericht.getCorrelationId() != null) {
                tokenId = correlatie.get(bericht.getCorrelationId());

                final Token token = jbpmContext.getToken(tokenId);
                token.signal();

            } else {

                processInstance.signal();
            }
            // CHECKSTYLE:OFF catch Throwable toegevoegd om de stacktrace te krijgen wanneer er in test iets fout gaat.
        } catch (final Throwable t) {
            // CHECKSTYLE:ON
            t.printStackTrace();
            throw new RuntimeException(t);
        } finally {
            jbpmContext.close();
        }
    }

    public void signalProcess(final String transition) {
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstanceForUpdate(processInstanceId);
            processInstance.signal(transition);
        } finally {
            jbpmContext.close();
        }

    }

    public boolean processEnded() {
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);

            return processInstance.hasEnded();
        } finally {

            jbpmContext.close();
        }
    }

    public boolean processHumanTask() {
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            final ProcessInstance subProcessInstance = processInstance.getRootToken().getSubProcessInstance();
            if (subProcessInstance == null) {
                return false;
            }

            final Token subProcessToken = subProcessInstance.getRootToken();
            final Node subProcessNode = subProcessToken.getNode();
            final NodeType subProcessNodeType = subProcessNode.getNodeType();

            return "Task".equals(subProcessNodeType.toString());
        } finally {

            jbpmContext.close();
        }

    }

    public void signalHumanTask(final String transition) {
        Assert.assertTrue(processHumanTask());
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            final ProcessInstance subProcessInstance = processInstance.getRootToken().getSubProcessInstance();
            subProcessInstance.getContextInstance().setVariable("restart", transition);
            subProcessInstance.signal();

        } finally {

            jbpmContext.close();
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public List<MviBericht> getMviBerichten() {
        return mviBerichten;
    }

    public List<Lo3Bericht> getVospgBerichten() {
        return vospgBerichten;
    }

    public List<BrpBericht> getBrpBerichten() {
        return brpBerichten;
    }

    public List<SyncBericht> getSyncBerichten() {
        return syncBerichten;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void testBerichtFormat(final Bericht bericht) {
        try {
            LOG.info("Testing bericht: {}", bericht);
            final String formatted = bericht.format();
            LOG.info("Formatted: {}", formatted);

            final Bericht parsed;
            if (bericht instanceof MviBericht) {
                parsed = new MviBerichtFactory().getBericht(formatted);
            } else if (bericht instanceof BrpBericht) {
                parsed = BrpBerichtFactory.SINGLETON.getBericht(formatted);
                // ((BrpBericht) parsed).setBrpGemeente(((BrpBericht) bericht).getBrpGemeente());
                // ((BrpBericht) parsed).setLo3Gemeente(((BrpBericht) bericht).getLo3Gemeente());
            } else if (bericht instanceof Lo3Bericht) {
                parsed = new Lo3BerichtFactory().getBericht(formatted);
                ((Lo3Bericht) parsed).setBronGemeente(((Lo3Bericht) bericht).getBronGemeente());
                ((Lo3Bericht) parsed).setDoelGemeente(((Lo3Bericht) bericht).getDoelGemeente());
            } else if (bericht instanceof SyncBericht) {
                parsed = SyncBerichtFactory.SINGLETON.getBericht(formatted);
            } else {
                throw new AssertionError("Onbekend bericht type.");
            }
            parsed.setMessageId(bericht.getMessageId());
            parsed.setCorrelationId(bericht.getCorrelationId());
            LOG.info("Parsed: {}", parsed);

            Assert.assertEquals(bericht, parsed);
        } catch (final BerichtInhoudException e) {
            throw new AssertionError(e);
        } catch (final UnsupportedOperationException unsupportedOperationException) {
            return;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    protected void checkBerichten(final int brp, final int mvi, final int vospg, final int sync) {
        Assert.assertEquals(brp + " BRP berichten verwacht", brp, getBrpBerichten().size());
        Assert.assertEquals(mvi + " MVI berichten verwacht", mvi, getMviBerichten().size());
        Assert.assertEquals(vospg + " VOSPG berichten verwacht", vospg, getVospgBerichten().size());
        Assert.assertEquals(sync + " sync berichten verwacht", sync, getSyncBerichten().size());
    }

    // CHECKSTYLE:OFF - Return count
    protected <T extends Bericht> T getBericht(final Class<T> berichtClass) {
        if (SyncBericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen sync berichten aanwezig", !getSyncBerichten().isEmpty());
            final SyncBericht syncBericht = getSyncBerichten().remove(0);
            if (berichtClass.isInstance(syncBericht)) {
                return (T) syncBericht;
            } else {
                throw new AssertionError("Bericht is niet van verwacht type");
            }
        } else if (MviBericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen mvi berichten aanwezig", !getMviBerichten().isEmpty());
            final MviBericht mviBericht = getMviBerichten().remove(0);
            if (berichtClass.isInstance(mviBericht)) {
                return (T) mviBericht;
            } else {
                throw new AssertionError("Bericht is niet van verwacht type");
            }
        } else if (BrpBericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen brp berichten aanwezig", !getBrpBerichten().isEmpty());
            final BrpBericht brpBericht = getBrpBerichten().remove(0);
            if (berichtClass.isInstance(brpBericht)) {
                return (T) brpBericht;
            } else {
                throw new AssertionError("Bericht is niet van verwacht type");
            }
        } else if (Lo3Bericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen vospg berichten aanwezig", !getVospgBerichten().isEmpty());
            final Lo3Bericht lo3Bericht = getVospgBerichten().remove(0);
            if (berichtClass.isInstance(lo3Bericht)) {
                return (T) lo3Bericht;
            } else {
                throw new AssertionError("Bericht is niet van verwacht type (verwacht=" + berichtClass.getName()
                        + ", ontvangen=" + lo3Bericht.getClass().getName() + ")");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected <T extends Bericht> void forceerTimeout(final Class<T> berichtClass, final String kanaal) {

        final int maxAantalHerhalingen;
        if ("BRP".equalsIgnoreCase(kanaal)) {
            maxAantalHerhalingen = BRP_MAX_HERHALINGEN;
        } else if ("VOSPG".equalsIgnoreCase(kanaal)) {
            maxAantalHerhalingen = VOSPG_MAX_HERHALINGEN;
        } else if ("SYNC".equalsIgnoreCase(kanaal)) {
            maxAantalHerhalingen = SYNC_MAX_HERHALINGEN;
        } else if ("MVI".equalsIgnoreCase(kanaal)) {
            maxAantalHerhalingen = 0;
        } else {
            throw new IllegalArgumentException("onbekend kanaal: " + kanaal);
        }

        int poging = 0;
        while (poging++ < maxAantalHerhalingen) {
            signalProcess("timeout");
            checkBerichten(kanaal, 1, true);
            final T herhalingBericht = getBericht(berichtClass);
            Assert.assertNotNull(herhalingBericht);
        }

        // deze timeout gaat over de maxHerhalingen heen
        signalProcess("timeout");
    }

    private void checkBerichten(final String kanaal, final int aantalBerichten, final boolean andereKanalenLeeg) {

        // @formatter:off
        if ("BRP".equalsIgnoreCase(kanaal)) {
            Assert.assertEquals(aantalBerichten + " " + kanaal + " berichten verwacht", aantalBerichten, getBrpBerichten().size());
            if(andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "VOSPG" + " berichten verwacht", 0, getVospgBerichten().size());
                Assert.assertEquals(0 + " " + "SYNC" + " berichten verwacht", 0, getSyncBerichten().size());
                Assert.assertEquals(0 + " " + "MVI" + " berichten verwacht", 0, getMviBerichten().size());                
            }
        } else if ("VOSPG".equalsIgnoreCase(kanaal)) {
            Assert.assertEquals(aantalBerichten + " " + kanaal + " berichten verwacht", aantalBerichten, getVospgBerichten().size());
            if(andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "BRP" + " berichten verwacht", 0, getBrpBerichten().size());
                Assert.assertEquals(0 + " " + "SYNC" + " berichten verwacht", 0, getSyncBerichten().size());
                Assert.assertEquals(0 + " " + "MVI" + " berichten verwacht", 0, getMviBerichten().size());                
            }
        } else if ("SYNC".equalsIgnoreCase(kanaal)) {
            Assert.assertEquals(aantalBerichten + " " + kanaal + " berichten verwacht", aantalBerichten, getSyncBerichten().size());
            if(andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "BRP" + " berichten verwacht", 0, getBrpBerichten().size());
                Assert.assertEquals(0 + " " + "VOSPG" + " berichten verwacht", 0, getVospgBerichten().size());
                Assert.assertEquals(0 + " " + "MVI" + " berichten verwacht", 0, getMviBerichten().size());                
            }
        } else if("MVI".equalsIgnoreCase(kanaal)) {
            Assert.assertEquals(aantalBerichten + " " + kanaal + " berichten verwacht", aantalBerichten, getMviBerichten().size());
            if(andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "BRP" + " berichten verwacht", 0, getBrpBerichten().size());
                Assert.assertEquals(0 + " " + "VOSPG" + " berichten verwacht", 0, getVospgBerichten().size());
                Assert.assertEquals(0 + " " + "SYNC" + " berichten verwacht", 0, getSyncBerichten().size());                
            }
        } else {
            throw new IllegalArgumentException("onbekend kanaal: " + kanaal);
        }
        // @formatter:on
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static class TestSpringActionHandler implements ActionHandler {
        private static final long serialVersionUID = 1L;

        // CHECKSTYLE:OFF
        static AbstractJbpmTest testRef;
        // CHECKSTYLE:ON

        private String bean;

        @Override
        public void execute(final ExecutionContext ec) throws Exception {
            LOG.info("TestSpringActionHandler.execute; bean=" + bean);

            // Lookup bean in application context
            final SpringAction springTask = testRef.applicationContext.getBean(bean, SpringAction.class);

            ExecutionContext.pushCurrentContext(ec);

            // Executes
            final Map<String, Object> result = springTask.execute(ec.getContextInstance().getVariables());
            if (result != null) {
                if (result.containsKey("input")) {
                    throw new IllegalArgumentException(
                            "Het is niet toegestaan de variabele met key \"input\" te overschrijven!\nOriginele value: "
                                    + ec.getContextInstance().getVariables().get("input") + ", afgekeurde value: "
                                    + result.get("input") + ".");
                }
                ec.getContextInstance().addVariables(result);
            }

            ExecutionContext.popCurrentContext(ec);

            // Notify process instance that work item has been completed
            if (!(springTask instanceof NoSignal)) {
                ec.getToken().signal();
            }
        }

        /**
         * @param bean
         *            the bean to set
         */
        public void setBean(final String bean) {
            this.bean = bean;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static class TestSpringDecisionHandler implements DecisionHandler {
        private static final long serialVersionUID = 1L;

        // CHECKSTYLE:OFF
        static AbstractJbpmTest testRef;
        // CHECKSTYLE:ON

        private String bean;

        @Override
        public String decide(final ExecutionContext executionContext) throws Exception {
            // Lookup bean in application context
            final SpringDecision springTask = testRef.applicationContext.getBean(bean, SpringDecision.class);

            // Executes
            return springTask.execute(executionContext.getContextInstance().getVariables());
        }

        /**
         * @param bean
         *            the bean to set
         */
        public void setBean(final String bean) {
            this.bean = bean;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static class TestEsbActionHandler implements ActionHandler {
        private static final long serialVersionUID = 1L;

        private String esbServiceName;
        @SuppressWarnings("unused")
        private String esbCategoryName;
        private DefaultElement bpmToEsbVars;
        private DefaultElement esbToBpmVars;

        // CHECKSTYLE:OFF
        static AbstractJbpmTest testRef;

        // CHECKSTYLE:ON

        @Override
        public void execute(final ExecutionContext executionContext) throws Exception {

            final Message message =
                    new JBpmObjectMapper().mapFromJBpmToEsbMessage(bpmToEsbVars, false, executionContext);
            final Bericht bericht = (Bericht) message.getBody().get();
            if (bericht == null) {
                return;
            }

            testBerichtFormat(bericht);

            testRef.correlatie.put(bericht.getMessageId(), executionContext.getToken().getId());

            if ("MVI-Outbound".equals(esbServiceName)) {
                testRef.mviBerichten.add((MviBericht) bericht);
            } else if ("BRP-Outbound".equals(esbServiceName)) {
                testRef.brpBerichten.add((BrpBericht) bericht);
            } else if ("VOSPG-Outbound".equals(esbServiceName)) {
                testRef.vospgBerichten.add((Lo3Bericht) bericht);
            } else if ("Sync-Outbound".equals(esbServiceName)) {
                testRef.syncBerichten.add((SyncBericht) bericht);
            } else {
                throw new RuntimeException("Onbekende ESB Handler service voor test");
            }
        }

        public void setEsbCategoryName(final String esbCategoryName) {
            this.esbCategoryName = esbCategoryName;
        }

        public void setEsbServiceName(final String esbServiceName) {
            this.esbServiceName = esbServiceName;
        }

        public DefaultElement getBpmToEsbVars() {
            return bpmToEsbVars;
        }

        public void setBpmToEsbVars(final DefaultElement bpmToEsbVars) {
            this.bpmToEsbVars = bpmToEsbVars;
        }

        public DefaultElement getEsbToBpmVars() {
            return esbToBpmVars;
        }

        public void setEsbToBpmVars(final DefaultElement esbToBpmVars) {
            this.esbToBpmVars = esbToBpmVars;
        }
    }

}
