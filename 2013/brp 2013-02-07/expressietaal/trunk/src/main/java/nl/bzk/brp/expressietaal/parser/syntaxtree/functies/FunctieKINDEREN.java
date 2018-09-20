/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;

/**
 * Representeert de functie KINDEREN(P). De functie geeft de kinderen van persoon P in een lijst terug.
 */
public class FunctieKINDEREN extends AbstractBetrokkenhedenFunctie {

    /**
     * Constructor. Creeer functie-expressie voor functie KINDEREN(P).
     *
     * @param argumenten Argumenten voor de functie.
     */
    public FunctieKINDEREN(final List<Expressie> argumenten) {
        super(Keywords.KINDEREN, argumenten);
    }

    @Override
    protected Expressie create(final List<Expressie> argumentenAanroep) {
        return new FunctieKINDEREN(argumentenAanroep);
    }

    @Override
    protected SoortRelatie getSoortRelatie() {
        return SoortRelatie.FAMILIERECHTELIJKE_BETREKKING;
    }

    @Override
    protected SoortBetrokkenheid getBetrokkenheidPersoon() {
        return SoortBetrokkenheid.OUDER;
    }

    @Override
    protected SoortBetrokkenheid getBetrokkenheidGerelateerdPersoon() {
        return SoortBetrokkenheid.KIND;
    }
}
