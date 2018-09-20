/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NumberLiteralExpressie;
import nl.bzk.brp.model.RootObject;

/**
 * Default implementatie van interface AttributeSolver.
 */
public class DefaultSolver implements AttributeSolver {
    @Override
    public EvaluatieResultaat solve(final RootObject rootObject, final Attributes attribute) {
        Expressie resultExpressie = attribute.getAttribuutWaarde(rootObject);
        if (resultExpressie != null) {
            return new EvaluatieResultaat(resultExpressie);
        } else {
            return new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribute.getSyntax());
        }
    }

    @Override
    public EvaluatieResultaat solve(final RootObject rootObject, final Attributes attribute, final int index) {
        EvaluatieResultaat result;
        if (index < 1 || index > attribute.getMaxIndex(rootObject)) {
            result = new EvaluatieResultaat(EvaluatieFoutCode.INDEX_BUITEN_BEREIK, String.valueOf(index));
        } else {
            Expressie resultExpressie = attribute.getAttribuutWaarde(rootObject, index);
            if (resultExpressie != null) {
                result = new EvaluatieResultaat(resultExpressie);
            } else {
                result = new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribute.getSyntax());
            }
        }

        return result;
    }

    @Override
    public EvaluatieResultaat getMaxIndex(final RootObject rootObject, final Attributes attribute) {
        if (attribute.getType() == ExpressieType.INDEXED) {
            return new EvaluatieResultaat(new NumberLiteralExpressie(attribute.getMaxIndex(rootObject)));
        } else {
            return new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_IS_GEEN_INDEX, attribute.getSyntax());
        }
    }
}
