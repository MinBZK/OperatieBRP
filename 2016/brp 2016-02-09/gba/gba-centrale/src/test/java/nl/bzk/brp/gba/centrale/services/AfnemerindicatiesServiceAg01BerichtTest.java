/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.gba.centrale.berichten.AfnemerindicatieOnderhoudOpdracht;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatiesServiceAg01BerichtTest {

    private static final String UITGAAND_BERICHT = "Dit is een uitgaand bericht";

    private final AfnemerindicatieOnderhoudOpdracht opdracht = new AfnemerindicatieOnderhoudOpdracht();
    private final Partij partij = TestPartijBuilder.maker().metCode(36101).maak();

    private ToegangLeveringsautorisatie toegangLeveringsautorisatie;

    @Mock
    private Session session;

    @Mock
    private Message message;

    @Before
    public void init() throws JMSException {
        toegangLeveringsautorisatie = new ToegangLeveringsautorisatie() {
        };
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "iD", 123);
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER, null, null);
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "geautoriseerde", partijRol);

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie() {
        };
        ReflectionTestUtils.setField(toegangLeveringsautorisatie, "leveringsautorisatie", leveringsautorisatie);
        ReflectionTestUtils.setField(leveringsautorisatie, "iD", 765);

        Mockito.when(session.createMessage()).thenReturn(message);
    }

    @Test
    public void testCreateMessage() throws JMSException {
        final AfnemerindicatiesService.Ag01Bericht ag01Bericht =
                new AfnemerindicatiesService.Ag01Bericht(UITGAAND_BERICHT, toegangLeveringsautorisatie, opdracht);
        ag01Bericht.createMessage(session);

        Mockito.verify(session).createMessage();
        Mockito.verify(message).setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, UITGAAND_BERICHT);
        Mockito.verify(message).setStringProperty(Matchers.eq(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS), Matchers.anyString());
        Mockito.verify(message).setStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE, "36101");
        Mockito.verify(message).setStringProperty(LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID, "765");

    }
}
