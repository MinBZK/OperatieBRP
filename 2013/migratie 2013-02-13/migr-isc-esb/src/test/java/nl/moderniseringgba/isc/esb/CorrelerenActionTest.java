/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import java.util.List;

import nl.moderniseringgba.isc.esb.invoker.ServiceInvoker;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessCorrelatieStore;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.jboss.soa.esb.addressing.PortReference;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class CorrelerenActionTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    // Subject
    private CorrelerenAction subject;

    // Dependencies
    private ConfigTree configTree;
    private ServiceInvoker bpmSignalInvoker;
    private ServiceInvoker bpmStartInvoker;
    private ServiceInvoker bpmFoutInvoker;
    private ProcessCorrelatieStore processCorrelatieStore;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);
        bpmSignalInvoker = Mockito.mock(ServiceInvoker.class);
        bpmStartInvoker = Mockito.mock(ServiceInvoker.class);
        bpmFoutInvoker = Mockito.mock(ServiceInvoker.class);
        processCorrelatieStore = Mockito.mock(ProcessCorrelatieStore.class);
        berichtenDao = Mockito.mock(BerichtenDao.class);

        subject = new CorrelerenAction(configTree);
        subject.setBpmSignalInvoker(bpmSignalInvoker);
        subject.setBpmStartInvoker(bpmStartInvoker);
        subject.setBpmFoutInvoker(bpmFoutInvoker);
        subject.setProcessCorrelatieStore(processCorrelatieStore);
        subject.setBerichtenDao(berichtenDao);
    }

    @Test
    public void testStartNieuwProces() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(message.getBody()).thenReturn(body);

        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        Assert.assertNotNull("Niet het juiste bericht om mee te testen", ii01Bericht.getStartCyclus());
        Mockito.when(message.getBody().get()).thenReturn(ii01Bericht);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(bpmStartInvoker).deliverAsync(message);
        Mockito.verifyZeroInteractions(bpmSignalInvoker, bpmFoutInvoker);
        Mockito.verify(body).add(Constants.PROCESS_DEFINITION_NAME, "uc301");
    }

    @Test
    public void testStartNieuwProcesNok() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(message.getBody()).thenReturn(body);

        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        ib01Bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        Assert.assertNull("Niet het juiste bericht om mee te testen", ib01Bericht.getStartCyclus());
        Mockito.when(message.getBody().get()).thenReturn(ib01Bericht);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(bpmFoutInvoker).deliverAsync(message);
        Mockito.verifyZeroInteractions(bpmSignalInvoker, bpmStartInvoker);
    }

    @Test
    public void testNegeerHerhaling() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_INDICATIE_HERHALING)).thenReturn(Boolean.TRUE);
        Mockito.when(message.getBody()).thenReturn(body);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verifyZeroInteractions(bpmSignalInvoker, bpmStartInvoker, bpmFoutInvoker);
    }

    @Test
    public void testSignaleerProces() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(35L);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");
        Mockito.when(message.getBody()).thenReturn(body);

        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        Mockito.when(message.getBody().get()).thenReturn(ib01Bericht);

        Mockito.when(processCorrelatieStore.zoekProcessCorrelatie("M00000000001")).thenReturn(
                new ProcessData(42L, 31L, 44L, "Counter", 1, null, null));

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).updateProcessInstance(35L, 42L);

        // @formatter:off 
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final Class<List<PortReference.Extension>> listClass = (Class)List.class;
        // @formatter:on
        final ArgumentCaptor<List<PortReference.Extension>> argument = ArgumentCaptor.forClass(listClass);
        Mockito.verify(bpmSignalInvoker).deliverAsync(Matchers.eq(message), argument.capture());

        final List<PortReference.Extension> extensions = argument.getValue();
        Assert.assertNotNull(extensions);

        Mockito.verifyZeroInteractions(bpmStartInvoker, bpmFoutInvoker);
    }

    @Test
    public void testSignaleerProcesAddressingOk() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(35L);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE)).thenReturn("0600");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE)).thenReturn("0500");
        Mockito.when(message.getBody()).thenReturn(body);

        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        Mockito.when(message.getBody().get()).thenReturn(ib01Bericht);

        Mockito.when(processCorrelatieStore.zoekProcessCorrelatie("M00000000001")).thenReturn(
                new ProcessData(42L, 31L, 44L, "Counter", 1, "0500", "0600"));

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).updateProcessInstance(35L, 42L);

        // @formatter:off 
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final Class<List<PortReference.Extension>> listClass = (Class)List.class;
        // @formatter:on
        final ArgumentCaptor<List<PortReference.Extension>> argument = ArgumentCaptor.forClass(listClass);
        Mockito.verify(bpmSignalInvoker).deliverAsync(Matchers.eq(message), argument.capture());

        final List<PortReference.Extension> extensions = argument.getValue();
        Assert.assertNotNull(extensions);

        Mockito.verifyZeroInteractions(bpmStartInvoker, bpmFoutInvoker);
    }

    @Test
    public void testSignaleerProcesAddressingNok() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(35L);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE)).thenReturn("0600");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE)).thenReturn("0500");
        Mockito.when(message.getBody()).thenReturn(body);

        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        ib01Bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        Mockito.when(message.getBody().get()).thenReturn(ib01Bericht);

        Mockito.when(processCorrelatieStore.zoekProcessCorrelatie("M00000000001")).thenReturn(
                new ProcessData(42L, 31L, 44L, "Counter", 1, "0800", "0900"));

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(bpmFoutInvoker).deliverAsync(message);
        Mockito.verifyZeroInteractions(bpmStartInvoker, bpmSignalInvoker);
    }

    @Test
    public void testSignaleerProcesNok() throws Exception {
        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(35L);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");
        Mockito.when(message.getBody()).thenReturn(body);

        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        ib01Bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        Mockito.when(message.getBody().get()).thenReturn(ib01Bericht);

        Mockito.when(processCorrelatieStore.zoekProcessCorrelatie("M00000000001")).thenReturn(null);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(bpmFoutInvoker).deliverAsync(message);
        Mockito.verifyZeroInteractions(bpmStartInvoker, bpmSignalInvoker);
    }

    @Test
    public void testNoOps() throws Exception {
        final Message message = Mockito.mock(Message.class);
        final Throwable throwable = Mockito.mock(Throwable.class);

        subject.initialise();
        subject.processSuccess(message);
        subject.processException(message, throwable);
        subject.destroy();
    }
}
