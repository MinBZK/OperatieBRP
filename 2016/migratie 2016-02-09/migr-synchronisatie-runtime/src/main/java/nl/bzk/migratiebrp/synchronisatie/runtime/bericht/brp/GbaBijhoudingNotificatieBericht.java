/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Deze class definieert de interface voor het GBA Bijhoudings notificatie bericht naar de BRP levering module.
 *
 * Simpele POJO waar de informatie in staat die wordt verstuurd, puur voor doorgeven van administratieve handeling id en
 * bijgehouden persoon id's in JSON formaat.
 */
public final class GbaBijhoudingNotificatieBericht {
    @JsonProperty
    private final Long administratieveHandelingId;
    @JsonProperty
    private final List<Integer> bijgehoudenPersoonIds;

    /**
     * Constructor die een administratieve handeling id en een afnemer aanneemt.
     *
     * @param administratieveHandelingId
     *            De administratieve handeling.
     * @param bijgehoudenPersoonIds
     *            De lijst met bijgehouden personen.
     */
    public GbaBijhoudingNotificatieBericht(final Long administratieveHandelingId, final List<Integer> bijgehoudenPersoonIds) {
        this.administratieveHandelingId = administratieveHandelingId;
        this.bijgehoudenPersoonIds = bijgehoudenPersoonIds;
        if (administratieveHandelingId == null) {
            throw new IllegalArgumentException("Er dient een identifier van een administratieve handeling meegegeven "
                                               + "te worden na een bijhouding van de GBA.");
        }
        if (bijgehoudenPersoonIds == null || bijgehoudenPersoonIds.isEmpty()) {
            throw new IllegalArgumentException("Er dient minimaal één bijgehouden persoon meegegeven te worden " + "na een bijhouding van de GBA.");
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("admhndID", administratieveHandelingId).append("persoonIDs", bijgehoudenPersoonIds).toString();
    }
}
