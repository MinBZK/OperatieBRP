/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerelateerde informatie.
 */
public final class GerelateerdeInformatie {

    private final List<Long> administratieveHandelingIds = new ArrayList<>();
    private final List<String> partijen = new ArrayList<>();
    private final List<String> aNummers = new ArrayList<>();

    /**
     * Maakt een GerelateerdeInformatie object.
     * 
     * @param administratieveHandelingIds
     *            lijst van administratieve handeling ids
     * @param partijen
     *            lijst van partijen
     * @param aNummers
     *            lijst van anummers
     */
    public GerelateerdeInformatie(final List<Long> administratieveHandelingIds, final List<String> partijen, final List<String> aNummers) {
        vul(administratieveHandelingIds, this.administratieveHandelingIds);
        vul(partijen, this.partijen);
        vul(aNummers, this.aNummers);
    }

    private static <T>  void vul(final List<T> bron, final List<T> doel) {
        if (bron != null) {
            for (final T waarde : bron) {
                if (waarde != null && !"".equals(waarde)) {
                    doel.add(waarde);
                }
            }
        }
    }

    /**
     * Geeft het id van de administratieve handeling.
     * 
     * @return administratieveHandelingId
     */
    public List<Long> getAdministratieveHandelingIds() {
        return administratieveHandelingIds;
    }

    /**
     * Geeft de partijen.
     * 
     * @return partijen
     */
    public List<String> getPartijen() {
        return partijen;
    }

    /**
     * Geeft de anummers.
     * 
     * @return aNummers
     */
    public List<String> getaNummers() {
        return aNummers;
    }

}
