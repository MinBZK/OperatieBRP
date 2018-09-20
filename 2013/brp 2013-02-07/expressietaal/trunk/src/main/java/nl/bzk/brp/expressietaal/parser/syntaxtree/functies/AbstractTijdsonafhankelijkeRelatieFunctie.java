/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ListExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.RootObjectExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Representeert een functie die een lijst van een gespecificeerde soort tijdsonafhankelijke relaties van een persoon
 * teruggeeft. Voorbeeld van een dergelijke relatie is Familierechtelijke Betrekking.
 */

public abstract class AbstractTijdsonafhankelijkeRelatieFunctie extends AbstractRelatieFunctie {

    /**
     * Constructor. Creeer functie-expressie voor functie KINDEREN(P).
     *
     * @param functie       Naam (keyword) van de functie.
     * @param typeElementen Type van de elementen in de lijst die de functie oplevert.
     * @param argumenten    Argumenten voor de functie.
     */
    protected AbstractTijdsonafhankelijkeRelatieFunctie(final Keywords functie, final ExpressieType typeElementen,
                                                        final List<Expressie> argumenten)
    {
        super(functie, typeElementen, argumenten);
    }

    @Override
    protected Expressie calculate(final List<Expressie> gereduceerdeArgumenten, final Context context) {
        if (gereduceerdeArgumenten.size() == 1) {
            EvaluatieResultaat argument = gereduceerdeArgumenten.get(0).evalueer(context);
            if (argument.succes() && argument.getExpressie().getType() == ExpressieType.PERSOON) {
                Persoon persoon = (Persoon) ((RootObjectExpressie) argument.getExpressie()).getRootObject();
                List<Expressie> values = new ArrayList<Expressie>();
                if (persoon.getBetrokkenheden() != null) {
                    for (Betrokkenheid b : persoon.getBetrokkenheden()) {
                        Relatie r = b.getRelatie();
                        if (getSoortRelatie() == null || r.getSoort() == getSoortRelatie()) {
                            values.add(new RootObjectExpressie(r, getTypeElementen()));
                        }
                    }
                }
                return new ListExpressie(values);
            }
        }
        return null;
    }

}
