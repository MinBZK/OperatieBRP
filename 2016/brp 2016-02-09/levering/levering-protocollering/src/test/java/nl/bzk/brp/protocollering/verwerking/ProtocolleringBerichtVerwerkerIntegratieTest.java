/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.AbstractIntegratieTestMetJmsEnDatabase;
import nl.bzk.brp.protocollering.TestData;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class ProtocolleringBerichtVerwerkerIntegratieTest extends AbstractIntegratieTestMetJmsEnDatabase {

    private static final String ID = "iD";

    @Inject
    private JmsTemplate publiceerProtocolleringTemplate;

    @PersistenceContext(unitName = "nl.bzk.brp.protocollering")
    private EntityManager em;

    private final JsonStringSerializer<ProtocolleringOpdracht> serialiseerder = new JsonStringSerializer<>(ProtocolleringOpdracht.class);

    private String protocolleringOpdrachtJson;

    @Before
    public void init() {
        final ProtocolleringOpdracht protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht();
        protocolleringOpdrachtJson = serialiseerder.serialiseerNaarString(protocolleringOpdracht);
    }

    @Test
    public void testProtocolleringBerichtOntvangen() throws InterruptedException {
        publiceerProtocolleringTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final TextMessage textMessage = new ActiveMQTextMessage();
                textMessage.setText(protocolleringOpdrachtJson);

                return textMessage;
            }
        });

        Thread.sleep(1000L);

        final List leveringenInDatabase = em.createQuery("from LeveringModel").getResultList();
        assertEquals(1, leveringenInDatabase.size());

        final List<LeveringPersoonModel> leveringPersonenInDatabase =
            em.createQuery("from LeveringPersoonModel", LeveringPersoonModel.class).getResultList();
        assertEquals(2, leveringPersonenInDatabase.size());

        final Set<LeveringPersoonModel> leveringPersoonModellen = new HashSet<>();
        leveringPersoonModellen.addAll(leveringPersonenInDatabase);

        final ProtocolleringOpdracht protocolleringOpdrachtVanuitDatabase =
                new ProtocolleringOpdracht((LeveringModel) leveringenInDatabase.get(0), leveringPersoonModellen);

        //reset de id's zodat hij te vergelijken is met de originele json
        ReflectionTestUtils.setField(protocolleringOpdrachtVanuitDatabase.getLevering(), ID, null);
        for (final LeveringPersoonModel leveringPersoonInDb : protocolleringOpdrachtVanuitDatabase.getPersonen()) {
            ReflectionTestUtils.setField(leveringPersoonInDb, ID, null);
        }

        assertThat(
                protocolleringOpdrachtJson, sameJSONAs(serialiseerder.serialiseerNaarString(protocolleringOpdrachtVanuitDatabase))
                .allowingExtraUnexpectedFields().allowingAnyArrayOrdering());
    }

}
