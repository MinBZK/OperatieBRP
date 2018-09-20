/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

/**
 * Type enumeratie voor de IST stapel matching.
 */
public enum StapelMatchType {
    /**
     * Als stapels 1 op 1 matching zijn.
     */
    MATCHED(1),
    /**
     * Als een stapel op meerdere stapels matched.
     */
    NON_UNIQUE_MATCH(2),
    /**
     * Als de stapel nieuw is.
     */
    STAPEL_NIEUW(3),
    /**
     * Als de stapel verwijderd is.
     */
    STAPEL_VERWIJDERD(4);

    private final int sorteerVolgorde;

    /**
     * Constructor voor enumeratie.
     * 
     * @param sorteerVolgorde
     *            geeft de volgorde aan waarin deze enumeratie gesorteerd moet worden
     */
    StapelMatchType(final int sorteerVolgorde) {
        this.sorteerVolgorde = sorteerVolgorde;
    }

    /**
     * @return De sorteer volgorde.
     */
    public int getSorteerVolgorde() {
        return sorteerVolgorde;
    }
}
