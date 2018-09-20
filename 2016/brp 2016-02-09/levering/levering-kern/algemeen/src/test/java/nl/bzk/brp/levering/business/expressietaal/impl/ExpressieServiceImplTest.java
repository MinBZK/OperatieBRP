/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal.impl;

import java.util.Date;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.levering.algemeen.service.DienstFilterExpressiesService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpressieServiceImplTest {

    private static final String WAAR                   = "WAAR";
    private static final String ONWAAR                 = "ONWAAR";
    private static final String WAAR_EN_ONWAAR_EN_WAAR = "WAAR EN (ONWAAR EN WAAR)";
    private static final String WAAR_WAAR_WAAR         = "WAAR EN (WAAR EN WAAR)";
    private static final String TEST                   = "test";

    @InjectMocks
    private final ExpressieService expressieService = new ExpressieServiceImpl();

    @Mock
    private DienstFilterExpressiesService dienstFilterExpressiesService;

    @Test
    public final void testEvalueerWaarPersoon() throws ExpressieExceptie {
        final Persoon testPersoon = new PersoonView(TestPersoonJohnnyJordaan.maak());
        final Expressie expressie = BRPExpressies.parse(WAAR).getExpressie();
        final Date start = new Date();

        final Expressie resultaat = expressieService.evalueer(expressie, testPersoon);
        Assert.assertEquals(BooleanLiteralExpressie.WAAR, resultaat);
    }

    @Test
    public final void testEvalueerWaar() throws ExpressieExceptie {
        final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.parse(WAAR).getExpressie();
        final Expressie resultaat = expressieService.evalueer(expressie, testPersoon);
        Assert.assertEquals(BooleanLiteralExpressie.WAAR, resultaat);
    }

    @Test
    public final void testEvalueerOnwaar() throws ExpressieExceptie {
        final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.parse(ONWAAR).getExpressie();

        final Expressie resultaat = expressieService.evalueer(expressie, testPersoon);

        Assert.assertEquals(BooleanLiteralExpressie.ONWAAR, resultaat);
    }

    @Test
    public final void testGeefPopulatiebeperking() throws ExpressieExceptie {
        final Leveringinformatie leveringinformatie = maakLeveringsAutorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, TEST, WAAR,
            null);
        final Expressie resultaat = expressieService.geefPopulatiebeperking(leveringinformatie.getToegangLeveringsautorisatie()
            .getNaderePopulatiebeperkingExpressieString(), leveringinformatie.getDienst().getDienstbundel().getNaderePopulatiebeperkingExpressieString()
            , leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getPopulatiebeperkingExpressieString());
        Assert.assertEquals(WAAR_WAAR_WAAR, resultaat.toString());
    }

    @Test
    public final void testGeefPopulatiebeperkingMetNaderePopulatiebeperking() throws ExpressieExceptie {
        final Leveringinformatie leveringinformatie = maakLeveringsAutorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, TEST, WAAR, ONWAAR);
        final Expressie resultaat = expressieService.geefPopulatiebeperking(leveringinformatie.getToegangLeveringsautorisatie()
            .getNaderePopulatiebeperkingExpressieString(), leveringinformatie.getDienst().getDienstbundel().getNaderePopulatiebeperkingExpressieString()
            , leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getPopulatiebeperkingExpressieString());
        Assert.assertEquals(WAAR_EN_ONWAAR_EN_WAAR, resultaat.toString());
    }

    @Test
    public final void testGeefPopulatiebeperkingZonderNaderePopulatiebeperking() throws ExpressieExceptie {
        final Leveringinformatie leveringinformatie = maakLeveringsAutorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, TEST, WAAR, null);
        final Expressie resultaat = expressieService.geefPopulatiebeperking(leveringinformatie.getToegangLeveringsautorisatie()
            .getNaderePopulatiebeperkingExpressieString(), leveringinformatie.getDienst().getDienstbundel().getNaderePopulatiebeperkingExpressieString()
            , leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getPopulatiebeperkingExpressieString());
        Assert.assertEquals(WAAR_WAAR_WAAR, resultaat.toString());
    }

    @Test
    public final void testAttenderingscriterium() throws ExpressieExceptie {
        final Leveringinformatie leveringinformatie = maakLeveringsAutorisatie(SoortDienst.ATTENDERING, TEST, ONWAAR, null);
        final Expressie resultaat = expressieService.geefAttenderingsCriterium(leveringinformatie);
        Assert.assertEquals(TEST, resultaat.toString());
    }

    @Test
    public final void testAttenderingscriteriumVerkeerdeCategorieDienst() throws ExpressieExceptie {
        final Leveringinformatie leveringinformatie = maakLeveringsAutorisatie(SoortDienst.ZOEK_PERSOON, TEST, WAAR, null);
        final Expressie resultaat = expressieService.geefAttenderingsCriterium(leveringinformatie);
        Assert.assertEquals(null, resultaat);
    }

    @Test
    public final void testAttenderingscriteriumZonderGevuldAttenderingsCriterium() throws ExpressieExceptie {
        final Expressie resultaat = expressieService.geefAttenderingsCriterium(maakLeveringsAutorisatie(SoortDienst.ATTENDERING, null, ONWAAR, null));
        Assert.assertNull(resultaat);
    }

    @Test
    public final void testAttenderingscriteriumZonderAttenderingsCriterium() throws ExpressieExceptie {
        final Expressie resultaat = expressieService.geefAttenderingsCriterium(maakLeveringsAutorisatie(SoortDienst.ATTENDERING, null, ONWAAR, null));
        Assert.assertNull(resultaat);
    }

    private Leveringinformatie maakLeveringsAutorisatie(SoortDienst soortDienst, String attenderingscriterium, String populatiebeperking, String naderePopulatiebeperking ) {

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metPopulatiebeperking(populatiebeperking).maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Dienst dienst = TestDienstBuilder.maker().metAttenderingscriterium(attenderingscriterium).metSoortDienst(soortDienst).maak();
        TestDienstbundelBuilder.maker().metDiensten(dienst).metNaderePopulatiebeperking(naderePopulatiebeperking).maak();
        return new Leveringinformatie(tla, dienst);
    }
}
