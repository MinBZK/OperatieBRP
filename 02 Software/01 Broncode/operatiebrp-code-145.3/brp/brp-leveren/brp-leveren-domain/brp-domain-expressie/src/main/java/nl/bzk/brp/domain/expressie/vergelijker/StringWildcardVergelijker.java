/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.StringLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link OperatorType#WILDCARD} vergelijker voor {@link ExpressieType#STRING} expressies.
 */
@Component
@VergelijkerConfig(operator = OperatorType.WILDCARD, typeSupport = ExpressieType.STRING)
final class StringWildcardVergelijker implements Vergelijker<StringLiteral, StringLiteral> {

    @Override
    public Expressie apply(final StringLiteral l, final StringLiteral r) {
        final boolean resultaat = l.alsString().matches(r.getStringWaardeAlsReguliereExpressie());
        return BooleanLiteral.valueOf(resultaat);
    }
}
