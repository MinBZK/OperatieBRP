/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.levering.algemeen.service.ToekomstigeActieService;
import nl.bzk.brp.levering.business.bepalers.impl.PopulatieBinnenBuitenBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonPredikaatView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopulatieBinnenBuitenBepalerTest extends AbstractBepalerTest {

    private static final DatumTijdAttribuut TSREG_2010 = DatumTijdAttribuut.bouwDatumTijd(2010, 6, 1, 0, 0, 0);

    @InjectMocks
    private final PopulatieBinnenBuitenBepaler populatieBepaler = new PopulatieBinnenBuitenBepaler();

    @Mock
    private ToekomstigeActieService toekomstigeActieService;

    @Mock
    private ExpressieService expressieService;

    private final AdministratieveHandelingModel admhndVerhuizing = mock(AdministratieveHandelingModel.class);
    private final PersoonHisVolledigImpl        testPersoon      = getTestPersoon();
    private Leveringsautorisatie leveringsautorisatie;
    private Expressie            expressie;

    @Before
    public final void setup() {
        expressie = null;
        leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TESTABO").maak();
        when(toekomstigeActieService.geefToekomstigeActieIds(any(AdministratieveHandelingModel.class),
            any(PersoonHisVolledigImpl.class))).thenReturn(new HashSet<Long>());
        when(admhndVerhuizing.getTijdstipRegistratie()).thenReturn(TSREG_2010);
    }

    @Test
    public final void testNieuwPersoonBuiten() throws ExpressieExceptie {
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.ONWAAR);
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, admhndVerhuizing, expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.BUITEN));
    }

    @Test
    public final void testBestaandPersoonBinnen() throws ExpressieExceptie {
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.WAAR, BooleanLiteralExpressie.WAAR);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, admhndVerhuizing, expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.BINNEN));
    }

    @Test
    public final void testFoutInExpressie() throws ExpressieExceptie {
        expressie = new StringLiteralExpressie("Test");
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class))).thenThrow(new ExpressieExceptie("FOUT"));
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, admhndVerhuizing, expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.BUITEN));
    }

    @Test
    public final void testWaarbijExpressieEvalueertNaarNull() throws ExpressieExceptie {
        final Expressie nullExpressieResultaat = mock(Expressie.class);
        when(nullExpressieResultaat.isNull()).thenReturn(true);
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(nullExpressieResultaat, nullExpressieResultaat);
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, admhndVerhuizing, expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.BUITEN));
    }

}
