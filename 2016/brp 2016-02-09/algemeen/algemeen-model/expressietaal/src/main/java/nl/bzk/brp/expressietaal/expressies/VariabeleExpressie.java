/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een variabele (in een expressie).
 */
public class VariabeleExpressie extends AbstractNonLiteralExpressie {

    private final String identifier;

    /**
     * Constructor.
     *
     * @param aIdentifier Naam van de variabele.
     */
    public VariabeleExpressie(final String aIdentifier) {
        super();
        if (aIdentifier != null) {
            this.identifier = aIdentifier;
        } else {
            this.identifier = "unknown_identifier";
        }
    }

    public final String getIdentifier() {
        return identifier;
    }

    @Override
    public final ExpressieType getType(final Context context) {
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (context != null) {
            final Expressie waarde = context.zoekWaarde(identifier);
            if (waarde != null) {
                type = waarde.getType(context);
            }
        }
        return type;
    }

    @Override
    public final Expressie evalueer(final Context context) {
        Expressie waarde = context.zoekWaarde(identifier);
        if (waarde == null) {
            waarde = new FoutExpressie(EvaluatieFoutCode.VARIABELE_NIET_GEVONDEN, identifier);
        }
        return waarde;
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_LITERAL;
    }

    @Override
    public final boolean isVariabele() {
        return true;
    }

    @Override
    public final Attribuut getAttribuut() {
        return null;
    }

    @Override
    public final Groep getGroep() {
        return null;
    }

    @Override
    public final ExpressieType bepaalTypeVanElementen(final Context context) {
        return getType(context);
    }

    @Override
    public final int aantalElementen() {
        return 1;
    }

    @Override
    public final boolean bevatOngebondenVariabele(final String id) {
        return getIdentifier().equalsIgnoreCase(id);
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        final Expressie expressie;
        if (context != null) {
            final Expressie waarde = context.zoekWaarde(identifier);
            if (waarde != null && waarde.isConstanteWaarde()) {
                expressie = waarde.optimaliseer(context);
            } else {
                expressie = this;
            }
        } else {
            expressie = this;
        }
        return expressie;
    }

    @Override
    protected final String stringRepresentatie() {
        return identifier;
    }
}
