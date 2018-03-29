/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.berekening;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link DatumLiteral} min {@link GetalLiteral} berekening.
 */
@Component
@BerekeningConfig(operator = OperatorType.MIN, typeLinks = ExpressieType.GETAL, typeRechts = ExpressieType.GETAL)
final class GetalMinGetal implements Berekening<GetalLiteral, GetalLiteral> {

    @Override
    public Expressie apply(final GetalLiteral getalLiteral, final GetalLiteral getalLiteral2) {
        return new GetalLiteral(getalLiteral.getWaarde() - getalLiteral2.getWaarde());
    }
}
