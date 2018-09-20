/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.regels;

import nl.bzk.brp.business.regels.Bedrijfsregel;
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
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BRLV0014Test {

    private static final String POPULATIEBEPERKING_WAAR   = "WAAR";
    private static final String POPULATIEBEPERKING_ONWAAR = "ONWAAR";

    @Mock
    private ExpressieService expressieService;

    @InjectMocks
    private final BRLV0014 regel = new BRLV0014();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0014, regel.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AutorisatieRegelContext.class, regel.getContextType());
    }

    @Test
    public void testVoerRegelUitIsCorrectMetLeveringsautorisatie() throws ExpressieExceptie {
        final Expressie expressie = Mockito.mock(Expressie.class);
        final Expressie geevalueerdeExpressie = Mockito.mock(Expressie.class);
        Mockito.when(expressie.evalueer(Mockito.any(Context.class))).thenReturn(geevalueerdeExpressie);
        Mockito.when(geevalueerdeExpressie.alsBoolean()).thenReturn(true);
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn
            (expressie);

        final PersoonView persoon = maakHuidigeSituatie();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(POPULATIEBEPERKING_WAAR, SoortDienst.DUMMY);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);

        final boolean regelResultaat = regel.valideer(regelContext);
        Assert.assertEquals(Bedrijfsregel.VALIDE, regelResultaat);
    }

    @Test
    public void testVoerRegelUitMetLeveringsautorisatieKanNietVanwegeExpressie() throws ExpressieExceptie {
        final Expressie expressie = Mockito.mock(Expressie.class);
        final Expressie geevalueerdeExpressie = Mockito.mock(Expressie.class);
        Mockito.when(expressie.evalueer(Mockito.any(Context.class))).thenReturn(geevalueerdeExpressie);
        Mockito.when(geevalueerdeExpressie.alsBoolean()).thenReturn(false);
        Mockito.when(expressieService.geefPopulatiebeperking(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn
            (expressie);

        final PersoonView persoon = maakHuidigeSituatie();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(POPULATIEBEPERKING_ONWAAR, SoortDienst.DUMMY);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);

        final boolean regelResultaat = regel.valideer(regelContext);
        Assert.assertEquals(Bedrijfsregel.INVALIDE, regelResultaat);
    }

    @Test
    public void testVoerRegelUitMetNullHuidigeSituatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.metDienst(POPULATIEBEPERKING_WAAR, SoortDienst.DUMMY);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker()
            .metLeveringsautorisatie(leveringsautorisatie).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, null, null, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);

        final boolean regelResultaat = regel.valideer(regelContext);
        Assert.assertEquals(Bedrijfsregel.VALIDE, regelResultaat);
    }

    private PersoonView maakHuidigeSituatie() {
        return new PersoonView(TestPersoonJohnnyJordaan.maak());
    }


}
