/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;

/**
 * Representeert een attribuutexpressie.
 */
public class AttribuutExpressie extends AbstractNonLiteralExpressie {

    private final String object;
    private final Attributes attribuut;

    /**
     * Constructor. Creëert een attribuutverwijzing voor een gegeven object.
     *
     * @param object    De naam van het object.
     * @param attribuut Het attribuutpad van het benoemde attribuut.
     */
    public AttribuutExpressie(final String object, final Attributes attribuut) {
        this.object = object.toUpperCase();
        this.attribuut = attribuut;
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
        return true;
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
    public final String alsLeesbareString() {
        return object + "." + attribuut.getSyntax();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return String.format("$[%s<%s>.%s]", object, attribuut.getObjectType().getNaam(), attribuut.getSyntax());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return attribuut.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        EvaluatieResultaat result;
        if (context != null) {
            EvaluatieResultaat value = context.solve(object, attribuut);
            if (value != null) {
                result = value;
            } else {
                result = new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, alsLeesbareString());
            }
        } else {
            result = new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, alsLeesbareString());
        }
        return result;
    }

    @Override
    public final boolean includes(final Attributes gezochtAttribuut) {
        return this.attribuut == gezochtAttribuut;
    }
}
