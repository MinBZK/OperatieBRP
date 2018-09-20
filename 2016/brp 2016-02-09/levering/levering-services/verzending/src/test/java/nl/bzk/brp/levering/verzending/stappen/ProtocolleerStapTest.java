/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.excepties.ProtocolleerExceptie;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieMisluktExceptie;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieService;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleerStapTest {

    @InjectMocks
    private final ProtocolleerStap protocolleerStap = new ProtocolleerStap();

    @Mock
    private ProtocolleringPublicatieService protocolleringPublicatieService;

    private final DatumTijdAttribuut tsReg                      = DatumTijdAttribuut.bouwDatumTijd(2013, 1, 1, 13, 1, 23);
    private final List<Integer>      persoonIDs                 = Arrays.asList(1, 2, 3);
    private final Set<Integer>       persoonIDSet               = new HashSet<>(persoonIDs);
    private       long               administratieveHandelingId = 1324;
    private       int                dienstId                   = 321;

    private BerichtContext maakEnVulTestExchange() throws JMSException {
        final BerichtStuurgegevensGroepModel stuurgegevens =
            new BerichtStuurgegevensGroepModel(null, null, null, null, null, null,
                DatumTijdAttribuut.bouwDatumTijd(2014, 6, 14), null);

        final SynchronisatieBerichtGegevens metadata = new SynchronisatieBerichtGegevens();
        metadata.setStuurgegevens(stuurgegevens);
        metadata.setGeleverdePersoonsIds(persoonIDs);
        metadata.setAdministratieveHandelingTijdstipRegistratie(tsReg);
        metadata.setDatumAanvangMaterielePeriodeResultaat(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        metadata.setDatumTijdAanvangFormelePeriodeResultaat(tsReg);
        metadata.setDatumTijdEindeFormelePeriodeResultaat(tsReg);
        metadata.setAdministratieveHandelingId(administratieveHandelingId);
        metadata.setDienstId(dienstId);
        metadata.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT));
        metadata.setSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        final BerichtModel archiefBericht = Mockito.mock(BerichtModel.class);

        final Message jmsBericht = Mockito.mock(Message.class);
        Mockito.when(jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU)).thenReturn
            (Protocolleringsniveau.DUMMY.name());

        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        berichtContext.setSynchronisatieBerichtGegevens(metadata);
        berichtContext.setBerichtArchiefModel(archiefBericht);
        return berichtContext;
    }

    @Test
    public final void testProcess() throws Exception {
        final BerichtContext berichtContext = maakEnVulTestExchange();

        protocolleerStap.process(berichtContext);

        final ArgumentCaptor<ProtocolleringOpdracht> argument = ArgumentCaptor.forClass(ProtocolleringOpdracht.class);
        Mockito.verify(protocolleringPublicatieService).publiceerProtocolleringGegevens(argument.capture());

        final LeveringModel leveringModel = argument.getValue().getLevering();
        Assert.assertThat(leveringModel.getDatumTijdKlaarzettenLevering(), Matchers.not(Matchers.nullValue()));
        Assert.assertThat(leveringModel.getDatumTijdAanvangFormelePeriodeResultaat().getWaarde(), Matchers.equalTo(
            DateTime.parse("2013-01-01T13:01:23").toDate()));
        Assert.assertThat(leveringModel.getSoortSynchronisatie().getWaarde(), Matchers.equalTo(SoortSynchronisatie.VOLLEDIGBERICHT));
        Assert.assertThat(leveringModel.getAdministratieveHandelingId(), Matchers.equalTo(administratieveHandelingId));

        final Set<LeveringPersoonModel> personen = argument.getValue().getPersonen();
        Assert.assertThat(personen.size(), Matchers.equalTo(persoonIDSet.size()));
    }

    @Test
    public final void testProcessBackReferenceWordtNietGezetVoorMinimaleBerichtGrootte() throws Exception {
        final BerichtContext testExchange = maakEnVulTestExchange();

        protocolleerStap.process(testExchange);

        final ArgumentCaptor<ProtocolleringOpdracht> argument = ArgumentCaptor.forClass(ProtocolleringOpdracht.class);
        Mockito.verify(protocolleringPublicatieService).publiceerProtocolleringGegevens(argument.capture());

        final ProtocolleringOpdracht protocolleringOpdracht = argument.getValue();

        Assert.assertEquals(3, protocolleringOpdracht.getPersonen().size());
        for (final LeveringPersoonModel persoon : protocolleringOpdracht.getPersonen()) {
            Assert.assertNull(persoon.getLevering());
        }
    }

    @Test
    public final void testProcessGeenProtocolleringIvmGeheimNiveau() throws Exception {
        final BerichtContext berichtContext = maakEnVulTestExchange();
        Mockito.when(berichtContext.getJmsBericht().getStringProperty(LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU)).thenReturn
            (Protocolleringsniveau.GEHEIM.name());
            protocolleerStap.process(berichtContext);
        Mockito.verifyZeroInteractions(protocolleringPublicatieService);
    }

    @Test(expected = ProtocolleerExceptie.class)
    public final void testProtocolleerLeveringMetFout() throws Exception {
        Mockito.doThrow(ProtocolleringPublicatieMisluktExceptie.class).when(protocolleringPublicatieService)
            .publiceerProtocolleringGegevens(Mockito.any(ProtocolleringOpdracht.class));
        final BerichtContext berichtContext = maakEnVulTestExchange();
        protocolleerStap.process(berichtContext);
    }

    @Test(expected = RuntimeException.class)
    public final void testProtocolleerLeveringMetOnbekendeFout() throws Exception {
        Mockito.doThrow(RuntimeException.class).when(protocolleringPublicatieService)
            .publiceerProtocolleringGegevens(Mockito.any(ProtocolleringOpdracht.class));
        final BerichtContext berichtContext = maakEnVulTestExchange();
        protocolleerStap.process(berichtContext);
    }

    @Test(expected = ProtocolleerExceptie.class)
    public final void testProtocolleerLeveringMetInvalideBerichtGegevens() throws Exception {
        final BerichtContext berichtContext = maakEnVulTestExchange();
        Mockito.doThrow(ProtocolleerExceptie.class)
            .when(protocolleringPublicatieService)
            .publiceerProtocolleringGegevens(Mockito.any(ProtocolleringOpdracht.class));
        protocolleerStap.process(berichtContext);
    }
}
