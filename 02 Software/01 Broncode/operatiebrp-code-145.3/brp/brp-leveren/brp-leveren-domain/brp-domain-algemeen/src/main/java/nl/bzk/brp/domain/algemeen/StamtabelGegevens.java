/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.util.List;
import java.util.Map;

/**
 * StamtabelGegevens.
 */
public final class StamtabelGegevens {
    /**
     * TABEL_POSTFIX.
     */
    public static final String TABEL_POSTFIX = "Tabel";
    private final StamgegevenTabel stamgegevenTabel;
    private final List<Map<String, Object>> stamgegevens;

    /**
     * @param stamgegevenTabel stamgegevenTabel
     * @param stamgegevens     stamgegevens
     */
    public StamtabelGegevens(final StamgegevenTabel stamgegevenTabel, final List<Map<String, Object>> stamgegevens) {
        this.stamgegevenTabel = stamgegevenTabel;
        this.stamgegevens = stamgegevens;
    }

    public StamgegevenTabel getStamgegevenTabel() {
        return stamgegevenTabel;
    }

    public List<Map<String, Object>> getStamgegevens() {
        return stamgegevens;
    }

}
