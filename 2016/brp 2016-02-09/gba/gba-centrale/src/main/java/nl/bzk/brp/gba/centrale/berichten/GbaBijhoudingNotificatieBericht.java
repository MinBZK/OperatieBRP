/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Simpele POJO waar de informatie in staat die wordt verstuurd, puur voor doorgeven van administratieve handeling id
 * en bijgehouden persoon id's in JSON formaat.
 */
public final class GbaBijhoudingNotificatieBericht {

    @JsonProperty
    private Long administratieveHandelingId;

    @JsonProperty
    private List<Integer> bijgehoudenPersoonIds;

    /**
     * Default constructor nodig voor deserializatie.
     */
    public GbaBijhoudingNotificatieBericht() {

    }

    /**
     * Constructor die een administratieve handeling id en een afnemer aanneemt.
     *
     * @param administratieveHandelingId De administratieve handeling.
     * @param bijgehoudenPersoonIds     De lijst met bijgehouden personen.
     */
    public GbaBijhoudingNotificatieBericht(final Long administratieveHandelingId,
                                 final List<Integer> bijgehoudenPersoonIds)
    {
        this.administratieveHandelingId = administratieveHandelingId;
        this.bijgehoudenPersoonIds = bijgehoudenPersoonIds;

        if (administratieveHandelingId == null) {
            throw new IllegalArgumentException("Er dient een identifier van een administratieve handeling meegegeven "
                                                       + "te worden na een bijhouding van de GBA.");
        }

        if (bijgehoudenPersoonIds == null || bijgehoudenPersoonIds.isEmpty()) {
            throw new IllegalArgumentException("Er dient minimaal één bijgehouden persoon meegegeven te worden "
                                                       + "na een bijhouding van de GBA.");
        }
    }

    /**
     * @return administratieve handeling id
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * @param administratieveHandelingId administratieve handeling id
     */
    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * @return bijgehouden personen ids
     */
    public List<Integer> getBijgehoudenPersoonIds() {
        return bijgehoudenPersoonIds;
    }

    /**
     * @param bijgehoudenPersoonIds bijgehouden personen ids
     */
    public void setBijgehoudenPersoonIds(final List<Integer> bijgehoudenPersoonIds) {
        this.bijgehoudenPersoonIds = bijgehoudenPersoonIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("admhndID", administratieveHandelingId)
                .append("persoonIDs", bijgehoudenPersoonIds)
                .toString();
    }
}
