/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;

public final class OnbekendeOudersExpressieTest {

    private static final String ER_ZIJN_OUDER_PERSONEN_EXPRESSIE             = "AANTAL(OUDERS()) > 0";
    private static final String ER_ZIJN_OUDER_PERSONEN_VAN_SOORT_I_EXPRESSIE = "AANTAL(FILTER(OUDERS(), o, o.soort = \"I\")) > 0";
    private static final String ER_ZIJN_OUDER_BETROKKENHEDEN_EXPRESSIE       =
        "AANTAL(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\")) = 1";

    private PersoonHisVolledigImpl persoonVolledigZonderOuderPersonen;

    private PersoonView persoonZonderOuderPersonen;

    @Before
    public void setup() {
        persoonVolledigZonderOuderPersonen = TestPersoonJohnnyJordaan.maak();

        for (final OuderHisVolledigImpl ouderHisVolledig : persoonVolledigZonderOuderPersonen.getKindBetrokkenheid().getRelatie().getOuderBetrokkenheden()) {
            ouderHisVolledig.setPersoon(null);
        }

        persoonZonderOuderPersonen = new PersoonView(persoonVolledigZonderOuderPersonen);
    }

    @Test
    public void testHeeftGeenOuderPersonenOpPersoonView() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_EXPRESSIE, persoonZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderPersonenOpPersoonHisVolledig() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_EXPRESSIE, persoonVolledigZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderPersoonAttributenOpPersoonHisVolledig() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_VAN_SOORT_I_EXPRESSIE, persoonVolledigZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderPersoonAttributenOpPersoonView() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_VAN_SOORT_I_EXPRESSIE, persoonZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testAttribuutOphalenVanLegeOuder() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer("RMAP(OUDERS(), o, $o.soort)", persoonVolledigZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertEquals(0, expressieResultaat.aantalElementen());
    }

    @Test
    public void testAttribuutOphalenVanOuderBetrokkenheidKanWel() {
        final Expressie expressieResultaat =
            BRPExpressies.evalueer(
                "RMAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), b, $b.ouderschap.ouder)",
                persoonVolledigZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertEquals(1, expressieResultaat.aantalElementen());
    }

    @Test
    public void testHeeftWelOuderschapOpPersoonView() {
        final Expressie expressieResultaat =
            BRPExpressies.evalueer(
                ER_ZIJN_OUDER_BETROKKENHEDEN_EXPRESSIE,
                persoonZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftWelOuderschapOpPersoonHisVolledig() {
        final Expressie expressieResultaat =
            BRPExpressies.evalueer(
                ER_ZIJN_OUDER_BETROKKENHEDEN_EXPRESSIE,
                persoonVolledigZonderOuderPersonen);

        assertFalse(expressieResultaat.isFout());
        assertTrue(expressieResultaat.alsBoolean());
    }

}
