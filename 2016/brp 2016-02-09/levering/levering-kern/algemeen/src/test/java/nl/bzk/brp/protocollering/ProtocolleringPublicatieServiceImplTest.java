/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieMisluktExceptie;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieServiceImpl;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/amq-test-context.xml")
public class ProtocolleringPublicatieServiceImplTest extends AbstractJUnit4SpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String SERIALIZEERDER = "serialiseerder";

    @Inject
    private ProtocolleringPublicatieServiceImpl protocolleringPublicatieService;

    @Inject
    private JmsTemplate publiceerProtocolleringTemplate;

    private ProtocolleringOpdracht protocolleringOpdracht;

    @Before
    public final void setUp() {
        final Integer toegangLeveringsautorisatieId = 1;
        final Integer dienstId = 2;
        final DatumTijdAttribuut datumTijdKlaarzettenLevering = DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1, 1, 1, 1);
        final DatumTijdAttribuut peilmomentFormeelResultaat = DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1, 1, 1, 1);
        final DatumAttribuut peilmomentMaterieelResultaat = DatumTijdAttribuut.bouwDatumTijd(2012, 0, 0).naarDatum();
        final Long administratieveHandelingId = 24L;
        final SoortSynchronisatieAttribuut soortSynchronisatie =
            new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT);

        final LeveringModel levering = new LeveringModel(toegangLeveringsautorisatieId, dienstId,
            datumTijdKlaarzettenLevering, null,
            peilmomentMaterieelResultaat, peilmomentMaterieelResultaat,
            peilmomentFormeelResultaat, peilmomentFormeelResultaat,
            administratieveHandelingId, soortSynchronisatie);

        final LeveringPersoonModel leveringPersoon1 = new LeveringPersoonModel(levering, 1);
        final LeveringPersoonModel leveringPersoon2 = new LeveringPersoonModel(levering, 2);
        final Set<LeveringPersoonModel> leveringPersonen = new HashSet<>();
        leveringPersonen.add(leveringPersoon1);
        leveringPersonen.add(leveringPersoon2);

        protocolleringOpdracht = new ProtocolleringOpdracht(levering, leveringPersonen);
        protocolleringOpdracht.setSoortDienst(SoortDienst.ATTENDERING);

        ReflectionTestUtils.setField(protocolleringPublicatieService, SERIALIZEERDER, new JsonStringSerializer<>(ProtocolleringOpdracht.class));
    }

    @Test
    public final void testPubliceerProtocolleringGegevens() throws Exception {
        LOGGER.debug(protocolleringOpdracht.toString());

        protocolleringPublicatieService.publiceerProtocolleringGegevens(protocolleringOpdracht);

        final Message message = publiceerProtocolleringTemplate.receive();
        final TextMessage protocolleringMessage;
        if (message instanceof TextMessage) {
            protocolleringMessage = (TextMessage) message;
            LOGGER.debug("Bericht inhoud:{}", protocolleringMessage.getText());
            LOGGER.debug("Bericht info correlationid={},messageId={},redelivered?{},jmstype={},timestamp={}",
                new Object[]{ message.getJMSCorrelationID(), message.getJMSMessageID(),
                    message.getJMSRedelivered(), message.getJMSTimestamp(), });
        }
    }

    @Test(expected = ProtocolleringPublicatieMisluktExceptie.class)
    public final void testPubliceerProtocolleringGegevensMetSerialiseerExceptie() throws Exception {
        final JsonStringSerializer<ProtocolleringOpdracht> serialiseerder = mock(JsonStringSerializer.class);
        when(serialiseerder.serialiseerNaarString(any(ProtocolleringOpdracht.class)))
            .thenThrow(SerialisatieExceptie.class);
        ReflectionTestUtils.setField(protocolleringPublicatieService, SERIALIZEERDER, serialiseerder);
        protocolleringPublicatieService.publiceerProtocolleringGegevens(protocolleringOpdracht);
    }

    @Test(expected = ProtocolleringPublicatieMisluktExceptie.class)
    public final void testPubliceerProtocolleringGegevensMetNullObject() throws Exception {
        protocolleringPublicatieService.publiceerProtocolleringGegevens(null);
    }

    @Test(expected = ProtocolleringPublicatieMisluktExceptie.class)
    public final void testPubliceerProtocolleringGegevensMetInvalideObject() throws Exception {
        final ProtocolleringOpdracht protocolleringOpdrachtMock = new ProtocolleringOpdracht();

        protocolleringPublicatieService.publiceerProtocolleringGegevens(protocolleringOpdrachtMock);
    }
}
