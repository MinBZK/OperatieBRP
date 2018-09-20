/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public final class OntbrekendeOudersExpressieTest {

    private static final String ER_ZIJN_OUDER_PERSONEN_EXPRESSIE             = "AANTAL(OUDERS()) > 0";
    private static final String ER_ZIJN_OUDER_PERSONEN_VAN_SOORT_I_EXPRESSIE = "AANTAL(FILTER(OUDERS(), o, o.soort = \"I\")) > 0";
    private static final String ER_ZIJN_OUDER_BETROKKENHEDEN_EXPRESSIE       =
        "AANTAL(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\")) > 0";

    private PersoonHisVolledigImpl persoonVolledigZonderOuderBetrokkenheden;

    private PersoonView persoonZonderOuderBetrokkenheden;

    @Before
    public void setup() {
        persoonVolledigZonderOuderBetrokkenheden = TestPersoonJohnnyJordaan.maak();

        RelatieHisVolledigImpl relatie = persoonVolledigZonderOuderBetrokkenheden.getKindBetrokkenheid().getRelatie();
        for (final OuderHisVolledigImpl ouderHisVolledig : relatie.getOuderBetrokkenheden()) {
            relatie.getBetrokkenheden().remove(ouderHisVolledig);
        }

        persoonZonderOuderBetrokkenheden = new PersoonView(persoonVolledigZonderOuderBetrokkenheden);
    }

    @Test
    public void testHeeftGeenOuderPersonenOpPersoonHisVolledig() {
        final Expressie expressieResultaat = BRPExpressies
                .evalueer(ER_ZIJN_OUDER_PERSONEN_EXPRESSIE, persoonVolledigZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderPersonenOpPersoonView() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_EXPRESSIE, persoonZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderPersoonAttributenOpPersoonHisVolledig() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_VAN_SOORT_I_EXPRESSIE, persoonVolledigZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderPersoonAttributenOpPersoonView() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer(ER_ZIJN_OUDER_PERSONEN_VAN_SOORT_I_EXPRESSIE, persoonZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testAttribuutOphalenVanLegeOuder() {
        final Expressie expressieResultaat = BRPExpressies
            .evalueer("RMAP(OUDERS(), o, $o.samengestelde_naam.geslachtsnaamstam)", persoonVolledigZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertEquals(0, expressieResultaat.aantalElementen());
    }

    @Test
    public void testAttribuutOphalenVanNietBestaandeOuderBetrokkenheid() {
        final Expressie expressieResultaat =
            BRPExpressies.evalueer(
                "RMAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), b, $b.ouderschap.ouder)",
                    persoonVolledigZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertEquals(0, expressieResultaat.aantalElementen());
    }

    @Test
    public void testHeeftGeenOuderschapOpPersoonView() {
        final Expressie expressieResultaat =
            BRPExpressies.evalueer(
                ER_ZIJN_OUDER_BETROKKENHEDEN_EXPRESSIE,
                    persoonZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

    @Test
    public void testHeeftGeenOuderschapOpPersoonHisVolledig() {
        final Expressie expressieResultaat =
            BRPExpressies.evalueer(
                ER_ZIJN_OUDER_BETROKKENHEDEN_EXPRESSIE,
                    persoonVolledigZonderOuderBetrokkenheden);

        assertFalse(expressieResultaat.isFout());
        assertFalse(expressieResultaat.alsBoolean());
    }

}
