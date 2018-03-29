/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PopulatieTransitieBepalerImplTest {

    @InjectMocks
    private PopulatieTransitieBepalerImpl populatieBepaler;

    @Mock
    private ExpressieService expressieService;


    @Before
    public void setUp() {
        final ZonedDateTime datum = DatumUtil.nuAlsZonedDateTime();
        BrpNu.set(datum);
    }

    @Test
    public final void bepaalPopulatieVoorNieuwPersoonBuiten() throws ExpressieException {
        final Leveringsautorisatie la = TestAutorisaties.metSoortDienst(SoortDienst.ATTENDERING);
        final AdministratieveHandeling ah = TestVerantwoording.maakAdministratieveHandeling(1);
        final Persoonslijst persoonslijst1 = TestBuilders.maakBasisPersoon(1, ah.getActie(1));

        final Expressie expressie = Mockito.mock(Expressie.class);
        Mockito.when(expressieService.evalueer(Mockito.eq(expressie), Mockito.any())).thenReturn(false);
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(persoonslijst1, expressie, la);
        Assert.assertEquals(Populatie.BUITEN, populatie);

        Mockito.verify(expressieService, Mockito.only()).evalueer(Mockito.eq(expressie), Mockito.any());
    }

    @Test
    public final void bepaalPopulatieVoorNieuwPersoonBetreedt() throws ExpressieException {
        final Leveringsautorisatie la = TestAutorisaties.metSoortDienst(SoortDienst.ATTENDERING);
        final AdministratieveHandeling ah = TestVerantwoording.maakAdministratieveHandeling(1);
        final Persoonslijst persoonslijst1 = TestBuilders.maakBasisPersoon(1, ah.getActie(1));
        final Expressie expressie = Mockito.mock(Expressie.class);
        Mockito.when(expressieService.evalueer(Mockito.eq(expressie), Mockito.any())).thenReturn(true);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(persoonslijst1, expressie, la);
        Assert.assertEquals(Populatie.BETREEDT, populatie);

        Mockito.verify(expressieService, Mockito.only()).evalueer(Mockito.eq(expressie), Mockito.any());
    }

    @Test
    public final void bepaalPopulatieVoorBestaandePersoonBuiten() throws ExpressieException {
        //buiten > buiten -> BUITEN
        bepaalPopulatieVoorBestaandePersoonBuiten(Populatie.BUITEN, false, false);

        //buiten > binnen -> BETREEDT
        Mockito.reset(expressieService);
        bepaalPopulatieVoorBestaandePersoonBuiten(Populatie.BETREEDT, false, true);

        //binnen > buiten -> VERLAAT
        Mockito.reset(expressieService);
        bepaalPopulatieVoorBestaandePersoonBuiten(Populatie.VERLAAT, true, false);

        //binnen > binnen -> BINNEN
        Mockito.reset(expressieService);
        bepaalPopulatieVoorBestaandePersoonBuiten(Populatie.BINNEN, true, true);
    }

    private void bepaalPopulatieVoorBestaandePersoonBuiten(final Populatie buiten, final boolean vorigePositie, final boolean huidigePositie) {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetHandelingen(1);
        final Leveringsautorisatie la = TestAutorisaties.metSoortDienst(SoortDienst.ATTENDERING);
        final Expressie expressie = Mockito.mock(Expressie.class);
        Mockito.when(expressieService.evalueer(Mockito.eq(expressie), Mockito.any())).thenReturn(huidigePositie, vorigePositie);
        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(persoonslijst, expressie, la);
        Assert.assertEquals(buiten, populatie);

        Mockito.verify(expressieService, Mockito.times(2)).evalueer(Mockito.eq(expressie), Mockito.any());
    }
}
