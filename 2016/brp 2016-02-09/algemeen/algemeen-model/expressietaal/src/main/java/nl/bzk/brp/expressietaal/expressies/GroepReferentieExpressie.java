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
import nl.bzk.brp.expressietaal.symbols.ExpressieGroep;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een expressie die verwijst naar een BRP-groep. Evaluatie leidt tot een referentie naar een BRP-groep.
 */
public class GroepReferentieExpressie extends AbstractNonLiteralExpressie {

    private final String             object;
    private final ExpressieGroep     expressieGroep;

    /**
     * Constructor. Creëert een attribuutverwijzing voor een gegeven object.
     *
     * @param aObject             De naam van het object.
     * @param aExpressieGroep Het attribuutpad van de benoemde groep.
     */
    public GroepReferentieExpressie(final String aObject, final ExpressieGroep aExpressieGroep)
    {
        super();
        if (aObject != null) {
            this.object = aObject;
        } else {
            this.object = "unknown_object";
        }
        this.expressieGroep = aExpressieGroep;
    }

    public final String getObject() {
        return this.object;
    }

    public final ExpressieGroep getExpressieGroep() {
        return this.expressieGroep;
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.GROEP;
    }

    @Override
    public final Expressie evalueer(final Context context) {
        final Expressie result;
        if (context != null && expressieGroep != null) {
            final Expressie value = context.bepaalGroep(object, expressieGroep);
            if (value != null) {
                result = value;
            } else {
                result = new FoutExpressie(EvaluatieFoutCode.GROEP_NIET_GEVONDEN, stringRepresentatie());
            }
        } else {
            result = new FoutExpressie(EvaluatieFoutCode.GROEP_NIET_GEVONDEN, stringRepresentatie());
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
    public final Groep getGroep() {
        return null;
    }

    @Override
    public final ExpressieType bepaalTypeVanElementen(final Context context) {
        final ExpressieType type;
        if (expressieGroep != null && expressieGroep.isLijst()) {
            type = expressieGroep.getType();
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
    public Attribuut getAttribuut() {
        return null;
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
        if (expressieGroep == null) {
            string = NullValue.getInstance().toString();
        } else {
            string = String.format("%c%s.%s", Characters.REFERENCE, object, expressieGroep.getSyntax());
        }
        return string;
    }
}
