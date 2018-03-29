/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtAfnemerindicatie;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatieService;
import nl.bzk.brp.service.afnemerindicatie.RegistreerAfnemerindicatieCallback;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import org.apache.cxf.interceptor.Fault;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.InputSource;

/**
 * OnderhoudAfnemerindicatiesWebServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatiesWebServiceImplTest {

    private static final String OIN_ONDERTEKENAAR = "00000001001932603000";
    private static final String OIN_TRANSPORTEUR = "00000001001932603001";

    @InjectMocks
    private OnderhoudAfnemerindicatiesWebServiceImpl onderhoudAfnemerindicatiesWebService;

    @Mock
    private OnderhoudAfnemerindicatieService onderhoudAfnemerindicatieService;
    @Mock
    private OinResolver oinResolver;
    @Mock
    private SchemaValidatorService schemaValidatorService;

    @Before
    public void voorTest() {
        Mockito.when(oinResolver.get()).thenReturn(new OIN(OIN_ONDERTEKENAAR, OIN_TRANSPORTEUR));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void verwerkRequest() throws Exception {

        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_synRegistreerAfnemerindicatie_plaats.xml").getInputStream()));
        doAnswer(a -> {
            final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().metPartijCode
                    ("000001").metStuurgegevens().metReferentienummer("refNr")
                    .metTijdstipVerzending(ZonedDateTime.of(2016, 9, 27, 14, 17, 0, 0,
                            ZoneId.of("UTC"))).metZendendePartij(
                            TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build())
                    .eindeStuurgegevens()
                    .metResultaat(
                            BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam())
                                    .metVerwerking(
                                            VerwerkingsResultaat
                                                    .GESLAAGD).build())
                    .build();
            final BerichtAfnemerindicatie berichtAfnemerindicatie = BerichtAfnemerindicatie.builder().metPartijCode("1")
                    .metTijdstipRegistratie(ZonedDateTime.of(2016, 9, 27, 14, 17, 0, 0,
                            ZoneId.of("UTC"))).metBsn("123").build();
            ((RegistreerAfnemerindicatieCallback) a.getArguments()[1]).verwerkResultaat(SoortDienst.PLAATSING_AFNEMERINDICATIE,
                    new OnderhoudAfnemerindicatieAntwoordBericht(basisBerichtGegevens, berichtAfnemerindicatie)
            );
            return null;
        }).when(onderhoudAfnemerindicatieService).onderhoudAfnemerindicatie(any(), any());

        DOMSource response = onderhoudAfnemerindicatiesWebService.invoke(request);

        ArgumentCaptor<AfnemerindicatieVerzoek> argumentCaptor =
                ArgumentCaptor.forClass(AfnemerindicatieVerzoek.class);
        ArgumentCaptor<RegistreerAfnemerindicatieCallback> callbackCaptor =
                ArgumentCaptor.forClass(RegistreerAfnemerindicatieCallback.class);
        Mockito.verify(onderhoudAfnemerindicatieService).onderhoudAfnemerindicatie(argumentCaptor.capture(), callbackCaptor.capture());
        AfnemerindicatieVerzoek afnemerindicatieVerzoek = argumentCaptor.getValue();
        Assert.assertEquals(OIN_ONDERTEKENAAR, afnemerindicatieVerzoek.getOin().getOinWaardeOndertekenaar());
        Assert.assertEquals(OIN_TRANSPORTEUR, afnemerindicatieVerzoek.getOin().getOinWaardeTransporteur());
        Assert.assertNotNull(response);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebServiceException.class)
    public void foutiefAntwoordbericht() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_synRegistreerAfnemerindicatie_plaats.xml").getInputStream()));
        doAnswer(a -> {
            final RegistreerAfnemerindicatieCallbackImpl o = (RegistreerAfnemerindicatieCallbackImpl) a.getArguments()[1];
            ReflectionTestUtils.setField(o, "xml", "<foute><xml>");
            return null;
        }).when(onderhoudAfnemerindicatieService).onderhoudAfnemerindicatie(any(), any());

        onderhoudAfnemerindicatiesWebService.invoke(request);
    }

    @Test(expected = Fault.class)
    public void gooitSoapFaultBijFalenValidatie() {
        doThrow(SchemaValidatorService.SchemaValidatieException.class).when(schemaValidatorService).valideer(any(), any());

        onderhoudAfnemerindicatiesWebService.invoke(null);
    }


    @Test
    public void valideerAfnemerindicatieSchema() throws Exception {
        final String pad = "/lvg_synRegistreerAfnemerindicatie.xml";
        SchemaValidatorService.doValideerTegenSchema(new StreamSource(new ClassPathResource(pad).getInputStream()),
                OnderhoudAfnemerindicatiesWebServiceImpl.SCHEMA);
    }


}
