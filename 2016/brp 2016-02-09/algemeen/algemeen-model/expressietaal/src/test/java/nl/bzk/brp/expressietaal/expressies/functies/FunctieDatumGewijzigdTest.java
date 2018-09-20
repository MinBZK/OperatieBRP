/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.ONWAAR;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.WAAR;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(Parameterized.class)
public class FunctieDatumGewijzigdTest {
    private final String datumVorig;
    private final String datumHuidig;
    private final String resultaat;

    public FunctieDatumGewijzigdTest(final String datumVorig, final String datumHuidig, final String resultaat) {
        this.datumVorig = datumVorig;
        this.datumHuidig = datumHuidig;
        this.resultaat = resultaat;
    }

    @Parameterized.Parameters(name = "{index}: GEWIJZIGD({0}, {1}) = {2}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
            { null, "00000000", WAAR },
            { "00000000", "00000000", ONWAAR },
            { "00000000", "19880000", WAAR },
            { "00000000", "19880309", WAAR },
            { null, "19880309", WAAR },
            { "19880000", "19880000", ONWAAR },
            { "19880000", "00000000", WAAR },
            { "19880300", "19880000", WAAR },
            { "00000000", null, WAAR },
            { null, null, ONWAAR },
            { "19880309", "19880309", ONWAAR },
        });
    }

    @Test
    public void testDatumGewijzigd() {
        // arrange
        final PersoonHisVolledigImpl testPersoon1 = TestPersoonJohnnyJordaan.maak();
        if (datumVorig == null) {
            //verwijder attribuut indien null (=ontbrekend)
            ReflectionTestUtils.setField(testPersoon1.getPersoonGeboorteHistorie().getActueleRecord(),
                "datumGeboorte", null);
        } else {
            ReflectionTestUtils.setField(testPersoon1.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                "waarde", Integer.parseInt(datumVorig));
        }

        final PersoonHisVolledigImpl testPersoon2 = TestPersoonJohnnyJordaan.maak();
        if (datumHuidig == null) {
            //verwijder attribuut indien null (=ontbrekend)
            ReflectionTestUtils.setField(testPersoon2.getPersoonGeboorteHistorie().getActueleRecord(),
                "datumGeboorte", null);
        } else {
            ReflectionTestUtils.setField(testPersoon2.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                "waarde", Integer.parseInt(datumHuidig));
        }

        final PersoonView testPersoonView1 = new PersoonView(testPersoon1);
        final PersoonView testPersoonView2 = new PersoonView(testPersoon2);

        final Context context = new Context();
        context.definieer("persoon1", new BrpObjectExpressie(testPersoonView1, ExpressieType.PERSOON));
        context.definieer("persoon2", new BrpObjectExpressie(testPersoonView2, ExpressieType.PERSOON));

        final String expressie = "GEWIJZIGD(persoon1, persoon2, [geboorte.datum])";
        final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);

        // act
        final Expressie expressieResultaat = BRPExpressies.evalueer(geparsdeExpressie.getExpressie(), context);

        // assert
        assertEquals(resultaat, expressieResultaat.alsString());
    }
}
