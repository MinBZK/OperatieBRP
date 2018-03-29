/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Basis-interface voor alle expressies. De belangrijkste kenmerken van expressies zijn dat ze 1) een type hebben en
 * 2) geëvalueerd kunnen worden.
 */
@ThreadSafe
public interface Expressie {

    /**
     * Geeft het type van de Expressie, zo precies mogelijk. Voor de meeste expressies is er een
     * intrinsiek type bekend (boolean operator, optellen, aftrekken, enzovoort), maar voor symbolische waarden
     * (variabelen en attributen) is dat niet altijd zo. In dat laatste geval is het type UNKNOWN. In die laatste
     * gevallen kan het type pas definitief bepaald worden tijdens evaluatie.
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context is.
     * @return Type van de Expressie.
     */
    ExpressieType getType(Context context);

    /**
     * Evalueert de expressie gegeven een context van identifiers. In de regel wordt de expressie berekend voor een
     * bepaald BRP-object. Dit object is via de context beschikbaar; standaard is dat "persoon", een identifier die
     * verwijst naar de persoon waarvoor de expressie geëvalueerd wordt).
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context is.
     * @return Het resultaat van de evaluatie.
     */
    Expressie evalueer(Context context);

    /**
     * Geeft de prioriteit van de expressie.
     * @return Prioriteit van de expressie.
     */
    Prioriteit getPrioriteit();

    /**
     * Geeft TRUE als de Expressie een constante waarde (bijvoorbeeld constant getal of string) betreft; anders FALSE.
     * @return TRUE als Expressie een constante waarde is; anders FALSE.
     */
    boolean isConstanteWaarde();

    /**
     * Geeft TRUE als de Expressie een constante waarde (niet NULL en geen foutmelding) van het gegeven type betreft;
     * anders FALSE.
     * @param expressieType Type waarvan de constante moet zijn.
     * @return TRUE als Expressie een constante boolean-waarde is; anders FALSE.
     */
    default boolean isConstanteWaarde(ExpressieType expressieType) {
        return isConstanteWaarde(expressieType, null);
    }

    /**
     * Geeft TRUE als de Expressie een constante waarde (niet NULL en geen foutmelding) van het gegeven type betreft;
     * anders FALSE.
     * @param expressieType Type waarvan de constante moet zijn.
     * @param context De bekende symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context is.
     * @return TRUE als Expressie een constante boolean-waarde is; anders FALSE.
     */
    default boolean isConstanteWaarde(ExpressieType expressieType, Context context) {
        return isConstanteWaarde() && getType(context) == expressieType;
    }

    /**
     * Geeft TRUE als de Expressie een null-waarde representeert. Dit kan een 'constante' waarde NULL zijn, maar ook
     * een berekening die niet kan worden afgerond door het ontbreken van velden of waarden.
     * @return TRUE als Expressie null is; anders FALSE.
     */
    default boolean isNull() {
        return this == NullLiteral.INSTANCE;
    }

    /**
     * Geeft TRUE als de Expressie een variabele is; anders FALSE.
     * @return TRUE als Expressie een variabele is; anders FALSE.
     */
    default boolean isVariabele() {
        return false;
    }

    /**
     * Geeft de boolean-waarde van de expressie. Als het type niet BOOLEAN is, is het resultaat standaard FALSE.
     * @return Boolean waarde van de expressie.
     */
    default boolean alsBoolean() {
        return false;
    }

    /**
     * Geeft de expressie terug als een compositie.
     * @return de expressie als compositie
     */
    default LijstExpressie alsLijst() {
        if (this instanceof LijstExpressie) {
            return (LijstExpressie) this;
        }
        throw new ExpressieRuntimeException("Expressie is geen compositie");
    }

    /**
     * Geeft de stringwaarde van de expressie. Als het type niet STRING is, is het resultaat de stringrepresentatie
     * van de expressie.
     * @return Stringwaarde van de expressie.
     */
    default String alsString() {
        return toString();
    }
}
