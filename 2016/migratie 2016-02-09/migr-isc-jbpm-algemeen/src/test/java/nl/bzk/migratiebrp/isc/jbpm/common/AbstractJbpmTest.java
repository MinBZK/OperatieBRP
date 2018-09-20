/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageId;
import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.isc.IscBericht;
import nl.bzk.migratiebrp.bericht.model.isc.factory.IscBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.register.Autorisatie;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.isc.jbpm.common.actionhandler.EsbActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.common.actionhandler.OutboundHandler;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import nl.bzk.migratiebrp.register.client.AutorisatieService;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Node.NodeType;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.job.Job;
import org.jbpm.jpdl.xml.JpdlXmlReader;
import org.jbpm.jpdl.xml.Problem;
import org.jbpm.jpdl.xml.ProblemListener;
import org.jbpm.msg.MessageService;
import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;
import org.jbpm.svc.Services;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(value = "iscTransactionManager")
@Rollback(value = false)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration({"classpath:test-datasource.xml",
                       "classpath:test-jta.xml",
                       "classpath:isc-jbpm-algemeen.xml",
                       "classpath:test-outbound.xml",
                       "classpath*:isc-jbpm-usecase-beans.xml" })
public abstract class AbstractJbpmTest {

    /** Aantal herhaling geconfigureerd voor BRP. */
    public static final int BRP_MAX_HERHALINGEN = 2;
    /** Aantal herhaling geconfigureerd voor VOSPG. */
    public static final int VOSPG_MAX_HERHALINGEN = 5;

    protected static final Logger LOG = LoggerFactory.getLogger();

    private static final int BRP_TIMEOUT = 4;
    private static final int VOSPG_TIMEOUT = 8;
    private static final int LOCK_TIMEOUT = 1;

    private static final TestcaseOutputter MIGR_TEST_ISC_OUTPUTTER = new TestcaseOutputter();

    @Autowired
    @Qualifier("iscDataSource")
    protected DataSource iscDataSource;

    @Inject
    protected JbpmConfiguration jbpmConfiguration;
    @Inject
    protected SessionFactory sessionFactory;

    @Autowired
    private BerichtenDao berichtenDao;

    private final List<Lo3Bericht> vospgBerichten = new ArrayList<>();
    private final List<BrpBericht> brpBerichten = new ArrayList<>();
    private final List<SyncBericht> syncBerichten = new ArrayList<>();

    private final Map<String, Long> correlatie = new HashMap<>();

    private final String processDefinitionXml;
    private String processName;
    private Long processInstanceId;

    // jUnit rule werkt niet via getter/setter
    @Rule
    public final TestName testName = new TestName();

    public AbstractJbpmTest(final String processDefinitionXml) {
        this.processDefinitionXml = processDefinitionXml;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @SuppressWarnings("unchecked")
    private JbpmContext createJbpmContext() {
        final JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        // Message service 'disablen', anders gaan subprocessen niet goed
        jbpmContext.getServices().getServiceFactories().put(Services.SERVICENAME_MESSAGE, new NoopMessageServiceFactory());

        return jbpmContext;
    }

    @BeforeTransaction
    public void setupDatabase() {
        try (Connection connection = iscDataSource.getConnection()) {
            final Statement statement = connection.createStatement();

            executeScript(statement, "/sql/mig-drop.sql", true);
            // Ignore failures, vanwege initially deferred fk constraint in de scripts.
            // Dit statement faalt op een H2 database. Tests draaien prima zonder FK.
            executeScript(statement, "/sql/mig-create.sql", true);
            executeScript(statement, "/sql/mig-data.sql", false);

            statement.execute("delete from mig_configuratie");
            statement.execute("insert into mig_configuratie(configuratie, waarde) values ('brp.timeout', '" + BRP_TIMEOUT + " hours')");
            statement.execute("insert into mig_configuratie(configuratie, waarde) values ('brp.herhalingen', '" + BRP_MAX_HERHALINGEN + "')");
            statement.execute("insert into mig_configuratie(configuratie, waarde) values ('vospg.timeout', '" + VOSPG_TIMEOUT + " hours')");
            statement.execute("insert into mig_configuratie(configuratie, waarde) values ('vospg.herhalingen', '" + VOSPG_MAX_HERHALINGEN + "')");
            statement.execute("insert into mig_configuratie(configuratie, waarde) values ('lock.timeout', '" + LOCK_TIMEOUT + " hours')");

            statement.close();

        } catch (final
            IOException
            | SQLException e)
        {
            Assert.fail("Kon database setup niet uitvoeren: " + e.getMessage());
        }

        // Opschonen hibernate caches
        sessionFactory.getCache().evictAllRegions();
    }

    @Before
    public void setupJbpm() {
        final JbpmContext jbpmContext = createJbpmContext();
        try {
            final String[] processDefXmls = processDefinitionXml.split(",");

            for (final String processDefXml : processDefXmls) {
                LOG.info("Deploying process definition: " + processDefXml);

                // Setup process definition
                final JpdlXmlReader jpdlReader =
                        new JpdlXmlReader(new InputSource(this.getClass().getResourceAsStream(processDefXml)), new ProblemListener()
                {
                            @Override
                            public void addProblem(final Problem problem) {
                                LOG.error(problem.toString());
                            }
                        });
                final ProcessDefinition processDefinition = jpdlReader.readProcessDefinition();
                jbpmContext.deployProcessDefinition(processDefinition);
                // Take first to do test on
                if (processName == null) {
                    processName = processDefinition.getName();
                }
            }

            // Setup database
            // final Session session = (Session)
            // jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            // final Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
            // @Override
            // public Connection execute(final Connection connection) throws SQLException {
            // return connection;
            // }
            // });

        } finally {
            jbpmContext.close();
        }

    }

    private void executeScript(final Statement statement, final String scriptResource, final boolean ignoreException) throws IOException, SQLException {
        final Resource script = new ClassPathResource(scriptResource);
        final String scriptContents = IOUtils.toString(script.getInputStream());

        final List<String> statements = new ArrayList<>();
        ScriptUtils.splitSqlScript(scriptContents, ';', statements);

        for (final String sql : statements) {
            try {
                statement.execute(sql);
            } catch (final SQLException e) {
                if (!ignoreException) {
                    throw e;
                }
            }
        }
    }

    @After
    @Transactional(value = "iscTransactionManager")
    public void cleanupJbpm() {
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            // Drop database
            final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            final Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
                @Override
                public Connection execute(final Connection connection) throws SQLException {
                    return connection;
                }
            });

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

    private Long bewaarBericht(final Bericht bericht, final JbpmContext jbpmContext) {
        // Sla bericht op op de 'esb'-manier.
        final String SQL =
                "insert into mig_bericht(tijdstip, correlation_id, "
                           + "verzendende_partij, ontvangende_partij, bericht, naam, kanaal) "
                           + "values(?, ?, ?, ?, ?, ?, ?)";

        final Long berichtId;
        final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
        final Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
            @Override
            public Connection execute(final Connection connection) throws SQLException {
                return connection;
            }
        });
        // jbpmContext.getConnection()
        try (final PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setString(2, bericht.getCorrelationId());
            statement.setString(3, bericht instanceof Lo3Bericht ? ((Lo3Bericht) bericht).getBronGemeente() : null);
            statement.setString(4, bericht instanceof Lo3Bericht ? ((Lo3Bericht) bericht).getDoelGemeente() : null);
            statement.setString(5, bericht.format());
            statement.setString(6, bericht.getBerichtType());
            statement.setString(7, getKanaal(bericht));
            statement.executeUpdate();

            final ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            berichtId = rs.getLong(1);

        } catch (final
            SQLException
            | BerichtInhoudException e)
        {
            throw new RuntimeException("Probleem bij opslaan bericht", e);
        }

        // Bepaal message-id
        bericht.setMessageId(MessageId.bepaalMessageId(berichtId));

        // Update message-id
        try (final PreparedStatement statement = connection.prepareStatement("update mig_bericht set message_id = ? where id = ?")) {

            statement.setString(1, bericht.getMessageId());
            statement.setLong(2, berichtId);
            statement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException("Probleem bij updaten msg-id voor bericht", e);
        }

        return berichtId;

    }

    @Transactional(value = "iscTransactionManager")
    protected Bericht leesBericht(final Long berichtId) {
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            // Haal bericht op via 'normale' dao.
            return berichtenDao.leesBericht(berichtId);
        } finally {
            jbpmContext.close();
        }
    }

    private String getKanaal(final Bericht bericht) {
        if (bericht instanceof SyncBericht) {
            return "SYNC";
        } else if (bericht instanceof Lo3Bericht) {
            return "VOSPG";
        } else if (bericht instanceof BrpBericht) {
            return "BRP";
        } else if (bericht instanceof IscBericht) {
            return "ISC";
        } else {
            throw new IllegalArgumentException("Onbekend type bericht");
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /**
     * Start het proces met als 'input' variabele het meegegeven bericht.
     *
     * @param bericht
     */
    @Transactional(value = "iscTransactionManager")
    public void startProcess(final Bericht bericht) {

        final JbpmContext jbpmContext = createJbpmContext();

        try {
            // Sla berichten op in de MIG_BERICHTEN tabel (ESB variant)
            final Long berichtId = bewaarBericht(bericht, jbpmContext);

            // Nu bericht outputten want het messageId kan aangepast zijn.
            MIGR_TEST_ISC_OUTPUTTER.outputSignalBericht(bericht);
            AbstractJbpmTest.testBerichtFormat(bericht);

            final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate(processName);
            jbpmContext.save(processInstance);
            final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            session.flush();

            processInstanceId = processInstance.getId();
            LOG.info("Process instance: {}", processInstanceId);

            /*
             * De variabele mapping moet overeen komen met het starten van een JBPM process in de jboss-esb.xml <mapping
             * esb="BODY_CONTENT" bpm="input" />
             */
            processInstance.getContextInstance().setVariable("input", berichtId);

            processInstance.signal();
        } finally {

            jbpmContext.close();
        }

    }

    /**
     * Start het proces met als 'input' variabele de meegegeven inputVar.
     *
     * @param inputVar
     *            Object
     */
    @Transactional(value = "iscTransactionManager")
    public void startProcess(final Object inputVar) {
        final JbpmContext jbpmContext = createJbpmContext();
        try {
            final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate(processName);
            jbpmContext.save(processInstance);
            final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
            session.flush();

            processInstanceId = processInstance.getId();
            /*
             * De variabele mapping moet overeen komen met het starten van een JBPM process in de jboss-esb.xml <mapping
             * esb="BODY_CONTENT" bpm="input" />
             */
            processInstance.getContextInstance().setVariable("input", inputVar);
            processInstance.signal();
        } finally {
            jbpmContext.close();
        }
    }

    @Transactional(value = "iscTransactionManager")
    public void signalProcess() {
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            processInstance.signal();
        } finally {
            jbpmContext.close();
        }
    }

    @Transactional(value = "iscTransactionManager")
    public void signalBrp(final Bericht bericht) {
        Assert.assertTrue("signalBrp moet worden aangeroepen met een BrpBericht", bericht instanceof BrpBericht);
        signalProcess(bericht, "brpBericht");
    }

    @Transactional(value = "iscTransactionManager")
    public void signalVospg(final Bericht bericht) {
        Assert.assertTrue("signalVospg moet worden aangeroepen met een Lo3Bericht", bericht instanceof Lo3Bericht);
        signalProcess(bericht, "vospgBericht");
    }

    @Transactional(value = "iscTransactionManager")
    public void signalSync(final Bericht bericht) {
        Assert.assertTrue("signalSync moet worden aangeroepen met een SyncBericht", bericht instanceof SyncBericht);
        signalProcess(bericht, "syncBericht");
    }

    @Transactional(value = "iscTransactionManager")
    public void signalProcess(final Bericht bericht, final String jbpmVariable) {
        MIGR_TEST_ISC_OUTPUTTER.outputSignalBericht(bericht);

        AbstractJbpmTest.testBerichtFormat(bericht);
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            ProcessInstance processInstance = jbpmContext.loadProcessInstanceForUpdate(processInstanceId);

            final ProcessInstance subProcessInstance = processInstance.getRootToken().getSubProcessInstance();
            if (subProcessInstance != null) {
                processInstance = subProcessInstance;
            }

            final Token token;
            if (bericht != null && bericht.getCorrelationId() != null) {
                final Long tokenId = correlatie.get(bericht.getCorrelationId());

                token = jbpmContext.getToken(tokenId);
            } else {
                token = processInstance.getRootToken();
            }

            if (bericht != null) {
                // Sla berichten op in de MIG_BERICHTEN tabel
                final Long berichtId = bewaarBericht(bericht, jbpmContext);

                // Bericht id en type toevoegen als variabele
                processInstance.getContextInstance().setVariable(jbpmVariable, berichtId, token);
                processInstance.getContextInstance().setVariable(jbpmVariable + "Type", bericht.getBerichtType(), token);
            }

            token.signal();
        } catch (final Throwable t /*
                                    * catch Throwable toegevoegd om de stacktrace te krijgen wanneer er in test iets
                                    * fout gaat
                                    */) {
            t.printStackTrace();
            throw new RuntimeException(t);
        } finally {
            jbpmContext.close();
        }
    }

    @Transactional(value = "iscTransactionManager")
    public void signalProcess(final String transition) {
        MIGR_TEST_ISC_OUTPUTTER.outputTransition(transition);

        final JbpmContext jbpmContext = createJbpmContext();

        try {
            ProcessInstance processInstance = jbpmContext.loadProcessInstanceForUpdate(processInstanceId);
            final ProcessInstance subProcessInstance = processInstance.getRootToken().getSubProcessInstance();
            if (subProcessInstance != null) {
                processInstance = subProcessInstance;
            }
            processInstance.signal(transition);
        } finally {
            jbpmContext.close();
        }
    }

    @Transactional(value = "iscTransactionManager")
    public boolean processEnded() {
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);

            return processInstance.hasEnded();
        } finally {

            jbpmContext.close();
        }
    }

    @Transactional(value = "iscTransactionManager")
    public boolean processHumanTask() {
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            final ProcessInstance subProcessInstance = processInstance.getRootToken().getSubProcessInstance();
            // if (subProcessInstance == null) {
            // return false;
            // }

            final Token token = subProcessInstance == null ? processInstance.getRootToken() : subProcessInstance.getRootToken();
            final Node node = token.getNode();
            final NodeType nodeType = node.getNodeType();

            return "Task".equals(nodeType.toString());
        } finally {

            jbpmContext.close();
        }

    }

    @Transactional(value = "iscTransactionManager")
    public void signalHumanTask(final String transition) {
        MIGR_TEST_ISC_OUTPUTTER.outputHumanTask(transition);

        Assert.assertTrue("Proces wacht niet op een beheerderactie (humantask)", processHumanTask());
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            final ProcessInstance subProcessInstance = processInstance.getRootToken().getSubProcessInstance();

            final ContextInstance contextInstance =
                    subProcessInstance == null ? processInstance.getContextInstance() : subProcessInstance.getContextInstance();

            // Controleer foutpaden
            final FoutafhandelingPaden foutafhandelingPaden = (FoutafhandelingPaden) contextInstance.getVariable("foutafhandelingPaden");
            LOG.info("signalHumanTask: foutafhandelingPaden={}", foutafhandelingPaden);
            if (!foutafhandelingPaden.getSelectItems().containsValue(transition)) {
                throw new IllegalArgumentException(
                    "Transitie '" + transition + "' onbekend (paden=" + foutafhandelingPaden.getSelectItems().values() + ").");
            }

            contextInstance.setVariable("restart", transition);

            if (subProcessInstance == null) {
                processInstance.signal();
            } else {
                subProcessInstance.signal();
            }

        } finally {

            jbpmContext.close();
        }
    }

    @Transactional(value = "iscTransactionManager")
    protected void checkVariabele(final String naam, final Object waarde) {
        Assert.assertTrue("Proces wacht niet op een beheerderactie (humantask)", processHumanTask());
        final JbpmContext jbpmContext = createJbpmContext();

        try {
            final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
            final ContextInstance contextInstance = processInstance.getContextInstance();
            final Object variableValue = contextInstance.getVariable(naam);

            Assert.assertEquals("Variabele " + naam + " heeft niet de gewenste waarde.", waarde, variableValue);
        } finally {
            jbpmContext.close();
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van vospg berichten.
     *
     * @return vospg berichten
     */
    public List<Lo3Bericht> getVospgBerichten() {
        return vospgBerichten;
    }

    /**
     * Geef de waarde van brp berichten.
     *
     * @return brp berichten
     */
    public List<BrpBericht> getBrpBerichten() {
        return brpBerichten;
    }

    /**
     * Geef de waarde van sync berichten.
     *
     * @return sync berichten
     */
    public List<SyncBericht> getSyncBerichten() {
        return syncBerichten;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void testBerichtFormat(final Bericht bericht) {

        if (bericht instanceof AbstractOngeldigBericht || bericht instanceof AbstractOnbekendBericht) {
            return;
        }

        try {
            LOG.info("Testing bericht: {}", bericht);
            final String formatted = bericht.format();
            LOG.info("Formatted: {}", formatted);

            final Bericht parsed;
            if (bericht instanceof BrpBericht) {
                parsed = BrpBerichtFactory.SINGLETON.getBericht(formatted);
            } else if (bericht instanceof Lo3Bericht) {
                parsed = new Lo3BerichtFactory().getBericht(formatted);
                ((Lo3Bericht) parsed).setBronGemeente(((Lo3Bericht) bericht).getBronGemeente());
                ((Lo3Bericht) parsed).setDoelGemeente(((Lo3Bericht) bericht).getDoelGemeente());
            } else if (bericht instanceof SyncBericht) {
                parsed = SyncBerichtFactory.SINGLETON.getBericht(formatted);
            } else if (bericht instanceof IscBericht) {
                parsed = IscBerichtFactory.SINGLETON.getBericht(formatted);
            } else {
                throw new AssertionError("Onbekend bericht type.");
            }
            parsed.setMessageId(bericht.getMessageId());
            parsed.setCorrelationId(bericht.getCorrelationId());
            LOG.info("Parsed: {}", parsed);

            Assert.assertEquals("Bericht test (format/parse) niet ok", bericht, parsed);
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

    protected void controleerBerichten(final int brp, final int vospg, final int sync) {
        Assert.assertEquals(brp + " BRP berichten verwacht", brp, getBrpBerichten().size());
        Assert.assertEquals(vospg + " VOSPG berichten verwacht", vospg, getVospgBerichten().size());
        Assert.assertEquals(sync + " sync berichten verwacht", sync, getSyncBerichten().size());
    }

    @SuppressWarnings("unchecked")
    protected <T extends Bericht> T getBericht(final Class<T> berichtClass) {
        if (SyncBericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen sync berichten aanwezig", !getSyncBerichten().isEmpty());
            final SyncBericht syncBericht = getSyncBerichten().remove(0);
            if (berichtClass.isInstance(syncBericht)) {
                MIGR_TEST_ISC_OUTPUTTER.outputGetBericht(syncBericht);
                return (T) syncBericht;
            } else {
                throw new AssertionError(
                    "Bericht is niet van verwacht type (verwacht=" + berichtClass.getName() + ", ontvangen=" + syncBericht.getClass().getName() + ")");
            }
        } else if (BrpBericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen brp berichten aanwezig", !getBrpBerichten().isEmpty());
            final BrpBericht brpBericht = getBrpBerichten().remove(0);
            if (berichtClass.isInstance(brpBericht)) {
                MIGR_TEST_ISC_OUTPUTTER.outputGetBericht(brpBericht);
                return (T) brpBericht;
            } else {
                throw new AssertionError(
                    "Bericht is niet van verwacht type (verwacht=" + berichtClass.getName() + ", ontvangen=" + brpBericht.getClass().getName() + ")");
            }
        } else if (Lo3Bericht.class.isAssignableFrom(berichtClass)) {
            Assert.assertTrue("Geen vospg berichten aanwezig", !getVospgBerichten().isEmpty());
            final Lo3Bericht lo3Bericht = getVospgBerichten().remove(0);
            if (berichtClass.isInstance(lo3Bericht)) {
                MIGR_TEST_ISC_OUTPUTTER.outputGetBericht(lo3Bericht);
                return (T) lo3Bericht;
            } else {
                throw new AssertionError(
                    "Bericht is niet van verwacht type (verwacht=" + berichtClass.getName() + ", ontvangen=" + lo3Bericht.getClass().getName() + ")");
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
            if (andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "VOSPG" + " berichten verwacht", 0, getVospgBerichten().size());
                Assert.assertEquals(0 + " " + "SYNC" + " berichten verwacht", 0, getSyncBerichten().size());
            }
        } else if ("VOSPG".equalsIgnoreCase(kanaal)) {
            Assert.assertEquals(aantalBerichten + " " + kanaal + " berichten verwacht", aantalBerichten, getVospgBerichten().size());
            if (andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "BRP" + " berichten verwacht", 0, getBrpBerichten().size());
                Assert.assertEquals(0 + " " + "SYNC" + " berichten verwacht", 0, getSyncBerichten().size());
            }
        } else if ("SYNC".equalsIgnoreCase(kanaal)) {
            Assert.assertEquals(aantalBerichten + " " + kanaal + " berichten verwacht", aantalBerichten, getSyncBerichten().size());
            if (andereKanalenLeeg) {
                Assert.assertEquals(0 + " " + "BRP" + " berichten verwacht", 0, getBrpBerichten().size());
                Assert.assertEquals(0 + " " + "VOSPG" + " berichten verwacht", 0, getVospgBerichten().size());
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

    @Transactional(value = "iscTransactionManager")
    protected void executeInJbpmContext(final JbpmWorker worker) {
        final JbpmContext jbpmContext = createJbpmContext();
        try {
            worker.execute(jbpmContext, processInstanceId);
        } finally {
            jbpmContext.close();
        }
    }

    public static interface JbpmWorker {
        public void execute(JbpmContext context, Long processInstanceId);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @AfterClass
    public static void disableOutputter() {
        try {
            if (MIGR_TEST_ISC_OUTPUTTER.isOutputting()) {
                throw new AssertionError("TEST NIET COMMITTEN MET MIGR-TEST-ISC FLOW OUTPUT!!!!!");
            }
        } finally {
            MIGR_TEST_ISC_OUTPUTTER.disableOutput();
        }
    }

    @Before
    public void registerTestcaseStartInOutputter() {
        MIGR_TEST_ISC_OUTPUTTER.startTestcase(this.getClass().getSimpleName() + "-" + testName.getMethodName());
    }

    /**
     * Zet de waarde van output berichten.
     *
     * @param outputDirectory
     *            output berichten
     */
    protected static void setOutputBerichten(final String outputDirectory) {
        MIGR_TEST_ISC_OUTPUTTER.enableOutput(outputDirectory);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Before
    public void setupRefToStaticTestOutboundHandler() {
        TestOutboundHandler.testRef = this;
    }

    public static final class TestOutboundHandler implements OutboundHandler {

        static AbstractJbpmTest testRef;

        @Override
        public void handleMessage(final String serviceName, final String bodyContent, final Map<String, Object> attributes) {

            final Long berichtId = Long.valueOf(bodyContent);
            if (berichtId == null) {
                return;
            }

            final String kanaal;
            switch (serviceName) {
                case "BRP-Outbound":
                    kanaal = "BRP";
                    break;
                case "VOSPG-Outbound":
                    kanaal = "VOSPG";
                    break;
                case "Sync-Outbound":
                    kanaal = "SYNC";
                    break;
                case "Voisc-Outbound":
                    kanaal = "VOISC";
                    break;
                default:
                    throw new RuntimeException("Onbekende ESB Handler service voor test");
            }

            // Zet het kanaal erbij om te testen goed te laten verlopen
            final Session session =
                    (Session) testRef.jbpmConfiguration.getCurrentJbpmContext().getServices().getPersistenceService().getCustomSession(Session.class);
            final Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
                @Override
                public Connection execute(final Connection connection) throws SQLException {
                    return connection;
                }
            });
            try {
                final PreparedStatement statement = connection.prepareStatement("update mig_bericht set kanaal = ? where id = ?");
                statement.setString(1, kanaal);
                statement.setLong(2, berichtId);
                statement.execute();
                statement.close();
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }

            final Bericht bericht = testRef.berichtenDao.leesBericht(berichtId);

            AbstractJbpmTest.testBerichtFormat(bericht);

            testRef.correlatie.put(bericht.getMessageId(), (Long) attributes.get(EsbActionHandler.TOKEN_ID_ATTRIBUTE));

            switch (serviceName) {
                case "BRP-Outbound":
                    testRef.brpBerichten.add((BrpBericht) bericht);
                    break;
                case "VOSPG-Outbound":
                    testRef.vospgBerichten.add((Lo3Bericht) bericht);
                    break;
                case "Sync-Outbound":
                    testRef.syncBerichten.add((SyncBericht) bericht);
                    break;
                default:
                    throw new RuntimeException("Onbekende ESB Handler service voor test: " + serviceName);
            }
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private GemeenteRegister gemeenteRegister;

    protected void setGemeenteRegister(final GemeenteRegister gemeenteRegister) {
        this.gemeenteRegister = gemeenteRegister;
    }

    @Before
    public void setupGemeenteService() {
        TestGemeenteService.testRef = this;
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente("0599", "580599", null));
        gemeenten.add(new Gemeente("0429", "580429", null));
        gemeenten.add(new Gemeente("0699", "580699", intToDate(20090101)));
        gemeenten.add(new Gemeente("0717", "580717", null));
        gemeenteRegister = new GemeenteRegisterImpl(gemeenten);
    }

    protected Date intToDate(final int date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(date));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static final class TestGemeenteService implements GemeenteService {

        static AbstractJbpmTest testRef;

        @Override
        public GemeenteRegister geefRegister() {
            Assert.assertNotNull(testRef.gemeenteRegister);
            MIGR_TEST_ISC_OUTPUTTER.outputGemeenteRegister(testRef.gemeenteRegister);
            return testRef.gemeenteRegister;
        }

        @Override
        public void refreshRegister() {
        }

        @Override
        public void clearRegister() {
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private AutorisatieRegister autorisatieRegister;

    protected void setAutorisatieRegister(final AutorisatieRegister autorisatieRegister) {
        this.autorisatieRegister = autorisatieRegister;
    }

    @Before
    public void setupAutorisatieService() {
        TestAutorisatieService.testRef = this;
        final List<Autorisatie> autorisaties = new ArrayList<>();
        autorisaties.add(new Autorisatie("580001", 100034, 200001, 200002, null, null));
        autorisaties.add(new Autorisatie("580002", 100035, null, null, 200003, null));
        autorisatieRegister = new AutorisatieRegisterImpl(autorisaties);
    }

    public static final class TestAutorisatieService implements AutorisatieService {

        static AbstractJbpmTest testRef;

        @Override
        public AutorisatieRegister geefRegister() {
            Assert.assertNotNull(testRef.autorisatieRegister);
            MIGR_TEST_ISC_OUTPUTTER.outputAutorisatieRegister(testRef.autorisatieRegister);
            return testRef.autorisatieRegister;
        }

        @Override
        public void refreshRegister() {
        }

        @Override
        public void clearRegister() {
        }
    }

    /**
     * Dummy message service factory.
     */
    public static final class NoopMessageServiceFactory implements ServiceFactory {
        private static final long serialVersionUID = 1L;

        @Override
        public Service openService() {
            return new NoopMessageService();
        }

        @Override
        public void close() {
            // Niets
        }

    }

    /**
     * Dummy message service.
     */
    public static final class NoopMessageService implements MessageService {
        private static final long serialVersionUID = 1L;

        @Override
        public void send(final Job job) {
            // Niets
        }

        @Override
        public void close() {
            // Niets
        }

    }

}
