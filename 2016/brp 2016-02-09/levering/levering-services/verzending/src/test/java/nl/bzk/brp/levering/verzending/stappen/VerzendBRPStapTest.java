/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.ws.Endpoint;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.verzending.LvgSynchronisatieVerwerkingImpl;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.excepties.VerzendExceptie;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.webservice.kern.interceptor.BerichtArchiveringUitInterceptor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class VerzendBRPStapTest {

    private static final String  LOCALHOST_URL              = "http://localhost:8988/kennisgeving";
    private static final String  ID                         = "iD";

    private static Endpoint endpoint;

    @InjectMocks
    private final VerzendBRPStap stap = new VerzendBRPStap();

    @Mock
    private BerichtArchiveringUitInterceptor archiveringOutInterceptor;

    @BeforeClass
    public static void createServer() {
        endpoint = Endpoint.publish(LOCALHOST_URL, new LvgSynchronisatieVerwerkingImpl());
    }

    @AfterClass
    public static void tearDown() {
        endpoint.stop();
    }

    @Test
    public final void testProcessSuccesvol() throws Exception {
        process("test_bericht.xml");
    }

    @Test(expected = VerzendExceptie.class)
    public final void stuurBerichtMetFouten() throws Exception {
        process("test_bericht_met_fouten.xml");
    }

    private void process(final String xmlBody) throws Exception {
        final Message message = maakMessageVanXmlBestand(xmlBody);
        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), message);
        final BerichtModel berichtArchiefModel = new BerichtModel();
        ReflectionTestUtils.setField(berichtArchiefModel, ID, 1L);
        berichtContext.setBerichtArchiefModel(berichtArchiefModel);
        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = new SynchronisatieBerichtGegevens();
        synchronisatieBerichtGegevens.setAdministratieveHandelingTijdstipRegistratie(new DatumTijdAttribuut(new Date()));
        synchronisatieBerichtGegevens.setGeleverdePersoonsIds(Collections.singletonList(1));
        synchronisatieBerichtGegevens.setSoortDienst(SoortDienst.DUMMY);
        synchronisatieBerichtGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.DUMMY));
        berichtContext.setSynchronisatieBerichtGegevens(synchronisatieBerichtGegevens);

        stap.process(berichtContext);

        verify(archiveringOutInterceptor).getAdditionalInterceptors();
    }

    /**
     * Maakt een message van een xml bestand.
     *
     * @param xmlBestand naam van xml bestand
     * @return message object
     * @throws IOException iO exception
     */
    private Message maakMessageVanXmlBestand(final String xmlBestand) throws IOException, JMSException {
        final String xmlString = leesBestandAlsString(xmlBestand);
        final Message message = Mockito.mock(Message.class);
        Mockito.when(message.getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT)).thenReturn(xmlString);
        Mockito.when(message.getStringProperty(LeveringConstanten.JMS_PROPERTY_BRP_AFLEVER_URI)).thenReturn(LOCALHOST_URL);
        return message;
    }

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
