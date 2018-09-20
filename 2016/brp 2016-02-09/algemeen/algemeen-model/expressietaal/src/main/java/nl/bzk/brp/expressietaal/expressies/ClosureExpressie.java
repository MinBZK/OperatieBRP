/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import java.util.Iterator;
import nl.bzk.brp.expressietaal.AbstractExpressie;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Een expressie die een of meer tijdelijke variabelen definieert.
 */
public final class ClosureExpressie extends AbstractExpressie {

    private final Expressie expressie;
    private final Context   closure;

    /**
     * Constructor.
     *
     * @param aExpressie Expressie waarbinnen de variabelen bekend zijn.
     * @param aClosure   Een afbeelding van nul of meer variabelen op een waarde.
     */
    public ClosureExpressie(final Expressie aExpressie, final Context aClosure)
    {
        super();
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

        final Iterator<String> identifiers = closure.identifiers().iterator();
        Expressie gevondenFout = null;
        while (identifiers.hasNext() && gevondenFout == null) {
            final String id = identifiers.next();
            // Bereken de waarde van de variabele id. NB. De berekening wordt uitgevoerd met de context die als
            // argument is meegegeven. Dat betekent dat geintroduceerde variabelen elkaar niet 'kennen'.
            final Expressie eval = closure.zoekWaarde(id).evalueer(context);
            if (eval.isFout()) {
                gevondenFout = eval;
            } else {
                newContext.definieer(id, eval);
            }
        }
        final Expressie resultaat;
        if (gevondenFout == null) {
            // De expressie wordt uitgerekend met behulp van de uitgerekende waarden van variabelen.
            resultaat = expressie.evalueer(newContext);
        } else {
            resultaat = gevondenFout;
        }
        return resultaat;
    }

    @Override
    public int getPrioriteit() {
        return PRIORITEIT_WAARBIJ;
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
    public boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public int alsInteger() {
        // Default integer-waarde voor een expressie is 0.
        return 0;
    }

    @Override
    public long alsLong() {
        return 0L;
    }

    @Override
    public String alsString() {
        return stringRepresentatie();
    }

    @Override
    public Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    public ExpressieType bepaalTypeVanElementen(final Context context) {
        return expressie.bepaalTypeVanElementen(context);
    }

    @Override
    public int aantalElementen() {
        return expressie.aantalElementen();
    }

    @Override
    public Iterable<? extends Expressie> getElementen() {
        return expressie.getElementen();
    }

    @Override
    public Expressie getElement(final int index) {
        return getElementUitNietLijstExpressie(index);
    }

    @Override
    public boolean bevatOngebondenVariabele(final String id) {
        // Bepaal alle variabelen die door deze closure geintroduceerd worden.
        final Iterator<String> identifiers = closure.identifiers().iterator();
        boolean result = false;

        while (identifiers.hasNext() && !result) {
            final String closureVariabele = identifiers.next();
            // Controleer of de variabele voorkomt in de expressies in de closure.
            // bevatOngebondenVariabele("x") is voor "(y waarbij y=x)" TRUE.
            final Expressie waarde = closure.zoekWaarde(closureVariabele);
            result = waarde.bevatOngebondenVariabele(id);
        }

        // Als 1) de variabele ID niet voorkomt in de expressies in de closure
        // en 2) een variabele uit de closure de naam heeft van de gezochte variabele (id) en in feite dus opnieuw
        // gedefinieerd wordt, is het antwoord FALSE.
        if (!result) {
            boolean opnieuwGedefinieerd = false;
            while (identifiers.hasNext() && !opnieuwGedefinieerd) {
                final String closureVariabele = identifiers.next();
                opnieuwGedefinieerd = closureVariabele.equals(id);
            }

            if (opnieuwGedefinieerd) {
                result = false;
            } else {
                // Als de variabele niet gedefinieerd wordt, controleer dan of hij voorkomt in de expressie.
                result = expressie.bevatOngebondenVariabele(id);
            }

        }
        return result;
    }

    @Override
    public Expressie optimaliseer(final Context context) {
        final Context closureGeoptimaliseerd = new Context(context);
        Iterator<String> identifiers = closure.identifiers().iterator();

        // Controleer of alle aan variabelen toegekende waarden constant zijn.
        boolean allemaalConstant = true;
        // Tel de feitelijk geintroduceerde en ook in de expressie gebruikte variabelen.
        int gebruikteVariabelen = 0;

        while (identifiers.hasNext()) {
            final String id = identifiers.next();
            // Bepaal de geoptimaliseerde expressie voor de waarde van variabele id.
            final Expressie exp = closure.zoekWaarde(id).optimaliseer(context);

            // Als de resulterende expressie niet een constante is, zijn dus niet alle waarden constant.
            if (!exp.isConstanteWaarde()) {
                allemaalConstant = false;
            }

            // Controleer of de variabele id voorkomt in de omsloten expressie en niet wordt geoptimaliseerd tot een
            // constante waarde.
            if (expressie.bevatOngebondenVariabele(id)) {
                // Als de variabele voorkomt, voeg hem dan toe aan de geoptimaliseerde closure. Variabelen die niet
                // voorkomen hoeven ook niet meegenomen te worden.
                closureGeoptimaliseerd.definieer(id, exp);
                gebruikteVariabelen++;
            }
        }
        final Expressie resultaat;
        if (allemaalConstant || gebruikteVariabelen == 0) {
            // Als alle waarden constant zijn (en dus direct ingevuld kunnen worden in de expressie) of als geen van
            // de gedefinieerde variabelen ook daadwerkelijk wordt gebruikt, vergeet dan de closure en geef gewoon
            // de geoptimaliseerde expressie terug.
            resultaat = expressie.optimaliseer(closureGeoptimaliseerd);
        } else {
            // In de andere gevallen maak een nieuwe closure die alleen daadwerkelijk gebruikte variabelen bevat en
            // waarvan de waarden ook geoptimaliseerd zijn.
            final Expressie geoptimaliseerdeExpressie = expressie.optimaliseer(closureGeoptimaliseerd);

            // Het kan voorkomen dat variabelen in de expressie zijn vervangen door een constante waarde en dat ze
            // na de vorige optimalisatieslag niet meer voorkomen.
            identifiers = closureGeoptimaliseerd.identifiers().iterator();
            final Context closureGeoptimaliseerdTweedeFase = new Context(context);
            while (identifiers.hasNext()) {
                final String id = identifiers.next();
                // Bepaal de geoptimaliseerde expressie voor de waarde van variabele id.
                final Expressie exp = closureGeoptimaliseerd.zoekWaarde(id);
                // Controleer of de variabele id voorkomt in de omsloten expressie en niet wordt geoptimaliseerd tot een
                // constante waarde.
                if (geoptimaliseerdeExpressie.bevatOngebondenVariabele(id)) {
                    // Als de variabele voorkomt, voeg hem dan toe aan de geoptimaliseerde closure. Variabelen die niet
                    // voorkomen hoeven ook niet meegenomen te worden.
                    closureGeoptimaliseerdTweedeFase.definieer(id, exp);
                }
            }
            resultaat = new ClosureExpressie(geoptimaliseerdeExpressie, closureGeoptimaliseerdTweedeFase);
        }
        return resultaat;
    }

    @Override
    protected String stringRepresentatie() {
        final StringBuilder bld = new StringBuilder();
        bld.append('(');
        bld.append(expressie.toString());
        bld.append(") ");
        bld.append(DefaultKeywordMapping.getSyntax(Keyword.WAARBIJ));
        bld.append(' ');
        boolean first = true;
        final Iterator<String> identifiers = closure.identifiers().iterator();
        while (identifiers.hasNext()) {
            final String id = identifiers.next();
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
