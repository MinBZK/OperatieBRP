/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * Represents a termination record as described in the sPd-protocol.
 */
final class TerminationRecord implements SpdElement {

    /**
     * The length field of the operation record.
     */
    static final String LENGTH_FIELD = "00000";

    @Override
    public <V> V accept(final SpdElementVisitor<V> visitor) {
        return visitor.visit(this);
    }
}
