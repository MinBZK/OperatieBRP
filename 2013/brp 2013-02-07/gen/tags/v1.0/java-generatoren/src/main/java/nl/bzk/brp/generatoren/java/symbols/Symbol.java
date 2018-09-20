/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import nl.bzk.brp.metaregister.model.Attribuut;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van attributen en worden afgebeeld op een basistype.
 */
public interface Symbol {
    /**
     * Geeft de naam van het betreffende attribuut zoals het in de enumeratie voorkomt.
     *
     * @return Naam van attribuut.
     */
    String getEnumAttribuutNaam();

    /**
     * Geeft de syntax van het betreffende attribuut zoals het in de expressietaal gebruikt wordt.
     *
     * @return Syntax van symbol.
     */
    String getSyntax();

    /**
     * Geeft het (expressie)type van het attribuut. Dit is het type, zoals in de expressietaal gebruikt wordt.
     *
     * @return Expressietype van het symbol.
     */
    String getExpressieType();

    /**
     * Geeft de naam van het type waartoe het symbol in de expressietaal behoort. In de meeste gevallen zal dit het
     * type PERSOON zijn.
     *
     * @return Type van de groep waartoe symbol behoort.
     */
    String getExpressieTypeGroep();

    /**
     * Geeft het Attribuut zoals het in het BMR voorkomt en waarop het symbol is afgebeeld.
     *
     * @return BMR-attribuut.
     */
    Attribuut getBmrAttribuut();

    /**
     * Geeft het symbool waartoe dit symbool behoort. Als het symbool is afgeleid van een 'inverse attribuut'
     * (bijvoorbeeld huisnummer dat bij een adres hoort), dan is via getParentSymbol te achterhalen wat het
     * 'omvattende' attribuut (adres) is.
     *
     * @return Symbool van de parent.
     */
    Symbol getParentSymbol();

    /**
     * Geeft het toegangspad tot de waarde van het symbol in Java. Hier vindt de feitelijke afbeelding plaats van
     * symbolen uit de expressietaal naar Java-objecten en -attributen.
     *
     * @return Java-toegangspad tot het attribuut.
     */
    JavaAccessPath getJavaAccessPath();

    /**
     * Geeft het JavaDoc-commentaar voor de functie/klasse die verantwoordelijk is voor het ophalen van de waarde van
     * het symbol.
     *
     * @return JavaDoc-commentaar.
     */
    String getGetterJavaDoc();

    /**
     * Geeft de naam van de (gegenereerde) klasse die verantwoordelijk is voor het ophalen van de waarde van het symbol.
     *
     * @return Klassenaam.
     */
    String getGetterClassName();
}

