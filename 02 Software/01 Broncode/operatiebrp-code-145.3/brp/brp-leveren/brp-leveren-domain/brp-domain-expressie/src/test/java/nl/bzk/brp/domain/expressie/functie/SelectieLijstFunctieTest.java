/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import static nl.bzk.brp.domain.leveringmodel.helper.TestBuilders.maakPersoonMetIdentificatieNrs;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.Sets;
import java.net.URISyntaxException;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import org.junit.Before;
import org.junit.Test;

public class SelectieLijstFunctieTest {

    private static final String BSN_1 = "123456789";
    private static final String BSN_NIET_BESTAAND = "111111111";
    private static final String ANR_1 = "987654321";
    private static final String ANR_NIET_BESTAAND = "999999999";

    private Expressie selectieLijstExpressie;

    @Before
    public void voorTest() throws ExpressieException, URISyntaxException {
        selectieLijstExpressie = ExpressieParser.parse("SELECTIE_LIJST()");
    }

    @Test
    public void testBsnInSelectieLijst() throws ExpressieException, URISyntaxException {
        final Context ctx = new Context();
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        voegSelectieBestandToeAanCtx(ctx, BSN_1, Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.WAAR, resultaat);
    }

    @Test
    public void testBsnAlsAnrInSelectieLijst() throws ExpressieException, URISyntaxException {
        final Context ctx = new Context();
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        voegSelectieBestandToeAanCtx(ctx, BSN_1, Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.ONWAAR, resultaat);
    }

    @Test
    public void testBsnNietInSelectieLijst() throws ExpressieException, URISyntaxException {
        final Context ctx1 = new Context();
        final Context ctx = ctx1;
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        voegSelectieBestandToeAanCtx(ctx, BSN_NIET_BESTAAND, Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.ONWAAR, resultaat);
    }

    @Test
    public void testAnrInSelectieLijst() throws ExpressieException, URISyntaxException {
        final Context ctx = new Context();
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        voegSelectieBestandToeAanCtx(ctx, ANR_1, Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.WAAR, resultaat);
    }

    @Test
    public void testAnrNietInSelectieLijst() throws ExpressieException, URISyntaxException {
        final Context ctx1 = new Context();
        final Context ctx = ctx1;
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        voegSelectieBestandToeAanCtx(ctx, ANR_NIET_BESTAAND, Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.ONWAAR, resultaat);
    }

    @Test
    public void testGeenSelectieLijstVoorDienst() throws ExpressieException, URISyntaxException {
        final Context ctx = new Context();
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.WAAR, resultaat);
    }

    @Test
    public void testLegeSelectieLijstVoorDienst() throws ExpressieException, URISyntaxException {
        final Context ctx = new Context();
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, maakPersoonMetIdentificatieNrs(BSN_1, ANR_1));
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_SELECTIE_LIJST, SelectieLijst.GEEN_LIJST);
        final BooleanLiteral resultaat = (BooleanLiteral) selectieLijstExpressie.evalueer(ctx);
        assertEquals(BooleanLiteral.WAAR, resultaat);
    }


    private void voegSelectieBestandToeAanCtx(Context ctx, String anrNietBestaand, final Element element) {
        final Set<String> waarden = Sets.newHashSet(anrNietBestaand);
        final SelectieLijst
                selectiebestandLijst =
                new SelectieLijst(1, ElementHelper.getAttribuutElement(element), waarden);
        ctx.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_SELECTIE_LIJST, selectiebestandLijst);
    }
}
