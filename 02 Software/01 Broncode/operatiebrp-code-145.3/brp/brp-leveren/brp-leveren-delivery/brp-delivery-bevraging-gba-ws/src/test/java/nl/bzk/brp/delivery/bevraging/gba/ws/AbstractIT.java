/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.test.dal.AbstractDBUnitUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.VraagPL;
import nl.bzk.brp.service.algemeen.PartijCodeResolver;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.binding.soap.SoapBindingFactory;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.local.LocalTransportFactory;
import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Base64Utils;

public class AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String ENDPOINT_ADDRESS_ADHOC = "local://adhocService";
    private static final String ENDPOINT_ADDRESS_VRAAGPL = "local://vraagPLService";
    private static Properties portProperties = new Properties();
    private static Server webserver;
    private static Server webserverVraagPL;
    private static GenericXmlApplicationContext databaseContext;
    private static GenericXmlApplicationContext subjectContext;
    private GenericXmlApplicationContext testContext;

    @BeforeClass
    public static void startDependencies() throws IOException, DatabaseUnitException, SQLException {
        try (ServerSocket databasePort = new ServerSocket(0)) {
            LOG.info("Configuring database to port: " + databasePort.getLocalPort());
            portProperties.setProperty("test.database.port", Integer.toString(databasePort.getLocalPort()));
        }
        try (ServerSocket brokerPort = new ServerSocket(0)) {
            LOG.info("Configuring messagebroker to port: " + brokerPort.getLocalPort());
            portProperties.setProperty("test.messagebroker.port", Integer.toString(brokerPort.getLocalPort()));
        }

        // Start DB
        databaseContext = new GenericXmlApplicationContext();
        databaseContext.load("classpath:test-embedded-database.xml");
        databaseContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        databaseContext.refresh();

        AbstractDBUnitUtil dbunit = databaseContext.getAutowireCapableBeanFactory().getBean(AbstractDBUnitUtil.class);
        dbunit.setInMemory();
        dbunit.insert(dbunit.createConnection(), AbstractIT.class, "common-fixtures.xml", "adresvraag-fixtures.xml", "persoonsvraag-fixtures.xml",
                "vraagpl-fixtures.xml");

        // Start test subject
        final Properties subjectProperties = new Properties();
        subjectProperties.setProperty("brp.bevraging.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        subjectProperties.setProperty("brp.bevraging.database.host", "localhost");
        subjectProperties.setProperty("brp.bevraging.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("brp.bevraging.database.name", "brp");
        subjectProperties.setProperty("brp.bevraging.database.username", "sa");
        subjectProperties.setProperty("brp.bevraging.database.password", "");

        subjectProperties.setProperty("jdbc.archivering.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        subjectProperties.setProperty("jdbc.archivering.database.host", "localhost");
        subjectProperties.setProperty("jdbc.archivering.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("jdbc.archivering.database.name", "brp");
        subjectProperties.setProperty("jdbc.archivering.username", "sa");
        subjectProperties.setProperty("jdbc.archivering.password", "");

        subjectProperties.setProperty("jdbc.protocollering.database.driver", "org.hsqldb.jdbc.pool.JDBCXADataSource");
        subjectProperties.setProperty("jdbc.protocollering.database.host", "localhost");
        subjectProperties.setProperty("jdbc.protocollering.database.port", portProperties.getProperty("test.database.port"));
        subjectProperties.setProperty("jdbc.protocollering.database.name", "brp");
        subjectProperties.setProperty("jdbc.protocollering.username", "sa");
        subjectProperties.setProperty("jdbc.protocollering.password", "");

        subjectContext = new GenericXmlApplicationContext();
        subjectContext.load("classpath:brp-delivery-bevraging-gba-ws.xml");
        subjectContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", subjectProperties));
        subjectContext.refresh();

        startServer(subjectContext);

        Stream.of(
                3000020, 3000021, 3000022, 3000025
        ).forEach(AbstractIT::logPersoonBlob);
    }

    @AfterClass
    public static void stopTestContext() {
        if (subjectContext != null) {
            try {
                subjectContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten SUBJECT context", e);
            }
        }

        if (databaseContext != null) {
            try {
                databaseContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten DATABASE context", e);
            }
        }

        if (webserver != null) {
            webserver.stop();
            webserver.destroy();
        }

        if (webserverVraagPL != null) {
            webserverVraagPL.stop();
            webserverVraagPL.destroy();
        }
    }

    private static void setupBus() {
        Bus bus = BusFactory.getDefaultBus();
        SoapBindingFactory bindingFactory = new SoapBindingFactory();
        bindingFactory.setBus(bus);
        bus.getExtension(BindingFactoryManager.class)
                .registerBindingFactory("http://schemas.xmlsoap.org/wsdl/soap/", bindingFactory);
        bus.getExtension(BindingFactoryManager.class)
                .registerBindingFactory("http://schemas.xmlsoap.org/wsdl/soap/http", bindingFactory);

        DestinationFactoryManager dfm = bus.getExtension(DestinationFactoryManager.class);

        LocalTransportFactory localTransport = new LocalTransportFactory();
        dfm.registerDestinationFactory("http://schemas.xmlsoap.org/soap/http", localTransport);
        dfm.registerDestinationFactory("http://schemas.xmlsoap.org/wsdl/soap/http", localTransport);
        dfm.registerDestinationFactory("http://cxf.apache.org/bindings/xformat", localTransport);
        dfm.registerDestinationFactory("http://cxf.apache.org/transports/local", localTransport);

        ConduitInitiatorManager extension = bus.getExtension(ConduitInitiatorManager.class);
        extension.registerConduitInitiator(LocalTransportFactory.TRANSPORT_ID, localTransport);
        extension.registerConduitInitiator("http://schemas.xmlsoap.org/wsdl/soap/", localTransport);
        extension.registerConduitInitiator("http://schemas.xmlsoap.org/soap/http", localTransport);
        extension.registerConduitInitiator("http://schemas.xmlsoap.org/soap/", localTransport);

        bus.getOutInterceptors().add(new LoggingOutInterceptor());
    }

    private static void startServer(GenericXmlApplicationContext context) {
        setupBus();
        AdhocService webservice = context.getAutowireCapableBeanFactory().getBean("adhocServiceImpl", AdhocService.class);
        VraagPLService vraagPLService = context.getAutowireCapableBeanFactory().getBean("vraagPLServiceImpl", VraagPLService.class);

        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setAddress(ENDPOINT_ADDRESS_ADHOC);
        factory.setServiceBean(webservice);
        factory.setServiceClass(AdhocService.class);

        JaxWsServerFactoryBean factoryVraagPL = new JaxWsServerFactoryBean();
        factoryVraagPL.setAddress(ENDPOINT_ADDRESS_VRAAGPL);
        factoryVraagPL.setServiceBean(vraagPLService);
        factoryVraagPL.setServiceClass(VraagPLService.class);

        webserver = factory.create();
        webserverVraagPL = factoryVraagPL.create();
    }

    private static void logPersoonBlob(int persoonId) {
        JtaTransactionManager
                transactionManager =
                subjectContext.getAutowireCapableBeanFactory().getBean("masterTransactionManager", JtaTransactionManager.class);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager, new DefaultTransactionDefinition());

        PersoonRepository persoonRepository = subjectContext.getAutowireCapableBeanFactory().getBean(PersoonRepository.class);
        transactionTemplate.execute(action -> {
            final Persoon persoon = persoonRepository.haalPersoonOp(persoonId);
            try {
                LOG.info("Persoonblob met id " + persoonId + ": " + Base64Utils.encodeToString(Blobber.toJsonBytes(Blobber.maakBlob(persoon))));
                return null;
            } catch (final BlobException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    @Before
    public void start() throws BlobException {
        // Create test context
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
        testContext.refresh();
        testContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @After
    public void shutdown() throws InterruptedException {
        if (testContext != null) {
            try {
                testContext.close();
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten TEST context", e);
            }
        }
    }

    Antwoord request(final Vraag vraag) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress(ENDPOINT_ADDRESS_ADHOC);
        factory.setServiceClass(AdhocService.class);
        AdhocService webclient = (AdhocService) factory.create();
        return webclient.vraag(vraag);
    }

    Antwoord request(final Vraag vraag, final String partijCode) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress(ENDPOINT_ADDRESS_ADHOC);
        factory.setServiceClass(AdhocService.class);
        factory.setOutInterceptors(Collections.singletonList(new PartijCodeHeaderInterceptor(partijCode)));
        AdhocService webclient = (AdhocService) factory.create();
        return webclient.vraag(vraag);
    }

    Antwoord request(final VraagPL vraag, final String partijCode) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress(ENDPOINT_ADDRESS_VRAAGPL);
        factory.setServiceClass(VraagPLService.class);
        factory.setOutInterceptors(Collections.singletonList(new PartijCodeHeaderInterceptor(partijCode)));
        VraagPLService webclient = (VraagPLService) factory.create();
        return webclient.vraagPL(vraag);
    }

    void assertSuccess(final Antwoord antwoord) {
        assertAntwoord(antwoord, "A", 0);
    }

    void assertAntwoord(final Antwoord antwoord, final String letter, final int code) {
        assertEquals("Resultaat letter is niet correct", letter, antwoord.getResultaat().getLetter());
        assertEquals("Resultaat code is niet correct", code, antwoord.getResultaat().getCode());
    }

    void assertANummers(final Antwoord antwoord, final String... anummers) {
        assertSuccess(antwoord);
        assertEquals(anummers.length, antwoord.getPersoonslijsten().size());
        assertEquals("ANummers moet correct zijn", Lists.newArrayList(anummers),
                antwoord.getPersoonslijsten().stream()
                        .map(pl -> pl.getCategoriestapels().get(0).getCategorievoorkomens().get(0).getElementen().get(0).getWaarde())
                        .collect(Collectors.toList()));
    }

    private static class PartijCodeHeaderInterceptor extends AbstractSoapInterceptor {
        private final String partijCode;

        PartijCodeHeaderInterceptor(final String partijCode) {
            super(Phase.POST_LOGICAL);
            this.partijCode = partijCode;
        }

        @Override
        public void handleMessage(final SoapMessage message) throws Fault {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put(PartijCodeResolver.HEADER.PARTIJ_CODE.getNaam(), Collections.singletonList(partijCode));
            message.put(Message.PROTOCOL_HEADERS, headers);
        }
    }
}
