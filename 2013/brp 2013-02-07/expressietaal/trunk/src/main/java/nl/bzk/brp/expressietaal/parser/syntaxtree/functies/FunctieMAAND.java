/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.AbstractFunctieExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.DateLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NumberLiteralExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Representeert de functie MAAND(D). De functie geeft het volgnummer van de maand in datum D als resultaat.
 */
public class FunctieMAAND extends AbstractFunctieExpressie {

    /**
     * Constructor. Creeer functie-expressie voor functie MAAND().
     *
     * @param argumenten Argumenten voor de functie.
     */
    public FunctieMAAND(final List<Expressie> argumenten) {
        super(Keywords.MAAND, argumenten, ExpressieType.NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie create(final List<Expressie> argumentenAanroep) {
        return new FunctieMAAND(argumentenAanroep);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie calculate(final List<Expressie> gereduceerdeArgumenten, final Context context) {
        Expressie result = null;
        if (gereduceerdeArgumenten.size() > 0) {
            Expressie arg = gereduceerdeArgumenten.get(0);
            if (arg.getType() == ExpressieType.DATE && arg.isConstanteWaarde()) {
                DateLiteralExpressie date = (DateLiteralExpressie) arg;
                result = new NumberLiteralExpressie(date.getMaand());
            } else {
                result = create(gereduceerdeArgumenten);
            }
        }
        return result;
    }
}
