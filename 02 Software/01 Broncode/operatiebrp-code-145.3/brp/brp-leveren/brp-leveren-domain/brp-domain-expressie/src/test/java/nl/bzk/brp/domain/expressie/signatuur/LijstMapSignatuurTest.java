/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.signatuur;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import org.junit.Test;

public class LijstMapSignatuurTest {

    private final LijstExpressie lijstExpr = new LijstExpressie(Lists.newArrayList(new GetalLiteral(1L), new GetalLiteral(2L)));
    private final VariabeleExpressie varExpr = new VariabeleExpressie("x");

    @Test
    public void test1() throws Exception {
        final LijstMapSignatuur signatuur = new LijstMapSignatuur();

        //correct
        assertTrue(signatuur.test(
                Lists.newArrayList(lijstExpr, varExpr, BooleanLiteral.WAAR),
                new Context()
        ));

        //tweede argument is geen variable
        assertFalse(signatuur.test(
                Lists.newArrayList(lijstExpr, lijstExpr, BooleanLiteral.WAAR),
                new Context()
        ));

        //geen argumenten
        assertFalse(signatuur.test(Lists.newArrayList(), new Context()));

        //null argumenten
        assertFalse(signatuur.test(null, new Context()));
    }

}