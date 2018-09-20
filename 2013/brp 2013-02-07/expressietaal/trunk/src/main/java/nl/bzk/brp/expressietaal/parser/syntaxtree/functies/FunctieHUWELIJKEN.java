/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.Relatie;
import org.joda.time.DateTime;

/**
 * Representeert de functie HUWELIJKEN(P). De functie geeft de huwelijken van persoon P in een lijst terug.
 */
public class FunctieHUWELIJKEN extends AbstractTijdsafhankelijkeRelatieFunctie {

    /**
     * Constructor. Creeer functie-expressie voor functie HUWELIJKEN(P).
     *
     * @param argumenten Argumenten voor de functie.
     */
    protected FunctieHUWELIJKEN(final List<Expressie> argumenten) {
        super(Keywords.HUWELIJKEN, ExpressieType.HUWELIJK, argumenten);
    }

    @Override
    protected SoortRelatie getSoortRelatie() {
        return SoortRelatie.HUWELIJK;
    }

    @Override
    protected Expressie create(final List<Expressie> argumentenAanroep) {
        return new FunctieHUWELIJKEN(argumentenAanroep);
    }

    @Override
    protected boolean datumMatch(final Relatie relatie, final DateTime datum) {
        return true;
    }
}
