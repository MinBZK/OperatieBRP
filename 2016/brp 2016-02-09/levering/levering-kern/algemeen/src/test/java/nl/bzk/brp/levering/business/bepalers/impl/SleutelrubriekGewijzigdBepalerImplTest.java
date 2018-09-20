/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.levering.algemeen.service.impl.ToekomstigeActieServiceImpl;
import nl.bzk.brp.levering.business.bepalers.AbstractBepalerTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class SleutelrubriekGewijzigdBepalerImplTest extends AbstractBepalerTest {

    private static final String EXPRESSIE_GEWIJZIGD_GESLACHTSAANDUIDING = "GEWIJZIGD(oud, nieuw, [geslachtsaanduiding.geslachtsaanduiding])";

    private final SleutelrubriekGewijzigdBepalerImpl sleutelrubriekGewijzigdBepaler =
        new SleutelrubriekGewijzigdBepalerImpl();

    private Leveringsautorisatie la;

    @Before
    public void setUp() {
        la = TestLeveringsautorisatieBuilder.maker().metNaam("Testabo").maak();
        ReflectionTestUtils.setField(sleutelrubriekGewijzigdBepaler, "toekomstigeActieService", new ToekomstigeActieServiceImpl());
    }

    @Test
    public void testBepaalAttributenGewijzigdNieuwPersoonNietsGewijzigd() {
        final Expressie expressie = getExpressieUitTekst("GEWIJZIGD(oud, nieuw, [overlijden.datum])");
        final boolean resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndGeboorte(), expressie, la);
        assertFalse(resultaat);
    }

    @Test
    public void testBepaalAttributenGewijzigdNieuwPersoonIetsGewijzigd() {
        final Expressie expressie = getExpressieUitTekst(EXPRESSIE_GEWIJZIGD_GESLACHTSAANDUIDING);
        final boolean resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndGeboorte(), expressie, la);
        assertTrue(resultaat);
    }

    @Test
    public void testBepaalAttributenGewijzigdBestaandPersoonNietsGewijzigd() {
        final Expressie expressie = getExpressieUitTekst(EXPRESSIE_GEWIJZIGD_GESLACHTSAANDUIDING);
        final boolean resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndVerhuizing(), expressie, la);
        assertFalse(resultaat);
    }

    @Test
    public void testBepaalAttributenGewijzigdBestaandPersoonIetsGewijzigd() {
        Expressie expressie = getExpressieUitTekst("GEWIJZIGD(oud, nieuw, [bijhouding.bijhoudingspartij])");
        boolean resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndVerhuizing(), expressie, la);
        assertTrue(resultaat);

        expressie = getExpressieUitTekst("GEWIJZIGD(oud, oud, [bijhouding.bijhoudingspartij])");
        resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndVerhuizing(), expressie, la);
        assertFalse(resultaat);
    }

    @Test
    public void testBepaalAttributenGewijzigdBestaandPersoonWoonplaatsnaamGewijzigd() {
        Expressie expressie = getExpressieUitTekst("GEWIJZIGD(oud, nieuw, [adressen], [woonplaatsnaam])");
        boolean resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndVerhuizing(), expressie, la);
        assertTrue(resultaat);

        // Checken of deze expressie echt werkt via vergelijking op nieuw adres met nieuw adres, moet in geen
        // wijziging resulteren.
        expressie = getExpressieUitTekst("GEWIJZIGD(nieuw, nieuw, [adressen], [woonplaatsnaam])");
        resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndVerhuizing(), expressie, la);
        assertFalse(resultaat);
    }

    @Test
    public void testBepaalAttributenGewijzigdWaarbijEvalueertNaarNullDanMoetFalseTeruggeven() {
        final Expressie expressie = mock(Expressie.class);
        when(expressie.evalueer(any(Context.class))).thenReturn(null);

        final boolean resultaat = sleutelrubriekGewijzigdBepaler.bepaalAttributenGewijzigd(getTestPersoon(), getAdmhndGeboorte(), expressie, la);
        assertFalse(resultaat);
    }

    @Test
    public void testBepaalVorigeTijdstipRegistratie() {
        final PersoonHisVolledigImpl testPersoon = getTestPersoon();

        final DatumTijdAttribuut vorigeTijdstipRegistratie = sleutelrubriekGewijzigdBepaler.bepaalVorigeTijdstipRegistratie(testPersoon, getAdmhndVerhuizing());
        assertNotNull(vorigeTijdstipRegistratie);
        assertEquals(TSREG_1990, vorigeTijdstipRegistratie);
    }

    private Expressie getExpressieUitTekst(final String expressieTekst) {
        return BRPExpressies.parse(expressieTekst, getParserContext()).getExpressie();
    }

    private Context getParserContext() {
        final Context parserContext = new Context();
        parserContext.declareer(
                SleutelrubriekGewijzigdBepalerImpl.ATTENDERING_EXPRESSIE_OUD,
                ExpressieType.PERSOON);
        parserContext.declareer(
                SleutelrubriekGewijzigdBepalerImpl.ATTENDERING_EXPRESSIE_NIEUW,
                ExpressieType.PERSOON);
        return parserContext;
    }

}
