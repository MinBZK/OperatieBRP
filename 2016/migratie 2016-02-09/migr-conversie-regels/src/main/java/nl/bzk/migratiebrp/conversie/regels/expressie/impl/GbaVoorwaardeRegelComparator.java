/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.io.Serializable;
import java.util.Comparator;
import org.springframework.stereotype.Component;

/**
 * Bepaald de volgorde van de GbaVoorwaardeRegel.
 */
@Component
public class GbaVoorwaardeRegelComparator implements Comparator<GbaVoorwaardeRegel>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public final int compare(final GbaVoorwaardeRegel voorwaarde1, final GbaVoorwaardeRegel voorwaarde2) {
        return voorwaarde1.volgorde() - voorwaarde2.volgorde();
    }

}
