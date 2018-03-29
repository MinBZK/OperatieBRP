/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link OperatorType#GELIJK} vergelijker voor {@link ExpressieType#DATUM} en {@link ExpressieType#DATUMTIJD} expressies.
 */
@Component
@VergelijkerConfig(operator = OperatorType.GELIJK, typeSupport = {ExpressieType.DATUM, ExpressieType.DATUMTIJD})
final class DatumGelijkVergelijker implements Vergelijker<DatumLiteral, DatumLiteral> {

    @Override
    public Expressie apply(final DatumLiteral l, final DatumLiteral r) {
        return new DatumVergelijkHelper(l, r).bepaalGelijk();
    }
}
