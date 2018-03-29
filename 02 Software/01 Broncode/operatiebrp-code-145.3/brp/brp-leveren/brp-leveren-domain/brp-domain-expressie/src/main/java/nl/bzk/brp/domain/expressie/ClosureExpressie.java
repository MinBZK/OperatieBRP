/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;


/**
 * Een expressie die een of meer tijdelijke variabelen definieert.
 */
public final class ClosureExpressie implements Expressie {

    private final Expressie expressie;
    private final Context closure;

    /**
     * Constructor.
     *
     * @param aExpressie Expressie waarbinnen de variabelen bekend zijn.
     * @param aClosure   Een afbeelding van nul of meer variabelen op een waarde.
     */
    public ClosureExpressie(final Expressie aExpressie, final Context aClosure) {
        this.expressie = aExpressie;
        this.closure = aClosure;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return expressie.getType(context);
    }

    @Override
    public Expressie evalueer(final Context context) {
        // De nieuwe context bevat alle geevalueerde waarden van geintroduceerde variabelen.
        final Context newContext = new Context(context);
        for (final String id : closure.identifiers()) {
            // Bereken de waarde van de variabele id. NB. De berekening wordt uitgevoerd met de context die als
            // argument is meegegeven. Dat betekent dat geintroduceerde variabelen elkaar niet 'kennen'.
            final Expressie eval = closure.zoekWaarde(id).evalueer(context);
            newContext.definieer(id, eval);
        }
        // De expressie wordt uitgerekend met behulp van de uitgerekende waarden van variabelen.
        return expressie.evalueer(newContext);
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_WAARBIJ;
    }

    @Override
    public boolean isConstanteWaarde() {
        return expressie.isConstanteWaarde();
    }

    @Override
    public boolean isVariabele() {
        return expressie.isVariabele();
    }

    @Override
    public String alsString() {
        return toString();
    }

    @Override
    public String toString() {
        final StringBuilder bld = new StringBuilder();
        bld.append('(');
        bld.append(expressie.toString());
        bld.append(") ");
        bld.append("WAARBIJ");
        bld.append(' ');
        boolean first = true;
        for (final String id : closure.identifiers()) {
            if (!first) {
                bld.append(", ");
            }
            bld.append(id);
            bld.append(" = ");
            bld.append(closure.zoekWaarde(id).toString());
            first = false;
        }
        return bld.toString();
    }
}
