/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * Interface defining sPd-protocol elements.
 */
@FunctionalInterface
interface SpdElement {
    /**
     * Accept method for visitor pattern.
     * @param visitor visitor
     * @param <V> result
     * @return object of type V
     */
    <V> V accept(SpdElementVisitor<V> visitor);
}
