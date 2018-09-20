/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class FunctieGewijzigdTest {

    private static final String GEWIJZIGD_PERSOON1_PERSOON2_ANUMMER = "GEWIJZIGD(persoon1, persoon2, [anummer])";
    private static final String PERSOON_1 = "persoon1";
    private static final String PERSOON_2 = "persoon2";

    @Test
    public void testGewijzigdANummerNietsGewijzigd() {
        final PersoonHisVolledigImpl testPersoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonView testPersoonView1 = new PersoonView(testPersoon1);

        final PersoonHisVolledigImpl testPersoon2 = TestPersoonJohnnyJordaan.maak();
        final PersoonView testPersoonView2 = new PersoonView(testPersoon2);

        final String expressie = GEWIJZIGD_PERSOON1_PERSOON2_ANUMMER;

        final Context context = new Context();
        context.definieer(PERSOON_1, new BrpObjectExpressie(testPersoonView1, ExpressieType.PERSOON));
        context.definieer(PERSOON_2, new BrpObjectExpressie(testPersoonView2, ExpressieType.PERSOON));

        final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);
        final Expressie expressieNaParsing = geparsdeExpressie.getExpressie();


        final Expressie expressieResultaat = BRPExpressies.evalueer(expressieNaParsing, context);

        assertEquals(ExpressieType.BOOLEAN, expressieNaParsing.getType(context));
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testGewijzigdANummerIetsGewijzigd() {
        final PersoonHisVolledigImpl testPersoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonView testPersoonView1 = new PersoonView(testPersoon1);

        final PersoonHisVolledigImpl testPersoon2 = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(
                testPersoon2.getPersoonIdentificatienummersHistorie().getActueleRecord().getAdministratienummer(),
                "waarde", Long.MAX_VALUE);
        final PersoonView testPersoonView2 = new PersoonView(testPersoon2);

        final String expressie = GEWIJZIGD_PERSOON1_PERSOON2_ANUMMER;

        final Context context = new Context();
        context.definieer(PERSOON_1, new BrpObjectExpressie(testPersoonView1, ExpressieType.PERSOON));
        context.definieer(PERSOON_2, new BrpObjectExpressie(testPersoonView2, ExpressieType.PERSOON));

        final ParserResultaat geparsdeExpressie = BRPExpressies.parse(expressie);

        final Expressie expressieResultaat = BRPExpressies.evalueer(geparsdeExpressie.getExpressie(), context);

        assertTrue(expressieResultaat.alsBoolean());
    }

    @Test
    public void testGewijzigdFunctieMetFunctieParameters() {
        final PersoonHisVolledigImpl testPersoon1 = TestPersoonJohnnyJordaan.maak();
        final PersoonView testPersoonView1 = new PersoonView(testPersoon1);

        final PersoonHisVolledigImpl testPersoon2 = TestPersoonJohnnyJordaan.maak();

        final FamilierechtelijkeBetrekkingHisVolledigImpl ouder = RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonRolInHeeft(testPersoon2, SoortBetrokkenheid.OUDER);
        final GeslachtsnaamstamAttribuut stam = ouder.getKindBetrokkenheid().getPersoon().getPersoonSamengesteldeNaamHistorie().getActueleRecord().getGeslachtsnaamstam();
        ReflectionTestUtils.setField(stam, "waarde", "stam_aangepast");
        final PersoonView testPersoonView2 = new PersoonView(testPersoon2);

        final Context context = new Context();
        context.definieer("oud", new BrpObjectExpressie(testPersoonView1, ExpressieType.PERSOON));
        context.definieer("nieuw", new BrpObjectExpressie(testPersoonView2, ExpressieType.PERSOON));

        ParserResultaat geparsdeExpressie = BRPExpressies.parse("GEWIJZIGD(KINDEREN(oud), KINDEREN(nieuw), [samengestelde_naam.geslachtsnaamstam])");
        Expressie expressieResultaat = BRPExpressies.evalueer(geparsdeExpressie.getExpressie(), context);
        assertTrue(expressieResultaat.alsBoolean());

        // vergelijk op een ander attribuut zou niet een wijziging moeten opleveren
        geparsdeExpressie = BRPExpressies.parse("GEWIJZIGD(KINDEREN(oud), KINDEREN(nieuw), [samengestelde_naam.voornamen])");
        expressieResultaat = BRPExpressies.evalueer(geparsdeExpressie.getExpressie(), context);
        assertFalse(expressieResultaat.alsBoolean());

        // vergelijk op een ander attribuut zou niet een wijziging moeten opleveren, ook niet indien alle waardes null zijn.
        geparsdeExpressie = BRPExpressies.parse("GEWIJZIGD(KINDEREN(oud), KINDEREN(nieuw), [samengestelde_naam.scheidingsteken])");
        expressieResultaat = BRPExpressies.evalueer(geparsdeExpressie.getExpressie(), context);
        assertFalse(expressieResultaat.alsBoolean());
    }

}
