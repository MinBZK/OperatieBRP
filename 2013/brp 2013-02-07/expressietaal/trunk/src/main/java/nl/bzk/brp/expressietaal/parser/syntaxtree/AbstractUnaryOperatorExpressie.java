/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;

/**
 * Representeert expressies van unaire operatoren.
 */
public abstract class AbstractUnaryOperatorExpressie extends AbstractNonLiteralExpressie {

    private final Expressie term;

    /**
     * Constructor.
     *
     * @param term Term van de operator.
     */
    AbstractUnaryOperatorExpressie(final Expressie term) {
        this.term = term;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        return "(" + operatorAsString() + " " + term.alsLeesbareString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return "(" + operatorAsFormalString() + term.alsFormeleString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        Expressie rexp = term.optimaliseer();
        return bereken(rexp);
    }

    @Override
    public final boolean isVariabele() {
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
    public final ExpressieType getTypeElementen() {
        return ExpressieType.UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        EvaluatieResultaat eTerm = term.evalueer(context);
        if (eTerm.succes()) {
            return new EvaluatieResultaat(bereken(eTerm.getExpressie()));
        } else {
            return eTerm;
        }
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        return term.includes(attribuut);
    }

    /**
     * Geef de operator als leesbare string.
     *
     * @return De stringrepresentatie.
     */
    protected abstract String operatorAsString();

    /**
     * Geef de operator als formele string.
     *
     * @return De stringrepresentatie.
     */
    protected abstract String operatorAsFormalString();

    /**
     * Probeer de expressie volledig te berekenen, gegeven twee gereduceerde termen.
     *
     * @param gereduceerdeTerm Gereduceerde term.
     * @return De resultaat van de berekening.
     */
    protected abstract Expressie bereken(final Expressie gereduceerdeTerm);

    /**
     * Optimaliseer de expressie indien mogelijk, gegeven twee gereduceerde termen. Hierbij wordt gebruik gemaakt
     * van eigenschappen van operatoren, zoals X+0 == X en Y AND TRUE == Y.
     *
     * @param gereduceerdeTerm Gereduceerde term.
     * @return De resultaat van de berekening.
     */
    protected abstract Expressie optimaliseer(Expressie gereduceerdeTerm);

    /**
     * Creëer een nieuwe expressie voor deze operator met de gegeven terme.
     *
     * @param operatorTerm Linkerterm voor de operator.
     * @return De operatorexpressie.
     */
    protected abstract Expressie create(Expressie operatorTerm);
}
