/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import java.util.LinkedList;
import java.util.List;

import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Representeert functieaanroepen.
 */
public abstract class AbstractFunctieExpressie extends AbstractExpressie {

    private final Keywords functie;
    private final List<Expressie> argumenten;
    private final ExpressieType type;

    /**
     * Constructor. Maak een expressie die een functieaanroep representeert.
     *
     * @param functie    Naam van de functie.
     * @param argumenten Argumenten van de functie.
     * @param type       Type van het functieresultaat.
     */
    protected AbstractFunctieExpressie(final Keywords functie, final List<Expressie> argumenten,
                                       final ExpressieType type)
    {
        this.functie = functie;
        this.argumenten = argumenten;
        this.type = type;
    }

    /**
     * Constructor. Maak een expressie die een functieaanroep zonder argumenten representeert.
     *
     * @param functie Naam van de functie.
     * @param type    Type van het functieresultaat.
     */
    protected AbstractFunctieExpressie(final Keywords functie, final ExpressieType type) {
        this(functie, new LinkedList<Expressie>(), type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return type;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        StringBuilder result = new StringBuilder();
        result.append(DefaultKeywordMapping.getSyntax(functie));
        result.append("(");
        boolean eersteArgument = true;
        for (Expressie argument : argumenten) {
            if (!eersteArgument) {
                result.append(", ");
            }
            eersteArgument = false;
            result.append(argument.alsLeesbareString());
        }
        result.append(")");
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        StringBuilder result = new StringBuilder();
        result.append("@");
        result.append(DefaultKeywordMapping.getSyntax(functie));
        result.append("(");
        boolean eersteArgument = true;
        for (Expressie argument : argumenten) {
            if (!eersteArgument) {
                result.append(",");
            }
            eersteArgument = false;
            result.append(argument.alsFormeleString());
        }
        result.append(")");
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isConstanteWaarde() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isAttribuut() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isLijstExpressie() {
        return false;
    }

    @Override
    public ExpressieType getTypeElementen() {
        return ExpressieType.UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        List<Expressie> geoptimaliseerdeArgumenten = new LinkedList<Expressie>();
        for (Expressie argument : argumenten) {
            geoptimaliseerdeArgumenten.add(argument.optimaliseer());
        }
        return calculate(geoptimaliseerdeArgumenten, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        EvaluatieResultaat result = null;
        if (evalueerArgumenten()) {
            List<Expressie> berekendeArgumenten = new LinkedList<Expressie>();
            for (Expressie argument : argumenten) {
                EvaluatieResultaat argResult = argument.evalueer(context);
                if (argResult.succes()) {
                    berekendeArgumenten.add(argResult.getExpressie());
                } else {
                    result = argResult;
                }
            }
            if (result == null) {
                result = new EvaluatieResultaat(calculate(berekendeArgumenten, context));
            }
        } else {
            Expressie eval = calculate(argumenten, context);
            if (eval != null) {
                result = new EvaluatieResultaat(eval);
            } else {
                result = new EvaluatieResultaat(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }
        return result;
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        for (Expressie argument : argumenten) {
            if (argument.includes(attribuut)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creeer een functie-expressie met de opgegeven argumentenAanroep.
     *
     * @param argumentenAanroep Argumenten van de functieaanroep.
     * @return Functie-expressie.
     */
    @SuppressWarnings("UnusedDeclaration")
    protected abstract Expressie create(final List<Expressie> argumentenAanroep);

    /**
     * Bereken de functie op basis van de meegegeven gereduceerde argumenten (expressies).
     *
     * @param gereduceerdeArgumenten Argumenten van de functieaanroep.
     * @param context                Gedefinieerde identifiers.
     * @return Resultaat van het de functie of null, indien fout.
     */
    protected abstract Expressie calculate(final List<Expressie> gereduceerdeArgumenten, final Context context);

    /**
     * Geeft true als de argumenten van de functie geëvalueerd moeten worden, voordat de functie berekend wordt. Dit is
     * by default het geval.
     *
     * @return True als argumenten geëvalueerd moeten worden.
     */
    protected boolean evalueerArgumenten() {
        return true;
    }
}
