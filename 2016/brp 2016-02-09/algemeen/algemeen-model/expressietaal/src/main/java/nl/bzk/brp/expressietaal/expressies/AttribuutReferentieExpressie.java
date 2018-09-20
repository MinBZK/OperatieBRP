/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import nl.bzk.brp.expressietaal.Characters;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een expressie die verwijst naar een BRP-attribuut. Evaluatie leidt tot een referentie naar een BRP-attribuut (en niet tot een waarde,
 * zoals bij AttribuutExpressie).
 */
public class AttribuutReferentieExpressie extends AbstractNonLiteralExpressie {

    private final String             object;
    private final ExpressieAttribuut expressieAttribuut;

    /**
     * Constructor. Creëert een attribuutverwijzing voor een gegeven object.
     *
     * @param aObject             De naam van het object.
     * @param aExpressieAttribuut Het attribuutpad van het benoemde attribuut.
     */
    public AttribuutReferentieExpressie(final String aObject, final ExpressieAttribuut aExpressieAttribuut)
    {
        super();
        if (aObject != null) {
            this.object = aObject;
        } else {
            this.object = "unknown_object";
        }
        this.expressieAttribuut = aExpressieAttribuut;
    }

    public final String getObject() {
        return this.object;
    }

    public final ExpressieAttribuut getExpressieAttribuut() {
        return this.expressieAttribuut;
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.ATTRIBUUT;
    }

    @Override
    public final Expressie evalueer(final Context context) {
        final Expressie result;
        if (context != null && expressieAttribuut != null) {
            final Expressie value = context.bepaalAttribuut(object, expressieAttribuut);
            if (value != null) {
                result = value;
            } else {
                result = new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, stringRepresentatie());
            }
        } else {
            result = new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, stringRepresentatie());
        }
        return result;
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_LITERAL;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    @Override
    public final Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    public final ExpressieType bepaalTypeVanElementen(final Context context) {
        final ExpressieType type;
        if (expressieAttribuut != null && expressieAttribuut.isLijst()) {
            type = expressieAttribuut.getType();
        } else {
            type = getType(context);
        }
        return type;
    }

    @Override
    public final int aantalElementen() {
        return 1;
    }

    @Override
    public final boolean bevatOngebondenVariabele(final String id) {
        return object.equalsIgnoreCase(id);
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        return this;
    }

    @Override
    protected final String stringRepresentatie() {
        final String string;
        if (expressieAttribuut == null) {
            string = NullValue.getInstance().toString();
        } else {
            string = String.format("%c%s.%s", Characters.REFERENCE, object, expressieAttribuut.getSyntax());
        }
        return string;
    }
}
