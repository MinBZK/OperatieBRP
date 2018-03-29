/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Resolver voor de partij code van de versturende partij.
 */
@FunctionalInterface
public interface PartijCodeResolver extends Supplier<Optional<String>> {

    /**
     * @return geauthenticeerde partij code.
     */
    @Override
    Optional<String> get();

    /**
     * Enumeratie van headers.
     */
    enum HEADER {
        PARTIJ_CODE("partij-code");

        private final String naam;

        HEADER(String naam) {
            this.naam = naam;
        }

        /**
         * Geeft de naam van de header terug.
         * @return naam van de header
         */
        public String getNaam() {
            return this.naam;
        }
    }
}
