/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.admhndpublicatie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.HashSet;
import java.util.Set;

/**
 * HandelingVoorPublicatie.
 */
@JsonAutoDetect
public final class HandelingVoorPublicatie {

    private Long admhndId;
    private Set<Long> bijgehoudenPersonen = new HashSet<>();

    /**
     * default constructor.
     */
    public HandelingVoorPublicatie() {
        ////public constructor v json deserialisatie
    }

    /**
     * Gets bijgehouden personen.
     * @return the bijgehouden personen
     */
    public Set<Long> getBijgehoudenPersonen() {
        return bijgehoudenPersonen;
    }

    /**
     * Sets bijgehouden personen.
     * @param bijgehoudenPersonen the bijgehouden personen
     */
    public void setBijgehoudenPersonen(final Set<Long> bijgehoudenPersonen) {
        this.bijgehoudenPersonen = bijgehoudenPersonen;
    }

    /**
     * Gets admhnd id.
     * @return the admhnd id
     */
    public Long getAdmhndId() {
        return admhndId;
    }

    /**
     * Sets admhnd id.
     * @param admhndId the admhnd id
     */
    public void setAdmhndId(final Long admhndId) {
        this.admhndId = admhndId;
    }

}
