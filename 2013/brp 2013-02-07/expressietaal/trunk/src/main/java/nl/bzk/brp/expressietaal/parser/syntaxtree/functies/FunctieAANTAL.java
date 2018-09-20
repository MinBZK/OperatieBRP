/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.AbstractFunctieExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ListExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NumberLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.UnqualifiedIndexedAttribuutExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Representeert de functie AANTAL(A). De functie geeft het aantal elementen van geïndiceerd attribuut A terug.
 */
public class FunctieAANTAL extends AbstractFunctieExpressie {
    /**
     * Constructor. Creeer functie-expressie voor functie AANTAL().
     *
     * @param argumenten Argumenten van de aanroep.
     */
    public FunctieAANTAL(final List<Expressie> argumenten) {
        super(Keywords.AANTAL, argumenten, ExpressieType.NUMBER);
    }

    @Override
    protected final Expressie create(final List<Expressie> argumentenAanroep) {
        return new FunctieAANTAL(argumentenAanroep);
    }

    @Override
    protected final Expressie calculate(final List<Expressie> gereduceerdeArgumenten, final Context context) {
        Expressie result = null;
        if (gereduceerdeArgumenten.size() == 1) {
            if (gereduceerdeArgumenten.get(0).getType() == ExpressieType.INDEXED && context != null) {
                UnqualifiedIndexedAttribuutExpressie uiae = (UnqualifiedIndexedAttribuutExpressie)
                        gereduceerdeArgumenten.get(0);

                if (uiae != null) {
                    EvaluatieResultaat evaluatie = context.getMaxIndex(uiae.getObject(), uiae.getIndexedAttribuut());
                    if (evaluatie.succes()) {
                        result = evaluatie.getExpressie();
                    }
                }
            } else if (gereduceerdeArgumenten.get(0).getType() == ExpressieType.LIST) {
                result = new NumberLiteralExpressie(
                        ((ListExpressie) gereduceerdeArgumenten.get(0)).getElements().size());
            }
        }
        return result;
    }
}
