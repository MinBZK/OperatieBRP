/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;

/**
 * De comparator voor {@link Verwerkingssoort}.
 */
@Bedrijfsregel(Regel.R1806)
final class VerwerkingssoortComparator implements Comparator<Verwerkingssoort>, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * VerwerkingssoortComparator.
     */
    static final VerwerkingssoortComparator INSTANCE = new VerwerkingssoortComparator();

    @Override
    public int compare(final Verwerkingssoort verwerkingssoort1, final Verwerkingssoort verwerkingssoort2) {
        int vergelijking = 0;
        if (verwerkingssoort1 != null && verwerkingssoort2 != null) {
            vergelijking = verwerkingssoort1.getId() - verwerkingssoort2.getId();
        }
        return vergelijking;
    }

}
