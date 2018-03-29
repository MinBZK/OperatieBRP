/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

/**
 * Constanten voor JMX.
 */
final class JMXConstanten {

    /**
     * Object name to bind JMX object to.
     */
    static final String OBJECT_NAME = "nl.bzk.migratiebrp.isc:name=ISC";

    /**
     * constructor.
     */
    private JMXConstanten() {
        throw new IllegalStateException("Class moet niet worden geïnstantieerd.");
    }
}
