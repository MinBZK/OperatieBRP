/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.verwerker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;

import java.util.ArrayList;
import java.util.Collections;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.PortInitializer;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendAfnemersIndicatieBerichtVerwerker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-embedded-database-brp-gbav.xml", "classpath:test-embedded-hornetq.xml"}, initializers = PortInitializer.class)
public class VerzendAfnemersIndicatieBerichtVerwerkerTest {

    private VerzendAfnemersIndicatieBerichtVerwerker subject;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    private Destination destination;

    @Mock
    private AfnemersIndicatieRepository repository;

    @Captor
    private ArgumentCaptor<ArrayList<String>> captor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        subject = new VerzendAfnemersIndicatieBerichtVerwerker(destination, jmsTemplate, repository);
    }

    @After
    public void clearMessageQueue() {
        Message message;
        do {
            message = jmsTemplate.receive(destination);
        } while (message != null);
    }

    @Test
    public void initialize() {
        assertEquals(0, subject.aantalBerichten());
        assertEquals(0, subject.aantalVerzonden());
    }

    @Test
    public void voegBerichtToe() {
        final AfnemersindicatiesBericht bericht = new AfnemersindicatiesBericht();
        subject.voegBerichtToe(bericht);
        assertEquals(1, subject.aantalBerichten());
    }

    @Test(expected = IllegalStateException.class)
    public void voegBerichtToeNaVerwerken() {
        subject.verwerkBerichten();
        subject.voegBerichtToe(new AfnemersindicatiesBericht());
    }

    @Test(expected = IllegalStateException.class)
    public void meerdereKerenVerwerken() {
        subject.verwerkBerichten();
        subject.verwerkBerichten();
    }

    @Test
    public void verwerkenMoetAantalVerzondenAanpassen() {
        final AfnemersindicatiesBericht bericht = new AfnemersindicatiesBericht();
        bericht.setAfnemersindicaties(new Lo3Afnemersindicatie("1", Collections.emptyList()));
        subject.voegBerichtToe(bericht);
        subject.voegBerichtToe(bericht);
        subject.verwerkBerichten();
        assertEquals(2, subject.aantalVerzonden());
    }

    @Test
    public void verwerkenBerichtVersturen() throws JMSException, InterruptedException {
        final AfnemersindicatiesBericht bericht = new AfnemersindicatiesBericht();
        bericht.setAfnemersindicaties(new Lo3Afnemersindicatie("1", Collections.emptyList()));
        subject.voegBerichtToe(bericht);
        subject.verwerkBerichten();

        final TextMessage message = (TextMessage) jmsTemplate.receive(destination);
        assertEquals(bericht.format(), message.getText());

        Mockito.verify(repository).updateAfnemersIndicatiesBerichtStatus(captor.capture(), argThat(equalTo(ConversieResultaat.VERZONDEN)));
        assertThat(captor.getValue(), hasItem("1"));
    }
}
