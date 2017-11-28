/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie DAG(D). De functie DAG(datum) geeft het dagnummer van de datum of NULL als dat onbekend is.
 * Voorbeelden:
 * <table>
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>DAG(1980/MRT/10)</td><td>10</td></tr>
 * <tr><td>DAG(1980/MRT/?)</td><td>NULL</td></tr>
 * <tr><td>DAG(1980/MRT/0)</td><td>NULL</td></tr>
 * <tr><td>DAG(NULL)</td><td>NULL</td></tr>
 * </table>
 */
@Component
@FunctieKeyword("DAG")
final class DagFunctie extends AbstractDatumDeelFunctie {

    @Override
    Expressie geefDatumDeelExpressie(final DatumLiteral date) {
        return date.getDag().isVraagteken() ? NullLiteral.INSTANCE : new GetalLiteral(date.getDag().getWaarde());
    }
}
