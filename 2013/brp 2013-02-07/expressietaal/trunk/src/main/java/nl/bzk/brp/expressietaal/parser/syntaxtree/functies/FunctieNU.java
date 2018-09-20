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
import nl.bzk.brp.expressietaal.symbols.Keywords;
import org.joda.time.DateTime;

/**
 * Representeert de functie NU(). De functie geeft de huidige datum (bij executie van de expressie) als resultaat.
 */
public class FunctieNU extends AbstractFunctieExpressie {

    /**
     * Constructor. Creeer functie-expressie voor functie NU().
     */
    public FunctieNU() {
        super(Keywords.NU, ExpressieType.DATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie create(final List<Expressie> argumentenAanroep) {
        return new FunctieNU();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Expressie calculate(final List<Expressie> gereduceerdeArgumenten, final Context context) {
        DateTime dt = new DateTime();
        return new DateLiteralExpressie(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
    }
}
