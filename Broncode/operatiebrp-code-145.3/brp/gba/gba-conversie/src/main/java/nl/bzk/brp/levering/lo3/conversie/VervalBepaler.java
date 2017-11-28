/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Bepaal het vervallen record in een historie set.
 */
public interface VervalBepaler {

    /**
     * Bepaal het vervallen record (record waarbij de actie verval voorkomt in acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het vervallen record, kan null zijn
     */
    static MetaRecord bepaalVerval(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        MetaRecord resultaat = null;

        final List<MetaRecord> records =
                historieSet.stream()
                        .filter(
                                h -> (h.getActieVerval() != null && acties.contains(h.getActieVerval().getId()))
                                        || (h.getActieVervalTbvLeveringMutaties() != null && acties.contains(h.getActieVervalTbvLeveringMutaties().getId())))
                        .collect(Collectors.toList());

        for (final MetaRecord historie : records) {
            if (resultaat == null || (resultaat.getActieAanpassingGeldigheid() != null && historie.getActieAanpassingGeldigheid() == null)) {
                resultaat = historie;
            }
        }

        return resultaat;
    }
}
