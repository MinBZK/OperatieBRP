/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PopulatieBinnenBuitenBepalerImplTest {

    private static final ZonedDateTime TSREG_2010 = ZonedDateTime.of(2010, 6, 1, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);

    @InjectMocks
    private PopulatieBinnenBuitenBepalerImpl populatieBepaler;

    @Mock
    private ExpressieService expressieService;

    //@formatter:off
    private final Persoonslijst         testPersoon      = new Persoonslijst(
        TestBuilders.maakLeegPersoon()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(TestVerantwoording.maakActie(1, TSREG_2010))
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build(),
        0L);
    //@formatter:on
    private Leveringsautorisatie leveringsautorisatie;
    private Expressie expressie;

    @Before
    public final void setup() {
        expressie = null;
        leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
    }

    @Test
    public final void testNieuwPersoonBuiten() throws ExpressieException {
        when(expressieService.evalueer(any(), any())).thenReturn(false);
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon,
                expressie,
                leveringsautorisatie);
        assertThat(populatie, is(Populatie.BUITEN));

        Mockito.verify(expressieService).evalueer(any(), any());
    }

    @Test
    public final void testBestaandPersoonBinnen() throws ExpressieException {
        when(expressieService.evalueer(any(Expressie.class), any(Persoonslijst.class)))
                .thenReturn(true);

        final Populatie populatie = populatieBepaler
                .bepaalInUitPopulatie(testPersoon, expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.BINNEN));

        Mockito.verify(expressieService).evalueer(any(), any());
    }

    @Test
    public final void testWaarbijExpressieEvalueertNaarNull() throws ExpressieException {
        final Expressie nullExpressieResultaat = mock(Expressie.class);
        when(nullExpressieResultaat.isNull()).thenReturn(true);
        when(expressieService.evalueer(any(), any())).thenReturn(null);
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, expressie,
                leveringsautorisatie);
        assertThat(populatie, is(Populatie.BUITEN));

        Mockito.verify(expressieService).evalueer(any(), any());
    }
}
