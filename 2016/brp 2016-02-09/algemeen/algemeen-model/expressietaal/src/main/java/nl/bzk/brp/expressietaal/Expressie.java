/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Basis-interface voor alle expressies. De belangrijkste kenmerken van expressies zijn dat ze 1) een type hebben en
 * 2) geëvalueerd kunnen worden.
 */
public interface Expressie {

    /**
     * Geeft het type van de Expressie, zo precies mogelijk. Voor de meeste expressies is er een
     * intrinsiek type bekend (boolean operator, optellen, aftrekken, enzovoort), maar voor symbolische waarden
     * (variabelen en attributen) is dat niet altijd zo. In dat laatste geval is het type UNKNOWN. In die laatste
     * gevallen kan het type pas definitief bepaald worden tijdens evaluatie.
     *
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen
     *                context is.
     * @return Type van de Expressie.
     */
    ExpressieType getType(Context context);

    /**
     * Evalueert de expressie gegeven een context van identifiers. In de regel wordt de expressie berekend voor een
     * bepaald BRP-object. Dit object is via de context beschikbaar; standaard is dat "persoon", een identifier die
     * verwijst naar de persoon waarvoor de expressie geëvalueerd wordt).
     *
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen
     *                context is.
     * @return Het resultaat van de evaluatie.
     */
    Expressie evalueer(Context context);

    /**
     * Geeft de prioriteit van de expressie.
     *
     * @return Prioriteit van de expressie.
     */
    int getPrioriteit();

    /**
     * Geeft TRUE als de Expressie een fout is (een fout representeert).
     *
     * @return TRUE als de Expressie een fout is; anders FALSE.
     */
    boolean isFout();

    /**
     * Geeft TRUE als de Expressie een constante waarde (bijvoorbeeld constant getal of string) betreft; anders FALSE.
     *
     * @return TRUE als Expressie een constante waarde is; anders FALSE.
     */
    boolean isConstanteWaarde();

    /**
     * Geeft TRUE als de Expressie een constante waarde (niet NULL en geen foutmelding) van het gegeven type betreft;
     * anders FALSE.
     *
     * @param expressieType Type waarvan de constante moet zijn.
     * @return TRUE als Expressie een constante boolean-waarde is; anders FALSE.
     */
    boolean isConstanteWaarde(ExpressieType expressieType);

    /**
     * Geeft TRUE als de Expressie een constante waarde (niet NULL en geen foutmelding) van het gegeven type betreft;
     * anders FALSE.
     *
     * @param expressieType Type waarvan de constante moet zijn.
     * @param context       De bekende symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen
     *                      context is.
     * @return TRUE als Expressie een constante boolean-waarde is; anders FALSE.
     */
    boolean isConstanteWaarde(ExpressieType expressieType, Context context);

    /**
     * Geeft TRUE als de Expressie een null-waarde representeert. Dit kan een 'constante' waarde NULL zijn, maar ook
     * een berekening die niet kan worden afgerond door het ontbreken van velden of waarden.
     *
     * @return TRUE als Expressie null is; anders FALSE.
     */
    boolean isNull();

    /**
     * Geeft TRUE als de Expressie een null-waarde representeert. Dit kan een 'constante' waarde NULL zijn, maar ook
     * een berekening die niet kan worden afgerond door het ontbreken van velden of waarden.
     *
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen
     *                context is.
     * @return TRUE als Expressie null is; anders FALSE.
     */
    boolean isNull(Context context);

    /**
     * Geeft TRUE als de Expressie een variabele is; anders FALSE.
     *
     * @return TRUE als Expressie een variabele is; anders FALSE.
     */
    boolean isVariabele();

    /**
     * Geeft TRUE als de Expressie een lijstexpressie betreft (bijvoorbeeld "{10, 20, 30}") en niet bijvoorbeeld
     * een string of getal; anders FALSE.
     *
     * @return TRUE als Expressie een lijstexpressie is; anders FALSE.
     */
    boolean isLijstExpressie();

    /**
     * Geeft het type van elementen, indien de expressie een lijst is (isLijstExpressie() == true) en alle elementen
     * van hetzelfde type zijn. Zo niet, dan wordt ExpressieType.UNKNOWN teruggegeven.
     *
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen
     *                context is.
     * @return Type van de elementen of UNKNOWN.
     */
    ExpressieType bepaalTypeVanElementen(Context context);

    /**
     * Geeft het aantal elementen waar de expressie uit bestaat. Indien de expressie geen lijst is, is het resultaat
     * altijd 1.
     *
     * @return Aantal elementen.
     */
    int aantalElementen();

    /**
     * Geeft de elementen waaruit de expressie bestaat. Als het een lijst is, zijn het de individuele elementen van die
     * lijst, anders de expressie zelf.
     *
     * @return Elementen van de expressie.
     */
    Iterable<? extends Expressie> getElementen();

    /**
     * Geeft het element op de gegeven index terug. Het eerste element heeft index 0.
     * Als de expressie geen lijst is, is het eerste element (index 0) de expressie zelf.
     *
     * @param index Index van element.
     * @return Element of NULL als de index buiten bereik van de lijst is.
     */
    Expressie getElement(final int index);

    /**
     * Geeft de boolean-waarde van de expressie. Als het type niet BOOLEAN is, is het resultaat standaard FALSE.
     *
     * @return Boolean waarde van de expressie.
     */
    boolean alsBoolean();

    /**
     * Geeft de integerwaarde van de expressie. Als het type niet NUMBER is, is het resultaat standaard 0.
     *
     * @return Integerwaarde van de expressie.
     */
    int alsInteger();

    /**
     * Geeft de longwaarde van de expressie. Als het type niet NUMBER is, is het resultaat standaard 0.
     *
     * @return Integerwaarde van de expressie.
     */
    long alsLong();

    /**
     * Geeft de stringwaarde van de expressie. Als het type niet STRING is, is het resultaat de stringrepresentatie
     * van de expressie.
     *
     * @return Stringwaarde van de expressie.
     */
    String alsString();

    /**
     * Geeft de attribuutverwijzing als waarde van de expressie terug. Als het type niet ATTRIBUUT is, is het resultaat
     * NULL.
     *
     * @return Attribuutverwijzing als waarde van de expressie.
     */
    Attribuut getAttribuut();

    /**
     * Geeft de groepverwijzing als waarde van de expressie terug. Als het type niet GROEP is, is het resultaat
     * NULL.
     *
     * @return Groepverwijzing als waarde van de expressie.
     */
    Groep getGroep();

    /**
     * Geeft TRUE als de variabele id als ongebonden variabele voorkomt in de expressie; anders FALSE. Als de
     * variabele binnen de expressie zelf wordt gedefinieerd en niet bekend is buiten de expressie,
     * dan is het resultaat dus FALSE.
     *
     * @param id Te zoeken variabele.
     * @return TRUE als de variabele ongebonden voorkomt in de expressie; anders FALSE.
     */
    boolean bevatOngebondenVariabele(final String id);

    /**
     * Geeft een geoptimaliseerde versie van de expressie. Daarbij worden berekenbare (sub)expressies uitgerekend en
     * vervangen door het resultaat. De expressie "10 + 5" wordt bijvoorbeeld gereduceerd tot "15" en
     * "leeftijd < 18 EN FALSE" tot "FALSE". Alle expressies die onafhankelijk zijn van een externe waarde (via een
     * attribuut of functie), kunnen worden berekend.
     *
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen
     *                context is.
     * @return De geoptimaliseerde expressie.
     */
    Expressie optimaliseer(final Context context);
}
