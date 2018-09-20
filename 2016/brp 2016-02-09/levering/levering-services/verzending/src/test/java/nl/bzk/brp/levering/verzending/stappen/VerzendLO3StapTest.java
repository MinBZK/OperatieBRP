/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import static org.mockito.Mockito.never;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.excepties.VerzendExceptie;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.InvalidDestinationException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class VerzendLO3StapTest {

    private static final String ID                       = "iD";
    private static final String TEST_BERICHT_XML_BESTAND = "test_bericht.xml";


    @InjectMocks
    private final VerzendLO3Stap verzendLO3Stap = new VerzendLO3Stap();

    @Mock
    private JmsTemplate lo3AfnemersJmsTemplate;

    @Test
    public final void testProcessSuccesvol() throws Exception {
        final BerichtContext berichtContext = maakMessageVanXmlBestand(TEST_BERICHT_XML_BESTAND);
        verzendLO3Stap.process(berichtContext);
        Mockito.verify(lo3AfnemersJmsTemplate).send(Mockito.any(MessageCreator.class));
    }

    @Test(expected = VerzendExceptie.class)
    public final void testProcessMetNullLeveringBericht() throws Exception {
        final BerichtContext berichtContext = maakMessageVanXmlBestand("test_bericht_met_fouten.xml");
        Mockito.when(berichtContext.getJmsBericht().getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT)).thenReturn(null);
        verzendLO3Stap.process(berichtContext);
        Mockito.verify(lo3AfnemersJmsTemplate, never()).send(Mockito.any(MessageCreator.class));
    }

    @Test
    public final void stuurBerichtMetFouten() throws Exception {
        final BerichtContext berichtContext = maakMessageVanXmlBestand("test_bericht_met_fouten.xml");
        verzendLO3Stap.process(berichtContext);
        // Gaat goed, aangezien er bij plaatsen van JMS bericht geen validatie wordt gedaan op XML van bericht.
        Mockito.verify(lo3AfnemersJmsTemplate).send(Mockito.any(MessageCreator.class));
    }

    @Test(expected = VerzendExceptie.class)
    public final void stuurBerichtMaarJmsTemplateGeeftFout() throws Exception {
        Mockito.doThrow(new InvalidDestinationException(new javax.jms.InvalidDestinationException("Test exceptie!")))
            .when(lo3AfnemersJmsTemplate).send(Mockito.any(MessageCreator.class));

        final BerichtContext berichtContext = maakMessageVanXmlBestand(TEST_BERICHT_XML_BESTAND);
        verzendLO3Stap.process(berichtContext);
        Mockito.verify(lo3AfnemersJmsTemplate).send(Mockito.any(MessageCreator.class));
    }

    /**
     * Maakt een message van een xml bestand.
     *
     * @param xmlBestand naam van xml bestand
     * @return message object
     * @throws IOException iO exception
     */
    private BerichtContext maakMessageVanXmlBestand(final String xmlBestand) throws IOException, JMSException {
        final String xmlString = leesBestandAlsString(xmlBestand);
        final Message message = Mockito.mock(Message.class);
        Mockito.when(message.getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT)).thenReturn(xmlString);
        Mockito.when(message.getStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE)).thenReturn("12345");
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), message);
        berichtContext.setSynchronisatieBerichtGegevens(new SynchronisatieBerichtGegevens(null, 1));
        return berichtContext;
    }

    /**
     * Leest een bestand als string.
     *
     * @param bestandsnaam bestandsnaam
     * @return string representatie van bestand
     * @throws IOException the iO exception
     */
    private String leesBestandAlsString(final String bestandsnaam) throws IOException {
        final StringBuilder resultaat = new StringBuilder();

        final BufferedReader lezer = new BufferedReader(
            new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(bestandsnaam))
        );

        String line;
        while ((line = lezer.readLine()) != null) {
            resultaat.append(line);
        }

        lezer.close();

        return resultaat.toString();
    }
}
