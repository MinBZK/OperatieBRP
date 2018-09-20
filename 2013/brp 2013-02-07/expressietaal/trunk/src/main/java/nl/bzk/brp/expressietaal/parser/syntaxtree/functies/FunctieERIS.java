/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.AbstractFunctieExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ListExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.RootObjectExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.VariableExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.model.RootObject;

/**
 * Representeert de functie ER_IS(L,V,C). De functie geeft true terug als minstens één element uit lijst L voldoet aan
 * conditie C, waarbij variabele V steeds de waarde van een element uit L aanneemt.
 */
public class FunctieERIS extends AbstractFunctieExpressie {

    /**
     * Het aantal argumenten van de functie ER_IS().
     */
    public static final int AANTAL_ARGUMENTEN = 3;

    /**
     * Constructor. Creeer functie-expressie voor functie ER_IS().
     *
     * @param argumenten Argumenten van de aanroep.
     */
    public FunctieERIS(final List<Expressie> argumenten) {
        super(Keywords.ER_IS, argumenten, ExpressieType.BOOLEAN);
    }

    @Override
    protected Expressie create(final List<Expressie> argumentenAanroep) {
        return new FunctieERIS(argumentenAanroep);
    }

    @Override
    protected Expressie calculate(final List<Expressie> gereduceerdeArgumenten, final Context context) {
        Expressie result = null;
        EvaluatieResultaat lijstResultaat = gereduceerdeArgumenten.get(0).evalueer(context);
        Expressie variabeleArgument = gereduceerdeArgumenten.get(1);
        Expressie conditie = gereduceerdeArgumenten.get(2);

        if (lijstResultaat.getFout() == null && lijstResultaat.getExpressie().isLijstExpressie()
                && variabeleArgument.isVariabele())
        {
            String variabele = ((VariableExpressie) variabeleArgument).getIdentifier();
            ListExpressie values = (ListExpressie) lijstResultaat.getExpressie();

            boolean booleanResult = false;
            Context newContext = new Context(context);

            for (Expressie value : values.getElements()) {
                if (value.isRootObject()) {
                    RootObject rootObject = ((RootObjectExpressie) value).getRootObject();
                    newContext.put(variabele, rootObject);

                    EvaluatieResultaat valueCondition = conditie.evalueer(newContext);
                    if (valueCondition.isBooleanWaarde()) {
                        booleanResult = valueCondition.getBooleanWaarde();
                    }
                }
                if (booleanResult) {
                    break;
                }
            }
            result = new BooleanLiteralExpressie(booleanResult);
        }

        return result;
    }

    @Override
    protected boolean evalueerArgumenten() {
        return false;
    }
}
