/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link OperatorType#KLEINER_OF_GELIJK} vergelijker voor {@link ExpressieType#LIJST} expressies.
 */
@Component
@VergelijkerConfig(operator = OperatorType.KLEINER_OF_GELIJK, typeSupport = ExpressieType.LIJST)
final class LijstKleinerOfGelijkVergelijker implements Vergelijker<LijstExpressie, LijstExpressie> {

    @Override
    public Expressie apply(final LijstExpressie l, final LijstExpressie r) {
        return BooleanLiteral.valueOf(l.size() <= r.size());
    }
}
