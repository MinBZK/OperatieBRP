/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.MaterieleHistoriePredikaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
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
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class VoorkomenFilterStapTest {

    private static final String              ID                  = "iD";
    @InjectMocks
    private final        VoorkomenFilterStap voorkomenFilterStap = new VoorkomenFilterStap();

    @Mock
    private VoorkomenFilterService voorkomenFilterService;

    @Test
    public final void testVoerStapUitFilterWordtAangeroepen() throws ExpressieExceptie {

        final Dienst dienst = TestDienstBuilder.maker().maak();

        final BevragingBerichtContextBasis context = new BevragingBerichtContextBasis(new BerichtenIds(1L, 2L), null, null, null);
        context.setLeveringinformatie(new Leveringinformatie(null, dienst));

        final PersoonHisVolledigImpl gevondenPersoon1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(gevondenPersoon1, ID, 12);
        final PersoonHisVolledigImpl gevondenPersoon2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(gevondenPersoon2, ID, 13);
        final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(gevondenPersoon1,
            MaterieleHistoriePredikaat.geldigOp(DatumTijdAttribuut.nu(), DatumAttribuut.vandaag()));
        final PersoonHisVolledigView persoonHisVolledigView2 = new PersoonHisVolledigView(gevondenPersoon2,
            MaterieleHistoriePredikaat.geldigOp(DatumTijdAttribuut.nu(),
                DatumAttribuut.vandaag()));

        final BevragingResultaat resultaat = new BevragingResultaat(null);
        resultaat.voegGevondenPersoonToe(persoonHisVolledigView1);
        resultaat.voegGevondenPersoonToe(persoonHisVolledigView2);

        voorkomenFilterStap.voerStapUit(null, context, resultaat);

        Mockito.verify(voorkomenFilterService, Mockito.times(1)).voerVoorkomenFilterUit(persoonHisVolledigView1, dienst);
        Mockito.verify(voorkomenFilterService, Mockito.times(1)).voerVoorkomenFilterUit(persoonHisVolledigView2, dienst);
    }
}
