/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.grammar;

import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.OperatorType;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;


/**
 * Interface die de door de parser te gebruiken grammar beschrijft. Implementaties van deze interface zullen de
 * werkelijke grammar bevatten en op basis daarvan de door deze interface gedefinieerde methodes moeten implementeren
 * zodat de parser op basis daarvan de juiste parsetree (syntax tree) kan opbouwen.
 */
public interface Grammar {

    /**
     * Indicatie of de opgegeven identifier binnen de grammar aanwezig is (als identifier) of niet.
     *
     * @param identifier de te controleren identifier.
     * @return {@code true} indien de identifier bestaat en anders {@code false}.
     */
    boolean isIdentifier(String identifier);

    /**
     * Indicatie of de opgegeven operator-identifier binnen de grammar aanwezig is (als operator-identifier) of niet.
     *
     * @param identifier de te controleren operator-identifier.
     * @return {@code true} indien de operator-identifier bestaat en anders {@code false}.
     */
    boolean isOperatorIdentifier(String identifier);

    /**
     * Retourneert het {@link OperatorType} wat door de opgegeven operator-identifier wordt weergegeven.
     *
     * @param identifier de operator-identifier waarvoor het operatortype wordt opgevraagd.
     * @return het {@link OperatorType} wat door de opgegeven operator-identifier wordt weergegeven.
     */
    OperatorType getOperatorTypeVoorOperatorIdentifier(String identifier);

    /**
     * Indicatie of het opgegeven operator-symbool binnen de grammar aanwezig is (als operator-symbool) of niet.
     *
     * @param symbool het te controleren operator-symbool.
     * @return {@code true} indien het operator-symbool bestaat en anders {@code false}.
     */
    boolean isOperatorSymbool(String symbool);

    /**
     * Retourneert het {@link OperatorType} wat door het opgegeven operator-symbool wordt weergegeven.
     *
     * @param symbool het operator-symbool waarvoor het operatortype wordt opgevraagd.
     * @return het {@link OperatorType} wat door het opgegeven operator-symbool wordt weergegeven.
     */
    OperatorType getOperatorTypeVoorOperatorSymbool(String symbool);

    /**
     * Indicatie of de opgegeven functie binnen de grammar aanwezig is (als functie) of niet.
     *
     * @param functie de te controleren functie.
     * @return {@code true} indien de functie bestaat en anders {@code false}.
     */
    boolean isFunctie(String functie);

    /**
     * Retourneert het aantal argumenten voor de opgegeven functie.
     *
     * @param functie de functie waarvoor het aantal argumenten wordt opgevraagd.
     * @return het aantal argumenten voor de opgegeven functie.
     */
    int getAantalArgumentenVoorFunctie(String functie);

    /**
     * Indicatie of de opgegeven identifier binnen de grammar bekend is als een identifier die vervangen dient te
     * worden of niet.
     *
     * @param identifier de te controleren identifier.
     * @return {@code true} indien de identifier conform de grammar vervangen dient te worden en anders {@code false}.
     */
    boolean isTeVervangenIdentifier(String identifier);

    /**
     * Retourneert een lijst van {@code Token} instanties die als vervanging gebruikt dienen te worden voor de
     * opgegeven {@code identifier}.
     *
     * @param identifier de identifier waarvoor de vervanging wordt opgevraagd.
     * @return een lijst van tokens.
     */
    List<Token> getVervangingVoorIdentifier(String identifier);

}
