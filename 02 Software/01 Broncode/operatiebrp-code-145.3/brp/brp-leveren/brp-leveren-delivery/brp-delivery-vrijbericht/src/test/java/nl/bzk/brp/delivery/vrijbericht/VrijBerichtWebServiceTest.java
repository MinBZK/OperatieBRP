/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerwerker;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.apache.cxf.interceptor.Fault;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;

/**
 * Unit test voor {@link VrijBerichtWebService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtWebServiceTest {

    @InjectMocks
    private VrijBerichtWebService webService;
    @Mock
    private OinResolver oinResolver;
    @Mock
    private SchemaValidatorService schemaValidatorService;
    @Mock
    private VrijBerichtVerwerker vrijBerichtService;
    @Captor
    private ArgumentCaptor<VrijBerichtVerzoek> verzoekArgumentCaptor;

    @Test
    public void testInvoke() throws Exception {
        //@formatter:off
        final ZonedDateTime time = ZonedDateTime.of(2016, 10, 5, 8, 42, 0, 0, ZoneId.of("CET"));
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metReferentienummer("referentienummer")
                    .metCrossReferentienummer("crossReferentienummer")
                    .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                    .metTijdstipVerzending(time)
                .eindeStuurgegevens()
                .metMeldingen(Collections.singletonList(new Melding(Regel.R1321)))
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing")
                    .build()
                ).build();
        //@formatter:on
        final VrijBerichtAntwoordBericht bericht = new VrijBerichtAntwoordBericht(basisBerichtGegevens);
        final DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("vrb_vrbStuurVrijBericht.xml").getInputStream()));
        when(oinResolver.get()).thenReturn(new OIN("123", "456"));
        doAnswer(a -> {
            ((VrijBerichtCallbackImpl) a.getArguments()[1]).verwerkBericht(bericht);
            return null;
        }).when(vrijBerichtService).stuurVrijBericht(any(), any());

        webService.invoke(request);

        InOrder inOrder = inOrder(schemaValidatorService, oinResolver, vrijBerichtService);
        inOrder.verify(schemaValidatorService).valideer(request, VrijBerichtWebService.SCHEMA);
        inOrder.verify(oinResolver).get();
        verify(vrijBerichtService).stuurVrijBericht(verzoekArgumentCaptor.capture(), any(VrijBerichtCallbackImpl.class));
        assertThat(verzoekArgumentCaptor.getValue().getXmlBericht(), is(not(nullValue())));
    }

    @Test(expected = Fault.class)
    public void gooitSoapFaultBijFalenValidatie() {
        doThrow(SchemaValidatorService.SchemaValidatieException.class).when(schemaValidatorService).valideer(any(), any());

        webService.invoke(null);
    }

    @Test
    public void valideerTegenVrijBerichtSchema() throws Exception {
        final String pad = "/vrb_vrbVrijBericht.xml";
        SchemaValidatorService.doValideerTegenSchema(new StreamSource(new ClassPathResource(pad).getInputStream()), VrijBerichtWebService.SCHEMA);
    }

}
