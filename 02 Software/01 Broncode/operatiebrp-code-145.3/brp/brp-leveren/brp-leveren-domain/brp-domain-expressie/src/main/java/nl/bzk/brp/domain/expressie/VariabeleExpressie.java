/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Representeert een variabele (in een expressie).
 */
public final class VariabeleExpressie implements NonLiteralExpressie {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final String identifier;

    /**
     * Constructor.
     *
     * @param aIdentifier Naam van de variabele.
     */
    public VariabeleExpressie(final String aIdentifier) {
        this.identifier = aIdentifier;
    }

    @Override
    public Expressie evalueer(final Context context) {
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("evalueer: {}", identifier);
        }
        final Expressie waarde = context.zoekWaarde(identifier);
        if (waarde == null) {
            throw new ExpressieRuntimeException("De expressie verwijst naar een niet-gedefinieerde variabele: " + identifier);
        }
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("gevonden waarde: {}", waarde);
        }
        return waarde;
    }

    @Override
    public ExpressieType getType(final Context context) {
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (context != null) {
            final Expressie waarde = context.zoekWaarde(identifier);
            if (waarde != null) {
                type = waarde.getType(context);
            }
        }
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_LITERAL;
    }

    @Override
    public boolean isVariabele() {
        return true;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
