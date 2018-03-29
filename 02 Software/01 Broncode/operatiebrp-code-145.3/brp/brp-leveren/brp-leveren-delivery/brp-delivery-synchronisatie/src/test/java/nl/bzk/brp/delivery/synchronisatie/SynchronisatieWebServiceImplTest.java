/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableMap;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStamgegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import nl.bzk.brp.service.synchronisatie.persoon.SynchroniseerPersoonService;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenCallback;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenService;
import org.apache.cxf.interceptor.Fault;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.InputSource;

/**
 * SynchronisatieWebServiceImplTest.
 */

@RunWith(MockitoJUnitRunner.class)
public class SynchronisatieWebServiceImplTest {

    private static final String OIN_ONDERTEKENAAR = "00000001001932603000";
    private static final String OIN_TRANSPORTEUR = "00000001001932603001";

    @InjectMocks
    private SynchronisatieWebServiceImpl synchronisatieWebService;
    @Mock
    private SynchroniseerPersoonService synchroniseerPersoonService;
    @Mock
    private SynchroniseerStamgegevenService synchroniseerStamgegevenService;
    @Mock
    private SchemaValidatorService schemaValidatorService;
    @Mock
    private OinResolver oinResolver;
    @Captor
    private ArgumentCaptor<SynchronisatieVerzoek> verzoekCaptor;

    @Before
    public void voorTest() {
        when(oinResolver.get()).thenReturn(new OIN(OIN_ONDERTEKENAAR, OIN_TRANSPORTEUR));
    }

    @Test
    public void synchroniseerPersoonHappyFlow() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_synGeefSynchronisatiePersoon.xml").getInputStream()));
        doAnswer(a -> {
            final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().metPartijCode
                    ("000001").metStuurgegevens().metReferentienummer("refNrAntwoord").metCrossReferentienummer("refNr").metTijdstipVerzending
                    (ZonedDateTime.of(2016, 9, 27, 14, 17, 0, 0,
                            ZoneId.of("UTC"))).metZendendePartij(
                    TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build())
                    .eindeStuurgegevens()
                    .metResultaat(
                            BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam()).metVerwerking(
                                    VerwerkingsResultaat
                                            .GESLAAGD).build())
                    .build();

            ((SynchroniseerPersoonService.SynchronisatieCallback) a.getArguments()[1]).verwerkResultaat(
                    new SynchroniseerPersoonAntwoordBericht(basisBerichtGegevens)
            );
            return null;
        }).when(synchroniseerPersoonService).synchroniseer(any(), any());

        Source response = synchronisatieWebService.invoke(request);

        verify(schemaValidatorService).valideer(request, SynchronisatieWebServiceImpl.SCHEMA);
        verify(synchroniseerPersoonService).synchroniseer(verzoekCaptor.capture(), any());
        SynchronisatieVerzoek synchronisatieVerzoek = verzoekCaptor.getValue();
        assertEquals(OIN_ONDERTEKENAAR, synchronisatieVerzoek.getOin().getOinWaardeOndertekenaar());
        assertEquals(OIN_TRANSPORTEUR, synchronisatieVerzoek.getOin().getOinWaardeTransporteur());
        assertEquals(SoortDienst.SYNCHRONISATIE_PERSOON, synchronisatieVerzoek.getSoortDienst());
        Assert.assertNotNull(response);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(response, result);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><brp:lvg_synGeefSynchronisatiePersoon_R"
                        + " xmlns:brp=\"http://www.bzk.nl/brp/brp0200\"><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp"
                        + ":zendendeSysteem>BRP"
                        + "</brp:zendendeSysteem><brp:referentienummer>refNrAntwoord</brp:referentienummer><brp:crossReferentienummer>refNr</brp"
                        + ":crossReferentienummer><brp:tijdstipVerzending>2016-09-27T14:17:00"
                        + ".000Z</brp:tijdstipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Geslaagd</brp:verwerking><brp:hoogsteMeldingsniveau"
                        + ">Geen</brp:hoogsteMeldingsniveau></brp:resultaat></brp:lvg_synGeefSynchronisatiePersoon_R>",
                result.getWriter().toString());
    }

    @Test(expected = WebServiceException.class)
    public void synchroniseerPersoonError() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_synGeefSynchronisatiePersoon.xml").getInputStream()));
        synchronisatieWebService.invoke(request);
    }

    @Test
    public void synchroniseerStamgegevenHappyFlow() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_synGeefSynchronisatieStamgegeven.xml").getInputStream()));
        //@formatter:off
        final BerichtStamgegevens berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON_ADRES),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE))
            ), asList(
                ImmutableMap.<String, Object>builder().put("gem", "Amsterdam").build(),
                ImmutableMap.<String, Object>builder().put("gem", "Rotterdam").build(),
                ImmutableMap.<String, Object>builder().put("gem", "Utrecht").build()
            ))
        );
        //@formatter:on
        doAnswer(a -> {
            final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().metPartijCode
                    ("000001").metStuurgegevens().metReferentienummer("refNr").metTijdstipVerzending(ZonedDateTime.of(2016, 9, 27, 14, 17, 0, 0,
                    ZoneId.of("UTC"))).metZendendePartij(
                    TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build())
                    .eindeStuurgegevens()
                    .metResultaat(
                            BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam()).metVerwerking(
                                    VerwerkingsResultaat
                                            .GESLAAGD).build())
                    .build();

            ((SynchroniseerStamgegevenCallback) a.getArguments()[1]).verwerkResultaat(
                    new SynchroniseerStamgegevenBericht(basisBerichtGegevens, berichtStamgegevens)
            );
            return null;
        }).when(synchroniseerStamgegevenService).maakResponse(any(), any());

        Source response = synchronisatieWebService.invoke(request);

        verify(schemaValidatorService).valideer(request, SynchronisatieWebServiceImpl.SCHEMA);
        ArgumentCaptor<SynchronisatieVerzoek> argumentCaptor =
                ArgumentCaptor.forClass(SynchronisatieVerzoek.class);
        verify(synchroniseerStamgegevenService).maakResponse(argumentCaptor.capture(), any());
        SynchronisatieVerzoek synchronisatieVerzoek = argumentCaptor.getValue();
        assertEquals(OIN_ONDERTEKENAAR, synchronisatieVerzoek.getOin().getOinWaardeOndertekenaar());
        assertEquals(OIN_TRANSPORTEUR, synchronisatieVerzoek.getOin().getOinWaardeTransporteur());
        assertEquals(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN, synchronisatieVerzoek.getSoortDienst());
        Assert.assertNotNull(response);
    }

    @Test(expected = WebServiceException.class)
    public void synchroniseerPersoonWebServiceException() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_synGeefSynchronisatiePersoon.xml").getInputStream()));
        doAnswer(a -> {
            final SynchroniseerPersoonCallbackImpl o = (SynchroniseerPersoonCallbackImpl) a.getArguments()[1];
            ReflectionTestUtils.setField(o, "xml", "<foute><xml>");
            return null;
        }).when(synchroniseerPersoonService).synchroniseer(any(), any());

        synchronisatieWebService.invoke(request);
    }

    @Test(expected = Fault.class)
    public void gooitSoapFaultBijFalenValidatie() {
        doThrow(SchemaValidatorService.SchemaValidatieException.class).when(schemaValidatorService).valideer(any(), any());

        synchronisatieWebService.invoke(null);
    }

    @Test
    public void valideerTegenSynchronisatieSchema() throws Exception {
        final String pad = "/lvg_synGeefSynchronisatiePersoon.xml";
        SchemaValidatorService.doValideerTegenSchema(new StreamSource(new ClassPathResource(pad).getInputStream()), SynchronisatieWebServiceImpl.SCHEMA);
    }
}
