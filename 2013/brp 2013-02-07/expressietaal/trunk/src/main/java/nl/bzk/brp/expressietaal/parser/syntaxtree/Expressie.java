/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;

/**
 * Interface voor expressies.
 */
public interface Expressie {

    /**
     * Geeft het type van de Expressie, zo precies mogelijk als is vastgesteld. Voor de meeste expressies is er een
     * intrinsiek type bekend (boolean operator, optellen, aftrekken, enzovoort), maar voor symbolische waarden
     * (velden e.d.) is dat niet altijd zo. Het gebruik van een symbool in een expressie kan echter wel een type
     * impliceren.
     *
     * @return Type van de Expressie.
     */
    ExpressieType getType();

    /**
     * Geeft de expressie gerepresenteerd als leesbare string.
     *
     * @return De stringrepresentatie.
     */
    String alsLeesbareString();

    /**
     * Geeft de expressie gepresenteerd in een formele(re) notatie dan een leesbare string.
     *
     * @return De stringrepresentatie.
     */
    String alsFormeleString();

    /**
     * Geeft true als de Expressie een constante waarde (bijvoorbeeld constant getal of string) betreft; anders false.
     *
     * @return True als Expressie een constante waarde is.
     */
    boolean isConstanteWaarde();

    /**
     * Geeft true als de Expressie een vaste lijstexpressie betreft (bijvoorbeeld "[10, 20, 30]") en niet bijvoorbeeld
     * een variabele of functie.
     *
     * @return True als Expressie een vaste lijstexpressie is.
     */
    boolean isLijstExpressie();

    /**
     * Geeft het type van elementen, indien de expressie een lijst is (isLijstExpressie() == true) en alle elementen
     * van hetzelfde type zijn. Zo niet, dan wordt
     * ExpressieType.UNKNOWN teruggegeven.
     *
     * @return Type van de elementen of UNKNOWN.
     */
    ExpressieType getTypeElementen();

    /**
     * Geeft true als de Expressie een variabele is.
     *
     * @return True als Expressie een variabele is.
     */
    boolean isVariabele();

    /**
     * Geeft true als de Expressie een attribuut is.
     *
     * @return True als Expressie een attribuut is.
     */
    boolean isAttribuut();

    /**
     * Geeft true als de Expressie een rootobject representeert.
     *
     * @return True als Expressie een rootobject representeert.
     */
    boolean isRootObject();

    /**
     * Optimaliseert de expressie zoveel mogelijk. Daarbij worden berekenbare (sub)expressies uitgerekend en vervangen
     * door het resultaat. De expressie "10+5" wordt bijvoorbeeld gereduceerd tot "15" en "leeftijd < 18 AND FALSE" tot
     * "FALSE". Er vindt geen resolutie van attributen plaats.
     *
     * @return De geoptimaliseerde expressie.
     */
    Expressie optimaliseer();

    /**
     * Evalueert de expressie gegeven de dictionary van objecten.
     *
     * @param context De bekende symbolische namen (identifiers) afgebeeld op hun bijbehorend RootObject.
     * @return Het resultaat van de evaluatie.
     */
    EvaluatieResultaat evalueer(Context context);

    /**
     * Geeft TRUE als het attribuut voorkomt in de expressie.
     *
     * @param attribuut Te zoeken attribuut.
     * @return TRUE als attribuut voorkomt in expressie.
     */
    boolean includes(Attributes attribuut);
}
