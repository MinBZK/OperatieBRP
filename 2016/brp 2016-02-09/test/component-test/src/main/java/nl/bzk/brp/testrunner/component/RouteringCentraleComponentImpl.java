/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.util.Afnemerbericht;
import nl.bzk.brp.testrunner.component.util.PoortManager;
import nl.bzk.brp.testrunner.component.util.VersieUrlChecker;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@LogischeNaam(ComponentNamen.ROUTERINGCENTRALE)
final class RouteringCentraleComponentImpl extends AbstractDockerComponent implements RouteringCentrale {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ActiveMQConnectionFactory activeMQConnectionFactory;
    private final int jmxPoort = PoortManager.get().geefVrijePoort();

    public RouteringCentraleComponentImpl(Omgeving omgeving) {
        super(omgeving);
    }


    @Override
    protected Map<String, String> geefOmgevingsVariabelen() {
        final Map<String, String> map = super.geefOmgevingsVariabelen();
        map.put("JMX_PORT", String.valueOf(jmxPoort));
        map.put("DOCKER_IP", getOmgeving().geefOmgevingHost());
        return map;
    }

    @Override
    protected int geefExternePoort(final int internePoort) {
        //intern en extern moet voor JMX gelijk zijn!
        if (internePoort == jmxPoort) {
            return internePoort;
        }
        return super.geefExternePoort(internePoort);
    }

    @Override
    protected Map<String, String> geefInterneLinkOpLogischeLinkMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("activemqdb", ComponentNamen.BRP_DB);
        return map;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "brp-message-broker");
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.BROKER_POORT_61616);
        internePoorten.add(Poorten.WEB_POORT_8080);
        internePoorten.add(jmxPoort);
        return internePoorten;
    }

    @Override
    protected DockerImage geefDockerImage() {
        return new DockerImage("brp/brp-message-broker");
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

    private ActiveMQConnectionFactory maakConnectionFactory() {
        if (this.activeMQConnectionFactory == null) {
            this.activeMQConnectionFactory = new ActiveMQConnectionFactory(String.
                format("tcp://%s:%d", getOmgeving().geefOmgevingHost(), getPoortMap().get(Poorten.BROKER_POORT_61616)));
        }
        return this.activeMQConnectionFactory;

    }
}
