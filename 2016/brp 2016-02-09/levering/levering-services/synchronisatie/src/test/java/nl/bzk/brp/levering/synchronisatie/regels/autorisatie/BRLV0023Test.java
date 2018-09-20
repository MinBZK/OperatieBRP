/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.autorisatie;

import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BRLV0023Test {

    private static final String POPULATIEBEPERKING_WAAR   = "WAAR";
    private static final String POPULATIEBEPERKING_ONWAAR = "ONWAAR";

    @Mock
    private ExpressieService expressieService;

    @InjectMocks
    private BRLV0023 regel = new BRLV0023();

    @Test
    public final void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0023, regel.getRegel());
    }

    @Test
    public final void testVoerRegelUitGeenMeldingen() throws ExpressieExceptie {
        final Expressie expressie = Mockito.mock(Expressie.class);
        final Expressie geevalueerdeExpressie = Mockito.mock(Expressie.class);
        Mockito.when(expressie.evalueer(Mockito.any(Context.class))).thenReturn(geevalueerdeExpressie);
        Mockito.when(geevalueerdeExpressie.alsBoolean()).thenReturn(true);
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn
            (expressie);

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();
        final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(toegangLeveringsautorisatie,
            leveringsautorisatie.geefDiensten().iterator().next(), null,
            SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        final boolean isValide = regel.valideer(regelContext);
        Assert.assertTrue(isValide);
    }

    @Test
    public final void testVoerRegelUitPersoonValtBuitenPopulatieCriterium() throws ExpressieExceptie {
        final Expressie expressie = Mockito.mock(Expressie.class);
        final Expressie geevalueerdeExpressie = Mockito.mock(Expressie.class);
        Mockito.when(expressie.evalueer(Mockito.any(Context.class))).thenReturn(geevalueerdeExpressie);
        Mockito.when(geevalueerdeExpressie.alsBoolean()).thenReturn(false);
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn
            (expressie);

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(POPULATIEBEPERKING_ONWAAR,
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, leveringsautorisatie.geefDiensten().iterator().next(), null,
                SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        final boolean isValide = regel.valideer(regelContext);
        Assert.assertFalse(isValide);
    }

    @Test
    public final void testVoerRegelUitGeenMeldingenDienstIsNull() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.zonderGeldigeDienst(POPULATIEBEPERKING_WAAR);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, null,
                null, SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);
        final boolean isValide = regel.valideer(regelContext);
        Assert.assertTrue(isValide);
    }
}
