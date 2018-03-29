/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link StufBerichtServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StufBerichtServiceImplTest {

    @InjectMocks
    private StufBerichtServiceImpl stufBerichtService;

    @Mock
    private PartijService partijService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieValidatieService;
    @Mock
    private StufBerichtBerichtFactory stufBerichtBerichtFactory;
    @Mock
    private StufBerichtInhoudControleService stufBerichtInhoudControleService;
    @Mock
    private StufVertaalService stufVertaalService;

    private Partij zendendePartij;
    private StufBerichtVerzoek verzoek;

    @Before
    public void before() {
        zendendePartij = TestPartijBuilder.maakBuilder().metId(2).metCode("000456").metDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(1)).build();
        when(partijService.vindPartijOpCode("000456")).thenReturn(zendendePartij);

        when(partijService.geefBrpPartij()).thenReturn(zendendePartij);

        verzoek = new StufBerichtVerzoek();
        verzoek.setSoortDienst(SoortDienst.GEEF_STUF_BG_BERICHT);
        verzoek.getStuurgegevens().setZendendePartijCode("000456");
        verzoek.getParameters().setLeveringsAutorisatieId("1");
    }

    @Test
    public void happyFlow() throws Exception {
        final StufBericht stufBericht = new StufBericht("stuf", "Mutatiebericht");

        final StufTransformatieResultaat transormatie = new StufTransformatieResultaat();
        final String vertaling1 = "stuf";
        transormatie.getVertalingen().add(new StufTransformatieVertaling("stuf"));
        when(stufVertaalService.vertaal(Mockito.any())).thenReturn(transormatie);

        final StufBerichtResultaat resultaat = stufBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen(), is(Collections.emptyList()));
        inOrder(partijService, leveringsautorisatieValidatieService, stufBerichtInhoudControleService, stufVertaalService);
        Assert.assertEquals(vertaling1, resultaat.getStufVertalingen().stream().findFirst().orElse(null).getVertaling());
    }


    @Test
    public void meldingBijAutorisatieException() throws Exception {
        doThrow(new AutorisatieException(new Melding(Regel.R2524))).when(leveringsautorisatieValidatieService).controleerAutorisatie(Mockito.any());

        final StufBerichtResultaat resultaat = stufBerichtService.verwerkVerzoek(verzoek);
        //algemene autorisatie fout
        assertThat(resultaat.getMeldingen().get(0).getRegel(), is(Regel.R2343));
    }

    @Test
    public void stapMeldingException() throws Exception {
        doThrow(new StapMeldingException(new Melding(Regel.R2439))).when(stufBerichtInhoudControleService).controleerInhoud(any());

        final StufBerichtResultaat resultaat = stufBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen().get(0).getRegel(), is(Regel.R2439));
    }


    @Test(expected = RuntimeException.class)
    public void runtimeException() throws Exception {
        doThrow(new RuntimeException("krak")).when(stufVertaalService).vertaal(any());

        stufBerichtService.verwerkVerzoek(verzoek);
    }
}
