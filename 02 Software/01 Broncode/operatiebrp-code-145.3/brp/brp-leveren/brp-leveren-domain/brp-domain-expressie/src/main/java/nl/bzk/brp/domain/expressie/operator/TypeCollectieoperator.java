/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

/**
 * Enumeratie voor de wijze waarop collectieoperatoren
 * de vergelijkingen doen.
 *
 * @see EAOperator
 * @see EAINOperator
 * @see EAINWildcardOperator
 */
public enum TypeCollectieoperator {

    /**
     * Logische AND constructie. Alle waarden moeten voldoen aan de vergelijking.
     * <br>bijvoorbeeld:
     * <br>{1,2,3} A&gt;0
     */
    EN,
    /**
     * Logische OR constructie. Er moet minimaal één waarde voldoen aan de vergelijking.
     * <br>bijvoorbeeld:
     * <br>{1,2,3} E&gt;2
     */
    OF
}
