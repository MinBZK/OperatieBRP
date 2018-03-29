/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JoinInformatie.
 */
class JoinInformatie {
    private String persAlias;
    private Map<String, String> aliasMap = new HashMap<>();
    private Set<Tabel> toegevoegdeTabellen = new HashSet<>();

    /**
     * @param persAlias persAlias
     */
    JoinInformatie(final String persAlias) {
        this.persAlias = persAlias;
    }

    String getPersAlias() {
        return persAlias;
    }

    Map<String, String> getAliasMap() {
        return aliasMap;
    }

    Set<Tabel> getToegevoegdeTabellen() {
        return toegevoegdeTabellen;
    }
}
