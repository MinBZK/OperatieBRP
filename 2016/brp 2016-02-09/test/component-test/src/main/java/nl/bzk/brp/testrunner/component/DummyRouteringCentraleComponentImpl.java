/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.omgeving.ComponentException;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.component.util.Afnemerbericht;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@LogischeNaam(ComponentNamen.ROUTERINGCENTRALE)
final class DummyRouteringCentraleComponentImpl extends AbstractComponent implements RouteringCentrale {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private BrokerService broker;

    public DummyRouteringCentraleComponentImpl(final Omgeving omgeving) {
        super(omgeving);
    }

    @Override
    public void doStart() {
        final Integer brokerPoort = getPoortMap().get(Poorten.BROKER_POORT_61616);
        broker = new BrokerService();
        broker.setBrokerName("brp-routering-centrale-broker-" + brokerPoort);
        try {
            broker.addConnector("tcp://0.0.0.0:" + brokerPoort);
            broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
            broker.setUseJmx(true);
            broker.setStartAsync(false);
            broker.start();
        } catch (Exception e) {
            throw new ComponentException(e);
        }
    }

    @Override
    public void doStop() {
        try {
            broker.stop();
        } catch (Exception e) {
            throw new ComponentException(e);
        }
    }

    @Override
    public void verstuurHandeling(final long administratieveHandeling) {
        LOGGER.info("Plaats handeling op queue: " +administratieveHandeling);
        final JmsTemplate jmsTemplate = new JmsTemplate(maakConnectionFactory());
        jmsTemplate.send("AdministratieveHandelingen", new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(String.format("{\"administratieveHandelingId\":%d}", administratieveHandeling));
            }
        });
    }

    @Override
    public List<Afnemerbericht> geefAfnemerberichten(final String queue) throws JMSException {
        final JmsTemplate jmsTemplate = new JmsTemplate(maakConnectionFactory());
        // er zit iets tijd in het beschikbaar maken van berichten uit de
        // persistent store
        jmsTemplate.setReceiveTimeout(2500);
        final List<Afnemerbericht> berichtenLijst = new LinkedList<>();
        ActiveMQMessage bericht;
        while ((bericht = (ActiveMQMessage) jmsTemplate.receive(queue)) != null) {
            final Afnemerbericht afnemerbericht = new Afnemerbericht(
                bericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE),
                bericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID),
                bericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT)
            );
            berichtenLijst.add(afnemerbericht);
            jmsTemplate.setReceiveTimeout(JmsTemplate.RECEIVE_TIMEOUT_NO_WAIT);
        }
        return berichtenLijst;
    }

    @Override
    public <T> T voerUit(final SessionVerzoek<T> verzoek) throws JMSException {
        final Session session = maakConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        try {
            return verzoek.voerUitMetSessie(session);
        } finally {
            session.close();
        }
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.BROKER_POORT_61616);
        return internePoorten;
    }

    protected Map<String, String> geefInjectOmgevingsVariabelen() {
        final Map<String, String> map = super.geefInjectOmgevingsVariabelen();
        map.put("ROUTERINGCENTRALE_PORT_61616_TCP_PORT", String.valueOf(getPoortMap().get(Poorten.BROKER_POORT_61616)));
        map.put("ROUTERINGCENTRALE_ENV_HOSTNAME", String.valueOf(getOmgeving().geefComponent(ComponentNamen.ROUTERINGCENTRALE).getOmgeving().geefOmgevingHost()));
        return map;
    }

    protected Map<String, String> geefAddHostParameters() {
        final Map<String, String> map = super.geefAddHostParameters();
        map.put(ComponentNamen.ROUTERINGCENTRALE, getOmgeving().geefOmgevingHost());
        return map;
    }

    //TODO gebruik Pooled versie
    private ConnectionFactory maakConnectionFactory() {
        return new ActiveMQConnectionFactory(broker.getDefaultSocketURIString());
    }

}
