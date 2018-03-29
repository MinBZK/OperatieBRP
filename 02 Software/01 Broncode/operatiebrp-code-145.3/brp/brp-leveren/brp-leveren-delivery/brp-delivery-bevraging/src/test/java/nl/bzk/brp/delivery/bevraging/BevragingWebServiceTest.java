/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.apache.cxf.interceptor.Fault;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;
import org.xml.sax.InputSource;

/**
 * Unit test voor {@link BevragingWebService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BevragingWebServiceTest {
    private static final String OIN_ONDERTEKENAAR = "00000001001932603000";
    private static final String OIN_TRANSPORTEUR = "00000001001932603001";

    @InjectMocks
    private BevragingWebService service;
    @Mock
    private OinResolver oinResolver;
    @Mock
    private SchemaValidatorService schemaValidatorService;
    @Spy
    private Map<SoortDienst, BevragingVerzoekVerwerker<BevragingVerzoek>> bevragingVerzoekVerwerkerMap = new HashMap<>();

    @Mock
    private BevragingVerzoekVerwerker<BevragingVerzoek> bevragingVerzoekVerwerker;

    @Before
    public void voorTest() {
        when(oinResolver.get()).thenReturn(new OIN(OIN_ONDERTEKENAAR, OIN_TRANSPORTEUR));
    }

    @Test
    public void geefDetailsPersoonHappyFlow() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_bvgGeefDetailsPersoon_bsn.xml").getInputStream()));
        bevragingVerzoekVerwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, bevragingVerzoekVerwerker);

        Mockito.doAnswer(a -> {
            final BevragingCallbackImpl bevragingCallback = (BevragingCallbackImpl) a.getArguments()[1];
            final BevragingResultaat resultaat = new BevragingResultaat();
            resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metCode("000000").build());

            final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
            bevragingVerzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
            bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(1));
            bevragingCallback.verwerkResultaat(bevragingVerzoek, resultaat);
            return null;
        }).when(bevragingVerzoekVerwerker).verwerk(Mockito.any(), Mockito.any());

        service.invoke(request);

        verify(schemaValidatorService).valideer(request, BevragingWebService.SCHEMA);
    }



    @Test(expected = WebServiceException.class)
    public void exceptieBijOnverwachteDienst() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_bvgGeefDetailsPersoon_bsn.xml").getInputStream()));
        bevragingVerzoekVerwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, bevragingVerzoekVerwerker);

        Mockito.doAnswer(a -> {
            final BevragingCallbackImpl bevragingCallback = (BevragingCallbackImpl) a.getArguments()[1];
            final BevragingResultaat resultaat = new BevragingResultaat();
            resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().build());

            final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
            bevragingVerzoek.setSoortDienst(SoortDienst.ATTENDERING);
            bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(1));
            bevragingCallback.verwerkResultaat(bevragingVerzoek, resultaat);
            return null;
        }).when(bevragingVerzoekVerwerker).verwerk(Mockito.any(), Mockito.any());

        service.invoke(request);

        verify(schemaValidatorService).valideer(request, BevragingWebService.SCHEMA);
    }

    @Test(expected = WebServiceException.class)
    public void exceptieAlsResponseNietGeschrevenKanWorden() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_bvgGeefDetailsPersoon_bsn.xml").getInputStream()));
        bevragingVerzoekVerwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, bevragingVerzoekVerwerker);

        Mockito.doAnswer(a -> {
            final BevragingCallbackImpl bevragingCallback = (BevragingCallbackImpl) a.getArguments()[1];
            final BevragingResultaat resultaat = new BevragingResultaat();
            //dit zal een npe forceren en dus geen xml
            //resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().build());

            final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
            bevragingVerzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
            bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(1));
            bevragingCallback.verwerkResultaat(bevragingVerzoek, resultaat);
            return null;
        }).when(bevragingVerzoekVerwerker).verwerk(Mockito.any(), Mockito.any());

        service.invoke(request);

        verify(schemaValidatorService).valideer(request, BevragingWebService.SCHEMA);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebServiceException.class)
    public void foutiefAntwoordbericht() throws Exception {
        bevragingVerzoekVerwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, bevragingVerzoekVerwerker);
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_bvgGeefDetailsPersoon_bsn.xml").getInputStream()));
        doAnswer(a -> {
            final BevragingCallbackImpl o = (BevragingCallbackImpl) a.getArguments()[1];
            ReflectionTestUtils.setField(o, "xml", "<foute><xml>");
            return null;
        }).when(bevragingVerzoekVerwerker).verwerk(any(), any());

        service.invoke(request);
    }

    @Test(expected = Fault.class)
    public void gooitSoapFaultBijFalenValidatie() {
        doThrow(SchemaValidatorService.SchemaValidatieException.class).when(schemaValidatorService).valideer(any(), any());

        service.invoke(null);
    }

    @Test(expected = WebServiceException.class)
    public void geefDetailsPersoon_AlgFout() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_bvgGeefDetailsPersoon_bsn.xml").getInputStream()));
        bevragingVerzoekVerwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, bevragingVerzoekVerwerker);

        Mockito.doAnswer(a -> {
            final BevragingCallbackImpl bevragingCallback = (BevragingCallbackImpl) a.getArguments()[1];
            final BevragingResultaat resultaat = new BevragingResultaat();
            resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().build());

            final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
            bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(1));
            bevragingCallback.verwerkResultaat(bevragingVerzoek, resultaat);
            return null;
        }).when(bevragingVerzoekVerwerker).verwerk(Mockito.any(), Mockito.any());

        service.invoke(request);
    }

    @Test(expected = WebServiceException.class)
    public void geefDetailsPersoon_SoortdienstOntbreekt() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("lvg_bvgGeefDetailsPersoon_bsn.xml").getInputStream()));
       // bevragingVerzoekVerwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, bevragingVerzoekVerwerker);

        Mockito.doAnswer(a -> {
            final BevragingCallbackImpl bevragingCallback = (BevragingCallbackImpl) a.getArguments()[1];
            final BevragingResultaat resultaat = new BevragingResultaat();
            resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().build());

            final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
            bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(1));
            bevragingCallback.verwerkResultaat(bevragingVerzoek, resultaat);
            return null;
        }).when(bevragingVerzoekVerwerker).verwerk(Mockito.any(), Mockito.any());

        service.invoke(request);
    }

    @Test
    public void valideerTegenBevragingSchema() throws Exception {
        final String pad = "/lvg_bvgZoekPersoon.xml";
        SchemaValidatorService.doValideerTegenSchema(new StreamSource(new ClassPathResource(pad).getInputStream()), BevragingWebService.SCHEMA);
    }

    @Test
    public void defaultConstructor() {
        Assert.notNull(new BevragingWebService(), "Ws mag niet null zijn na constructor aanroep.");
    }

}
