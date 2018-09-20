/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.AbstractFunctieExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ListExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.RootObjectExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Representeert een functie die een lijst van via een relatie betrokken personen teruggeeft.
 */
public abstract class AbstractBetrokkenhedenFunctie extends AbstractFunctieExpressie {

    /**
     * Constructor. Creeer functie-expressie voor functie KINDEREN(P).
     *
     * @param functie    Naam (keyword) van de functie.
     * @param argumenten Argumenten voor de functie.
     */
    protected AbstractBetrokkenhedenFunctie(final Keywords functie, final List<Expressie> argumenten) {
        super(functie, argumenten, ExpressieType.LIST);
    }

    @Override
    public ExpressieType getTypeElementen() {
        return ExpressieType.PERSOON;
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
                        if (b.getRol() == getBetrokkenheidPersoon()) {
                            Relatie r = b.getRelatie();
                            if (getSoortRelatie() == null || r.getSoort() == getSoortRelatie()) {
                                for (Betrokkenheid b2 : r.getBetrokkenheden()) {
                                    //TODO: gebruik equals() als die gegenereerd wordt.
                                    if (persoon != b2.getPersoon()
                                            && b2.getRol() == getBetrokkenheidGerelateerdPersoon())
                                    {
                                        values.add(new RootObjectExpressie(b2.getPersoon(), ExpressieType.PERSOON));
                                    }
                                }
                            }
                        }
                    }
                }

                return new ListExpressie(values);
            }
        }
        return null;
    }

    /**
     * Geeft het soort relatie waarover de functie gaat.
     *
     * @return Relevante soort relatie.
     */
    protected abstract SoortRelatie getSoortRelatie();

    /**
     * Geeft de rol van de persoon voor wie de functie wordt uitgevoerd.
     *
     * @return Rol van persoon.
     */
    protected abstract SoortBetrokkenheid getBetrokkenheidPersoon();

    /**
     * Geeft de rol van de gerelateerde persoon.
     *
     * @return Rol van de gerelateerde.
     */
    protected abstract SoortBetrokkenheid getBetrokkenheidGerelateerdPersoon();
}
