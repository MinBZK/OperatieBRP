/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.nfr.loadgenerator;

/**
 * Administratieve Handeling.
 */
final class Handeling {

    private final Long handelingId;
    private final Integer persoonId;

    /**
     * Instantieert een nieuwe administratieve handeling.
     *
     * @param persoonId   persoon id
     * @param handelingId handeling id
     */
    public Handeling(final Integer persoonId, final Long handelingId) {
        this.handelingId = handelingId;
        this.persoonId = persoonId;
    }

    public Long getHandelingId() {
        return handelingId;
    }

    public Integer getPersoonId() {
        return persoonId;
    }

}
