/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.AbstractFunctieExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;

/**
 * Representeert een functie die een lijst van een gespecificeerde soort relaties van een persoon teruggeeft.
 */
public abstract class AbstractRelatieFunctie extends AbstractFunctieExpressie {

    private final ExpressieType typeElementen;

    /**
     * Constructor. Creeer functie-expressie voor functie KINDEREN(P).
     *
     * @param functie       Naam (keyword) van de functie.
     * @param typeElementen Type van de elementen in de lijst die de functie oplevert.
     * @param argumenten    Argumenten voor de functie.
     */
    protected AbstractRelatieFunctie(final Keywords functie, final ExpressieType typeElementen,
                                     final List<Expressie> argumenten)
    {
        super(functie, argumenten, ExpressieType.LIST);
        this.typeElementen = typeElementen;
    }

    @Override
    public ExpressieType getTypeElementen() {
        return typeElementen;
    }

    /**
     * Geeft het soort relatie waarover de functie gaat.
     *
     * @return Relevante soort relatie.
     */
    protected abstract SoortRelatie getSoortRelatie();
}
