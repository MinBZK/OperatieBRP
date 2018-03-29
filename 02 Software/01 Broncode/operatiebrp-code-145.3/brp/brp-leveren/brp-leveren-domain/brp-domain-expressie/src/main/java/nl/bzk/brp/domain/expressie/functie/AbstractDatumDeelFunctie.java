/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;

/**
 * Abstractie voor het berekenen van een datumdeel.
 */
abstract class AbstractDatumDeelFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    AbstractDatumDeelFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur(ExpressieType.LIJST),
                new SimpeleSignatuur(ExpressieType.DATUM),
                new SimpeleSignatuur(ExpressieType.DATUMTIJD)));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie expressie = argumenten.get(0);
        Expressie arg = expressie;
        if (expressie instanceof LijstExpressie) {
            final LijstExpressie lijstExpressie = (LijstExpressie) expressie;
            if (lijstExpressie.isEmpty()) {
                return NullLiteral.INSTANCE;
            }
            arg = lijstExpressie.geefEnkeleWaarde();
            assertSignatuurCorrect(Lists.newArrayList(arg), context);
        }
        Expressie result = NullLiteral.INSTANCE;
        if (!arg.isNull()) {
            final Expressie datumDeelExpressie = geefDatumDeelExpressie((DatumLiteral) arg);
            if (datumDeelExpressie != null) {
                result = datumDeelExpressie;
            }
        }
        return result;
    }

    /**
     * @param arg een D
     * @return de expressie voor het datumdeel.
     */
    abstract Expressie geefDatumDeelExpressie(final DatumLiteral arg);

    @Override
    public final ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.GETAL;
    }
}
