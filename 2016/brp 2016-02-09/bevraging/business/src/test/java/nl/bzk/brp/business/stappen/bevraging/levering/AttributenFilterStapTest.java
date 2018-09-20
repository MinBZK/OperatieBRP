/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.ArrayList;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.MaterieleHistoriePredikaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AttributenFilterStapTest {

    @Mock
    private AttributenFilterService attributenFilterService;

    @InjectMocks
    private AttributenFilterStap attributenFilterStap = new AttributenFilterStap();

    @Test
    public final void testVoerStapUitFilterWordtAangeroepen() throws ExpressieExceptie {
        final Leveringsautorisatie abo = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.ATTENDERING);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metDummyGeautoriseerde().metLeveringsautorisatie(abo).maak();
        final BevragingBerichtContextBasis context =
            new BevragingBerichtContextBasis(new BerichtenIds(1L, 2L), null, null, null);
        final Dienst dienst = abo.geefDienst(SoortDienst.ATTENDERING);
        context.setLeveringinformatie(new Leveringinformatie(tla, dienst));

        final PersoonHisVolledigImpl gevondenPersoon1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .build();
        final PersoonHisVolledigImpl gevondenPersoon2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .build();
        final BevragingResultaat resultaat = new BevragingResultaat(null);
        resultaat.voegGevondenPersoonToe(new PersoonHisVolledigView(
            gevondenPersoon1,
            MaterieleHistoriePredikaat.geldigOp(DatumTijdAttribuut.nu(), DatumAttribuut.vandaag())));
        resultaat.voegGevondenPersoonToe(new PersoonHisVolledigView(
            gevondenPersoon2,
                MaterieleHistoriePredikaat.geldigOp(DatumTijdAttribuut.nu(), DatumAttribuut.vandaag())));

        attributenFilterStap.voerStapUit(null, context, resultaat);

        Mockito.verify(attributenFilterService, Mockito.times(1)).zetMagGeleverdWordenVlaggen(new ArrayList<>(resultaat.getGevondenPersonen()), dienst,
                                                                                              Rol.DUMMY);
    }
}
