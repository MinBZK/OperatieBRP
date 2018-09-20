/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;

/**
 * Representeert expressies van binaire operatoren.
 */
public abstract class AbstractBinaryOperatorExpressie extends AbstractNonLiteralExpressie {

    private final Expressie termLinks;
    private final Expressie termRechts;

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    AbstractBinaryOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        this.termLinks = termLinks;
        this.termRechts = termRechts;
    }

    Expressie getTermLinks() {
        return termLinks;
    }

    Expressie getTermRechts() {
        return termRechts;
    }

    @Override
    public final String alsLeesbareString() {
        return "(" + termLinks.alsLeesbareString() + " " + operatorAsString() + " "
                + termRechts.alsLeesbareString() + ")";
    }

    @Override
    public final String alsFormeleString() {
        return "(" + termLinks.alsFormeleString() + operatorAsFormalString() + termRechts.alsFormeleString() + ")";
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    @Override
    public final boolean isAttribuut() {
        return false;
    }

    @Override
    public final boolean isLijstExpressie() {
        return false;
    }

    @Override
    public final ExpressieType getTypeElementen() {
        return ExpressieType.UNKNOWN;
    }

    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        EvaluatieResultaat result;
        EvaluatieResultaat eLinks = termLinks.evalueer(context);
        if (eLinks.succes()) {
            EvaluatieResultaat eRechts = termRechts.evalueer(context);
            if (eRechts.succes()) {
                Expressie evaluatie = bereken(eLinks.getExpressie(), eRechts.getExpressie());
                result = new EvaluatieResultaat(evaluatie);
            } else {
                result = eRechts;
            }
        } else {
            result = eLinks;
        }
        return result;
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        return termLinks.includes(attribuut) || termRechts.includes(attribuut);
    }

    /**
     * Geeft de operator als leesbare string.
     *
     * @return De stringrepresentatie.
     */
    protected abstract String operatorAsString();

    /**
     * Geeft de operator als formele string.
     *
     * @return De stringrepresentatie.
     */
    protected abstract String operatorAsFormalString();

    /**
     * Probeert de expressie volledig te berekenen.
     *
     * @param linkerTerm  Linkerterm van de operator.
     * @param rechterTerm Rechterterm van de operator.
     * @return De resultaat van de berekening.
     */
    protected abstract Expressie bereken(final Expressie linkerTerm, final Expressie rechterTerm);

    /**
     * Creëer een nieuwe expressie voor deze operator met de twee gegeven termen.
     *
     * @param operatorTermLinks  Linkerterm voor de operator.
     * @param operatorTermRechts Rechterterm voor de operator.
     * @return De operatorexpressie.
     */
    protected abstract Expressie create(Expressie operatorTermLinks, Expressie operatorTermRechts);
}
